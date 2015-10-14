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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.zauth.api.ZAuth;
import org.zalando.example.zauth.controller.WebController;

/**
 */
@Configuration
public class ApplicationConfig {

	/**
	 * ZAuth api, can be injected into controller if needed.
	 * 
	 * @param repository
	 * @return ZAuth
	 * @see WebController
	 */
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public ZAuth zAuth(final ConnectionRepository repository) {
        Connection<ZAuth> connection = repository.findPrimaryConnection(ZAuth.class);
        return connection != null ? connection.getApi() : null;
    }
}
