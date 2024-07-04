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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import com.yookue.commonplexus.springutil.cglib.EnhancedBeanCopier;


/**
 * Utilities for {@link com.yookue.commonplexus.springutil.cglib.EnhancedBeanCopier}
 *
 * @author David Hsing
 * @see org.springframework.cglib.beans.BeanCopier
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class BeanCopierWraps {
    /**
     * Copy properties from the {@code source} to the {@code target}, ignored the source properties which is null
     *
     * @param source the source object to read properties from
     * @param target the target object to write properties to
     */
    public static void copyIfNotNull(@Nullable Object source, @Nullable Object target) {
        if (source == null || target == null) {
            return;
        }
        EnhancedBeanCopier copier = EnhancedBeanCopier.create(source.getClass(), target.getClass(), true);
        copier.copy(source, target, (sourceValue, targetType, targetSetter, targetName, targetValue) -> sourceValue != null ? sourceValue : targetValue);
    }

    /**
     * Copy properties from the {@code source} to the {@code target}, ignored the source properties which is empty
     *
     * @param source the source object to read properties from
     * @param target the target object to write properties to
     */
    public static void copyIfNotEmpty(@Nullable Object source, @Nullable Object target) {
        if (source == null || target == null) {
            return;
        }
        EnhancedBeanCopier copier = EnhancedBeanCopier.create(source.getClass(), target.getClass(), true);
        copier.copy(source, target, (sourceValue, targetType, targetSetter, targetName, targetValue) -> ObjectUtils.isEmpty(sourceValue) ? targetValue : sourceValue);
    }

    /**
     * Copy properties from the {@code source} to the {@code target}, ignored the source properties which is blank
     *
     * @param source the source object to read properties from
     * @param target the target object to write properties to
     */
    public static void copyIfNotBlank(@Nullable Object source, @Nullable Object target) {
        if (source == null || target == null) {
            return;
        }
        EnhancedBeanCopier copier = EnhancedBeanCopier.create(source.getClass(), target.getClass(), true);
        copier.copy(source, target, (sourceValue, targetType, targetSetter, targetName, targetValue) -> (sourceValue instanceof String && StringUtils.hasText((String) sourceValue)) ? sourceValue : targetValue);
    }
}
