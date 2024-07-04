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


import jakarta.annotation.Nullable;
import org.springframework.util.SerializationUtils;


/**
 * Utilities for {@link org.springframework.util.SerializationUtils}
 *
 * @author David Hsing
 * @see org.springframework.util.SerializationUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class SerializationUtilsWraps {
    @Nullable
    public static byte[] serialize(@Nullable Object object) {
        try {
            return SerializationUtils.serialize(object);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static Object deserialize(@Nullable byte[] input) {
        try {
            return SerializationUtils.deserialize(input);
        } catch (Exception ignored) {
        }
        return null;
    }
}
