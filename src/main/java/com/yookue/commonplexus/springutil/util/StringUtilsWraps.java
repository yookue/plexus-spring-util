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


import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.constant.RegexVariantConst;
import com.yookue.commonplexus.javaseutil.constant.StringVariantConst;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.CharSequenceWraps;
import com.yookue.commonplexus.javaseutil.util.EnumerationPlainWraps;
import com.yookue.commonplexus.javaseutil.util.StreamPlainWraps;


/**
 * Utilities for {@link org.springframework.util.StringUtils}
 *
 * @author David Hsing
 * @see org.springframework.util.StringUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class StringUtilsWraps {
    public static String formatCurlyBrackets(@Nullable String template, @Nullable Object... arguments) {
        return formatCurlyBrackets(template, ArrayUtilsWraps.asList(arguments));
    }

    /**
     * Return formatted string template as {@link org.slf4j.Logger} does
     *
     * @param template string template to be formatted with curly brackets
     * @param arguments anything to be converted to string
     *
     * @return formatted string template as {@link org.slf4j.Logger} does
     */
    public static String formatCurlyBrackets(@Nullable String template, @Nullable Collection<Object> arguments) {
        if (!StringUtils.hasText(template) || CollectionUtils.isEmpty(arguments)) {
            return template;
        }
        String result = template;
        for (Object argument : arguments) {
            result = RegExUtils.replaceFirst(result, RegexVariantConst.CURLY_BRACKETS, ObjectUtils.getDisplayString(argument));
        }
        return result;
    }

    @Nullable
    public static <T> String joinWith(@Nullable T[] objects, char delimiter) {
        return joinWith(objects, CharUtils.toString(delimiter));
    }

    @Nullable
    public static <T> String joinWith(@Nullable T[] objects, @Nullable CharSequence delimiter) {
        return ArrayUtils.isEmpty(objects) ? null : StringUtils.arrayToDelimitedString(objects, CharSequenceWraps.toStringIgnoreNull(delimiter, StringVariantConst.EMPTY));
    }

    @Nullable
    public static String joinWith(@Nullable Collection<?> collection, char delimiter) {
        return joinWith(collection, CharUtils.toString(delimiter));
    }

    @Nullable
    public static String joinWith(@Nullable Collection<?> collection, @Nullable CharSequence delimiter) {
        return CollectionUtils.isEmpty(collection) ? null : StringUtils.collectionToDelimitedString(collection, CharSequenceWraps.toStringIgnoreNull(delimiter, StringVariantConst.EMPTY));
    }

    public static String quoteDisplayString(@Nullable Object object) {
        return (object == null) ? null : StringUtils.quote(ObjectUtilsWraps.getDisplayString(object));
    }

    @Nullable
    public static String quoteDisplayString(@Nullable Object object, char delimiter) {
        return quoteDisplayString(object, CharUtils.toString(delimiter));
    }

    @Nullable
    public static String quoteDisplayString(@Nullable Object object, @Nullable CharSequence delimiter) {
        String display = ObjectUtilsWraps.getDisplayString(object);
        return (display == null) ? null : org.apache.commons.lang3.StringUtils.join(delimiter, display, delimiter);
    }

    @Nullable
    public static Properties splitKeyValuesIntoProperties(@Nullable String text, char groupDelimiter, char keyValueDelimiter) {
        return splitKeyValuesIntoProperties(text, CharUtils.toString(groupDelimiter), CharUtils.toString(keyValueDelimiter));
    }

    @Nullable
    public static Properties splitKeyValuesIntoProperties(@Nullable String text, char groupDelimiter, char keyValueDelimiter, char charsToDelete) {
        return splitKeyValuesIntoProperties(text, CharUtils.toString(groupDelimiter), CharUtils.toString(keyValueDelimiter), CharUtils.toString(charsToDelete));
    }

    /**
     * Return a {@code Properties} instance that split by the given group delimiter and key value delimiter
     * <pre><code>
     *     StringUtilsWraps.splitKeyValuesIntoProperties("firstKey=firstValue, secondKey=secondValue", ",", "=")
     * </code></pre>
     *
     * @param text the string to split (potentially {@code null} or empty)
     * @param groupDelimiter the separate string for groups, {@code null} means use whitespace
     * @param keyValueDelimiter the separate string for key values, {@code null} or empty means use "="
     *
     * @return a {@code Properties} instance that split by the given group delimiter and key value delimiter
     *
     * @see org.springframework.util.StringUtils#splitArrayElementsIntoProperties
     */
    @Nullable
    public static Properties splitKeyValuesIntoProperties(@Nullable String text, @Nullable CharSequence groupDelimiter, @Nullable CharSequence keyValueDelimiter) {
        return splitKeyValuesIntoProperties(text, groupDelimiter, keyValueDelimiter, null);
    }

    /**
     * Return a {@code Properties} instance that split by the given group delimiter and key value delimiter
     * <pre><code>
     *     StringUtilsWraps.splitKeyValuesIntoProperties("firstKey=firstValue, secondKey=secondValue", ",", "=", null)
     * </code></pre>
     *
     * @param text the string to split (potentially {@code null} or empty)
     * @param groupDelimiter the separate string for groups, {@code null} means use whitespace
     * @param keyValueDelimiter the separate string for key values, {@code null} or empty means use "="
     * @param charsToDelete one or more characters to remove from each element
     *
     * @return a {@code Properties} instance that split by the given group delimiter and key value delimiter
     *
     * @see org.springframework.util.StringUtils#splitArrayElementsIntoProperties
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static Properties splitKeyValuesIntoProperties(@Nullable String text, @Nullable CharSequence groupDelimiter, @Nullable CharSequence keyValueDelimiter, @Nullable CharSequence charsToDelete) {
        if (!StringUtils.hasText(text) || !StringUtils.hasLength(groupDelimiter)) {
            return null;
        }
        String[] groups = StringUtils.split(text, groupDelimiter.toString());
        if (ArrayUtils.isEmpty(groups)) {
            return null;
        }
        if (!StringUtils.hasText(keyValueDelimiter)) {
            keyValueDelimiter = CharUtils.toString(CharVariantConst.EQUALS);
        }
        return StringUtils.splitArrayElementsIntoProperties(groups, keyValueDelimiter.toString(), CharSequenceWraps.toStringIgnoreNull(charsToDelete));
    }

    /**
     * @see org.springframework.util.StringUtils#toStringArray(Collection)
     */
    @Nullable
    public static String[] toStringArray(@Nullable Collection<?> collection) {
        return CollectionUtils.isEmpty(collection) ? null : collection.stream().map(ObjectUtils::getDisplayString).toArray(String[]::new);
    }

    /**
     * @see org.springframework.util.StringUtils#toStringArray(Enumeration)
     */
    @Nullable
    public static String[] toStringArray(@Nullable Enumeration<?> enumeration) {
        return EnumerationPlainWraps.isEmpty(enumeration) ? null : StreamPlainWraps.stream(enumeration).map(ObjectUtils::getDisplayString).toArray(String[]::new);
    }
}
