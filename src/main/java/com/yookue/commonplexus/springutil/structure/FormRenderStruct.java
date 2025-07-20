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

package com.yookue.commonplexus.springutil.structure;


import java.io.Serializable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import com.yookue.commonplexus.springutil.enumeration.FormRenderType;
import lombok.experimental.Accessors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Structure for form render
 *
 * @param <T> the type of the object inside
 *
 * @author David Hsing
 *
 * @reference "https://xrender.fun"
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class FormRenderStruct<T> implements Serializable {
    private FormRenderType type = FormRenderType.STRING;
    private T properties;

    @Nonnull
    public static <T> FormRenderStruct <T> of(@Nullable FormRenderType type, @Nullable T properties) {
        return new FormRenderStruct<>(type, properties);
    }
}
