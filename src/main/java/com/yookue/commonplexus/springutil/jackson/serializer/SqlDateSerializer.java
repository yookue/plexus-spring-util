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

package com.yookue.commonplexus.springutil.jackson.serializer;


import java.text.DateFormat;
import jakarta.annotation.Nullable;
import lombok.NoArgsConstructor;


/**
 * {@link com.fasterxml.jackson.databind.JsonSerializer} for serializing {@link java.lang.String} to {@link java.sql.Date}
 * <p>
 * Usage: annotates on bean properties<br/>
 * <pre><code>
 *     {@literal @}JsonSerialize(using = SqlDateSerializer.class)
 * </code></pre>
 *
 * @author David Hsing
 * @see com.fasterxml.jackson.databind.JsonSerializer
 * @see com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
@NoArgsConstructor
@SuppressWarnings("unused")
public class SqlDateSerializer extends com.fasterxml.jackson.databind.ser.std.SqlDateSerializer {
    public SqlDateSerializer(@Nullable Boolean useTimestamp, @Nullable DateFormat dateFormat) {
        super(useTimestamp, dateFormat);
    }
}
