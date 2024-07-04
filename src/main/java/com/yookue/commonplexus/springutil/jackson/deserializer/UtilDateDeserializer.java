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

package com.yookue.commonplexus.springutil.jackson.deserializer;


import java.io.IOException;
import java.util.Date;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.yookue.commonplexus.javaseutil.constant.TemporalFormatCombo;
import com.yookue.commonplexus.javaseutil.util.UtilDateWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link com.fasterxml.jackson.databind.JsonDeserializer} for deserializing {@link java.lang.String} to  {@link java.util.Date}
 * <p>
 * Usage: annotates on bean type, fields, or methods<br/>
 * <pre><code>
 *     {@literal @}JsonDeserialize(using = UtilDateDeserializer.class)
 * </code></pre>
 *
 * @author David Hsing
 * @see com.fasterxml.jackson.databind.JsonDeserializer
 * @see com.fasterxml.jackson.databind.annotation.JsonDeserialize
 * @see org.springframework.format.annotation.DateTimeFormat
 */
@Getter
@Setter
public class UtilDateDeserializer extends JsonDeserializer<Date> {
    private String[] dateFormats = TemporalFormatCombo.ALL_DATETIME_DATES;

    @Override
    public Date deserialize(@Nullable JsonParser parser, @Nullable DeserializationContext context) throws IOException {
        if (parser == null || StringUtils.isBlank(parser.getText())) {
            return null;
        }
        return UtilDateWraps.parseDateTimeWithFormats(StringUtils.trimToNull(parser.getText()), dateFormats);
    }
}
