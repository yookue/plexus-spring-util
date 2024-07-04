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

package com.yookue.commonplexus.springutil.jackson.strategy;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;


/**
 * {@link com.fasterxml.jackson.databind.PropertyNamingStrategy} for using original field names
 * <p>
 * Usage: annotates on bean type<br/>
 * <pre><code>
 *     {@literal @}JsonNaming(value = OriginFieldNameStrategy.class)
 * </code></pre>
 *
 * @author David Hsing
 * @reference "http://stackoverflow.com/questions/18319931/custom-naming-strategy-and-jsonproperty-in-jackson"
 * @reference "http://wiki.fasterxml.com/JacksonFeaturePropertyNamingStrategy"
 * @see com.fasterxml.jackson.databind.PropertyNamingStrategy
 * @see com.fasterxml.jackson.databind.annotation.JsonNaming
 */
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class OriginFieldNameStrategy extends PropertyNamingStrategy {
    @Override
    public String nameForField(@Nullable MapperConfig<?> config, @Nonnull AnnotatedField field, @Nonnull String defaultName) {
        return field.getName();
    }

    @Override
    public String nameForGetterMethod(@Nullable MapperConfig<?> config, @Nonnull AnnotatedMethod method, @Nonnull String defaultName) {
        return method.getName();
    }

    @Override
    public String nameForSetterMethod(@Nullable MapperConfig<?> config, @Nonnull AnnotatedMethod method, @Nonnull String defaultName) {
        return method.getName();
    }
}
