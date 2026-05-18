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


import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import cn.unikue.commonplexus.javaseutil.util.StackTraceWraps;
import lombok.extern.slf4j.Slf4j;


/**
 * Tests for {@link cn.unikue.commonplexus.springutil.util.TemporalUnitWraps}
 *
 * @author David Hsing
 */
@Slf4j
class TemporalUnitWrapsTest {
    @Test
    void toHumanReadable_ZeroSeconds() {
        String result = TemporalUnitWraps.toHumanReadable(0, Locale.SIMPLIFIED_CHINESE);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("永久", result);
    }

    @Test
    void toHumanReadable_NegativeSeconds() {
        String result = TemporalUnitWraps.toHumanReadable(-100, Locale.SIMPLIFIED_CHINESE);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("永久", result);
    }

    @Test
    void toHumanReadable_OnlySeconds() {
        String result = TemporalUnitWraps.toHumanReadable(45, Locale.SIMPLIFIED_CHINESE);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("45 秒", result);
    }

    @Test
    void toHumanReadable_OnlyMinutes() {
        String result = TemporalUnitWraps.toHumanReadable(125, Locale.SIMPLIFIED_CHINESE);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2 分 5 秒", result);
    }

    @Test
    void toHumanReadable_OnlyHours() {
        String result = TemporalUnitWraps.toHumanReadable(7325, Locale.SIMPLIFIED_CHINESE);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2 时 2 分 5 秒", result);
    }

    @Test
    void toHumanReadable_OnlyDays() {
        String result = TemporalUnitWraps.toHumanReadable(93725, Locale.SIMPLIFIED_CHINESE);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("1 日 2 时 2 分 5 秒", result);
    }

    @Test
    void toHumanReadable_OnlyMonths() {
        long seconds = 2592000L + 93725L;    // 1 month + 1 day 2 hours 2 minutes 5 seconds
        String result = TemporalUnitWraps.toHumanReadable(seconds, Locale.SIMPLIFIED_CHINESE);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("1 月 1 日 2 时 2 分 5 秒", result);
    }

    @Test
    void toHumanReadable_OnlyYears() {
        long seconds = 31536000L + 2592000L + 93725L;    // 1 year + 1 month + 1 day 2 hours 2 minutes 5 seconds
        String result = TemporalUnitWraps.toHumanReadable(seconds, Locale.SIMPLIFIED_CHINESE);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("1 年 1 月 1 日 2 时 2 分 5 秒", result);
    }

    @Test
    void toHumanReadable_MultipleYears() {
        long seconds = 31536000L * 5;    // 5 years
        String result = TemporalUnitWraps.toHumanReadable(seconds, Locale.SIMPLIFIED_CHINESE);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("5 年", result);
    }

    @Test
    void toHumanReadable_EnglishLocale() {
        String result = TemporalUnitWraps.toHumanReadable(31536000L + 2592000L + 93725L, Locale.US);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("1 Year 1 Month 1 Day 2 Hours 2 Minutes 5 Seconds", result);
    }

    @Test
    void toHumanReadable_EnglishPlural() {
        long seconds = 31536000L * 2 + 2592000L * 3 + 86400L * 4 + 3600L * 5 + 60L * 6 + 7;
        String result = TemporalUnitWraps.toHumanReadable(seconds, Locale.US);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("2 Years 3 Months 4 Days 5 Hours 6 Minutes 7 Seconds", result);
    }

    @Test
    void toHumanReadable_TraditionalChineseLocale() {
        String result = TemporalUnitWraps.toHumanReadable(31536000L + 2592000L + 93725L, Locale.TRADITIONAL_CHINESE);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("1 年 1 月 1 日 2 時 2 分 5 秒", result);
    }

    @Test
    void toHumanReadable_SingleUnit_English() {
        String result = TemporalUnitWraps.toHumanReadable(3600, Locale.US);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("1 Hour", result);
    }

    @Test
    void toHumanReadable_SingleUnit_Chinese() {
        String result = TemporalUnitWraps.toHumanReadable(3600, Locale.SIMPLIFIED_CHINESE);
        log.info("{}: {}", StackTraceWraps.getExecutingMethodName(), result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("1 时", result);
    }
}
