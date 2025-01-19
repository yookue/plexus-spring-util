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
import java.time.LocalDateTime;
import java.util.Collection;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import com.yookue.commonplexus.javaseutil.constant.TemporalFormatConst;
import com.yookue.commonplexus.javaseutil.util.LocalDateWraps;
import com.yookue.commonplexus.springutil.constant.ResponseBodyConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Structure for REST response
 *
 * @author David Hsing
 * @see org.springframework.http.ResponseEntity
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class RestResponseStruct implements Serializable {
    private Integer status = ResponseBodyConst.CODE_FAILURE;
    private Object data;
    private String message;
    private String additive;
    private String reminder;

    @DateTimeFormat(pattern = TemporalFormatConst.ISO_YYYYMMDD_HHMMSS)
    private LocalDateTime timestamp = LocalDateWraps.getCurrentDateTime();

    public RestResponseStruct(@Nonnull Integer status) {
        this.status = status;
    }

    public RestResponseStruct(@Nonnull HttpStatus status) {
        this.status = status.value();
    }

    public RestResponseStruct(@Nonnull Integer status, @Nullable Object data) {
        this.status = status;
        this.data = data;
    }

    public RestResponseStruct(@Nonnull HttpStatus status, @Nullable Object data) {
        this.status = status.value();
        this.data = data;
    }

    public RestResponseStruct(@Nonnull Integer status, @Nullable Object data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public RestResponseStruct(@Nonnull HttpStatus status, @Nullable Object data, @Nullable String message) {
        this.status = status.value();
        this.data = data;
        this.message = message;
    }

    public RestResponseStruct appendMessage(@Nullable String message) {
        this.message = StringUtils.join(this.message, message);
        return this;
    }

    public RestResponseStruct appendMessage(@Nullable Collection<String> messages) {
        return appendMessage(messages, null);
    }

    public RestResponseStruct appendMessage(@Nullable Collection<String> messages, @Nullable String delimiter) {
        if (!CollectionUtils.isEmpty(messages)) {
            message = StringUtils.join(message, StringUtils.join(messages, delimiter));
        }
        return this;
    }

    public RestResponseStruct prependMessage(@Nullable String message) {
        this.message = StringUtils.join(message, this.message);
        return this;
    }

    public RestResponseStruct appendAdditive(@Nullable String additive) {
        this.additive = StringUtils.join(this.additive, additive);
        return this;
    }

    public RestResponseStruct appendAdditive(@Nullable Collection<String> additives) {
        return appendAdditive(additives, null);
    }

    public RestResponseStruct appendAdditive(@Nullable Collection<String> additives, @Nullable String delimiter) {
        if (!CollectionUtils.isEmpty(additives)) {
            additive = StringUtils.join(additive, StringUtils.join(additives, delimiter));
        }
        return this;
    }

    public RestResponseStruct prependAdditive(@Nullable String additive) {
        this.additive = StringUtils.join(additive, this.additive);
        return this;
    }

    public RestResponseStruct setStatus(@Nonnull Integer status) {
        this.status = status;
        return this;
    }

    public RestResponseStruct setStatus(@Nonnull HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public ResponseEntity<RestResponseStruct> toResponseEntity() {
        return new ResponseEntity<>(this, HttpStatus.valueOf(getStatus()));
    }

    public ResponseEntity<RestResponseStruct> toResponseEntity(@Nullable MultiValueMap<String, String> headers) {
        return new ResponseEntity<>(this, headers, HttpStatus.valueOf(getStatus()));
    }
}
