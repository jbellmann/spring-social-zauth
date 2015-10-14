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

import org.springframework.web.client.RestTemplate;
import org.zalando.zauth.users.User;
import org.zalando.zauth.users.UsersOperations;

/**
 * @author  jbellmann
 */
class UsersTemplate extends AbstractZAuthOperations implements UsersOperations {

    private final RestTemplate restTemplate;

    UsersTemplate(final RestTemplate restTemplate, final boolean authorized) {
        super(authorized);
        this.restTemplate = restTemplate;
    }

    @Override
    public User getUserById(final String id) {
        User user = restTemplate.getForObject(buildUsersUri("/employees/" + id), User.class);
        return user;
    }
//
// public IamProfile getUserProfile() {
//
//// @SuppressWarnings("unchecked")
//// Map<String, ?> user = restTemplate.getForObject(buildUri("/employees/me"), Map.class);
//// String id = String.valueOf(user.get("id"));
//// String name = String.valueOf(user.get("name"));
//// String firstName = String.valueOf(user.get("first_name"));
//// String lastName = String.valueOf(user.get("last_name"));
//// String gender = String.valueOf(user.get("gender"));
//// String locale = String.valueOf(user.get("locale"));
//
// return new IamProfile("klaus");
// }
//
// public IamProfile getUserProfileWithUsername(final String username) {
// return restTemplate.getForObject(AbstractIamOperations.USERS_API_URL_BASE + "/employees/" + username,
// IamProfile.class);
// }

}
