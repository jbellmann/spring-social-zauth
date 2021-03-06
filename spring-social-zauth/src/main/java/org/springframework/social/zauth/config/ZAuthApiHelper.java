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
package org.springframework.social.zauth.config;

import org.springframework.social.UserIdSource;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.zauth.api.ZAuth;

/**
 * @author  jbellmann
 */
public class ZAuthApiHelper implements ApiHelper<ZAuth> {

    private final UsersConnectionRepository usersConnectionRepository;

    private final UserIdSource userIdSource;

    public ZAuthApiHelper(final UsersConnectionRepository usersConnectionRepository, final UserIdSource userIdSource) {
        this.usersConnectionRepository = usersConnectionRepository;
        this.userIdSource = userIdSource;
    }

    public ZAuth getApi() {
        Connection<ZAuth> connection = usersConnectionRepository.createConnectionRepository(userIdSource.getUserId())
                                                              .findPrimaryConnection(ZAuth.class);

        return connection != null ? connection.getApi() : null;
    }
}
