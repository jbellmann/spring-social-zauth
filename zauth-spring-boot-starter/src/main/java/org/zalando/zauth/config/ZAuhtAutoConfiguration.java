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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.zauth.user.DefaultZAuthSocialUserDetailsService;

@Configuration
@EnableSocial
@EnableConfigurationProperties({ ZAuthProperties.class })
public class ZAuhtAutoConfiguration {

	@Autowired
	private UserDetailsManager userDetailsManager;

	@Autowired
	private ZAuthProperties zauthProperties;

	@Bean
	public DefaultZAuthSocialConfigurer zauthSocialConfigurer() {
		return new DefaultZAuthSocialConfigurer(userDetailsManager, zauthProperties);
	}

	@Configuration
	@ConditionalOnMissingBean({ SocialUserDetailsService.class })
	public static class SocialUserDetailsServiceConfiguration {

		@Autowired
		private UserDetailsManager userDetailsManager;

		@Bean
		public SocialUserDetailsService socialUserDetailsService() {
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
}
