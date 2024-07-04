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
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * {@link com.fasterxml.jackson.databind.JsonDeserializer} for trimming {@link java.lang.String}
 * <p>
 * Usage: annotates on bean type, fields, or methods<br/>
 * <pre><code>
 *     {@literal @}JsonDeserialize(using = StringTrimmerDeserializer.class)
 * </code></pre>
 *
 * @author David Hsing
 * @reference "https://stackoverflow.com/questions/40180185/spring-controller-advice-to-trim-json-data"
 * @see com.fasterxml.jackson.databind.JsonDeserializer
 * @see com.fasterxml.jackson.databind.annotation.JsonDeserialize
 * @see org.springframework.beans.propertyeditors.StringTrimmerEditor
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class StringTrimmerDeserializer extends JsonDeserializer<String> {
    private boolean emptyAsNull = false;

    @Override
    public String deserialize(@Nullable JsonParser parser, @Nullable DeserializationContext context) throws IOException {
        if (parser == null) {
            return null;
        }
        return emptyAsNull ? StringUtils.trimToNull(parser.getText()) : StringUtils.trim(parser.getText());
    }
}
