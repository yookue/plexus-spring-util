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

package com.yookue.commonplexus.springutil.util;


import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import com.google.common.base.CaseFormat;


/**
 * Utilities for {@link com.google.common.base.CaseFormat}
 *
 * @author David Hsing
 * @see com.google.common.base.CaseFormat
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class CaseFormatWraps {
    public static String lowerHyphen2LowerUnderscore(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_UNDERSCORE, text);
    }

    public static String lowerHyphen2LowerCamel(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, text);
    }

    public static String lowerHyphen2UpperCamel(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, text);
    }

    public static String lowerHyphen2UpperUnderscore(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_UNDERSCORE, text);
    }

    public static String lowerUnderscore2LowerHyphen(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, text);
    }

    public static String lowerUnderscore2LowerCamel(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, text);
    }

    public static String lowerUnderscore2UpperCamel(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, text);
    }

    public static String lowerUnderscore2UpperUnderscore(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, text);
    }

    public static String lowerCamel2LowerHyphen(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, text);
    }

    public static String lowerCamel2LowerUnderscore(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, text);
    }

    public static String lowerCamel2UpperCamel(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, text);
    }

    public static String lowerCamel2UpperUnderscore(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, text);
    }

    public static String upperCamel2LowerHyphen(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, text);
    }

    public static String upperCamel2LowerUnderscore(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, text);
    }

    public static String upperCamel2LowerCamel(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, text);
    }

    public static String upperCamel2UpperUnderscore(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, text);
    }

    public static String upperUnderscore2LowerHyphen(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, text);
    }

    public static String upperUnderscore2LowerUnderscore(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, text);
    }

    public static String upperUnderscore2LowerCamel(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, text);
    }

    public static String upperUnderscore2UpperCamel(@Nullable String text) {
        return StringUtils.isBlank(text) ? text : CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, text);
    }
}
