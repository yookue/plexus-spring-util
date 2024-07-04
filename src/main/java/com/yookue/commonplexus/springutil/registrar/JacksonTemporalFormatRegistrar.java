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
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import com.yookue.commonplexus.javaseutil.constant.AssertMessageConst;
import com.yookue.commonplexus.springutil.annotation.JacksonTemporalFormat;
import com.yookue.commonplexus.springutil.util.JacksonConfigWraps;


/**
 * Registrar of date time formats for jackson
 *
 * @author David Hsing
 * @see org.springframework.context.annotation.ImportAware
 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 */
public class JacksonTemporalFormatRegistrar implements ImportAware {
    private final Class<? extends Annotation> annotation = JacksonTemporalFormat.class;
    private AnnotationAttributes attributes;

    @Override
    public void setImportMetadata(@Nonnull AnnotationMetadata metadata) {
        attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (attributes == null) {
            throw new IllegalArgumentException(String.format("@%s is not present on importing class: %s", annotation.getSimpleName(), metadata.getClassName()));    // $NON-NLS-1$
        }
    }

    /**
     * {@link org.springframework.http.converter.json.Jackson2ObjectMapperBuilder} can be customized by one or more {@link org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer} beans
     * <p>
     * These customizer beans can be ordered (Spring own customizer has an order of 0)
     *
     * @see com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
     * @see com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
     * @see com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
     */
    @Bean
    @Order(value = 100)
    public Jackson2ObjectMapperBuilderCustomizer jacksonDateTimeCustomizer(@Nonnull JacksonProperties properties) {
        Assert.notNull(attributes, AssertMessageConst.NOT_NULL);
        String dateFormat = StringUtils.defaultIfBlank(attributes.getString("dateFormat"), properties.getDateFormat());    // $NON-NLS-1$
        String timeFormat = attributes.getString("timeFormat");    // $NON-NLS-1$
        String dateTimeFormat = attributes.getString("dateTimeFormat");    // $NON-NLS-1$
        return JacksonConfigWraps.dateTimeCustomizer(dateFormat, timeFormat, dateTimeFormat, properties.getTimeZone());
    }
}
