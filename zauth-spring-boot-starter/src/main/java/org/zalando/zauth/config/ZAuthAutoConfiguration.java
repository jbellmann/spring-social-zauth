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

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.oauth2.ClientCredentialsSupplier;
import org.springframework.social.oauth2.JsonCredentialFileReader;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.zauth.signup.DefaultZAuthConnectionSignupService;
import org.springframework.social.zauth.user.DefaultZAuthSocialUserDetailsService;

@Configuration
public class ZAuthAutoConfiguration {
    private final Logger log = LoggerFactory.getLogger(ZAuthAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public UsersConnectionRepositoryConfigAdapter usersConnectionRepositoryConfigAdapter(
            ConnectionSignUp connectionSignUp) {

        return new UsersConnectionRepositoryConfigAdapter() {

            @Override
            public UsersConnectionRepository configure(ConnectionFactoryLocator connectionFactoryLocator) {

                // for the example 'InMemory' is ok, but could be also JDBC or
                // custom
                InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(
                        connectionFactoryLocator);

                repository.setConnectionSignUp(connectionSignUp);
                return repository;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionSignUp connectionSignUp(UserDetailsManager userDetailsManager) {
        return new DefaultZAuthConnectionSignupService(userDetailsManager);
    }

    @Configuration
    @ConditionalOnMissingBean({ SocialUserDetailsService.class })
    public static class SocialUserDetailsServiceConfiguration {

        @Bean
        public SocialUserDetailsService socialUserDetailsService(UserDetailsManager userDetailsManager) {
            return new DefaultZAuthSocialUserDetailsService(userDetailsManager);
        }
    }

    @Configuration
    @ConditionalOnMissingBean({ UserDetailsManager.class })
    public static class UserDetailsManagerConfiguration {

        @Bean
        public UserDetailsManager userDetailsManager() {
            return new InMemoryUserDetailsManager(new ArrayList<UserDetails>());
        }
    }

    @Bean
    @ConditionalOnMissingBean({ ClientCredentialsSupplier.class })
    public ClientCredentialsSupplier jsonClientCredentialsSupplier(ZAuthProperties zauthProperties) {
        log.info("CREATE JSON_CLIENT_CREDENTIALS ...");
        return new JsonCredentialFileReader(zauthProperties.getCredentialsDirectoryPath());
    }
}
