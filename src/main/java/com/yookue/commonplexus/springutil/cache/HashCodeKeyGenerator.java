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
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.constant.StringVariantConst;
import com.yookue.commonplexus.javaseutil.constant.SymbolVariantConst;
import lombok.Getter;
import lombok.Setter;


/**
 * Cache key generator by hash codes
 *
 * @author David Hsing
 * @see org.springframework.cache.interceptor.KeyGenerator
 * @see org.springframework.cache.interceptor.SimpleKeyGenerator
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class HashCodeKeyGenerator extends AbstractKeyGenerator {
    private boolean paramParentheses = true;

    @Override
    protected void beforeGenerate(@Nonnull Object target, @Nonnull Method method, @Nullable Object... params) {
        Class<?> targetClass = AopUtils.getTargetClass(target);
        HashCodeKeyFormat clazzAnnotation = AnnotationUtils.findAnnotation(targetClass, HashCodeKeyFormat.class);
        if (clazzAnnotation != null) {
            processAnnotation(clazzAnnotation);
        }
        HashCodeKeyFormat methodAnnotation = AnnotationUtils.findAnnotation(method, HashCodeKeyFormat.class);
        if (methodAnnotation != null) {
            processAnnotation(methodAnnotation);
        }
    }

    @Override
    protected String resolveParams(@Nonnull Object target, @Nonnull Method method, @Nullable Object... params) {
        String result = ArrayUtils.isEmpty(params) ? StringVariantConst.NULL : String.format(SymbolVariantConst.HEX_ORDER_SQUARES, Math.abs(Arrays.deepHashCode(params)));
        return !paramParentheses ? result : StringUtils.join(CharVariantConst.PARENTHESIS_LEFT, CharVariantConst.PARENTHESIS_RIGHT, result);
    }

    private void processAnnotation(@Nonnull HashCodeKeyFormat annotation) {
        super.setPrefix(annotation.prefix());
        super.setSuffix(annotation.suffix());
        super.setClazzName(annotation.clazzName());
        super.setShortClazzName(annotation.shortClazzName());
        super.setMethodHash(annotation.methodHash());
        paramParentheses = annotation.paramParentheses();
    }
}
