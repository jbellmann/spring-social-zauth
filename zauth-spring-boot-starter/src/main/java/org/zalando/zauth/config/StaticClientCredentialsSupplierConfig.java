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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.oauth2.ClientCredentialsSupplier;
import org.springframework.social.oauth2.StaticClientCredentialsSupplier;

@Configuration
@ConditionalOnStaticClientId
@AutoConfigureBefore({ PlatformClientCredentialsSupplierConfig.class, ZAuthAutoConfiguration.class })
public class StaticClientCredentialsSupplierConfig {
    private final Logger log = LoggerFactory.getLogger(StaticClientCredentialsSupplierConfig.class);

    @Bean
    public ClientCredentialsSupplier staticClientCredentialsSupplier(ZAuthProperties zauthProperties) {
        log.info("CREATE STATIC_CLIENT_CREDENTIALS ...");
        return new StaticClientCredentialsSupplier(zauthProperties.getClientId(), zauthProperties.getClientSecret());
    }

}
