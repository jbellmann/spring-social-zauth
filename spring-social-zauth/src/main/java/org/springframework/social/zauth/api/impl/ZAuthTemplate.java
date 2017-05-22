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
package org.springframework.social.zauth.api.impl;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.zauth.api.ZAuth;

/**
 * @author jbellmann
 */
public class ZAuthTemplate extends AbstractOAuth2ApiBinding implements ZAuth {

    private final String accessToken;

    private String userId;

    // private String tokenEndpoint;

    // private RestTemplate restTemplate;

    public ZAuthTemplate() {
        initialize();
        accessToken = null;
    }

    public ZAuthTemplate(final String accessToken, String tokenEndpoint) {
        super(accessToken);
        this.accessToken = accessToken;
        // this.tokenEndpoint = tokenEndpoint;
        initialize();
    }

    private void initialize() {
        super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
        // ClientHttpRequestFactory requestFactory =
        // ClientHttpRequestFactorySelector.getRequestFactory();
        // restTemplate = new RestTemplate(requestFactory);
        initSubApis();
    }

    private UidExtractor extractor = new UidExtractor();

    private void initSubApis() {
        this.userId = extractor.extractUid(accessToken);
    }

    @Override
    public String getCurrentLogin() {
        return userId;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }
}
