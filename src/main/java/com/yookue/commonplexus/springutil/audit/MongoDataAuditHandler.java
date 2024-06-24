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

package com.yookue.commonplexus.springutil.audit;


import javax.annotation.Nonnull;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.auditing.IsNewAwareAuditingHandler;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.context.PersistentEntities;
import com.yookue.commonplexus.springutil.general.AbstractAuditableDocument;


/**
 * {@link org.springframework.data.auditing.AuditingHandler} for mongo document
 *
 * @author David Hsing
 * @see org.springframework.data.auditing.IsNewAwareAuditingHandler
 */
@SuppressWarnings("unused")
public class MongoDataAuditHandler extends IsNewAwareAuditingHandler {
    public MongoDataAuditHandler(@Nonnull MappingContext<? extends PersistentEntity<?, ?>, ? extends PersistentProperty<?>> context) {
        this(PersistentEntities.of(context));
    }

    public MongoDataAuditHandler(@Nonnull PersistentEntities entities) {
        super(entities);
    }

    public MongoDataAuditHandler(@Nonnull PersistentEntities entities, @Nonnull AuditorAware<?> auditor) {
        super(entities);
        super.setAuditorAware(auditor);
    }

    @Nonnull
    @Override
    public <T> T markCreated(@Nonnull T source) {
        return (source instanceof AbstractAuditableDocument && BooleanUtils.isTrue(((AbstractAuditableDocument) source).getAuditCreate())) ? super.markCreated(source) : source;
    }

    @Nonnull
    @Override
    public <T> T markModified(@Nonnull T source) {
        return (source instanceof AbstractAuditableDocument && BooleanUtils.isTrue(((AbstractAuditableDocument) source).getAuditCreate())) ? super.markModified(source) : source;
    }
}
