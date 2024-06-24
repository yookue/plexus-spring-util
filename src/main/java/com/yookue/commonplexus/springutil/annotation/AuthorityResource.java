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
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * Annotation for marking a controller/method as an authority resource
 *
 * @author David Hsing
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier
@SuppressWarnings("unused")
public @interface AuthorityResource {
    /**
     * The identifier of the resource
     * <p>
     * Then this is blank on a controller, it could be the simple name of the controller class
     * <p>
     * Then this is blank on a method, it could be the simple name of the method
     *
     * @return the identifier of the resource
     */
    String identifier() default StringUtils.EMPTY;

    /**
     * The description of the resource
     * <p>
     * This could be the message code of a resource bundle, when {@code multilingual} is true
     *
     * @return the description of the resource
     */
    String description();

    /**
     * Whether the {@code description} is a message code or not
     *
     * @return whether the {@code description} is a message code or not
     */
    boolean multilingual() default false;
}
