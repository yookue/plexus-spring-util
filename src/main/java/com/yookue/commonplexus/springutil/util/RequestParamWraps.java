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


import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import jakarta.annotation.Nullable;
import jakarta.persistence.Id;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.annotation.BeanCopyIgnore;
import com.yookue.commonplexus.javaseutil.annotation.ViewSubmitIgnore;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.constant.SymbolVariantConst;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.javaseutil.util.EnumerationPlainWraps;
import com.yookue.commonplexus.javaseutil.util.IterablePlainWraps;
import com.yookue.commonplexus.javaseutil.util.ListPlainWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.NumberUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;


/**
 * Utilities for {@link org.springframework.web.bind.ServletRequestUtils}
 *
 * @author David Hsing
 * @see org.springframework.web.bind.ServletRequestUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class RequestParamWraps {
    @Nullable
    public static String firstNonEmptyCookie(@Nullable HttpServletRequest request, @Nullable String... names) {
        return firstNonEmptyCookie(request, ArrayUtilsWraps.asList(names));
    }

    @Nullable
    public static String firstNonEmptyCookie(@Nullable HttpServletRequest request, @Nullable Collection<String> names) {
        if (request == null || CollectionUtils.isEmpty(names)) {
            return null;
        }
        return names.stream().filter(StringUtils::isNotBlank).map(element -> getCookieValue(request, element)).filter(StringUtils::isNotEmpty).findFirst().orElse(null);
    }

    @Nullable
    public static String firstNonBlankCookie(@Nullable HttpServletRequest request, @Nullable String... names) {
        return firstNonBlankCookie(request, ArrayUtilsWraps.asList(names));
    }

    @Nullable
    public static String firstNonBlankCookie(@Nullable HttpServletRequest request, @Nullable Collection<String> names) {
        if (request == null || CollectionUtils.isEmpty(names)) {
            return null;
        }
        return names.stream().filter(StringUtils::isNotBlank).map(element -> getCookieValue(request, element)).filter(StringUtils::isNotBlank).findFirst().orElse(null);
    }

    @Nullable
    public static String firstNonEmptyHeader(@Nullable HttpServletRequest request, @Nullable String... headers) {
        return firstNonEmptyHeader(request, ArrayUtilsWraps.asList(headers));
    }

    @Nullable
    public static String firstNonEmptyHeader(@Nullable HttpServletRequest request, @Nullable Collection<String> headers) {
        if (request == null || CollectionUtils.isEmpty(headers)) {
            return null;
        }
        return headers.stream().filter(StringUtils::isNotBlank).map(request::getHeader).filter(StringUtils::isNotEmpty).findFirst().orElse(null);
    }

    @Nullable
    public static String firstNonBlankHeader(@Nullable HttpServletRequest request, @Nullable String... headers) {
        return firstNonBlankHeader(request, ArrayUtilsWraps.asList(headers));
    }

    @Nullable
    public static String firstNonBlankHeader(@Nullable HttpServletRequest request, @Nullable Collection<String> headers) {
        if (request == null || CollectionUtils.isEmpty(headers)) {
            return null;
        }
        return headers.stream().filter(StringUtils::isNotBlank).map(request::getHeader).filter(StringUtils::isNotBlank).findFirst().orElse(null);
    }

    @Nullable
    public static String firstNonEmptyParameter(@Nullable HttpServletRequest request, @Nullable String... params) {
        return firstNonEmptyParameter(request, ArrayUtilsWraps.asList(params));
    }

    @Nullable
    public static String firstNonEmptyParameter(@Nullable HttpServletRequest request, @Nullable Collection<String> params) {
        if (request == null || CollectionUtils.isEmpty(params)) {
            return null;
        }
        return params.stream().filter(StringUtils::isNotBlank).map(request::getParameter).filter(StringUtils::isNotEmpty).findFirst().orElse(null);
    }

    @Nullable
    public static String firstNonBlankParameter(@Nullable HttpServletRequest request, @Nullable String... params) {
        return firstNonBlankParameter(request, ArrayUtilsWraps.asList(params));
    }

    @Nullable
    public static String firstNonBlankParameter(@Nullable HttpServletRequest request, @Nullable Collection<String> params) {
        if (request == null || CollectionUtils.isEmpty(params)) {
            return null;
        }
        return params.stream().filter(StringUtils::isNotBlank).map(request::getParameter).filter(StringUtils::isNotBlank).findFirst().orElse(null);
    }

    @Nullable
    public static Boolean getBooleanCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getBooleanCookie(request, name, null);
    }

    @Nullable
    public static Boolean getBooleanCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Boolean defaultValue) {
        return ObjectUtils.defaultIfNull(BooleanUtils.toBooleanObject(getStringCookieTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Double getDoubleCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getDoubleCookie(request, name, null);
    }

    @Nullable
    public static Double getDoubleCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Double defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toDoubleObject(getStringCookieTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Float getFloatCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getFloatCookie(request, name, null);
    }

    @Nullable
    public static Float getFloatCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Float defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toFloatObject(getStringCookieTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Integer getIntegerCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getIntegerCookie(request, name, null);
    }

    @Nullable
    public static Integer getIntegerCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Integer defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toIntegerObject(getStringCookieTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Long getLongCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getLongCookie(request, name, null);
    }

    @Nullable
    public static Long getLongCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Long defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toLongObject(getStringCookieTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Short getShortCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getShortCookie(request, name, null);
    }

    @Nullable
    public static Short getShortCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Short defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toShortObject(getStringCookieTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static String getStringCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringCookie(request, name, null);
    }

    @Nullable
    public static String getStringCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        String result = (request == null || StringUtils.isBlank(name)) ? null : getCookieValue(request, name);
        return ObjectUtils.defaultIfNull(result, defaultValue);
    }

    @Nullable
    public static String getStringCookieTrimming(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringCookieTrimming(request, name, false, null);
    }

    @Nullable
    public static String getStringCookieTrimming(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        return getStringCookieTrimming(request, name, false, defaultValue);
    }

    @Nullable
    public static String getStringCookieTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull) {
        return getStringCookieTrimming(request, name, emptyAsNull, null);
    }

    @Nullable
    public static String getStringCookieTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull, @Nullable String defaultValue) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValue;
        }
        String value = getCookieValue(request, name);
        String result = emptyAsNull ? StringUtils.trimToNull(value) : StringUtils.trim(value);
        return ObjectUtils.defaultIfNull(result, defaultValue);
    }

    @Nullable
    public static Map<String, String> getCookieObjectMap(@Nullable HttpServletRequest request) {
        return getCookieObjectMap(request, false, null);
    }

    @Nullable
    public static Map<String, String> getCookieObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull) {
        return getCookieObjectMap(request, emptyAsNull, null);
    }

    @Nullable
    public static Map<String, String> getCookieObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull, @Nullable Map<String, String> defaultValues) {
        if (request == null) {
            return defaultValues;
        }
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isEmpty(cookies)) {
            return defaultValues;
        }
        Map<String, String> result = new LinkedHashMap<>();
        for (Cookie cookie : cookies) {
            String name = cookie.getName(), value = cookie.getValue();
            result.put(name, emptyAsNull ? StringUtils.trimToNull(value) : value);
        }
        return result.isEmpty() ? defaultValues : result;
    }

    @Nullable
    public static Boolean getBooleanHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getBooleanHeader(request, name, null);
    }

    @Nullable
    public static Boolean getBooleanHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Boolean defaultValue) {
        return ObjectUtils.defaultIfNull(BooleanUtils.toBooleanObject(getStringHeaderTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Boolean[] getBooleanHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getBooleanHeaders(request, name, null);
    }

    @Nullable
    public static Boolean[] getBooleanHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Boolean[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Boolean[] result = (values == null) ? null : Arrays.stream(values).map(BooleanUtils::toBooleanObject).toArray(Boolean[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static Double getDoubleHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getDoubleHeader(request, name, null);
    }

    @Nullable
    public static Double getDoubleHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Double defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toDoubleObject(getStringHeaderTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Double[] getDoubleHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getDoubleHeaders(request, name, null);
    }

    @Nullable
    public static Double[] getDoubleHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Double[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Double[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toDoubleObject).toArray(Double[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static Float getFloatHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getFloatHeader(request, name, null);
    }

    @Nullable
    public static Float getFloatHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Float defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toFloatObject(getStringHeaderTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Float[] getFloatHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getFloatHeaders(request, name, null);
    }

    @Nullable
    public static Float[] getFloatHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Float[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Float[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toFloatObject).toArray(Float[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static Integer getIntegerHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getIntegerHeader(request, name, null);
    }

    @Nullable
    public static Integer getIntegerHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Integer defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toIntegerObject(getStringHeaderTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Integer[] getIntegerHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getIntegerHeaders(request, name, null);
    }

    @Nullable
    public static Integer[] getIntegerHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Integer[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Integer[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toIntegerObject).toArray(Integer[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static Long getLongHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getLongHeader(request, name, null);
    }

    @Nullable
    public static Long getLongHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Long defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toLongObject(getStringHeaderTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Long[] getLongHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getLongHeaders(request, name, null);
    }

    @Nullable
    public static Long[] getLongHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Long[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Long[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toLongObject).toArray(Long[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static Short getShortHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getShortHeader(request, name, null);
    }

    @Nullable
    public static Short getShortHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Short defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toShortObject(getStringHeaderTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Short[] getShortHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getShortHeaders(request, name, null);
    }

    @Nullable
    public static Short[] getShortHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Short[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Short[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toShortObject).toArray(Short[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static String getStringHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringHeader(request, name, null);
    }

    @Nullable
    public static String getStringHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        String result = (request == null || StringUtils.isBlank(name)) ? null : request.getHeader(name);
        return ObjectUtils.defaultIfNull(result, defaultValue);
    }

    @Nullable
    public static String[] getStringHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringHeaders(request, name, null);
    }

    @Nullable
    public static String[] getStringHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String[] defaultValues) {
        String[] result = (request == null || StringUtils.isBlank(name)) ? null : getHeaderValues(request, name);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static String getStringHeaderTrimming(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringHeaderTrimming(request, name, false, null);
    }

    @Nullable
    public static String getStringHeaderTrimming(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        return getStringHeaderTrimming(request, name, false, defaultValue);
    }

    @Nullable
    public static String getStringHeaderTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull) {
        return getStringHeaderTrimming(request, name, emptyAsNull, null);
    }

    @Nullable
    public static String getStringHeaderTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull, @Nullable String defaultValue) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValue;
        }
        String result = emptyAsNull ? StringUtils.trimToNull(request.getHeader(name)) : StringUtils.trim(request.getHeader(name));
        return ObjectUtils.defaultIfNull(result, defaultValue);
    }

    @Nullable
    public static String[] getStringHeadersTrimming(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringHeadersTrimming(request, name, false, null);
    }

    @Nullable
    public static String[] getStringHeadersTrimming(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String[] defaultValues) {
        return getStringHeadersTrimming(request, name, false, defaultValues);
    }

    @Nullable
    public static String[] getStringHeadersTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull) {
        return getStringHeadersTrimming(request, name, emptyAsNull, null);
    }

    @Nullable
    public static String[] getStringHeadersTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull, @Nullable String[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        return ObjectUtils.defaultIfNull(StringUtilsWraps.trimStringArray(emptyAsNull, getHeaderValues(request, name)), defaultValues);
    }

    @Nullable
    public static Map<String, Object> getHeaderObjectMap(@Nullable HttpServletRequest request) {
        return getHeaderObjectMap(request, false, null);
    }

    @Nullable
    public static Map<String, Object> getHeaderObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull) {
        return getHeaderObjectMap(request, emptyAsNull, null);
    }

    @Nullable
    public static Map<String, Object> getHeaderObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull, @Nullable Map<String, Object> defaultValues) {
        if (request == null) {
            return defaultValues;
        }
        Set<String> names = EnumerationPlainWraps.toElementSet(request.getHeaderNames());
        Map<String, Object> result = new LinkedHashMap<>();
        IterablePlainWraps.forEach(names, name -> {
            List<String> values = EnumerationPlainWraps.toElementList(request.getHeaders(name));
            if (CollectionUtils.isEmpty(values)) {
                result.put(name, null);
                return;
            }
            if (CollectionPlainWraps.isSingleton(values)) {
                String value = ListPlainWraps.getFirst(values);
                result.put(name, emptyAsNull ? StringUtils.trimToNull(value) : value);
                return;
            }
            StringUtilsWraps.trimStringCollection(emptyAsNull, values);
            result.put(name, values);
        }, StringUtils::isNotBlank);
        return result.isEmpty() ? defaultValues : result;
    }

    @Nullable
    public static Boolean getBooleanParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getBooleanParameter(request, name, null);
    }

    @Nullable
    public static Boolean getBooleanParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Boolean defaultValue) {
        return ObjectUtils.defaultIfNull(BooleanUtils.toBooleanObject(getStringParameterTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Boolean[] getBooleanParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getBooleanParameters(request, name, null);
    }

    @Nullable
    public static Boolean[] getBooleanParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Boolean[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Boolean[] result = (values == null) ? null : Arrays.stream(values).map(BooleanUtils::toBooleanObject).toArray(Boolean[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static Double getDoubleParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getDoubleParameter(request, name, null);
    }

    @Nullable
    public static Double getDoubleParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Double defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toDoubleObject(getStringParameterTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Double[] getDoubleParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getDoubleParameters(request, name, null);
    }

    @Nullable
    public static Double[] getDoubleParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Double[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Double[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toDoubleObject).toArray(Double[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static Float getFloatParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getFloatParameter(request, name, null);
    }

    @Nullable
    public static Float getFloatParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Float defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toFloatObject(getStringParameterTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Float[] getFloatParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getFloatParameters(request, name, null);
    }

    @Nullable
    public static Float[] getFloatParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Float[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Float[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toFloatObject).toArray(Float[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static Integer getIntegerParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getIntegerParameter(request, name, null);
    }

    @Nullable
    public static Integer getIntegerParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Integer defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toIntegerObject(getStringParameterTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Integer[] getIntegerParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getIntegerParameters(request, name, null);
    }

    @Nullable
    public static Integer[] getIntegerParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Integer[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Integer[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toIntegerObject).toArray(Integer[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static Long getLongParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getLongParameter(request, name, null);
    }

    @Nullable
    public static Long getLongParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Long defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toLongObject(getStringParameterTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Long[] getLongParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getLongParameters(request, name, null);
    }

    @Nullable
    public static Long[] getLongParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Long[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Long[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toLongObject).toArray(Long[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static Short getShortParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getShortParameter(request, name, null);
    }

    @Nullable
    public static Short getShortParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Short defaultValue) {
        return ObjectUtils.defaultIfNull(NumberUtilsWraps.toShortObject(getStringParameterTrimming(request, name)), defaultValue);
    }

    @Nullable
    public static Short[] getShortParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getShortParameters(request, name, null);
    }

    @Nullable
    public static Short[] getShortParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Short[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Short[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toShortObject).toArray(Short[]::new);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static String getStringParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getStringParameter(request, name, null);
    }

    @Nullable
    public static String getStringParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        String result = (request == null || StringUtils.isBlank(name)) ? null : request.getParameter(name);
        return ObjectUtils.defaultIfNull(result, defaultValue);
    }

    @Nullable
    public static String[] getStringParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getStringParameters(request, name, null);
    }

    @Nullable
    public static String[] getStringParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable String[] defaultValues) {
        String[] result = (request == null || StringUtils.isBlank(name)) ? null : request.getParameterValues(name);
        return ObjectUtils.defaultIfNull(result, defaultValues);
    }

    @Nullable
    public static String getStringParameterTrimming(@Nullable ServletRequest request, @Nullable String name) {
        return getStringParameterTrimming(request, name, false, null);
    }

    @Nullable
    public static String getStringParameterTrimming(@Nullable ServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        return getStringParameterTrimming(request, name, false, defaultValue);
    }

    @Nullable
    public static String getStringParameterTrimming(@Nullable ServletRequest request, @Nullable String name, boolean emptyAsNull) {
        return getStringParameterTrimming(request, name, emptyAsNull, null);
    }

    @Nullable
    public static String getStringParameterTrimming(@Nullable ServletRequest request, @Nullable String name, boolean emptyAsNull, @Nullable String defaultValue) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValue;
        }
        String value = request.getParameter(name);
        String result = emptyAsNull ? StringUtils.trimToNull(value) : StringUtils.trim(value);
        return ObjectUtils.defaultIfNull(result, defaultValue);
    }

    @Nullable
    public static String[] getStringParametersTrimming(@Nullable ServletRequest request, @Nullable String name) {
        return getStringParametersTrimming(request, name, false, null);
    }

    @Nullable
    public static String[] getStringParametersTrimming(@Nullable ServletRequest request, @Nullable String name, @Nullable String[] defaultValues) {
        return getStringParametersTrimming(request, name, false, defaultValues);
    }

    @Nullable
    public static String[] getStringParametersTrimming(@Nullable ServletRequest request, @Nullable String name, boolean emptyAsNull) {
        return getStringParametersTrimming(request, name, emptyAsNull, null);
    }

    @Nullable
    public static String[] getStringParametersTrimming(@Nullable ServletRequest request, @Nullable String name, boolean emptyAsNull, @Nullable String[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        return ObjectUtils.defaultIfNull(StringUtilsWraps.trimStringArray(emptyAsNull, request.getParameterValues(name)), defaultValues);
    }

    @Nullable
    public static Map<String, Object> getParameterObjectMap(@Nullable HttpServletRequest request) {
        return getParameterObjectMap(request, false, false);
    }

    @Nullable
    public static Map<String, Object> getParameterObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull) {
        return getParameterObjectMap(request, emptyAsNull, false);
    }

    @Nullable
    public static Map<String, Object> getParameterObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull, boolean includePayload) {
        if (request == null) {
            return null;
        }
        Map<String, Object> result = new LinkedHashMap<>();
        MapPlainWraps.forEach(request.getParameterMap(), (key, values) -> {
            if (ArrayUtils.isEmpty(values)) {
                result.put(key, null);
                return;
            }
            if (StringUtils.endsWith(key, SymbolVariantConst.SQUARE_BRACKETS) || ArrayUtils.getLength(values) > 1) {
                result.put(key, ArrayUtilsWraps.asList(values));
                return;
            }
            result.put(key, emptyAsNull ? StringUtils.trimToNull(values[0]) : values[0]);
        }, (key, values) -> StringUtils.isNotBlank(key));
        // Checks if the request has payload
        if (!includePayload || !WebUtilsWraps.isAjaxRequest(request)) {
            return MapPlainWraps.emptyAsNull(result);
        }
        // Converts the payload into map and processes it
        String content = WebUtilsWraps.getContentAsStringQuietly(request);
        Map<String, Object> payloads = JsonParserWraps.parseChildToMap(content, null);
        if (CollectionUtils.isEmpty(payloads)) {
            return MapPlainWraps.emptyAsNull(result);
        }
        MapPlainWraps.forEach(payloads, (key, value) -> {
            if (value instanceof String instance && emptyAsNull) {
                result.put(key, StringUtils.trimToNull(instance));
            } else {
                result.put(key, value);
            }
        }, (key, value) -> StringUtils.isNotBlank(key));
        return MapPlainWraps.emptyAsNull(result);
    }

    public static Cookie getCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getCookie(request, name, null);
    }

    public static Cookie getCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Cookie defaultValue) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValue;
        }
        return Arrays.stream(request.getCookies()).filter(element -> StringUtils.equals(element.getName(), name)).findFirst().orElse(defaultValue);
    }

    public static String getCookieValue(@Nullable HttpServletRequest request, @Nullable String name) {
        return getCookieValue(request, name, null);
    }

    public static String getCookieValue(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        Cookie cookie = getCookie(request, name);
        return (cookie == null) ? defaultValue : cookie.getValue();
    }

    /**
     * @see org.springframework.http.ResponseCookie
     */
    public static List<Cookie> getCookies(@Nullable HttpServletRequest request) {
        return (request == null) ? null : ArrayUtilsWraps.asList(true, request.getCookies());
    }

    public static String getCookiesAsString(@Nullable HttpServletRequest request) {
        return getCookiesAsString(request, CharUtils.toString(CharVariantConst.EQUALS), SymbolVariantConst.SEMICOLON_SPACE);
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String getCookiesAsString(@Nullable HttpServletRequest request, @Nullable CharSequence keyValueDelimiter, @Nullable CharSequence groupDelimiter) {
        if (request == null || StringUtils.isAnyBlank(keyValueDelimiter, groupDelimiter)) {
            return null;
        }
        List<Cookie> cookies = getCookies(request);
        if (CollectionUtils.isEmpty(cookies)) {
            return null;
        }
        StringJoiner joiner = new StringJoiner(groupDelimiter);
        cookies.forEach(cookie -> joiner.add(StringUtils.join(cookie.getName(), keyValueDelimiter, StringUtils.defaultString(cookie.getValue()))));
        return joiner.toString();
    }

    public static String getHeaderValue(@Nullable HttpServletRequest request, @Nullable String name) {
        return getHeaderValue(request, name, null);
    }

    public static String getHeaderValue(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        return (request == null || StringUtils.isBlank(name)) ? defaultValue : Objects.toString(request.getHeader(name), defaultValue);
    }

    public static String[] getHeaderValues(@Nullable HttpServletRequest request, @Nullable String name) {
        return getHeaderValues(request, name, null);
    }

    public static String[] getHeaderValues(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String[] defaultValues) {
        return (request == null || StringUtils.isBlank(name)) ? defaultValues : EnumerationPlainWraps.toElementArray(request.getHeaders(name));
    }

    public static void populateBean(@Nullable Object bean, @Nullable HttpServletRequest request) {
        populateBean(bean, request, false, (Collection<String>) null);
    }

    public static void populateBean(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull) {
        populateBean(bean, request, emptyAsNull, (Collection<String>) null);
    }

    public static void populateBean(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull, @Nullable String... ignoredFields) {
        populateBean(bean, request, emptyAsNull, ArrayUtilsWraps.asList(ignoredFields));
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void populateBean(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull, @Nullable Collection<String> ignoredFields) {
        if (ObjectUtils.anyNull(bean, request)) {
            return;
        }
        Map<String, Object> paramValues = getParameterObjectMap(request, emptyAsNull);
        if (CollectionUtils.isEmpty(paramValues)) {
            return;
        }
        Collection<Class<? extends Annotation>> annotations = Arrays.asList(Id.class, CreatedBy.class, LastModifiedBy.class, LastModifiedDate.class, BeanCopyIgnore.class, ViewSubmitIgnore.class);
        Set<String> fieldNames = ReflectionUtilsWraps.getFieldNamesWithAnyAnnotationsToSet(bean.getClass(), annotations);
        if (!CollectionUtils.isEmpty(fieldNames)) {
            fieldNames.forEach(paramValues::remove);
        }
        BeanUtilsWraps.mapToBeanQuietly(paramValues, bean);
    }
}
