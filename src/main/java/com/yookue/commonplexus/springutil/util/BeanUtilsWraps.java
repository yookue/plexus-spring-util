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


import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.PropertyPlainWraps;


/**
 * Utilities for {@link org.springframework.beans.BeanUtils}
 *
 * @author David Hsing
 * @see org.springframework.beans.BeanUtils
 * @see org.springframework.cglib.beans.BeanMap
 * @see org.springframework.cglib.beans.BeanCopier
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class BeanUtilsWraps {
    public static void copyProperties(@Nullable Object source, @Nullable Object target, @Nullable Collection<String> excludes) throws BeansException {
        copyProperties(source, target, true, excludes);
    }

    public static void copyProperties(@Nullable Object source, @Nullable Object target, boolean exclude, @Nullable String... fields) throws BeansException {
        copyProperties(source, target, exclude, ArrayUtilsWraps.asList(fields));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void copyProperties(@Nullable Object source, @Nullable Object target, boolean exclude, @Nullable Collection<String> fields) throws BeansException {
        if (source == null || target == null || (!exclude && CollectionUtils.isEmpty(fields))) {
            return;
        }
        String[] ignored;
        if (exclude) {
            ignored = (fields == null) ? null : fields.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        } else {
            Set<String> nested = ReflectionUtilsWraps.getNestedFieldNamesToSet(target.getClass());
            if (CollectionUtils.isEmpty(nested)) {
                return;
            }
            ignored = nested.stream().filter(element -> !CollectionPlainWraps.containsString(fields, element)).toArray(String[]::new);
        }
        BeanUtils.copyProperties(source, target, ignored);
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void copyPropertiesQuietly(@Nullable Object source, @Nullable Object target, @Nullable String... excludes) {
        if (source == null || target == null) {
            return;
        }
        try {
            BeanUtils.copyProperties(source, target, excludes);
        } catch (Exception ignored) {
        }
    }

    public static void copyPropertiesQuietly(@Nullable Object source, @Nullable Object target, @Nullable Collection<String> excludes) {
        copyPropertiesQuietly(source, target, true, excludes);
    }

    public static void copyPropertiesQuietly(@Nullable Object source, @Nullable Object target, boolean exclude, @Nullable String... fields) {
        copyPropertiesQuietly(source, target, exclude, ArrayUtilsWraps.asList(fields));
    }

    public static void copyPropertiesQuietly(@Nullable Object source, @Nullable Object target, boolean exclude, @Nullable Collection<String> fields) {
        try {
            copyProperties(source, target, exclude, fields);
        } catch (Exception ignored) {
        }
    }

    @Nullable
    public static Map<String, ?> beanToMap(@Nullable Object bean) {
        return beanToMap(bean, true, Collections.emptyList());
    }

    @Nullable
    public static Map<String, ?> beanToMap(@Nullable Object bean, @Nullable Collection<String> excludes) {
        return beanToMap(bean, true, excludes);
    }

    @Nullable
    public static Map<String, ?> beanToMap(@Nullable Object bean, boolean exclude, @Nullable String... fields) {
        return beanToMap(bean, true, ArrayUtilsWraps.asList(fields));
    }

    @Nullable
    public static Map<String, ?> beanToMap(@Nullable Object bean, boolean exclude, @Nullable Collection<String> fields) {
        if (bean == null || (!exclude && CollectionUtils.isEmpty(fields))) {
            return null;
        }
        BeanMap map = BeanMap.create(bean);
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> result = new LinkedHashMap<>(MapPlainWraps.size(map));
        for (Object key : map.keySet()) {
            String name = ObjectUtils.getDisplayString(key);
            if ((exclude && !CollectionPlainWraps.containsString(fields, name)) || (!exclude && CollectionPlainWraps.containsString(fields, name))) {
                result.put(name, map.get(key));
            }
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    public static Properties beanToProperties(@Nullable Object bean) {
        return PropertyPlainWraps.fromMap(beanToMap(bean));
    }

    @Nullable
    public static Properties beanToProperties(@Nullable Object bean, @Nullable Collection<String> excludes) {
        return PropertyPlainWraps.fromMap(beanToMap(bean, excludes));
    }

    @Nullable
    public static Properties beanToProperties(@Nullable Object bean, boolean exclude, @Nullable String... fields) {
        return PropertyPlainWraps.fromMap(beanToMap(bean, exclude, fields));
    }

    @Nullable
    public static Properties beanToProperties(@Nullable Object bean, boolean exclude, @Nullable Collection<String> fields) {
        return PropertyPlainWraps.fromMap(beanToMap(bean, exclude, fields));
    }

    public static void mapToBean(@Nullable Map<?, ?> map, @Nullable Object bean) throws BeansException {
        if (map == null || bean == null || map.isEmpty()) {
            return;
        }
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            setProperty(bean, ObjectUtils.getDisplayString(entry.getKey()), entry.getValue());
        }
    }

    public static void mapToBeanQuietly(@Nullable Map<String, ?> map, @Nullable Object bean) {
        try {
            mapToBean(map, bean);
        } catch (Exception ignored) {
        }
    }

    @Nullable
    public static <T> T instantiateClassQuietly(@Nullable Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            return BeanUtils.instantiateClass(clazz);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T instantiateClassQuietly(@Nullable Class<?> clazz, Class<T> assignableTo) {
        if (clazz == null || assignableTo == null) {
            return null;
        }
        try {
            return BeanUtils.instantiateClass(clazz, assignableTo);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> T instantiateClassQuietly(@Nullable Constructor<T> constructor, @Nullable Object... args) {
        if (constructor == null || ArrayUtils.isEmpty(args)) {
            return null;
        }
        try {
            return BeanUtils.instantiateClass(constructor, args);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * @see org.springframework.beans.BeanUtils#copyProperties(java.lang.Object, java.lang.Object)
     */
    public static boolean setProperty(@Nullable Object bean, @Nullable String property, @Nullable Object value) throws BeansException {
        if (bean == null || !StringUtils.hasText(property)) {
            return false;
        }
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(bean.getClass(), property);
        if (descriptor == null) {
            return false;
        }
        Method writeMethod = descriptor.getWriteMethod();
        if (writeMethod == null) {
            return false;
        }
        try {
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                writeMethod.setAccessible(true);
            }
            writeMethod.invoke(bean, value);
            return true;
        } catch (Exception ex) {
            throw new FatalBeanException("Could not copy property '" + property + "' from source to target", ex);
        }
    }

    public static boolean setPropertyQuietly(@Nullable Object bean, @Nullable String property, @Nullable Object value) {
        try {
            return setProperty(bean, property, value);
        } catch (Exception ignored) {
        }
        return false;
    }
}
