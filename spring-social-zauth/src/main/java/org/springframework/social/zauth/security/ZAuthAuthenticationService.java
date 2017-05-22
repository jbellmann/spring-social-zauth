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

import java.util.Map;

import org.springframework.social.oauth2.ClientCredentialsSupplier;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.security.provider.OAuth2AuthenticationService;
import org.springframework.social.zauth.api.ZAuth;
import org.springframework.social.zauth.connect.ZAuthConnectionFactory;
import org.springframework.util.Assert;

/**
 * @author jbellmann
 */
public class ZAuthAuthenticationService extends OAuth2AuthenticationService<ZAuth> {

    private final Map<String, String> additionalParams;

    public ZAuthAuthenticationService(ClientCredentialsSupplier clientCredentialsSupplier,
            Map<String, String> additionalParams, String authorizationEndpoint, String tokenEndpoint) {
        super(new ZAuthConnectionFactory(clientCredentialsSupplier, authorizationEndpoint, tokenEndpoint, additionalParams));
        Assert.notNull(additionalParams, "'additionalParams' should not be null");
        this.additionalParams = additionalParams;
    }

    @Override
    protected void addCustomParameters(OAuth2Parameters params) {
        for (Map.Entry<String, String> entry : additionalParams.entrySet()) {
            params.add(entry.getKey(), entry.getValue());
        }
    }
}
