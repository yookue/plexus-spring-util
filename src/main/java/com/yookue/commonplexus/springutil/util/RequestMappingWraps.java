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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.yookue.commonplexus.javaseutil.structure.BooleanDataStruct;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.javaseutil.util.FilenamePlainWraps;
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;
import com.yookue.commonplexus.springutil.constant.AntPathConst;
import com.yookue.commonplexus.springutil.structure.RequestMappingStruct;


/**
 * Utilities for {@link org.springframework.web.servlet.mvc.method.RequestMappingInfo}
 *
 * @author David Hsing
 * @see org.springframework.web.servlet.mvc.method.RequestMappingInfo
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see org.springframework.web.bind.annotation.GetMapping
 * @see org.springframework.web.bind.annotation.PostMapping
 * @see org.springframework.web.bind.annotation.PatchMapping
 * @see org.springframework.web.bind.annotation.PutMapping
 * @see org.springframework.web.bind.annotation.DeleteMapping
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class RequestMappingWraps {
    @Nullable
    public static String[] getAllMappingPaths(@Nullable Class<?> controller) {
        Set<String> result = getAllMappingPathsToSet(controller);
        return CollectionUtils.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Return all the mapping paths on a controller, without the ending slash and stars (/**)
     *
     * @param controller the controller class
     *
     * @return all the mapping paths on a controller, without the ending slash and stars (/**)
     */
    @Nullable
    public static Set<String> getAllMappingPathsToSet(@Nullable Class<?> controller) {
        if (!StereotypeBeanWraps.isMergedController(controller)) {
            return null;
        }
        String[] requestMappings = getRequestMappingPaths(controller);
        String[] getMappings = getGetMappingPaths(controller);
        String[] postMappings = getPostMappingPaths(controller);
        String[] patchMappings = getPatchMappingPaths(controller);
        String[] putMappings = getPutMappingPaths(controller);
        String[] deleteMappings = getDeleteMappingPaths(controller);
        Set<String> result = new LinkedHashSet<>();
        CollectionPlainWraps.addAll(result, requestMappings, getMappings, postMappings, patchMappings, putMappings, deleteMappings);
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    /**
     * Return request mapping paths that annotated on a controller
     *
     * @param controller the controller class
     *
     * @return request mapping paths that annotated on a controller
     */
    @Nullable
    public static String[] getRequestMappingPaths(@Nullable Class<?> controller) {
        if (!StereotypeBeanWraps.isMergedController(controller)) {
            return null;
        }
        RequestMapping mapping = AnnotationUtils.findAnnotation(controller, RequestMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    /**
     * Return request mapping paths that annotated on a controller method
     *
     * @param method the method of controller class
     *
     * @return request mapping paths that annotated on a controller method
     */
    @Nullable
    public static String[] getRequestMappingPaths(@Nullable Method method) {
        if (method == null) {
            return null;
        }
        RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    /**
     * Return get mapping paths that annotated on a controller
     *
     * @param controller the controller class
     *
     * @return get mapping paths that annotated on a controller
     */
    @Nullable
    public static String[] getGetMappingPaths(@Nullable Class<?> controller) {
        if (!StereotypeBeanWraps.isMergedController(controller)) {
            return null;
        }
        GetMapping mapping = AnnotationUtils.findAnnotation(controller, GetMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    /**
     * Return get mapping paths that annotated on a controller method
     *
     * @param method the method of controller class
     *
     * @return get mapping paths that annotated on a controller method
     */
    @Nullable
    public static String[] getGetMappingPaths(@Nullable Method method) {
        if (method == null) {
            return null;
        }
        GetMapping mapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    /**
     * Return post mapping paths that annotated on a controller
     *
     * @param controller the controller class
     *
     * @return post mapping paths that annotated on a controller
     */
    @Nullable
    public static String[] getPostMappingPaths(@Nullable Class<?> controller) {
        if (!StereotypeBeanWraps.isMergedController(controller)) {
            return null;
        }
        PostMapping mapping = AnnotationUtils.findAnnotation(controller, PostMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    /**
     * Return post mapping paths that annotated on a controller method
     *
     * @param method the method of controller class
     *
     * @return post mapping paths that annotated on a controller method
     */
    @Nullable
    public static String[] getPostMappingPaths(@Nullable Method method) {
        if (method == null) {
            return null;
        }
        PostMapping mapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    /**
     * Return patch mapping paths that annotated on a controller
     *
     * @param controller the controller class
     *
     * @return patch mapping paths that annotated on a controller
     */
    @Nullable
    public static String[] getPatchMappingPaths(@Nullable Class<?> controller) {
        if (!StereotypeBeanWraps.isMergedController(controller)) {
            return null;
        }
        PatchMapping mapping = AnnotationUtils.findAnnotation(controller, PatchMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    /**
     * Return patch mapping paths that annotated on a controller method
     *
     * @param method the method of controller class
     *
     * @return patch mapping paths that annotated on a controller method
     */
    @Nullable
    public static String[] getPatchMappingPaths(@Nullable Method method) {
        if (method == null) {
            return null;
        }
        PatchMapping mapping = AnnotationUtils.findAnnotation(method, PatchMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    /**
     * Return put mapping paths that annotated on a controller
     *
     * @param controller the controller class
     *
     * @return put mapping paths that annotated on a controller
     */
    @Nullable
    public static String[] getPutMappingPaths(@Nullable Class<?> controller) {
        if (!StereotypeBeanWraps.isMergedController(controller)) {
            return null;
        }
        PutMapping mapping = AnnotationUtils.findAnnotation(controller, PutMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    /**
     * Return put mapping paths that annotated on a controller method
     *
     * @param method the method of controller class
     *
     * @return put mapping paths that annotated on a controller method
     */
    @Nullable
    public static String[] getPutMappingPaths(@Nullable Method method) {
        if (method == null) {
            return null;
        }
        PutMapping mapping = AnnotationUtils.findAnnotation(method, PutMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    /**
     * Return delete mapping paths that annotated on a controller
     *
     * @param controller the controller class
     *
     * @return delete mapping paths that annotated on a controller
     */
    @Nullable
    public static String[] getDeleteMappingPaths(@Nullable Class<?> controller) {
        if (!StereotypeBeanWraps.isMergedController(controller)) {
            return null;
        }
        DeleteMapping mapping = AnnotationUtils.findAnnotation(controller, DeleteMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    /**
     * Return delete mapping paths that annotated on a controller method
     *
     * @param method the method of controller class
     *
     * @return delete mapping paths that annotated on a controller method
     */
    @Nullable
    public static String[] getDeleteMappingPaths(@Nullable Method method) {
        if (method == null) {
            return null;
        }
        DeleteMapping mapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);
        return (mapping == null) ? null : mapping.path();
    }

    public static Map<RequestMappingInfo, HandlerMethod> getRequestMappingMethods(@Nullable ApplicationContext context) {
        return getRequestMappingMethods(context, null, null, false);
    }

    /**
     * Return request mapping info with handle methods in the given application context
     *
     * @param context the application context
     * @param filter the predicate to filter request mapping info, may be {@code null}
     *
     * @return request mapping info with handle methods in the given application context
     */
    public static Map<RequestMappingInfo, HandlerMethod> getRequestMappingMethods(@Nullable ApplicationContext context, @Nullable BiPredicate<RequestMappingInfo, HandlerMethod> filter) {
        return getRequestMappingMethods(context, filter, null, false);
    }

    /**
     * Return request mapping info with handle methods in the given application context
     *
     * @param context the application context
     * @param filter the predicate to filter request mapping info, may be {@code null}
     * @param controller a controller class, may be {@code null}
     *
     * @return request mapping info with handle methods in the given application context
     */
    public static Map<RequestMappingInfo, HandlerMethod> getRequestMappingMethods(@Nullable ApplicationContext context, @Nullable BiPredicate<RequestMappingInfo, HandlerMethod> filter, @Nullable Class<?> controller) {
        return getRequestMappingMethods(context, filter, controller, false);
    }

    /**
     * Return request mapping info with handle methods in the given application context
     *
     * @param context the application context
     * @param filter the predicate to filter request mapping info, may be {@code null}
     * @param controller a controller class, may be {@code null}
     * @param recursive whether to search subclasses of {@code controller} or not
     *
     * @return request mapping info with handle methods in the given application context
     *
     * @see "org.springframework.boot.actuate.endpoint.web.servlet.ControllerEndpointHandlerMapping"
     */
    @Nullable
    public static Map<RequestMappingInfo, HandlerMethod> getRequestMappingMethods(@Nullable ApplicationContext context, @Nullable BiPredicate<RequestMappingInfo, HandlerMethod> filter, @Nullable Class<?> controller, boolean recursive) {
        if (context == null || (controller != null && !StereotypeBeanWraps.isMergedController(controller))) {
            return null;
        }
        RequestMappingHandlerMapping bean = BeanFactoryWraps.getBean(context, RequestMappingHandlerMapping.class);
        if (bean == null) {
            return null;
        }
        Map<RequestMappingInfo, HandlerMethod> origin = bean.getHandlerMethods();
        if (CollectionUtils.isEmpty(origin)) {
            return null;
        }
        if (ObjectUtils.allNull(filter, controller)) {
            return origin;
        }
        Map<RequestMappingInfo, HandlerMethod> cloned = new LinkedHashMap<>(origin.size());
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : origin.entrySet()) {
            Class<?> handlerClass = entry.getValue().getBeanType();
            boolean matchClass = (controller == null) || (recursive ? ClassUtils.isAssignable(controller, handlerClass) : handlerClass == controller);
            if (matchClass && (filter == null || filter.test(entry.getKey(), entry.getValue()))) {
                cloned.put(entry.getKey(), entry.getValue());
            }
        }
        return CollectionUtils.isEmpty(cloned) ? null : Collections.unmodifiableMap(cloned);
    }

    public static Map<RequestMappingInfo, HandlerMethod> getRequestMappingMethods(@Nullable ApplicationContext context, @Nullable Class<?> controller) {
        return getRequestMappingMethods(context, null, controller, false);
    }

    @Nullable
    public static String[] getRequestMappingPatterns(@Nullable RequestMappingStruct... structs) {
        return getRequestMappingPatterns(ArrayUtilsWraps.asList(structs));
    }

    @Nullable
    public static String[] getRequestMappingPatterns(@Nullable Collection<RequestMappingStruct> structs) {
        Set<String> result = getRequestMappingPatternsToSet(structs);
        return CollectionUtils.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    public static Set<String> getRequestMappingPatternsToSet(@Nullable RequestMappingStruct... structs) {
        return getRequestMappingPatternsToSet(ArrayUtilsWraps.asList(structs));
    }

    /**
     * Return extracted patterns of request mappings
     *
     * @param structs a collection of request mappings
     *
     * @return extracted patterns of request mappings
     */
    @Nullable
    public static Set<String> getRequestMappingPatternsToSet(@Nullable Collection<RequestMappingStruct> structs) {
        if (CollectionUtils.isEmpty(structs)) {
            return null;
        }
        Set<String> result = new LinkedHashSet<>(structs.size());
        for (RequestMappingStruct struct : structs) {
            MultiValueMap<Class<? extends Annotation>, ElementType> mappings = struct.getMappings();
            if (struct.getMappingInfo() == null || CollectionUtils.isEmpty(mappings)) {
                continue;
            }
            PatternsRequestCondition condition = struct.getMappingInfo().getPatternsCondition();
            if (condition == null) {
                continue;
            }
            if (MultiMapWraps.containsValue(mappings, ElementType.TYPE)) {
                Set<String> patterns = condition.getPatterns().stream().filter(StringUtils::isNoneBlank).map(element -> StringUtilsWraps.appendIfMissing(FilenamePlainWraps.removeEndSlashes(element), AntPathConst.SLASH_STARS)).collect(Collectors.toSet());
                result.addAll(patterns);
                continue;
            }
            if (MultiMapWraps.containsValue(mappings, ElementType.METHOD)) {
                result.addAll(condition.getPatterns());
            }
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    public static String[] getRequestMappingPatternsWithAnnotation(@Nullable ApplicationContext context, @Nullable Class<? extends Annotation> annotation) {
        return getRequestMappingPatternsWithAnnotation(context, annotation, null);
    }

    @Nullable
    public static String[] getRequestMappingPatternsWithAnnotation(@Nullable ApplicationContext context, @Nullable Class<? extends Annotation> annotation, @Nullable BiPredicate<RequestMappingInfo, HandlerMethod> filter) {
        Set<String> result = getRequestMappingPatternsWithAnnotationToSet(context, annotation);
        return CollectionUtils.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public static Set<String> getRequestMappingPatternsWithAnnotationToSet(@Nullable ApplicationContext context, @Nullable Class<? extends Annotation> annotation) {
        return getRequestMappingPatternsWithAnnotationToSet(context, annotation, null);
    }

    /**
     * Return a set of string that contains the path patterns with the given annotation, in the application context
     *
     * @param context the application context
     * @param annotation the annotation to filter
     *
     * @return a set of string that contains the path patterns with the given annotation, in the application context
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Set<String> getRequestMappingPatternsWithAnnotationToSet(@Nullable ApplicationContext context, @Nullable Class<? extends Annotation> annotation, @Nullable BiPredicate<RequestMappingInfo, HandlerMethod> filter) {
        if (ObjectUtils.anyNull(context, annotation)) {
            return null;
        }
        Map<RequestMappingInfo, HandlerMethod> mappings = getRequestMappingMethods(context, filter);
        if (CollectionUtils.isEmpty(mappings)) {
            return null;
        }
        Set<String> result = new LinkedHashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : mappings.entrySet()) {
            boolean onClass = AnnotatedElementUtils.hasAnnotation(entry.getValue().getBeanType(), annotation);
            boolean onMethod = AnnotatedElementUtils.hasAnnotation(entry.getValue().getMethod(), annotation);
            if (onClass || onMethod) {
                PatternsRequestCondition condition = entry.getKey().getPatternsCondition();
                if (condition == null || CollectionUtils.isEmpty(condition.getPatterns())) {
                    continue;
                }
                if (onClass) {
                    Set<String> patterns = condition.getPatterns().stream().filter(StringUtils::isNoneBlank).map(element -> StringUtilsWraps.appendIfMissing(FilenamePlainWraps.removeEndSlashes(element), AntPathConst.SLASH_STARS)).collect(Collectors.toSet());
                    result.addAll(patterns);
                    continue;
                }
                result.addAll(condition.getPatterns());
            }
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    @SafeVarargs
    public static String[] getRequestMappingPatternsWithAllAnnotations(@Nullable ApplicationContext context, @Nullable Class<? extends Annotation>... annotations) {
        Set<String> result = getRequestMappingPatternsWithAllAnnotationsToSet(context, ArrayUtilsWraps.asList(annotations));
        return CollectionUtils.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    public static String[] getRequestMappingPatternsWithAllAnnotations(@Nullable ApplicationContext context, @Nullable Collection<Class<? extends Annotation>> annotations) {
        Set<String> result = getRequestMappingPatternsWithAllAnnotationsToSet(context, annotations);
        return CollectionUtils.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static Set<String> getRequestMappingPatternsWithAllAnnotationsToSet(@Nullable ApplicationContext context, @Nullable Class<? extends Annotation>... annotations) {
        return getRequestMappingPatternsWithAllAnnotationsToSet(context, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Return a set of string that contains the path patterns with all annotations, in the application context
     *
     * @param context the application context
     * @param annotations the annotations to filter
     *
     * @return a set of string that contains the path patterns with all annotations, in the application context
     */
    @Nullable
    public static Set<String> getRequestMappingPatternsWithAllAnnotationsToSet(@Nullable ApplicationContext context, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (context == null || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        Map<RequestMappingInfo, HandlerMethod> mappings = getRequestMappingMethods(context);
        List<RequestMappingStruct> structs = filterHandlerMethodsWithAllAnnotations(mappings, annotations);
        return getRequestMappingPatternsToSet(structs);
    }

    @Nullable
    @SafeVarargs
    public static String[] getRequestMappingPatternsWithAnyAnnotations(@Nullable ApplicationContext context, @Nullable Class<? extends Annotation>... annotations) {
        Set<String> result = getRequestMappingPatternsWithAnyAnnotationsToSet(context, ArrayUtilsWraps.asList(annotations));
        return CollectionUtils.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    public static String[] getRequestMappingPatternsWithAnyAnnotations(@Nullable ApplicationContext context, @Nullable Collection<Class<? extends Annotation>> annotations) {
        Set<String> result = getRequestMappingPatternsWithAnyAnnotationsToSet(context, annotations);
        return CollectionUtils.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static Set<String> getRequestMappingPatternsWithAnyAnnotationsToSet(@Nullable ApplicationContext context, @Nullable Class<? extends Annotation>... annotations) {
        return getRequestMappingPatternsWithAnyAnnotationsToSet(context, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Return a set of string that contains the path patterns with any annotations, in the application context
     *
     * @param context the application context
     * @param annotations the annotations to filter
     *
     * @return a set of string that contains the path patterns with any annotations, in the application context
     */
    @Nullable
    public static Set<String> getRequestMappingPatternsWithAnyAnnotationsToSet(@Nullable ApplicationContext context, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (context == null || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        Map<RequestMappingInfo, HandlerMethod> mappings = getRequestMappingMethods(context);
        List<RequestMappingStruct> structs = filterHandlerMethodsWithAnyAnnotations(mappings, annotations);
        return getRequestMappingPatternsToSet(structs);
    }

    @Nullable
    @SafeVarargs
    public static List<RequestMappingStruct> filterHandlerMethodsWithAllAnnotations(@Nullable Map<RequestMappingInfo, HandlerMethod> mappings, @Nullable Class<? extends Annotation>... annotations) {
        return filterHandlerMethodsWithAllAnnotations(mappings, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Return a list of mapping info structures that filtered with all annotations
     *
     * @param mappings the mapping info
     * @param annotations the annotations to filter
     *
     * @return a list of mapping info structures that filtered with all annotations
     */
    @Nullable
    public static List<RequestMappingStruct> filterHandlerMethodsWithAllAnnotations(@Nullable Map<RequestMappingInfo, HandlerMethod> mappings, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (CollectionUtils.isEmpty(mappings) || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        List<RequestMappingStruct> result = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : mappings.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            RequestMappingStruct struct = new RequestMappingStruct(entry.getKey(), entry.getValue());
            BooleanDataStruct<MultiValueMap<Class<? extends Annotation>, ElementType>> booleanData = AnnotationUtilsWraps.allPresentAnywhereEx(entry.getValue().getMethod(), annotations);
            if (booleanData.isSuccess() && !CollectionUtils.isEmpty(booleanData.getData())) {
                struct.setMappings(booleanData.getData());
            }
            if (struct.isNotEmpty()) {
                result.add(struct);
            }
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    @SafeVarargs
    public static List<RequestMappingStruct> filterHandlerMethodsWithAnyAnnotations(@Nullable Map<RequestMappingInfo, HandlerMethod> mappings, @Nullable Class<? extends Annotation>... annotations) {
        return filterHandlerMethodsWithAnyAnnotations(mappings, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Return a list of mapping info structures that filtered with any annotations
     *
     * @param mappings the mapping info
     * @param annotations the annotations to filter
     *
     * @return a list of mapping info structures that filtered with any annotations
     */
    @Nullable
    public static List<RequestMappingStruct> filterHandlerMethodsWithAnyAnnotations(@Nullable Map<RequestMappingInfo, HandlerMethod> mappings, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (CollectionUtils.isEmpty(mappings) || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        List<RequestMappingStruct> result = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : mappings.entrySet()) {
            RequestMappingStruct struct = new RequestMappingStruct(entry.getKey(), entry.getValue());
            if (entry.getValue() == null) {
                continue;
            }
            BooleanDataStruct<MultiValueMap<Class<? extends Annotation>, ElementType>> booleanData = AnnotationUtilsWraps.anyPresentAnywhereEx(entry.getValue().getMethod(), annotations);
            if (booleanData.isSuccess() && !CollectionUtils.isEmpty(booleanData.getData())) {
                struct.setMappings(booleanData.getData());
            }
            if (struct.isNotEmpty()) {
                result.add(struct);
            }
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }
}
