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


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.mail.internet.InternetAddress;
import org.apache.commons.lang3.StringUtils;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;


/**
 * Utilities for jakarta mail
 *
 * @author David Hsing
 * @see javax.mail.internet.MimeUtility
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JakartaMailWraps {
    @Nullable
    public static InternetAddress mailAddress(@Nullable String email) throws UnsupportedEncodingException {
        return mailAddress(email, null);
    }

    /**
     * Returns a new {@link javax.mail.internet.InternetAddress} with the specified email
     *
     * @param email the given email address
     * @param personal whether to extract personal information automatically (before the character @)
     *
     * @return a new {@link javax.mail.internet.InternetAddress} with the specified email
     *
     * @throws java.io.UnsupportedEncodingException if the personal name can't be encoded in the default charset
     */
    @Nullable
    public static InternetAddress mailAddress(@Nullable String email, boolean personal) throws UnsupportedEncodingException {
        return mailAddress(email, (personal ? StringUtils.substringBefore(email, CharVariantConst.AT) : null));
    }

    @Nullable
    public static InternetAddress mailAddress(@Nullable String email, @Nullable String personal) throws UnsupportedEncodingException {
        return mailAddress(email, personal, null);
    }

    @Nullable
    public static InternetAddress mailAddress(@Nullable String email, @Nullable String personal, @Nullable Charset charset) throws UnsupportedEncodingException {
        return StringUtils.isBlank(email) ? null : new InternetAddress(email, personal, Objects.toString(charset, null));
    }
}
