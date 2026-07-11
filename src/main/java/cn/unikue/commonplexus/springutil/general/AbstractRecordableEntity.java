/*
 * Copyright (c) 2016 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.general;


import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cn.unikue.commonplexus.javaseutil.validation.sequence.InsertUpdateSequence;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Abstract jpa entity for recording history
 *
 * @author David Hsing
 */
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public abstract class AbstractRecordableEntity implements Serializable {
    @Column(name = "record_id", nullable = false, updatable = false, length = 40)
    @NotBlank(groups = InsertUpdateSequence.class)
    @Size(max = 40)
    @JsonIgnore
    private String recordId;

    @Column(name = "record_type", nullable = false, updatable = false, length = 1)
    @NotBlank(groups = InsertUpdateSequence.class)
    @Size(max = 1)
    @JsonIgnore
    private String recordType;

    @Column(name = "record_user", nullable = false, updatable = false, length = 40)
    @NotBlank(groups = InsertUpdateSequence.class)
    @Size(max = 40)
    @JsonIgnore
    private String recordUser;

    @Column(name = "record_time", nullable = false, updatable = false)
    @NotNull(groups = InsertUpdateSequence.class)
    @JsonIgnore
    private LocalDateTime recordTime;
}
