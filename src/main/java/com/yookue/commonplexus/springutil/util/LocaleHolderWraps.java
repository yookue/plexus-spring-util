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


import java.util.Locale;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import com.yookue.commonplexus.javaseutil.util.LocalePlainWraps;


/**
 * Utilities for {@link org.springframework.context.i18n.LocaleContextHolder}
 *
 * @author David Hsing
 * @see org.springframework.context.i18n.LocaleContextHolder
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class LocaleHolderWraps {
    @Nullable
    public static String getOptionalSpace() {
        return needOptionalSpace() ? StringUtils.SPACE : null;
    }

    public static boolean needOptionalSpace() {
        return !LocalePlainWraps.equalsLanguage(LocaleContextHolder.getLocale(), Locale.CHINESE);
    }

    @Nonnull
    public static String toLanguageTag() {
        return LocaleContextHolder.getLocale().toLanguageTag();
    }
}
