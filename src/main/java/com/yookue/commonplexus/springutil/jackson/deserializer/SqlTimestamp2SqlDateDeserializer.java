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
import javax.annotation.Nullable;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.yookue.commonplexus.javaseutil.util.SqlDateWraps;


/**
 * {@link com.fasterxml.jackson.databind.JsonDeserializer} for deserializing {@link java.sql.Timestamp} {@link java.lang.Long} to {@link java.util.Date}
 * <p>
 * Usage: annotates on bean type, fields, or methods<br/>
 * <pre><code>
 *     {@literal @}JsonDeserialize(using = SqlTimestamp2SqlDateDeserializer.class)
 * </code></pre>
 *
 * @author David Hsing
 * @see com.fasterxml.jackson.databind.JsonDeserializer
 * @see com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
@SuppressWarnings("unused")
public class SqlTimestamp2SqlDateDeserializer extends SqlTimestamp2UtilDateDeserializer {
    @Override
    public Date deserialize(@Nullable JsonParser parser, @Nullable DeserializationContext context) throws IOException {
        return SqlDateWraps.castFromUtilDate(super.deserialize(parser, context));
    }
}
