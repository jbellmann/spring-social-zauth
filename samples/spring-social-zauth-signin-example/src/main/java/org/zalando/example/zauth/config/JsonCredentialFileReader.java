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

import java.io.IOException;

import org.springframework.social.oauth2.ClientCredentialsSupplier;
import org.springframework.social.oauth2.FileCredentialsSupplierSupport;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author jbellmann
 *
 */
public class JsonCredentialFileReader extends FileCredentialsSupplierSupport implements ClientCredentialsSupplier {

    private final ObjectMapper mapper = new ObjectMapper();

    public JsonCredentialFileReader(String credentialsDirectoryPath) {
        super(credentialsDirectoryPath);
    }

    protected ClientCredentials getClientCredentials() throws IOException {
        return readAsJson("client.json", ClientCredentials.class);
    }

    protected <T> T readAsJson(String filename, Class<T> type) throws IOException {
        return mapper.readValue(getFileInCredentialsDir(filename), type);
    }

    @Override
    public String getClientId() {
        try {
            return getClientCredentials().getClientId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getClientSecret() {
        try {
            return getClientCredentials().getClientSecret();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
