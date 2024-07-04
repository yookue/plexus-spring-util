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

package com.yookue.commonplexus.springutil.structure;


import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.util.Collection;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.springutil.util.MultiMapWraps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Structure for mapping {@link org.springframework.web.servlet.mvc.method.RequestMappingInfo} and {@link org.springframework.web.method.HandlerMethod}
 *
 * @author David Hsing
 * @see org.springframework.web.servlet.mvc.method.RequestMappingInfo
 * @see org.springframework.web.method.HandlerMethod
 * @see org.springframework.http.ResponseEntity
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings("unused")
public class RequestMappingStruct implements Serializable {
    private RequestMappingInfo mappingInfo;
    private HandlerMethod handlerMethod;
    private MultiValueMap<Class<? extends Annotation>, ElementType> mappings = new LinkedMultiValueMap<>();

    public RequestMappingStruct(@Nullable RequestMappingInfo mappingInfo, @Nullable HandlerMethod handlerMethod) {
        this.mappingInfo = mappingInfo;
        this.handlerMethod = handlerMethod;
    }

    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public void addMapping(@Nullable Class<? extends Annotation> annotation, @Nullable ElementType element) {
        if (mappings == null) {
            mappings = new LinkedMultiValueMap<>();
        }
        if (ObjectUtils.allNotNull(annotation, element)) {
            mappings.add(annotation, element);
        }
    }

    public void addMapping(@Nullable MultiValueMap<Class<? extends Annotation>, ElementType> mapping) {
        if (mappings == null) {
            mappings = new LinkedMultiValueMap<>();
        }
        MapPlainWraps.ifNotEmpty(mapping, mappings::addAll);
    }

    public void clearMappings() {
        if (mappings != null) {
            mappings.clear();
        }
    }

    public boolean containsMapping(@Nullable Class<? extends Annotation> annotation, @Nullable ElementType element) {
        return MultiMapWraps.containsKeyValue(mappings, annotation, element);
    }

    @SuppressWarnings("unchecked")
    public boolean containsAllAnnotations(@Nullable Class<? extends Annotation>... annotations) {
        return containsAllAnnotations(ArrayUtilsWraps.asList(annotations));
    }

    public boolean containsAllAnnotations(@Nullable Collection<Class<? extends Annotation>> annotations) {
        return CollectionPlainWraps.isNotEmpty(annotations) && MapPlainWraps.containsAllKeys(mappings, annotations);
    }

    @SuppressWarnings("unchecked")
    public boolean containsAnyAnnotations(@Nullable Class<? extends Annotation>... annotations) {
        return containsAnyAnnotations(ArrayUtilsWraps.asList(annotations));
    }

    public boolean containsAnyAnnotations(@Nullable Collection<Class<? extends Annotation>> annotations) {
        return CollectionPlainWraps.isNotEmpty(annotations) && MapPlainWraps.containsAnyKeys(mappings, annotations);
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(mappings);
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
