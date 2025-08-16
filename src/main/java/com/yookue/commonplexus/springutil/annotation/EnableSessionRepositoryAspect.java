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
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.yookue.commonplexus.springutil.registrar.SessionRepositoryAspectRegistrar;


/**
 * Annotation for enables a {@link com.yookue.commonplexus.springutil.aspect.SessionRepositoryAspect}
 *
 * @author David Hsing
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@Import(value = SessionRepositoryAspectRegistrar.class)
@SuppressWarnings("unused")
public @interface EnableSessionRepositoryAspect {
    /**
     * Returns whether to fire a {@link com.yookue.commonplexus.springutil.event.HttpSessionSavedEvent} only on attribute changes
     *
     * @return whether to fire a {@link com.yookue.commonplexus.springutil.event.HttpSessionSavedEvent} only on attribute changes
     */
    boolean fireChangedOnly() default true;

    /**
     * Returns the ignored attributes of the session
     * <p>
     * The default attributes is like {@code "creationTime"} {@code "maxInactiveInterval"}
     *
     * @return the ignored attributes of the session
     */
    String[] ignoredAttributes() default {"creationTime", "maxInactiveInterval"};    // $NON-NLS-1$ $NON-NLS-2$

    /**
     * Returns whether to remove the attribute prefix {@code "sessionAttr:"} of the session
     *
     * @return whether to remove the attribute prefix {@code "sessionAttr:"} of the session
     */
    boolean removeAttributePrefix() default true;
}
