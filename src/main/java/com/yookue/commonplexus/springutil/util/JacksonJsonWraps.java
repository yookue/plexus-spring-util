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


import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;


/**
 * Utilities for {@link com.fasterxml.jackson.databind.ObjectMapper}
 *
 * @author David Hsing
 *
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @see com.fasterxml.jackson.core.JsonGenerator
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "DataFlowIssue", "RedundantSuppression", "UnusedReturnValue"})
public abstract class JacksonJsonWraps {
    @Nullable
    public static JsonGenerator createGenerator(@Nullable ObjectMapper mapper, @Nullable OutputStream output) {
        if (ObjectUtils.anyNull(mapper, output)) {
            return null;
        }
        try {
            return mapper.createGenerator(output);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonGenerator createGenerator(@Nullable ObjectMapper mapper, @Nullable OutputStream output, @Nullable JsonEncoding encoding) {
        if (ObjectUtils.anyNull(mapper, output)) {
            return null;
        }
        try {
            return mapper.createGenerator(output, encoding);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonGenerator createGenerator(@Nullable ObjectMapper mapper, @Nullable Writer writer) {
        if (ObjectUtils.anyNull(mapper, writer)) {
            return null;
        }
        try {
            return mapper.createGenerator(writer);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonGenerator createGenerator(@Nullable ObjectMapper mapper, @Nullable File output, @Nullable JsonEncoding encoding) {
        if (ObjectUtils.anyNull(mapper, output)) {
            return null;
        }
        try {
            return mapper.createGenerator(output, encoding);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonGenerator createGenerator(@Nullable ObjectMapper mapper, @Nullable DataOutput output) {
        if (ObjectUtils.anyNull(mapper, output)) {
            return null;
        }
        try {
            return mapper.createGenerator(output);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonParser createParser(@Nullable ObjectMapper mapper, @Nullable File source) {
        if (ObjectUtils.anyNull(mapper, source)) {
            return null;
        }
        try {
            return mapper.createParser(source);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonParser createParser(@Nullable ObjectMapper mapper, @Nullable URL source) {
        if (ObjectUtils.anyNull(mapper, source)) {
            return null;
        }
        try {
            return mapper.createParser(source);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonParser createParser(@Nullable ObjectMapper mapper, @Nullable InputStream input) {
        if (ObjectUtils.anyNull(mapper, input)) {
            return null;
        }
        try {
            return mapper.createParser(input);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonParser createParser(@Nullable ObjectMapper mapper, @Nullable Reader reader) {
        if (ObjectUtils.anyNull(mapper, reader)) {
            return null;
        }
        try {
            return mapper.createParser(reader);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonParser createParser(@Nullable ObjectMapper mapper, @Nullable byte[] content) {
        if (ObjectUtils.anyNull(mapper, content)) {
            return null;
        }
        try {
            return mapper.createParser(content);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonParser createParser(@Nullable ObjectMapper mapper, @Nullable byte[] content, int offset, int length) {
        if (ObjectUtils.anyNull(mapper, content)) {
            return null;
        }
        try {
            return mapper.createParser(content, offset, length);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonParser createParser(@Nullable ObjectMapper mapper, @Nullable String content) {
        if (ObjectUtils.anyNull(mapper, content)) {
            return null;
        }
        try {
            return mapper.createParser(content);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonParser createParser(@Nullable ObjectMapper mapper, @Nullable char[] content) {
        if (ObjectUtils.anyNull(mapper, content)) {
            return null;
        }
        try {
            return mapper.createParser(content);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonParser createParser(@Nullable ObjectMapper mapper, @Nullable char[] content, int offset, int length) {
        if (ObjectUtils.anyNull(mapper, content)) {
            return null;
        }
        try {
            return mapper.createParser(content, offset, length);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonParser createParser(@Nullable ObjectMapper mapper, @Nullable DataInput input) {
        if (ObjectUtils.anyNull(mapper, input)) {
            return null;
        }
        try {
            return mapper.createParser(input);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonParser createNonBlockingByteArrayParser(@Nullable ObjectMapper mapper) {
        try {
            return mapper.createNonBlockingByteArrayParser();
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable JsonParser parser, @Nullable Class<T> valueType) {
        if (ObjectUtils.anyNull(mapper, parser, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(parser, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable JsonParser parser, @Nullable TypeReference<T> reference) {
        if (ObjectUtils.anyNull(mapper, parser, reference)) {
            return null;
        }
        try {
            return mapper.readValue(parser, reference);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable JsonParser parser, @Nullable ResolvedType valueType) {
        if (ObjectUtils.anyNull(mapper, parser, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(parser, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable JsonParser parser, @Nullable JavaType valueType) {
        if (ObjectUtils.anyNull(mapper, parser, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(parser, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T extends TreeNode> T readTree(@Nullable ObjectMapper mapper, @Nullable JsonParser parser) {
        if (ObjectUtils.anyNull(mapper, parser)) {
            return null;
        }
        try {
            return mapper.readTree(parser);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> MappingIterator<T> readValues(@Nullable ObjectMapper mapper, @Nullable JsonParser parser, @Nullable ResolvedType valueType) {
        if (ObjectUtils.anyNull(mapper, parser, valueType)) {
            return null;
        }
        try {
            return mapper.readValues(parser, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> MappingIterator<T> readValues(@Nullable ObjectMapper mapper, @Nullable JsonParser parser, @Nullable JavaType valueType) {
        if (ObjectUtils.anyNull(mapper, parser, valueType)) {
            return null;
        }
        try {
            return mapper.readValues(parser, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> MappingIterator<T> readValues(@Nullable ObjectMapper mapper, @Nullable JsonParser parser, @Nullable Class<T> valueType) {
        if (ObjectUtils.anyNull(mapper, parser, valueType)) {
            return null;
        }
        try {
            return mapper.readValues(parser, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> MappingIterator<T> readValues(@Nullable ObjectMapper mapper, @Nullable JsonParser parser, @Nullable TypeReference<T> reference) {
        if (ObjectUtils.anyNull(mapper, parser, reference)) {
            return null;
        }
        try {
            return mapper.readValues(parser, reference);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonNode readTree(@Nullable ObjectMapper mapper, @Nullable InputStream input) {
        if (ObjectUtils.anyNull(mapper, input)) {
            return null;
        }
        try {
            return mapper.readTree(input);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonNode readTree(@Nullable ObjectMapper mapper, @Nullable Reader reader) {
        if (ObjectUtils.anyNull(mapper, reader)) {
            return null;
        }
        try {
            return mapper.readTree(reader);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonNode readTree(@Nullable ObjectMapper mapper, @Nullable String content) {
        if (mapper == null || StringUtils.isBlank(content)) {
            return null;
        }
        try {
            return mapper.readTree(content);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonNode readTree(@Nullable ObjectMapper mapper, @Nullable byte[] content) {
        if (mapper == null || ArrayUtils.isEmpty(content)) {
            return null;
        }
        try {
            return mapper.readTree(content);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonNode readTree(@Nullable ObjectMapper mapper, @Nullable byte[] content, int offset, int length) {
        if (mapper == null || ArrayUtils.isEmpty(content) || length <= 0) {
            return null;
        }
        try {
            return mapper.readTree(content, offset, length);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonNode readTree(@Nullable ObjectMapper mapper, @Nullable File source) {
        if (ObjectUtils.anyNull(mapper, source)) {
            return null;
        }
        try {
            return mapper.readTree(source);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonNode readTree(@Nullable ObjectMapper mapper, @Nullable URL source) {
        if (ObjectUtils.anyNull(mapper, source)) {
            return null;
        }
        try {
            return mapper.readTree(source);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static void writeValue(@Nullable ObjectMapper mapper, @Nullable JsonGenerator generator, @Nullable Object value) {
        if (ObjectUtils.anyNull(mapper, generator)) {
            return;
        }
        try {
            mapper.writeValue(generator, value);
        } catch (Exception ignored) {
        }
    }

    public static void writeTree(@Nullable ObjectMapper mapper, @Nullable JsonGenerator generator, @Nullable TreeNode node) {
        if (ObjectUtils.anyNull(mapper, generator)) {
            return;
        }
        try {
            mapper.writeTree(generator, node);
        } catch (Exception ignored) {
        }
    }

    public static void writeTree(@Nullable ObjectMapper mapper, @Nullable JsonGenerator generator, @Nullable JsonNode node) {
        if (ObjectUtils.anyNull(mapper, generator)) {
            return;
        }
        try {
            mapper.writeTree(generator, node);
        } catch (Exception ignored) {
        }
    }

    @Nullable
    public static <T> T treeToValue(@Nullable ObjectMapper mapper, @Nullable TreeNode node, @Nullable Class<T> valueType) {
        if (ObjectUtils.anyNull(mapper, node)) {
            return null;
        }
        try {
            return mapper.treeToValue(node, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T treeToValue(@Nullable ObjectMapper mapper, @Nullable TreeNode node, @Nullable JavaType valueType) {
        if (ObjectUtils.anyNull(mapper, node)) {
            return null;
        }
        try {
            return mapper.treeToValue(node, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T extends JsonNode> T valueToTree(@Nullable ObjectMapper mapper, Object value) {
        if (mapper == null) {
            return null;
        }
        try {
            return mapper.valueToTree(value);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable File source, @Nullable Class<T> valueType) {
        if (ObjectUtils.anyNull(mapper, source, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(source, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable File source, @Nullable TypeReference<T> reference) {
        if (ObjectUtils.anyNull(mapper, source, reference)) {
            return null;
        }
        try {
            return mapper.readValue(source, reference);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable File source, @Nullable JavaType valueType) {
        if (ObjectUtils.anyNull(mapper, source, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(source, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable URL source, @Nullable Class<T> valueType) {
        if (ObjectUtils.anyNull(mapper, source, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(source, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable URL source, @Nullable TypeReference<T> reference) {
        if (ObjectUtils.anyNull(mapper, source, reference)) {
            return null;
        }
        try {
            return mapper.readValue(source, reference);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable URL source, @Nullable JavaType valueType) {
        if (ObjectUtils.anyNull(mapper, source, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(source, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable String content, @Nullable Class<T> valueType) {
        if (ObjectUtils.anyNull(mapper, valueType) || StringUtils.isBlank(content)) {
            return null;
        }
        try {
            return mapper.readValue(content, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable String content, @Nullable TypeReference<T> reference) {
        if (ObjectUtils.anyNull(mapper, content, reference)) {
            return null;
        }
        try {
            return mapper.readValue(content, reference);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable String content, @Nullable JavaType valueType) {
        if (ObjectUtils.anyNull(mapper, content, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(content, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable Reader source, @Nullable Class<T> valueType) {
        if (ObjectUtils.anyNull(mapper, source, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(source, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable Reader source, @Nullable TypeReference<T> reference) {
        if (ObjectUtils.anyNull(mapper, source, reference)) {
            return null;
        }
        try {
            return mapper.readValue(source, reference);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable Reader source, @Nullable JavaType valueType) {
        if (ObjectUtils.anyNull(mapper, source, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(source, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable InputStream input, @Nullable Class<T> valueType) {
        if (ObjectUtils.anyNull(mapper, input, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(input, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable InputStream input, @Nullable TypeReference<T> reference) {
        if (ObjectUtils.anyNull(mapper, input, reference)) {
            return null;
        }
        try {
            return mapper.readValue(input, reference);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable InputStream input, @Nullable JavaType valueType) {
        if (ObjectUtils.anyNull(mapper, input, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(input, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable byte[] source, @Nullable Class<T> valueType) {
        if (ObjectUtils.anyNull(mapper, source, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(source, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable byte[] source, int offset, int length, Class<T> valueType) {
        if (ObjectUtils.anyNull(mapper, source, valueType) || length <= 0) {
            return null;
        }
        try {
            return mapper.readValue(source, offset, length, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable byte[] source, @Nullable TypeReference<T> reference) {
        if (ObjectUtils.anyNull(mapper, source, reference)) {
            return null;
        }
        try {
            return mapper.readValue(source, reference);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable byte[] source, int offset, int length, @Nullable TypeReference<T> reference) {
        if (ObjectUtils.anyNull(mapper, source, reference) || length <= 0) {
            return null;
        }
        try {
            return mapper.readValue(source, offset, length, reference);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable byte[] source, @Nullable JavaType valueType) {
        if (ObjectUtils.anyNull(mapper, source, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(source, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable byte[] source, int offset, int length, @Nullable JavaType valueType) {
        if (ObjectUtils.anyNull(mapper, source, valueType) || length <= 0) {
            return null;
        }
        try {
            return mapper.readValue(source, offset, length, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable DataInput input, @Nullable Class<T> valueType) {
        if (ObjectUtils.anyNull(mapper, input, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(input, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T readValue(@Nullable ObjectMapper mapper, @Nullable DataInput input, @Nullable JavaType valueType) {
        if (ObjectUtils.anyNull(mapper, input, valueType)) {
            return null;
        }
        try {
            return mapper.readValue(input, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static void writeValue(@Nullable ObjectMapper mapper, @Nullable File output, @Nullable Object value) {
        if (ObjectUtils.anyNull(mapper, output)) {
            return;
        }
        try {
            mapper.writeValue(output, value);
        } catch (Exception ignored) {
        }
    }

    public static void writeValue(@Nullable ObjectMapper mapper, @Nullable OutputStream output, @Nullable Object value) {
        if (ObjectUtils.anyNull(mapper, output)) {
            return;
        }
        try {
            mapper.writeValue(output, value);
        } catch (Exception ignored) {
        }
    }

    public static void writeValue(@Nullable ObjectMapper mapper, @Nullable DataOutput output, @Nullable Object value) {
        if (ObjectUtils.anyNull(mapper, output)) {
            return;
        }
        try {
            mapper.writeValue(output, value);
        } catch (Exception ignored) {
        }
    }

    public static void writeValue(@Nullable ObjectMapper mapper, @Nullable Writer writer, @Nullable Object value) {
        if (ObjectUtils.anyNull(mapper, writer)) {
            return;
        }
        try {
            mapper.writeValue(writer, value);
        } catch (Exception ignored) {
        }
    }

    @Nullable
    public static String writeValueAsString(@Nullable ObjectMapper mapper, @Nullable Object value) {
        if (mapper == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static byte[] writeValueAsBytes(@Nullable ObjectMapper mapper, @Nullable Object value) {
        if (mapper == null) {
            return null;
        }
        try {
            return mapper.writeValueAsBytes(value);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T convertValue(@Nullable ObjectMapper mapper, @Nullable Object value, @Nullable Class<T> valueType) {
        if (mapper == null) {
            return null;
        }
        try {
            return mapper.convertValue(value, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T convertValue(@Nullable ObjectMapper mapper, @Nullable Object value, @Nullable TypeReference<T> reference) {
        if (mapper == null) {
            return null;
        }
        try {
            return mapper.convertValue(value, reference);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T convertValue(@Nullable ObjectMapper mapper, @Nullable Object value, @Nullable JavaType valueType) {
        if (mapper == null) {
            return null;
        }
        try {
            return mapper.convertValue(value, valueType);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T updateValue(@Nullable ObjectMapper mapper, @Nullable T value, @Nullable Object override) {
        if (mapper == null) {
            return null;
        }
        try {
            return mapper.updateValue(value, override);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static void acceptJsonFormatVisitor(@Nullable ObjectMapper mapper, @Nullable Class<?> type, @Nullable JsonFormatVisitorWrapper visitor) {
        if (ObjectUtils.anyNull(mapper, type)) {
            return;
        }
        try {
            mapper.acceptJsonFormatVisitor(type, visitor);
        } catch (Exception ignored) {
        }
    }

    public static void acceptJsonFormatVisitor(@Nullable ObjectMapper mapper, @Nullable JavaType type, @Nullable JsonFormatVisitorWrapper visitor) {
        if (ObjectUtils.anyNull(mapper, type)) {
            return;
        }
        try {
            mapper.acceptJsonFormatVisitor(type, visitor);
        } catch (Exception ignored) {
        }
    }

    public static Boolean getSubNodeAsBoolean(@Nullable JsonNode node, int index) {
        return getSubNodeAsBoolean(node, index, null);
    }

    public static Boolean getSubNodeAsBoolean(@Nullable JsonNode node, int index, Boolean defaultValue) {
        if (node == null || index < 0) {
            return defaultValue;
        }
        JsonNode subNode = node.get(index);
        if (subNode == null) {
            return defaultValue;
        }
        return (defaultValue == null) ? subNode.asBoolean() : subNode.asBoolean(defaultValue);
    }

    public static Boolean getSubNodeAsBoolean(@Nullable JsonNode node, String fieldName) {
        return getSubNodeAsBoolean(node, fieldName, null);
    }

    public static Boolean getSubNodeAsBoolean(@Nullable JsonNode node, String fieldName, Boolean defaultValue) {
        if (node == null || StringUtils.isBlank(fieldName)) {
            return defaultValue;
        }
        JsonNode subNode = node.get(fieldName);
        if (subNode == null) {
            return defaultValue;
        }
        return (defaultValue == null) ? subNode.asBoolean() : subNode.asBoolean(defaultValue);
    }

    public static Double getSubNodeAsDouble(@Nullable JsonNode node, int index) {
        return getSubNodeAsDouble(node, index, null);
    }

    public static Double getSubNodeAsDouble(@Nullable JsonNode node, int index, Double defaultValue) {
        if (node == null || index < 0) {
            return defaultValue;
        }
        JsonNode subNode = node.get(index);
        if (subNode == null) {
            return defaultValue;
        }
        return (defaultValue == null) ? subNode.asDouble() : subNode.asDouble(defaultValue);
    }

    public static Double getSubNodeAsDouble(@Nullable JsonNode node, String fieldName) {
        return getSubNodeAsDouble(node, fieldName, null);
    }

    public static Double getSubNodeAsDouble(@Nullable JsonNode node, String fieldName, Double defaultValue) {
        if (node == null || StringUtils.isBlank(fieldName)) {
            return defaultValue;
        }
        JsonNode subNode = node.get(fieldName);
        if (subNode == null) {
            return defaultValue;
        }
        return (defaultValue == null) ? subNode.asDouble() : subNode.asDouble(defaultValue);
    }

    public static Integer getSubNodeAsInteger(@Nullable JsonNode node, int index) {
        return getSubNodeAsInteger(node, index, null);
    }

    public static Integer getSubNodeAsInteger(@Nullable JsonNode node, int index, Integer defaultValue) {
        if (node == null || index < 0) {
            return defaultValue;
        }
        JsonNode subNode = node.get(index);
        if (subNode == null) {
            return defaultValue;
        }
        return (defaultValue == null) ? subNode.asInt() : subNode.asInt(defaultValue);
    }

    public static Integer getSubNodeAsInteger(@Nullable JsonNode node, String fieldName) {
        return getSubNodeAsInteger(node, fieldName, null);
    }

    public static Integer getSubNodeAsInteger(@Nullable JsonNode node, String fieldName, Integer defaultValue) {
        if (node == null || StringUtils.isBlank(fieldName)) {
            return defaultValue;
        }
        JsonNode subNode = node.get(fieldName);
        if (subNode == null) {
            return defaultValue;
        }
        return (defaultValue == null) ? subNode.asInt() : subNode.asInt(defaultValue);
    }

    public static Long getSubNodeAsLong(@Nullable JsonNode node, int index) {
        return getSubNodeAsLong(node, index, null);
    }

    public static Long getSubNodeAsLong(@Nullable JsonNode node, int index, Long defaultValue) {
        if (node == null || index < 0) {
            return defaultValue;
        }
        JsonNode subNode = node.get(index);
        if (subNode == null) {
            return defaultValue;
        }
        return (defaultValue == null) ? subNode.asLong() : subNode.asLong(defaultValue);
    }

    public static Long getSubNodeAsLong(@Nullable JsonNode node, String fieldName) {
        return getSubNodeAsLong(node, fieldName, null);
    }

    public static Long getSubNodeAsLong(@Nullable JsonNode node, String fieldName, Long defaultValue) {
        if (node == null || StringUtils.isBlank(fieldName)) {
            return defaultValue;
        }
        JsonNode subNode = node.get(fieldName);
        if (subNode == null) {
            return defaultValue;
        }
        return (defaultValue == null) ? subNode.asLong() : subNode.asLong(defaultValue);
    }

    public static String getSubNodeAsString(@Nullable JsonNode node, int index) {
        return getSubNodeAsString(node, index, null);
    }

    public static String getSubNodeAsString(@Nullable JsonNode node, int index, String defaultValue) {
        if (node == null || index < 0) {
            return defaultValue;
        }
        JsonNode subNode = node.get(index);
        if (subNode == null) {
            return defaultValue;
        }
        return (defaultValue == null) ? subNode.asText() : subNode.asText(defaultValue);
    }

    public static String getSubNodeAsString(@Nullable JsonNode node, String fieldName) {
        return getSubNodeAsString(node, fieldName, null);
    }

    public static String getSubNodeAsString(@Nullable JsonNode node, String fieldName, String defaultValue) {
        if (node == null || StringUtils.isBlank(fieldName)) {
            return defaultValue;
        }
        JsonNode subNode = node.get(fieldName);
        if (subNode == null) {
            return defaultValue;
        }
        return (defaultValue == null) ? subNode.asText() : subNode.asText(defaultValue);
    }
}
