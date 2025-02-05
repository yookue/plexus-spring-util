/*
 * Copyright (c) 2025 Yookue Ltd. All rights reserved.
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

package com.yookue.commonplexus.springutil.cache;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.commons.lang3.StringUtils;


/**
 * Annotation that formats local hash code key generator for spring cache
 *
 * <p>
 * Specifies the local cache key generator format
 *
 * @author David Hsing
 * @see com.yookue.commonplexus.springutil.annotation.EnableHashCodeKeyGenerator
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
@SuppressWarnings("unused")
public @interface HashCodeKeyFormat {
    /**
     * Returns the prefix of the generated keys
     *
     * @return the prefix of the generated keys
     */
    String prefix() default StringUtils.EMPTY;

    /**
     * Returns the suffix of the generated keys
     *
     * @return the suffix of the generated keys
     */
    String suffix() default StringUtils.EMPTY;

    /**
     * Returns whether to prepend the class name or not
     *
     * @return whether to prepend the class name or not
     */
    boolean clazzName() default false;

    /**
     * Returns whether to use the short class name when {@code clazzName} is {@code true}
     *
     * @return whether to use the short class name when {@code clazzName} is {@code true}
     */
    boolean shortClazzName() default true;

    /**
     * Returns whether to use the hash code of the method or not
     *
     * @return whether to use the hash code of the method or not
     */
    boolean methodHash() default false;

    /**
     * Returns whether to wrap the params with parentheses or not
     *
     * @return whether to wrap the params with parentheses or not
     */
    boolean paramParentheses() default true;
}
