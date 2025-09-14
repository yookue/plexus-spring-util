/*
 * Copyright (c) 2025 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.converter.spring;


import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import cn.unikue.commonplexus.javaseutil.constant.TemporalFormatCombo;
import cn.unikue.commonplexus.javaseutil.util.LocalDateWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * Converts an object to {@link java.time.LocalDateTime}
 *
 * @author David Hsing
 *
 * @see org.springframework.core.convert.converter.Converter
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    private String[] formats = TemporalFormatCombo.DATETIME_FORMATS;
    private String[] spares = TemporalFormatCombo.DATE_FORMATS;

    @Override
    public LocalDateTime convert(@Nullable String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (String pattern : formats) {
            LocalDateTime result = LocalDateWraps.parseDateTimeQuietly(value, pattern);
            if (result != null) {
                return result;
            }
        }
        for (String pattern : spares) {
            LocalDate date = LocalDateWraps.parseDateQuietly(value, pattern);
            if (date != null) {
                return LocalDateWraps.toLocalDateTime(date);
            }
        }
        return null;
    }
}
