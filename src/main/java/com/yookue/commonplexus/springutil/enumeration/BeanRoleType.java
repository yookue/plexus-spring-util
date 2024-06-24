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
 * Enumerations of bean role types
 *
 * @author David Hsing
 * @see org.springframework.beans.factory.config.BeanDefinition
 */
@AllArgsConstructor
@Getter
@SuppressWarnings("unused")
public enum BeanRoleType implements ValueEnum<Integer> {
    /**
     * Indicates that a {@link org.springframework.beans.factory.config.BeanDefinition} is a major part of the application
     * <br>
     * Typically corresponds to a user-defined bean
     */
    APPLICATION(0),    // $NON-NLS-1$

    /**
     * Indicates that a {@link org.springframework.beans.factory.config.BeanDefinition} is a supporting part of some larger configuration
     * <br>
     * Typically, an outer {@link org.springframework.beans.factory.parsing.ComponentDefinition}
     * <br>
     * {@code SUPPORT} beans are considered important enough to be aware of when looking more closely at a particular
     * {@link org.springframework.beans.factory.parsing.ComponentDefinition}, but not when looking at the overall configuration of an application
     */
    SUPPORT(1),    // $NON-NLS-1$

    /**
     * Indicates that a {@link org.springframework.beans.factory.config.BeanDefinition} is providing an entirely background role and has no relevance to the end-user
     * <br>
     * This hint is used when registering beans that are completely part of the internal workings of a {@link org.springframework.beans.factory.parsing.ComponentDefinition}
     */
    INFRASTRUCTURE(2);    // $NON-NLS-1$

    private final Integer value;
}
