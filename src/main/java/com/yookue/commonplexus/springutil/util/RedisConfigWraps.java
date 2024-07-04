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
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Utilities for configuring redis
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class RedisConfigWraps {
    @Nonnull
    public static RedisCacheConfiguration cacheConfiguration() {
        return cacheConfiguration(null, null, null);
    }

    @Nonnull
    public static RedisCacheConfiguration cacheConfiguration(@Nullable RedisCacheConfiguration configuration) {
        return cacheConfiguration(configuration, null, null);
    }

    @Nonnull
    public static RedisCacheConfiguration cacheConfiguration(@Nullable CacheProperties properties) {
        return cacheConfiguration(null, properties, null);
    }

    @Nonnull
    public static RedisCacheConfiguration cacheConfiguration(@Nullable JacksonProperties properties) {
        return cacheConfiguration(null, null, properties);
    }

    /**
     * @reference "https://blog.csdn.net/weixin_29146779/article/details/112816912"
     */
    @Nonnull
    @SuppressWarnings({"JavadocDeclaration", "JavadocLinkAsPlainText"})
    public static RedisCacheConfiguration cacheConfiguration(@Nullable RedisCacheConfiguration configuration, @Nullable CacheProperties cacheProperties, @Nullable JacksonProperties jacksonProperties) {
        RedisCacheConfiguration result = (configuration != null) ? configuration : RedisCacheConfiguration.defaultCacheConfig();
        if (configuration == null && cacheProperties != null && cacheProperties.getRedis().getTimeToLive() != null) {
            result = result.entryTtl(cacheProperties.getRedis().getTimeToLive());
        }
        result = result.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        result = result.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonJsonSerializer(jacksonProperties)));
        return result;
    }

    public static RedisCacheManager.RedisCacheManagerBuilder cacheManagerBuilder(@Nullable RedisConnectionFactory factory) {
        return cacheManagerBuilder(factory, null, null);
    }

    public static RedisCacheManager.RedisCacheManagerBuilder cacheManagerBuilder(@Nullable RedisConnectionFactory factory, @Nullable CacheProperties cacheProperties, @Nullable JacksonProperties jacksonProperties) {
        if (factory == null) {
            return null;
        }
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(factory);
        if (cacheProperties != null && cacheProperties.getRedis().isEnableStatistics()) {
            builder = builder.enableStatistics();
        }
        builder = builder.cacheDefaults(cacheConfiguration(null, cacheProperties, jacksonProperties));
        return builder;
    }

    @Nullable
    public static RedisCacheManager cacheManager(@Nullable RedisConnectionFactory factory) {
        return cacheManager(factory, null, null);
    }

    @Nullable
    public static RedisCacheManager cacheManager(@Nullable RedisConnectionFactory factory, @Nullable CacheProperties cacheProperties, @Nullable JacksonProperties jacksonProperties) {
        RedisCacheManager.RedisCacheManagerBuilder builder = cacheManagerBuilder(factory, cacheProperties, jacksonProperties);
        return (builder == null) ? null : builder.build();
    }

    @Nonnull
    public static Jackson2JsonRedisSerializer<Object> jacksonJsonSerializer() {
        return jacksonJsonSerializer(null);
    }

    @Nonnull
    public static Jackson2JsonRedisSerializer<Object> jacksonJsonSerializer(@Nullable JacksonProperties properties) {
        ObjectMapper mapper = JacksonConfigWraps.jsonObjectMapper(properties);
        mapper = mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper = mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        return new Jackson2JsonRedisSerializer<>(mapper, Object.class);
    }

    public static RedisTemplate<String, Object> jacksonJsonSerializerTemplate(@Nullable RedisConnectionFactory factory) {
        return jacksonJsonSerializerTemplate(factory, null);
    }

    public static RedisTemplate<String, Object> jacksonJsonSerializerTemplate(@Nullable RedisConnectionFactory factory, @Nullable JacksonProperties properties) {
        return objectValueSerializerTemplate(factory, jacksonJsonSerializer(properties));
    }

    public static RedisTemplate<String, Object> genericJsonSerializerTemplate(@Nullable RedisConnectionFactory factory) {
        return objectValueSerializerTemplate(factory, new GenericJackson2JsonRedisSerializer());
    }

    public static RedisTemplate<String, Object> objectValueSerializerTemplate(@Nullable RedisConnectionFactory factory, @Nullable RedisSerializer<Object> valueSerializer) {
        if (factory == null || valueSerializer == null) {
            return null;
        }
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        RedisTemplate<String, Object> result = new RedisTemplate<>();
        result.setConnectionFactory(factory);
        result.setKeySerializer(stringSerializer);
        result.setHashKeySerializer(stringSerializer);
        result.setValueSerializer(valueSerializer);
        result.setHashValueSerializer(valueSerializer);
        result.afterPropertiesSet();
        return result;
    }
}
