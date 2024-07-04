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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yookue.commonplexus.javaseutil.constant.AssertMessageConst;
import com.yookue.commonplexus.javaseutil.util.LocalDateWraps;
import lombok.Getter;


/**
 * Abstract bson document with {@link org.bson.types.ObjectId}
 *
 * @author David Hsing
 * @reference "https://www.jianshu.com/p/463d513cca6f"
 * @see org.springframework.data.mongodb.util.json.ParameterBindingDocumentCodec
 */
@Getter
@MappedSuperclass
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public abstract class AbstractBsonDocument implements Serializable {
    @Id
    @Column(name = "_id")
    @JsonIgnore
    private ObjectId objectId;

    public void setObjectId(@Nullable ObjectId id) {
        objectId = id;
    }

    public void setObjectId(@Nullable Date date) {
        objectId = (date == null) ? null : new ObjectId(date);
    }

    public void setObjectId(@Nullable LocalDate date) {
        Date utilDate = LocalDateWraps.toUtilDate(date);
        setObjectId(utilDate);
    }

    public void setObjectId(@Nullable LocalDateTime dateTime) {
        Date utilDate = LocalDateWraps.toUtilDate(dateTime);
        setObjectId(utilDate);
    }

    public void setObjectId(long milliseconds) {
        Assert.isTrue(milliseconds > 0L, AssertMessageConst.IS_POSITIVE);
        objectId = new ObjectId(new Date(milliseconds));
    }
}
