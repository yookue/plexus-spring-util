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
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Abstract bson document for recording history
 *
 * @author David Hsing
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("unused")
public abstract class AbstractRecordableDocument extends AbstractBsonDocument {
    @Field(name = "RECORD_ID")
    @JsonIgnore
    private String recordId;

    @Field(name = "RECORD_TYPE")
    @JsonIgnore
    private String recordType;

    @Field(name = "RECORD_USER")
    @JsonIgnore
    private String recordUser;

    @Field(name = "RECORD_TIME")
    @JsonIgnore
    private LocalDateTime recordTime;
}
