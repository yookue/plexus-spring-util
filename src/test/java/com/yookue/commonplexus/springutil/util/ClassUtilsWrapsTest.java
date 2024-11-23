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


/**
 * Tests for {@link com.yookue.commonplexus.springutil.util.ClassUtilsWraps}
 *
 * @author David Hsing
 */
class ClassUtilsWrapsTest {
    @Test
    void isAssignable() {
        Assertions.assertTrue(ClassUtilsWraps.isAssignable(Number.class, Integer.class));
        Assertions.assertTrue(ClassUtilsWraps.isAssignable("java.lang.Number", "java.lang.Integer"));    // $NON-NLS-1$ // $NON-NLS-2$
        Assertions.assertFalse(ClassUtilsWraps.isAssignable(Integer.class, Number.class));
        Assertions.assertFalse(ClassUtilsWraps.isAssignable("java.lang.Integer", "java.lang.Number"));    // $NON-NLS-1$ // $NON-NLS-2$
    }
}
