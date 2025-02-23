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


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;


/**
 * Utilities for Spring aop
 *
 * @author David Hsing
 * @see org.springframework.aop.support.AopUtils
 * @see org.springframework.aop.framework.AopProxyUtils
 * @see org.springframework.aop.config.AopNamespaceUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class AopUtilsWraps {
    @Nullable
    public static Constructor<?> getConstructor(@Nullable JoinPoint point) throws NoSuchMethodException, SecurityException {
        ConstructorSignature signature = getConstructorSignature(point);
        return (signature == null) ? null : point.getTarget().getClass().getConstructor(signature.getParameterTypes());
    }

    @Nullable
    public static Constructor<?> getConstructorQuietly(@Nullable JoinPoint point) {
        try {
            return getConstructor(point);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static ConstructorSignature getConstructorSignature(@Nullable JoinPoint point) {
        return (point != null && point.getSignature() instanceof ConstructorSignature alias) ? alias : null;
    }

    public static MethodSignature getMethodSignature(@Nullable JoinPoint point) {
        return (point != null && point.getSignature() instanceof MethodSignature alias) ? alias : null;
    }

    public static Object getParameter(@Nullable JoinPoint point, int index) {
        return getParameter(point, index, null);
    }

    public static Object getParameter(@Nullable JoinPoint point, int index, @Nullable Object defaultValue) {
        return (point == null || index < 0) ? defaultValue : ArrayUtils.get(point.getArgs(), index, defaultValue);
    }

    public static <T> T getParameterAs(@Nullable JoinPoint point, int index, @Nullable Class<T> expectedType) {
        return getParameterAs(point, index, expectedType, null);
    }

    public static <T> T getParameterAs(@Nullable JoinPoint point, int index, @Nullable Class<T> expectedType, @Nullable T defaultValue) {
        return ObjectUtilsWraps.castAs(getParameter(point, index), expectedType, defaultValue);
    }

    @Nullable
    public static Class<?> getTargetClass(@Nullable Object candidate) {
        return (candidate == null) ? null : AopUtils.getTargetClass(candidate);
    }

    @Nullable
    public static Method getTargetMethod(@Nullable JoinPoint point) throws NoSuchMethodException, SecurityException {
        MethodSignature signature = getMethodSignature(point);
        return (signature == null) ? null : point.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
    }

    @Nullable
    public static Method getTargetMethodQuietly(@Nullable JoinPoint point) {
        try {
            return getTargetMethod(point);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static boolean isTargetClassAssignable(@Nullable JoinPoint point, @Nullable Class<?> superclass) {
        return point != null && ClassUtilsWraps.isAssignable(superclass, point.getTarget().getClass());
    }

    public static boolean isTargetClassAssignable(@Nullable JoinPoint point, @Nullable String superclassName) {
        return isTargetClassAssignable(point, superclassName, null);
    }

    public static boolean isTargetClassAssignable(@Nullable JoinPoint point, @Nullable String superclassName, @Nullable ClassLoader classLoader) {
        return point != null && ClassUtilsWraps.isAssignable(superclassName, point.getTarget().getClass().getName(), classLoader);
    }
}
