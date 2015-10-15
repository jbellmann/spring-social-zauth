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
package org.springframework.social.zauth.api.impl;

import java.util.Map;
import java.util.Optional;

/**
 * @author  jbellmann
 */
public final class Uid {

    private final Map<String, ?> response;

    public Uid(final Map<String, ?> response) {
        this.response = response;
    }

    public Optional<String> getUid() {
        return Optional.ofNullable((String) response.get("uid"));
    }
}