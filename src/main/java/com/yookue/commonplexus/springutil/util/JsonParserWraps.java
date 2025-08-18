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


import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.ClassUtils;
import com.yookue.commonplexus.javaseutil.util.FieldUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;
import com.yookue.commonplexus.springutil.enumeration.JsonParserType;


/**
 * Utilities for json parsers
 *
 * @author David Hsing
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JsonParserWraps {
    /**
     * Returns the first json parser type that exists in the classpath
     *
     * @return the first json parser type that exists in the classpath
     */
    @Nullable
    public static JsonParserType detectParserType() {
        return detectParserType(null);
    }

    /**
     * Returns the first json parser type that exists in the classpath
     *
     * @param loader The classloader, may be null
     *
     * @return the first json parser type that exists in the classpath
     */
    @Nullable
    public static JsonParserType detectParserType(@Nullable ClassLoader loader) {
        return Arrays.stream(JsonParserType.class.getEnumConstants()).filter(type -> ClassUtils.isPresent(type.getValue(), loader)).findFirst().orElse(null);
    }

    /**
     * Returns the found json node that matches the given {@code field} in the {@code content}, with the json {@code parser} instance
     *
     * @param content The total content of json string
     * @param field The name of the json node
     * @param parser The json parser instance, maybe {@link com.fasterxml.jackson.databind.ObjectMapper}, {@link com.google.gson.Gson} or {@link jakarta.json.JsonReader}
     *
     * @return the found json node that matches the given {@code field} in the {@code content}, with the json {@code parser} instance
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Object findNodeValue(@Nullable String content, @Nullable String field, @Nullable Object parser) {
        if (StringUtils.isAnyBlank(content, field) || parser == null) {
            return null;
        }
        if (ClassUtilsWraps.isAssignableValue(JsonParserType.JACKSON.getValue(), parser)) {
            com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) toJsonTree(content, parser);
            return (node == null || node.isNull()) ? null : node.findValue(field);
        } else if (ClassUtilsWraps.isAssignableValue(JsonParserType.GSON.getValue(), parser)) {
            com.google.gson.JsonElement node = (com.google.gson.JsonElement) toJsonTree(content, parser);
            return (node == null || node.isJsonNull()) ? null : node.getAsJsonObject().getAsJsonObject(field);
        } else if (ClassUtilsWraps.isAssignableValue(JsonParserType.JAKARTA.getValue(), parser)) {
            try (jakarta.json.JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(content))) {
                return jsonReader.read().getValue(field);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * Returns the found json node text that matches the given {@code field} in the {@code content}, under the bean factory
     *
     * @param content The total content of json string
     * @param field The field name of the json node
     * @param factory The bean factory instance that contains json parser, which maybe {@link com.fasterxml.jackson.databind.ObjectMapper} or {@link com.google.gson.Gson}
     *
     * @return the found json node text that matches the given {@code field} in the {@code content}, under the bean factory
     */
    @Nullable
    public static String findNodeValueAsString(@Nullable String content, @Nullable String field, @Nullable BeanFactory factory) {
        return (factory == null) ? null : findNodeValueAsString(content, field, factory, detectParserType(factory.getClass().getClassLoader()));
    }

    /**
     * Returns the found json node text that matches the given {@code field} in the {@code content}, under the bean factory, with the specified json parser type
     *
     * @param content The total content of json string
     * @param field The name of the json node
     * @param factory The bean factory instance that contains json parser
     * @param type The enumeration type of json parser to use, which maybe {@link com.fasterxml.jackson.databind.ObjectMapper} or {@link com.google.gson.Gson}
     *
     * @return the found json node text that matches the given {@code field} in the {@code content}, under the bean factory, with the specified json parser type
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static String findNodeValueAsString(@Nullable String content, @Nullable String field, @Nullable BeanFactory factory, @Nullable JsonParserType type) {
        if (StringUtils.isAnyBlank(content, field) || factory == null || !isParserPresent(type)) {
            return null;
        }
        if (type == JsonParserType.JACKSON) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = BeanFactoryWraps.firstBeanOfType(factory, com.fasterxml.jackson.databind.ObjectMapper.class);
            com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) findNodeValue(content, field, mapper);
            return (node == null || node.isNull()) ? null : node.asText();
        } else if (type == JsonParserType.GSON) {
            com.google.gson.Gson gson = BeanFactoryWraps.firstBeanOfType(factory, com.google.gson.Gson.class);
            com.google.gson.JsonElement node = (com.google.gson.JsonElement) findNodeValue(content, field, gson);
            return (node == null || node.isJsonNull()) ? null : node.getAsString();
        } else if (type == JsonParserType.JAKARTA) {
            try (jakarta.json.JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(content))) {
                return (jsonReader.read() instanceof jakarta.json.JsonObject alias) ? alias.getString(field) : null;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * Returns the found json node text that matches the given {@code field} in the {@code content}, with the specified json parser type
     *
     * @param content The total content of json string
     * @param field The name of the json node
     * @param type The enumeration type of json parser to use
     *
     * @return the found json node text that matches the given {@code field} in the {@code content}, with the specified json parser type
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static String findNodeValueAsString(@Nullable String content, @Nullable String field, @Nullable JsonParserType type) {
        if (StringUtils.isAnyBlank(content, field) || !isParserPresent(type)) {
            return null;
        }
        if (type == JsonParserType.JACKSON) {
            com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) findNodeValue(content, field, new com.fasterxml.jackson.databind.ObjectMapper());
            return (node == null || node.isNull()) ? null : node.asText();
        } else if (type == JsonParserType.GSON) {
            com.google.gson.JsonElement node = (com.google.gson.JsonElement) findNodeValue(content, field, new com.google.gson.Gson());
            return (node == null || node.isJsonNull()) ? null : node.getAsString();
        } else if (type == JsonParserType.JAKARTA) {
            try (jakarta.json.JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(content))) {
                return (jsonReader.read() instanceof jakarta.json.JsonObject alias) ? alias.getString(field) : null;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * Returns the found json node text that matches the given {@code field} in the {@code content}, with the json {@code parser} instance
     *
     * @param content The total content of json string
     * @param field The name of the json node
     * @param parser The json parser instance, maybe {@link com.fasterxml.jackson.databind.ObjectMapper} or {@link com.google.gson.Gson}
     *
     * @return the found json node text that matches the given {@code field} in the {@code content}, with the json {@code parser} instance
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static String findNodeValueAsString(@Nullable String content, @Nullable String field, @Nullable Object parser) {
        if (StringUtils.isAnyBlank(content, field) || parser == null) {
            return null;
        }
        if (ClassUtilsWraps.isAssignableValue(JsonParserType.JACKSON.getValue(), parser)) {
            com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) findNodeValue(content, field, parser);
            if (node == null || node.isNull()) {
                return null;
            }
            com.fasterxml.jackson.databind.JsonNode found = node.findValue(field);
            return (found == null || found.isNull()) ? null : found.asText();
        } else if (ClassUtilsWraps.isAssignableValue(JsonParserType.GSON.getValue(), parser)) {
            com.google.gson.JsonElement node = (com.google.gson.JsonElement) findNodeValue(content, field, parser);
            if (node == null || node.isJsonNull()) {
                return null;
            }
            com.google.gson.JsonObject found = node.getAsJsonObject().getAsJsonObject(field);
            return (found == null || found.isJsonNull()) ? null : found.getAsString();
        } else if (ClassUtilsWraps.isAssignableValue(JsonParserType.JAKARTA.getValue(), parser)) {
            try (jakarta.json.JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(content))) {
                return (jsonReader.read() instanceof jakarta.json.JsonObject alias) ? alias.getString(field) : null;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * Returns the found json node that matches the given {@code field} in the {@code content}, with the json {@code parser} instance
     *
     * @param content The total content of json string
     * @param field The name of the json node
     * @param parser The json parser instance, maybe {@link com.fasterxml.jackson.databind.ObjectMapper} or {@link com.google.gson.Gson}
     *
     * @return the found json node that matches the given {@code field} in the {@code content}, with the json {@code parser} instance
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static List<?> findNodeValues(@Nullable String content, @Nullable String field, @Nullable Object parser) {
        if (StringUtils.isAnyBlank(content, field) || parser == null) {
            return null;
        }
        if (ClassUtilsWraps.isAssignableValue(JsonParserType.JACKSON.getValue(), parser)) {
            com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) toJsonTree(content, parser);
            return (node == null || node.isNull()) ? null : node.findValues(field);
        } else if (ClassUtilsWraps.isAssignableValue(JsonParserType.GSON.getValue(), parser)) {
            com.google.gson.JsonElement node = (com.google.gson.JsonElement) toJsonTree(content, parser);
            if (node == null || node.isJsonNull()) {
                return null;
            }
            com.google.gson.JsonArray array = node.getAsJsonObject().getAsJsonArray(field);
            if (array == null || array.isEmpty()) {
                return null;
            }
            return FieldUtilsWraps.readDeclaredFieldAs(array, "elements", true, List.class);    // $NON-NLS-1$
        } else if (ClassUtilsWraps.isAssignableValue(JsonParserType.JAKARTA.getValue(), parser)) {
            try (jakarta.json.JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(content))) {
                jakarta.json.JsonValue value = jsonReader.read().getValue(field);
                return (value instanceof jakarta.json.JsonArray alias) ? alias.getValuesAs(jakarta.json.JsonValue.class) : null;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * Returns the found json node texts that matches the given {@code field} in the {@code content}, under the bean factory
     *
     * @param content The total content of json string
     * @param field The field name of the json node
     * @param factory The bean factory instance that contains json parser, which maybe {@link com.fasterxml.jackson.databind.ObjectMapper} or {@link com.google.gson.Gson}
     *
     * @return the found json node texts that matches the given {@code field} in the {@code content}, under the bean factory
     */
    @Nullable
    public static List<String> findNodeValuesAsString(@Nullable String content, @Nullable String field, @Nullable BeanFactory factory) {
        return (factory == null) ? null : findNodeValuesAsString(content, field, factory, detectParserType(factory.getClass().getClassLoader()));
    }

    /**
     * Returns the found json node texts that matches the given {@code field} in the {@code content}, under the bean factory, with the specified json parser type
     *
     * @param content The total content of json string
     * @param field The name of the json node
     * @param factory The bean factory instance that contains json parser
     * @param type The enumeration type of json parser to use, which maybe {@link com.fasterxml.jackson.databind.ObjectMapper} or {@link com.google.gson.Gson}
     *
     * @return the found json node texts that matches the given {@code field} in the {@code content}, under the bean factory, with the specified json parser type
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "DuplicatedCode"})
    public static List<String> findNodeValuesAsString(@Nullable String content, @Nullable String field, @Nullable BeanFactory factory, @Nullable JsonParserType type) {
        if (StringUtils.isAnyBlank(content, field) || factory == null || !isParserPresent(type)) {
            return null;
        }
        if (type == JsonParserType.JACKSON) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = BeanFactoryWraps.firstBeanOfType(factory, com.fasterxml.jackson.databind.ObjectMapper.class);
            return findNodeValuesAsString(content, field, mapper);
        } else if (type == JsonParserType.GSON) {
            com.google.gson.Gson gson = BeanFactoryWraps.firstBeanOfType(factory, com.google.gson.Gson.class);
            return findNodeValuesAsString(content, field, gson);
        } else if (type == JsonParserType.JAKARTA) {
            try (jakarta.json.JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(content))) {
                jakarta.json.JsonValue value = jsonReader.read().getValue(field);
                return (value instanceof jakarta.json.JsonArray alias) ? alias.getValuesAs(jakarta.json.JsonString::getString) : null;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * Returns the found json node texts that matches the given {@code field} in the {@code content}, with the specified json parser type
     *
     * @param content The total content of json string
     * @param field The name of the json node
     * @param type The enumeration type of json parser to use
     *
     * @return the found json node texts that matches the given {@code field} in the {@code content}, with the specified json parser type
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "DuplicatedCode"})
    public static List<String> findNodeValuesAsString(@Nullable String content, @Nullable String field, @Nullable JsonParserType type) {
        if (StringUtils.isAnyBlank(content, field) || !isParserPresent(type)) {
            return null;
        }
        if (type == JsonParserType.JACKSON) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return findNodeValuesAsString(content, field, mapper);
        } else if (type == JsonParserType.GSON) {
            com.google.gson.Gson gson = new com.google.gson.Gson();
            return findNodeValuesAsString(content, field, gson);
        } else if (type == JsonParserType.JAKARTA) {
            try (jakarta.json.JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(content))) {
                jakarta.json.JsonValue value = jsonReader.read().getValue(field);
                return (value instanceof jakarta.json.JsonArray alias) ? alias.getValuesAs(jakarta.json.JsonString::getString) : null;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * Returns the string representation of the found json node that matches the given {@code field} in the {@code content}, with the json {@code parser} instance
     *
     * @param content The total content of json string
     * @param field The name of the json node
     * @param parser The json parser instance, maybe {@link com.fasterxml.jackson.databind.ObjectMapper} or {@link com.google.gson.Gson}
     *
     * @return the string representation of the found json node that matches the given {@code field} in the {@code content}, with the json {@code parser} instance
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "DuplicatedCode"})
    public static List<String> findNodeValuesAsString(@Nullable String content, @Nullable String field, @Nullable Object parser) {
        if (StringUtils.isAnyBlank(content, field) || parser == null) {
            return null;
        }
        if (ClassUtilsWraps.isAssignableValue(JsonParserType.JACKSON.getValue(), parser)) {
            com.fasterxml.jackson.databind.JsonNode node = (com.fasterxml.jackson.databind.JsonNode) toJsonTree(content, parser);
            return (node == null || node.isNull()) ? null : node.findValuesAsText(field);
        } else if (ClassUtilsWraps.isAssignableValue(JsonParserType.GSON.getValue(), parser)) {
            com.google.gson.JsonElement node = (com.google.gson.JsonElement) toJsonTree(content, parser);
            if (node == null || node.isJsonNull()) {
                return null;
            }
            com.google.gson.JsonArray array = node.getAsJsonObject().getAsJsonArray(field);
            List<String> result = new ArrayList<>(array.size());
            for (com.google.gson.JsonElement element : array) {
                if (element != null) {
                    result.add(element.getAsString());
                }
            }
            return result.isEmpty() ? null : result;
        } else if (ClassUtilsWraps.isAssignableValue(JsonParserType.JAKARTA.getValue(), parser)) {
            try (jakarta.json.JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(content))) {
                jakarta.json.JsonValue value = jsonReader.read().getValue(field);
                return (value instanceof jakarta.json.JsonArray alias) ? alias.getValuesAs(jakarta.json.JsonString::getString) : null;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public static boolean isParserPresent(@Nullable JsonParserType type) {
        return isParserPresent(type, null);
    }

    public static boolean isParserPresent(@Nullable JsonParserType type, @Nullable ClassLoader loader) {
        return type != null && ClassUtils.isPresent(type.getValue(), loader);
    }

    public static Map<String, Object> parseJsonToMap(@Nullable String content) {
        return parseJsonToMap(content, detectParserType());
    }

    /**
     * Returns a map of direct child that contains field names and field values
     *
     * @param content The text to parse
     * @param type The json parser type, may be null, auto determined if this is null
     *
     * @return a map of direct child that contains field names and field values
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseJsonToMap(@Nullable String content, @Nullable JsonParserType type) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        if (type == null) {
            type = detectParserType();
            if (type == null) {
                return null;
            }
        }
        if (type == JsonParserType.JACKSON) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return JacksonJsonWraps.readValue(mapper, content, new com.fasterxml.jackson.core.type.TypeReference<>(){});
        } else if (type == JsonParserType.GSON) {
            com.google.gson.Gson gson = new com.google.gson.Gson();
            return GsonJsonWraps.fromJson(gson, content, new com.google.gson.reflect.TypeToken<Map<String, Object>>(){}.getType());
        } else if (type == JsonParserType.JAKARTA) {
            try (jakarta.json.bind.Jsonb jsonb = jakarta.json.bind.JsonbBuilder.create()) {
                return jsonb.fromJson(content, Map.class);
            } catch (Exception ignored) {
            }
            return null;
        }
        return null;
    }

    @Nullable
    public static String toJsonString(@Nullable Object value, @Nullable BeanFactory factory) {
        return ObjectUtils.anyNull(value, factory) ? null : toJsonString(value, factory, detectParserType(factory.getClass().getClassLoader()));
    }

    @Nullable
    public static String toJsonString(@Nullable Object value, @Nullable BeanFactory factory, @Nullable JsonParserType type) {
        if (ObjectUtils.anyNull(value, factory, type) || !isParserPresent(type)) {
            return null;
        }
        if (type == JsonParserType.JACKSON) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = BeanFactoryWraps.firstBeanOfType(factory, com.fasterxml.jackson.databind.ObjectMapper.class);
            return JacksonJsonWraps.writeValueAsString(mapper, value);
        } else if (type == JsonParserType.GSON) {
            com.google.gson.Gson gson = BeanFactoryWraps.firstBeanOfType(factory, com.google.gson.Gson.class);
            return GsonJsonWraps.toJson(gson, value);
        } else if (type == JsonParserType.JAKARTA) {
            jakarta.json.bind.JsonbConfig config = BeanFactoryWraps.firstBeanOfType(factory, jakarta.json.bind.JsonbConfig.class);
            try (jakarta.json.bind.Jsonb jsonb = jakarta.json.bind.JsonbBuilder.create(config)) {
                return jsonb.toJson(value);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    @Nullable
    public static String toJsonString(@Nullable Object value) {
        return toJsonString(value, detectParserType());
    }

    @Nullable
    public static String toJsonString(@Nullable Object value, @Nullable JsonParserType type) {
        if (value == null || !isParserPresent(type)) {
            return null;
        }
        if (type == JsonParserType.JACKSON) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return JacksonJsonWraps.writeValueAsString(mapper, value);
        } else if (type == JsonParserType.GSON) {
            com.google.gson.Gson gson = new com.google.gson.Gson();
            return GsonJsonWraps.toJson(gson, value);
        } else if (type == JsonParserType.JAKARTA) {
            try (jakarta.json.bind.Jsonb jsonb = jakarta.json.bind.JsonbBuilder.create()) {
                return jsonb.toJson(value);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    @Nullable
    public static Object toJsonTree(@Nullable String content, @Nullable BeanFactory factory) {
        return ObjectUtils.anyNull(content, factory) ? null : toJsonTree(content, factory, detectParserType(factory.getClass().getClassLoader()));
    }

    /**
     * Returns the json tree that matches the given {@code content}, with the specified json parser type
     *
     * @param content The string representation of json
     * @param type The enumeration type of json parser to use
     *
     * @return the found json node that matches the given {@code content}, with the specified json parser type
     */
    @Nullable
    public static Object toJsonTree(@Nullable String content, @Nullable BeanFactory factory, @Nullable JsonParserType type) {
        if (StringUtils.isBlank(content) || type == null) {
            return null;
        }
        if (type == JsonParserType.JACKSON) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = BeanFactoryWraps.firstBeanOfType(factory, com.fasterxml.jackson.databind.ObjectMapper.class);
            return toJsonTree(content, mapper);
        } else if (type == JsonParserType.GSON) {
            com.google.gson.Gson gson = BeanFactoryWraps.firstBeanOfType(factory, com.google.gson.Gson.class);
            return toJsonTree(content, gson);
        } else if (type == JsonParserType.JAKARTA) {
            try (jakarta.json.JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(content))) {
                return jsonReader.read();
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    @Nullable
    public static Object toJsonTree(@Nullable String content) {
        return toJsonTree(content, detectParserType());
    }

    /**
     * Returns the json tree that matches the given {@code content}, with the specified json parser type
     *
     * @param content The string representation of json
     * @param type The enumeration type of json parser to use
     *
     * @return the found json node that matches the given {@code content}, with the specified json parser type
     */
    @Nullable
    public static Object toJsonTree(@Nullable String content, @Nullable JsonParserType type) {
        if (StringUtils.isBlank(content) || !isParserPresent(type)) {
            return null;
        }
        if (type == JsonParserType.JACKSON) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return toJsonTree(content, mapper);
        } else if (type == JsonParserType.GSON) {
            com.google.gson.Gson gson = new com.google.gson.Gson();
            return toJsonTree(content, gson);
        } else if (type == JsonParserType.JAKARTA) {
            try (jakarta.json.JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(content))) {
                return jsonReader.read();
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    /**
     * Returns the json tree that matches the given {@code content}, with the json {@code parser} instance
     *
     * @param content The string representation of json
     * @param parser The json parser instance, should be in the {@link com.yookue.commonplexus.springutil.enumeration.JsonParserType}
     *
     * @return the found json node that matches the given {@code content}, with the json {@code parser} instance
     */
    @Nullable
    public static Object toJsonTree(@Nullable String content, @Nullable Object parser) {
        if (StringUtils.isBlank(content) || parser == null) {
            return null;
        }
        if (ClassUtilsWraps.isAssignableValue(JsonParserType.JACKSON.getValue(), parser)) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = ObjectUtilsWraps.castAs(parser, com.fasterxml.jackson.databind.ObjectMapper.class);
            return JacksonJsonWraps.readTree(mapper, content);
        } else if (ClassUtilsWraps.isAssignableValue(JsonParserType.GSON.getValue(), parser)) {
            com.google.gson.Gson gson = ObjectUtilsWraps.castAs(parser, com.google.gson.Gson.class);
            return GsonJsonWraps.toJsonTree(gson, content);
        } else if (ClassUtilsWraps.isAssignableValue(JsonParserType.JAKARTA.getValue(), parser)) {
            try (jakarta.json.JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(content))) {
                return jsonReader.read();
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public static void traverseNode(@Nullable com.fasterxml.jackson.databind.JsonNode node, @Nullable Consumer<com.fasterxml.jackson.databind.JsonNode> action) {
        if (ObjectUtils.anyNull(node, action)) {
            return;
        }
        action.accept(node);
        if (node.isContainerNode()) {
            for (com.fasterxml.jackson.databind.JsonNode child : node) {
                traverseNode(child, action);
            }
        }
    }

    public static void traverseNode(@Nullable com.google.gson.JsonElement node, @Nullable Consumer<com.google.gson.JsonElement> action) {
        if (ObjectUtils.anyNull(node, action)) {
            return;
        }
        action.accept(node);
        if (node.isJsonArray()) {
            for (com.google.gson.JsonElement child : node.getAsJsonArray()) {
                traverseNode(child, action);
            }
        } else if (node.isJsonObject()) {
            for (Map.Entry<String, com.google.gson.JsonElement> entry : node.getAsJsonObject().entrySet()) {
                traverseNode(entry.getValue(), action);
            }
        }
    }

    public static void traverseNode(@Nullable jakarta.json.JsonValue node, @Nullable Consumer<jakarta.json.JsonValue> action) {
        if (ObjectUtils.anyNull(node, action)) {
            return;
        }
        action.accept(node);
        if (node.getValueType() == jakarta.json.JsonValue.ValueType.ARRAY) {
            for (jakarta.json.JsonValue child : node.asJsonArray()) {
                traverseNode(child, action);
            }
        } else if (node.getValueType() == jakarta.json.JsonValue.ValueType.OBJECT) {
            for (Map.Entry<String, jakarta.json.JsonValue> entry : node.asJsonObject().entrySet()) {
                traverseNode(entry.getValue(), action);
            }
        }
    }
}
