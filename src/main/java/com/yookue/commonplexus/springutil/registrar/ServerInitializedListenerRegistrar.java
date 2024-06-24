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

package com.yookue.commonplexus.springutil.registrar;


import java.lang.annotation.Annotation;
import javax.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import com.yookue.commonplexus.springutil.annotation.EnableServerInitializedListener;
import com.yookue.commonplexus.springutil.event.listener.WebServerInitializedListener;


/**
 * Registrar of a {@link org.springframework.boot.web.context.WebServerInitializedEvent} listener
 *
 * @author David Hsing
 */
public class ServerInitializedListenerRegistrar implements ImportAware {
    private final Class<? extends Annotation> annotation = EnableServerInitializedListener.class;
    private AnnotationAttributes attributes;

    @Override
    public void setImportMetadata(@Nonnull AnnotationMetadata metadata) {
        attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (attributes == null) {
            throw new IllegalArgumentException(String.format("@%s is not present on importing class: %s", annotation.getSimpleName(), metadata.getClassName()));    // $NON-NLS-1$
        }
    }

    @Bean
    @ConditionalOnMissingBean(value = WebServerInitializedEvent.class, parameterizedContainer = WebServerInitializedListener.class)
    public WebServerInitializedListener<WebServerInitializedEvent> webServerInitializedListener() {
        WebServerInitializedListener<WebServerInitializedEvent> result = new WebServerInitializedListener<>();
        result.setLogMessage(attributes.getBoolean("logMessage"));    // $NON-NLS-1$
        return result;
    }
}
