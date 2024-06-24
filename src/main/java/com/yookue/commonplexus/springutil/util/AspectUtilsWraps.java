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
import javax.annotation.Nullable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;


/**
 * Utilities for AspectJ
 *
 * @author David Hsing
 * @see org.springframework.aop.support.AopUtils
 * @see org.springframework.aop.framework.AopProxyUtils
 * @see org.springframework.aop.config.AopNamespaceUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class AspectUtilsWraps {
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
        return (point != null && point.getSignature() instanceof ConstructorSignature) ? (ConstructorSignature) point.getSignature() : null;
    }

    @Nullable
    public static Method getMethod(@Nullable JoinPoint point) throws NoSuchMethodException, SecurityException {
        MethodSignature signature = getMethodSignature(point);
        return (signature == null) ? null : point.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
    }

    @Nullable
    public static Method getMethodQuietly(@Nullable JoinPoint point) {
        try {
            return getMethod(point);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static MethodSignature getMethodSignature(@Nullable JoinPoint point) {
        return (point != null && point.getSignature() instanceof MethodSignature) ? (MethodSignature) point.getSignature() : null;
    }
}
