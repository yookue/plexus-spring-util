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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.yookue.commonplexus.javaseutil.constant.TemporalFormatConst;
import com.yookue.commonplexus.springutil.registrar.JacksonTemporalFormatRegistrar;


/**
 * Annotation that customizes date time formats of jackson
 *
 * @author David Hsing
 * @see com.yookue.commonplexus.springutil.registrar.JacksonTemporalFormatRegistrar
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Configuration(proxyBeanMethods = false)
@Import(value = JacksonTemporalFormatRegistrar.class)
@SuppressWarnings("unused")
public @interface JacksonTemporalFormat {
    /**
     * Returns the date format
     *
     * @return the date format
     */
    String dateFormat() default TemporalFormatConst.ISO_YYYYMMDD;

    /**
     * Returns the time format
     *
     * @return the time format
     */
    String timeFormat() default TemporalFormatConst.ISO_HHMMSS;

    /**
     * Returns the date time format
     *
     * @return the date time format
     */
    String dateTimeFormat() default TemporalFormatConst.ISO_YYYYMMDD_HHMMSS;
}
