/*
 * Copyright (c) 2016 Yookue Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yookue.commonplexus.springutil.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import com.yookue.commonplexus.javaseutil.util.StackTraceWraps;
import com.yookue.commonplexus.springutil.MockApplicationInitializer;
import lombok.extern.slf4j.Slf4j;


/**
 * Tests for {@link com.yookue.commonplexus.springutil.util.PropertyBinderWraps}
 *
 * @author David Hsing
 */
@SpringBootTest(classes = MockApplicationInitializer.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
class PropertyBinderWrapsTest {
    @Autowired
    private Environment environment;

    @Test
    void contains() {
        boolean result = PropertyBinderWraps.contains(environment, "spring.cache");
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertTrue(result);
    }

    @Test
    void notContains() {
        boolean result = PropertyBinderWraps.contains(environment, "spring.caching");
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertFalse(result);
    }

    @Test
    void equals() {
        boolean result = PropertyBinderWraps.equals("spring.test-case", "spring.testCase");
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertTrue(result);
    }

    @Test
    void notEquals() {
        boolean result = PropertyBinderWraps.equals("spring.test-case", "spring.testingCase");
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertFalse(result);
    }
}
