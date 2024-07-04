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


import java.lang.reflect.Method;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.yookue.commonplexus.javaseutil.constant.JavaKeywordConst;
import com.yookue.commonplexus.javaseutil.enumeration.LineSeparatorType;


/**
 * Utilities for JavaPoet
 *
 * @author David Hsing
 * @see com.squareup.javapoet.JavaFile
 * @see "https://github.com/square/javapoet"
 * @see "https://www.baeldung.com/java-poet"
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JavapoetPlainWraps {
    public static void addLineSeparator(@Nullable CodeBlock.Builder builder, boolean skipEmpty) {
        addLineSeparator(builder, skipEmpty, 1);
    }

    public static void addLineSeparator(@Nullable CodeBlock.Builder builder, boolean skipEmpty, int count) {
        if (builder == null || (builder.isEmpty() && skipEmpty)) {
            return;
        }
        for (int i = 0; i < count; i++) {
            builder.add(LineSeparatorType.LF.getValue());
        }
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void addReturnStatement(@Nullable Method method, @Nullable MethodSpec.Builder builder, boolean skipVoid) {
        if (ObjectUtils.anyNull(method, builder)) {
            return;
        }
        if (method.getReturnType() == Void.TYPE) {
            if (!skipVoid) {
                builder.addStatement(JavaKeywordConst.RETURN);
            }
            return;
        }
        if (method.getReturnType() == Boolean.TYPE) {
            builder.addStatement("return false");    // $NON-NLS-1$
        } else if (method.getReturnType() == Byte.TYPE || method.getReturnType() == Character.TYPE || method.getReturnType() == Integer.TYPE || method.getReturnType() == Short.TYPE) {
            builder.addStatement("return 0");    // $NON-NLS-1$
        } else if (method.getReturnType() == Double.TYPE) {
            builder.addStatement("return 0.0D");    // $NON-NLS-1$
        } else if (method.getReturnType() == Float.TYPE) {
            builder.addStatement("return 0.0F");    // $NON-NLS-1$
        } else if (method.getReturnType() == Long.TYPE) {
            builder.addStatement("return 0L");    // $NON-NLS-1$
        } else {
            builder.addStatement("return null");    // $NON-NLS-1$
        }
    }
}
