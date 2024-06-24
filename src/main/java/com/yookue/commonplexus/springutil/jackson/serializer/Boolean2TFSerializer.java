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


import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yookue.commonplexus.javaseutil.util.BooleanUtilsWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link com.fasterxml.jackson.databind.JsonSerializer} for serializing {@link java.lang.Boolean} to {@link java.lang.String} with values "t/f"
 * <p>
 * Usage: annotates on bean properties<br/>
 * <pre><code>
 *     {@literal @}JsonSerialize(using = Boolean2TFSerializer.class)
 * </code></pre>
 *
 * @author David Hsing
 * @see com.fasterxml.jackson.databind.JsonSerializer
 * @see com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class Boolean2TFSerializer extends JsonSerializer<Boolean> {
    private boolean uppercase = false;

    @Override
    public void serialize(@Nullable Boolean value, @Nonnull JsonGenerator generator, @Nullable SerializerProvider provider) throws IOException {
        generator.writeString(BooleanUtilsWraps.toStringTF(value, uppercase));
    }
}
