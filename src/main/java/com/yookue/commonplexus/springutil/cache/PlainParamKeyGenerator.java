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


import java.util.StringJoiner;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.constant.JavaKeywordConst;
import com.yookue.commonplexus.javaseutil.constant.TemporalFormatConst;
import com.yookue.commonplexus.javaseutil.util.LocalDateWraps;
import com.yookue.commonplexus.javaseutil.util.RegexUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.UtilDateWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * Cache key generator by pain params
 *
 * @author David Hsing
 * @see org.springframework.cache.interceptor.SimpleKeyGenerator
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class PlainParamKeyGenerator extends AbstractKeyGenerator {
    private int maxParamLength = 64;
    private boolean wrapWithParentheses = false;

    @Override
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    protected String resolveParams(@Nullable Object... params) {
        if (ArrayUtils.isEmpty(params)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        if (wrapWithParentheses) {
            builder.append(CharVariantConst.PARENTHESIS_LEFT);
        }
        StringJoiner joiner = new StringJoiner(CharUtils.toString(CharVariantConst.COMMA));
        for (Object param : params) {
            if (param == null) {
                joiner.add(JavaKeywordConst.NULL);
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
                String sequence = alias.toString();
                String reserves = RegexUtilsWraps.reserveAlphanumeric(sequence);
                if (StringUtils.isNotBlank(reserves) && maxParamLength > 0) {
                    reserves = StringUtils.left(reserves, maxParamLength);
                }
                joiner.add(StringUtils.join(reserves, CharVariantConst.TILDE, sequence.hashCode()));
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
        builder.append(joiner);
        if (wrapWithParentheses) {
            builder.append(CharVariantConst.PARENTHESIS_RIGHT);
        }
        return builder.toString();
    }
}
