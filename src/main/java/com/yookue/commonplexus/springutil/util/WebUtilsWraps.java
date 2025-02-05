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


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import jakarta.annotation.Nullable;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestWrapper;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletResponseWrapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeType;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.WebAsyncManager;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;
import com.yookue.commonplexus.javaseutil.constant.AssertMessageConst;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.constant.HttpHeaderConst;
import com.yookue.commonplexus.javaseutil.constant.StringVariantConst;
import com.yookue.commonplexus.javaseutil.util.CharsetPlainWraps;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.javaseutil.util.ConstructorUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.EnumerationPlainWraps;
import com.yookue.commonplexus.javaseutil.util.FileUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.InetAddressWraps;
import com.yookue.commonplexus.javaseutil.util.IoUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.ListPlainWraps;
import com.yookue.commonplexus.javaseutil.util.LocalePlainWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.NumberUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;
import com.yookue.commonplexus.springutil.constant.SpringAttributeConst;


/**
 * Utilities for {@link org.springframework.web.util.WebUtils}
 *
 * @author David Hsing
 * @see org.springframework.web.util.WebUtils
 * @see org.springframework.web.cors.CorsUtils
 * @see org.springframework.web.context.request.async.WebAsyncUtils
 * @see org.springframework.web.servlet.support.RequestContextUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue", "JavadocReference", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public abstract class WebUtilsWraps {
    /**
     * Add an attribute to a http request instance
     *
     * @param request a http request instance
     * @param response a http response instance, must not be null when {@code input} is true
     * @param name the attribute name
     * @param value the attribute value
     *
     * @see org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap
     * @see org.springframework.web.servlet.DispatcherServlet#doService
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void addInputFlashAttribute(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable String name, @Nullable Object value) {
        if (ObjectUtils.anyNull(request, response) || StringUtils.isBlank(name)) {
            return;
        }
        FlashMapManager manager = RequestContextUtils.getFlashMapManager(request);
        if (manager == null) {
            return;
        }
        FlashMap map = manager.retrieveAndUpdate(request, response);
        if (map == null) {
            map = new FlashMap();
        }
        map.put(name, value);
        request.setAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE, map);
    }

    public static void addOutputFlashAttribute(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Object value) {
        if (request == null || StringUtils.isBlank(name)) {
            return;
        }
        FlashMap map = RequestContextUtils.getOutputFlashMap(request);
        Assert.notNull(map, AssertMessageConst.NOT_NULL);
        map.put(name, value);
        request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, map);
    }

    public static String attachDispositionFilename(@Nullable String filename) {
        return attachDispositionFilename(filename, null);
    }

    /**
     * @see org.springframework.http.ContentDisposition
     */
    @Nullable
    public static String attachDispositionFilename(@Nullable String filename, @Nullable Charset encoding) {
        return StringUtils.isBlank(filename) ? null : ContentDisposition.attachment().filename(filename, ObjectUtils.defaultIfNull(encoding, StandardCharsets.UTF_8)).build().toString();
    }

    public static void closePrintWriter(@Nullable HttpServletResponse response) throws IOException {
        if (response != null) {
            response.getWriter().close();
        }
    }

    public static void closePrintWriterQuietly(@Nullable HttpServletResponse response) {
        try {
            closePrintWriter(response);
        } catch (Exception ignored) {
        }
    }

    public static void forwardRequest(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable String path) throws ServletException, IOException {
        forwardRequest(request, response, path, null, null);
    }

    public static void forwardRequest(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable String path, @Nullable Map<String, Object> attributes) throws ServletException, IOException {
        forwardRequest(request, response, path, attributes, null);
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void forwardRequest(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable String path, @Nullable Map<String, Object> attributes, @Nullable Integer status) throws ServletException, IOException {
        if (ObjectUtils.anyNull(request, response) || response.isCommitted() || StringUtils.isBlank(path)) {
            return;
        }
        MapPlainWraps.forEach(attributes, request::setAttribute, (key, value) -> StringUtils.isNotBlank(key));
        NumberUtilsWraps.ifPositive(status, element -> response.setStatus(status));
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    public static void forwardRequestQuietly(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable String path) {
        forwardRequestQuietly(request, response, path, null, null);
    }

    public static void forwardRequestQuietly(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable String path, @Nullable Map<String, Object> attributes) {
        forwardRequestQuietly(request, response, path, attributes, null);
    }

    public static void forwardRequestQuietly(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable String path, @Nullable Map<String, Object> attributes, @Nullable Integer status) {
        try {
            forwardRequest(request, response, path, attributes, status);
        } catch (Exception ignored) {
        }
    }

    @Nullable
    public static HttpSession getContextSession() {
        HttpServletRequest request = getContextServletRequest();
        return (request == null) ? null : request.getSession(false);
    }

    @Nullable
    public static String getContextSessionId() {
        return getContextSessionId(false);
    }

    @Nullable
    public static String getContextSessionId(boolean createIfNull) {
        return getSessionId(getContextServletRequest(), createIfNull);
    }

    @Nullable
    public static HttpServletRequest getContextServletRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (attributes instanceof ServletRequestAttributes alias) ? alias.getRequest() : null;
    }

    @Nullable
    public static HttpServletResponse getContextServletResponse() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (attributes instanceof ServletRequestAttributes alias) ? alias.getResponse() : null;
    }

    /**
     * @reference "http://blog.csdn.net/lidachao01/article/details/53638718"
     */
    public static byte[] getContentAsBytes(@Nullable HttpServletRequest request) throws IOException {
        if (request == null || request.getContentLength() <= 0) {
            return null;
        }
        ContentCachingRequestWrapper wrapper = getServletRequestWrapper(request, ContentCachingRequestWrapper.class);
        return wrapper != null ? wrapper.getContentAsByteArray() : StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Nullable
    public static byte[] getContentAsBytesQuietly(@Nullable HttpServletRequest request) {
        try {
            return getContentAsBytes(request);
        } catch (IOException ignored) {
        }
        return null;
    }

    /**
     * @reference "http://blog.csdn.net/lidachao01/article/details/53638718"
     * @see org.springframework.web.util.ContentCachingRequestWrapper
     */
    public static String getContentAsString(@Nullable HttpServletRequest request) throws IOException {
        if (request == null) {
            return null;
        }
        String charset = CharsetPlainWraps.defaultCharsetName(request.getCharacterEncoding());
        ContentCachingRequestWrapper wrapper = getServletRequestWrapper(request, ContentCachingRequestWrapper.class);
        if (wrapper == null) {
            return IoUtilsWraps.toString(request.getInputStream(), charset);
        }
        byte[] caches = wrapper.getContentAsByteArray();
        return ArrayUtils.isEmpty(caches) ? IoUtilsWraps.toString(request.getInputStream(), charset) : IoUtilsWraps.toString(caches, charset);
    }

    @Nullable
    public static String getContentAsStringQuietly(@Nullable HttpServletRequest request) {
        try {
            return getContentAsString(request);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getContentAsStringTrimming(@Nullable HttpServletRequest request, boolean emptyAsNull) throws IOException {
        if (request == null) {
            return null;
        }
        String result = getContentAsString(request);
        return emptyAsNull ? StringUtils.trimToNull(result) : StringUtils.trim(result);
    }

    @Nullable
    public static String getContentAsStringTrimmingQuietly(@Nullable HttpServletRequest request, boolean emptyAsNull) {
        try {
            return getContentAsStringTrimming(request, emptyAsNull);
        } catch (IOException ignored) {
        }
        return null;
    }

    /**
     * @see org.springframework.web.servlet.support.RequestContextUtils#getInputFlashMap
     */
    public static FlashMap getInputFlashMap(@Nullable HttpServletRequest request) {
        return (request == null) ? null : (FlashMap) request.getAttribute(DispatcherServlet.INPUT_FLASH_MAP_ATTRIBUTE);
    }

    public static FlashMap getOutputFlashMap(@Nullable HttpServletRequest request) {
        return (request == null) ? null : RequestContextUtils.getOutputFlashMap(request);
    }

    public static Locale getLocaleFromCookie(@Nullable HttpServletRequest request, @Nullable String cookieName) {
        return ListPlainWraps.getFirst(getLocalesFromCookie(request, cookieName));
    }

    public static Locale getLocaleFromSession(@Nullable HttpServletRequest request, @Nullable String sessionName) {
        if (request == null || StringUtils.isBlank(sessionName)) {
            return null;
        }
        Object locale = WebUtils.getSessionAttribute(request, sessionName);
        if (locale instanceof Locale alias) {
            return alias;
        } else if (locale instanceof String alias) {
            return LocaleParserWraps.parse(alias);
        }
        return null;
    }

    /**
     * Return a language priority list consisting of language ranges included in the given {@code ranges} and their equivalent language ranges if available
     *
     * @param range a list of comma-separated language ranges or a list of language ranges in the form of the "Accept-Language" header, defined in <a href="http://tools.ietf.org/html/rfc2616">RFC 2616</a>
     * <pre>
     *     "zh,en;q=0.8"
     * </pre>
     *
     * @return a language priority list consisting of language ranges included in the given {@code ranges} and their equivalent language ranges if available
     *
     * @see org.springframework.http.HttpHeaders#getAcceptLanguageAsLocales
     */
    @Nullable
    public static List<Locale> getLocalesFromAcceptLanguage(@Nullable String range) {
        List<Locale.LanguageRange> locales = LocalePlainWraps.parseLanguageRangeQuietly(range);
        return CollectionUtils.isEmpty(locales) ? null : locales.stream().map(locale -> Locale.forLanguageTag(locale.getRange())).collect(Collectors.toList());
    }

    public static List<Locale> getLocalesFromCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        if (request == null || StringUtils.isBlank(name)) {
            return null;
        }
        Cookie cookie = WebUtils.getCookie(request, name);
        return (cookie == null) ? null : getLocalesFromAcceptLanguage(cookie.getValue());
    }

    public static String getRealPath(@Nullable ServletContext context, @Nullable String path) {
        if (context == null) {
            return null;
        }
        try {
            return WebUtils.getRealPath(context, StringUtils.defaultString(path));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * @reference "http://www.cnblogs.com/ITtangtang/p/3927768.html"
     * @see "org.apache.http.conn.util.InetAddressWraps"
     * @see "com.alibaba.druid.util.DruidWebUtils#getRemoteAddr(HttpServletRequest)"
     */
    public static String getRemoteAddress(@Nullable HttpServletRequest request) throws UnknownHostException {
        if (request == null) {
            return null;
        }
        String result = request.getHeader(HttpHeaderConst.X_FORWARDED_FOR);
        if (StringUtils.contains(result, CharVariantConst.COMMA)) {
            result = StringUtils.substringBefore(result, CharVariantConst.COMMA);
        }
        if (StringUtils.isBlank(result) || StringUtils.equalsIgnoreCase(result, StringVariantConst.UNKNOWN)) {
            result = RequestParamWraps.firstNonBlankHeader(request, HttpHeaderConst.PROXY_CLIENT_IP, HttpHeaderConst.WL_PROXY_CLIENT_IP, HttpHeaderConst.X_REAL_IP);
        }
        if (StringUtils.isBlank(result) || StringUtils.equalsIgnoreCase(result, StringVariantConst.UNKNOWN)) {
            result = request.getRemoteAddr();
        }
        return InetAddressWraps.toLanAddress(result);
    }

    @Nullable
    public static String getRemoteAddressQuietly(@Nullable HttpServletRequest request) {
        try {
            return getRemoteAddress(request);
        } catch (UnknownHostException ignored) {
        }
        return null;
    }

    public static Object getRequestAttribute(@Nullable HttpServletRequest request, @Nullable String name) {
        return (request == null || StringUtils.isBlank(name)) ? null : request.getAttribute(name);
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> T getRequestAttributeAs(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(request, expectedType) || StringUtils.isBlank(name)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(request.getAttribute(name), expectedType);
    }

    public static List<String> getRequestAttributeNames(@Nullable HttpServletRequest request) {
        return (request == null) ? null : EnumerationPlainWraps.toElementList(request.getAttributeNames());
    }

    public static Throwable getRequestDefaultError(@Nullable HttpServletRequest request) {
        return getRequestAttributeAs(request, SpringAttributeConst.SERVLET_DEFAULT_ERROR, Throwable.class);
    }

    public static HttpSession getSession(@Nullable HttpServletRequest request) {
        return getSession(request, false);
    }

    /**
     * Returns the current <code>HttpSession</code> associated with this request
     * <p>
     * To make sure the session is properly maintained, you must call this method before the response is committed
     *
     * @param request the <code>HttpServletRequest</code> associated
     * @param createIfNull if there is no current session and <code>create</code> is true, returns a new session
     *
     * @return the current <code>HttpSession</code> associated with this request
     */
    public static HttpSession getSession(@Nullable HttpServletRequest request, boolean createIfNull) {
        return (request == null) ? null : request.getSession(createIfNull);
    }

    public static String getSessionId(@Nullable HttpServletRequest request) {
        return getSessionId(request, false);
    }

    @Nullable
    public static String getSessionId(@Nullable HttpServletRequest request, boolean createIfNull) {
        HttpSession session = getSession(request, createIfNull);
        return (session == null) ? null : session.getId();
    }

    @Nullable
    public static String getSessionIdRequested(@Nullable HttpServletRequest request) {
        return (request == null) ? null : request.getRequestedSessionId();
    }

    public static Object getSessionAttribute(@Nullable HttpServletRequest request, @Nullable String name) {
        return (request == null || StringUtils.isBlank(name)) ? null : WebUtils.getSessionAttribute(request, name);
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> T getSessionAttributeAs(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(request, expectedType) || StringUtils.isBlank(name)) {
            return null;
        }
        return ObjectUtilsWraps.castAs(WebUtils.getSessionAttribute(request, name), expectedType);
    }

    public static void setSessionAttribute(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Object value) {
        setSessionAttribute(request, name, value, true);
    }

    public static void setSessionAttribute(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Object value, boolean createIfNull) {
        if (request == null || StringUtils.isBlank(name)) {
            return;
        }
        HttpSession session = request.getSession(createIfNull && value != null);
        if (session != null) {
            session.setAttribute(name, value);
        }
    }

    public static void removeSessionAttribute(@Nullable HttpServletRequest request, @Nullable String name) {
        if (request == null || StringUtils.isBlank(name)) {
            return;
        }
        HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute(name);
        }
    }

    @Nullable
    public static Map<String, Object> getServletErrorAttributes(@Nullable HttpServletRequest request) {
        ErrorAttributeOptions options = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.BINDING_ERRORS, ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE);
        return getServletErrorAttributes(request, options);
    }

    /**
     * @see org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController#getErrorAttributes
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static Map<String, Object> getServletErrorAttributes(@Nullable HttpServletRequest request, @Nullable ErrorAttributeOptions options) {
        if (ObjectUtils.anyNull(request, options)) {
            return null;
        }
        WebRequest webRequest = new ServletWebRequest(request);
        return new DefaultErrorAttributes().getErrorAttributes(webRequest, options);
    }

    /**
     * @see org.springframework.web.multipart.MultipartHttpServletRequest
     */
    @Nullable
    public static List<Part> getServletParts(@Nullable HttpServletRequest request, @Nullable String partName) throws IOException, ServletException {
        if (StringUtils.isBlank(partName) || !HttpHeaderWraps.isContentTypeMultipart(request)) {
            return null;
        }
        Collection<Part> parts = request.getParts();
        if (CollectionUtils.isEmpty(parts)) {
            return null;
        }
        return parts.stream().filter(part -> StringUtils.isNotBlank(part.getContentType()) && StringUtils.equals(part.getName(), partName)).collect(Collectors.toList());
    }

    @Nullable
    public static List<Part> getServletPartsQuietly(@Nullable HttpServletRequest request, @Nullable String partName) {
        try {
            return getServletParts(request, partName);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * @reference "https://docs.oracle.com/javaee/7/tutorial/servlets016.htm#BABDGFJJ"
     */
    public static String getServletPartFilename(@Nullable Part part) {
        if (part == null) {
            return null;
        }
        String[] contents = StringUtils.split(part.getHeader(HttpHeaders.CONTENT_DISPOSITION), CharVariantConst.SEMICOLON);
        if (ArrayUtils.isEmpty(contents)) {
            return null;
        }
        for (String content : contents) {
            if (StringUtils.startsWithIgnoreCase(content, StringVariantConst.FILENAME) && StringUtils.contains(content, CharVariantConst.EQUAL)) {
                String filename = StringUtils.remove(StringUtils.substringAfter(content, CharVariantConst.EQUAL), CharVariantConst.DOUBLE_QUOTE);
                return StringUtils.trimToNull(filename);
            }
        }
        return null;
    }

    /**
     * @see org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController#getStatus
     */
    public static HttpStatusCode getServletStatus(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return (status == null) ? null : HttpStatus.resolve(status);
    }

    public static <T extends ServletRequestWrapper> T getServletRequestWrapper(@Nullable HttpServletRequest request, @Nullable Class<T> expectedType) {
        if (!(request instanceof ServletRequestWrapper alias) || expectedType == null) {
            return null;
        }
        ServletRequest wrapper = alias.getRequest();
        while (wrapper instanceof ServletRequestWrapper subAlias) {
            if (ClassUtils.isAssignableValue(expectedType, wrapper)) {
                return ObjectUtilsWraps.castAs(wrapper, expectedType);
            }
            wrapper = subAlias.getRequest();
        }
        return null;
    }

    public static void invalidateSession(@Nullable HttpServletRequest request) {
        HttpSession session = getSession(request);
        if (session != null) {
            session.invalidate();
        }
    }

    public static boolean isContentCachingWrapper(@Nullable HttpServletRequest request) {
        return request instanceof ContentCachingRequestWrapper;
    }

    public static boolean isContentCachingWrapper(@Nullable HttpServletResponse response) {
        return response instanceof ContentCachingResponseWrapper;
    }

    /**
     * @see org.springframework.web.filter.OncePerRequestFilter#isAsyncDispatch
     */
    public static boolean isAsyncDispatch(@Nullable HttpServletRequest request) {
        return request != null && DispatcherType.ASYNC.equals(request.getDispatcherType());
    }

    /**
     * @see org.springframework.web.context.request.async.WebAsyncUtils#getAsyncManager(jakarta.servlet.ServletRequest)
     */
    public static boolean isAsyncRequest(@Nullable ServletRequest request) {
        return request != null && ClassUtils.isAssignableValue(WebAsyncManager.class, request.getAttribute(WebAsyncUtils.WEB_ASYNC_MANAGER_ATTRIBUTE));
    }

    /**
     * @see org.springframework.web.context.request.async.WebAsyncUtils#getAsyncManager(org.springframework.web.context.request.WebRequest)
     */
    public static boolean isAsyncRequest(@Nullable WebRequest request) {
        return request != null && ClassUtils.isAssignableValue(WebAsyncManager.class, request.getAttribute(WebAsyncUtils.WEB_ASYNC_MANAGER_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST));
    }

    /**
     * @see org.springframework.web.filter.OncePerRequestFilter#isAsyncStarted
     */
    public static boolean isAsyncStarted(@Nullable ServletRequest request) {
        return request != null && WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted();
    }

    /**
     * @see org.springframework.web.filter.OncePerRequestFilter#isAsyncStarted
     */
    public static boolean isAsyncStarted(@Nullable WebRequest request) {
        return request != null && WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted();
    }

    /**
     * Returns whether the {@code request} is ajax capable
     * <p>
     * With a header named {@code "X-Requested-With"} and value {@code "XMLHttpRequest"}
     *
     * @return whether the {@code request} is ajax capable
     */
    public static boolean isAjaxRequest(@Nullable HttpServletRequest request) {
        return HttpHeaderWraps.existsHeaderValueIgnoreCase(request, HttpHeaderConst.X_REQUESTED_WITH, HttpHeaderConst.XML_HTTP_REQUEST);
    }

    /**
     * Returns whether the {@code request} is rest capable
     * <p>
     * With a header named {@code "X-Requested-With"} and value {@code "XMLHttpRequest"}, or accept {@code "application/json"}, or accept {@code "application/xml"}
     *
     * @return whether the {@code request} is rest capable
     */
    public static boolean isRestRequest(@Nullable HttpServletRequest request) {
        return isAjaxRequest(request) || HttpHeaderWraps.isAcceptApplicationJson(request) || HttpHeaderWraps.isAcceptApplicationXml(request);
    }

    public static boolean isCanonicalModelView(@Nullable ModelAndView view) {
        return view != null && StringUtils.isNotBlank(view.getViewName()) && !isForwardModelView(view) && !isRedirectModelView(view);
    }

    /**
     * @see "org.thymeleaf.spring5.view.ThymeleafViewResolver"
     */
    public static boolean isForwardModelView(@Nullable ModelAndView view) {
        return view != null && StringUtils.startsWithIgnoreCase(view.getViewName(), UrlBasedViewResolver.FORWARD_URL_PREFIX);
    }

    /**
     * @see org.springframework.web.servlet.view.RedirectView
     */
    public static boolean isRedirectModelView(@Nullable ModelAndView view) {
        return view != null && StringUtils.startsWithIgnoreCase(view.getViewName(), UrlBasedViewResolver.REDIRECT_URL_PREFIX);
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static boolean saveMultipartFile(@Nullable MultipartFile multipart, @Nullable File output, boolean append) throws IllegalArgumentException, IOException {
        if (ObjectUtils.anyNull(multipart, output)) {
            return false;
        }
        FileUtilsWraps.forceMkdirParentDeletable(output, !append);
        multipart.transferTo(output);
        return true;
    }

    public static boolean saveMultipartFileQuietly(@Nullable MultipartFile multipart, @Nullable File output, boolean append) {
        try {
            return saveMultipartFile(multipart, output, append);
        } catch (Exception ignored) {
        }
        return false;
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static long saveServletPart(@Nullable Part part, @Nullable File output, boolean append) throws IllegalArgumentException, IOException {
        if (ObjectUtils.anyNull(part, output)) {
            return 0L;
        }
        FileUtils.forceMkdirParent(output);
        return saveServletPart(part, FileUtils.openOutputStream(output, append), IOUtils.DEFAULT_BUFFER_SIZE);
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static long saveServletPart(@Nullable Part part, @Nullable OutputStream output, int bufferSize) throws IllegalArgumentException, IOException {
        if (ObjectUtils.anyNull(part, output) || bufferSize < 0) {
            return 0L;
        }
        try (InputStream input = part.getInputStream()) {
            return IOUtils.copy(input, output, bufferSize);
        }
    }

    public static long saveServletPartQuietly(@Nullable Part part, @Nullable File output, boolean append) {
        try {
            return saveServletPart(part, output, append);
        } catch (Exception ignored) {
        }
        return 0L;
    }

    public static long saveServletPartQuietly(@Nullable Part part, @Nullable OutputStream output, int bufferSize) {
        try {
            return saveServletPart(part, output, bufferSize);
        } catch (Exception ignored) {
        }
        return 0L;
    }

    @SuppressWarnings("DataFlowIssue")
    public static ServletRequest wrapServletRequest(@Nullable ServletRequest request, @Nullable Class<? extends ServletRequestWrapper> wrapper) throws NoSuchMethodException {
        if (ObjectUtils.anyNull(request, wrapper) || ClassUtils.isAssignableValue(wrapper, request)) {
           return request;
        }
        Constructor<? extends ServletRequestWrapper> constructor = ConstructorUtils.getAccessibleConstructor(wrapper, ServletRequest.class);
        if (constructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on class: " + wrapper.getName());
        }
        return ConstructorUtilsWraps.newInstance(constructor, request);
    }

    @SuppressWarnings("DataFlowIssue")
    public static ServletResponse wrapServletResponse(@Nullable ServletResponse response, @Nullable Class<? extends ServletResponseWrapper> wrapper) throws NoSuchMethodException {
        if (ObjectUtils.anyNull(response, wrapper) || ClassUtils.isAssignableValue(wrapper, response)) {
            return response;
        }
        Constructor<? extends ServletResponseWrapper> constructor = ConstructorUtils.getAccessibleConstructor(wrapper, ServletResponse.class);
        if (constructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on class: " + wrapper.getName());
        }
        return ConstructorUtilsWraps.newInstance(constructor, response);
    }

    @SuppressWarnings("DataFlowIssue")
    public static HttpServletRequest wrapHttpServletRequest(@Nullable HttpServletRequest request, @Nullable Class<? extends HttpServletRequestWrapper> wrapper) throws NoSuchMethodException {
        if (ObjectUtils.anyNull(request, wrapper) || ClassUtils.isAssignableValue(wrapper, request)) {
           return request;
        }
        Constructor<? extends HttpServletRequestWrapper> constructor = ConstructorUtils.getAccessibleConstructor(wrapper, HttpServletRequestWrapper.class);
        if (constructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on class: " + wrapper.getName());
        }
        return ConstructorUtilsWraps.newInstance(constructor, request);
    }

    @SuppressWarnings("DataFlowIssue")
    public static HttpServletResponse wrapHttpServletResponse(@Nullable HttpServletResponse response, @Nullable Class<? extends HttpServletResponseWrapper> wrapper) throws NoSuchMethodException {
        if (ObjectUtils.anyNull(response, wrapper) || ClassUtils.isAssignableValue(wrapper, response)) {
            return response;
        }
        Constructor<? extends HttpServletResponseWrapper> constructor = ConstructorUtils.getAccessibleConstructor(wrapper, HttpServletResponseWrapper.class);
        if (constructor == null) {
            throw new NoSuchMethodException("No such accessible constructor on class: " + wrapper.getName());
        }
        return ConstructorUtilsWraps.newInstance(constructor, response);
    }

    public static HttpServletRequest wrapWithContentCaching(@Nullable HttpServletRequest request) {
        return (request == null || request instanceof ContentCachingRequestWrapper) ? request : new ContentCachingRequestWrapper(request);
    }

    public static HttpServletResponse wrapWithContentCaching(@Nullable HttpServletResponse response) {
        return (response == null || response instanceof ContentCachingResponseWrapper) ? response : new ContentCachingResponseWrapper(response);
    }

    public static void setWebAppRootSystemProperty(@Nullable ServletContext context) {
        if (context == null) {
            return;
        }
        try {
            WebUtils.setWebAppRootSystemProperty(context);
        } catch (Exception ignored) {
        }
    }

    public static void removeWebAppRootSystemProperty(@Nullable ServletContext context) {
        if (context == null) {
            return;
        }
        try {
            WebUtils.removeWebAppRootSystemProperty(context);
        } catch (Exception ignored) {
        }
    }

    public static void writeResponse(@Nullable HttpServletResponse response, @Nullable String text) throws IOException {
        writeResponse(response, text, (String) null, null, null, null, null);
    }

    public static void writeResponse(@Nullable HttpServletResponse response, @Nullable String text, @Nullable MimeType contentType, @Nullable Charset charset) throws IOException {
        writeResponse(response, text, contentType, charset, null, null, null);
    }

    public static void writeResponse(@Nullable HttpServletResponse response, @Nullable String text, String contentType, @Nullable String charset) throws IOException {
        writeResponse(response, text, contentType, charset, null, null, null);
    }

    public static void writeResponse(@Nullable HttpServletResponse response, @Nullable String text, @Nullable MimeType contentType, @Nullable Charset charset, @Nullable HttpStatusCode status) throws IOException {
        writeResponse(response, text, contentType, charset, status, null, null);
    }

    public static void writeResponse(@Nullable HttpServletResponse response, @Nullable String text, @Nullable String contentType, String charset, @Nullable HttpStatusCode status) throws IOException {
        writeResponse(response, text, contentType, charset, status, null, null);
    }

    public static void writeResponse(@Nullable HttpServletResponse response, @Nullable String text, @Nullable MimeType contentType, @Nullable Charset charset, @Nullable HttpStatusCode status, @Nullable Map<String, String> headers, @Nullable List<Cookie> cookies) throws IOException {
        writeResponse(response, text, Objects.toString(contentType, null), Objects.toString(charset, null), status, null, null);
    }

    public static void writeResponse(@Nullable HttpServletResponse response, @Nullable String text, @Nullable String contentType, @Nullable String charset, @Nullable HttpStatusCode status, @Nullable Map<String, String> headers, @Nullable List<Cookie> cookies) throws IOException {
        if (response == null || response.isCommitted()) {
            return;
        }
        if (StringUtils.isNotBlank(contentType)) {
            response.setContentType(contentType);
        }
        if (StringUtils.isNotBlank(charset)) {
            response.setCharacterEncoding(charset);
        }
        if (status != null) {
            response.setStatus(status.value());
        }
        MapPlainWraps.forEach(headers, response::addHeader, (key, value) -> StringUtils.isNotBlank(key));
        CollectionPlainWraps.forEach(cookies, response::addCookie, Objects::nonNull);
        if (StringUtils.isNotEmpty(text)) {
            response.getWriter().write(text);
        }
    }

    public static void writeResponseQuietly(@Nullable HttpServletResponse response, @Nullable String text) {
        writeResponseQuietly(response, text, (String) null, null, null, null, null);
    }

    public static void writeResponseQuietly(@Nullable HttpServletResponse response, @Nullable String text, @Nullable MimeType contentType, @Nullable Charset charset) {
        writeResponseQuietly(response, text, contentType, charset, null, null, null);
    }

    public static void writeResponseQuietly(@Nullable HttpServletResponse response, @Nullable String text, @Nullable String contentType, @Nullable String charset) {
        writeResponseQuietly(response, text, contentType, charset, null, null, null);
    }

    public static void writeResponseQuietly(@Nullable HttpServletResponse response, @Nullable String text, @Nullable MimeType contentType, @Nullable Charset charset, @Nullable HttpStatusCode status) {
        writeResponseQuietly(response, text, contentType, charset, status, null, null);
    }

    public static void writeResponseQuietly(@Nullable HttpServletResponse response, @Nullable String text, @Nullable String contentType, @Nullable String charset, @Nullable HttpStatusCode status) {
        writeResponseQuietly(response, text, contentType, charset, status, null, null);
    }

    public static void writeResponseQuietly(@Nullable HttpServletResponse response, @Nullable String text, @Nullable MimeType contentType, @Nullable Charset charset, @Nullable HttpStatusCode status, @Nullable Map<String, String> headers, @Nullable List<Cookie> cookies) {
        try {
            writeResponse(response, text, contentType, charset, status, headers, cookies);
        } catch (Exception ignored) {
        }
    }

    public static void writeResponseQuietly(@Nullable HttpServletResponse response, @Nullable String text, @Nullable String contentType, @Nullable String charset, @Nullable HttpStatusCode status, @Nullable Map<String, String> headers, @Nullable List<Cookie> cookies) {
        try {
            writeResponse(response, text, contentType, charset, status, headers, cookies);
        } catch (Exception ignored) {
        }
    }
}
