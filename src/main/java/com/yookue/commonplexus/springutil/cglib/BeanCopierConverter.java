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

package com.yookue.commonplexus.springutil.cglib;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;


/**
 * Interface for converting {@link com.yookue.commonplexus.springutil.cglib.EnhancedBeanCopier}
 *
 * @author David Hsing
 * @see org.springframework.cglib.core.Converter
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface BeanCopierConverter {
    /**
     * Returns the converted value that will be assigned to the target field
     *
     * @param sourceValue the source field value
     * @param targetType the target field class
     * @param targetSetter the setter method name of target field, represents as string
     * @param targetName the target field name
     * @param targetValue the original target field value
     *
     * @return the converted value that will be assigned to the target field
     */
    Object convert(@Nullable Object sourceValue, @Nonnull Class<?> targetType, @Nonnull Object targetSetter, @Nonnull String targetName, @Nullable Object targetValue);
}
