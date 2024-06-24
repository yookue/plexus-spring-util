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
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.yookue.commonplexus.javaseutil.util.CharsetPlainWraps;
import com.yookue.commonplexus.javaseutil.util.DurationUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.NumberUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.UtilDateWraps;
import com.yookue.commonplexus.springutil.constant.AmqpHeaderConst;
import com.yookue.commonplexus.springutil.identity.AlternativeUuidGenerator;


/**
 * Utilities for RabbitMQ
 *
 * @author David Hsing
 * @reference "https://www.rabbitmq.com/"
 * @see com.rabbitmq.client.AMQP.BasicProperties
 * @see org.springframework.boot.autoconfigure.amqp.RabbitProperties
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public abstract class RabbitMqWraps {
    @Nonnull
    public static BasicProperties buildBasicProperties() {
        return buildBasicProperties(null, null, null, null);
    }

    @Nonnull
    public static BasicProperties buildBasicProperties(@Nullable Map<String, Object> headers) {
        return buildBasicProperties(headers, null, null, null);
    }

    /**
     * Return a {@link com.rabbitmq.client.AMQP.BasicProperties} object with the specified content type and charset, and delivery mode
     *
     * @param headers additional headers to set for this properties
     * @param contentType the content type for properties, default is {@code MessageProperties.CONTENT_TYPE_JSON}
     * @param charset the charset for properties, default is {@code StandardCharsets.UTF_8}
     * @param mode the delivery mode, default is {@code MessageDeliveryMode.PERSISTENT}
     *
     * @return a {@link com.rabbitmq.client.AMQP.BasicProperties} object with the specified content type and charset, and delivery mode
     */
    @Nonnull
    public static BasicProperties buildBasicProperties(@Nullable Map<String, Object> headers, @Nullable String contentType, @Nullable Charset charset, @Nullable MessageDeliveryMode mode) {
        return buildDelayProperties(Duration.ZERO, null, headers, contentType, charset, mode);
    }

    @Nonnull
    public static BasicProperties buildDelayProperties(@Nullable Duration delay, @Nullable Integer delayedTimes) {
        return buildDelayProperties(delay, delayedTimes, null, null, null, null);
    }

    @Nonnull
    public static BasicProperties buildDelayProperties(@Nullable Duration delay, @Nullable Integer delayedTimes, @Nullable Map<String, Object> headers) {
        return buildDelayProperties(delay, delayedTimes, headers, null, null, null);
    }

    @Nonnull
    public static BasicProperties buildDelayProperties(@Nullable Duration delay, @Nullable Integer delayedTimes, @Nullable Map<String, Object> headers, @Nullable String contentType, @Nullable Charset charset, @Nullable MessageDeliveryMode mode) {
        BasicProperties.Builder builder = new BasicProperties.Builder();
        Map<String, Object> params = new LinkedHashMap<>();
        MapPlainWraps.putAllIfKeyNotBlank(params, headers);
        DurationUtilsWraps.ifPositive(delay, element -> params.put(MessageProperties.X_DELAY, element.toMillis()));
        params.put(AmqpHeaderConst.X_DELAYED_TIMES, NumberUtilsWraps.isPositive(delayedTimes) ? delayedTimes : 0);
        MapPlainWraps.ifNotEmpty(params, builder::headers);
        builder.contentType(StringUtils.defaultIfBlank(contentType, MessageProperties.CONTENT_TYPE_JSON));
        builder.contentEncoding(CharsetPlainWraps.defaultCharsetName(charset));
        builder.deliveryMode(MessageDeliveryMode.toInt(ObjectUtils.defaultIfNull(mode, MessageDeliveryMode.PERSISTENT)));
        builder.messageId(AlternativeUuidGenerator.getBalancedUuid());
        builder.timestamp(UtilDateWraps.getCurrentDateTime());
        return builder.build();
    }

    public static AcknowledgeMode getSimpleAcknowledgeMode(@Nullable RabbitProperties properties) {
        return (properties == null) ? null : properties.getListener().getSimple().getAcknowledgeMode();
    }

    public static AcknowledgeMode getDirectAcknowledgeMode(@Nullable RabbitProperties properties) {
        return (properties == null) ? null : properties.getListener().getDirect().getAcknowledgeMode();
    }
}
