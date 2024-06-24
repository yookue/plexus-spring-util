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


import com.yookue.commonplexus.javaseutil.support.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Enumerations of Spring Security "X-Frame-Options" header types
 *
 * @author David Hsing
 * @reference "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options"
 * @reference "https://html.spec.whatwg.org/multipage/document-lifecycle.html#the-x-frame-options-header"
 * @see org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode
 */
@AllArgsConstructor
@Getter
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public enum XFrameOptionType implements ValueEnum<String> {
    DENY("deny"),    // $NON-NLS-1$
    SAME_ORIGIN("sameorigin"),    // $NON-NLS-1$
    ALLOW_FROM("allow-from"),    // $NON-NLS-1$
    ALLOW_ALL("allowall"),    // $NON-NLS-1$
    INVALID("invalid");    // $NON-NLS-1$

    private final String value;
}
