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


import java.io.Reader;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


/**
 * Utilities for {@link com.google.gson.Gson}
 *
 * @author David Hsing
 * @see com.google.gson.Gson
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class GsonJsonWraps {
    @Nullable
    public static <T> T fromJson(@Nullable Gson instance, @Nullable JsonElement json, @Nullable Class<T> classOfT) {
        if (instance == null || classOfT == null) {
            return null;
        }
        try {
            return instance.fromJson(json, classOfT);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T fromJson(@Nullable Gson instance, @Nullable String json, @Nullable Class<T> classOfT) {
        if (instance == null || classOfT == null) {
            return null;
        }
        try {
            return instance.fromJson(json, classOfT);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T fromJson(@Nullable Gson instance, @Nullable String json, @Nullable Type typeOfT) {
        if (instance == null || StringUtils.isBlank(json) || typeOfT == null) {
            return null;
        }
        try {
            return instance.fromJson(json, typeOfT);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T fromJson(@Nullable Gson instance, @Nullable JsonElement json, @Nullable Type typeOfT) {
        if (instance == null || typeOfT == null) {
            return null;
        }
        try {
            return instance.fromJson(json, typeOfT);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T fromJson(@Nullable Gson instance, @Nullable JsonReader reader, @Nullable Type typeOfT) {
        if (instance == null || reader == null || typeOfT == null) {
            return null;
        }
        try {
            return instance.fromJson(reader, typeOfT);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T fromJson(@Nullable Gson instance, @Nullable Reader json, @Nullable Type typeOfT) {
        if (instance == null || json == null || typeOfT == null) {
            return null;
        }
        try {
            return instance.fromJson(json, typeOfT);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <T> T fromJson(@Nullable Gson instance, @Nullable Reader json, @Nullable Class<T> classOfT) {
        if (instance == null || json == null || classOfT == null) {
            return null;
        }
        try {
            return instance.fromJson(json, classOfT);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static String toJson(@Nullable Gson instance, @Nullable Object src, @Nullable Type typeOfSrc) {
        if (instance == null || typeOfSrc == null) {
            return null;
        }
        try {
            return instance.toJson(src, typeOfSrc);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static void toJson(@Nullable Gson instance, @Nullable Object src, @Nullable Type typeOfSrc, @Nullable JsonWriter writer) {
        if (instance == null || typeOfSrc == null || writer == null) {
            return;
        }
        try {
            instance.toJson(src, typeOfSrc, writer);
        } catch (Exception ignored) {
        }
    }

    public static void toJson(@Nullable Gson instance, @Nullable Object src, @Nullable Type typeOfSrc, @Nullable Appendable writer) {
        if (instance == null || typeOfSrc == null) {
            return;
        }
        try {
            instance.toJson(src, typeOfSrc, writer);
        } catch (Exception ignored) {
        }
    }

    @Nullable
    public static String toJson(@Nullable Gson instance, @Nullable JsonElement element) {
        if (instance == null || element == null) {
            return null;
        }
        try {
            return instance.toJson(element);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static void toJson(@Nullable Gson instance, @Nullable JsonElement element, @Nullable Appendable writer) {
        if (instance == null || element == null || writer == null) {
            return;
        }
        try {
            instance.toJson(element, writer);
        } catch (Exception ignored) {
        }
    }

    public static void toJson(@Nullable Gson instance, @Nullable JsonElement element, @Nullable JsonWriter writer) {
        if (instance == null || writer == null) {
            return;
        }
        try {
            instance.toJson(element, writer);
        } catch (Exception ignored) {
        }
    }

    public static void toJson(@Nullable Gson instance, @Nullable Object src, @Nullable Appendable writer) {
        if (instance == null || writer == null) {
            return;
        }
        try {
            instance.toJson(src, writer);
        } catch (Exception ignored) {
        }
    }

    @Nullable
    public static String toJson(@Nullable Gson instance, @Nullable Object src) {
        if (instance == null || src == null) {
            return null;
        }
        try {
            return instance.toJson(src);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonElement toJsonTree(@Nullable Gson instance, @Nullable Object src) {
        if (instance == null || src == null) {
            return null;
        }
        try {
            return instance.toJsonTree(src);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonElement toJsonTree(@Nullable Gson instance, @Nullable Object src, @Nullable Type typeOfSrc) {
        if (instance == null || typeOfSrc == null) {
            return null;
        }
        try {
            return instance.toJsonTree(src, typeOfSrc);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonElement parseString(@Nullable String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return JsonParser.parseString(json);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonElement parseReader(@Nullable Reader reader) {
        if (reader == null) {
            return null;
        }
        try {
            return JsonParser.parseReader(reader);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static JsonElement parseReader(@Nullable JsonReader reader) {
        if (reader == null) {
            return null;
        }
        try {
            return JsonParser.parseReader(reader);
        } catch (Exception ignored) {
        }
        return null;
    }
}
