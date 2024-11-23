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

package com.yookue.commonplexus.springutil.interceptor;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.yookue.commonplexus.javaseutil.constant.HttpHeaderConst;
import com.yookue.commonplexus.javaseutil.constant.StringVariantConst;
import com.yookue.commonplexus.javaseutil.identity.UtilUuidGenerator;
import com.yookue.commonplexus.springutil.util.HttpHeaderWraps;


/**
 * {@link org.springframework.web.servlet.HandlerInterceptor} for tracing Mapped Diagnostic Context
 * <p>
 * Usage: include <code>%X{traceId}</code> as pattern in your logging configuration
 *
 * @author David Hsing
 */
public class MdcTraceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        String traceId = HttpHeaderWraps.getHeader(request, HttpHeaderConst.X_TRACE_ID, UtilUuidGenerator.getPopularUuid());
        request.setAttribute(StringVariantConst.TRACE_ID, traceId);
        MDC.put(StringVariantConst.TRACE_ID, traceId);
        return true;
    }

    @Override
    public void postHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, @Nullable ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, @Nullable Exception cause) {
        request.removeAttribute(StringVariantConst.TRACE_ID);
        MDC.remove(StringVariantConst.TRACE_ID);
    }
}
