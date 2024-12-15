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


import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.CharsetPlainWraps;
import com.yookue.commonplexus.javaseutil.util.DurationUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.NumberUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.UtilDateWraps;
import com.yookue.commonplexus.springutil.constant.AmqpHeaderConst;
import com.yookue.commonplexus.springutil.identity.AlternativeUuidGenerator;


/**
 * Utilities for AMQP messages
 *
 * @author David Hsing
 * @see org.springframework.amqp.core.MessagePostProcessor
 * @see org.springframework.amqp.core.MessageProperties
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class AmqpUtilsWraps {
    @Nonnull
    public static MessagePostProcessor buildBasicProcessor() {
        return buildBasicProcessor(null, null, null, null);
    }

    @Nonnull
    public static MessagePostProcessor buildBasicProcessor(@Nullable Map<String, Object> headers) {
        return buildBasicProcessor(headers, null, null, null);
    }

    @Nonnull
    public static MessagePostProcessor buildBasicProcessor(@Nullable Map<String, Object> headers, @Nullable String contentType, @Nullable Charset charset, @Nullable MessageDeliveryMode mode) {
        return buildDelayProcessor(Duration.ZERO, null, headers, contentType, charset, mode);
    }

    @Nonnull
    public static MessagePostProcessor buildDelayProcessor(@Nullable Duration delay, @Nullable Integer delayedTimes) {
        return buildDelayProcessor(delay, delayedTimes, null, null, null, null);
    }

    @Nonnull
    public static MessagePostProcessor buildDelayProcessor(@Nullable Duration delay, @Nullable Integer delayedTimes, @Nullable Map<String, Object> headers) {
        return buildDelayProcessor(delay, delayedTimes, headers, null, null, null);
    }

    @Nonnull
    public static MessagePostProcessor buildDelayProcessor(@Nullable Duration delay, @Nullable Integer delayedTimes, @Nullable Map<String, Object> headers, @Nullable String contentType, @Nullable Charset charset, @Nullable MessageDeliveryMode mode) {
        return message -> {
            if (message == null || message.getMessageProperties() == null) {
                return null;
            }
            MessageProperties properties = message.getMessageProperties();
            MapPlainWraps.forEach(headers, properties::setHeader);
            DurationUtilsWraps.ifPositive(delay, element -> properties.setHeader(MessageProperties.X_DELAY, element.toMillis()));
            properties.setHeader(AmqpHeaderConst.X_DELAYED_TIMES, NumberUtilsWraps.isPositive(delayedTimes) ? delayedTimes : 0);
            properties.setContentType(StringUtils.defaultIfBlank(contentType, MessageProperties.CONTENT_TYPE_JSON));
            properties.setContentEncoding(CharsetPlainWraps.defaultCharsetName(charset));
            properties.setDeliveryMode(mode != null ? mode : MessageDeliveryMode.PERSISTENT);
            properties.setMessageId(AlternativeUuidGenerator.getBalanceId());
            properties.setTimestamp(UtilDateWraps.getCurrentDateTime());
            return message;
        };
    }

    public static String getContentAsString(@Nullable Message message) {
        return getContentAsString(message, null);
    }

    /**
     * @see org.springframework.amqp.core.Message#getBodyContentAsString
     */
    @SuppressWarnings("JavadocReference")
    public static String getContentAsString(@Nullable Message message, @Nullable Charset fallbackCharset) {
        if (message == null || message.getMessageProperties() == null || ArrayUtils.isEmpty(message.getBody())) {
            return null;
        }
        MessageProperties properties = message.getMessageProperties();
        if (StringUtils.equalsIgnoreCase(properties.getContentType(), MessageProperties.CONTENT_TYPE_SERIALIZED_OBJECT)) {
            return ObjectUtilsWraps.getDisplayString(SerializationUtils.deserialize(message.getBody()));
        }
        String charset = CharsetPlainWraps.defaultCharsetName(fallbackCharset);
        return IOUtils.toString(message.getBody(), StringUtils.defaultIfBlank(properties.getContentEncoding(), charset));
    }

    public static Integer getDelayedTimes(@Nullable Message message) {
        return getHeaderAs(message, AmqpHeaderConst.X_DELAYED_TIMES, Integer.class);
    }

    public static Object getHeader(@Nullable Message message, @Nullable String header) {
        return (message == null || StringUtils.isBlank(header)) ? null : MapPlainWraps.getObject(getHeaders(message), header);
    }

    @Nullable
    public static <T> T getHeaderAs(@Nullable Message message, @Nullable String header, @Nullable Class<T> expectedType) {
        if (ObjectUtils.anyNull(message, expectedType) || StringUtils.isBlank(header)) {
            return null;
        }
        return MapPlainWraps.getObjectAs(getHeaders(message), header, expectedType);
    }

    public static Map<String, Object> getHeaders(@Nullable Message message) {
        return (message == null || message.getMessageProperties() == null) ? null : message.getMessageProperties().getHeaders();
    }

    @Nullable
    public static Map<String, Object> getHeadersExcluding(@Nullable Message message, @Nullable String... headers) {
        return getHeadersExcluding(message, ArrayUtilsWraps.asList(headers));
    }

    @Nullable
    public static Map<String, Object> getHeadersExcluding(@Nullable Message message, @Nullable Collection<String> headers) {
        Map<String, Object> result = getHeaders(message);
        MapPlainWraps.removeByKeys(result, headers);
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    public static Map<String, Object> getHeadersExcludingDelay(@Nullable Message message) {
        return getHeadersExcluding(message, MessageProperties.X_DELAY, AmqpHeaderConst.X_DELAYED_TIMES);
    }

    @Nullable
    public static Map<String, Object> getHeadersIncluding(@Nullable Message message, @Nullable String... headers) {
        return getHeadersIncluding(message, ArrayUtilsWraps.asList(headers));
    }

    @Nullable
    public static Map<String, Object> getHeadersIncluding(@Nullable Message message, @Nullable Collection<String> headers) {
        Map<String, Object> result = getHeaders(message);
        MapPlainWraps.removeIfKey(result, key -> !StringUtilsWraps.equalsAny(key, headers));
        return CollectionUtils.isEmpty(result) ? null : result;
    }
}
