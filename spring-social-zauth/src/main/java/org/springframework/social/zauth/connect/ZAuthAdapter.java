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
package org.springframework.social.zauth.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.zauth.api.ZAuth;

/**
 * @author jbellmann
 */
public class ZAuthAdapter implements ApiAdapter<ZAuth> {

    public boolean test(final ZAuth api) {

        try {
            api.getCurrentLogin();
            return true;
        } catch (ApiException e) {
            return false;
        }
    }

    public void setConnectionValues(final ZAuth api, final ConnectionValues values) {

        // User user = api.userOperations().getUserById(api.getCurrentLogin());

        values.setProviderUserId(api.getCurrentLogin());
         values.setDisplayName(api.getCurrentLogin());
        // values.setImageUrl("https://apis.live.net/v5.0/" + profile.getId() +
        // "/picture");

    }

    public UserProfile fetchUserProfile(final ZAuth api) {

        return new UserProfileBuilder().setUsername(api.getCurrentLogin()).build();
    }

    public void updateStatus(final ZAuth api, final String message) {
        // not yet implemented
    }

}
