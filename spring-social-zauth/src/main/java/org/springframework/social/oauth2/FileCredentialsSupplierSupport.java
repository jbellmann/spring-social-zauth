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

import java.io.File;

import org.springframework.util.Assert;

public class FileCredentialsSupplierSupport {

    protected final String credentialsDirectoryPath;


    public FileCredentialsSupplierSupport(String credentialsDirectoryPath) {
        Assert.hasText(credentialsDirectoryPath, "'credentialsDirectoryPath' should never be null or empty");
        this.credentialsDirectoryPath = credentialsDirectoryPath;
        File credentialsDirectory = new File(this.credentialsDirectoryPath);
        Assert.isTrue(credentialsDirectory.exists(), "'credentialsDirectoryPath' does not exist");
        Assert.isTrue(credentialsDirectory.isDirectory(),
                "'credentialsDirectoryPath' should be a directory, but it's not");
    }

    protected File getFileInCredentialsDir(String filename) {
        return new File(credentialsDirectoryPath, filename);
    }

}
