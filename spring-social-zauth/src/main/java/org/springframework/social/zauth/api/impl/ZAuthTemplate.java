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

import java.util.Map;
import java.util.Optional;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.zauth.api.ZAuth;
import org.springframework.web.client.RestTemplate;
import org.zalando.zauth.teams.TeamsOperations;
import org.zalando.zauth.users.UsersOperations;

/**
 * @author jbellmann
 */
public class ZAuthTemplate extends AbstractOAuth2ApiBinding implements ZAuth {

	private static final String TOKEN_INFO_ENDPOINT = "https://auth.zalando.com/oauth2/tokeninfo?access_token=";

	private UsersOperations userOperations;

	private TeamsOperations teamsOperations;

	private final String accessToken;

	private String userId;

	private static final RestTemplate restTemplate = new RestTemplate();

	public ZAuthTemplate() {
		initialize();
		accessToken = null;
	}

	public ZAuthTemplate(final String accessToken) {
		super(accessToken);
		this.accessToken = accessToken;
		initialize();
	}

	private void initialize() {
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis();
	}

	private void initSubApis() {
		Optional<String> uid = new Uid(getTokenInfo(accessToken)).getUid();
		if (!uid.isPresent()) {
			throw new IllegalStateException("Unable to get 'uid' for 'accessToken' " + accessToken);
		}

		this.userId = uid.get();

		userOperations = new UsersTemplate(getRestTemplate(), isAuthorized());

		teamsOperations = new TeamsTemplate(getRestTemplate(), isAuthorized());
	}

	protected Map<String, ?> getTokenInfo(final String accessToken) {
		return restTemplate.getForObject(TOKEN_INFO_ENDPOINT + accessToken, Map.class);
	}

	public UsersOperations userOperations() {
		return userOperations;
	}

	@Override
	public TeamsOperations teamsOperations() {
		return teamsOperations;
	}

	@Override
	public String getCurrentLogin() {
		return userId;
	}

}
