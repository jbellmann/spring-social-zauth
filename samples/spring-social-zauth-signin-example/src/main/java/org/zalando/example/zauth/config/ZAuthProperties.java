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
package org.zalando.example.zauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Only for this example. Not official part of the lib. Could be done this way.
 *
 * @author  jbellmann
 */
@ConfigurationProperties(prefix = "zauth")
public class ZAuthProperties {

    private String clientId;

    private String clientSecret;

    private String credentialsDirectoryPath;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCredentialsDirectoryPath() {
        return credentialsDirectoryPath;
    }

    public void setCredentialsDirectoryPath(String credentialsDirectoryPath) {
        this.credentialsDirectoryPath = credentialsDirectoryPath;
    }

}
