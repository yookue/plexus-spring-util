/*
 * Copyright (c) 2016 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.ClassUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import cn.unikue.commonplexus.springutil.enumeration.JacksonTemporalType;
import cn.unikue.commonplexus.springutil.registrar.assistant.JacksonJdkDateCustomizer;
import cn.unikue.commonplexus.springutil.registrar.assistant.JacksonJodaTimeCustomizer;
import cn.unikue.commonplexus.springutil.registrar.assistant.JacksonJsr310Customizer;


/**
 * Utilities for {@link com.fasterxml.jackson.databind.ObjectMapper}
 *
 * @author David Hsing
 *
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @see org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
 * @see org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
 * @see org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JacksonConfigWraps {
    @Nonnull
    public static Jackson2ObjectMapperBuilderCustomizer dateTimeCustomizer(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone) {
        List<SimpleModule> modules = new ArrayList<>();
        modules.add(JacksonJdkDateCustomizer.mapperModule(dateFormat));
        if (ClassUtils.isPresent(JacksonTemporalType.JODA.getValue(), null)) {
            modules.add(JacksonJodaTimeCustomizer.mapperModule(dateFormat, timeFormat, dateTimeFormat));
        }
        if (ClassUtils.isPresent(JacksonTemporalType.JSR310.getValue(), null)) {
            modules.add(JacksonJsr310Customizer.mapperModule(dateFormat, timeFormat, dateTimeFormat));
        }
        SimpleModule[] alias = modules.stream().filter(Objects::nonNull).toArray(SimpleModule[]::new);
        return builder -> {
            builder.modulesToInstall(alias);
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

    /**
     * Return a {@link com.fasterxml.jackson.databind.json.JsonMapper.Builder} instance which is configured with the same features as the given {@code mapper}
     *
     * @param mapper The mapper to copy the features from
     *
     * @return a {@link com.fasterxml.jackson.databind.json.JsonMapper.Builder} instance which is configured with the same features as the given {@code mapper}
     *
     * @see com.fasterxml.jackson.databind.MapperFeature
     */
    @Nonnull
    public static JsonMapper.Builder jsonMapperBuilder(@Nullable ObjectMapper mapper) {
        JsonMapper.Builder builder = JsonMapper.builder().findAndAddModules();
        if (mapper == null) {
            return builder;
        }
        SerializationConfig serConfig = mapper.getSerializationConfig();
        DeserializationConfig deserConfig = mapper.getDeserializationConfig();
        Arrays.stream(SerializationFeature.values()).forEach(feature -> builder.configure(feature, serConfig.isEnabled(feature)));
        Arrays.stream(DeserializationFeature.values()).forEach(feature -> builder.configure(feature, deserConfig.isEnabled(feature)));
        Arrays.stream(MapperFeature.values()).forEach(feature -> builder.configure(feature, mapper.isEnabled(feature)));
        Arrays.stream(JsonGenerator.Feature.values()).forEach(feature -> builder.configure(feature, mapper.getFactory().isEnabled(feature)));
        builder.propertyNamingStrategy(mapper.getPropertyNamingStrategy()).serializationInclusion(serConfig.getDefaultPropertyInclusion().getValueInclusion()).defaultDateFormat(mapper.getDateFormat()).defaultLocale(serConfig.getLocale()).defaultTimeZone(serConfig.getTimeZone());
        return builder;
    }
}
