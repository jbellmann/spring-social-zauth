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
package org.zalando.example.zauth;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class UidExtractorTest {

    private String jwt = "eyJraWQiOiJwbGF0Zm9ybS1pYW0tc2FuZGJveCIsImFsZyI6IkVTMjU2In0.eyJzdWIiOiJmOGQ2Y2E2My01YWM1LTQ5OGQtYWE2YS0wOTc0MTMwZGU3ZjUiLCJodHRwczovL2lkZW50aXR5LnphbGFuZG8uY29tL3JlYWxtIjoidXNlcnMiLCJodHRwczovL2lkZW50aXR5LnphbGFuZG8uY29tL3Rva2VuIjoiQmVhcmVyIiwiaHR0cHM6Ly9pZGVudGl0eS56YWxhbmRvLmNvbS9tYW5hZ2VkLWlkIjoiamJlbGxtYW5uIiwiYXpwIjoiY3JlZHByb3YtdG9rZW5zdGVzdC1jcmVkZW50aWFscy1lbXBsb3llZSIsImh0dHBzOi8vaWRlbnRpdHkuemFsYW5kby5jb20vYnAiOiI4MTBkMWQwMC00MzEyLTQzZTUtYmQzMS1kODM3M2ZkZDI0YzciLCJhdXRoX3RpbWUiOjE0OTU0NDY2MzUsImlzcyI6Imh0dHBzOi8vc2FuZGJveC5pZGVudGl0eS56YWxhbmRvLmNvbSIsImV4cCI6MTQ5NTQ1MDIzNSwiaWF0IjoxNDk1NDQ2NjI1fQ.nxqHv5BHO8FNxJhMRrG2jJsNFEaViilaOJ68sBGiPWziZRyyJFE9WSin5co2SJAMXdSSpgqEQTKN7uSic6_6dw";

    @Test
    public void testUidExtractor() {
        UidExtractor ex = new UidExtractor();
        String uid = ex.extractUid(jwt);
        Assertions.assertThat(uid).isEqualTo("jbellmann");
    }

}
