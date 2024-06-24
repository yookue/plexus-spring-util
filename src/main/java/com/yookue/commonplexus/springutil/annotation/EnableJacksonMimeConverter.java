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

package com.yookue.commonplexus.springutil.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yookue.commonplexus.springutil.registrar.JacksonMimeConverterRegistrar;


/**
 * Annotation that enables additional mime types for jackson message converter
 *
 * @author David Hsing
 * @see org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
 * @see com.yookue.commonplexus.springutil.registrar.JacksonMimeConverterRegistrar
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(value = ObjectMapper.class)
@Import(value = JacksonMimeConverterRegistrar.class)
@SuppressWarnings("unused")
public @interface EnableJacksonMimeConverter {
    /**
     * Returns whether enable the json media type ("application/*+json") or not
     *
     * @return whether enable the json media type ("application/*+json") or not
     */
    boolean json() default true;

    /**
     * Returns whether enable the xml media type ("application/*+xml") or not
     *
     * @return whether enable the xml media type ("application/*+xml") or not
     */
    boolean xml() default true;
}
