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
import javax.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import com.yookue.commonplexus.springutil.annotation.EnableJacksonMimeConverter;
import com.yookue.commonplexus.springutil.jackson.processor.JacksonJsonMediaTypeProcessor;
import com.yookue.commonplexus.springutil.jackson.processor.JacksonXmlMediaTypeProcessor;


/**
 * Registrar of additional mime types for jackson message converter
 *
 * @author David Hsing
 * @reference "https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-customize-the-jackson-objectmapper"
 * @reference "https://www.baeldung.com/spring-boot-jsoncomponent"
 * @see org.springframework.context.annotation.ImportAware
 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 * @see org.springframework.boot.autoconfigure.http.JacksonHttpMessageConvertersConfiguration
 */
@SuppressWarnings({"JavadocReference", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class JacksonMimeConverterRegistrar implements ImportAware {
    private final Class<? extends Annotation> annotation = EnableJacksonMimeConverter.class;
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
    public JacksonJsonMediaTypeProcessor jacksonJsonMediaTypeProcessor() {
        return !attributes.getBoolean("json") ? null : new JacksonJsonMediaTypeProcessor();    // $NON-NLS-1$
    }

    @Bean
    @ConditionalOnMissingBean
    public JacksonXmlMediaTypeProcessor jacksonXmlMediaTypeProcessor() {
        return !attributes.getBoolean("xml") ? null : new JacksonXmlMediaTypeProcessor();    // $NON-NLS-1$
    }
}
