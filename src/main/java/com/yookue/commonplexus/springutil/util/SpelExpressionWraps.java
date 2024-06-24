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


import java.util.Map;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;


/**
 * Utilities for {@link org.springframework.expression.Expression}
 *
 * @author David Hsing
 * @see org.springframework.expression.spel.standard.SpelExpressionParser
 * @see org.springframework.expression.spel.support.StandardEvaluationContext
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class SpelExpressionWraps {
    @Nullable
    public static Object parseExpression(@Nullable String template, @Nullable Map<String, Object> params) throws ParseException, EvaluationException {
        return parseExpression(template, params, null);
    }

    @Nullable
    public static Object parseExpression(@Nullable String template, @Nullable Map<String, Object> params, @Nullable ParserContext context) throws ParseException, EvaluationException {
        if (StringUtils.isEmpty(template)) {
            return null;
        }
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(template, ObjectUtils.defaultIfNull(context, ParserContext.TEMPLATE_EXPRESSION));
        StandardEvaluationContext evaluation = new StandardEvaluationContext(params);
        evaluation.addPropertyAccessor(new MapAccessor());
        return expression.getValue(evaluation);
    }

    @Nullable
    public static Object parseExpressionQuietly(@Nullable String template, @Nullable Map<String, Object> params) {
        return parseExpressionQuietly(template, params, null);
    }

    @Nullable
    public static Object parseExpressionQuietly(@Nullable String template, @Nullable Map<String, Object> params, @Nullable ParserContext context) {
        try {
            return parseExpression(template, params, context);
        } catch (Exception ignored) {
        }
        return null;
    }
}
