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

public class IndexOfTest {

    @Test
    public void testIndexOf() {
        String host = "localhost:8443";
        host = host.substring(0, host.indexOf(":"));
        Assertions.assertThat(host).isEqualTo("localhost");
    }

}
