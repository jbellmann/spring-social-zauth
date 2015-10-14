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
package org.zalando.zauth.teams;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author  jbellmann
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {

    @JsonProperty("infrastructure-accounts")
    private List<InfrastructureAccount> infrastructureAccounts = new ArrayList<InfrastructureAccount>();

    @JsonProperty("mail")
    private List<String> mailAddresses = new ArrayList<String>();

    private List<String> aliases = new ArrayList<String>();

    private List<String> members = new ArrayList<String>();

    private String description;

    private String name;

    public Team() {
        //
    }

    public List<InfrastructureAccount> getInfrastructureAccounts() {
        return infrastructureAccounts;
    }

    public void setInfrastructureAccounts(final List<InfrastructureAccount> infrastructureAccounts) {
        this.infrastructureAccounts = infrastructureAccounts;
    }

    public List<String> getMailAddresses() {
        return mailAddresses;
    }

    public void setMailAddresses(final List<String> mailAddresses) {
        this.mailAddresses = mailAddresses;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(final List<String> aliases) {
        this.aliases = aliases;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(final List<String> members) {
        this.members = members;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
