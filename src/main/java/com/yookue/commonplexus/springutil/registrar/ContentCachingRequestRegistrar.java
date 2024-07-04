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

package com.yookue.commonplexus.springutil.registrar;


import java.lang.annotation.Annotation;
import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.springutil.annotation.EnableContentCachingRequest;
import com.yookue.commonplexus.springutil.filter.ContentCachingRequestFilter;


/**
 * Registrar of a {@link com.yookue.commonplexus.springutil.filter.ContentCachingRequestFilter}
 *
 * @author David Hsing
 */
public class ContentCachingRequestRegistrar implements ImportAware {
    private final Class<? extends Annotation> annotation = EnableContentCachingRequest.class;
    private AnnotationAttributes attributes;

    @Override
    public void setImportMetadata(@Nonnull AnnotationMetadata metadata) {
        attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (attributes == null) {
            throw new IllegalArgumentException(String.format("@%s is not present on importing class: %s", annotation.getSimpleName(), metadata.getClassName()));    // $NON-NLS-1$
        }
    }

    @Bean
    @ConditionalOnMissingBean(value = ContentCachingRequestFilter.class, parameterizedContainer = FilterRegistrationBean.class)
    public FilterRegistrationBean<ContentCachingRequestFilter> contentCachingRequestFilterRegistration() {
        ContentCachingRequestFilter filter = new ContentCachingRequestFilter();
        filter.setCacheMultipart(attributes.getBoolean("cacheMultipart"));    // $NON-NLS-1$
        filter.setCacheLimit(attributes.getNumber("cacheLimit"));    // $NON-NLS-1$
        filter.addExcludedPath(attributes.getStringArray("urlExcludes"));    // $NON-NLS-1$
        FilterRegistrationBean<ContentCachingRequestFilter> result = new FilterRegistrationBean<>(filter);
        result.setUrlPatterns(ArrayUtilsWraps.asList(attributes.getStringArray("urlPatterns")));    // $NON-NLS-1$
        result.setOrder(attributes.getNumber("filterOrder"));    // $NON-NLS-1$
        return result;
    }
}
