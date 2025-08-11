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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.core.AliasRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import com.yookue.commonplexus.javaseutil.function.IgnorableFailable;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;


/**
 * Utilities for {@link org.springframework.beans.factory.BeanFactory}
 *
 * @author David Hsing
 *
 * @see org.springframework.beans.factory.BeanFactory
 * @see org.springframework.beans.factory.BeanFactoryUtils
 * @see org.springframework.data.jpa.util.BeanDefinitionUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class BeanFactoryWraps {
    public static boolean allBeansMatchType(@Nullable BeanFactory factory, @Nullable Class<?> expectType, @Nullable String... beanNames) {
        return allBeansMatchType(factory, expectType, ArrayUtilsWraps.asList(beanNames));
    }

    public static boolean allBeansMatchType(@Nullable BeanFactory factory, @Nullable Class<?> expectType, @Nullable Collection<String> beanNames) {
        return ObjectUtils.allNotNull(factory, expectType) && !CollectionUtils.isEmpty(beanNames) && beanNames.stream().allMatch(IgnorableFailable.asPredicate(beanName -> StringUtils.isNotBlank(beanName) && factory.isTypeMatch(beanName, expectType)));
    }

    public static boolean allBeansMatchType(@Nullable BeanFactory factory, @Nullable ResolvableType expectType, @Nullable String... beanNames) {
        return allBeansMatchType(factory, expectType, ArrayUtilsWraps.asList(beanNames));
    }

    public static boolean allBeansMatchType(@Nullable BeanFactory factory, @Nullable ResolvableType expectType, @Nullable Collection<String> beanNames) {
        return ObjectUtils.allNotNull(factory, expectType) && !CollectionUtils.isEmpty(beanNames) && beanNames.stream().allMatch(IgnorableFailable.asPredicate(beanName -> StringUtils.isNotBlank(beanName) && factory.isTypeMatch(beanName, expectType)));
    }

    public static boolean anyBeansMatchType(@Nullable BeanFactory factory, @Nullable Class<?> expectType, @Nullable String... beanNames) {
        return anyBeansMatchType(factory, expectType, ArrayUtilsWraps.asList(beanNames));
    }

    public static boolean anyBeansMatchType(@Nullable BeanFactory factory, @Nullable Class<?> expectType, @Nullable Collection<String> beanNames) {
        return ObjectUtils.allNotNull(factory, expectType) && !CollectionUtils.isEmpty(beanNames) && beanNames.stream().filter(StringUtils::isNotBlank).anyMatch(IgnorableFailable.asPredicate(beanName -> factory.isTypeMatch(beanName, expectType)));
    }

    public static boolean anyBeansMatchType(@Nullable BeanFactory factory, @Nullable ResolvableType expectType, @Nullable String... beanNames) {
        return anyBeansMatchType(factory, expectType, ArrayUtilsWraps.asList(beanNames));
    }

    public static boolean anyBeansMatchType(@Nullable BeanFactory factory, @Nullable ResolvableType expectType, @Nullable Collection<String> beanNames) {
        return ObjectUtils.allNotNull(factory, expectType) && !CollectionUtils.isEmpty(beanNames) && beanNames.stream().filter(StringUtils::isNotBlank).anyMatch(IgnorableFailable.asPredicate(beanName -> factory.isTypeMatch(beanName, expectType)));
    }

    /**
     * Return a bean instance that uniquely matches the given object type
     *
     * @param factory The {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param expectType The class of the bean to instantiate
     * @param autowireMode By name or type, using the constants in the interface of {@link org.springframework.beans.factory.config.AutowireCapableBeanFactory}
     * @param dependencyCheck Whether to perform a dependency check for object
     *
     * @return a bean instance that uniquely matches the given object type
     *
     * @see org.springframework.beans.factory.BeanFactory#getBean(Class)
     * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory#autowire
     */
    @Nullable
    public static Object autowireBeanType(@Nullable BeanFactory factory, @Nullable Class<?> expectType, int autowireMode, boolean dependencyCheck) throws BeansException {
        if (!(factory instanceof AutowireCapableBeanFactory alias) || expectType == null) {
            return null;
        }
        return alias.autowire(expectType, autowireMode, dependencyCheck);
    }

    @Nullable
    public static Object autowireBeanTypeQuietly(@Nullable BeanFactory factory, @Nullable Class<?> expectType, int autowireMode, boolean dependencyCheck) {
        try {
            return autowireBeanType(factory, expectType, autowireMode, dependencyCheck);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T createBean(@Nullable BeanFactory factory, @Nullable Class<T> expectType) {
        if (!(factory instanceof AutowireCapableBeanFactory alias) || expectType == null) {
            return null;
        }
        try {
            return alias.createBean(expectType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static AutowireCapableBeanFactory castToAutowireBeanFactory(@Nullable ApplicationContext context) {
        try {
            WebApplicationContext webContext = ApplicationContextWraps.getWebApplicationContext(context);
            return (webContext == null) ? null : webContext.getAutowireCapableBeanFactory();
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static AutowireCapableBeanFactory castToAutowireBeanFactory(@Nullable BeanFactory factory) {
        return (factory instanceof AutowireCapableBeanFactory alias) ? alias : null;
    }

    @Nullable
    public static ListableBeanFactory castToListableBeanFactory(@Nullable BeanFactory factory) {
        return (factory instanceof ListableBeanFactory alias) ? alias : null;
    }

    public static boolean containsAllBeans(@Nullable BeanFactory factory, @Nullable String... beanNames) {
        return containsAllBeans(factory, ArrayUtilsWraps.asList(beanNames));
    }

    public static boolean containsAllBeans(@Nullable BeanFactory factory, @Nullable Collection<String> beanNames) {
        return factory != null && !CollectionUtils.isEmpty(beanNames) && beanNames.stream().allMatch(beanName -> StringUtils.isNotBlank(beanName) && factory.containsBean(beanName));
    }

    public static boolean containsAnyBeans(@Nullable BeanFactory factory, @Nullable String... beanNames) {
        return containsAnyBeans(factory, ArrayUtilsWraps.asList(beanNames));
    }

    public static boolean containsAnyBeans(@Nullable BeanFactory factory, @Nullable Collection<String> beanNames) {
        return factory != null && !CollectionUtils.isEmpty(beanNames) && beanNames.stream().filter(StringUtils::isNotBlank).anyMatch(factory::containsBean);
    }

    public static boolean containsBean(@Nullable BeanFactory factory, @Nullable String beanName) {
        return factory != null && StringUtils.isNotBlank(beanName) && factory.containsBean(beanName);
    }

    public static <T> boolean containsBean(@Nullable BeanFactory factory, @Nullable Class<T> expectType) {
        return getBean(factory, expectType) != null;
    }

    public static <T> boolean containsBean(@Nullable BeanFactory factory, @Nullable Class<T> expectType, @Nullable Object... args) {
        return getBean(factory, expectType, args) != null;
    }

    public static <T> boolean containsBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<T> expectType) {
        return getBean(factory, beanName, expectType) != null;
    }

    public static boolean containsBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Object... args) {
        return getBean(factory, beanName, args) != null;
    }

    /**
     * Check if this bean factory contains a bean definition with the given name
     *
     * @param factory The bean factory to look for
     * @param beanName The name of the bean to look for
     *
     * @return if this bean factory contains a bean definition with the given name
     */
    public static boolean containsBeanDefinition(@Nullable BeanFactory factory, @Nullable String beanName) {
        return StringUtils.isNotBlank(beanName) && (factory instanceof BeanDefinitionRegistry alias) && alias.containsBeanDefinition(beanName);
    }

    @Nullable
    public static Object firstBeanOfName(@Nullable BeanFactory factory, @Nullable String... beanNames) {
        return firstBeanOfName(factory, ArrayUtilsWraps.asList(beanNames));
    }

    @Nullable
    public static Object firstBeanOfName(@Nullable BeanFactory factory, @Nullable Collection<String> beanNames) {
        if (factory == null || CollectionPlainWraps.isEmpty(beanNames)) {
            return null;
        }
        for (String beanName : beanNames) {
            Object result = getBean(factory, beanName);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    @Nullable
    public static <T> T firstBeanOfName(@Nullable BeanFactory factory, @Nullable Class<T> expectType, @Nullable String... beanNames) {
        return firstBeanOfName(factory, expectType, ArrayUtilsWraps.asList(beanNames));
    }

    @Nullable
    public static <T> T firstBeanOfName(@Nullable BeanFactory factory, @Nullable Class<T> expectType, @Nullable Collection<String> beanNames) {
        if (factory == null || expectType == null || CollectionPlainWraps.isEmpty(beanNames)) {
            return null;
        }
        for (String beanName : beanNames) {
            T result = getBean(factory, beanName, expectType);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    @Nullable
    public static Class<?> firstBeanTypeOfName(@Nullable BeanFactory factory, @Nullable String... beanNames) {
        return firstBeanTypeOfName(factory, ArrayUtilsWraps.asList(beanNames));
    }

    @Nullable
    public static Class<?> firstBeanTypeOfName(@Nullable BeanFactory factory, @Nullable Collection<String> beanNames) {
        if (factory == null || CollectionPlainWraps.isEmpty(beanNames)) {
            return null;
        }
        for (String beanName : beanNames) {
            Class<?> result = getBeanType(factory, beanName);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    @Nullable
    public static Class<?> firstBeanTypeOfName(@Nullable BeanFactory factory, boolean allowFactoryBeanInit, @Nullable String... beanNames) {
        return firstBeanTypeOfName(factory, allowFactoryBeanInit, ArrayUtilsWraps.asList(beanNames));
    }

    @Nullable
    public static Class<?> firstBeanTypeOfName(@Nullable BeanFactory factory, boolean allowFactoryBeanInit, @Nullable Collection<String> beanNames) {
        if (factory == null || CollectionPlainWraps.isEmpty(beanNames)) {
            return null;
        }
        for (String beanName : beanNames) {
            Class<?> result = getBeanType(factory, beanName, allowFactoryBeanInit);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    @Nullable
    public static <T> T firstBeanOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectType) {
        ObjectProvider<T> provider = getBeanProvider(factory, expectType);
        return (provider == null || !provider.iterator().hasNext()) ? null : provider.iterator().next();
    }

    @Nullable
    public static <T> T firstBeanOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectType, @Nullable Class<?>... typeGenerics) {
        ObjectProvider<T> provider = getBeanProvider(factory, expectType, typeGenerics);
        return (provider == null || !provider.iterator().hasNext()) ? null : provider.iterator().next();
    }

    @Nullable
    public static <T> T firstBeanOfType(@Nullable BeanFactory factory, @Nullable ResolvableType expectType) {
        ObjectProvider<T> provider = getBeanProvider(factory, expectType);
        return (provider == null || !provider.iterator().hasNext()) ? null : provider.iterator().next();
    }

    /**
     * Returns an instance for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     *
     * @param factory The bean factory to look for
     * @param expectType Type the bean must match; can be an interface or superclass
     * @param allowEagerInit Whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return an instance for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     */
    @Nullable
    public static <T> T firstBeanOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectType, boolean allowEagerInit) {
        ObjectProvider<T> provider = getBeanProvider(factory, expectType, allowEagerInit);
        return (provider == null || !provider.iterator().hasNext()) ? null : provider.iterator().next();
    }

    /**
     * Returns an instance for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     *
     * @param factory The bean factory to look for
     * @param expectType The type of bean to match, can be a generic type declaration
     * @param allowEagerInit Whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return an instance for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     */
    @Nullable
    public static <T> T firstBeanOfType(@Nullable BeanFactory factory, @Nullable ResolvableType expectType, boolean allowEagerInit) {
        ObjectProvider<T> provider = getBeanProvider(factory, expectType, allowEagerInit);
        return (provider == null || !provider.iterator().hasNext()) ? null : provider.iterator().next();
    }

    /**
     * Return a bean instance that uniquely matches the given object type, if any
     *
     * @param factory A {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param expectType Type the bean must match; can be an interface or superclass
     *
     * @return a bean instance that uniquely matches the given object type, if any
     */
    @Nullable
    public static <T> T getBean(@Nullable BeanFactory factory, @Nullable Class<T> expectType) {
        if (ObjectUtils.anyNull(factory, expectType)) {
            return null;
        }
        try {
            return factory.getBean(expectType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <T> T getBean(@Nullable BeanFactory factory, @Nullable Class<T> expectType, @Nullable Object... args) {
        if (ObjectUtils.anyNull(factory, expectType)) {
            return null;
        }
        try {
            return ArrayUtils.isEmpty(args) ? factory.getBean(expectType) : factory.getBean(expectType, args);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static Object getBean(@Nullable BeanFactory factory, @Nullable String beanName) {
        if (factory == null || StringUtils.isBlank(beanName)) {
            return null;
        }
        try {
            return factory.getBean(beanName);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T getBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<T> expectType) {
        if (ObjectUtils.anyNull(factory, expectType) || StringUtils.isBlank(beanName)) {
            return null;
        }
        try {
            return factory.getBean(beanName, expectType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <T> T getBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<T> expectType, @Nullable Class<?>... typeGenerics) {
        if (ObjectUtils.anyNull(factory, expectType) || StringUtils.isBlank(beanName)) {
            return null;
        }
        try {
            if (ArrayUtils.isEmpty(typeGenerics)) {
                return factory.getBean(beanName, expectType);
            }
            String[] foundNames = getBeanNamesForType(factory, ResolvableType.forClassWithGenerics(expectType, typeGenerics));
            if (ArrayUtils.isEmpty(foundNames)) {
                return null;
            }
            for (String foundName : foundNames) {
                if (StringUtils.equals(foundName, beanName)) {
                    return factory.getBean(beanName, expectType);
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Object getBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable ResolvableType expectType) {
        if (ObjectUtils.anyNull(factory, expectType) || StringUtils.isBlank(beanName)) {
            return null;
        }
        try {
            String[] foundNames = getBeanNamesForType(factory, expectType);
            if (ArrayUtils.isEmpty(foundNames)) {
                return null;
            }
            for (String foundName : foundNames) {
                if (StringUtils.equals(foundName, beanName)) {
                    return factory.getBean(beanName);
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Object getBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Object... args) {
        if (factory == null || StringUtils.isBlank(beanName)) {
            return null;
        }
        try {
            return ArrayUtils.isEmpty(args) ? factory.getBean(beanName) : factory.getBean(beanName, args);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static String[] getBeanAliases(@Nullable BeanFactory factory, @Nullable String beanName) {
        return (factory == null || StringUtils.isBlank(beanName)) ? null : factory.getAliases(beanName);
    }

    /**
     * Returns an annotation on the specified bean, traversing its interfaces and superclasses if no annotation can be found on the given class itself, as well as checking the bean's factory method (if any)
     *
     * @param factory The bean factory to look for
     * @param beanName The name of the bean to look for annotations on
     * @param annotation The type of annotation to look for (at class, interface or factory method level of the specified bean)
     *
     * @return the annotation of the given type if found, or {@code null} otherwise
     */
    @Nullable
    public static <A extends Annotation> A getBeanAnnotation(@Nullable BeanFactory factory, @Nullable String beanName, Class<A> annotation) {
        return getBeanAnnotation(factory, beanName, annotation, true);
    }

    /**
     * Returns an annotation on the specified bean, traversing its interfaces and superclasses if no annotation can be found on the given class itself, as well as checking the bean's factory method (if any)
     *
     * @param factory The bean factory to look for
     * @param beanName The name of the bean to look for annotations on
     * @param annotation The type of annotation to look for (at class, interface or factory method level of the specified bean)
     * @param allowFactoryBeanInit Whether a {@code FactoryBean} may get initialized just for the purpose of determining its object type
     *
     * @return the annotation of the given type if found, or {@code null} otherwise
     */
    @Nullable
    public static <A extends Annotation> A getBeanAnnotation(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<A> annotation, boolean allowFactoryBeanInit) {
        if (!(factory instanceof ListableBeanFactory alias) || StringUtils.isBlank(beanName) || annotation == null) {
            return null;
        }
        try {
            return alias.findAnnotationOnBean(beanName, annotation, allowFactoryBeanInit);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Determine the type of the bean with the given name
     *
     * @param factory A {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param beanName The name of the bean to query
     *
     * @return the type of the bean, or {@code null} if not determinable
     */
    @Nullable
    public static Class<?> getBeanType(@Nullable BeanFactory factory, @Nullable String beanName) {
        if (factory == null || StringUtils.isBlank(beanName)) {
            return null;
        }
        try {
            return factory.getType(beanName);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Determine the type of the bean with the given name
     *
     * @param factory A {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param beanName The name of the bean to query
     * @param allowFactoryBeanInit Whether a {@code FactoryBean} may get initialized
     *
     * @return the type of the bean, or {@code null} if not determinable
     */
    @Nullable
    public static Class<?> getBeanType(@Nullable BeanFactory factory, @Nullable String beanName, boolean allowFactoryBeanInit) {
        if (factory == null || StringUtils.isBlank(beanName)) {
            return null;
        }
        try {
            return factory.getType(beanName, allowFactoryBeanInit);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    @SafeVarargs
    public static String[] getBeanNamesWithAllAnnotations(@Nullable BeanFactory factory, @Nullable Class<? extends Annotation>... annotations) {
        Set<String> result = getBeanNamesWithAllAnnotationsToSet(factory, annotations);
        return CollectionUtils.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    public static String[] getBeanNamesWithAllAnnotations(@Nullable BeanFactory factory, @Nullable Collection<Class<? extends Annotation>> annotations) {
        Set<String> result = getBeanNamesWithAllAnnotationsToSet(factory, annotations);
        return CollectionUtils.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static Set<String> getBeanNamesWithAllAnnotationsToSet(@Nullable BeanFactory factory, @Nullable Class<? extends Annotation>... annotations) {
        return getBeanNamesWithAllAnnotationsToSet(factory, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Return a set of bean names that match the given annotations, in intersection
     *
     * @param factory A {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param annotations The given annotations
     *
     * @return a set of bean names that match the given annotations, in intersection
     *
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#getBeanNamesForAnnotation
     */
    @Nullable
    public static Set<String> getBeanNamesWithAllAnnotationsToSet(@Nullable BeanFactory factory, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (!(factory instanceof ListableBeanFactory alias) || CollectionUtils.isEmpty(annotations) || annotations.stream().anyMatch(Objects::isNull)) {
            return null;
        }
        Set<String> result = new LinkedHashSet<>();
        int index = 0;
        for (Class<? extends Annotation> annotation : annotations) {
            if (annotation == null) {
                return null;
            }
            try {
                Set<String> set = ArrayUtilsWraps.asSet(alias.getBeanNamesForAnnotation(annotation));
                if (CollectionUtils.isEmpty(set)) {
                    return null;
                }
                if (index == 0) {
                    result.addAll(set);
                } else {
                    result.removeIf(element -> !set.contains(element));
                }
            } catch (Exception ignored) {
            }
            if (result.isEmpty()) {
                break;
            }
            index++;
        }
        return result.isEmpty() ? null : result;
    }

    @Nullable
    @SafeVarargs
    public static String[] getBeanNamesWithAnyAnnotations(@Nullable BeanFactory factory, @Nullable Class<? extends Annotation>... annotations) {
        Set<String> result = getBeanNamesWithAnyAnnotationsToSet(factory, annotations);
        return CollectionUtils.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    public static String[] getBeanNamesWithAnyAnnotations(@Nullable BeanFactory factory, @Nullable Collection<Class<? extends Annotation>> annotations) {
        Set<String> result = getBeanNamesWithAnyAnnotationsToSet(factory, annotations);
        return CollectionUtils.isEmpty(result) ? null : result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Nullable
    @SafeVarargs
    public static Set<String> getBeanNamesWithAnyAnnotationsToSet(@Nullable BeanFactory factory, @Nullable Class<? extends Annotation>... annotations) {
        return getBeanNamesWithAnyAnnotationsToSet(factory, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Return a set of bean names that match any of the given annotations
     *
     * @param factory A {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param annotations The given annotations
     *
     * @return a set of bean names that match any of the given annotations
     *
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#getBeanNamesForAnnotation
     */
    @Nullable
    public static Set<String> getBeanNamesWithAnyAnnotationsToSet(@Nullable BeanFactory factory, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (!(factory instanceof ListableBeanFactory alias) || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        return annotations.stream().filter(Objects::nonNull).map(annotation -> ArrayUtilsWraps.asSet(alias.getBeanNamesForAnnotation(annotation))).filter(element -> !CollectionUtils.isEmpty(element)).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public static BeanDefinition getBeanDefinition(@Nullable BeanFactory factory, @Nullable String beanName) throws NoSuchBeanDefinitionException {
        if (!(factory instanceof BeanDefinitionRegistry alias) || StringUtils.isBlank(beanName)) {
            return null;
        }
        return alias.getBeanDefinition(beanName);
    }

    @Nullable
    public static BeanDefinition getBeanDefinitionQuietly(@Nullable BeanFactory factory, @Nullable String beanName) {
        try {
            return getBeanDefinition(factory, beanName);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns the number of beans defined in the factory
     *
     * @param factory The bean factory to look for
     *
     * @return the number of beans defined in the factory
     */
    public static int getBeanDefinitionCount(@Nullable BeanFactory factory) {
        return (factory instanceof BeanDefinitionRegistry alias) ? alias.getBeanDefinitionCount() : 0;
    }

    /**
     * Returns the names of all beans defined in this factory
     *
     * @param factory The bean factory to look for
     *
     * @return the names of all beans defined in this factory
     */
    @Nullable
    public static String[] getBeanDefinitionNames(@Nullable BeanFactory factory) {
        if (!(factory instanceof BeanDefinitionRegistry alias)) {
            return null;
        }
        String[] result = alias.getBeanDefinitionNames();
        return ArrayUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    public static <T> ObjectProvider<T> getBeanProvider(@Nullable BeanFactory factory, @Nullable Class<T> expectType) {
        return ObjectUtils.anyNull(factory, expectType) ? null : factory.getBeanProvider(expectType);
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <T> ObjectProvider<T> getBeanProvider(@Nullable BeanFactory factory, @Nullable Class<T> expectType, @Nullable Class<?>... typeGenerics) {
        if (ObjectUtils.anyNull(factory, expectType)) {
            return null;
        }
        return ArrayUtils.isEmpty(typeGenerics) ? factory.getBeanProvider(expectType) : factory.getBeanProvider(ResolvableType.forClassWithGenerics(expectType, typeGenerics));
    }

    @Nullable
    public static <T> ObjectProvider<T> getBeanProvider(@Nullable BeanFactory factory, @Nullable ResolvableType expectType) {
        return ObjectUtils.anyNull(factory, expectType) ? null : factory.getBeanProvider(expectType);
    }

    /**
     * Returns a provider for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     *
     * @param factory The bean factory to look for
     * @param expectType Type the bean must match; can be an interface or superclass
     * @param allowEagerInit Whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return a provider for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     */
    @Nullable
    public static <T> ObjectProvider<T> getBeanProvider(@Nullable BeanFactory factory, @Nullable Class<T> expectType, boolean allowEagerInit) {
        if (!(factory instanceof ListableBeanFactory alias) || expectType == null) {
            return null;
        }
        return alias.getBeanProvider(expectType, allowEagerInit);
    }

    /**
     * Returns a provider for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     *
     * @param factory The bean factory to look for
     * @param expectType The type of bean to match, can be a generic type declaration
     * @param allowEagerInit Whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return a provider for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     */
    @Nullable
    public static <T> ObjectProvider<T> getBeanProvider(@Nullable BeanFactory factory, @Nullable ResolvableType expectType, boolean allowEagerInit) {
        if (!(factory instanceof ListableBeanFactory alias) || expectType == null) {
            return null;
        }
        return alias.getBeanProvider(expectType, allowEagerInit);
    }

    /**
     * Returns the names of beans matching the given type (including subclasses), judging from either bean definitions or the value of {@code getObjectType} in the case of FactoryBeans
     *
     * @param factory The bean factory to look for
     * @param expectType The type of bean to match, can be a generic type declaration
     *
     * @return the names of beans (or objects created by FactoryBeans) matching
     */
    @Nullable
    public static String[] getBeanNamesForType(@Nullable BeanFactory factory, @Nullable ResolvableType expectType) {
        return getBeanNamesForType(factory, expectType, true, true);
    }

    /**
     * Returns the names of beans matching the given type (including subclasses), judging from either bean definitions or the value of {@code getObjectType} in the case of FactoryBeans
     *
     * @param factory The bean factory to look for
     * @param expectType The type of bean to match, can be a generic type declaration
     * @param includeNonSingletons Whether to include prototype or scoped beans too or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit Whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return the names of beans (or objects created by FactoryBeans) matching
     */
    @Nullable
    public static String[] getBeanNamesForType(@Nullable BeanFactory factory, @Nullable ResolvableType expectType, boolean includeNonSingletons, boolean allowEagerInit) {
        if (!(factory instanceof ListableBeanFactory alias) || expectType == null) {
            return null;
        }
        return alias.getBeanNamesForType(expectType, includeNonSingletons, allowEagerInit);
    }

    /**
     * Return the names of beans matching the given type (including subclasses), judging from either bean definitions or the value of {@code getObjectType} in the case of FactoryBeans
     *
     * @param factory The bean factory to look for
     * @param expectType The class or interface to match, or {@code null} for all bean names
     *
     * @return the names of beans (or objects created by FactoryBeans) matching
     */
    @Nullable
    public static String[] getBeanNamesForType(@Nullable BeanFactory factory, @Nullable Class<?> expectType) {
        return getBeanNamesForType(factory, expectType, true, true);
    }

    /**
     * Returns the names of beans matching the given type (including subclasses), judging from either bean definitions or the value of {@code getObjectType} in the case of FactoryBeans
     *
     * @param factory The bean factory to look for
     * @param expectType The class or interface to match, or {@code null} for all bean names
     * @param includeNonSingletons Whether to include prototype or scoped beans too or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit Whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return the names of beans (or objects created by FactoryBeans) matching
     */
    @Nullable
    public static String[] getBeanNamesForType(@Nullable BeanFactory factory, @Nullable Class<?> expectType, boolean includeNonSingletons, boolean allowEagerInit) {
        if (!(factory instanceof ListableBeanFactory alias) || expectType == null) {
            return null;
        }
        return alias.getBeanNamesForType(expectType, includeNonSingletons, allowEagerInit);
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses)
     *
     * @param factory The bean factory to look for
     * @param expectType The class or interface to match, or {@code null} for all concrete beans
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectType) {
        return getBeansOfType(factory, expectType, true, true);
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses), with specified {@code beanNames}
     *
     * @param factory The bean factory to look for
     * @param expectType The class or interface to match, or {@code null} for all concrete beans
     * @param beanNames The bean names that should match
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectType, @Nullable String... beanNames) {
        return getBeansOfType(factory, expectType, ArrayUtilsWraps.asList(beanNames));
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses), with specified {@code beanNames}
     *
     * @param factory The bean factory to look for
     * @param expectType The class or interface to match, or {@code null} for all concrete beans
     * @param beanNames The bean names that should match
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectType, @Nullable Collection<String> beanNames) {
        if (!(factory instanceof ListableBeanFactory) || expectType == null || CollectionUtils.isEmpty(beanNames)) {
            return null;
        }
        Map<String, T> result = getBeansOfType(factory, expectType);
        if (!CollectionUtils.isEmpty(result)) {
            MapPlainWraps.removeIfKey(result, element -> !beanNames.contains(element));
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses)
     *
     * @param factory The bean factory to look for
     * @param expectType The class or interface to match, or {@code null} for all concrete beans
     * @param includeNonSingletons Whether to include prototype or scoped beans too or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit Whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectType, boolean includeNonSingletons, boolean allowEagerInit) {
        if (!(factory instanceof ListableBeanFactory alias) || expectType == null) {
            return null;
        }
        try {
            return alias.getBeansOfType(expectType, includeNonSingletons, allowEagerInit);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses), with specified {@code beanNames}
     *
     * @param factory The bean factory to look for
     * @param expectType The class or interface to match, or {@code null} for all concrete beans
     * @param includeNonSingletons Whether to include prototype or scoped beans too or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit Whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     * @param beanNames The bean names that should match
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectType, boolean includeNonSingletons, boolean allowEagerInit, @Nullable String... beanNames) {
        return getBeansOfType(factory, expectType, includeNonSingletons, allowEagerInit, ArrayUtilsWraps.asList(beanNames));
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses), with specified {@code beanNames}
     *
     * @param factory The bean factory to look for
     * @param expectType The class or interface to match, or {@code null} for all concrete beans
     * @param includeNonSingletons Whether to include prototype or scoped beans too or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit Whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     * @param beanNames The bean names that should match
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectType, boolean includeNonSingletons, boolean allowEagerInit, @Nullable Collection<String> beanNames) {
        if (!(factory instanceof ListableBeanFactory) || expectType == null || CollectionUtils.isEmpty(beanNames)) {
            return null;
        }
        Map<String, T> result = getBeansOfType(factory, expectType, includeNonSingletons, allowEagerInit);
        if (!CollectionUtils.isEmpty(result)) {
            MapPlainWraps.removeIfKey(result, element -> !beanNames.contains(element));
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    /**
     * Find all beans which are annotated with the supplied {@code annotation}
     *
     * @param factory The bean factory to look for
     * @param annotation The type of annotation to look for (at class, interface or factory method level of the specified bean)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static Map<String, Object> getBeansWithAnnotation(@Nullable BeanFactory factory, @Nullable Class<? extends Annotation> annotation) {
        if (!(factory instanceof ListableBeanFactory alias) || annotation == null) {
            return null;
        }
        try {
            return alias.getBeansWithAnnotation(annotation);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Find all beans which are annotated with the supplied {@code annotation}, casting to {@code expectType}
     *
     * @param factory The bean factory to look for
     * @param annotation The type of annotation to look for (at class, interface or factory method level of the specified bean)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansWithAnnotationAs(@Nullable BeanFactory factory, @Nullable Class<? extends Annotation> annotation, @Nullable Class<T> expectType) {
        if (!(factory instanceof ListableBeanFactory) || ObjectUtils.anyNull(annotation, expectType)) {
            return null;
        }
        Map<String, Object> nameBeans = getBeansWithAnnotation(factory, annotation);
        if (CollectionUtils.isEmpty(nameBeans)) {
            return null;
        }
        Map<String, T> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : nameBeans.entrySet()) {
            if (ClassUtils.isAssignableValue(expectType, entry.getValue())) {
                result.put(entry.getKey(), ObjectUtilsWraps.castAs(entry.getValue(), expectType));
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Find all beans which are annotated with all the supplied {@code annotations}
     *
     * @param factory The bean factory to look for
     * @param annotations The types of annotations to look for (at class, interface or factory method level of the specified bean)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    @SafeVarargs
    public static Map<String, Object> getBeansWithAllAnnotations(@Nullable BeanFactory factory, @Nullable Class<? extends Annotation>... annotations) {
        return getBeansWithAllAnnotations(factory, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Find all beans which are annotated with all the supplied {@code annotations}
     *
     * @param factory The bean factory to look for
     * @param annotations The types of annotations to look for (at class, interface or factory method level of the specified bean)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static Map<String, Object> getBeansWithAllAnnotations(@Nullable BeanFactory factory, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (!(factory instanceof ListableBeanFactory alias) || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        Map<String, Object> result = new LinkedHashMap<>();
        int index = 0;
        for (Class<? extends Annotation> annotation : annotations) {
            if (annotation == null) {
                return null;
            }
            try {
                Map<String, Object> map = alias.getBeansWithAnnotation(annotation);
                if (index == 0) {
                    result.putAll(map);
                } else {
                    for (Map.Entry<String, Object> entry : result.entrySet()) {
                        if (!map.containsKey(entry.getKey())) {
                            result.remove(entry.getKey());
                        }
                    }
                    MapPlainWraps.removeIfKey(result, element -> !map.containsKey(element));
                }
            } catch (Exception ignored) {
            }
            if (result.isEmpty()) {
                break;
            }
            index++;
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Find all beans which are annotated with any of the supplied {@code annotations}
     *
     * @param factory The bean factory to look for
     * @param annotations The types of annotations to look for (at class, interface or factory method level of the specified bean)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    @SafeVarargs
    public static Map<String, Object> getBeansWithAnyAnnotations(@Nullable BeanFactory factory, @Nullable Class<? extends Annotation>... annotations) {
        return getBeansWithAnyAnnotations(factory, ArrayUtilsWraps.asList(annotations));
    }

    /**
     * Find all beans which are annotated with any of the supplied {@code annotations}
     *
     * @param factory The bean factory to look for
     * @param annotations The types of annotations to look for (at class, interface or factory method level of the specified bean)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static Map<String, Object> getBeansWithAnyAnnotations(@Nullable BeanFactory factory, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (!(factory instanceof ListableBeanFactory alias) || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        Map<String, Object> result = new LinkedHashMap<>();
        for (Class<? extends Annotation> annotation : annotations) {
            if (annotation == null) {
                continue;
            }
            try {
                result.putAll(alias.getBeansWithAnnotation(annotation));
            } catch (Exception ignored) {
            }
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    public static boolean isBeanAlias(@Nullable BeanFactory factory, @Nullable String beanAlias) {
        return StringUtils.isNotBlank(beanAlias) && (factory instanceof AliasRegistry alias) && alias.isAlias(beanAlias);
    }

    public static boolean isBeanNameUsed(@Nullable BeanFactory factory, @Nullable String beanName) {
        return StringUtils.isNotBlank(beanName) && (factory instanceof BeanDefinitionRegistry alias) && alias.isBeanNameInUse(beanName);
    }

    public static boolean isBeanPrototype(@Nullable BeanFactory factory, @Nullable String beanName) {
        if (factory == null || StringUtils.isBlank(beanName)) {
            return false;
        }
        try {
            return factory.isPrototype(beanName);
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean matchBeanType(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<?> expectType) {
        if (ObjectUtils.anyNull(factory, expectType) || StringUtils.isBlank(beanName)) {
            return false;
        }
        try {
            return factory.isTypeMatch(beanName, expectType);
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean matchBeanType(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable ResolvableType expectType) {
        if (ObjectUtils.anyNull(factory, expectType) || StringUtils.isBlank(beanName)) {
            return false;
        }
        try {
            return factory.isTypeMatch(beanName, expectType);
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean isBeanSingleton(@Nullable BeanFactory factory, @Nullable String beanName) {
        if (factory == null || StringUtils.isBlank(beanName)) {
            return false;
        }
        try {
            return factory.isSingleton(beanName);
        } catch (Exception ignored) {
        }
        return false;
    }

    @SuppressWarnings("DataFlowIssue")
    public static boolean registerBeanAlias(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable String beanAlias) throws IllegalStateException {
        if (!(factory instanceof AliasRegistry alias) || StringUtils.isAnyBlank(beanName, beanAlias)) {
            return false;
        }
        alias.registerAlias(beanName, beanAlias);
        return true;
    }

    public static boolean registerBeanAliasQuietly(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable String alias) {
        try {
            return registerBeanAlias(factory, beanName, alias);
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean registerBeanDefinition(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<?> expectType) throws BeanDefinitionStoreException {
        return registerBeanDefinition(factory, beanName, expectType, null);
    }

    /**
     * @see org.springframework.beans.factory.support.BeanDefinitionRegistry
     * @see org.springframework.beans.factory.support.BeanDefinitionBuilder
     */
    public static boolean registerBeanDefinition(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<?> expectType, @Nullable String parentName) throws BeanDefinitionStoreException {
        if (!(factory instanceof BeanDefinitionRegistry alias) || StringUtils.isBlank(beanName) || expectType == null) {
            return false;
        }
        AbstractBeanDefinition definition = StringUtils.isBlank(parentName) ? new RootBeanDefinition() : new GenericBeanDefinition();
        definition.setBeanClass(expectType);
        if (StringUtils.isNotBlank(parentName)) {
            definition.setParentName(parentName);
        }
        alias.registerBeanDefinition(beanName, definition);
        return true;
    }

    public static boolean registerBeanDefinitionQuietly(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<?> expectType) {
        return registerBeanDefinitionQuietly(factory, beanName, expectType, null);
    }

    public static boolean registerBeanDefinitionQuietly(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<?> expectType, @Nullable String parentName) {
        try {
            return registerBeanDefinition(factory, beanName, expectType, parentName);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * @see org.springframework.beans.factory.support.BeanDefinitionRegistry
     * @see org.springframework.beans.factory.support.BeanDefinitionBuilder
     */
    public static boolean registerBeanDefinition(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable BeanDefinition definition) throws BeanDefinitionStoreException {
        if (!(factory instanceof BeanDefinitionRegistry alias) || StringUtils.isBlank(beanName) || definition == null) {
            return false;
        }
        alias.registerBeanDefinition(beanName, definition);
        return true;
    }

    public static boolean registerBeanDefinitionQuietly(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable BeanDefinition definition) {
        try {
            return registerBeanDefinition(factory, beanName, definition);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * @see org.springframework.beans.factory.config.SingletonBeanRegistry
     */
    public static boolean registerSingletonBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Object beanInstance) throws IllegalStateException {
        if (!(factory instanceof SingletonBeanRegistry alias) || StringUtils.isBlank(beanName) || beanInstance == null) {
            return false;
        }
        alias.registerSingleton(beanName, beanInstance);
        return true;
    }

    public static boolean registerSingletonBeanQuietly(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Object beanInstance) {
        try {
            return registerSingletonBean(factory, beanName, beanInstance);
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean removeBeanAlias(@Nullable BeanFactory factory, @Nullable String alias) throws IllegalStateException {
        if (!(factory instanceof AliasRegistry instance) || StringUtils.isBlank(alias)) {
            return false;
        }
        instance.removeAlias(alias);
        return true;
    }

    public static boolean removeBeanAliasQuietly(@Nullable BeanFactory factory, @Nullable String alias) {
        try {
            return removeBeanAlias(factory, alias);
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean removeBeanDefinition(@Nullable BeanFactory factory, @Nullable String beanName) throws NoSuchBeanDefinitionException {
        if (!(factory instanceof BeanDefinitionRegistry instance) || StringUtils.isBlank(beanName)) {
            return false;
        }
        instance.removeBeanDefinition(beanName);
        return true;
    }

    public static boolean removeBeanDefinitionQuietly(@Nullable BeanFactory factory, @Nullable String beanName) {
        try {
            return removeBeanDefinition(factory, beanName);
        } catch (Exception ignored) {
        }
        return false;
    }
}
