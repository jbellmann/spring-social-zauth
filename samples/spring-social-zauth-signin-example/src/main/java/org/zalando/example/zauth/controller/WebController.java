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
package org.zalando.example.zauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.zauth.api.ZAuth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Simple controller that uses {@link ZAuth}-connection to provide some information<br/>
 * from ZAuth-Services (Teams, Users).
 * 
 * @author jbellmann
 *
 */
@Controller
public class WebController {

    private final ZAuth zAuth;

    @Autowired
    public WebController(final ZAuth zAuth) {
        this.zAuth = zAuth;
    }

    @RequestMapping(value = "/signin")
    public String signin() {
        return "signin";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/", "/index"})
    public String index(final Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getPrincipal();

        String currentLogin = zAuth.getCurrentLogin();
        model.addAttribute("currentLogin", currentLogin);

//        User user = zAuth.userOperations().getUserById(currentLogin);
//        model.addAttribute("user", user);
        return "index";
    }

}
