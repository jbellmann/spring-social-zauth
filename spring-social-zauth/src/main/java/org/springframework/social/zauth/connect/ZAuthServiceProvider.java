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

import java.util.Map;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.ClientCredentialsSupplier;
import org.springframework.social.zauth.api.ZAuth;
import org.springframework.social.zauth.api.impl.ZAuthTemplate;

/**
 * @author jbellmann
 */
public class ZAuthServiceProvider extends AbstractOAuth2ServiceProvider<ZAuth> {

    private final String tokenEndpoint;

    public ZAuthServiceProvider(ClientCredentialsSupplier clientCredentialsSupplier, String authorizationEndpoint,
            String tokenEndpoint, Map<String,String> additionalParams) {
        super(new ZAuthOAuth2Template(clientCredentialsSupplier, authorizationEndpoint, tokenEndpoint, additionalParams));
        this.tokenEndpoint = tokenEndpoint;
    }

    @Override
    public ZAuth getApi(final String accessToken) {
        return new ZAuthTemplate(accessToken, tokenEndpoint);
    }
}
