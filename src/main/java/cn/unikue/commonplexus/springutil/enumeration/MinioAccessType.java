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
 * Enumerations of minio access types
 *
 * @author David Hsing
 */
@AllArgsConstructor
@Getter
@ToString
@SuppressWarnings("unused")
public enum MinioAccessType implements ValueEnum<String> {
    DEFAULT("default"),    // $NON-NLS-1$
    PRIVATE("private"),    // $NON-NLS-1$
    PUBLIC_READ("public-read"),    // $NON-NLS-1$
    PUBLIC_READ_WRITE("public-read-write"),    // $NON-NLS-1$
    AUTHENTICATED_READ("authenticated-read"),    // $NON-NLS-1$
    AUTHENTICATED_READ_WRITE("authenticated-read-write");    // $NON-NLS-1$

    private final String value;
}
