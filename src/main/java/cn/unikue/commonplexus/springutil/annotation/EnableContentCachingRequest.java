/*
 * Copyright (c) 2016 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import cn.unikue.commonplexus.springutil.constant.AntPathConst;
import cn.unikue.commonplexus.springutil.registrar.ContentCachingRequestRegistrar;


/**
 * Annotation that enables a {@link cn.unikue.commonplexus.springutil.filter.ContentCachingRequestFilter}
 *
 * @author David Hsing
 *
 * @see org.springframework.web.util.ContentCachingRequestWrapper
 * @see cn.unikue.commonplexus.springutil.filter.ContentCachingRequestFilter
 * @see cn.unikue.commonplexus.springutil.registrar.ContentCachingRequestRegistrar
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@Import(value = ContentCachingRequestRegistrar.class)
@SuppressWarnings("unused")
public @interface EnableContentCachingRequest {
    /**
     * Returns the url patterns of the filter
     *
     * @return the url patterns of the filter
     */
    String[] urlPatterns() default {AntPathConst.SLASH_STARS};

    /**
     * Returns the url excludes of the filter
     *
     * @return the url excludes of the filter
     */
    String[] urlExcludes() default {};

    /**
     * Returns the order of the filter
     *
     * @return the order of the filter
     */
    int filterOrder() default 0;

    /**
     * Returns whether enables caching multipart or not
     *
     * @return whether enables caching multipart or not
     */
    boolean cacheMultipart() default false;

    /**
     * Returns the max length of the cache in the request wrapper
     * <p>
     * Negative or zero values means no limit
     *
     * @return the max length of the cache in the request wrapper
     */
    int cacheLimit() default -1;
}
