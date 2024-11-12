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

package com.yookue.commonplexus.springutil.cglib;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.DebuggingClassWriter;
import com.yookue.commonplexus.javaseutil.util.StackTraceWraps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * Tests for {@link com.yookue.commonplexus.springutil.cglib.EnhancedBeanCopier}
 *
 * @author David Hsing
 */
@Slf4j
class EnhancedBeanCopierTest {
    @Test
    void copyIgnoreNullSource() {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:/plexus-test");
        SimpleStruct source = new SimpleStruct(), target = new SimpleStruct();
        source.setCode(200);
        target.setCode(500);
        target.setName("yookue");
        EnhancedBeanCopier copier = EnhancedBeanCopier.create(SimpleStruct.class, SimpleStruct.class, true);
        copier.copy(source, target, (sourceValue, targetType, targetSetter, targetName, targetValue) -> sourceValue == null ? targetValue : sourceValue);
        log.info("{} = {}", StackTraceWraps.getExecutingMethodName(), target.getName());
        Assertions.assertEquals("yookue", target.getName());
    }

    @Data
    private static class SimpleStruct {
        private Integer code;
        private String name;
    }
}
