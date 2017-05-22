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

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.ClientCredentialsSupplier;
import org.springframework.social.zauth.api.ZAuth;

/**
 * @author jbellmann
 */
public class ZAuthConnectionFactory extends OAuth2ConnectionFactory<ZAuth> {

    public static final String PROVIDER_ID = "zauth";

    public ZAuthConnectionFactory(ClientCredentialsSupplier clientCredentialsSupplier, String authorizationEndpoint,
            String tokenEndpoint, Map<String,String> additionalParams) {
        super(PROVIDER_ID, new ZAuthServiceProvider(clientCredentialsSupplier, authorizationEndpoint, tokenEndpoint, additionalParams),
                new ZAuthAdapter());
    }

    @Override
    public Connection<ZAuth> createConnection(final AccessGrant accessGrant) {
        Connection<ZAuth> con = super.createConnection(accessGrant);
        return con;
    }

}
