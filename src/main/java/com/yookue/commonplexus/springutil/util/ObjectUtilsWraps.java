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
import java.util.function.Consumer;
import java.util.function.Predicate;
import jakarta.annotation.Nullable;
import org.springframework.util.ObjectUtils;


/**
 * Utilities for {@link org.springframework.util.ObjectUtils}
 *
 * @author David Hsing
 *
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
     * @param source The object to build a string representation for
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
     * @param source The object to build a string representation for
     *
     * @return a hex string form of an object's identity hash code
     */
    @Nullable
    public static String getIdentityHexString(@Nullable Object source) {
        return (source == null) ? null : ObjectUtils.getIdentityHexString(source);
    }

    public static <T> void ifEmpty(@Nullable T source, @Nullable Consumer<? super T> action) {
        ifEmptyOrElse(source, action, null);
    }

    public static <T> void ifEmpty(@Nullable T source, @Nullable Runnable action) {
        ifEmptyOrElse(source, action, null);
    }

    public static <T> void ifEmptyOrElse(@Nullable T source, @Nullable Consumer<? super T> absentAction, @Nullable Consumer<? super T> presentAction) {
        if (ObjectUtils.isEmpty(source)) {
            if (absentAction != null) {
                absentAction.accept(source);
            }
        } else {
            if (presentAction != null) {
                presentAction.accept(source);
            }
        }
    }

    public static <T> void ifEmptyOrElse(@Nullable T source, @Nullable Runnable absentAction, @Nullable Runnable presentAction) {
        if (ObjectUtils.isEmpty(source)) {
            if (absentAction != null) {
                absentAction.run();
            }
        } else {
            if (presentAction != null) {
                presentAction.run();
            }
        }
    }

    public static void ifNull(@Nullable Object source, @Nullable Runnable action) {
        if (source == null && action != null) {
            action.run();
        }
    }

    public static <T> void ifNullOrElse(@Nullable T source, @Nullable Runnable absentAction, @Nullable Consumer<? super T> presentAction) {
        if (source == null) {
            if (absentAction != null) {
                absentAction.run();
            }
        } else {
            if (presentAction != null) {
                presentAction.accept(source);
            }
        }
    }

    public static <T> void ifNullOrElse(@Nullable T source, @Nullable Runnable absentAction, @Nullable Runnable presentAction) {
        if (source == null) {
            if (absentAction != null) {
                absentAction.run();
            }
        } else {
            if (presentAction != null) {
                presentAction.run();
            }
        }
    }

    public static <T> void ifNotEmpty(@Nullable T source, @Nullable Consumer<? super T> action) {
        ifNotEmpty(source, action, null);
    }

    public static <T> void ifNotEmpty(@Nullable T source, @Nullable Consumer<? super T> action, @Nullable Predicate<? super T> filter) {
        if (!ObjectUtils.isEmpty(source) && action != null && (filter == null || filter.test(source))) {
            action.accept(source);
        }
    }

    public static <T> void ifNotEmpty(@Nullable T source, @Nullable Runnable action) {
        if (!ObjectUtils.isEmpty(source) && action != null) {
            action.run();
        }
    }

    public static <T> void ifNotNull(@Nullable T source, @Nullable Consumer<? super T> action) {
        ifNotNull(source, action, null);
    }

    public static <T> void ifNotNull(@Nullable T source, @Nullable Consumer<? super T> action, @Nullable Predicate<? super T> filter) {
        if (source != null && action != null && (filter == null || filter.test(source))) {
            action.accept(source);
        }
    }

    public static <T> void ifNotNull(@Nullable T source, @Nullable Runnable action) {
        if (source != null && action != null) {
            action.run();
        }
    }
}
