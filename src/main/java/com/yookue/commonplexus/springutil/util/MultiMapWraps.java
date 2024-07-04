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


import java.util.List;
import java.util.Objects;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;


/**
 * Utilities for {@link org.springframework.util.MultiValueMap}
 *
 * @author David Hsing
 * @see org.springframework.util.MultiValueMap
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class MultiMapWraps {
    /**
     * Add the given value to the {@code target} map for the given key
     *
     * @param target the target map to add to
     * @param key the key to search
     * @param value the value to be added
     */
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> void add(@Nullable MultiValueMap<K, V> target, @Nullable K key, @Nullable V value) {
        if (ObjectUtils.allNotNull(target, key, value)) {
            target.add(key, value);
        }
    }

    /**
     * Add all the values to the {@code target} map for the given key
     *
     * @param target the target map to add to
     * @param key the key to search
     * @param values the values to be added
     */
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> void addAll(@Nullable MultiValueMap<K, V> target, @Nullable K key, @Nullable List<? extends V> values) {
        if (ObjectUtils.allNotNull(target, key)) {
            target.addAll(key, values);
        }
    }

    /**
     * Add all the {@code source} map to the {@code target} map
     *
     * @param target the target map to add to
     * @param source the key and values to be added
     */
    public static <K, V> void addAll(@Nullable MultiValueMap<K, V> target, @Nullable MultiValueMap<K, V> source) {
        if (target != null && MapPlainWraps.isNotEmpty(source)) {
            target.addAll(source);
        }
    }

    public static <K> boolean containsKey(@Nullable MultiValueMap<K, ?> map, @Nullable K key) {
        return MapPlainWraps.containsKey(map, key);
    }

    public static <K, V> boolean containsKeyValue(@Nullable MultiValueMap<K, V> map, @Nullable K key, @Nullable V value) {
        return MapPlainWraps.containsKey(map, key) && CollectionPlainWraps.contains(map.get(key), value);
    }

    @SafeVarargs
    public static <K, V> boolean containsKeyValues(@Nullable MultiValueMap<K, V> map, @Nullable K key, @Nullable V... values) {
        return MapPlainWraps.containsKey(map, key) && CollectionPlainWraps.containsAll(map.get(key), values);
    }

    public static <V> boolean containsValue(@Nullable MultiValueMap<?, V> map, @Nullable V value) {
        return !CollectionUtils.isEmpty(map) && map.keySet().stream().anyMatch(key -> CollectionPlainWraps.contains(map.get(key), value));
    }

    /**
     * Returns the first element of value list for the given key
     *
     * @param map the source map to look for
     * @param key the key to find
     *
     * @return the first element of value list for the given key
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> V firstValue(@Nullable MultiValueMap<K, V> map, @Nullable K key) {
        return ObjectUtils.anyNull(map, key) ? null : map.getFirst(key);
    }

    /**
     * Returns the first non-null value for the given key
     *
     * @param map the source map to look for
     * @param key the key to find
     *
     * @return the first non-null value for the given key
     */
    @Nullable
    public static <K, V> V firstNonNullValue(@Nullable MultiValueMap<K, V> map, @Nullable K key) {
        return !containsKey(map, key) ? null : map.get(key).stream().filter(Objects::nonNull).findFirst().orElse(null);
    }

    /**
     * Returns the first non-empty value for the given key
     *
     * @param map the source map to look for
     * @param key the key to find
     *
     * @return the first non-empty value for the given key
     */
    @Nullable
    public static <K, V> V firstNonEmptyValue(@Nullable MultiValueMap<K, V> map, @Nullable K key) {
        return !containsKey(map, key) ? null : map.get(key).stream().filter(ObjectUtils::isNotEmpty).findFirst().orElse(null);
    }

    /**
     * Returns the first non-blank value for the given key
     *
     * @param map the source map to look for
     * @param key the key to find
     *
     * @return the first non-blank value for the given key
     */
    @Nullable
    public static <K, V extends CharSequence> V firstNonBlankValue(@Nullable MultiValueMap<K, V> map, @Nullable K key) {
        return !containsKey(map, key) ? null : map.get(key).stream().filter(StringUtils::isNotBlank).findFirst().orElse(null);
    }

    /**
     * Returns the value array for the given key
     *
     * @param map the source map to look for
     * @param key the key to find
     *
     * @return the value array for the given key
     */
    @Nullable
    public static <K, V> V[] getValues(@Nullable MultiValueMap<K, V> map, @Nullable K key) {
        return !containsKey(map, key) ? null : CollectionPlainWraps.toElementArray(map.get(key));
    }

    public static <K, V> boolean removeByValueContains(@Nullable MultiValueMap<K, V> map, @Nullable V value) {
        return MapPlainWraps.isNotEmpty(map) && map.entrySet().removeIf(entry -> entry != null && CollectionPlainWraps.contains(entry.getValue(), value));
    }
}
