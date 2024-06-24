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
import javax.annotation.Nullable;
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
 * @see org.springframework.beans.factory.BeanFactory
 * @see org.springframework.beans.factory.BeanFactoryUtils
 * @see org.springframework.data.jpa.util.BeanDefinitionUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class BeanFactoryWraps {
    public static boolean allBeansMatchType(@Nullable BeanFactory factory, @Nullable Class<?> expectedType, @Nullable String... beanNames) {
        return allBeansMatchType(factory, expectedType, ArrayUtilsWraps.asList(beanNames));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean allBeansMatchType(@Nullable BeanFactory factory, @Nullable Class<?> expectedType, @Nullable Collection<String> beanNames) {
        return ObjectUtils.allNotNull(factory, expectedType) && !CollectionUtils.isEmpty(beanNames) && beanNames.stream().allMatch(IgnorableFailable.asPredicate(beanName -> StringUtils.isNotBlank(beanName) && factory.isTypeMatch(beanName, expectedType)));
    }

    public static boolean allBeansMatchType(@Nullable BeanFactory factory, @Nullable ResolvableType expectedType, @Nullable String... beanNames) {
        return allBeansMatchType(factory, expectedType, ArrayUtilsWraps.asList(beanNames));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean allBeansMatchType(@Nullable BeanFactory factory, @Nullable ResolvableType expectedType, @Nullable Collection<String> beanNames) {
        return ObjectUtils.allNotNull(factory, expectedType) && !CollectionUtils.isEmpty(beanNames) && beanNames.stream().allMatch(IgnorableFailable.asPredicate(beanName -> StringUtils.isNotBlank(beanName) && factory.isTypeMatch(beanName, expectedType)));
    }

    public static boolean anyBeansMatchType(@Nullable BeanFactory factory, @Nullable Class<?> expectedType, @Nullable String... beanNames) {
        return anyBeansMatchType(factory, expectedType, ArrayUtilsWraps.asList(beanNames));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean anyBeansMatchType(@Nullable BeanFactory factory, @Nullable Class<?> expectedType, @Nullable Collection<String> beanNames) {
        return ObjectUtils.allNotNull(factory, expectedType) && !CollectionUtils.isEmpty(beanNames) && beanNames.stream().filter(StringUtils::isNotBlank).anyMatch(IgnorableFailable.asPredicate(beanName -> factory.isTypeMatch(beanName, expectedType)));
    }

    public static boolean anyBeansMatchType(@Nullable BeanFactory factory, @Nullable ResolvableType expectedType, @Nullable String... beanNames) {
        return anyBeansMatchType(factory, expectedType, ArrayUtilsWraps.asList(beanNames));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean anyBeansMatchType(@Nullable BeanFactory factory, @Nullable ResolvableType expectedType, @Nullable Collection<String> beanNames) {
        return ObjectUtils.allNotNull(factory, expectedType) && !CollectionUtils.isEmpty(beanNames) && beanNames.stream().filter(StringUtils::isNotBlank).anyMatch(IgnorableFailable.asPredicate(beanName -> factory.isTypeMatch(beanName, expectedType)));
    }

    /**
     * Return a bean instance that uniquely matches the given object type
     *
     * @param factory a {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param expectedType the class of the bean to instantiate
     * @param autowireMode by name or type, using the constants in the interface of {@link org.springframework.beans.factory.config.AutowireCapableBeanFactory}
     * @param dependencyCheck whether to perform a dependency check for object
     *
     * @return a bean instance that uniquely matches the given object type
     *
     * @see org.springframework.beans.factory.BeanFactory#getBean(Class)
     * @see org.springframework.beans.factory.config.AutowireCapableBeanFactory#autowire
     */
    @Nullable
    public static Object autowireBeanType(@Nullable BeanFactory factory, @Nullable Class<?> expectedType, int autowireMode, boolean dependencyCheck) throws BeansException {
        if (!(factory instanceof AutowireCapableBeanFactory) || expectedType == null) {
            return null;
        }
        AutowireCapableBeanFactory beanFactory = (AutowireCapableBeanFactory) factory;
        return beanFactory.autowire(expectedType, autowireMode, dependencyCheck);
    }

    @Nullable
    public static Object autowireBeanTypeQuietly(@Nullable BeanFactory factory, @Nullable Class<?> expectedType, int autowireMode, boolean dependencyCheck) {
        try {
            return autowireBeanType(factory, expectedType, autowireMode, dependencyCheck);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T createBean(@Nullable BeanFactory factory, @Nullable Class<T> expectedType) {
        if (!(factory instanceof AutowireCapableBeanFactory) || expectedType == null) {
            return null;
        }
        AutowireCapableBeanFactory beanFactory = (AutowireCapableBeanFactory) factory;
        try {
            return beanFactory.createBean(expectedType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static Object createBean(@Nullable BeanFactory factory, @Nullable Class<?> expectedType, int autowireMode, boolean dependencyCheck) {
        if (!(factory instanceof AutowireCapableBeanFactory) || expectedType == null) {
            return null;
        }
        AutowireCapableBeanFactory beanFactory = (AutowireCapableBeanFactory) factory;
        try {
            return beanFactory.createBean(expectedType, autowireMode, dependencyCheck);
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
        return (factory instanceof AutowireCapableBeanFactory) ? (AutowireCapableBeanFactory) factory : null;
    }

    @Nullable
    public static ListableBeanFactory castToListableBeanFactory(@Nullable BeanFactory factory) {
        return (factory instanceof ListableBeanFactory) ? (ListableBeanFactory) factory : null;
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

    public static <T> boolean containsBean(@Nullable BeanFactory factory, @Nullable Class<T> expectedType) {
        return getBean(factory, expectedType) != null;
    }

    public static <T> boolean containsBean(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, @Nullable Object... args) {
        return getBean(factory, expectedType, args) != null;
    }

    public static <T> boolean containsBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<T> expectedType) {
        return getBean(factory, beanName, expectedType) != null;
    }

    public static boolean containsBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Object... args) {
        return getBean(factory, beanName, args) != null;
    }

    /**
     * Check if this bean factory contains a bean definition with the given name
     *
     * @param factory the bean factory to look for
     * @param beanName the name of the bean to look for
     *
     * @return if this bean factory contains a bean definition with the given name
     */
    public static boolean containsBeanDefinition(@Nullable BeanFactory factory, @Nullable String beanName) {
        if (!(factory instanceof BeanDefinitionRegistry) || StringUtils.isBlank(beanName)) {
            return false;
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
        return registry.containsBeanDefinition(beanName);
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
    public static <T> T firstBeanOfName(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, @Nullable String... beanNames) {
        return firstBeanOfName(factory, expectedType, ArrayUtilsWraps.asList(beanNames));
    }

    @Nullable
    public static <T> T firstBeanOfName(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, @Nullable Collection<String> beanNames) {
        if (factory == null || expectedType == null || CollectionPlainWraps.isEmpty(beanNames)) {
            return null;
        }
        for (String beanName : beanNames) {
            T result = getBean(factory, beanName, expectedType);
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
    public static <T> T firstBeanOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectedType) {
        ObjectProvider<T> provider = getBeanProvider(factory, expectedType);
        return (provider == null || !provider.iterator().hasNext()) ? null : provider.iterator().next();
    }

    @Nullable
    public static <T> T firstBeanOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, @Nullable Class<?>... typeGenerics) {
        ObjectProvider<T> provider = getBeanProvider(factory, expectedType, typeGenerics);
        return (provider == null || !provider.iterator().hasNext()) ? null : provider.iterator().next();
    }

    @Nullable
    public static <T> T firstBeanOfType(@Nullable BeanFactory factory, @Nullable ResolvableType expectedType) {
        ObjectProvider<T> provider = getBeanProvider(factory, expectedType);
        return (provider == null || !provider.iterator().hasNext()) ? null : provider.iterator().next();
    }

    /**
     * Returns an instance for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     *
     * @param factory the bean factory to look for
     * @param expectedType type the bean must match; can be an interface or superclass
     * @param allowEagerInit whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return an instance for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     */
    @Nullable
    public static <T> T firstBeanOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, boolean allowEagerInit) {
        ObjectProvider<T> provider = getBeanProvider(factory, expectedType, allowEagerInit);
        return (provider == null || !provider.iterator().hasNext()) ? null : provider.iterator().next();
    }

    /**
     * Returns an instance for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     *
     * @param factory the bean factory to look for
     * @param expectedType the type of bean to match, can be a generic type declaration
     * @param allowEagerInit whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return an instance for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     */
    @Nullable
    public static <T> T firstBeanOfType(@Nullable BeanFactory factory, @Nullable ResolvableType expectedType, boolean allowEagerInit) {
        ObjectProvider<T> provider = getBeanProvider(factory, expectedType, allowEagerInit);
        return (provider == null || !provider.iterator().hasNext()) ? null : provider.iterator().next();
    }

    /**
     * Return a bean instance that uniquely matches the given object type, if any
     *
     * @param factory a {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param expectedType type the bean must match; can be an interface or superclass
     *
     * @return a bean instance that uniquely matches the given object type, if any
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> T getBean(@Nullable BeanFactory factory, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(factory, expectedType)) {
            return null;
        }
        try {
            return factory.getBean(expectedType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> T getBean(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, @Nullable Object... args) {
        if (ObjectUtils.anyNull(factory, expectedType)) {
            return null;
        }
        try {
            return ArrayUtils.isEmpty(args) ? factory.getBean(expectedType) : factory.getBean(expectedType, args);
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
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> T getBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(factory, expectedType) || StringUtils.isBlank(beanName)) {
            return null;
        }
        try {
            return factory.getBean(beanName, expectedType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> T getBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<T> expectedType, @Nullable Class<?>... typeGenerics) {
        if (ObjectUtils.anyNull(factory, expectedType) || StringUtils.isBlank(beanName)) {
            return null;
        }
        try {
            if (ArrayUtils.isEmpty(typeGenerics)) {
                return factory.getBean(beanName, expectedType);
            }
            String[] foundNames = getBeanNamesForType(factory, ResolvableType.forClassWithGenerics(expectedType, typeGenerics));
            if (ArrayUtils.isEmpty(foundNames)) {
                return null;
            }
            for (String foundName : foundNames) {
                if (StringUtils.equals(foundName, beanName)) {
                    return factory.getBean(beanName, expectedType);
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static Object getBean(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable ResolvableType expectedType) {
        if (ObjectUtils.anyNull(factory, expectedType) || StringUtils.isBlank(beanName)) {
            return null;
        }
        try {
            String[] foundNames = getBeanNamesForType(factory, expectedType);
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
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
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
     * @param factory the bean factory to look for
     * @param beanName the name of the bean to look for annotations on
     * @param annotation the type of annotation to look for (at class, interface or factory method level of the specified bean)
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
     * @param factory the bean factory to look for
     * @param beanName the name of the bean to look for annotations on
     * @param annotation the type of annotation to look for (at class, interface or factory method level of the specified bean)
     * @param allowFactoryBeanInit whether a {@code FactoryBean} may get initialized just for the purpose of determining its object type
     *
     * @return the annotation of the given type if found, or {@code null} otherwise
     */
    @Nullable
    public static <A extends Annotation> A getBeanAnnotation(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<A> annotation, boolean allowFactoryBeanInit) {
        if (!(factory instanceof ListableBeanFactory) || StringUtils.isBlank(beanName) || annotation == null) {
            return null;
        }
        ListableBeanFactory beanFactory = (ListableBeanFactory) factory;
        try {
            return beanFactory.findAnnotationOnBean(beanName, annotation, allowFactoryBeanInit);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Determine the type of the bean with the given name
     *
     * @param factory a {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param beanName the name of the bean to query
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
     * @param factory a {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param beanName the name of the bean to query
     * @param allowFactoryBeanInit whether a {@code FactoryBean} may get initialized
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
     * @param factory a {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param annotations the given annotations
     *
     * @return a set of bean names that match the given annotations, in intersection
     *
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#getBeanNamesForAnnotation
     */
    @Nullable
    public static Set<String> getBeanNamesWithAllAnnotationsToSet(@Nullable BeanFactory factory, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (!(factory instanceof ListableBeanFactory) || CollectionUtils.isEmpty(annotations) || annotations.stream().anyMatch(Objects::isNull)) {
            return null;
        }
        Set<String> result = new LinkedHashSet<>();
        ListableBeanFactory beanFactory = (ListableBeanFactory) factory;
        int index = 0;
        for (Class<? extends Annotation> annotation : annotations) {
            if (annotation == null) {
                return null;
            }
            try {
                Set<String> set = ArrayUtilsWraps.asSet(beanFactory.getBeanNamesForAnnotation(annotation));
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
     * @param factory a {@link org.springframework.beans.factory.BeanFactory} object that be searched
     * @param annotations the given annotations
     *
     * @return a set of bean names that match any of the given annotations
     *
     * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#getBeanNamesForAnnotation
     */
    @Nullable
    public static Set<String> getBeanNamesWithAnyAnnotationsToSet(@Nullable BeanFactory factory, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (!(factory instanceof ListableBeanFactory) || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        ListableBeanFactory beanFactory = (ListableBeanFactory) factory;
        return annotations.stream().filter(Objects::nonNull).map(annotation -> ArrayUtilsWraps.asSet(beanFactory.getBeanNamesForAnnotation(annotation))).filter(element -> !CollectionUtils.isEmpty(element)).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public static BeanDefinition getBeanDefinition(@Nullable BeanFactory factory, @Nullable String beanName) throws NoSuchBeanDefinitionException {
        if (!(factory instanceof BeanDefinitionRegistry) || StringUtils.isBlank(beanName)) {
            return null;
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
        return registry.getBeanDefinition(beanName);
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
     * @param factory the bean factory to look for
     *
     * @return the number of beans defined in the factory
     */
    public static int getBeanDefinitionCount(@Nullable BeanFactory factory) {
        return (factory instanceof BeanDefinitionRegistry) ? ((BeanDefinitionRegistry) factory).getBeanDefinitionCount() : 0;
    }

    /**
     * Returns the names of all beans defined in this factory
     *
     * @param factory the bean factory to look for
     *
     * @return the names of all beans defined in this factory
     */
    @Nullable
    public static String[] getBeanDefinitionNames(@Nullable BeanFactory factory) {
        if (!(factory instanceof BeanDefinitionRegistry)) {
            return null;
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
        String[] result = registry.getBeanDefinitionNames();
        return ArrayUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> ObjectProvider<T> getBeanProvider(@Nullable BeanFactory factory, @Nullable Class<T> expectedType) {
        return ObjectUtils.anyNull(factory, expectedType) ? null : factory.getBeanProvider(expectedType);
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> ObjectProvider<T> getBeanProvider(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, @Nullable Class<?>... typeGenerics) {
        if (ObjectUtils.anyNull(factory, expectedType)) {
            return null;
        }
        return ArrayUtils.isEmpty(typeGenerics) ? factory.getBeanProvider(expectedType) : factory.getBeanProvider(ResolvableType.forClassWithGenerics(expectedType, typeGenerics));
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> ObjectProvider<T> getBeanProvider(@Nullable BeanFactory factory, @Nullable ResolvableType expectedType) {
        return ObjectUtils.anyNull(factory, expectedType) ? null : factory.getBeanProvider(expectedType);
    }

    /**
     * Returns a provider for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     *
     * @param factory the bean factory to look for
     * @param expectedType type the bean must match; can be an interface or superclass
     * @param allowEagerInit whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return a provider for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     */
    @Nullable
    public static <T> ObjectProvider<T> getBeanProvider(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, boolean allowEagerInit) {
        if (!(factory instanceof ListableBeanFactory) || expectedType == null) {
            return null;
        }
        ListableBeanFactory beanFactory = (ListableBeanFactory) factory;
        return beanFactory.getBeanProvider(expectedType, allowEagerInit);
    }

    /**
     * Returns a provider for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     *
     * @param factory the bean factory to look for
     * @param expectedType the type of bean to match, can be a generic type declaration
     * @param allowEagerInit whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return a provider for the specified bean, allowing for lazy on-demand retrieval of instances, including availability and uniqueness options
     */
    @Nullable
    public static <T> ObjectProvider<T> getBeanProvider(@Nullable BeanFactory factory, @Nullable ResolvableType expectedType, boolean allowEagerInit) {
        if (!(factory instanceof ListableBeanFactory) || expectedType == null) {
            return null;
        }
        ListableBeanFactory beanFactory = (ListableBeanFactory) factory;
        return beanFactory.getBeanProvider(expectedType, allowEagerInit);
    }

    /**
     * Returns the names of beans matching the given type (including subclasses), judging from either bean definitions or the value of {@code getObjectType} in the case of FactoryBeans
     *
     * @param factory the bean factory to look for
     * @param expectedType the type of bean to match, can be a generic type declaration
     *
     * @return the names of beans (or objects created by FactoryBeans) matching
     */
    @Nullable
    public static String[] getBeanNamesForType(@Nullable BeanFactory factory, @Nullable ResolvableType expectedType) {
        return getBeanNamesForType(factory, expectedType, true, true);
    }

    /**
     * Returns the names of beans matching the given type (including subclasses), judging from either bean definitions or the value of {@code getObjectType} in the case of FactoryBeans
     *
     * @param factory the bean factory to look for
     * @param expectedType the type of bean to match, can be a generic type declaration
     * @param includeNonSingletons whether to include prototype or scoped beans too or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return the names of beans (or objects created by FactoryBeans) matching
     */
    @Nullable
    public static String[] getBeanNamesForType(@Nullable BeanFactory factory, @Nullable ResolvableType expectedType, boolean includeNonSingletons, boolean allowEagerInit) {
        if (!(factory instanceof ListableBeanFactory) || expectedType == null) {
            return null;
        }
        ListableBeanFactory beanFactory = (ListableBeanFactory) factory;
        return beanFactory.getBeanNamesForType(expectedType, includeNonSingletons, allowEagerInit);
    }

    /**
     * Return the names of beans matching the given type (including subclasses), judging from either bean definitions or the value of {@code getObjectType} in the case of FactoryBeans
     *
     * @param factory the bean factory to look for
     * @param expectedType the class or interface to match, or {@code null} for all bean names
     *
     * @return the names of beans (or objects created by FactoryBeans) matching
     */
    @Nullable
    public static String[] getBeanNamesForType(@Nullable BeanFactory factory, @Nullable Class<?> expectedType) {
        return getBeanNamesForType(factory, expectedType, true, true);
    }

    /**
     * Returns the names of beans matching the given type (including subclasses), judging from either bean definitions or the value of {@code getObjectType} in the case of FactoryBeans
     *
     * @param factory the bean factory to look for
     * @param expectedType the class or interface to match, or {@code null} for all bean names
     * @param includeNonSingletons whether to include prototype or scoped beans too or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return the names of beans (or objects created by FactoryBeans) matching
     */
    @Nullable
    public static String[] getBeanNamesForType(@Nullable BeanFactory factory, @Nullable Class<?> expectedType, boolean includeNonSingletons, boolean allowEagerInit) {
        if (!(factory instanceof ListableBeanFactory) || expectedType == null) {
            return null;
        }
        ListableBeanFactory beanFactory = (ListableBeanFactory) factory;
        return beanFactory.getBeanNamesForType(expectedType, includeNonSingletons, allowEagerInit);
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses)
     *
     * @param factory the bean factory to look for
     * @param expectedType the class or interface to match, or {@code null} for all concrete beans
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectedType) {
        return getBeansOfType(factory, expectedType, true, true);
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses), with specified {@code beanNames}
     *
     * @param factory the bean factory to look for
     * @param expectedType the class or interface to match, or {@code null} for all concrete beans
     * @param beanNames the bean names that should match
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, @Nullable String... beanNames) {
        return getBeansOfType(factory, expectedType, ArrayUtilsWraps.asList(beanNames));
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses), with specified {@code beanNames}
     *
     * @param factory the bean factory to look for
     * @param expectedType the class or interface to match, or {@code null} for all concrete beans
     * @param beanNames the bean names that should match
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, @Nullable Collection<String> beanNames) {
        if (!(factory instanceof ListableBeanFactory) || expectedType == null || CollectionUtils.isEmpty(beanNames)) {
            return null;
        }
        Map<String, T> result = getBeansOfType(factory, expectedType);
        if (!CollectionUtils.isEmpty(result)) {
            MapPlainWraps.removeIfKey(result, element -> !beanNames.contains(element));
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses)
     *
     * @param factory the bean factory to look for
     * @param expectedType the class or interface to match, or {@code null} for all concrete beans
     * @param includeNonSingletons whether to include prototype or scoped beans too or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, boolean includeNonSingletons, boolean allowEagerInit) {
        if (!(factory instanceof ListableBeanFactory) || expectedType == null) {
            return null;
        }
        ListableBeanFactory beanFactory = (ListableBeanFactory) factory;
        try {
            return beanFactory.getBeansOfType(expectedType, includeNonSingletons, allowEagerInit);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses), with specified {@code beanNames}
     *
     * @param factory the bean factory to look for
     * @param expectedType the class or interface to match, or {@code null} for all concrete beans
     * @param includeNonSingletons whether to include prototype or scoped beans too or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     * @param beanNames the bean names that should match
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, boolean includeNonSingletons, boolean allowEagerInit, @Nullable String... beanNames) {
        return getBeansOfType(factory, expectedType, includeNonSingletons, allowEagerInit, ArrayUtilsWraps.asList(beanNames));
    }

    /**
     * Returns the bean instances that match the given object type (including subclasses), with specified {@code beanNames}
     *
     * @param factory the bean factory to look for
     * @param expectedType the class or interface to match, or {@code null} for all concrete beans
     * @param includeNonSingletons whether to include prototype or scoped beans too or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit whether stream-based access may initialize <i>lazy-init singletons</i> and <i>objects created by FactoryBeans</i> (or by factory methods with a "factory-bean" reference)
     * @param beanNames the bean names that should match
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static <T> Map<String, T> getBeansOfType(@Nullable BeanFactory factory, @Nullable Class<T> expectedType, boolean includeNonSingletons, boolean allowEagerInit, @Nullable Collection<String> beanNames) {
        if (!(factory instanceof ListableBeanFactory) || expectedType == null || CollectionUtils.isEmpty(beanNames)) {
            return null;
        }
        Map<String, T> result = getBeansOfType(factory, expectedType, includeNonSingletons, allowEagerInit);
        if (!CollectionUtils.isEmpty(result)) {
            MapPlainWraps.removeIfKey(result, element -> !beanNames.contains(element));
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    /**
     * Find all beans which are annotated with the supplied {@code annotation}
     *
     * @param factory the bean factory to look for
     * @param annotation the type of annotation to look for (at class, interface or factory method level of the specified bean)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static Map<String, Object> getBeansWithAnnotation(@Nullable BeanFactory factory, @Nullable Class<? extends Annotation> annotation) {
        if (!(factory instanceof ListableBeanFactory) || annotation == null) {
            return null;
        }
        ListableBeanFactory beanFactory = (ListableBeanFactory) factory;
        try {
            return beanFactory.getBeansWithAnnotation(annotation);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Find all beans which are annotated with the supplied {@code annotation}, casting to {@code expectedType}
     *
     * @param factory the bean factory to look for
     * @param annotation the type of annotation to look for (at class, interface or factory method level of the specified bean)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> Map<String, T> getBeansWithAnnotationAs(@Nullable BeanFactory factory, @Nullable Class<? extends Annotation> annotation, @Nullable Class<T> expectedType) {
        if (!(factory instanceof ListableBeanFactory) || ObjectUtils.anyNull(annotation, expectedType)) {
            return null;
        }
        Map<String, Object> nameBeans = getBeansWithAnnotation(factory, annotation);
        if (CollectionUtils.isEmpty(nameBeans)) {
            return null;
        }
        Map<String, T> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : nameBeans.entrySet()) {
            if (ClassUtils.isAssignableValue(expectedType, entry.getValue())) {
                result.put(entry.getKey(), ObjectUtilsWraps.castAs(entry.getValue(), expectedType));
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Find all beans which are annotated with all the supplied {@code annotations}
     *
     * @param factory the bean factory to look for
     * @param annotations the types of annotations to look for (at class, interface or factory method level of the specified bean)
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
     * @param factory the bean factory to look for
     * @param annotations the types of annotations to look for (at class, interface or factory method level of the specified bean)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static Map<String, Object> getBeansWithAllAnnotations(@Nullable BeanFactory factory, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (!(factory instanceof ListableBeanFactory) || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        Map<String, Object> result = new LinkedHashMap<>();
        ListableBeanFactory beanFactory = (ListableBeanFactory) factory;
        int index = 0;
        for (Class<? extends Annotation> annotation : annotations) {
            if (annotation == null) {
                return null;
            }
            try {
                Map<String, Object> map = beanFactory.getBeansWithAnnotation(annotation);
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
     * @param factory the bean factory to look for
     * @param annotations the types of annotations to look for (at class, interface or factory method level of the specified bean)
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
     * @param factory the bean factory to look for
     * @param annotations the types of annotations to look for (at class, interface or factory method level of the specified bean)
     *
     * @return a map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    @Nullable
    public static Map<String, Object> getBeansWithAnyAnnotations(@Nullable BeanFactory factory, @Nullable Collection<Class<? extends Annotation>> annotations) {
        if (!(factory instanceof ListableBeanFactory) || CollectionUtils.isEmpty(annotations)) {
            return null;
        }
        Map<String, Object> result = new LinkedHashMap<>();
        ListableBeanFactory beanFactory = (ListableBeanFactory) factory;
        for (Class<? extends Annotation> annotation : annotations) {
            if (annotation == null) {
                continue;
            }
            try {
                result.putAll(beanFactory.getBeansWithAnnotation(annotation));
            } catch (Exception ignored) {
            }
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    public static boolean isBeanAlias(@Nullable BeanFactory factory, @Nullable String alias) {
        if (!(factory instanceof AliasRegistry) || StringUtils.isBlank(alias)) {
            return false;
        }
        AliasRegistry registry = (AliasRegistry) factory;
        return registry.isAlias(alias);
    }

    public static boolean isBeanNameUsed(@Nullable BeanFactory factory, @Nullable String beanName) {
        if (!(factory instanceof BeanDefinitionRegistry) || StringUtils.isBlank(beanName)) {
            return false;
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
        return registry.isBeanNameInUse(beanName);
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

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean matchBeanType(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<?> expectedType) {
        if (ObjectUtils.anyNull(factory, expectedType) || StringUtils.isBlank(beanName)) {
            return false;
        }
        try {
            return factory.isTypeMatch(beanName, expectedType);
        } catch (Exception ignored) {
        }
        return false;
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean matchBeanType(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable ResolvableType expectedType) {
        if (ObjectUtils.anyNull(factory, expectedType) || StringUtils.isBlank(beanName)) {
            return false;
        }
        try {
            return factory.isTypeMatch(beanName, expectedType);
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

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean registerBeanAlias(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable String alias) throws IllegalStateException {
        if (!(factory instanceof AliasRegistry) || StringUtils.isAnyBlank(beanName, alias)) {
            return false;
        }
        AliasRegistry registry = (AliasRegistry) factory;
        registry.registerAlias(beanName, alias);
        return true;
    }

    public static boolean registerBeanAliasQuietly(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable String alias) {
        try {
            return registerBeanAlias(factory, beanName, alias);
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean registerBeanDefinition(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<?> expectedType) throws BeanDefinitionStoreException {
        return registerBeanDefinition(factory, beanName, expectedType, null);
    }

    /**
     * @see org.springframework.beans.factory.support.BeanDefinitionRegistry
     * @see org.springframework.beans.factory.support.BeanDefinitionBuilder
     */
    public static boolean registerBeanDefinition(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<?> expectedType, @Nullable String parentName) throws BeanDefinitionStoreException {
        if (!(factory instanceof BeanDefinitionRegistry) || StringUtils.isBlank(beanName) || expectedType == null) {
            return false;
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
        AbstractBeanDefinition definition = StringUtils.isBlank(parentName) ? new RootBeanDefinition() : new GenericBeanDefinition();
        definition.setBeanClass(expectedType);
        if (StringUtils.isNotBlank(parentName)) {
            definition.setParentName(parentName);
        }
        registry.registerBeanDefinition(beanName, definition);
        return true;
    }

    public static boolean registerBeanDefinitionQuietly(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<?> expectedType) {
        return registerBeanDefinitionQuietly(factory, beanName, expectedType, null);
    }

    public static boolean registerBeanDefinitionQuietly(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable Class<?> expectedType, @Nullable String parentName) {
        try {
            return registerBeanDefinition(factory, beanName, expectedType, parentName);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * @see org.springframework.beans.factory.support.BeanDefinitionRegistry
     * @see org.springframework.beans.factory.support.BeanDefinitionBuilder
     */
    public static boolean registerBeanDefinition(@Nullable BeanFactory factory, @Nullable String beanName, @Nullable BeanDefinition definition) throws BeanDefinitionStoreException {
        if (!(factory instanceof BeanDefinitionRegistry) || StringUtils.isBlank(beanName) || definition == null) {
            return false;
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
        registry.registerBeanDefinition(beanName, definition);
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
        if (!(factory instanceof SingletonBeanRegistry) || StringUtils.isBlank(beanName) || beanInstance == null) {
            return false;
        }
        SingletonBeanRegistry registry = (SingletonBeanRegistry) factory;
        registry.registerSingleton(beanName, beanInstance);
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
        if (!(factory instanceof AliasRegistry) || StringUtils.isBlank(alias)) {
            return false;
        }
        AliasRegistry registry = (AliasRegistry) factory;
        registry.removeAlias(alias);
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
        if (!(factory instanceof BeanDefinitionRegistry) || StringUtils.isBlank(beanName)) {
            return false;
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
        registry.removeBeanDefinition(beanName);
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
