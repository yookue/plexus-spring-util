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


import java.util.TimeZone;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.util.ClassUtils;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yookue.commonplexus.javaseutil.constant.TemporalFormatConst;


/**
 * Customizer of joda-time for {@link com.fasterxml.jackson.databind.ObjectMapper}
 *
 * @author David Hsing
 */
public abstract class JacksonJodaTimeCustomizer {
    @Nullable
    public static SimpleModule mapperModule(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat) {
        if (ClassUtils.isPresent("com.fasterxml.jackson.datatype.joda.JodaModule", null)) {    // $NON-NLS-1$
            return null;
        }
        dateFormat = StringUtils.defaultIfBlank(dateFormat, TemporalFormatConst.ISO_YYYYMMDD);
        timeFormat = StringUtils.defaultIfBlank(timeFormat, TemporalFormatConst.ISO_HHMMSS);
        dateTimeFormat = StringUtils.defaultIfBlank(dateTimeFormat, TemporalFormatConst.ISO_YYYYMMDD_HHMMSS);
        com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat dateFormatter = new com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat(org.joda.time.format.DateTimeFormat.forPattern(dateFormat));
        com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat timeFormatter = new com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat(org.joda.time.format.DateTimeFormat.forPattern(timeFormat));
        com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat dateTimeFormatter = new com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat(org.joda.time.format.DateTimeFormat.forPattern(dateTimeFormat));
        SimpleModule result = new com.fasterxml.jackson.datatype.joda.JodaModule();
        result.addSerializer(org.joda.time.LocalDate.class, new com.fasterxml.jackson.datatype.joda.ser.LocalDateSerializer(dateFormatter));
        result.addSerializer(org.joda.time.LocalTime.class, new com.fasterxml.jackson.datatype.joda.ser.LocalTimeSerializer(timeFormatter));
        result.addSerializer(org.joda.time.LocalDateTime.class, new com.fasterxml.jackson.datatype.joda.ser.LocalDateTimeSerializer(timeFormatter));
        result.addDeserializer(org.joda.time.LocalDate.class, new com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer(dateFormatter));
        result.addDeserializer(org.joda.time.LocalTime.class, new com.fasterxml.jackson.datatype.joda.deser.LocalTimeDeserializer(timeFormatter));
        result.addDeserializer(org.joda.time.LocalDateTime.class, new com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer(dateTimeFormatter));
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
