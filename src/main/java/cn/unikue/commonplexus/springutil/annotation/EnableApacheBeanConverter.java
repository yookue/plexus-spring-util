/*
 * Copyright (c) 2016 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.commons.beanutils2.Converter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import cn.unikue.commonplexus.springutil.registrar.ApacheBeanConverterRegistrar;


/**
 * Annotation that enables object converters for apache bean utils
 * <p>
 * This annotation is used by utilities with {@link org.springframework.beans.BeanUtils#copyProperties(java.lang.Object, java.lang.Object)}
 *
 * @author David Hsing
 *
 * @see org.apache.commons.beanutils2.Converter
 * @see cn.unikue.commonplexus.springutil.registrar.ApacheBeanConverterRegistrar
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(value = Converter.class)
@Import(value = ApacheBeanConverterRegistrar.class)
@SuppressWarnings("unused")
public @interface EnableApacheBeanConverter {
}
