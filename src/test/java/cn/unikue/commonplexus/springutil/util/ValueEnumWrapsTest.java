/*
 * Copyright (c) 2016 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import cn.unikue.commonplexus.javaseutil.util.StackTraceWraps;
import cn.unikue.commonplexus.javaseutil.util.ValueEnumWraps;
import cn.unikue.commonplexus.springutil.enumeration.CacheManagerType;
import lombok.extern.slf4j.Slf4j;


/**
 * Tests for {@link cn.unikue.commonplexus.javaseutil.util.ValueEnumWraps}
 *
 * @author David Hsing
 */
@Slf4j
class ValueEnumWrapsTest {
    @Test
    void getNameOfValue() {
        CacheManagerType result = ValueEnumWraps.ofValue(CacheManagerType.class, CacheManagerType.REDIS.getValue());
        Assertions.assertNotNull(result);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result.name());
    }
}
