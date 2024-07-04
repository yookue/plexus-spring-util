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


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.FileCopyUtils;


/**
 * Utilities for {@link org.springframework.util.FileCopyUtils}
 *
 * @author David Hsing
 * @see org.springframework.util.FileCopyUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class FileCopyWraps {
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static int copy(@Nullable File input, @Nullable File output) {
        if (ObjectUtils.anyNull(input, output)) {
            return 0;
        }
        try {
            return FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
        return 0;
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void copy(@Nullable byte[] input, @Nullable OutputStream output) {
        if (ObjectUtils.anyNull(input, output)) {
            return;
        }
        try {
            FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static int copy(@Nullable Reader input, @Nullable Writer output) {
        if (ObjectUtils.anyNull(input, output)) {
            return 0;
        }
        try {
            return FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
        return 0;
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void copy(@Nullable String input, @Nullable Writer output) {
        if (ObjectUtils.anyNull(input, output)) {
            return;
        }
        try {
            FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void copy(@Nullable byte[] input, @Nullable File output) {
        if (ObjectUtils.anyNull(input, output)) {
            return;
        }
        try {
            FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static int copy(@Nullable InputStream input, @Nullable OutputStream output) {
        if (ObjectUtils.anyNull(input, output)) {
            return 0;
        }
        try {
            return FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
        return 0;
    }

    @Nullable
    public static String copyToString(@Nullable Reader input) throws IOException {
        return (input == null) ? null : FileCopyUtils.copyToString(input);
    }

    @Nullable
    public static String copyToStringQuietly(@Nullable Reader input) {
        try {
            return copyToString(input);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static byte[] copyToByteArray(@Nullable InputStream input) throws IOException {
        return (input == null) ? null : FileCopyUtils.copyToByteArray(input);
    }

    @Nullable
    public static byte[] copyToByteArrayQuietly(@Nullable InputStream input) {
        try {
            return copyToByteArray(input);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static byte[] copyToByteArrayQuietly(@Nullable File input) {
        if (input == null) {
            return null;
        }
        try {
            return FileCopyUtils.copyToByteArray(input);
        } catch (Exception ignored) {
        }
        return null;
    }
}
