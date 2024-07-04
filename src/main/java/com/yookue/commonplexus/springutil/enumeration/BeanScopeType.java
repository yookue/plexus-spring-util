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

package com.yookue.commonplexus.springutil.enumeration;


import com.yookue.commonplexus.javaseutil.support.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Enumerations of bean scope types
 *
 * @author David Hsing
 * @reference "https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes"
 * @see org.springframework.beans.factory.config.BeanDefinition
 */
@AllArgsConstructor
@Getter
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public enum BeanScopeType implements ValueEnum<String> {
    /**
     * Scopes a single bean definition to a single object instance for each Spring IoC container
     */
    SINGLETON("singleton"),    // $NON-NLS-1$

    /**
     * Scopes a single bean definition to any number of object instances
     */
    PROTOTYPE("prototype"),    // $NON-NLS-1$

    /**
     * Scopes a single bean definition to the lifecycle of a single HTTP request
     * <br>
     * That is, each HTTP request has its own instance of a bean created off the back of a single bean definition
     * <br>
     * Only valid in the context of a web-aware Spring {@link org.springframework.context.ApplicationContext}
     */
    REQUEST("request"),    // $NON-NLS-1$

    /**
     * Scopes a single bean definition to the lifecycle of an HTTP Session
     * <br>
     * Only valid in the context of a web-aware Spring {@link org.springframework.context.ApplicationContext}
     */
    SESSION("session"),    // $NON-NLS-1$

    /**
     * Scopes a single bean definition to the lifecycle of a {@link jakarta.servlet.ServletContext}
     * <br>
     * Only valid in the context of a web-aware Spring {@link org.springframework.context.ApplicationContext}
     */
    APPLICATION("application"),    // $NON-NLS-1$

    /**
     * Scopes a single bean definition to the lifecycle of a WebSocket
     * <br>
     * Only valid in the context of a web-aware Spring {@link org.springframework.context.ApplicationContext}
     */
    WEBSOCKET("websocket");    // $NON-NLS-1$

    private final String value;
}
