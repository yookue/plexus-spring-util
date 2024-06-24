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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.ClassUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;


/**
 * Utilities for {@link org.springframework.util.ReflectionUtils}
 *
 * @author David Hsing
 * @see org.springframework.util.ReflectionUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ReflectionUtilsWraps {
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void doWithLocalFields(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldCallback callback, @Nullable ReflectionUtils.FieldFilter filter) {
        if (ObjectUtils.anyNull(clazz, callback)) {
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        if (ArrayUtils.isEmpty(fields)) {
            return;
        }
        for (Field field : fields) {
            if (filter == null || filter.matches(field)) {
                try {
                    callback.doWith(field);
                } catch (IllegalAccessException ex) {
                    throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
                }
            }
        }
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void doWithLocalMethods(@Nullable Class<?> clazz, @Nullable ReflectionUtils.MethodCallback callback, @Nullable ReflectionUtils.MethodFilter filter) {
        if (ObjectUtils.anyNull(clazz, callback)) {
            return;
        }
        Method[] methods = clazz.getDeclaredMethods();
        if (ArrayUtils.isEmpty(methods)) {
            return;
        }
        for (Method method : methods) {
            if (filter == null || filter.matches(method)) {
                try {
                    callback.doWith(method);
                } catch (IllegalAccessException ex) {
                    throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + ex);
                }
            }
        }
    }

    @Nullable
    public static Field[] getDeclaredFields(@Nullable Class<?> clazz) {
        return getDeclaredFields(clazz, null);
    }

    @Nullable
    public static Field[] getDeclaredFields(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        List<Field> fields = getDeclaredFieldsToList(clazz, filter);
        return CollectionUtils.isEmpty(fields) ? null : fields.toArray(ArrayUtils.EMPTY_FIELD_ARRAY);
    }

    @Nullable
    public static List<Field> getDeclaredFieldsToList(@Nullable Class<?> clazz) {
        return getDeclaredFieldsToList(clazz, null);
    }

    @Nullable
    public static List<Field> getDeclaredFieldsToList(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        if (clazz == null) {
            return null;
        }
        List<Field> result = new ArrayList<>();
        doWithLocalFields(clazz, result::add, filter);
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    public static Set<Field> getDeclaredFieldsToSet(@Nullable Class<?> clazz) {
        return getDeclaredFieldsToSet(clazz, null);
    }

    @Nullable
    public static Set<Field> getDeclaredFieldsToSet(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        List<Field> fields = getDeclaredFieldsToList(clazz, filter);
        return CollectionUtils.isEmpty(fields) ? null : new LinkedHashSet<>(fields);
    }

    @Nullable
    public static String[] getDeclaredFieldNames(@Nullable Class<?> clazz) {
        return getDeclaredFieldNames(clazz, null);
    }

    @Nullable
    public static String[] getDeclaredFieldNames(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        List<String> names = getDeclaredFieldNamesToList(clazz, filter);
        return CollectionUtils.isEmpty(names) ? null : names.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    public static List<String> getDeclaredFieldNamesToList(@Nullable Class<?> clazz) {
        return getDeclaredFieldNamesToList(clazz, null);
    }

    @Nullable
    public static List<String> getDeclaredFieldNamesToList(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        List<String> result = new ArrayList<>();
        doWithLocalFields(clazz, element -> result.add(element.getName()), filter);
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    public static Set<String> getDeclaredFieldNamesToSet(@Nullable Class<?> clazz) {
        return getDeclaredFieldNamesToSet(clazz, null);
    }

    @Nullable
    public static Set<String> getDeclaredFieldNamesToSet(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        List<String> names = getDeclaredFieldNamesToList(clazz, filter);
        return CollectionUtils.isEmpty(names) ? null : new LinkedHashSet<>(names);
    }

    @Nullable
    public static Field[] getNestedFields(@Nullable Class<?> clazz) {
        return getNestedFields(clazz, null);
    }

    @Nullable
    public static Field[] getNestedFields(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        List<Field> fields = getNestedFieldsToList(clazz, filter);
        return CollectionUtils.isEmpty(fields) ? null : fields.toArray(ArrayUtils.EMPTY_FIELD_ARRAY);
    }

    @Nullable
    public static List<Field> getNestedFieldsToList(@Nullable Class<?> clazz) {
        return getNestedFieldsToList(clazz, null);
    }

    @Nullable
    public static List<Field> getNestedFieldsToList(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        if (clazz == null) {
            return null;
        }
        List<Field> result = new ArrayList<>();
        ReflectionUtils.doWithFields(clazz, result::add, filter);
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    public static Set<Field> getNestedFieldsToSet(@Nullable Class<?> clazz) {
        return getNestedFieldsToSet(clazz, null);
    }

    @Nullable
    public static Set<Field> getNestedFieldsToSet(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        List<Field> fields = getNestedFieldsToList(clazz, filter);
        return CollectionUtils.isEmpty(fields) ? null : new LinkedHashSet<>(fields);
    }

    @Nullable
    public static String[] getNestedFieldNames(@Nullable Class<?> clazz) {
        return getNestedFieldNames(clazz, null);
    }

    @Nullable
    public static String[] getNestedFieldNames(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        List<String> names = getNestedFieldNamesToList(clazz, filter);
        return CollectionUtils.isEmpty(names) ? null : names.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    public static List<String> getNestedFieldNamesToList(@Nullable Class<?> clazz) {
        return getNestedFieldNamesToList(clazz, null);
    }

    @Nullable
    public static List<String> getNestedFieldNamesToList(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        if (clazz == null) {
            return null;
        }
        List<String> result = new ArrayList<>();
        ReflectionUtils.doWithFields(clazz, element -> result.add(element.getName()), filter);
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    public static Set<String> getNestedFieldNamesToSet(@Nullable Class<?> clazz) {
        return getNestedFieldNamesToSet(clazz, null);
    }

    @Nullable
    public static Set<String> getNestedFieldNamesToSet(@Nullable Class<?> clazz, @Nullable ReflectionUtils.FieldFilter filter) {
        List<String> names = getNestedFieldNamesToList(clazz, filter);
        return CollectionUtils.isEmpty(names) ? null : new LinkedHashSet<>(names);
    }

    /**
     * Returns the found {@link java.lang.reflect.Field} with the given class and field name
     *
     * @param clazz the class to introspect
     * @param fieldName the name of the field
     *
     * @return the found {@link java.lang.reflect.Field} with the given class and field name
     */
    @Nullable
    public static Field findField(@Nullable Class<?> clazz, @Nullable String fieldName) {
        return (clazz == null || StringUtils.isBlank(fieldName)) ? null : ReflectionUtils.findField(clazz, fieldName);
    }

    /**
     * Returns the found {@link java.lang.reflect.Field} with the given class and field name/type
     *
     * @param clazz the class to introspect
     * @param fieldName the name of the field (could be {@code null} if {@code fieldType} is specified)
     * @param fieldType the type of the field (could be {@code null} if {@code fieldName} is specified)
     *
     * @return the found {@link java.lang.reflect.Field} with the given class and field name/type
     */
    @Nullable
    public static Field findField(@Nullable Class<?> clazz, @Nullable String fieldName, @Nullable Class<?> fieldType) {
        return (clazz == null || (StringUtils.isBlank(fieldName) && fieldType == null)) ? null : ReflectionUtils.findField(clazz, fieldName, fieldType);
    }

    /**
     * Returns the found accessible {@link java.lang.reflect.Field} with the given class and field name
     *
     * @param clazz the class to introspect
     * @param fieldName the name of the field
     *
     * @return the found accessible {@link java.lang.reflect.Field} with the given class and field name
     */
    @Nullable
    public static Field findFieldAccessible(@Nullable Class<?> clazz, @Nullable String fieldName) {
        return findFieldAccessible(clazz, fieldName, null);
    }

    /**
     * Returns the found accessible {@link java.lang.reflect.Field} with the given class and field name/type
     *
     * @param clazz the class to introspect
     * @param fieldName the name of the field (could be {@code null} if {@code fieldType} is specified)
     * @param fieldType the type of the field (could be {@code null} if {@code fieldName} is specified)
     *
     * @return the found accessible {@link java.lang.reflect.Field} with the given class and field name/type
     */
    @Nullable
    public static Field findFieldAccessible(@Nullable Class<?> clazz, @Nullable String fieldName, @Nullable Class<?> fieldType) {
        Field field = findField(clazz, fieldName, fieldType);
        makeAccessible(field);
        return field;
    }

    /**
     * Returns the found {@link java.lang.reflect.Method} with the given class and method name
     *
     * @param clazz the class to introspect
     * @param methodName the name of the method
     *
     * @return the found {@link java.lang.reflect.Method} with the given class and method name
     */
    @Nullable
    public static Method findMethod(@Nullable Class<?> clazz, @Nullable String methodName) {
        return (clazz == null || StringUtils.isBlank(methodName)) ? null : ReflectionUtils.findMethod(clazz, methodName);
    }

    /**
     * Returns the found {@link java.lang.reflect.Method} with the given class and method name/parameter types
     *
     * @param clazz the class to introspect
     * @param methodName the name of the method
     * @param paramTypes the parameter types of the method (could be {@code null} to indicate any signature)
     *
     * @return the found {@link java.lang.reflect.Method} with the given class and method name/parameter types
     */
    @Nullable
    public static Method findMethod(@Nullable Class<?> clazz, String methodName, @Nullable Class<?>... paramTypes) {
        return (clazz == null || StringUtils.isBlank(methodName)) ? null : ReflectionUtils.findMethod(clazz, methodName, paramTypes);
    }

    /**
     * Returns the found {@link java.lang.reflect.Method} with the given class and method name/assignable parameter types
     *
     * @param clazz the class to introspect
     * @param methodName the name of the method
     * @param paramTypes the assignable parameter types of the method (could be {@code null} to indicate any signature)
     *
     * @return the found {@link java.lang.reflect.Method} with the given class and method name/assignable parameter types
     */
    @Nullable
    public static Method findMethodAssignable(@Nullable Class<?> clazz, String methodName, @Nullable Class<?>... paramTypes) {
        if (clazz == null || StringUtils.isBlank(methodName)) {
            return null;
        }
        Method result = ReflectionUtils.findMethod(clazz, methodName, (Class<?>[]) null);
        return (result != null && ClassUtils.isAssignable(paramTypes, result.getParameterTypes())) ? result : null;
    }

    /**
     * Returns the found accessible {@link java.lang.reflect.Method} with the given class and method name
     *
     * @param clazz the class to introspect
     * @param methodName the name of the method
     *
     * @return the found accessible {@link java.lang.reflect.Method} with the given class and method name
     */
    @Nullable
    public static Method findMethodAccessible(@Nullable Class<?> clazz, @Nullable String methodName) {
        return findMethodAccessible(clazz, methodName, ArrayUtils.EMPTY_CLASS_ARRAY);
    }

    /**
     * Returns the found accessible {@link java.lang.reflect.Method} with the given class and method name/parameter types
     *
     * @param clazz the class to introspect
     * @param methodName the name of the method
     * @param paramTypes the parameter types of the method (could be {@code null} to indicate any signature)
     *
     * @return the found accessible {@link java.lang.reflect.Method} with the given class and method name/parameter types
     */
    @Nullable
    public static Method findMethodAccessible(@Nullable Class<?> clazz, String methodName, @Nullable Class<?>... paramTypes) {
        Method method = findMethod(clazz, methodName, paramTypes);
        makeAccessible(method);
        return method;
    }

    @Nullable
    public static Object getField(@Nullable Field field, @Nullable Object target) {
        return getField(field, false, target);
    }

    @Nullable
    public static Object getField(@Nullable Field field, boolean makeAccessible, @Nullable Object target) {
        if (field == null) {
            return null;
        }
        try {
            if (makeAccessible) {
                ReflectionUtils.makeAccessible(field);
            }
            return ReflectionUtils.getField(field, target);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static Object getField(@Nullable Class<?> clazz, @Nullable String fieldName, boolean makeAccessible, @Nullable Object target) {
        Field field = makeAccessible ? findFieldAccessible(clazz, fieldName) : findField(clazz, fieldName);
        return getField(field, target);
    }

    @Nullable
    public static Object getField(@Nullable Class<?> clazz, @Nullable String fieldName, @Nullable Class<?> fieldType, boolean makeAccessible, @Nullable Object target) {
        Field field = makeAccessible ? findFieldAccessible(clazz, fieldName, fieldType) : findField(clazz, fieldName, fieldType);
        return getField(field, target);
    }

    @Nullable
    public static <T> T getFieldAs(@Nullable Field field, @Nullable Object target, @Nullable Class<T> expectedType) {
        return getFieldAs(field, false, target, expectedType);
    }

    @Nullable
    public static <T> T getFieldAs(@Nullable Field field, boolean makeAccessible, @Nullable Object target, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(field, expectedType)) {
            return null;
        }
        if (makeAccessible) {
            makeAccessible(field);
        }
        return ObjectUtilsWraps.castAs(getField(field, target), expectedType);
    }

    @Nullable
    public static <T> T getFieldAs(@Nullable Class<?> clazz, @Nullable String fieldName, boolean makeAccessible, @Nullable Object target, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(clazz, expectedType) || StringUtils.isBlank(fieldName)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(getField(clazz, fieldName, makeAccessible, target), expectedType);
    }

    @Nullable
    public static <T> T getFieldAs(@Nullable Class<?> clazz, @Nullable String fieldName, @Nullable Class<?> fieldType, boolean makeAccessible, @Nullable Object target, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(clazz, expectedType) || StringUtils.isBlank(fieldName)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(getField(clazz, fieldName, fieldType, makeAccessible, target), expectedType);
    }

    @Nullable
    @SafeVarargs
    public static Field[] getFieldsWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getFieldsWithAllAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static Field[] getFieldsWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Field> fields = getFieldsWithAllAnnotationsToList(clazz, annotations);
        return CollectionUtils.isEmpty(fields) ? null : fields.toArray(ArrayUtils.EMPTY_FIELD_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static List<Field> getFieldsWithAllAnnotationsToList(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getFieldsWithAllAnnotationsToList(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static List<Field> getFieldsWithAllAnnotationsToList(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (clazz == null || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        List<Field> result = new ArrayList<>();
        ReflectionUtils.doWithFields(clazz, result::add, element -> AnnotationUtilsWraps.allPresent(element, annotations));
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    @SafeVarargs
    public static Field[] getFieldsWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getFieldsWithAnyAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static Field[] getFieldsWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Field> fields = getFieldsWithAnyAnnotationsToList(clazz, annotations);
        return CollectionUtils.isEmpty(fields) ? null : fields.toArray(ArrayUtils.EMPTY_FIELD_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static List<Field> getFieldsWithAnyAnnotationsToList(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getFieldsWithAnyAnnotationsToList(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static List<Field> getFieldsWithAnyAnnotationsToList(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (clazz == null || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        List<Field> result = new ArrayList<>();
        ReflectionUtils.doWithFields(clazz, result::add, element -> AnnotationUtilsWraps.anyPresent(element, annotations));
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    @SafeVarargs
    public static String[] getFieldNamesWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getFieldNamesWithAllAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static String[] getFieldNamesWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<String> names = getFieldNamesWithAllAnnotationsToList(clazz, annotations);
        return CollectionUtils.isEmpty(names) ? null : names.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static List<String> getFieldNamesWithAllAnnotationsToList(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getFieldNamesWithAllAnnotationsToList(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static List<String> getFieldNamesWithAllAnnotationsToList(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Field> fields = getFieldsWithAllAnnotationsToList(clazz, annotations);
        return CollectionUtils.isEmpty(fields) ? null : fields.stream().map(Field::getName).collect(Collectors.toList());
    }

    @Nullable
    @SafeVarargs
    public static Set<String> getFieldNamesWithAllAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getFieldNamesWithAllAnnotationsToSet(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static Set<String> getFieldNamesWithAllAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<String> names = getFieldNamesWithAllAnnotationsToList(clazz, annotations);
        return CollectionUtils.isEmpty(names) ? null : new LinkedHashSet<>(names);
    }

    @Nullable
    @SafeVarargs
    public static String[] getFieldNamesWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getFieldNamesWithAnyAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static String[] getFieldNamesWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        Set<String> names = getFieldNamesWithAnyAnnotationsToSet(clazz, annotations);
        return CollectionUtils.isEmpty(names) ? null : names.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static List<String> getFieldNamesWithAnyAnnotationsToList(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getFieldNamesWithAnyAnnotationsToList(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static List<String> getFieldNamesWithAnyAnnotationsToList(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Field> fields = getFieldsWithAnyAnnotationsToList(clazz, annotations);
        return CollectionUtils.isEmpty(fields) ? null : fields.stream().map(Field::getName).collect(Collectors.toList());
    }

    @Nullable
    @SafeVarargs
    public static Set<String> getFieldNamesWithAnyAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getFieldNamesWithAnyAnnotationsToSet(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static Set<String> getFieldNamesWithAnyAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<String> names = getFieldNamesWithAnyAnnotationsToList(clazz, annotations);
        return CollectionUtils.isEmpty(names) ? null : new LinkedHashSet<>(names);
    }

    @Nullable
    @SafeVarargs
    public static Method[] getMethodsWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodsWithAllAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static Method[] getMethodsWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Method> methods = getMethodsWithAllAnnotationsToList(clazz, annotations);
        return CollectionUtils.isEmpty(methods) ? null : methods.toArray(ArrayUtils.EMPTY_METHOD_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static List<Method> getMethodsWithAllAnnotationsToList(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodsWithAllAnnotationsToList(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static List<Method> getMethodsWithAllAnnotationsToList(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (clazz == null || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        List<Method> result = new ArrayList<>();
        ReflectionUtils.doWithMethods(clazz, result::add, element -> AnnotationUtilsWraps.allPresent(element, annotations));
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    @SafeVarargs
    public static Method[] getMethodsWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodsWithAnyAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static Method[] getMethodsWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Method> methods = getMethodsWithAnyAnnotationsToList(clazz, annotations);
        return CollectionUtils.isEmpty(methods) ? null : methods.toArray(ArrayUtils.EMPTY_METHOD_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static List<Method> getMethodsWithAnyAnnotationsToList(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodsWithAnyAnnotationsToList(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static List<Method> getMethodsWithAnyAnnotationsToList(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (clazz == null || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        List<Method> result = new ArrayList<>();
        ReflectionUtils.doWithMethods(clazz, result::add, element -> AnnotationUtilsWraps.anyPresent(element, annotations));
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    @SafeVarargs
    public static String[] getMethodNamesWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodNamesWithAllAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static String[] getMethodNamesWithAllAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        Set<String> names = getMethodNamesWithAllAnnotationsToSet(clazz, annotations);
        return CollectionUtils.isEmpty(names) ? null : names.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static Set<String> getMethodNamesWithAllAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodNamesWithAllAnnotationsToSet(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static Set<String> getMethodNamesWithAllAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Method> methods = getMethodsWithAllAnnotationsToList(clazz, annotations);
        return CollectionUtils.isEmpty(methods) ? null : methods.stream().map(Method::getName).collect(Collectors.toSet());
    }

    @Nullable
    @SafeVarargs
    public static String[] getMethodNamesWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodNamesWithAnyAnnotations(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static String[] getMethodNamesWithAnyAnnotations(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        Set<String> names = getMethodNamesWithAnyAnnotationsToSet(clazz, annotations);
        return CollectionUtils.isEmpty(names) ? null : names.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static Set<String> getMethodNamesWithAnyAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Class<? extends Annotation>... annotations) {
        return getMethodNamesWithAnyAnnotationsToSet(clazz, ArrayUtilsWraps.asList(annotations));
    }

    @Nullable
    public static Set<String> getMethodNamesWithAnyAnnotationsToSet(@Nullable Class<?> clazz, @Nullable Collection<Class<? extends Annotation>> annotations) {
        List<Method> methods = getMethodsWithAnyAnnotationsToList(clazz, annotations);
        return CollectionUtils.isEmpty(methods) ? null : methods.stream().map(Method::getName).collect(Collectors.toSet());
    }

    @Nullable
    public static Object invokeMethod(@Nullable Method method, @Nullable Object target) {
        return invokeMethod(method, target, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    @Nullable
    public static Object invokeMethod(@Nullable Method method, @Nullable Object target, @Nullable Object... args) {
        if (method == null) {
            return null;
        }
        try {
            return ReflectionUtils.invokeMethod(method, target, args);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static Object invokeMethod(@Nullable Class<?> clazz, @Nullable String methodName, boolean makeAccessible, @Nullable Object target) {
        Method method = makeAccessible ? findMethodAccessible(clazz, methodName) : findMethod(clazz, methodName);
        return invokeMethod(method, target);
    }

    @Nullable
    public static Object invokeMethod(@Nullable Class<?> clazz, @Nullable String methodName, boolean makeAccessible, @Nullable Object target, @Nullable Object... args) {
        Class<?>[] paramTypes = ClassUtilsWraps.getObjectClasses(args);
        Method method = makeAccessible ? findMethodAccessible(clazz, methodName, paramTypes) : findMethod(clazz, methodName, paramTypes);
        return invokeMethod(method, target, args);
    }

    @Nullable
    public static Object invokeMethodAssignable(@Nullable Class<?> clazz, @Nullable String methodName, boolean makeAccessible, @Nullable Object target, @Nullable Object... args) {
        Class<?>[] paramTypes = ClassUtilsWraps.getObjectClasses(args);
        Method method = findMethodAssignable(clazz, methodName, paramTypes);
        if (method == null) {
            return null;
        }
        if (makeAccessible) {
            makeAccessible(method);
        }
        return invokeMethod(method, target, args);
    }

    @Nullable
    public static <T> T invokeMethodAs(@Nullable Method method, @Nullable Object target, @Nullable Class<T> expectedType) {
        return invokeMethodAs(method, target, expectedType, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    @Nullable
    public static <T> T invokeMethodAs(@Nullable Method method, @Nullable Object target, @Nullable Class<T> expectedType, @Nullable Object... args) {
        return ObjectUtils.anyNull(method, expectedType) ? null : ObjectUtilsWraps.castAs(invokeMethod(method, target, args), expectedType);
    }

    @Nullable
    public static <T> T invokeMethodAs(@Nullable Class<?> clazz, @Nullable String methodName, boolean makeAccessible, @Nullable Object target, @Nullable Class<T> expectedType) {
        return invokeMethodAs(clazz, methodName, false, target, expectedType, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    @Nullable
    public static <T> T invokeMethodAs(@Nullable Class<?> clazz, @Nullable String methodName, boolean makeAccessible, @Nullable Object target, @Nullable Class<T> expectedType, @Nullable Object... args) {
        if (ObjectUtils.anyNull(clazz, expectedType) || StringUtils.isBlank(methodName)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(invokeMethod(clazz, methodName, makeAccessible, target, args), expectedType);
    }

    @Nullable
    public static <T> T invokeMethodAssignableAs(@Nullable Class<?> clazz, @Nullable String methodName, boolean makeAccessible, @Nullable Object target, @Nullable Class<T> expectedType, @Nullable Object... args) {
        if (ObjectUtils.anyNull(clazz, expectedType) || StringUtils.isBlank(methodName)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(invokeMethodAssignable(clazz, methodName, makeAccessible, target, args), expectedType);
    }

    public static boolean isUserDefined(@Nullable Method method) {
        return method != null && !method.isBridge() && !method.isSynthetic() && method.getDeclaringClass() != Object.class;
    }

    public static <A, B extends A> boolean isUserOverride(@Nullable Class<A> superclass, @Nullable Class<B> subclass, @Nullable String methodName) {
        return isUserOverride(superclass, subclass, methodName, ArrayUtils.EMPTY_CLASS_ARRAY);
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <A, B extends A> boolean isUserOverride(@Nullable Class<A> superclass, @Nullable Class<B> subclass, @Nullable String methodName, @Nullable Class<?>... paramTypes) {
        if (ObjectUtils.anyNull(superclass, subclass) || superclass == subclass || StringUtils.isBlank(methodName)) {
            return false;
        }
        Method superMethod = findMethod(superclass, methodName, paramTypes), subMethod = findMethod(subclass, methodName, paramTypes);
        return ObjectUtils.allNotNull(superclass, subclass) && superMethod.getDeclaringClass() != subMethod.getDeclaringClass();
    }

    public static void makeAccessible(@Nullable Constructor<?> constructor) {
        if (constructor == null) {
            return;
        }
        try {
            ReflectionUtils.makeAccessible(constructor);
        } catch (Exception ignored) {
        }
    }

    public static void makeAccessible(@Nullable Field field) {
        if (field == null) {
            return;
        }
        try {
            ReflectionUtils.makeAccessible(field);
        } catch (Exception ignored) {
        }
    }

    public static void makeAccessible(@Nullable Method method) {
        if (method == null) {
            return;
        }
        try {
            ReflectionUtils.makeAccessible(method);
        } catch (Exception ignored) {
        }
    }

    public static void setField(@Nullable Field field, @Nullable Object target, @Nullable Object value) {
        if (field == null) {
            return;
        }
        try {
            ReflectionUtils.setField(field, target, value);
        } catch (Exception ignored) {
        }
    }

    public static void setField(@Nullable Class<?> clazz, @Nullable String fieldName, boolean makeAccessible, @Nullable Object target, @Nullable Object value) {
        Field field = makeAccessible ? findFieldAccessible(clazz, fieldName) : findField(clazz, fieldName);
        setField(field, target, value);
    }

    public static void setField(@Nullable Class<?> clazz, @Nullable String fieldName, @Nullable Class<?> fieldType, boolean makeAccessible, @Nullable Object target, @Nullable Object value) {
        Field field = makeAccessible ? findFieldAccessible(clazz, fieldName, fieldType) : findField(clazz, fieldName, fieldType);
        setField(field, target, value);
    }
}
