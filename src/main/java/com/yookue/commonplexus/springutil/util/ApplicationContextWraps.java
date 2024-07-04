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


import jakarta.annotation.Nullable;
import jakarta.servlet.ServletContext;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;
import com.yookue.commonplexus.springutil.constant.SpringPropertyConst;


/**
 * Utilities for {@link org.springframework.context.ApplicationContext}
 *
 * @author David Hsing
 * @see org.springframework.context.ApplicationContext
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.context.support.GenericApplicationContext
 * @see org.springframework.web.context.WebApplicationContext
 * @see org.springframework.web.context.support.WebApplicationObjectSupport
 * @see org.springframework.web.context.support.WebApplicationContextUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ApplicationContextWraps {
    public static String getApplicationName(@Nullable ApplicationContext context) {
        return getApplicationName(context, null);
    }

    public static String getApplicationName(@Nullable ApplicationContext context, @Nullable String defaultValue) {
        return (context == null) ? defaultValue : StringUtils.firstNonBlank(context.getApplicationName(), getProperty(context, SpringPropertyConst.APPLICATION_NAME), defaultValue);
    }

    public static String getDisplayName(@Nullable ApplicationContext context) {
        return getDisplayName(context, null);
    }

    public static String getDisplayName(@Nullable ApplicationContext context, @Nullable String defaultValue) {
        return (context == null) ? defaultValue : StringUtils.defaultIfBlank(context.getDisplayName(), defaultValue);
    }

    public static ConfigurableApplicationContext getConfigurableApplicationContext(@Nullable ApplicationContext context) {
        return (context instanceof ConfigurableApplicationContext) ? (ConfigurableApplicationContext) context : null;
    }

    public static WebApplicationContext getWebApplicationContext(@Nullable ApplicationContext context) {
        return (context instanceof WebApplicationContext) ? (WebApplicationContext) context : null;
    }

    public static WebApplicationContext getWebApplicationContext(@Nullable ServletContext context) {
        return (context == null) ? null : WebApplicationContextUtils.getWebApplicationContext(context);
    }

    public static WebServerApplicationContext getWebServerApplicationContext(@Nullable ApplicationContext context) {
        return (context instanceof WebServerApplicationContext) ? (WebServerApplicationContext) context : null;
    }

    /**
     * @see org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer
     */
    public static Integer getLocalServerPort(@Nullable ApplicationContext context) {
        if (!(context instanceof ConfigurableApplicationContext instance)) {
            return null;
        }
        Integer result = ApplicationEnvironmentWraps.getLocalServerPort(instance.getEnvironment());
        if (result == null && context.getParent() != null) {
            return getLocalServerPort(context.getParent());
        }
        return result;
    }

    public static String getLocalServerPortProperty(@Nullable ApplicationContext context) {
        return getLocalServerPortProperty(getWebServerApplicationContext(context));
    }

    /**
     * @see org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer#onApplicationEvent
     */
    public static String getLocalServerPortProperty(@Nullable WebServerApplicationContext context) {
        return (context == null) ? null : StringUtils.join("local.", StringUtils.defaultIfBlank(context.getServerNamespace(), "server"), ".port");    // $NON-NLS-1$ // $NON-NLS-2$ // $NON-NLS-3$
    }

    public static String getProperty(@Nullable ApplicationContext context, @Nullable String name) {
        return getProperty(context, name, null);
    }

    @SuppressWarnings("DataFlowIssue")
    public static String getProperty(@Nullable ApplicationContext context, @Nullable String name, @Nullable String defaultValue) {
        return (context == null || StringUtils.isBlank(name)) ? defaultValue : context.getEnvironment().getProperty(name, defaultValue);
    }

    @Nullable
    public static ServletContext getServletContext(@Nullable ApplicationContext context) {
        WebApplicationContext webContext = getWebApplicationContext(context);
        return (webContext == null) ? null : webContext.getServletContext();
    }

    @Nullable
    public static String getServletContextPath(@Nullable ApplicationContext context) {
        ServletContext servletContext = getServletContext(context);
        return (servletContext == null) ? null : servletContext.getContextPath();
    }

    /**
     * @see org.springframework.web.util.ServletContextPropertyUtils
     */
    public static Object getServletContextAttribute(@Nullable ApplicationContext context, String name) {
        if (context == null || StringUtils.isBlank(name)) {
            return null;
        }
        ServletContext servletContext = getServletContext(context);
        return (servletContext == null) ? null : servletContext.getAttribute(name);
    }

    @Nullable
    public static <T> T getServletContextAttributeAs(@Nullable ApplicationContext context, @Nullable String name, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(context, expectedType) || StringUtils.isBlank(name)) {
            return null;
        }
        Object value = getServletContextAttribute(context, name);
        return (value == null) ? null : ObjectUtilsWraps.castAs(value, expectedType);
    }

    public static boolean setServletContextAttribute(@Nullable ApplicationContext context, @Nullable String name, @Nullable Object value) {
        if (context == null || StringUtils.isBlank(name)) {
            return false;
        }
        ServletContext servletContext = getServletContext(context);
        if (servletContext == null) {
            return false;
        }
        servletContext.setAttribute(name, value);
        return true;
    }
}
