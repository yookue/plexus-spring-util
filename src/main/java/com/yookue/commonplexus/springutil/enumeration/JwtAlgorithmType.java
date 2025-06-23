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
 * Enumerations of jwt algorithm types
 *
 * @author David Hsing
 *
 * @see "com.auth0.jwt.algorithms.Algorithm"
 */
@AllArgsConstructor
@Getter
@SuppressWarnings("unused")
public enum JwtAlgorithmType implements ValueEnum<String> {
    NONE(null),    // $NON-NLS-1$
    HS256("HmacSHA256"),    // $NON-NLS-1$
    HS384("HmacSHA384"),    // $NON-NLS-1$
    HS512("HmacSHA512");    // $NON-NLS-1$

    private final String value;
}
