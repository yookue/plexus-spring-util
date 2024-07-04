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

package com.yookue.commonplexus.springutil.context;


import java.util.Collection;
import java.util.Objects;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.CollectionUtils;


/**
 * {@link org.springframework.context.ApplicationContextInitializer} with {@link org.springframework.core.env.PropertySource}
 *
 * @author David Hsing
 * @see org.springframework.core.env.MapPropertySource
 * @see org.springframework.core.env.PropertiesPropertySource
 * @see org.springframework.core.io.support.ResourcePropertySource
 * @see "org.springframework.test.context.ContextConfiguration"
 * @see "https://www.jianshu.com/p/1c798a6c56be"
 */
@SuppressWarnings("unused")
public abstract class PropertySourceContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(@Nonnull ConfigurableApplicationContext context) {
        MutablePropertySources contextSources = context.getEnvironment().getPropertySources();
        // Add at head
        Collection<PropertySource<?>> headSources = getHeadPropertySources();
        if (!CollectionUtils.isEmpty(headSources)) {
            headSources.stream().filter(Objects::nonNull).forEach(contextSources::addFirst);
        }
        // Add at tail
        Collection<PropertySource<?>> tailSources = getTailPropertySources();
        if (!CollectionUtils.isEmpty(tailSources)) {
            tailSources.stream().filter(Objects::nonNull).forEach(contextSources::addLast);
        }
    }

    @Nullable
    protected abstract Collection<PropertySource<?>> getHeadPropertySources();

    @Nullable
    protected abstract Collection<PropertySource<?>> getTailPropertySources();
}
