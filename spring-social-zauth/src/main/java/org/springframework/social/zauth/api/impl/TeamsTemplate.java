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

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;
import org.zalando.zauth.teams.Account;
import org.zalando.zauth.teams.Network;
import org.zalando.zauth.teams.Team;
import org.zalando.zauth.teams.TeamsOperations;

/**
 * @author  jbellmann
 */
public class TeamsTemplate extends AbstractZAuthOperations implements TeamsOperations {

    private final RestTemplate restTemplate;

    public TeamsTemplate(final RestTemplate restTemplate, final boolean authorized) {
        super(authorized);
        this.restTemplate = restTemplate;
    }

    @Override
    public Team getTeamById(final String teamId) {

        Team team = new Team();
        team.setName(teamId);

        return team;
    }

    @Override
    public List<Account> getAccountsByType(final String accountType) {

        return new ArrayList<Account>();
    }

    @Override
    public List<Network> getNetworks(final String accountType, final String teamId) {

        return new ArrayList<Network>();
    }

}
