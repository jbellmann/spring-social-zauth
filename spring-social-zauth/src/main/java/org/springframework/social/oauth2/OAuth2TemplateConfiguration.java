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
package org.springframework.social.oauth2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Brings in all information needed by normal {@link OAuth2Template} but with
 * {@link Supplier} for 'client_id' and 'client_secret'.
 * 
 * @author jbellmann
 *
 */
public class OAuth2TemplateConfiguration {

    private String authorizeUrl;

    private String authenticateUrl;

    private String accessTokenUrl;

    private final ClientCredentialsSupplier clientCredentialsSupplier;

    private final MultiValueMap<String, String> additionalParams;

    public OAuth2TemplateConfiguration(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl,
            Map<String, String> additionalParams) {
        this(new StaticClientCredentialsSupplier(clientId, clientSecret), authorizeUrl, null, accessTokenUrl,
                additionalParams);
    }

    public OAuth2TemplateConfiguration(ClientCredentialsSupplier clientCredentialsSupplier, String authorizeUrl,
            String accessTokenUrl, Map<String, String> additionalParams) {
        this(clientCredentialsSupplier, authorizeUrl, null, accessTokenUrl, additionalParams);
    }

    public OAuth2TemplateConfiguration(ClientCredentialsSupplier clientCredentialsSupplier, String authorizeUrl,
            String authenticateUrl, String accessTokenUrl, Map<String, String> additionalParams) {
        Assert.notNull(clientCredentialsSupplier, "'clientCredentialsSupplier' should never be null");
        Assert.notNull(authorizeUrl, "The authorizeUrl property cannot be null");
        Assert.notNull(accessTokenUrl, "The accessTokenUrl property cannot be null");

        this.clientCredentialsSupplier = clientCredentialsSupplier;

        this.authorizeUrl = authorizeUrl;

        this.authenticateUrl = authenticateUrl;

        this.accessTokenUrl = accessTokenUrl;
        this.additionalParams = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : additionalParams.entrySet()) {
            this.additionalParams.put(entry.getKey(), Arrays.asList(entry.getValue()));
        }
    }

    public ClientCredentialsSupplier getClientCredentialsSupplier() {
        return clientCredentialsSupplier;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl + getClientInfo();
    }

    public String getAuthenticateUrl() {
        if (authenticateUrl == null) {
            return null;
        } else {
            return authenticateUrl + getClientInfo();
        }
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    protected String getClientInfo() {
        return "?client_id=" + formEncode(clientCredentialsSupplier.getClientId());
    }

    private String formEncode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            // should not happen, UTF-8 is always supported
            throw new IllegalStateException(ex);
        }
    }

    public MultiValueMap<String, String> getAdditionalParameters() {
        return this.additionalParams;
    }
}
