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
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Abstract cache key generator by hash codes
 * <p>
 * The maximum allowed key/value size is 512 MB
 *
 * @author David Hsing
 * @see org.springframework.cache.interceptor.KeyGenerator
 * @see org.springframework.cache.interceptor.SimpleKeyGenerator
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractKeyGenerator implements KeyGenerator {
    private String prefix;
    private String suffix;
    private boolean prependClassName = false;
    private boolean useShortClassName = true;
    private boolean appendMethodHash = false;

    @Nonnull
    @Override
    public Object generate(@Nonnull Object target, @Nonnull Method method, @Nullable Object... params) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(prefix)) {
            builder.append(StringUtilsWraps.appendIfMissing(prefix, CharVariantConst.COLON));
        }
        if (prependClassName) {
            Class<?> targetClass = AopUtils.getTargetClass(target);
            String className = useShortClassName ? ClassUtils.getShortClassName(targetClass) : targetClass.getCanonicalName();
            builder.append(className).append(CharVariantConst.COLON);
        }
        builder.append(method.getName());
        if (appendMethodHash) {
            builder.append(CharVariantConst.SQUARE_BRACKET_LEFT).append(method.hashCode()).append(CharVariantConst.SQUARE_BRACKET_RIGHT);
        }
        StringUtilsWraps.ifNotBlank(resolveParams(params), builder::append);
        if (StringUtils.isNotBlank(suffix)) {
            builder.append(StringUtilsWraps.prependIfMissing(suffix, CharVariantConst.COLON));
        }
        return builder.toString();
    }

    protected abstract String resolveParams(@Nullable Object... params);
}
