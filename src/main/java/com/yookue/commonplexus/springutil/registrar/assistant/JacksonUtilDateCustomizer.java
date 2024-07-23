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


import java.text.SimpleDateFormat;
import java.util.TimeZone;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yookue.commonplexus.javaseutil.constant.TemporalFormatConst;


/**
 * Customizer of util-date for {@link com.fasterxml.jackson.databind.ObjectMapper}
 *
 * @author David Hsing
 */
public abstract class JacksonUtilDateCustomizer {
    @Nonnull
    public static SimpleModule mapperModule(@Nullable String dateTimeFormat) {
        dateTimeFormat = StringUtils.defaultIfBlank(dateTimeFormat, TemporalFormatConst.ISO_YYYYMMDD_HHMMSS);
        SimpleModule result = new SimpleModule();
        result.addSerializer(java.util.Date.class, new com.fasterxml.jackson.databind.ser.std.DateSerializer(false, new SimpleDateFormat(dateTimeFormat)));
        result.addSerializer(java.sql.Date.class, new com.yookue.commonplexus.springutil.jackson.serializer.SqlDateSerializer(false, new SimpleDateFormat(dateTimeFormat)));
        result.addDeserializer(java.util.Date.class, new com.yookue.commonplexus.springutil.jackson.deserializer.UtilDateDeserializer());
        result.addDeserializer(java.sql.Date.class, new com.yookue.commonplexus.springutil.jackson.deserializer.SqlDateDeserializer());
        return result;
    }

    @Nonnull
    public static Jackson2ObjectMapperBuilderCustomizer mapperCustomizer(@Nullable String dateTimeFormat, @Nullable TimeZone timeZone) {
        return builder -> {
            builder.modulesToInstall(mapperModule(dateTimeFormat));
            builder.timeZone(ObjectUtils.defaultIfNull(timeZone, TimeZone.getDefault()));
        };
    }
}
