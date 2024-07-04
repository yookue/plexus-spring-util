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

package com.yookue.commonplexus.springutil.exception;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.core.ResolvableType;


/**
 * Thrown to indicate that the requested bean is missing
 *
 * @author David Hsing
 * @see org.springframework.beans.factory.NoSuchBeanDefinitionException
 */
@SuppressWarnings("unused")
public class NoSuchBeanException extends BeansException {
    public NoSuchBeanException(@Nonnull String beanName) {
        super(String.format("No bean named '%s' available", beanName));    // $NON-NLS-1$
    }

    public NoSuchBeanException(@Nonnull String beanName, @Nullable String message) {
        super(String.format("No bean named '%s' available: %s", beanName, message));    // $NON-NLS-1$
    }

    public NoSuchBeanException(@Nonnull Class<?> type) {
        this(ResolvableType.forClass(type));
    }

    public NoSuchBeanException(@Nonnull Class<?> type, @Nullable String message) {
        this(ResolvableType.forClass(type), message);
    }

    public NoSuchBeanException(@Nonnull ResolvableType type) {
        super(String.format("No qualifying bean of type '%s' available", type));    // $NON-NLS-1$
    }

    public NoSuchBeanException(@Nonnull ResolvableType type, @Nullable String message) {
        super(String.format("No qualifying bean of type '%s' available: %s", type, message));    // $NON-NLS-1$
    }
}
