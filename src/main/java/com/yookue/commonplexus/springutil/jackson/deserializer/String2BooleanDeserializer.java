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
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


/**
 * {@link com.fasterxml.jackson.databind.JsonDeserializer} for deserializing {@link java.lang.String} to {@link java.lang.Boolean}
 * <p>
 * Usage: annotates on bean type, fields, or methods<br/>
 * <pre><code>
 *     {@literal @}JsonDeserialize(using = String2BooleanDeserializer.class)
 * </code></pre>
 *
 * @author David Hsing
 * @see com.fasterxml.jackson.databind.JsonDeserializer
 * @see com.fasterxml.jackson.databind.annotation.JsonDeserialize
 * @see org.apache.commons.lang3.BooleanUtils#toBoolean(String)
 */
@SuppressWarnings("unused")
public class String2BooleanDeserializer extends JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(@Nullable JsonParser parser, @Nullable DeserializationContext context) throws IOException {
        if (parser == null || StringUtils.isBlank(parser.getText())) {
            return null;
        }
        String text = StringUtils.trimToNull(parser.getText());
        if (StringUtils.equalsAnyIgnoreCase(text, "true", "yes", "on", "y", "t", "1")) {    // $NON-NLS-1$ // $NON-NLS-2$ // $NON-NLS-3$ // $NON-NLS-4$ // $NON-NLS-5$ // $NON-NLS-6$
            return Boolean.TRUE;
        } else if (StringUtils.equalsAnyIgnoreCase(text, "false", "no", "off", "n", "f", "0")) {    // $NON-NLS-1$ // $NON-NLS-2$ // $NON-NLS-3$ // $NON-NLS-4$ // $NON-NLS-5$ // $NON-NLS-6$
            return Boolean.FALSE;
        }
        return null;
    }
}
