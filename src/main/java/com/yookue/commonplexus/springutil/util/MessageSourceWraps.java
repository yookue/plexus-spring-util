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


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;


/**
 * Utilities for {@link org.springframework.context.MessageSource}
 *
 * @author David Hsing
 * @see org.springframework.context.MessageSource
 * @see org.springframework.context.support.MessageSourceAccessor
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class MessageSourceWraps {
    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable String[] codes) {
        return firstMessage(source, codes, null, null, null);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable String[] codes, @Nullable Locale locale) {
        return firstMessage(source, codes, null, null, locale);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable Collection<String> codes) {
        return firstMessage(source, codes, null, null, null);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable Collection<String> codes, @Nullable Locale locale) {
        return firstMessage(source, codes, null, null, locale);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable String[] codes, @Nullable Object[] args) {
        return firstMessage(source, codes, args, null, null);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable String[] codes, @Nullable Object[] args, @Nullable Locale locale) {
        return firstMessage(source, codes, args, null, locale);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable Collection<String> codes, @Nullable Object[] args) {
        return firstMessage(source, codes, args, null, null);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable Collection<String> codes, @Nullable Object[] args, @Nullable Locale locale) {
        return firstMessage(source, codes, args, null, locale);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable String[] codes, @Nullable Object[] args, @Nullable String defaultMessage) {
        return firstMessage(source, ArrayUtilsWraps.asList(codes), args, null, null);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable String[] codes, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        return firstMessage(source, ArrayUtilsWraps.asList(codes), args, null, locale);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable Collection<String> codes, @Nullable Object[] args, @Nullable String defaultMessage) {
        return firstMessage(source, codes, args, defaultMessage, null);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable Collection<String> codes, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        if (source == null || CollectionUtils.isEmpty(codes)) {
            return defaultMessage;
        }
        return codes.stream().filter(StringUtils::isNotBlank).map(element -> getMessage(source, element, args, defaultMessage, locale)).filter(StringUtils::isNotEmpty).findFirst().orElse(defaultMessage);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param resolvable the value object storing attributes required to resolve a message (may include a default message)
     *
     * @return the first resolved message from the message source
     */
    public static String firstMessage(@Nullable MessageSource source, @Nullable MessageSourceResolvable resolvable) {
        return firstMessage(source, resolvable, null);
    }

    /**
     * Returns the first resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param resolvable the value object storing attributes required to resolve a message (may include a default message)
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String firstMessage(@Nullable MessageSource source, @Nullable MessageSourceResolvable resolvable, @Nullable Locale locale) {
        if (ObjectUtils.anyNull(source, resolvable)) {
            return null;
        }
        if (ArrayUtils.isEmpty(resolvable.getCodes())) {
            return resolvable.getDefaultMessage();
        }
        return Arrays.stream(resolvable.getCodes()).filter(StringUtils::isNotBlank).map(element -> getMessage(source, element, resolvable.getArguments(), resolvable.getDefaultMessage(), locale)).filter(StringUtils::isNotEmpty).findFirst().orElse(resolvable.getDefaultMessage());
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable String[] codes) {
        return firstMessageLookup(source, codes, null, null, null);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable String[] codes, @Nullable Locale locale) {
        return firstMessageLookup(source, codes, null, null, locale);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable Collection<String> codes) {
        return firstMessageLookup(source, codes, null, null, null);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable Collection<String> codes, @Nullable Locale locale) {
        return firstMessageLookup(source, codes, null, null, locale);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable String[] codes, @Nullable Object[] args) {
        return firstMessageLookup(source, codes, args, null, null);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable String[] codes, @Nullable Object[] args, @Nullable Locale locale) {
        return firstMessageLookup(source, codes, args, null, locale);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable Collection<String> codes, @Nullable Object[] args) {
        return firstMessageLookup(source, codes, args, null, null);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable Collection<String> codes, @Nullable Object[] args, @Nullable Locale locale) {
        return firstMessageLookup(source, codes, args, null, locale);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable String[] codes, @Nullable Object[] args, @Nullable String defaultMessage) {
        return firstMessageLookup(source, ArrayUtilsWraps.asList(codes), args, defaultMessage, null);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable String[] codes, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        return firstMessageLookup(source, ArrayUtilsWraps.asList(codes), args, defaultMessage, locale);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable Collection<String> codes, @Nullable Object[] args, @Nullable String defaultMessage) {
        return firstMessageLookup(source, codes, args, defaultMessage, null);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param codes the message codes to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable Collection<String> codes, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        if (source == null || CollectionUtils.isEmpty(codes)) {
            return defaultMessage;
        }
        if (locale == null) {
            locale = LocaleContextHolder.getLocale();
        }
        List<Locale> locales = LocaleUtils.localeLookupList(locale);
        if (CollectionUtils.isEmpty(locales)) {
            return defaultMessage;
        }
        return locales.stream().map(element -> firstMessage(source, codes, args, defaultMessage, element)).filter(StringUtils::isNotEmpty).findFirst().orElse(defaultMessage);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param resolvable the value object storing attributes required to resolve a message (may include a default message)
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable MessageSourceResolvable resolvable) {
        return firstMessageLookup(source, resolvable, null);
    }

    /**
     * Returns the first resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param resolvable the value object storing attributes required to resolve a message (may include a default message)
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the first resolved message from the message source, with locale lookup
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String firstMessageLookup(@Nullable MessageSource source, @Nullable MessageSourceResolvable resolvable, @Nullable Locale locale) {
        if (ObjectUtils.anyNull(source, resolvable)) {
            return null;
        }
        if (ArrayUtils.isEmpty(resolvable.getCodes())) {
            return resolvable.getDefaultMessage();
        }
        if (locale == null) {
            locale = LocaleContextHolder.getLocale();
        }
        List<Locale> locales = LocaleUtils.localeLookupList(locale);
        if (CollectionUtils.isEmpty(locales)) {
            return resolvable.getDefaultMessage();
        }
        return locales.stream().map(element -> firstMessage(source, resolvable, element)).filter(StringUtils::isNotEmpty).findFirst().orElse(resolvable.getDefaultMessage());
    }

    /**
     * Returns the resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     *
     * @return the resolved message from the message source
     */
    public static String getMessage(@Nullable MessageSource source, @Nullable String code) {
        return getMessage(source, code, null, null, null);
    }

    /**
     * Returns the resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the resolved message from the message source
     */
    public static String getMessage(@Nullable MessageSource source, @Nullable String code, @Nullable Locale locale) {
        return getMessage(source, code, null, null, locale);
    }

    /**
     * Returns the resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     *
     * @return the resolved message from the message source
     */
    public static String getMessage(@Nullable MessageSource source, @Nullable String code, @Nullable String defaultMessage) {
        return getMessage(source, code, null, defaultMessage, null);
    }

    /**
     * Returns the resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the resolved message from the message source
     */
    public static String getMessage(@Nullable MessageSource source, @Nullable String code, @Nullable String defaultMessage, @Nullable Locale locale) {
        return getMessage(source, code, null, defaultMessage, locale);
    }

    /**
     * Returns the resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     *
     * @return the resolved message from the message source
     */
    public static String getMessage(@Nullable MessageSource source, @Nullable String code, @Nullable Object[] args) {
        return getMessage(source, code, args, null, null);
    }

    /**
     * Returns the resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the resolved message from the message source
     */
    public static String getMessage(@Nullable MessageSource source, @Nullable String code, @Nullable Object[] args, @Nullable Locale locale) {
        return getMessage(source, code, args, null, locale);
    }

    /**
     * Returns the resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     *
     * @return the resolved message from the message source
     */
    public static String getMessage(@Nullable MessageSource source, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return getMessage(source, code, args, defaultMessage, null);
    }

    /**
     * Returns the resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the resolved message from the message source
     */
    public static String getMessage(@Nullable MessageSource source, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        if (source == null || StringUtils.isBlank(code)) {
            return defaultMessage;
        }
        try {
            return source.getMessage(code, args, defaultMessage, ObjectUtils.defaultIfNull(locale, LocaleContextHolder.getLocale()));
        } catch (Exception ignored) {
        }
        return defaultMessage;
    }

    /**
     * Returns the resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param resolvable the value object storing attributes required to resolve a message (may include a default message)
     *
     * @return the resolved message from the message source
     */
    public static String getMessage(@Nullable MessageSource source, @Nullable MessageSourceResolvable resolvable) {
        return getMessage(source, resolvable, null);
    }

    /**
     * Returns the resolved message from the message source
     *
     * @param source the message source to resolve code from
     * @param resolvable the value object storing attributes required to resolve a message (may include a default message)
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the resolved message from the message source
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String getMessage(@Nullable MessageSource source, @Nullable MessageSourceResolvable resolvable, @Nullable Locale locale) {
        if (ObjectUtils.anyNull(source, resolvable)) {
            return null;
        }
        if (locale == null) {
            locale = LocaleContextHolder.getLocale();
        }
        try {
            return source.getMessage(resolvable, locale);
        } catch (Exception ignored) {
        }
        return resolvable.getDefaultMessage();
    }

    /**
     * Returns the resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     *
     * @return the resolved message from the message source, with locale lookup
     */
    public static String getMessageLookup(@Nullable MessageSource source, @Nullable String code) {
        return getMessageLookup(source, code, null, null, null);
    }

    /**
     * Returns the resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the resolved message from the message source, with locale lookup
     */
    public static String getMessageLookup(@Nullable MessageSource source, @Nullable String code, @Nullable Locale locale) {
        return getMessageLookup(source, code, null, null, locale);
    }

    /**
     * Returns the resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     *
     * @return the resolved message from the message source, with locale lookup
     */
    public static String getMessageLookup(@Nullable MessageSource source, @Nullable String code, @Nullable Object[] args) {
        return getMessageLookup(source, code, args, null, null);
    }

    /**
     * Returns the resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the resolved message from the message source, with locale lookup
     */
    public static String getMessageLookup(@Nullable MessageSource source, @Nullable String code, @Nullable Object[] args, @Nullable Locale locale) {
        return getMessageLookup(source, code, args, null, locale);
    }

    /**
     * Returns the resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     *
     * @return the resolved message from the message source, with locale lookup
     */
    public static String getMessageLookup(@Nullable MessageSource source, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return getMessageLookup(source, code, args, defaultMessage, null);
    }

    /**
     * Returns the resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param code the message code to look up
     * @param args the arguments that will be filled in for params within the message (params look like "{0}", "{1,date}", "{2,time}" within a message), may be {@code null}
     * @param defaultMessage a default message to return if the lookup fails, may be {@code null}
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the resolved message from the message source, with locale lookup
     */
    public static String getMessageLookup(@Nullable MessageSource source, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        if (source == null || StringUtils.isBlank(code)) {
            return defaultMessage;
        }
        if (locale == null) {
            locale = LocaleContextHolder.getLocale();
        }
        List<Locale> locales = LocaleUtils.localeLookupList(locale);
        if (CollectionUtils.isEmpty(locales)) {
            return defaultMessage;
        }
        return locales.stream().map(element -> getMessage(source, code, args, defaultMessage, element)).filter(StringUtils::isNotEmpty).findFirst().orElse(defaultMessage);
    }

    /**
     * Returns the resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param resolvable the value object storing attributes required to resolve a message (may include a default message)
     *
     * @return the resolved message from the message source, with locale lookup
     */
    public static String getMessageLookup(@Nullable MessageSource source, @Nullable MessageSourceResolvable resolvable) {
        return getMessageLookup(source, resolvable, null);
    }

    /**
     * Returns the resolved message from the message source, with locale lookup
     *
     * @param source the message source to resolve code from
     * @param resolvable the value object storing attributes required to resolve a message (may include a default message)
     * @param locale the locale in which to do the lookup, may be {@code null}
     *
     * @return the resolved message from the message source, with locale lookup
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static String getMessageLookup(@Nullable MessageSource source, @Nullable MessageSourceResolvable resolvable, @Nullable Locale locale) {
        if (ObjectUtils.anyNull(source, resolvable)) {
            return null;
        }
        if (locale == null) {
            locale = LocaleContextHolder.getLocale();
        }
        List<Locale> locales = LocaleUtils.localeLookupList(locale);
        if (CollectionUtils.isEmpty(locales)) {
            return resolvable.getDefaultMessage();
        }
        return locales.stream().map(element -> getMessage(source, resolvable, element)).filter(StringUtils::isNotEmpty).findFirst().orElse(resolvable.getDefaultMessage());
    }
}
