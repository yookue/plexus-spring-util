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

package com.yookue.commonplexus.springutil.cache;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.StringJoiner;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.constant.StringVariantConst;
import com.yookue.commonplexus.javaseutil.constant.SymbolVariantConst;
import com.yookue.commonplexus.javaseutil.constant.TemporalFormatConst;
import com.yookue.commonplexus.javaseutil.util.LocalDateWraps;
import com.yookue.commonplexus.javaseutil.util.UtilDateWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * Cache key generator by pain params
 *
 * @author David Hsing
 * @see org.springframework.cache.interceptor.KeyGenerator
 * @see org.springframework.cache.interceptor.SimpleKeyGenerator
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class PlainParamKeyGenerator extends AbstractKeyGenerator {
    public static final int DEFAULT_KEY_LENGTH = 512;

    private int maxKeyLength = DEFAULT_KEY_LENGTH;
    private boolean paramParentheses = true;
    private boolean paramHash = true;

    @Override
    protected void beforeGenerate(@Nonnull Object target, @Nonnull Method method, @Nullable Object... params) {
        Class<?> targetClass = AopUtils.getTargetClass(target);
        PlainParamKeyFormat clazzAnnotation = AnnotationUtils.findAnnotation(targetClass, PlainParamKeyFormat.class);
        if (clazzAnnotation != null) {
            processAnnotation(clazzAnnotation);
        }
        PlainParamKeyFormat methodAnnotation = AnnotationUtils.findAnnotation(method, PlainParamKeyFormat.class);
        if (methodAnnotation != null) {
            processAnnotation(methodAnnotation);
        }
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    protected String resolveParams(@Nonnull Object target, @Nonnull Method method, @Nullable Object... params) {
        // Validate param length
        int minParamLength = paramParentheses ? 5 : 3;
        Assert.isTrue(maxKeyLength > minParamLength, "Prop 'maxParamLength' must be greater than " + minParamLength);
        // Generate cache key
        StringJoiner joiner = new StringJoiner(CharUtils.toString(CharVariantConst.COMMA));
        if (ArrayUtils.isEmpty(params)) {
            joiner.add(StringVariantConst.NULL);
        } else {
            for (Object param : params) {
                if (param == null) {
                    joiner.add(StringVariantConst.NULL);
                    continue;
                }
                if (param instanceof Boolean alias) {
                    joiner.add(BooleanUtils.toStringTrueFalse(alias));
                    continue;
                }
                if (param instanceof Character alias) {
                    joiner.add(CharUtils.toString(alias));
                    continue;
                }
                if (param instanceof CharSequence alias) {
                    joiner.add(alias.toString());
                    continue;
                }
                if (param instanceof java.util.Date alias) {
                    joiner.add(UtilDateWraps.formatDateTime(alias, TemporalFormatConst.NON_YYYYMMDD));
                    continue;
                }
                if (param instanceof java.time.LocalDate alias) {
                    joiner.add(LocalDateWraps.formatDate(alias, TemporalFormatConst.NON_YYYYMMDD));
                    continue;
                }
                if (param instanceof java.time.LocalDateTime alias) {
                    joiner.add(LocalDateWraps.formatDateTime(alias, TemporalFormatConst.NON_YYYYMMDD_HHMMSS));
                    continue;
                }
                if (param instanceof java.time.LocalTime alias) {
                    joiner.add(LocalDateWraps.formatTime(alias, TemporalFormatConst.NON_HHMMSS));
                    continue;
                }
                joiner.add(ObjectUtils.getDisplayString(param));
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append(StringUtils.abbreviate(joiner.toString(), maxKeyLength - minParamLength));
        String content = !paramParentheses ? builder.toString() : StringUtils.join(CharVariantConst.PARENTHESIS_LEFT, builder.toString(), CharVariantConst.PARENTHESIS_RIGHT);
        if (!paramHash) {
            return content;
        }
        return StringUtils.join(content, String.format(SymbolVariantConst.HEX_ORDER_SQUARES, Math.abs(Arrays.deepHashCode(params))));
    }

    private void processAnnotation(@Nonnull PlainParamKeyFormat annotation) {
        super.setPrefix(annotation.prefix());
        super.setSuffix(annotation.suffix());
        super.setClazzName(annotation.clazzName());
        super.setShortClazzName(annotation.shortClazzName());
        super.setMethodHash(annotation.methodHash());
        maxKeyLength = annotation.maxKeyLength();
        paramParentheses = annotation.paramParentheses();
        paramHash = annotation.paramHash();
    }
}
