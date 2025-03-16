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


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Utilities for jackson redis
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class JacksonRedisWraps {
    @Nonnull
    public static Jackson2JsonRedisSerializer<Object> jsonObjectSerializer() {
        return jsonObjectSerializer(null);
    }

    @Nonnull
    public static Jackson2JsonRedisSerializer<Object> jsonObjectSerializer(@Nullable JacksonProperties properties) {
        ObjectMapper mapper = JacksonConfigWraps.jsonObjectMapper(properties);
        mapper = mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper = mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        return new Jackson2JsonRedisSerializer<>(mapper, Object.class);
    }

    public static RedisTemplate<Object, Object> genericObjectRedisTemplate(@Nullable RedisConnectionFactory factory) {
        RedisSerializer<Object> serializer = RedisSerializer.json();
        return objectObjectRedisTemplate(factory, serializer, serializer);
    }

    public static RedisTemplate<Object, Object> objectObjectRedisTemplate(@Nullable RedisConnectionFactory factory) {
        return objectObjectRedisTemplate(factory, null);
    }

    public static RedisTemplate<Object, Object> objectObjectRedisTemplate(@Nullable RedisConnectionFactory factory, @Nullable JacksonProperties properties) {
        RedisSerializer<Object> serializer = jsonObjectSerializer(properties);
        return objectObjectRedisTemplate(factory, serializer, serializer);
    }

    @Nullable
    public static RedisTemplate<Object, Object> objectObjectRedisTemplate(@Nullable RedisConnectionFactory factory, @Nullable RedisSerializer<Object> keySerializer, @Nullable RedisSerializer<Object> valueSerializer) {
        return RedisConfigWraps.redisTemplate(factory, keySerializer, valueSerializer);
    }

    public static RedisTemplate<String, Object> stringObjectRedisTemplate(@Nullable RedisConnectionFactory factory) {
        return stringObjectRedisTemplate(factory, null);
    }

    public static RedisTemplate<String, Object> stringObjectRedisTemplate(@Nullable RedisConnectionFactory factory, @Nullable JacksonProperties properties) {
        RedisSerializer<String> keySerializer = RedisSerializer.string();
        RedisSerializer<Object> valueSerializer = jsonObjectSerializer(properties);
        return stringObjectRedisTemplate(factory, keySerializer, valueSerializer);
    }

    @Nullable
    public static RedisTemplate<String, Object> stringObjectRedisTemplate(@Nullable RedisConnectionFactory factory, @Nullable RedisSerializer<String> keySerializer, @Nullable RedisSerializer<Object> valueSerializer) {
        return RedisConfigWraps.redisTemplate(factory, keySerializer, valueSerializer);
    }
}
