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


import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;


/**
 * Utilities for {@link org.springframework.web.util.UriUtils}
 *
 * @author David Hsing
 * @see org.springframework.web.util.UriUtils
 * @see org.springframework.web.util.UrlPathHelper
 * @see org.springframework.web.util.UriComponents
 * @see org.springframework.web.util.UriComponentsBuilder
 * @see org.springframework.security.web.util.UrlUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class UriUtilsWraps {
    @Nullable
    public static String decodeUrlWithUtf8(@Nullable String url) {
        try {
            return StringUtils.isEmpty(url) ? null : URLDecoder.decode(url, StandardCharsets.UTF_8);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static String encodeUrlWithUtf8(@Nullable String url) {
        try {
            return StringUtils.isEmpty(url) ? null : URLEncoder.encode(url, StandardCharsets.UTF_8);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Returns the context path for the given request
     * <p>
     * Without schema, host, port or query string
     *
     * @param request the servlet request
     * @param endSlash whether to append a slash at the end of path or not
     *
     * @return the context path for the given request
     */
    public static String getContextPath(@Nullable HttpServletRequest request, boolean endSlash) {
        if (request == null) {
            return null;
        }
        String result = UrlPathHelper.defaultInstance.getContextPath(request);
        return (!endSlash || StringUtils.endsWith(result, CharUtils.toString(CharVariantConst.SLASH))) ? result : StringUtils.join(result, CharVariantConst.SLASH);
    }

    public static String getContextPathOriginating(@Nullable HttpServletRequest request) {
        return getContextPathOriginating(request, false);
    }

    public static String getContextPathOriginating(@Nullable HttpServletRequest request, boolean endSlash) {
        if (request == null) {
            return null;
        }
        String result = UrlPathHelper.defaultInstance.getOriginatingContextPath(request);
        return (!endSlash || StringUtils.endsWith(result, CharUtils.toString(CharVariantConst.SLASH))) ? result : StringUtils.join(result, CharVariantConst.SLASH);
    }

    /**
     * @return schema, host and port, not including the last slash
     */
    public static String getSchemaHostPort(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    /**
     * @return schema, host, port and context path, not including the last slash
     */
    public static String getSchemaHostPortContext(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getContextPath()).toString();
    }

    @Nullable
    public static String getQueryParamAsStringFromUri(@Nullable String uri, @Nullable String name) {
        return StringUtils.isAnyBlank(uri, name) ? null : MultiMapWraps.firstNonNullValue(getQueryParamsFromUri(uri), name);
    }

    @Nullable
    public static String getQueryParamAsStringFromUri(@Nullable URI uri, @Nullable String name) {
        return (uri == null || StringUtils.isBlank(name)) ? null : MultiMapWraps.firstNonNullValue(getQueryParamsFromUri(uri), name);
    }

    @Nullable
    public static String getQueryParamAsStringFromUrl(@Nullable String url, @Nullable String name) {
        return StringUtils.isAnyBlank(url, name) ? null : MultiMapWraps.firstNonNullValue(getQueryParamsFromUrl(url), name);
    }

    @Nullable
    public static String[] getQueryParamAsStringsFromUri(@Nullable String uri, @Nullable String name) {
        return StringUtils.isAnyBlank(uri, name) ? null : MultiMapWraps.getValues(getQueryParamsFromUri(uri), name);
    }

    @Nullable
    public static String[] getQueryParamAsStringsFromUri(@Nullable URI uri, @Nullable String name) {
        return (uri == null || StringUtils.isBlank(name)) ? null : MultiMapWraps.getValues(getQueryParamsFromUri(uri), name);
    }

    @Nullable
    public static String[] getQueryParamAsStringsFromUrl(@Nullable String url, @Nullable String name) {
        return StringUtils.isAnyBlank(url, name) ? null : MultiMapWraps.getValues(getQueryParamsFromUrl(url), name);
    }

    @Nullable
    public static MultiValueMap<String, String> getQueryParamsFromUri(@Nullable String uri) {
        UriComponents components = parseComponentsFromUri(uri);
        return (components == null) ? null : components.getQueryParams();
    }

    @Nullable
    public static MultiValueMap<String, String> getQueryParamsFromUri(@Nullable URI uri) {
        UriComponents components = parseComponentsFromUri(uri);
        return (components == null) ? null : components.getQueryParams();
    }

    @Nullable
    public static MultiValueMap<String, String> getQueryParamsFromUrl(@Nullable String url) {
        UriComponents components = parseComponentsFromUrl(url);
        return (components == null) ? null : components.getQueryParams();
    }

    public static String getQueryString(@Nullable HttpServletRequest request) {
        return (request == null) ? null : request.getQueryString();
    }

    public static String getQueryStringOriginating(@Nullable HttpServletRequest request) {
        return (request == null) ? null : UrlPathHelper.defaultInstance.getOriginatingQueryString(request);
    }

    /**
     * Returns the request URI for the given request
     * <p>
     * Without schema, host, port and query string, with context path
     *
     * @param request the servlet request
     *
     * @return the request URI for the given request
     */
    public static String getRequestUri(@Nullable HttpServletRequest request) {
        return (request == null) ? null : UrlPathHelper.defaultInstance.getRequestUri(request);
    }

    public static String getRequestUriOriginating(@Nullable HttpServletRequest request) {
        return (request == null) ? null : UrlPathHelper.defaultInstance.getOriginatingRequestUri(request);
    }

    /**
     * Returns the request URI and query string for the given request
     * <p>
     * Without schema, host, port, with context path and query string
     *
     * @param request the servlet request
     *
     * @return the request URI and query string for the given request
     */
    public static String getRequestUriQueryString(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String uri = UrlPathHelper.defaultInstance.getRequestUri(request), query = request.getQueryString();
        return StringUtils.isBlank(query) ? uri : StringUtils.join(uri, CharVariantConst.QUESTION, query);
    }

    public static String getRequestUriQueryStringOriginating(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String uri = UrlPathHelper.defaultInstance.getRequestUri(request), query = UrlPathHelper.defaultInstance.getOriginatingQueryString(request);
        return StringUtils.isBlank(query) ? uri : StringUtils.join(uri, CharVariantConst.QUESTION, query);
    }

    /**
     * Returns the request URL for the given request
     * <p>
     * With schema, host, port and context path, without query string
     *
     * @param request the servlet request
     *
     * @return the request URL for the given request
     */
    public static String getRequestUrl(@Nullable HttpServletRequest request) {
        return (request == null) ? null : request.getRequestURL().toString();
    }

    /**
     * Returns the request URL and query string for the given request
     * <p>
     * With schema, host, port, context path and query string
     *
     * @param request the servlet request
     *
     * @return the request URL and query string for the given request
     */
    public static String getRequestUrlQueryString(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String url = request.getRequestURL().toString(), query = request.getQueryString();
        return StringUtils.isBlank(query) ? url : StringUtils.join(url, CharVariantConst.QUESTION, query);
    }

    public static String getContextPath(@Nullable HttpServletRequest request) {
        return getContextPath(request, false);
    }

    /**
     * Returns the servlet path for the given request
     * <p>
     * Without schema, host, port, context path and query string
     *
     * @param request the servlet request
     *
     * @return the servlet path for the given request
     */
    public static String getServletPath(@Nullable HttpServletRequest request) {
        return (request == null) ? null : UrlPathHelper.defaultInstance.getServletPath(request);
    }

    public static String getServletPathOriginating(@Nullable HttpServletRequest request) {
        return (request == null) ? null : UrlPathHelper.defaultInstance.getOriginatingServletPath(request);
    }

    /**
     * Returns the servlet path and query string for the given request
     * <p>
     * Without schema, host, port, context path, with query string
     *
     * @param request the servlet request
     *
     * @return the servlet path and query string for the given request
     */
    public static String getServletPathQueryString(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String path = UrlPathHelper.defaultInstance.getServletPath(request), query = request.getQueryString();
        return StringUtils.isBlank(query) ? path : StringUtils.join(path, CharVariantConst.QUESTION, query);
    }

    @Nonnull
    public static UrlPathHelper newUrlPathHelper(@Nullable Charset defaultEncoding) {
        UrlPathHelper result = new UrlPathHelper();
        if (defaultEncoding != null) {
            result.setDefaultEncoding(defaultEncoding.name());
        }
        return result;
    }

    @Nonnull
    public static UrlPathHelper newUrlPathHelper(@Nullable String defaultEncoding) {
        UrlPathHelper result = new UrlPathHelper();
        if (StringUtils.isNotBlank(defaultEncoding)) {
            result.setDefaultEncoding(defaultEncoding);
        }
        return result;
    }

    @Nullable
    public static UriComponents parseComponentsFromUri(@Nullable String uri) {
        return StringUtils.isBlank(uri) ? null : UriComponentsBuilder.fromUriString(uri).build();
    }

    @Nullable
    public static UriComponents parseComponentsFromUri(@Nullable URI uri) {
        return (uri == null) ? null : UriComponentsBuilder.fromUri(uri).build();
    }

    @Nullable
    public static UriComponents parseComponentsFromUrl(@Nullable String url) {
        return StringUtils.isBlank(url) ? null : UriComponentsBuilder.fromHttpUrl(url).build();
    }
}
