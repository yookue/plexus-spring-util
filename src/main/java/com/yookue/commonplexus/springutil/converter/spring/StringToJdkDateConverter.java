/*
 * Copyright (c) 2025 Yookue Ltd. All rights reserved.
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

package com.yookue.commonplexus.springutil.converter.spring;


import java.util.Date;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import com.yookue.commonplexus.javaseutil.constant.TemporalFormatCombo;
import com.yookue.commonplexus.javaseutil.util.JdkDateWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * Converts an object to {@link java.util.Date}
 *
 * @author David Hsing
 *
 * @see org.springframework.core.convert.converter.Converter
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class StringToJdkDateConverter implements Converter<String, Date> {
    private String[] formats = TemporalFormatCombo.ALL_DATETIME_DATES;

    @Override
    public Date convert(@Nullable String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (String pattern : formats) {
            Date result = JdkDateWraps.parseDateQuietly(value, pattern);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
