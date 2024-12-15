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

package com.yookue.commonplexus.springutil.identity;


import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;


/**
 * UUID generator by {@link org.springframework.util.AlternativeJdkIdGenerator}
 *
 * @author David Hsing
 * @see org.springframework.util.AlternativeJdkIdGenerator
 */
@SuppressWarnings("unused")
public abstract class AlternativeUuidGenerator {
    private static final IdGenerator idGenerator = new AlternativeJdkIdGenerator();

    /**
     * Returns an uuid string with uppercase and none hyphens
     *
     * @return an uuid string with uppercase and none hyphens
     */
    @Nonnull
    public static String getCapitalId() {
        return getBalanceId(true, false);
    }

    /**
     * Returns an uuid string with lowercase and none hyphens
     *
     * @return an uuid string with lowercase and none hyphens
     */
    @Nonnull
    public static String getPopularId() {
        return getBalanceId(false, false);
    }

    @Nonnull
    public static String getBalanceId() {
        return getBalanceId(false, true);
    }

    @Nonnull
    public static String getBalanceId(boolean uppercase) {
        return getBalanceId(uppercase, true);
    }

    @Nonnull
    public static String getBalanceId(boolean uppercase, boolean hyphen) {
        String result = idGenerator.generateId().toString();
        result = uppercase ? StringUtils.upperCase(result) : StringUtils.lowerCase(result);
        return hyphen ? result : StringUtils.remove(result, CharVariantConst.HYPHEN);
    }
}
