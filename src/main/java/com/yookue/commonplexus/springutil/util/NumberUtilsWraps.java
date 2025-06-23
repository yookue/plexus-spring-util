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


import java.text.NumberFormat;
import java.util.Collection;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;


/**
 * Utilities for {@link org.springframework.util.NumberUtils}
 *
 * @author David Hsing
 *
 * @see org.springframework.util.NumberUtils
 * @see org.apache.commons.lang3.math.NumberUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class NumberUtilsWraps {
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <T extends Number> T convertNumber(@Nullable Number number, @Nullable Class<T> expectType) {
        if (ObjectUtils.anyNull(number, expectType)) {
            return null;
        }
        try {
            return org.springframework.util.NumberUtils.convertNumberToTargetClass(number, expectType);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T extends Number> boolean isNumberParsable(@Nullable String value, @Nullable Class<T> expectType) {
        return parseNumber(value, expectType, null) != null;
    }

    public static <T extends Number> boolean isNumberParsable(@Nullable String value, @Nullable Class<T> expectType, @Nullable NumberFormat format) {
        return parseNumber(value, expectType, format) != null;
    }

    @Nullable
    public static <T extends Number> T parseNumber(@Nullable String value, @Nullable Class<T> expectType) {
        return parseNumber(value, expectType, null);
    }

    @Nullable
    public static <T extends Number> T parseNumber(@Nullable String value, @Nullable Class<T> expectType, @Nullable NumberFormat format) {
        if (StringUtils.isBlank(value) || expectType == null) {
            return null;
        }
        try {
            return org.springframework.util.NumberUtils.parseNumber(value, expectType, format);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T extends Number & Comparable<? super T>> T maxParsableNumber(@Nullable Class<T> expectType, @Nullable String... values) {
        return maxParsableNumber(expectType, ArrayUtilsWraps.asList(values));
    }

    public static <T extends Number & Comparable<? super T>> T maxParsableNumber(@Nullable Class<T> expectType, @Nullable Collection<String> values) {
        return maxParsableNumber(expectType, null, values);
    }

    public static <T extends Number & Comparable<? super T>> T maxParsableNumber(@Nullable Class<T> expectType, @Nullable NumberFormat format, @Nullable String... values) {
        return maxParsableNumber(expectType, format, ArrayUtilsWraps.asList(values));
    }

    public static <T extends Number & Comparable<? super T>> T maxParsableNumber(@Nullable Class<T> expectType, @Nullable NumberFormat format, @Nullable Collection<String> values) {
        if (expectType == null || CollectionUtils.isEmpty(values)) {
            return null;
        }
        return values.stream().filter(NumberUtils::isParsable).distinct().map(element -> parseNumber(element, expectType, format)).max(ObjectUtils::compare).orElse(null);
    }

    public static <T extends Number & Comparable<? super T>> T minParsableNumber(@Nullable Class<T> expectType, @Nullable String... values) {
        return minParsableNumber(expectType, ArrayUtilsWraps.asList(values));
    }

    public static <T extends Number & Comparable<? super T>> T minParsableNumber(@Nullable Class<T> expectType, @Nullable Collection<String> values) {
        return minParsableNumber(expectType, null, values);
    }

    public static <T extends Number & Comparable<? super T>> T minParsableNumber(@Nullable Class<T> expectType, @Nullable NumberFormat format, @Nullable String... values) {
        return minParsableNumber(expectType, format, ArrayUtilsWraps.asList(values));
    }

    public static <T extends Number & Comparable<? super T>> T minParsableNumber(@Nullable Class<T> expectType, @Nullable NumberFormat format, @Nullable Collection<String> values) {
        if (expectType == null || CollectionUtils.isEmpty(values)) {
            return null;
        }
        return values.stream().filter(NumberUtils::isParsable).distinct().map(element -> parseNumber(element, expectType, format)).min(ObjectUtils::compare).orElse(null);
    }
}
