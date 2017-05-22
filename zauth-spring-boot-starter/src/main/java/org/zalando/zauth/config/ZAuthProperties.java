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
package org.zalando.zauth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Only for this example. Not official part of the lib. Could be done this way.
 *
 * @author jbellmann
 */
@ConfigurationProperties(prefix = "zauth")
public class ZAuthProperties {

    private static final String DEFAULT_CREDENTIALSDIRECTORY_PATH = "/meta/credentials";

    private String clientId;

    private String clientSecret;

    private String credentialsDirectoryPath = DEFAULT_CREDENTIALSDIRECTORY_PATH;

    private Map<String, String> customParams = new HashMap<>(0);

    private String authorizationEndpoint;

    private String tokenEndpoint;

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

    public Map<String, String> getCustomParams() {
        return customParams;
    }

    public void setCustomParams(Map<String, String> customParams) {
        this.customParams = customParams;
    }

    public String getAuthorizationEndpoint() {
        return authorizationEndpoint;
    }

    public void setAuthorizationEndpoint(String authorizationEndpoint) {
        this.authorizationEndpoint = authorizationEndpoint;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }
}
