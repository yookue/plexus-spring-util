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

package com.yookue.commonplexus.springutil.resolver;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.ContentCachingRequestWrapper;
import com.yookue.commonplexus.springutil.annotation.TextualRequestBody;
import com.yookue.commonplexus.springutil.util.HttpHeaderWraps;
import com.yookue.commonplexus.springutil.util.WebUtilsWraps;


/**
 * {@link org.springframework.web.method.support.HandlerMethodArgumentResolver} for resolving {@link com.yookue.commonplexus.springutil.annotation.TextualRequestBody}
 *
 * @author David Hsing
 * @reference "https://sadique.io/blog/2016/01/30/using-custom-arguments-in-spring-mvc-controllers/"
 * @reference "https://github.com/jkschneider/spring-mvc-requestbodypart"
 * @reference "https://github.com/chujianyun/Spring-MultiRequestBody"
 */
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class TextualRequestBodyResolver implements HandlerMethodArgumentResolver {
    public static final String REQUEST_ATTRIBUTE = "TEXTUAL_REQUEST_BODY";    // $NON-NLS-1$

    @Override
    public boolean supportsParameter(@Nonnull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TextualRequestBody.class);
    }

    @Override
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer container, @Nonnull NativeWebRequest request, @Nullable WebDataBinderFactory factory) {
        Object attribute = request.getAttribute(REQUEST_ATTRIBUTE, NativeWebRequest.SCOPE_REQUEST);
        if (attribute instanceof String) {
            return attribute;
        }
        TextualRequestBody annotation = parameter.getParameterAnnotation(TextualRequestBody.class);
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);
        if (ObjectUtils.anyNull(annotation, servletRequest) || (!annotation.multipart() && HttpHeaderWraps.isContentTypeMultipart(servletRequest))) {
            return null;
        }
        ContentCachingRequestWrapper wrapper;
        if (servletRequest instanceof ContentCachingRequestWrapper) {
            wrapper = (ContentCachingRequestWrapper) servletRequest;
        } else {
            wrapper = (annotation.limitation() >= 0) ? new ContentCachingRequestWrapper(servletRequest, annotation.limitation()) : new ContentCachingRequestWrapper(servletRequest);
        }
        String content = WebUtilsWraps.getContentAsStringQuietly(wrapper);
        if (StringUtils.isNotEmpty(content)) {
            request.setAttribute(REQUEST_ATTRIBUTE, content, NativeWebRequest.SCOPE_REQUEST);
        }
        return content;
    }
}
