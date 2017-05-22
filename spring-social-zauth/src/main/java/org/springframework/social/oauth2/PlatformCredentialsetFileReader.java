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

import static java.nio.file.Files.readAllBytes;

import java.io.IOException;

import org.springframework.util.Assert;

public class PlatformCredentialsetFileReader extends FileCredentialsSupplierSupport
        implements ClientCredentialsSupplier {

    private static final String CLIENT_SECRET = "-client-secret";
    private static final String CLIENT_ID = "-client-id";
    private final String name;

    public PlatformCredentialsetFileReader(String credentialsDirectoryPath, String name) {
        super(credentialsDirectoryPath);
        Assert.hasText(name, "'name' should never be null or empty");
        this.name = name;
    }

    @Override
    public String getClientId() {
        try {
            return new String(readAllBytes(getFileInCredentialsDir(this.name + CLIENT_ID).toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getClientSecret() {
        try {
            return new String(readAllBytes(getFileInCredentialsDir(this.name + CLIENT_SECRET).toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
