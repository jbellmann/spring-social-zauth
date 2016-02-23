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

import org.springframework.util.Assert;

/**
 * Behave as existing implementation when 'client_id' and 'client_secret' will
 * never change.
 * 
 * @author jbellmann
 *
 */
public class StaticClientCredentialsSupplier implements ClientCredentialsSupplier {

    private final String clientId;
    private final String clientSecret;

    public StaticClientCredentialsSupplier(String clientId, String clientSecret) {
        Assert.hasText(clientId, "'clientId' should never be null or empty");
        Assert.hasText(clientSecret, "'clientSecret' should never be null or empty");
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

}
