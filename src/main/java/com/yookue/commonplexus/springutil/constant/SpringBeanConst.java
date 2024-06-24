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

package com.yookue.commonplexus.springutil.constant;


/**
 * Constants for Spring beans
 *
 * @author David Hsing
 * @see org.springframework.web.servlet.DispatcherServlet
 */
@SuppressWarnings({"unused", "JavadocReference"})
public abstract class SpringBeanConst {
    /**
     * @see org.springframework.cache.annotation.ProxyCachingConfiguration#cacheInterceptor
     */
    public static final String CACHE_INTERCEPTOR = "cacheInterceptor";    // $NON-NLS-1$

    /**
     * @see org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     */
    public static final String CACHE_MANAGER = "cacheManager";    // $NON-NLS-1$
    public static final String CACHE_RESOLVER = "cacheResolver";    // $NON-NLS-1$

    /**
     * @see org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration
     */
    public static final String GSON = "gson";    // $NON-NLS-1$

    /**
     * @see org.springframework.web.servlet.HandlerExceptionResolver
     */
    public static final String HANDLER_EXCEPTION_RESOLVER = "handlerExceptionResolver";    // $NON-NLS-1$

    /**
     * @see org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
     */
    public static final String JACKSON_OBJECT_MAPPER = "jacksonObjectMapper";    // $NON-NLS-1$

    /**
     * @see org.springframework.data.domain.AuditorAware
     * @see org.springframework.data.jpa.repository.config.BeanDefinitionNames
     * @see org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
     */
    public static final String JPA_MAPPING_CONTEXT = "jpaMappingContext";    // $NON-NLS-1$

    /**
     * @see org.springframework.data.auditing.AuditingHandler
     * @see org.springframework.data.jpa.repository.config.JpaAuditingRegistrar#getAuditingHandlerBeanName
     * @see org.springframework.data.auditing.IsNewAwareAuditingHandler
     */
    public static final String JPA_AUDITING_HANDLER = "jpaAuditingHandler";    // $NON-NLS-1$

    /**
     * @see org.springframework.context.support.AbstractApplicationContext#MESSAGE_SOURCE_BEAN_NAME
     * @see org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration#messageSource
     */
    public static final String MESSAGE_SOURCE = "messageSource";    // $NON-NLS-1$

    /**
     * @see org.springframework.data.domain.AuditorAware
     * @see org.springframework.data.mongodb.config.BeanNames
     */
    public static final String MONGO_MAPPING_CONTEXT = "mongoMappingContext";    // $NON-NLS-1$

    /**
     * @see org.springframework.data.auditing.AuditingHandler
     * @see org.springframework.data.mongodb.config.MongoAuditingRegistrar#getAuditingHandlerBeanName
     */
    public static final String MONGO_AUDITING_HANDLER = "mongoAuditingHandler";    // $NON-NLS-1$

    /**
     * @see org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
     */
    public static final String REDIS_TEMPLATE = "redisTemplate";    // $NON-NLS-1$
    public static final String STRING_REDIS_TEMPLATE = "stringRedisTemplate";    // $NON-NLS-1$

    /**
     * @see org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#requestContextFilter
     */
    public static final String REQUEST_CONTEXT_FILTER = "requestContextFilter";    // $NON-NLS-1$

    /**
     * @see org.springframework.ui.context.support.UiApplicationContextUtils
     */
    public static final String THEME_SOURCE = "themeSource";    // $NON-NLS-1$
}
