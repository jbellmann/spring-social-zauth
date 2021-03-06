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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author jbellmann
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsManager);
    }

    // J-
    // @formatter:off
    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
            .formLogin()
                .loginPage("/signin")
                .failureUrl("/signin?param.error=bad_credentials")
                .permitAll()
        .and()
            .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .permitAll()
        .and()
            .authorizeRequests()
                .antMatchers("/favicon.ico", "/static-resources/**", "/css/**", "/js/**")
                    .permitAll()
                .antMatchers("/**")
                    .authenticated()
        .and()
            .rememberMe()
        .and()
            .apply(new SpringSocialConfigurer())
        .and()
            .csrf().disable();
    }
    // @formatter:on
    // J+

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsManager;
    }
}
