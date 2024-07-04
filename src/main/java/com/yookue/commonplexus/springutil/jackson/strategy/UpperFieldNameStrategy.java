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
import com.fasterxml.jackson.databind.PropertyNamingStrategy;


/**
 * {@link com.fasterxml.jackson.databind.PropertyNamingStrategy} for using uppercase field names
 * <p>
 * Usage: annotates on bean type<br/>
 * <pre><code>
 *     {@literal @}JsonNaming(value = UpperFieldNameStrategy.class)
 * </code></pre>
 *
 * @author David Hsing
 * @see com.fasterxml.jackson.databind.PropertyNamingStrategy
 * @see com.fasterxml.jackson.databind.annotation.JsonNaming
 * @reference "http://stackoverflow.com/questions/18319931/custom-naming-strategy-and-jsonproperty-in-jackson"
 * @reference "http://wiki.fasterxml.com/JacksonFeaturePropertyNamingStrategy"
 */
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class UpperFieldNameStrategy extends PropertyNamingStrategy {
    public String translate(@Nonnull String propertyName) {
        return propertyName.toUpperCase();
    }
}
