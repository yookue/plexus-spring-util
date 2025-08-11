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


import jakarta.annotation.Nonnull;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.yookue.commonplexus.springutil.converter.spring.StringToJdkDateConverter;
import com.yookue.commonplexus.springutil.converter.spring.StringToLocalDateConverter;
import com.yookue.commonplexus.springutil.converter.spring.StringToLocalDateTimeConverter;
import com.yookue.commonplexus.springutil.converter.spring.StringToLocalTimeConverter;
import com.yookue.commonplexus.springutil.converter.spring.StringToSqlDateConverter;


/**
 * Registrar of object converters for spring bean utils
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public class SpringBeanConverterRegistrar implements WebMvcConfigurer {
    @Override
    public void addFormatters(@Nonnull FormatterRegistry registry) {
        registry.addConverter(new StringToJdkDateConverter());
        registry.addConverter(new StringToSqlDateConverter());
        registry.addConverter(new StringToLocalDateConverter());
        registry.addConverter(new StringToLocalDateTimeConverter());
        registry.addConverter(new StringToLocalTimeConverter());
    }
}
