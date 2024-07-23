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

package com.yookue.commonplexus.springutil.registrar.assistant;


import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.util.ClassUtils;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yookue.commonplexus.javaseutil.constant.TemporalFormatConst;


/**
 * Customizer of jsr-310 date for {@link com.fasterxml.jackson.databind.ObjectMapper}
 *
 * @author David Hsing
 */
public abstract class JacksonJsr310Customizer {
    @Nullable
    public static SimpleModule mapperModule(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat) {
        if (ClassUtils.isPresent("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule", null)) {    // $NON-NLS-1$
            return null;
        }
        dateFormat = StringUtils.defaultIfBlank(dateFormat, TemporalFormatConst.ISO_YYYYMMDD);
        timeFormat = StringUtils.defaultIfBlank(timeFormat, TemporalFormatConst.ISO_HHMMSS);
        dateTimeFormat = StringUtils.defaultIfBlank(dateTimeFormat, TemporalFormatConst.ISO_YYYYMMDD_HHMMSS);
        SimpleModule result = new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeFormat);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        result.addSerializer(java.time.LocalDate.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer(dateFormatter));
        result.addSerializer(java.time.LocalTime.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer(timeFormatter));
        result.addSerializer(java.time.LocalDateTime.class, new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer(dateTimeFormatter));
        result.addDeserializer(java.time.LocalDate.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer(dateFormatter));
        result.addDeserializer(java.time.LocalTime.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer(timeFormatter));
        result.addDeserializer(java.time.LocalDateTime.class, new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer(dateTimeFormatter));
        return result;
    }

    @Nullable
    public static Jackson2ObjectMapperBuilderCustomizer mapperCustomizer(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone) {
        SimpleModule module = mapperModule(dateFormat, timeFormat, dateTimeFormat);
        return (module == null) ? null : builder -> {
            builder.modulesToInstall(module);
            builder.timeZone(ObjectUtils.defaultIfNull(timeZone, TimeZone.getDefault()));
        };
    }
}
