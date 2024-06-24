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


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Utilities for {@link org.springframework.security.crypto.password.PasswordEncoder}
 *
 * @author David Hsing
 * @see org.springframework.security.crypto.password.PasswordEncoder
 * @see org.springframework.security.crypto.factory.PasswordEncoderFactories
 * @see com.yookue.commonplexus.javaseutil.enumeration.SecurityAlgorithmType
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class PasswordEncoderWraps {
    @Nonnull
    public static PasswordEncoder bCryptEncoder(int strength) {
        return new BCryptPasswordEncoder(strength);
    }

    @Nullable
    public static PasswordEncoder bCryptEncoder(int strength, @Nullable String algorithm) throws NoSuchAlgorithmException {
        return StringUtils.isBlank(algorithm) ? null : new BCryptPasswordEncoder(strength, SecureRandom.getInstance(algorithm));
    }

    @Nullable
    public static PasswordEncoder bCryptEncoderQuietly(int strength, @Nullable String algorithm) {
        try {
            return bCryptEncoder(strength, algorithm);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static PasswordEncoder bCryptEncoder(@Nullable BCryptPasswordEncoder.BCryptVersion version, @Nullable String algorithm) throws NoSuchAlgorithmException {
        return (version == null || StringUtils.isBlank(algorithm)) ? null : new BCryptPasswordEncoder(version, SecureRandom.getInstance(algorithm));
    }

    @Nullable
    public static PasswordEncoder bCryptEncoderQuietly(@Nullable BCryptPasswordEncoder.BCryptVersion version, @Nullable String algorithm) {
        try {
            return bCryptEncoder(version, algorithm);
        } catch (Exception ignored) {
        }
        return null;
    }
}
