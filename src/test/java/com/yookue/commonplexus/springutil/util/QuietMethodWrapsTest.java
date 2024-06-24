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


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import com.yookue.commonplexus.javaseutil.constant.AssertMessageConst;
import com.yookue.commonplexus.javaseutil.util.StackTraceWraps;
import lombok.extern.slf4j.Slf4j;


/**
 * Tests for {@link com.yookue.commonplexus.springutil.util.QuietMethodWraps}
 *
 * @author David Hsing
 */
@Slf4j
class QuietMethodWrapsTest {
    @Test
    void writeMethods() throws IOException {
        File output = new File("D:/GsonJsonWraps.java");
        QuietMethodWraps.BuildParam param = new QuietMethodWraps.BuildParam();
        param.setMethodFilter(method -> StringUtils.startsWithAny(method.getName(), "fromJson", "toJson"));
        boolean result = QuietMethodWraps.writeMethods(Gson.class, param, output, StandardCharsets.UTF_8);
        log.info("{} = {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertTrue(result, AssertMessageConst.IS_TRUE);
    }
}
