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

package com.yookue.commonplexus.springutil.advice;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.yookue.commonplexus.javaseutil.constant.LogMessageConst;
import com.yookue.commonplexus.springutil.annotation.TextualResponseBody;
import lombok.extern.slf4j.Slf4j;


/**
 * {@link org.springframework.web.bind.annotation.ControllerAdvice} for textual response body
 *
 * @author David Hsing
 * @reference "https://blog.csdn.net/jiangzeyin_/article/details/81094258"
 */
@Slf4j
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class TextualResponseBodyAdvice implements ResponseBodyAdvice<String> {
    @Override
    public String beforeBodyWrite(@Nullable String body, @Nonnull MethodParameter parameter, @Nonnull MediaType mediaType, @Nonnull Class<? extends HttpMessageConverter<?>> converter, @Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response) {
        if (StringUtils.isEmpty(body) || supports(parameter, converter)) {
            return body;
        }
        TextualResponseBody annotation = parameter.getMethodAnnotation(TextualResponseBody.class);
        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(parameter.getDeclaringClass(), TextualResponseBody.class);
        }
        if (annotation != null && StringUtils.isNotBlank(annotation.contentType())) {
            try {
                MediaType contentType = MediaType.parseMediaType(annotation.contentType());
                response.getHeaders().setContentType(contentType);
            } catch (Exception ex) {
                log.error(LogMessageConst.EXCEPTION_OCCURRED, ex);
            }
        }
        return body;
    }

    @Override
    public boolean supports(@Nonnull MethodParameter parameter, @Nonnull Class<? extends HttpMessageConverter<?>> clazz) {
        return parameter.hasMethodAnnotation(TextualResponseBody.class) || AnnotatedElementUtils.hasAnnotation(parameter.getDeclaringClass(), TextualResponseBody.class);
    }
}
