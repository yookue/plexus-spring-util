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
import lombok.ToString;


/**
 * Enumerations of slider trigger types
 *
 * @author David Hsing
 * @reference "https://github.com/caijf/rc-slider-captcha"
 */
@AllArgsConstructor
@Getter
@ToString
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public enum SliderTriggerType implements ValueEnum<String> {
    PUZZLE("puzzle"),    // $NON-NLS-1$
    BUTTON("button");    // $NON-NLS-1$

    private final String value;
}
