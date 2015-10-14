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

import java.net.URI;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.support.URIBuilder;

import org.springframework.util.MultiValueMap;

public class AbstractZAuthOperations {

    protected static final String USERS_API_URL_BASE = "https://users.auth.zalando.com";
    protected static final String TEAMS_API_URL_BASE = "https://teams.auth.zalando.com";

    private final boolean authorized;

    public AbstractZAuthOperations(final boolean authorized) {
        this.authorized = authorized;
    }

    protected void requireAuthorized() {
        if (!authorized) {
            throw new MissingAuthorizationException("iam");
        }
    }

    protected String buildUsersUri(final String path) {
        return USERS_API_URL_BASE + path;
    }

    protected URI buildUsersUri(final String path, final String name, final String value) {

        URI uri = URIBuilder.fromUri(USERS_API_URL_BASE + path).queryParam(name, value).build();
        return uri;
    }

    protected URI buildUsersUri(final String path, final MultiValueMap<String, String> queryParams) {

        URI uri = URIBuilder.fromUri(USERS_API_URL_BASE + path).queryParams(queryParams).build();
        return uri;
    }

    protected String buildTeamsUri(final String path) {
        return TEAMS_API_URL_BASE + path;
    }

    protected URI buildTeamsUri(final String path, final String name, final String value) {

        URI uri = URIBuilder.fromUri(TEAMS_API_URL_BASE + path).queryParam(name, value).build();
        return uri;
    }

    protected URI buildTeamsUri(final String path, final MultiValueMap<String, String> queryParams) {

        URI uri = URIBuilder.fromUri(TEAMS_API_URL_BASE + path).queryParams(queryParams).build();
        return uri;
    }
}
