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

package com.yookue.commonplexus.springutil.enumeration;


import com.yookue.commonplexus.javaseutil.enumeration.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


/**
 * Enumerations of form render types
 *
 * @author David Hsing
 *
 * @reference "https://xrender.fun/form-render/api-schema#type-1"
 */
@AllArgsConstructor
@Getter
@ToString
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public enum FormRenderType implements ValueEnum<String> {
    STRING("string"),    // $NON-NLS-1$
    NUMBER("number"),    // $NON-NLS-1$
    BOOLEAN("boolean"),    // $NON-NLS-1$
    ARRAY("array"),    // $NON-NLS-1$
    RANGE("range"),    // $NON-NLS-1$
    HTML("html"),    // $NON-NLS-1$
    VOID("void");    // $NON-NLS-1$

    private final String value;
}
