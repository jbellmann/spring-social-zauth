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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.env.Environment;

import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationServiceRegistry;
import org.springframework.social.zauth.security.ZAuthAuthenticationService;

/**
 * @author  jbellmann
 */
public abstract class AbstractZAuthSocialConfigurer implements SocialConfigurer {

    private final Logger log = LoggerFactory.getLogger(AbstractZAuthSocialConfigurer.class);

    @Override
    public void addConnectionFactories(final ConnectionFactoryConfigurer connectionFactoryConfigurer,
            final Environment environment) {
        // we do not add the 'connectionFactory' here
        // because of registering it in #getUsersConnectionRepository below
    }

    @Override
    public UserIdSource getUserIdSource() {

        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(
            final ConnectionFactoryLocator connectionFactoryLocator) {

        // this is hacky, but didn't found out how to do these configuration without it
        if (connectionFactoryLocator instanceof SocialAuthenticationServiceRegistry) {
            log.debug("Initialize ConnectionFactory with key {} and secret {}",
                getClientId().substring(0, getClientIdSubstringLenght()),
                getClientSecret().substring(0, getClientSecretIdSubstringCount()));

            SocialAuthenticationServiceRegistry registry = (SocialAuthenticationServiceRegistry)
                connectionFactoryLocator;
            registry.addAuthenticationService(new ZAuthAuthenticationService(getClientId(), getClientSecret()));
        }

        return doGetUsersConnectionRepository(connectionFactoryLocator);
    }

    protected abstract UsersConnectionRepository doGetUsersConnectionRepository(
            ConnectionFactoryLocator connectionFactoryLocator);

    protected abstract String getClientId();

    protected abstract String getClientSecret();

    protected int getClientIdSubstringLenght() {
        return 8;
    }

    protected int getClientSecretIdSubstringCount() {
        return 4;
    }

}
