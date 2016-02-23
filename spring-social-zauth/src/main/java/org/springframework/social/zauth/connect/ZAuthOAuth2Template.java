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

import org.springframework.social.oauth2.ClientCredentialsSupplier;
import org.springframework.social.oauth2.DynamicOAuth2Template;
import org.springframework.social.oauth2.OAuth2TemplateConfiguration;

/**
 * @author jbellmann
 */
public class ZAuthOAuth2Template extends DynamicOAuth2Template {

    private static final String AUTHORIZE_URL = "https://auth.zalando.com/oauth2/authorize";
    private static final String ACCESS_TOKEN_URL = "https://auth.zalando.com/oauth2/access_token?realm=employees";

    public ZAuthOAuth2Template(ClientCredentialsSupplier clientCredentialsSupplier) {
        this(clientCredentialsSupplier, AUTHORIZE_URL, ACCESS_TOKEN_URL);
    }

    public ZAuthOAuth2Template(ClientCredentialsSupplier clientCredentialsSupplier,
            String authorizeUrl, String accessTokenUrl) {
        super(new OAuth2TemplateConfiguration(clientCredentialsSupplier, authorizeUrl, accessTokenUrl));
    }
}
