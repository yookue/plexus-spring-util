/*
 * Copyright (c) 2016 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.enumeration;


import cn.unikue.commonplexus.javaseutil.enumeration.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


/**
 * Enumerations of jackson temporal types
 *
 * @author David Hsing
 */
@AllArgsConstructor
@Getter
@ToString
@SuppressWarnings("unused")
public enum JacksonTemporalType implements ValueEnum<String> {
    JODA("com.fasterxml.jackson.datatype.joda.JodaModule"),    // $NON-NLS-1$
    JSR310("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule");    // $NON-NLS-1$

    private final String value;
}
