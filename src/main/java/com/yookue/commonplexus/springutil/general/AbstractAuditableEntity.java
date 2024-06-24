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


import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yookue.commonplexus.springutil.validation.sequence.InsertUpdateSequence;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Abstract jpa entity for Spring Data auditing
 *
 * @author David Hsing
 * @see org.springframework.data.auditing.AuditingHandler
 */
@MappedSuperclass
@Embeddable
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public abstract class AbstractAuditableEntity implements Serializable {
    @Column(name = "CREATE_USER", nullable = false, updatable = false, length = 40)
    @NotBlank(groups = InsertUpdateSequence.class)
    @Size(max = 40)
    @CreatedBy
    @JsonIgnore
    private String createUser;

    @Column(name = "CREATE_TIME", nullable = false, updatable = false)
    @NotNull(groups = InsertUpdateSequence.class)
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createTime;

    @Column(name = "MODIFY_USER", nullable = false, length = 40)
    @NotBlank(groups = InsertUpdateSequence.class)
    @Size(max = 40)
    @LastModifiedBy
    @JsonIgnore
    private String modifyUser;

    @Column(name = "MODIFY_TIME", nullable = false)
    @NotNull(groups = InsertUpdateSequence.class)
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
