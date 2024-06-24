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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import com.yookue.commonplexus.springutil.annotation.EnableSecurityContextAuditor;
import com.yookue.commonplexus.springutil.audit.SecurityContextAuditorAware;


/**
 * Registrar of a {@link com.yookue.commonplexus.springutil.audit.SecurityContextAuditorAware}
 *
 * @author David Hsing
 */
public class SecurityContextAuditorRegistrar implements ImportAware {
    public static final String AUDITOR_AWARE = "securityContextAuditorAware";    // $NON-NLS-1$
    private final Class<? extends Annotation> annotation = EnableSecurityContextAuditor.class;
    private AnnotationAttributes attributes;

    @Override
    public void setImportMetadata(@Nonnull AnnotationMetadata metadata) {
        attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (attributes == null) {
            throw new IllegalArgumentException(String.format("@%s is not present on importing class: %s", annotation.getSimpleName(), metadata.getClassName()));    // $NON-NLS-1$
        }
    }

    @Bean(name = AUDITOR_AWARE)
    @ConditionalOnMissingBean(name = AUDITOR_AWARE)
    public SecurityContextAuditorAware securityContextAuditorAware() {
        SecurityContextAuditorAware result = new SecurityContextAuditorAware();
        result.setDefaultAuditor(attributes.getString("defaultAuditor"));    // $NON-NLS-1$
        return result;
    }
}
