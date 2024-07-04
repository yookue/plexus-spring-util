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
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Nullable;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.constant.SymbolVariantConst;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;


/**
 * Utilities for {@link java.util.Map}
 *
 * @author David Hsing
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class MapPlainWraps {
    @Nullable
    @SafeVarargs
    public static <K, V> String firstNonBlankDisplayString(@Nullable Map<? super K, V> map, @Nullable K... keys) {
        return firstNonBlankDisplayString(map, ArrayUtilsWraps.asList(keys));
    }

    @Nullable
    public static <K, V> String firstNonBlankDisplayString(@Nullable Map<? super K, V> map, @Nullable Collection<K> keys) {
        if (CollectionUtils.isEmpty(map) || CollectionUtils.isEmpty(keys)) {
            return null;
        }
        return keys.stream().map(key -> ObjectUtils.getDisplayString(map.get(key))).filter(StringUtils::isNotBlank).findFirst().orElse(null);
    }

    @Nullable
    @SafeVarargs
    public static <K, V> String firstNonEmptyDisplayString(@Nullable Map<? super K, V> map, @Nullable K... keys) {
        return firstNonEmptyDisplayString(map, ArrayUtilsWraps.asList(keys));
    }

    @Nullable
    public static <K, V> String firstNonEmptyDisplayString(@Nullable Map<? super K, V> map, @Nullable Collection<K> keys) {
        if (CollectionUtils.isEmpty(map) || CollectionUtils.isEmpty(keys)) {
            return null;
        }
        return keys.stream().map(key -> ObjectUtils.getDisplayString(map.get(key))).filter(StringUtils::isNotEmpty).findFirst().orElse(null);
    }

    @Nullable
    public static <K, V> String toDelimitedString(@Nullable Map<K, V> map) {
        return toDelimitedString(map, CharUtils.toString(CharVariantConst.EQUALS), SymbolVariantConst.COMMA_SPACE);
    }

    @Nullable
    public static <K, V> String toDelimitedString(@Nullable Map<K, V> map, char keyValueDelimiter, char groupDelimiter) {
        return toDelimitedString(map, CharUtils.toString(keyValueDelimiter), CharUtils.toString(groupDelimiter), null, null);
    }

    @Nullable
    public static <K, V> String toDelimitedString(@Nullable Map<K, V> map, @Nullable CharSequence keyValueDelimiter, @Nullable CharSequence groupDelimiter) {
        return toDelimitedString(map, keyValueDelimiter, groupDelimiter, null, null);
    }

    @Nullable
    public static <K, V> String toDelimitedString(@Nullable Map<K, V> map, @Nullable CharSequence keyValueDelimiter, @Nullable CharSequence groupDelimiter, @Nullable CharSequence keyPrefix, @Nullable CharSequence keySuffix) {
        return toDelimitedString(map, keyValueDelimiter, groupDelimiter, keyPrefix, keySuffix, null, null);
    }

    /**
     * Return a delimited string with specified delimiters
     *
     * @param map the {@code Map} to convert
     * @param keyValueDelimiter the delimiter to separate key and value (typically is "=")
     * @param groupDelimiter the delimiter to separate groups (typically is "&amp;")
     * @param keyPrefix the string to start each key with
     * @param keySuffix the string to end each key with
     * @param valuePrefix the string to start each value with
     * @param valueSuffix the string to end each value with
     *
     * @return a delimited string with specified delimiters
     *
     * @see java.util.Properties
     * @see "org.apache.http.message.BasicNameValuePair"
     */
    @Nullable
    public static <K, V> String toDelimitedString(@Nullable Map<K, V> map, @Nullable CharSequence keyValueDelimiter, @Nullable CharSequence groupDelimiter, @Nullable CharSequence keyPrefix, @Nullable CharSequence keySuffix, @Nullable CharSequence valuePrefix, @Nullable CharSequence valueSuffix) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        StringJoiner joiner = new StringJoiner(ObjectUtils.getDisplayString(groupDelimiter));
        for (Map.Entry<K, V> entry : map.entrySet()) {
            StringBuilder builder = new StringBuilder();
            if (entry.getKey() != null) {
                builder.append(ObjectUtils.getDisplayString(keyPrefix));
            }
            builder.append(ObjectUtils.nullSafeToString(entry.getKey()));
            if (entry.getKey() != null) {
                builder.append(ObjectUtils.getDisplayString(keySuffix));
            }
            builder.append(ObjectUtils.getDisplayString(keyValueDelimiter));
            if (entry.getValue() != null) {
                builder.append(ObjectUtils.getDisplayString(valuePrefix));
            }
            builder.append(ObjectUtils.nullSafeToString(entry.getValue()));
            if (entry.getValue() != null) {
                builder.append(ObjectUtils.getDisplayString(valueSuffix));
            }
            joiner.add(builder.toString());
        }
        return joiner.toString();
    }
}
