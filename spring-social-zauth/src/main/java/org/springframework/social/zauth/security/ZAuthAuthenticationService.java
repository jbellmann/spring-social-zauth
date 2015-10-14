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
package org.springframework.social.zauth.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.social.connect.Connection;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.security.SocialAuthenticationRedirectException;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.provider.OAuth2AuthenticationService;
import org.springframework.social.zauth.api.ZAuth;
import org.springframework.social.zauth.connect.ZAuthConnectionFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

/**
 * @author  jbellmann
 */
public class ZAuthAuthenticationService extends OAuth2AuthenticationService<ZAuth> {

    static final String DEFAULT_SCOPE = "uid";

    private String defaultScope = DEFAULT_SCOPE;

    public ZAuthAuthenticationService(final String clientId, final String clientSecret) {
        super(new ZAuthConnectionFactory(clientId, clientSecret));
    }

    @Override
    public void setDefaultScope(final String defaultScope) {
        super.setDefaultScope(defaultScope);
        this.defaultScope = defaultScope;
    }

    @Override
    public SocialAuthenticationToken getAuthToken(final HttpServletRequest request, final HttpServletResponse response)
        throws SocialAuthenticationRedirectException {
        String code = request.getParameter("code");
        if (!StringUtils.hasText(code)) {
            OAuth2Parameters params = new OAuth2Parameters();
            params.setRedirectUri(buildReturnToUrl(request));
            setScope(request, params);
            params.add("state", super.getConnectionFactory().generateState()); // TODO: Verify the state value after
                                                                               // callback

            // important
            params.add("realm", "employees");

            throw new SocialAuthenticationRedirectException(getConnectionFactory().getOAuthOperations()
                    .buildAuthenticateUrl(params));
        } else if (StringUtils.hasText(code)) {
            try {
            	if(logger.isDebugEnabled()){            		
            		logger.debug("CODE : " + code);
            	}

                String returnToUrl = buildReturnToUrl(request);
                AccessGrant accessGrant = getConnectionFactory().getOAuthOperations().exchangeForAccess(code,
                        returnToUrl, getAdditionalParameters());

                // TODO avoid API call if possible (auth using token would be fine)
                Connection<ZAuth> connection = getConnectionFactory().createConnection(accessGrant);
                return new SocialAuthenticationToken(connection, null);
            } catch (RestClientException e) {
                logger.warn("failed to exchange for access", e);
                return null;
            }
        } else {
            return null;
        }
    }

    protected MultiValueMap<String, String> getAdditionalParameters() {
        return new LinkedMultiValueMap<String, String>();
    }

    protected void setScope(final HttpServletRequest request, final OAuth2Parameters params) {
        String requestedScope = request.getParameter("scope");
        if (StringUtils.hasLength(requestedScope)) {
            params.setScope(requestedScope);
        } else if (StringUtils.hasLength(defaultScope)) {
            params.setScope(defaultScope);
        }
    }
}
