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
 * Constants for Spring attributes
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class SpringAttributeConst {
    /**
     * @see org.springframework.boot.web.servlet.error.ErrorAttributes
     * @see org.springframework.boot.web.servlet.error.DefaultErrorAttributes
     */
    public static final String SERVLET_DEFAULT_ERROR = "org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR";    // $NON-NLS-1$

    /**
     * @see org.springframework.boot.web.reactive.error.DefaultErrorAttributes
     */
    public static final String REACTIVE_DEFAULT_ERROR = "org.springframework.boot.web.reactive.error.DefaultErrorAttributes.ERROR";    // $NON-NLS-1$

    public static final String CACHE_NAMESPACE = "spring:cache:";    // $NON-NLS-1$
    public static final String CACHE_ROOT_TARGET_CLASS = "#root.targetClass";    // $NON-NLS-1$
    public static final String CACHE_TARGET_CLASS = "#targetClass";    // $NON-NLS-1$
    public static final String CACHE_ROOT_METHOD_NAME = "#root.methodName";    // $NON-NLS-1$
    public static final String CACHE_METHOD_NAME = "#methodName";    // $NON-NLS-1$

    /**
     * Shortened form for {@code CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME}
     *
     * @see org.springframework.web.servlet.i18n.CookieLocaleResolver
     */
    public static final String LOCALE_RESOLVER_LOCALE = "LocaleResolver.LOCALE";    // $NON-NLS-1$

    /**
     * Shortened form for {@code SessionLocaleResolver.TIME_ZONE_SESSION_ATTRIBUTE_NAME}
     *
     * @see org.springframework.web.servlet.i18n.SessionLocaleResolver
     */
    public static final String LOCALE_RESOLVER_TIMEZONE = "LocaleResolver.TIMEZONE";    // $NON-NLS-1$

    /**
     * Shortened form for {@code CookieThemeResolver.THEME_REQUEST_ATTRIBUTE_NAME}
     *
     * @see org.springframework.web.servlet.theme.CookieThemeResolver
     */
    public static final String THEME_RESOLVER_THEME = "ThemeResolver.THEME";    // $NON-NLS-1$

    /**
     * @see org.springframework.session.data.redis.RedisIndexedSessionRepository
     */
    public static final String SESSION_NAMESPACE = "spring:session:";    // $NON-NLS-1$
}
