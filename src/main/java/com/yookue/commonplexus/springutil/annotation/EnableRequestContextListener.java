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
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.yookue.commonplexus.springutil.registrar.RequestContextListenerRegistrar;


/**
 * Annotation that enables a {@link org.springframework.web.context.request.RequestContextListener}
 * <p>
 * With this listener, the {@link jakarta.servlet.http.HttpServletRequest} can be autowired anywhere<br/>
 * And also, the {@link com.yookue.commonplexus.springutil.util.WebUtilsWraps#getContextServletRequest} can be performed anywhere
 *
 * @author David Hsing
 * @see org.springframework.web.context.request.RequestContextListener
 * @see com.yookue.commonplexus.springutil.registrar.RequestContextListenerRegistrar
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Import(value = RequestContextListenerRegistrar.class)
@SuppressWarnings("unused")
public @interface EnableRequestContextListener {
    /**
     * Returns the listener registration order
     *
     * @return the listener registration order
     */
    int listenerOrder() default 0;
}
