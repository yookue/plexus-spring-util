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


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.ClassUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.yookue.commonplexus.javaseutil.constant.TemporalFormatConst;
import com.yookue.commonplexus.springutil.jackson.deserializer.SqlDateDeserializer;
import com.yookue.commonplexus.springutil.jackson.deserializer.UtilDateDeserializer;
import com.yookue.commonplexus.springutil.jackson.serializer.SqlDateSerializer;


/**
 * Utilities for {@link com.fasterxml.jackson.databind.ObjectMapper}
 *
 * @author David Hsing
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @see org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
 * @see org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
 * @see org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JacksonConfigWraps {
    @Nonnull
    @SuppressWarnings("ParameterCanBeLocal")
    public static Jackson2ObjectMapperBuilderCustomizer dateTimeCustomizer(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone) {
        dateFormat = StringUtils.defaultIfBlank(dateTimeFormat, TemporalFormatConst.ISO_YYYYMMDD);
        timeFormat = StringUtils.defaultIfBlank(dateTimeFormat, TemporalFormatConst.ISO_HHMMSS);
        dateTimeFormat = StringUtils.defaultIfBlank(dateTimeFormat, TemporalFormatConst.ISO_YYYYMMDD_HHMMSS);
        // Prepare jackson install modules
        List<Module> prepareModules = new ArrayList<>();
        SimpleModule utilModule = new SimpleModule();
        utilModule.addSerializer(java.util.Date.class, new DateSerializer(false, new SimpleDateFormat(dateTimeFormat)));
        utilModule.addSerializer(java.sql.Date.class, new SqlDateSerializer(false, new SimpleDateFormat(dateTimeFormat)));
        utilModule.addDeserializer(java.util.Date.class, new UtilDateDeserializer());
        utilModule.addDeserializer(java.sql.Date.class, new SqlDateDeserializer());
        prepareModules.add(utilModule);
        if (ClassUtils.isPresent("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule", null)) {    // $NON-NLS-1$
            java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern(dateFormat);
            java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern(timeFormat);
            java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern(dateTimeFormat);
            SimpleModule jsrModule = new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule();
            jsrModule.addSerializer(java.time.LocalDate.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer(dateFormatter));
            jsrModule.addSerializer(java.time.LocalTime.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer(timeFormatter));
            jsrModule.addSerializer(java.time.LocalDateTime.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer(dateTimeFormatter));
            jsrModule.addDeserializer(java.time.LocalDate.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer(dateFormatter));
            jsrModule.addDeserializer(java.time.LocalTime.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer(timeFormatter));
            jsrModule.addDeserializer(java.time.LocalDateTime.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer(dateTimeFormatter));
            prepareModules.add(jsrModule);
        }
        if (ClassUtils.isPresent("com.fasterxml.jackson.datatype.joda.JodaModule", null)) {    // $NON-NLS-1$
            com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat dateFormatter = new com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat(org.joda.time.format.DateTimeFormat.forPattern(dateFormat));
            com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat timeFormatter = new com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat(org.joda.time.format.DateTimeFormat.forPattern(timeFormat));
            com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat dateTimeFormatter = new com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat(org.joda.time.format.DateTimeFormat.forPattern(dateTimeFormat));
            SimpleModule jodaModule = new com.fasterxml.jackson.datatype.joda.JodaModule();
            jodaModule.addSerializer(org.joda.time.LocalDate.class, new com.fasterxml.jackson.datatype.joda.ser.LocalDateSerializer(dateFormatter));
            jodaModule.addSerializer(org.joda.time.LocalTime.class, new com.fasterxml.jackson.datatype.joda.ser.LocalTimeSerializer(timeFormatter));
            jodaModule.addSerializer(org.joda.time.LocalDateTime.class, new com.fasterxml.jackson.datatype.joda.ser.LocalDateTimeSerializer(timeFormatter));
            jodaModule.addDeserializer(org.joda.time.LocalDate.class, new com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer(dateFormatter));
            jodaModule.addDeserializer(org.joda.time.LocalTime.class, new com.fasterxml.jackson.datatype.joda.deser.LocalTimeDeserializer(timeFormatter));
            jodaModule.addDeserializer(org.joda.time.LocalDateTime.class, new com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer(dateTimeFormatter));
            prepareModules.add(jodaModule);
        }
        return builder -> {
            builder.modulesToInstall(prepareModules.toArray(new Module[0]));
            builder.timeZone(ObjectUtils.defaultIfNull(timeZone, TimeZone.getDefault()));
        };
    }

    @Nonnull
    public static ObjectMapper jsonObjectMapper() {
        return jsonObjectMapper(null, null, null, null, null);
    }

    @Nonnull
    public static ObjectMapper jsonObjectMapper(@Nullable TimeZone timeZone, @Nullable Locale locale) {
        return jsonObjectMapper(null, null, null, timeZone, locale);
    }

    @Nonnull
    public static ObjectMapper jsonObjectMapper(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone, @Nullable Locale locale) {
        return jsonObjectMapperBuilder(dateFormat, timeFormat, dateTimeFormat, timeZone, locale).build();
    }

    @Nonnull
    public static ObjectMapper jsonObjectMapper(@Nullable JacksonProperties properties) {
        return (properties == null) ? jsonObjectMapper() : jsonObjectMapper(properties.getDateFormat(), null, null, properties.getTimeZone(), properties.getLocale());
    }

    @Nonnull
    public static Jackson2ObjectMapperBuilder jsonObjectMapperBuilder() {
        return jsonObjectMapperBuilder(null, null, null, null, null);
    }

    @Nonnull
    public static Jackson2ObjectMapperBuilder jsonObjectMapperBuilder(@Nullable TimeZone timeZone, @Nullable Locale locale) {
        return jsonObjectMapperBuilder(null, null, null, timeZone, locale);
    }

    @Nonnull
    public static Jackson2ObjectMapperBuilder jsonObjectMapperBuilder(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone, @Nullable Locale locale) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.failOnEmptyBeans(false);
        builder.failOnUnknownProperties(false);
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        builder.featuresToDisable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        builder.locale(ObjectUtils.defaultIfNull(locale, Locale.getDefault()));
        Jackson2ObjectMapperBuilderCustomizer dateTimeCustomizer = dateTimeCustomizer(dateFormat, timeFormat, dateTimeFormat, timeZone);
        dateTimeCustomizer.customize(builder);
        return builder;
    }

    @Nonnull
    public static ObjectMapper xmlObjectMapper() {
        return xmlObjectMapper(null, null, null, null, null);
    }

    @Nonnull
    public static ObjectMapper xmlObjectMapper(@Nullable TimeZone timeZone, @Nullable Locale locale) {
        return xmlObjectMapper(null, null, null, timeZone, locale);
    }

    @Nonnull
    public static ObjectMapper xmlObjectMapper(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone, @Nullable Locale locale) {
        return xmlObjectMapperBuilder(dateFormat, timeFormat, dateTimeFormat, timeZone, locale).build();
    }

    @Nonnull
    public static Jackson2ObjectMapperBuilder xmlObjectMapperBuilder() {
        return xmlObjectMapperBuilder(null, null, null, null, null);
    }

    @Nonnull
    public static Jackson2ObjectMapperBuilder xmlObjectMapperBuilder(@Nullable TimeZone timeZone, @Nullable Locale locale) {
        return xmlObjectMapperBuilder(null, null, null, timeZone, locale);
    }

    @Nonnull
    public static Jackson2ObjectMapperBuilder xmlObjectMapperBuilder(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone, @Nullable Locale locale) {
        return jsonObjectMapperBuilder(dateFormat, timeFormat, dateTimeFormat, timeZone, locale).createXmlMapper(true);
    }
}
