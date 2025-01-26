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
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.yookue.commonplexus.springutil.cache.PlainParamKeyGenerator;
import com.yookue.commonplexus.springutil.registrar.PlainParamKeyGeneratorRegistrar;


/**
 * Annotation that enables plain param key generator for spring cache
 *
 * @author David Hsing
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(value = KeyGenerator.class)
@Import(value = PlainParamKeyGeneratorRegistrar.class)
@SuppressWarnings("unused")
public @interface EnablePlainParamKeyGenerator {
    /**
     * Returns the max key length of the generated keys
     *
     * @return the max key length of the generated keys
     */
    int maxKeyLength() default PlainParamKeyGenerator.DEFAULT_KEY_LENGTH;

    /**
     * Returns whether to wrap the params with parentheses or not
     *
     * @return whether to wrap the params with parentheses or not
     */
    boolean wrapParentheses() default true;

    /**
     * Returns whether to append the hash code of the params or not
     *
     * @return whether to append the hash code of the params or not
     */
    boolean appendHashCode() default true;
}
