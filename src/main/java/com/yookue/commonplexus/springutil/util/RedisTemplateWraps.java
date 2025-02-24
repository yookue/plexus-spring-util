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


import java.time.Duration;
import java.time.temporal.ChronoUnit;
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
import com.yookue.commonplexus.javaseutil.util.DurationUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;


/**
 * Utilities for operating {@link org.springframework.data.redis.core.RedisTemplate}
 *
 * @author David Hsing
 * @see org.springframework.data.redis.core.RedisTemplate
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
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

    @Nullable
    public static <K, T> T getValueAs(@Nullable RedisTemplate<K, ?> template, @Nullable K key, @Nullable Class<T> expectedType) {
        return ObjectUtilsWraps.castAs(getValue(template, key), expectedType);
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> V getValueAndDelete(@Nullable RedisTemplate<K, V> template, @Nullable K key) {
        return ObjectUtils.anyNull(template, key) ? null : template.opsForValue().getAndDelete(key);
    }

    @Nullable
    public static <K, V> V getValueAndExpire(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable Long timeout, @Nullable ChronoUnit unit) {
        return getValueAndExpire(template, key, DurationUtilsWraps.ofChronoUnit(timeout, unit));
    }

    @Nullable
    public static <K, V> V getValueAndExpire(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable Long timeout, @Nullable TimeUnit unit) {
        return getValueAndExpire(template, key, DurationUtilsWraps.ofTimeUnit(timeout, unit));
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> V getValueAndExpire(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable Duration timeout) {
        if (ObjectUtils.anyNull(template, key)) {
            return null;
        }
        return (timeout == null) ? template.opsForValue().get(key) : template.opsForValue().getAndExpire(key, timeout);
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> V getValueAndPersist(@Nullable RedisTemplate<K, V> template, @Nullable K key) {
        return ObjectUtils.anyNull(template, key) ? null : template.opsForValue().getAndPersist(key);
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> V getValueAndSet(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value) {
        return ObjectUtils.anyNull(template, key) ? null : template.opsForValue().getAndSet(key, value);
    }

    @SuppressWarnings("DataFlowIssue")
    public static <K, V> void setValue(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value) {
        if (ObjectUtils.allNotNull(template, key)) {
            template.opsForValue().set(key, value);
        }
    }

    public static <K, V> void setValue(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable ChronoUnit unit) {
        setValue(template, key, value, DurationUtilsWraps.ofChronoUnit(timeout, unit));
    }

    public static <K, V> void setValue(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable TimeUnit unit) {
        setValue(template, key, value, DurationUtilsWraps.ofTimeUnit(timeout, unit));
    }

    @SuppressWarnings("DataFlowIssue")
    public static <K, V> void setValue(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Duration timeout) {
        if (ObjectUtils.anyNull(template, key)) {
            return;
        }
        if (timeout == null) {
            template.opsForValue().set(key, value);
        } else {
            template.opsForValue().set(key, value, timeout);
        }
    }

    public static <K, V> boolean setIfAbsent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable TimeUnit unit) {
        return setIfAbsent(template, key, value, DurationUtilsWraps.ofTimeUnit(timeout, unit));
    }

    public static <K, V> boolean setIfAbsent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable ChronoUnit unit) {
        return setIfAbsent(template, key, value, DurationUtilsWraps.ofChronoUnit(timeout, unit));
    }

    @SuppressWarnings("DataFlowIssue")
    public static <K, V> boolean setIfAbsent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Duration timeout) {
        if (ObjectUtils.anyNull(template, key)) {
            return false;
        }
        if (timeout == null) {
            return BooleanUtils.isTrue(template.opsForValue().setIfAbsent(key, value));
        } else {
            return BooleanUtils.isTrue(template.opsForValue().setIfAbsent(key, value, timeout));
        }
    }

    public static <K, V> boolean setIfPresent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable TimeUnit unit) {
        return setIfPresent(template, key, value, DurationUtilsWraps.ofTimeUnit(timeout, unit));
    }

    public static <K, V> boolean setIfPresent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable ChronoUnit unit) {
        return setIfPresent(template, key, value, DurationUtilsWraps.ofChronoUnit(timeout, unit));
    }

    @SuppressWarnings("DataFlowIssue")
    public static <K, V> boolean setIfPresent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Duration timeout) {
        if (ObjectUtils.anyNull(template, key)) {
            return false;
        }
        if (timeout == null) {
            return BooleanUtils.isTrue(template.opsForValue().setIfPresent(key, value));
        } else {
            return BooleanUtils.isTrue(template.opsForValue().setIfPresent(key, value, timeout));
        }
    }
}
