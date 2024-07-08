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


import java.util.Objects;
import jakarta.annotation.Nullable;
import org.springframework.util.ObjectUtils;


/**
 * Utilities for {@link org.springframework.util.ObjectUtils}
 *
 * @author David Hsing
 * @see org.springframework.util.ObjectUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ObjectUtilsWraps {
    public static String getDisplayString(@Nullable Object source) {
        return getDisplayString(source, null);
    }

    /**
     * Return a content-based string representation
     * Differ from {@link org.springframework.util.ObjectUtils#getDisplayString}, this return null rather than an empty string
     *
     * @param source the object to build a string representation for
     *
     * @return a content-based string representation if {@code object} is not {@code null}
     *
     * @see org.springframework.util.ObjectUtils#nullSafeToString(Object)
     */
    public static String getDisplayString(@Nullable Object source, @Nullable String nullString) {
        return (source == null) ? nullString : Objects.toString(ObjectUtils.getDisplayString(source), nullString);
    }

    /**
     * Return a hex string form of an object's identity hash code
     * Differ from {@link org.springframework.util.ObjectUtils#getIdentityHexString}, this return null when {@code object} is null
     *
     * @param source the object to build a string representation for
     *
     * @return a hex string form of an object's identity hash code
     */
    @Nullable
    public static String getIdentityHexString(@Nullable Object source) {
        return (source == null) ? null : ObjectUtils.getIdentityHexString(source);
    }
}
