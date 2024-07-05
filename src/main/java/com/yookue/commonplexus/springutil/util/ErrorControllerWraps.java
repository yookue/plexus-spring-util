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

package com.yookue.commonplexus.springutil.util;


import java.util.Map;
import jakarta.annotation.Nullable;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;


/**
 * Utilities for {@link org.springframework.boot.web.servlet.error.ErrorController}
 *
 * @author David Hsing
 * @see jakarta.servlet.RequestDispatcher
 * @see org.springframework.boot.web.servlet.error.ErrorController
 * @see org.springframework.boot.web.servlet.error.DefaultErrorAttributes
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ErrorControllerWraps {
    @Nullable
    public static <C extends AbstractErrorController> Object getErrorAttribute(@Nullable C controller, @Nullable HttpServletRequest request, @Nullable ErrorAttributeOptions options, @Nullable String attribute) {
        if (ObjectUtils.anyNull(controller, request, options) || StringUtils.isBlank(attribute)) {
            return null;
        }
        Map<String, Object> attributes = getErrorAttributes(controller, request, options);
        return MapPlainWraps.getObject(attributes, attribute);
    }

    @Nullable
    public static <C extends AbstractErrorController, T> T getErrorAttributeAs(@Nullable C controller, @Nullable HttpServletRequest request, @Nullable ErrorAttributeOptions options, @Nullable String attribute, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(controller, request, options, expectedType) || StringUtils.isBlank(attribute)) {
            return null;
        }
        Map<String, Object> attributes = getErrorAttributes(controller, request, options);
        return MapPlainWraps.getObjectAs(attributes, attribute, expectedType);
    }

    @Nullable
    public static <C extends AbstractErrorController> ErrorAttributes getErrorAttributes(@Nullable C controller) {
        return (controller == null) ? null : ReflectionUtilsWraps.getFieldAs(AbstractErrorController.class, "errorAttributes", true, controller, ErrorAttributes.class);    // $NON-NLS-1$
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <C extends AbstractErrorController> Map<String, Object> getErrorAttributes(@Nullable C controller, @Nullable HttpServletRequest request, @Nullable ErrorAttributeOptions options) {
        if (ObjectUtils.anyNull(controller, request, options)) {
            return null;
        }
        ErrorAttributes attributes = getErrorAttributes(controller);
        return (attributes == null) ? null : attributes.getErrorAttributes(new ServletWebRequest(request), options);
    }

    @Nullable
    public static <C extends BasicErrorController> ErrorAttributeOptions getErrorAttributeOptions(@Nullable C controller, @Nullable HttpServletRequest request, @Nullable MediaType type) {
        if (ObjectUtils.anyNull(controller, request, type)) {
            return null;
        }
        return ReflectionUtilsWraps.invokeMethodAs(BasicErrorController.class, "getErrorAttributeOptions", true, controller, ArrayUtilsWraps.toArray(request, type), ErrorAttributeOptions.class);    // $NON-NLS-1$
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <C extends AbstractErrorController> Throwable getErrorCause(@Nullable C controller, @Nullable HttpServletRequest request) {
        if (ObjectUtils.anyNull(controller, request)) {
            return null;
        }
        Throwable throwable = WebUtilsWraps.getRequestAttributeAs(request, RequestDispatcher.ERROR_EXCEPTION, Throwable.class);
        if (throwable != null) {
            return throwable;
        }
        ErrorAttributes attributes = getErrorAttributes(controller);
        return (attributes == null) ? null : attributes.getError(new ServletWebRequest(request));
    }

    @Nullable
    public static HttpStatus getErrorStatus(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Integer status = ObjectUtilsWraps.castAs(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE), Integer.class);
        if (status != null) {
            try {
                return HttpStatus.valueOf(status);
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}
