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


/**
 * Annotation for textual request body, within {@link org.springframework.stereotype.Controller} or {@link org.springframework.web.bind.annotation.RestController}
 * <p>
 * Needs a predefined bean of {@link com.yookue.commonplexus.springutil.resolver.TextualRequestBodyResolver}
 *
 * @author David Hsing
 * @see org.springframework.web.bind.annotation.RequestBody
 * @see com.yookue.commonplexus.springutil.resolver.TextualRequestBodyResolver
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SuppressWarnings("unused")
public @interface TextualRequestBody {
    /**
     * Force decode multipart as text
     */
    boolean multipart() default false;

    /**
     * The max length of decoded text, while negative means limitless
     */
    int limitation() default -1;
}
