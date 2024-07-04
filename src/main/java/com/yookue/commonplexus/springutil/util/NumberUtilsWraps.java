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
 * @see org.springframework.util.NumberUtils
 * @see org.apache.commons.lang3.math.NumberUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class NumberUtilsWraps {
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T extends Number> T convertNumber(@Nullable Number number, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(number, expectedType)) {
            return null;
        }
        try {
            return org.springframework.util.NumberUtils.convertNumberToTargetClass(number, expectedType);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T extends Number> boolean isNumberParsable(@Nullable String value, @Nullable Class<T> expectedType) {
        return parseNumber(value, expectedType, null) != null;
    }

    public static <T extends Number> boolean isNumberParsable(@Nullable String value, @Nullable Class<T> expectedType, @Nullable NumberFormat format) {
        return parseNumber(value, expectedType, format) != null;
    }

    @Nullable
    public static <T extends Number> T parseNumber(@Nullable String value, @Nullable Class<T> expectedType) {
        return parseNumber(value, expectedType, null);
    }

    @Nullable
    public static <T extends Number> T parseNumber(@Nullable String value, @Nullable Class<T> expectedType, @Nullable NumberFormat format) {
        if (StringUtils.isBlank(value) || expectedType == null) {
            return null;
        }
        try {
            return org.springframework.util.NumberUtils.parseNumber(value, expectedType, format);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T extends Number & Comparable<? super T>> T maxParsableNumber(@Nullable Class<T> expectedType, @Nullable String... values) {
        return maxParsableNumber(expectedType, ArrayUtilsWraps.asList(values));
    }

    public static <T extends Number & Comparable<? super T>> T maxParsableNumber(@Nullable Class<T> expectedType, @Nullable Collection<String> values) {
        return maxParsableNumber(expectedType, null, values);
    }

    public static <T extends Number & Comparable<? super T>> T maxParsableNumber(@Nullable Class<T> expectedType, @Nullable NumberFormat format, @Nullable String... values) {
        return maxParsableNumber(expectedType, format, ArrayUtilsWraps.asList(values));
    }

    public static <T extends Number & Comparable<? super T>> T maxParsableNumber(@Nullable Class<T> expectedType, @Nullable NumberFormat format, @Nullable Collection<String> values) {
        if (expectedType == null || CollectionUtils.isEmpty(values)) {
            return null;
        }
        return values.stream().filter(NumberUtils::isParsable).distinct().map(element -> parseNumber(element, expectedType, format)).max(ObjectUtils::compare).orElse(null);
    }

    public static <T extends Number & Comparable<? super T>> T minParsableNumber(@Nullable Class<T> expectedType, @Nullable String... values) {
        return minParsableNumber(expectedType, ArrayUtilsWraps.asList(values));
    }

    public static <T extends Number & Comparable<? super T>> T minParsableNumber(@Nullable Class<T> expectedType, @Nullable Collection<String> values) {
        return minParsableNumber(expectedType, null, values);
    }

    public static <T extends Number & Comparable<? super T>> T minParsableNumber(@Nullable Class<T> expectedType, @Nullable NumberFormat format, @Nullable String... values) {
        return minParsableNumber(expectedType, format, ArrayUtilsWraps.asList(values));
    }

    public static <T extends Number & Comparable<? super T>> T minParsableNumber(@Nullable Class<T> expectedType, @Nullable NumberFormat format, @Nullable Collection<String> values) {
        if (expectedType == null || CollectionUtils.isEmpty(values)) {
            return null;
        }
        return values.stream().filter(NumberUtils::isParsable).distinct().map(element -> parseNumber(element, expectedType, format)).min(ObjectUtils::compare).orElse(null);
    }
}
