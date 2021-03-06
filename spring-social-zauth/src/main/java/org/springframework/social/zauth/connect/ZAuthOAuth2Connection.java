/**
 * Copyright (C) 2015 Zalando SE (http://tech.zalando.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.zauth.connect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.GenericTypeResolver;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.ServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ZAuthOAuth2Connection<A> extends ZAuthAbstractConnection<A> {

    private static final long serialVersionUID = 4057584084077577480L;

    private transient final OAuth2ServiceProvider<A> serviceProvider;

    private String accessToken;
    
    private String refreshToken;
    
    private Long expireTime;

    private transient A api;
    
    private transient A apiProxy;

    private Map<String,String> additionalParams;

    /**
     * Creates a new {@link OAuth2Connection} from a access grant response.
     * Designed to be called to establish a new {@link OAuth2Connection} after receiving an access grant successfully.
     * The providerUserId may be null in this case: if so, this constructor will try to resolve it using the service API obtained from the {@link OAuth2ServiceProvider}.
     * @param providerId the provider id e.g. "facebook".
     * @param providerUserId the provider user id (may be null if not returned as part of the access grant)
     * @param accessToken the granted access token
     * @param refreshToken the granted refresh token
     * @param expireTime the access token expiration time
     * @param serviceProvider the OAuth2-based ServiceProvider
     * @param apiAdapter the ApiAdapter for the ServiceProvider
     */
    public ZAuthOAuth2Connection(String providerId, String providerUserId, String accessToken, String refreshToken, Long expireTime,
            OAuth2ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter, Map<String,String> additionalParams) {
        super(apiAdapter);
        this.serviceProvider = serviceProvider;
        initAccessTokens(accessToken, refreshToken, expireTime);
        initApi();
        initApiProxy();
        initKey(providerId, providerUserId);
        this.additionalParams = additionalParams;
    }
    
    /**
     * Creates a new {@link OAuth2Connection} from the data provided.
     * Designed to be called when re-constituting an existing {@link Connection} from {@link ConnectionData}.
     * @param data the data holding the state of this connection
     * @param serviceProvider the OAuth2-based ServiceProvider
     * @param apiAdapter the ApiAdapter for the ServiceProvider
     */
    public ZAuthOAuth2Connection(ConnectionData data, OAuth2ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
        this(data, serviceProvider, apiAdapter, new HashMap<String,String>());
    }

    /**
     * Creates a new {@link OAuth2Connection} from the data provided.
     * Designed to be called when re-constituting an existing {@link Connection} from {@link ConnectionData}.
     * @param data the data holding the state of this connection
     * @param serviceProvider the OAuth2-based ServiceProvider
     * @param apiAdapter the ApiAdapter for the ServiceProvider
     */
    public ZAuthOAuth2Connection(ConnectionData data, OAuth2ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter, Map<String,String> additionalParams) {
        super(data, apiAdapter);
        this.serviceProvider = serviceProvider;
        initAccessTokens(data.getAccessToken(), data.getRefreshToken(), data.getExpireTime());
        initApi();
        initApiProxy();
        this.additionalParams = additionalParams;
    }

    // implementing Connection

    public boolean hasExpired() {
        synchronized (getMonitor()) {
            return expireTime != null && System.currentTimeMillis() >= expireTime;
        }
    }

    public void refresh() {
        synchronized (getMonitor()) {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.setAll(this.additionalParams);
            AccessGrant accessGrant = serviceProvider.getOAuthOperations().refreshAccess(refreshToken, params);
            initAccessTokens(accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpireTime());
            initApi();
        }
    }

    public void refresh(AccessGrant accessGrant) {
        synchronized (getMonitor()) {
            initAccessTokens(accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpireTime());
//            initApi();
        }
    }

    public A getApi() {
        if (apiProxy != null) {
            return apiProxy;
        } else {
            synchronized (getMonitor()) {
                return api;
            }
        }
    }

    public ConnectionData createData() {
        synchronized (getMonitor()) {
            return new ConnectionData(getKey().getProviderId(), getKey().getProviderUserId(), getDisplayName(), getProfileUrl(), getImageUrl(), accessToken, null, refreshToken, expireTime);
        }
    }

    // internal helpers
    
    private void initAccessTokens(String accessToken, String refreshToken, Long expireTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;       
    }
    
    private void initApi() {
        api = serviceProvider.getApi(accessToken);
    }
    
    @SuppressWarnings("unchecked")
    private void initApiProxy() {
        Class<?> apiType = GenericTypeResolver.resolveTypeArgument(serviceProvider.getClass(), ServiceProvider.class);
        if (apiType.isInterface()) {
            apiProxy = (A) Proxy.newProxyInstance(apiType.getClassLoader(), new Class<?>[] { apiType }, new ApiInvocationHandler());
        }       
    }
    
    private class ApiInvocationHandler implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            synchronized (getMonitor()) {
                if (hasExpired()) {
                    throw new ExpiredAuthorizationException(getKey().getProviderId());
                }
                try {
                    return method.invoke(ZAuthOAuth2Connection.this.api, args);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
        }
    }

    // equas() and hashCode() generated by Eclipse
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((accessToken == null) ? 0 : accessToken.hashCode());
        result = prime * result + ((expireTime == null) ? 0 : expireTime.hashCode());
        result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        @SuppressWarnings("rawtypes")
        ZAuthOAuth2Connection other = (ZAuthOAuth2Connection) obj;
        
        if (accessToken == null) {
            if (other.accessToken != null) return false;
        } else if (!accessToken.equals(other.accessToken)) return false;

        if (expireTime == null) {
            if (other.expireTime != null) return false;
        } else if (!expireTime.equals(other.expireTime)) return false;
        
        if (refreshToken == null) {
            if (other.refreshToken != null) return false;
        } else if (!refreshToken.equals(other.refreshToken)) return false;

        return true;
    }

}
