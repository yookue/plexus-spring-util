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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import com.yookue.commonplexus.springutil.annotation.EnableBeanUtilsConverter;
import com.yookue.commonplexus.springutil.event.listener.BeanUtilsConverterListener;


/**
 * Registrar of object converters for apache bean utils
 *
 * @author David Hsing
 */
public class BeanUtilsConverterRegistrar implements ImportAware {
    private final Class<? extends Annotation> annotation = EnableBeanUtilsConverter.class;
    private AnnotationAttributes attributes;

    @Override
    public void setImportMetadata(@Nonnull AnnotationMetadata metadata) {
        attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (attributes == null) {
            throw new IllegalArgumentException(String.format("@%s is not present on importing class: %s", annotation.getSimpleName(), metadata.getClassName()));    // $NON-NLS-1$
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public BeanUtilsConverterListener beanUtilsConverterListener() {
        BeanUtilsConverterListener result = new BeanUtilsConverterListener();
        result.setPublishEvent(attributes.getBoolean("publishEvent"));    // $NON-NLS-1$
        return result;
    }
}
