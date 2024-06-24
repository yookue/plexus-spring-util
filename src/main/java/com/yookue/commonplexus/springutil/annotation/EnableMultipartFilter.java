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
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.yookue.commonplexus.springutil.constant.AntPathConst;
import com.yookue.commonplexus.springutil.registrar.MultipartFilterRegistrar;


/**
 * Annotation that enables a {@link org.springframework.web.multipart.support.MultipartFilter}
 *
 * @author David Hsing
 * @see org.springframework.web.multipart.support.MultipartFilter
 * @see org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration
 * @see com.yookue.commonplexus.springutil.registrar.MultipartFilterRegistrar
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Import(value = MultipartFilterRegistrar.class)
@SuppressWarnings("unused")
public @interface EnableMultipartFilter {
    /**
     * Returns the url patterns of the filter
     *
     * @return the url patterns of the filter
     */
    String[] urlPatterns() default {AntPathConst.SLASH_STAR};

    /**
     * Returns the order of the filter
     *
     * @return the order of the filter
     */
    int filterOrder() default 0;

    /**
     * Returns the multipart resolver bean name of the filter
     *
     * @return the multipart resolver bean name of the filter
     */
    String resolverName() default StringUtils.EMPTY;
}
