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
package org.zalando.zauth.config;

import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.oauth2.ClientCredentialsSupplier;
import org.springframework.social.zauth.config.AbstractZAuthSocialConfigurer;
import org.springframework.social.zauth.signup.DefaultZAuthConnectionSignupService;
import org.springframework.util.Assert;

/**
 * @author jbellmann
 */
public class DefaultZAuthSocialConfigurer extends AbstractZAuthSocialConfigurer {

    private ZAuthProperties zauthProperties;

    private UserDetailsManager userDetailsManager;

    public DefaultZAuthSocialConfigurer(UserDetailsManager userDetailsManager, ZAuthProperties zauthProperties) {
        Assert.notNull(userDetailsManager, "'userDetailsManager' should never be null");
        Assert.notNull(zauthProperties, "'zauthProperties' should never be null");
        this.userDetailsManager = userDetailsManager;
        this.zauthProperties = zauthProperties;
    }

    @Override
    protected UsersConnectionRepository doGetUsersConnectionRepository(
            final ConnectionFactoryLocator connectionFactoryLocator) {

        // for the example 'InMemory' is ok, but could be also JDBC or custom
        InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        repository.setConnectionSignUp(new DefaultZAuthConnectionSignupService(userDetailsManager));
        return repository;
    }

    @Override
    protected ClientCredentialsSupplier getClientCredentialsSupplier() {
        return new CredentialFileReader(zauthProperties.getCredentialsDirectoryPath());
    }

    @Override
    protected String getAuthorizationEndpoint() {
        return zauthProperties.getAuthorizationEndpoint();
    }

    @Override
    protected String getTokenEndpoint() {
        return zauthProperties.getTokenEndpoint();
    }

}
