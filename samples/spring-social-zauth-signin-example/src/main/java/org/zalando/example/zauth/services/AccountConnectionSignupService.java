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
package org.zalando.example.zauth.services;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.zauth.api.ZAuth;

/**
 * @author  jbellmann
 */
public class AccountConnectionSignupService implements ConnectionSignUp {

    private final Logger LOG = LoggerFactory.getLogger(AccountConnectionSignupService.class);

    private final UserDetailsManager userDetailsManager;

    public AccountConnectionSignupService(final UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public String execute(final Connection<?> connection) {

        Object api = connection.getApi();

        // use the api if you can
        if (api instanceof ZAuth) {
            ZAuth zAuth = (ZAuth) api;
            String login = zAuth.getCurrentLogin();
        }

        // or use more generic
        org.springframework.social.connect.UserProfile profile = connection.fetchUserProfile();

        String username = profile.getUsername();

        LOG.info("Created user with id: " + username);

        User user = new User(username, "", Collections.emptyList());
        userDetailsManager.createUser(user);

        return username;
    }
}
