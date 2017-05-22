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

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UidExtractor {

    private final String uid_key = "https://identity.zalando.com/managed-id";

    @SuppressWarnings("unchecked")
    public String extractUid(String accessToken) {
        String middle = accessToken.substring(accessToken.indexOf(".") + 1, accessToken.lastIndexOf("."));
        Map<String, String> content = null;
        ObjectMapper om = new ObjectMapper();
        try {
            content = om.readValue(Base64.getDecoder().decode(middle), Map.class);
            return (String) content.get(uid_key);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
