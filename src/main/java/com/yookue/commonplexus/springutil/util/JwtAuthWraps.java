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


import java.time.Duration;
import java.util.Date;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yookue.commonplexus.javaseutil.identity.JdkUuidGenerator;
import com.yookue.commonplexus.javaseutil.util.JdkDateWraps;
import com.yookue.commonplexus.springutil.enumeration.JwtAlgorithmType;


/**
 * Utilities for JWT
 *
 * @author David Hsing
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JwtAuthWraps {
    @Nonnull
    public static String encodeToken(@Nullable String audience, @Nullable String issuer, @Nullable String subject, @Nullable Date timestamp, @Nullable Duration timeout, @Nullable JwtAlgorithmType algorithm, @Nullable String secret) {
        JWTCreator.Builder builder = JWT.create();
        builder.withJWTId(JdkUuidGenerator.getPopularId());
        if (StringUtils.isNotBlank(audience)) {
            builder.withAudience(audience);
        }
        if (StringUtils.isNotBlank(issuer)) {
            builder.withIssuer(issuer);
        }
        if (StringUtils.isNotBlank(subject)) {
            builder.withSubject(subject);
        }
        Date timestampAlias = ObjectUtils.defaultIfNull(timestamp, JdkDateWraps.getCurrentDateTime());
        builder.withIssuedAt(timestampAlias);
        if (timeout != null) {
            builder.withExpiresAt(JdkDateWraps.plusTemporal(timestampAlias, timeout));
        }
        JwtAlgorithmType algorithmAlias = ObjectUtils.defaultIfNull(algorithm, JwtAlgorithmType.HS256);
        if (algorithmAlias != JwtAlgorithmType.NONE) {
            Assert.notNull(secret, "The secret must not be null");
        }
        return switch (algorithmAlias) {
            case NONE -> builder.sign(Algorithm.none());
            case HS256 -> builder.sign(Algorithm.HMAC256(secret));
            case HS384 -> builder.sign(Algorithm.HMAC384(secret));
            case HS512 -> builder.sign(Algorithm.HMAC512(secret));
        };
    }

    @Nullable
    public static DecodedJWT decodeToken(@Nullable String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            return JWT.decode(token);
        } catch (Exception ignored) {
            return null;
        }
    }
}
