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

package com.yookue.commonplexus.springutil.general;


import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Abstract bson document for Spring Data auditing
 *
 * @author David Hsing
 * @see org.springframework.data.auditing.AuditingHandler
 */
@MappedSuperclass
@Embeddable
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("unused")
public abstract class AbstractAuditableDocument extends AbstractBsonDocument {
    @Field(name = "CREATE_USER")
    @CreatedBy
    @JsonIgnore
    private String createUser;

    @Field(name = "CREATE_TIME")
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createTime;

    @Field(name = "MODIFY_USER")
    @LastModifiedBy
    @JsonIgnore
    private String modifyUser;

    @Field(name = "MODIFY_TIME")
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime modifyTime;

    @Transient
    @JsonIgnore
    private Boolean auditCreate = Boolean.TRUE;

    @Transient
    @JsonIgnore
    private Boolean auditModify = Boolean.TRUE;
}
