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
package org.zalando.example.zauth.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.oauth2.ClientCredentialsSupplier;
import org.springframework.social.zauth.config.AbstractZAuthSocialConfigurer;
import org.zalando.example.zauth.services.AccountConnectionSignupService;

/**
 * @author jbellmann
 */
@Configuration
@EnableSocial
@EnableConfigurationProperties({ ZAuthProperties.class })
public class SocialConfig extends AbstractZAuthSocialConfigurer {

    @Autowired
    private ZAuthProperties zauthProperties;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Override
    protected UsersConnectionRepository doGetUsersConnectionRepository(
            final ConnectionFactoryLocator connectionFactoryLocator) {

        // for the example 'InMemory' is ok, but could be also JDBC or custom
        InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        repository.setConnectionSignUp(new AccountConnectionSignupService(userDetailsManager));
        return repository;
    }

    @Override
    protected ClientCredentialsSupplier getClientCredentialsSupplier() {
        return new JsonCredentialFileReader(zauthProperties.getCredentialsDirectoryPath());
    }

    @Override
    protected Map<String, String> getCustomParameters() {
        return zauthProperties.getCustomParams();
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
