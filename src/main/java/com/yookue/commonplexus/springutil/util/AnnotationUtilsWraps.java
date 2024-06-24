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


import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.yookue.commonplexus.javaseutil.structure.BooleanDataStruct;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;


/**
 * Utilities for {@link org.springframework.core.annotation.AnnotationUtils}
 *
 * @author David Hsing
 * @see java.lang.reflect.AnnotatedElement
 * @see org.springframework.core.annotation.AnnotationUtils
 * @see org.springframework.core.annotation.AnnotatedElementUtils
 * @see org.apache.commons.lang3.AnnotationUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class AnnotationUtilsWraps {
    @SafeVarargs
    public static boolean allPresent(@Nullable AnnotatedElement element, @Nullable Class<? extends Annotation>... annotations) {
        return allPresent(element, ArrayUtilsWraps.asList(annotations));
    }

    public static boolean allPresent(@Nullable AnnotatedElement element, @Nullable Collection<Class<? extends Annotation>> annotations) {
        return element != null && !CollectionUtils.isEmpty(annotations) && annotations.stream().allMatch(annotation -> annotation != null && AnnotatedElementUtils.hasAnnotation(element, annotation));
    }

    @SafeVarargs
    public static boolean anyPresent(@Nullable AnnotatedElement element, @Nullable Class<? extends Annotation>... annotations) {
        return anyPresent(element, ArrayUtilsWraps.asList(annotations));
    }

    public static boolean anyPresent(@Nullable AnnotatedElement element, @Nullable Collection<Class<? extends Annotation>> annotations) {
        return element != null && !CollectionUtils.isEmpty(annotations) && annotations.stream().filter(Objects::nonNull).anyMatch(annotation -> AnnotatedElementUtils.hasAnnotation(element, annotation));
    }

    @SafeVarargs
    public static boolean allPresentAnywhere(@Nullable Method method, @Nullable Class<? extends Annotation>... annotations) {
        return allPresentAnywhere(method, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Returns whether all the annotations are present on the given method or the method owner class or not
     *
     * @param method the method to get annotations from
     * @param annotations the annotations to check
     *
     * @return whether all the annotations are present on the given method or the method owner class or not
     */
    public static boolean allPresentAnywhere(@Nullable Method method, @Nullable Collection<Class<? extends Annotation>> annotations) {
        return allPresentAnywhereEx(method, annotations).isSuccess();
    }

    @Nonnull
    @SafeVarargs
    public static BooleanDataStruct<MultiValueMap<Class<? extends Annotation>, ElementType>> allPresentAnywhereEx(@Nullable Method method, @Nullable Class<? extends Annotation>... annotations) {
        return allPresentAnywhereEx(method, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Returns a {@link com.yookue.commonplexus.javaseutil.structure.BooleanDataStruct} that contains all the annotations, on the given method or the method owner class
     * <p>
     * With annotation class as keys, {@link java.lang.annotation.ElementType} as values
     *
     * @param method the method to get annotations from
     * @param annotations the annotations to check
     *
     * @return a {@link com.yookue.commonplexus.javaseutil.structure.BooleanDataStruct} that contains all the annotations, on the given method or the method owner class
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static BooleanDataStruct<MultiValueMap<Class<? extends Annotation>, ElementType>> allPresentAnywhereEx(@Nullable Method method, @Nullable Collection<Class<? extends Annotation>> annotations) {
        BooleanDataStruct<MultiValueMap<Class<? extends Annotation>, ElementType>> result = new BooleanDataStruct<>();
        MultiValueMap<Class<? extends Annotation>, ElementType> mappings = getAnnotationsAnywhereEx(method, annotations);
        if (!CollectionUtils.isEmpty(mappings)) {
            result.setData(mappings);
            result.setSuccess(MapPlainWraps.containsAllKeys(mappings, annotations));
        }
        return result;
    }

    @SafeVarargs
    public static boolean anyPresentAnywhere(@Nullable Method method, @Nullable Class<? extends Annotation>... annotations) {
        return anyPresentAnywhere(method, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Returns whether any of the annotations are present on the given method or the method owner class or not
     *
     * @param method the method to get annotations from
     * @param annotations the annotations to check
     *
     * @return whether any of the annotations are present on the given method or the method owner class or not
     */
    public static boolean anyPresentAnywhere(@Nullable Method method, @Nullable Collection<Class<? extends Annotation>> annotations) {
        return anyPresentAnywhereEx(method, annotations).isSuccess();
    }

    @Nonnull
    @SafeVarargs
    public static BooleanDataStruct<MultiValueMap<Class<? extends Annotation>, ElementType>> anyPresentAnywhereEx(@Nullable Method method, @Nullable Class<? extends Annotation>... annotations) {
        return anyPresentAnywhereEx(method, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Returns a {@link com.yookue.commonplexus.javaseutil.structure.BooleanDataStruct} that contains any of the annotations, on the given method or the method owner class
     * <p>
     * With annotation class as keys, {@link java.lang.annotation.ElementType} as values
     *
     * @param method the method to get annotations from
     * @param annotations the annotations to check
     *
     * @return a {@link com.yookue.commonplexus.javaseutil.structure.BooleanDataStruct} that contains any of the annotations, on the given method or the method owner class
     */
    @Nonnull
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static BooleanDataStruct<MultiValueMap<Class<? extends Annotation>, ElementType>> anyPresentAnywhereEx(@Nullable Method method, @Nullable Collection<Class<? extends Annotation>> annotations) {
        BooleanDataStruct<MultiValueMap<Class<? extends Annotation>, ElementType>> result = new BooleanDataStruct<>();
        MultiValueMap<Class<? extends Annotation>, ElementType> mappings = getAnnotationsAnywhereEx(method, annotations);
        if (!CollectionUtils.isEmpty(mappings)) {
            result.setData(mappings);
            result.setSuccess(MapPlainWraps.containsAnyKeys(mappings, annotations));
        }
        return result;
    }

    @Nullable
    public static <A extends Annotation> A findAnnotation(@Nullable String className, @Nullable Class<A> annotation) {
        return findAnnotation(className, null, annotation);
    }

    @Nullable
    public static <A extends Annotation> A findAnnotation(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Class<A> annotation) {
        if (StringUtils.isBlank(className) || annotation == null) {
            return null;
        }
        Class<?> clazz = ClassUtilsWraps.forNameQuietly(className, classLoader);
        return (clazz == null) ? null : AnnotationUtils.findAnnotation(clazz, annotation);
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <A extends Annotation> A findAnnotationAnywhere(@Nullable Method method, @Nullable Class<A> annotation) {
        if (ObjectUtils.anyNull(method, annotation)) {
            return null;
        }
        A result = AnnotationUtils.findAnnotation(method, annotation);
        if (result == null) {
            result = AnnotationUtils.findAnnotation(method.getDeclaringClass(), annotation);
        }
        return result;
    }

    @Nullable
    public static <A extends Annotation> A getAnnotation(@Nullable String className, @Nullable Class<A> annotation) {
        return getAnnotation(className, null, annotation);
    }

    @Nullable
    public static <A extends Annotation> A getAnnotation(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Class<A> annotation) {
        if (StringUtils.isBlank(className) || annotation == null) {
            return null;
        }
        Class<?> clazz = ClassUtilsWraps.forNameQuietly(className, classLoader);
        return (clazz == null) ? null : AnnotationUtils.getAnnotation(clazz, annotation);
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <A extends Annotation> A getAnnotationAnywhere(@Nullable Method method, @Nullable Class<A> annotation) {
        if (ObjectUtils.anyNull(method, annotation)) {
            return null;
        }
        A result = AnnotationUtils.getAnnotation(method, annotation);
        if (result == null) {
            result = AnnotationUtils.getAnnotation(method.getDeclaringClass(), annotation);
        }
        return result;
    }

    @Nullable
    @SafeVarargs
    public static MultiValueMap<Class<? extends Annotation>, ElementType> getAnnotationsAnywhereEx(@Nullable Method method, @Nullable Class<? extends Annotation>... annotations) {
        return getAnnotationsAnywhereEx(method, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Returns the annotation map on the given method or on the method declared class
     *
     * @param method the method to get the annotation from
     * @param annotations the annotations to check
     *
     * @return the annotation map on the given method or on the method declared class
     */
    @Nullable
    public static MultiValueMap<Class<? extends Annotation>, ElementType> getAnnotationsAnywhereEx(@Nullable Method method, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (method == null || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        MultiValueMap<Class<? extends Annotation>, ElementType> result = new LinkedMultiValueMap<>(ArrayUtils.getLength(annotations));
        annotations.stream().filter(Objects::nonNull).forEach(annotation -> {
            if (AnnotatedElementUtils.hasAnnotation(method, annotation)) {
                result.add(annotation, ElementType.METHOD);
            }
            if (AnnotatedElementUtils.hasAnnotation(method.getDeclaringClass(), annotation)) {
                result.add(annotation, ElementType.TYPE);
            }
        });
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static Object getAnnotationAttribute(@Nullable AnnotatedElement element, @Nullable Class<? extends Annotation> annotation, @Nullable String attribute) {
        if (ObjectUtils.anyNull(element, annotation) || StringUtils.isBlank(attribute)) {
            return null;
        }
        return AnnotationUtils.getValue(AnnotationUtils.getAnnotation(element, annotation), attribute);
    }

    @Nullable
    public static <T> T getAnnotationAttributeAs(@Nullable AnnotatedElement element, @Nullable Class<? extends Annotation> annotation, @Nullable String attribute, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(element, annotation, expectedType) || StringUtils.isBlank(attribute)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(getAnnotationAttribute(element, annotation, attribute), expectedType);
    }

    public static String getAnnotationAttributeAsString(@Nullable AnnotatedElement element, @Nullable Class<? extends Annotation> annotation, @Nullable String attribute) {
        return getAnnotationAttributeAs(element, annotation, attribute, String.class);
    }
}
