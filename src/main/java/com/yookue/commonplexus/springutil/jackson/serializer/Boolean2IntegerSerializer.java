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


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.function.Failable;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;


/**
 * {@link com.fasterxml.jackson.databind.JsonSerializer} for serializing {@link java.lang.Boolean} to {@link java.lang.Integer} with values "1/0"
 * <p>
 * Usage: annotates on bean properties<br/>
 * <pre><code>
 *     {@literal @}JsonSerialize(using = Boolean2IntegerSerializer.class)
 * </code></pre>
 *
 * @author David Hsing
 * @see com.fasterxml.jackson.databind.JsonSerializer
 * @see com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
@SuppressWarnings("unused")
public class Boolean2IntegerSerializer extends JsonSerializer<Boolean> {
    @Override
    public void serialize(@Nullable Boolean value, @Nonnull JsonGenerator generator, @Nullable SerializerProvider provider) {
        ObjectUtilsWraps.ifNotNull(BooleanUtils.toInteger(value), Failable.asConsumer(generator::writeNumber));
    }
}
