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


import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;


/**
 * Utilities for {@link org.springframework.util.ClassUtils}
 *
 * @author David Hsing
 * @see org.springframework.util.ClassUtils
 * @see org.springframework.util.TypeUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ClassUtilsWraps {
    public static boolean allAssignable(@Nullable Class<?> subclass, @Nullable Class<?>... superclasses) {
        return allAssignable(subclass, ArrayUtilsWraps.asList(superclasses));
    }

    public static boolean allAssignable(@Nullable Class<?> subclass, @Nullable Collection<Class<?>> superclasses) {
        return subclass != null && !CollectionUtils.isEmpty(superclasses) && superclasses.stream().allMatch(superclass -> superclass != null && ClassUtils.isAssignable(superclass, subclass));
    }

    public static boolean anyAssignable(@Nullable Class<?> subclass, @Nullable Class<?>... superclasses) {
        return anyAssignable(subclass, ArrayUtilsWraps.asList(superclasses));
    }

    public static boolean anyAssignable(@Nullable Class<?> subclass, @Nullable Collection<Class<?>> superclasses) {
        return subclass != null && !CollectionUtils.isEmpty(superclasses) && superclasses.stream().filter(Objects::nonNull).anyMatch(superclass -> ClassUtils.isAssignable(superclass, subclass));
    }

    @SafeVarargs
    public static <T> boolean allAssignableValue(@Nullable Class<?> clazz, @Nullable T... values) {
        return allAssignableValue(clazz, ArrayUtilsWraps.asList(values));
    }

    public static <T> boolean allAssignableValue(@Nullable Class<?> clazz, @Nullable Collection<T> values) {
        return clazz != null && !CollectionUtils.isEmpty(values) && values.stream().allMatch(value -> value != null && ClassUtils.isAssignableValue(clazz, value));
    }

    @SafeVarargs
    public static <T> boolean anyAssignableValue(@Nullable Class<?> clazz, @Nullable T... values) {
        return anyAssignableValue(clazz, ArrayUtilsWraps.asList(values));
    }

    public static <T> boolean anyAssignableValue(@Nullable Class<?> clazz, @Nullable Collection<T> values) {
        return clazz != null && !CollectionUtils.isEmpty(values) && values.stream().filter(Objects::nonNull).anyMatch(value -> ClassUtils.isAssignableValue(clazz, value));
    }

    public static boolean allPresent(@Nullable ClassLoader classLoader, @Nullable String... classNames) {
        return allPresent(classLoader, ArrayUtilsWraps.asList(classNames));
    }

    public static boolean allPresent(@Nullable ClassLoader classLoader, @Nullable Collection<String> classNames) {
        return !CollectionUtils.isEmpty(classNames) && classNames.stream().allMatch(className -> ClassUtilsWraps.isPresent(className, classLoader));
    }

    public static boolean anyPresent(@Nullable ClassLoader classLoader, @Nullable String... classNames) {
        return anyPresent(classLoader, ArrayUtilsWraps.asList(classNames));
    }

    public static boolean anyPresent(@Nullable ClassLoader classLoader, @Nullable Collection<String> classNames) {
        return !CollectionUtils.isEmpty(classNames) && classNames.stream().filter(StringUtils::isNotBlank).anyMatch(className -> ClassUtils.isPresent(className, classLoader));
    }

    public static <T> boolean eachAnyAssignable(@Nullable Object one, @Nullable Object two) {
        return (one != null && ClassUtils.isAssignableValue(one.getClass(), two)) || (two != null && ClassUtils.isAssignableValue(two.getClass(), one));
    }

    @Nullable
    public static Class<?> forName(@Nullable String className) throws ClassNotFoundException, LinkageError {
        return forName(className, null);
    }

    /**
     * @see org.springframework.beans.BeanUtils#instantiateClass(Class)
     */
    @Nullable
    public static Class<?> forName(@Nullable String className, @Nullable ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
        return StringUtils.isBlank(className) ? null : ClassUtils.forName(className, classLoader);
    }

    @Nullable
    public static <T> Class<T> forNameAs(@Nullable String className, @Nullable Class<T> expectedType) throws ClassNotFoundException, LinkageError {
        return forNameAs(className, null, expectedType);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> Class<T> forNameAs(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Class<T> expectedType) throws ClassNotFoundException, LinkageError {
        if (StringUtils.isBlank(className) || expectedType == null) {
            return null;
        }
        Class<?> result = ClassUtils.forName(className, classLoader);
        return ClassUtils.isAssignable(expectedType, result) ? (Class<T>) result : null;
    }

    @Nullable
    public static Class<?> forNameQuietly(@Nullable String className) {
        return forNameQuietly(className, null);
    }

    @Nullable
    public static Class<?> forNameQuietly(@Nullable String className, @Nullable ClassLoader classLoader) {
        try {
            return forName(className, classLoader);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> Class<? extends T> forNameAsQuietly(@Nullable String className, @Nullable Class<T> expectedType) {
        return forNameAsQuietly(className, null, expectedType);
    }

    @Nullable
    public static <T> Class<T> forNameAsQuietly(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Class<T> expectedType) {
        try {
            return forNameAs(className, classLoader, expectedType);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static Class<?> getObjectClass(@Nullable Object object) {
        return (object == null) ? null : object.getClass();
    }

    public static String getQualifiedName(@Nullable Class<?> clazz) {
        return (clazz == null) ? null : ClassUtils.getQualifiedName(clazz);
    }

    public static String getQualifiedName(@Nullable Object object) {
        return (object == null) ? null : ClassUtils.getQualifiedName(object.getClass());
    }

    public static void ifPresent(@Nullable String className, @Nullable Consumer<String> action) {
        ifPresent(className, null, action);
    }

    public static void ifPresent(@Nullable String className, @Nullable Runnable action) {
        ifPresent(className, null, action);
    }

    public static void ifPresent(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Consumer<String> action) {
        ifPresentOrElse(className, classLoader, action, null);
    }

    public static void ifPresent(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Runnable action) {
        ifPresentOrElse(className, classLoader, action, null);
    }

    public static void ifPresentOrElse(@Nullable String className, @Nullable Consumer<String> presentAction, @Nullable Consumer<String> absentAction) {
        ifPresentOrElse(className, null, presentAction, absentAction);
    }

    public static void ifPresentOrElse(@Nullable String className, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        ifPresentOrElse(className, null, presentAction, absentAction);
    }

    public static void ifPresentOrElse(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Consumer<String> presentAction, @Nullable Consumer<String> absentAction) {
        if (isPresent(className, classLoader)) {
            if (presentAction != null) {
                presentAction.accept(className);
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(className);
            }
        }
    }

    public static void ifPresentOrElse(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isPresent(className, classLoader)) {
            if (presentAction != null) {
                presentAction.run();
            }
        } else {
            if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    public static void ifNotPresent(@Nullable String className, @Nullable Consumer<String> action) {
        ifNotPresent(className, null, action);
    }

    public static void ifNotPresent(@Nullable String className, @Nullable Runnable action) {
        ifNotPresent(className, null, action);
    }

    public static void ifNotPresent(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Consumer<String> action) {
        if (isNotPresent(className, classLoader) && action != null) {
            action.accept(className);
        }
    }

    public static void ifNotPresent(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Runnable action) {
        if (isNotPresent(className, classLoader) && action != null) {
            action.run();
        }
    }

    public static void ifPrimitive(@Nullable Class<?> clazz, @Nullable Consumer<Class<?>> action) {
        ifPrimitiveOrElse(clazz, action, null);
    }

    public static void ifPrimitive(@Nullable Class<?> clazz, @Nullable Runnable action) {
        ifPrimitiveOrElse(clazz, action, null);
    }

    public static void ifPrimitiveOrElse(@Nullable Class<?> clazz, @Nullable Consumer<Class<?>> presentAction, @Nullable Consumer<Class<?>> absentAction) {
        if (isPrimitive(clazz)) {
            if (presentAction != null) {
                presentAction.accept(clazz);
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(clazz);
            }
        }
    }

    public static void ifPrimitiveOrElse(@Nullable Class<?> clazz, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isPrimitive(clazz)) {
            if (presentAction != null) {
                presentAction.run();
            }
        } else {
            if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    public static void ifNotPrimitive(@Nullable Class<?> clazz, @Nullable Consumer<Class<?>> action) {
        if (isNotPrimitive(clazz) && action != null) {
            action.accept(clazz);
        }
    }

    public static void ifNotPrimitive(@Nullable Class<?> clazz, @Nullable Runnable action) {
        if (isNotPrimitive(clazz) && action != null) {
            action.run();
        }
    }

    public static boolean isPresent(@Nullable String className) {
        return isPresent(className, null);
    }

    public static boolean isPresent(@Nullable String className, @Nullable ClassLoader classLoader) {
        return StringUtils.isNotBlank(className) && ClassUtils.isPresent(className, classLoader);
    }

    public static boolean isNotPresent(@Nullable String className) {
        return !isPresent(className);
    }

    public static boolean isNotPresent(@Nullable String className, @Nullable ClassLoader classLoader) {
        return !isPresent(className, classLoader);
    }

    public static boolean isPrimitive(@Nullable Class<?> clazz) {
        return clazz != null && clazz.isPrimitive();
    }

    public static boolean isNotPrimitive(@Nullable Class<?> clazz) {
        return !isPrimitive(clazz);
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean isAssignable(@Nullable Class<?> superclass, @Nullable Class<?> subclass) {
        return ObjectUtils.allNotNull(superclass, subclass) && ClassUtils.isAssignable(superclass, subclass);
    }

    public static boolean isAssignable(@Nullable String superclassName, @Nullable String subclassName) {
        return isAssignable(superclassName, subclassName, null);
    }

    public static boolean isAssignable(@Nullable String superclassName, @Nullable String subclassName, @Nullable ClassLoader classLoader) {
        return StringUtils.isNoneBlank(superclassName, subclassName) && isAssignable(forNameQuietly(superclassName, classLoader), forNameQuietly(subclassName, classLoader));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean isAssignable(@Nullable Class<?>[] superclasses, @Nullable Class<?>[] subclasses) {
        if (ArrayUtils.isEmpty(superclasses) && ArrayUtils.isEmpty(subclasses)) {
            return true;
        }
        if (!ArrayUtils.isSameLength(superclasses, subclasses)) {
            return false;
        }
        for (int i = 0; i < superclasses.length; i++) {
            if (!isAssignable(superclasses[i], subclasses[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAssignableValue(@Nullable Class<?> superclass, @Nullable Object value) {
        return superclass != null && ClassUtils.isAssignableValue(superclass, value);
    }

    public static boolean isAssignableValue(@Nullable String superclassName, @Nullable Object value) {
        return isAssignableValue(superclassName, value, null);
    }

    public static boolean isAssignableValue(@Nullable String superclassName, @Nullable Object value, @Nullable ClassLoader classLoader) {
        Class<?> superclass = forNameQuietly(superclassName, classLoader);
        return superclass != null && ClassUtils.isAssignableValue(superclass, value);
    }

    public static boolean isInnerClass(@Nullable Class<?> clazz) {
        return clazz != null && ClassUtils.isInnerClass(clazz);
    }

    public static boolean isPrimitiveWrapper(@Nullable Class<?> clazz) {
        return clazz != null && ClassUtils.isPrimitiveWrapper(clazz);
    }

    public static boolean isPrimitiveOrWrapper(@Nullable Class<?> clazz) {
        return clazz != null && ClassUtils.isPrimitiveOrWrapper(clazz);
    }

    public static boolean isPrimitiveArray(@Nullable Class<?> clazz) {
        return clazz != null && ClassUtils.isPrimitiveArray(clazz);
    }

    public static boolean isPrimitiveWrapperArray(@Nullable Class<?> clazz) {
        return clazz != null && ClassUtils.isPrimitiveWrapperArray(clazz);
    }
}
