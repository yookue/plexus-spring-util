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
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.Failable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;


/**
 * Utilities for operating {@link org.springframework.data.redis.core.RedisTemplate}
 *
 * @author David Hsing
 * @see org.springframework.data.redis.core.RedisTemplate
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public abstract class RedisTemplateWraps {
    @SafeVarargs
    public static <K> long countKey(@Nullable RedisTemplate<K, ?> template, @Nullable K... keys) {
        return countKey(template, ArrayUtilsWraps.asList(keys));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K> long countKey(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> keys) {
        if (template == null || CollectionUtils.isEmpty(keys)) {
            return 0;
        }
        return keys.stream().filter(element -> Objects.nonNull(element) && template.hasKey(element)).count();
    }

    @SafeVarargs
    public static <K> long countPattern(@Nullable RedisTemplate<K, ?> template, @Nullable K... patterns) {
        return countPattern(template, ArrayUtilsWraps.asList(patterns));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K> long countPattern(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> patterns) {
        if (template == null || CollectionUtils.isEmpty(patterns)) {
            return 0;
        }
        return patterns.stream().filter(Objects::nonNull).map(template::keys).filter(CollectionPlainWraps::isNotEmpty).mapToLong(template::countExistingKeys).sum();
    }

    @SafeVarargs
    public static <K> void deleteKey(@Nullable RedisTemplate<K, ?> template, @Nullable K... keys) {
        deleteKey(template, ArrayUtilsWraps.asList(keys));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K> void deleteKey(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> keys) {
        if (template == null || CollectionUtils.isEmpty(keys)) {
            return;
        }
        keys.stream().filter(Objects::nonNull).forEach(Failable.asConsumer(template::delete));
    }

    @SafeVarargs
    public static <K> void deletePattern(@Nullable RedisTemplate<K, ?> template, @Nullable K... patterns) {
        deletePattern(template, ArrayUtilsWraps.asList(patterns));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K> void deletePattern(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> patterns) {
        if (template == null || CollectionUtils.isEmpty(patterns)) {
            return;
        }
        patterns.stream().filter(Objects::nonNull).map(template::keys).filter(CollectionPlainWraps::isNotEmpty).forEach(Failable.asConsumer(template::delete));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K> boolean existsKey(@Nullable RedisTemplate<K, ?> template, @Nullable K key) {
        return ObjectUtils.allNotNull(template, key) && BooleanUtils.isTrue(template.hasKey(key));
    }

    @SafeVarargs
    public static <K> boolean existsAllKeys(@Nullable RedisTemplate<K, ?> template, @Nullable K... keys) {
        return existsAllKeys(template, ArrayUtilsWraps.asList(keys));
    }

    public static <K> boolean existsAllKeys(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> keys) {
        return template != null && !CollectionUtils.isEmpty(keys) && keys.stream().filter(Objects::nonNull).allMatch(key -> existsKey(template, key));
    }

    @SafeVarargs
    public static <K> boolean existsAnyKeys(@Nullable RedisTemplate<K, ?> template, @Nullable K... keys) {
        return existsAnyKeys(template, ArrayUtilsWraps.asList(keys));
    }

    public static <K> boolean existsAnyKeys(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> keys) {
        return template != null && !CollectionUtils.isEmpty(keys) && keys.stream().filter(Objects::nonNull).anyMatch(key -> existsKey(template, key));
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> V getValue(@Nullable RedisTemplate<K, V> template, @Nullable K key) {
        return ObjectUtils.anyNull(template, key) ? null : template.opsForValue().get(key);
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> void setValue(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value) {
        if (ObjectUtils.allNotNull(template, key, value)) {
            template.opsForValue().set(key, value);
        }
    }

    public static <K, V> void setValueExpiring(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, long seconds) {
        setValueExpiring(template, key, value, TimeUnit.SECONDS, seconds);
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <K, V> void setValueExpiring(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable TimeUnit unit, long ttl) {
        if (ObjectUtils.anyNull(template, key, value)) {
            return;
        }
        template.opsForValue().set(key, value);
        if (unit != null && ttl > 0) {
            template.expire(key, ttl, unit);
        }
    }
}
