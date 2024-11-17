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
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertyState;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.constant.AssertMessageConst;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;


/**
 * Utilities for {@link org.springframework.boot.context.properties.bind.Binder}
 *
 * @author David Hsing
 * @see org.springframework.boot.context.properties.bind.Binder
 * @see org.springframework.boot.context.properties.source.ConfigurationPropertyName
 * @see org.springframework.boot.context.properties.source.ConfigurationPropertySource
 * @see org.springframework.core.env.AbstractEnvironment
 * @see org.springframework.core.env.AbstractPropertyResolver
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public abstract class PropertyBinderWraps {
    /**
     * Returns whether the environment contains the given property/prefix or not
     *
     * @param environment the environment of {@link org.springframework.core.env.ConfigurableEnvironment}
     * @param property the property name or property prefix, should be a valid name of {@link org.springframework.boot.context.properties.source.ConfigurationPropertyName}
     *
     * @return whether the environment contains the given property/prefix or not
     */
    public static boolean contains(@Nullable Environment environment, @Nullable String property) {
        if (!(environment instanceof ConfigurableEnvironment) || StringUtils.isBlank(property)) {
            return false;
        }
        ConfigurationPropertyName name = ConfigurationPropertyName.ofIfValid(property);
        if (name == null) {
            return false;
        }
        MutablePropertySources sources = ((ConfigurableEnvironment) environment).getPropertySources();
        Assert.notNull(sources, AssertMessageConst.NOT_NULL);
        for (PropertySource<?> source : sources) {
            if (source instanceof OriginTrackedMapPropertySource) {
                ConfigurationPropertySource alias = ConfigurationPropertySource.from(source);
                if (alias.containsDescendantOf(name) == ConfigurationPropertyState.PRESENT) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsAll(@Nullable Environment environment, @Nullable String... properties) {
        return containsAll(environment, ArrayUtilsWraps.asList(properties));
    }

    public static boolean containsAll(@Nullable Environment environment, @Nullable Collection<String> properties) {
        if (!(environment instanceof ConfigurableEnvironment) || CollectionUtils.isEmpty(properties)) {
            return false;
        }
        return properties.stream().allMatch(element -> contains(environment, element));
    }

    public static boolean containsAny(@Nullable Environment environment, @Nullable String... properties) {
        return containsAny(environment, ArrayUtilsWraps.asList(properties));
    }

    public static boolean containsAny(@Nullable Environment environment, @Nullable Collection<String> properties) {
        if (!(environment instanceof ConfigurableEnvironment) || CollectionUtils.isEmpty(properties)) {
            return false;
        }
        return properties.stream().anyMatch(element -> contains(environment, element));
    }

    /**
     * Returns whether the given properties are equal to each other or not
     *
     * @param property1 the first property
     * @param property2 the second property
     *
     * @return whether the given properties are equal to each other or not
     *
     * @reference "https://developer.aliyun.com/article/654834"
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean equals(@Nullable CharSequence property1, @Nullable CharSequence property2) {
        if (StringUtils.equals(property1, property2)) {
            return true;
        }
        if (StringUtils.isAnyEmpty(property1, property2)) {
            return false;
        }
        int length1 = property1.length();
        int length2 = property2.length();
        int offset1 = 0, offset2 = 0;
        int index1 = offset1;
        int index2 = offset2;
        while (index1 < length1 - offset1) {
            if (index2 >= length2 - offset2) {
                return false;
            }
            char character1 = Character.toLowerCase(property1.charAt(index1));
            char character2 = Character.toLowerCase(property2.charAt(index2));
            if (character1 == CharVariantConst.HYPHEN || character1 == CharVariantConst.UNDERSCORE) {
                index1++;
            } else if (character2 == CharVariantConst.HYPHEN || character2 == CharVariantConst.UNDERSCORE) {
                index2++;
            } else if (character1 != character2) {
                return false;
            } else {
                index1++;
                index2++;
            }
        }
        while (index2 < length2 - offset2) {
            char ch = property2.charAt(index2++);
            if (ch != CharVariantConst.HYPHEN && ch != CharVariantConst.UNDERSCORE) {
                return false;
            }
        }
        return true;
    }
}
