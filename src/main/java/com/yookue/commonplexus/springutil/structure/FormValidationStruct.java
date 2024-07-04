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
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.format.annotation.DateTimeFormat;
import com.yookue.commonplexus.javaseutil.constant.TemporalFormatConst;
import com.yookue.commonplexus.javaseutil.util.LocalDateWraps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Structure for FormValidation
 *
 * @author David Hsing
 * @reference "https://formvalidation.io/"
 * @reference "https://old.formvalidation.io/"
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class FormValidationStruct implements Serializable {
    private Boolean valid = Boolean.TRUE;
    private Object data;
    private String message;

    @DateTimeFormat(pattern = TemporalFormatConst.ISO_YYYYMMDD_HHMMSS)
    private LocalDateTime timestamp = LocalDateWraps.getCurrentDateTime();

    public FormValidationStruct(@Nonnull Boolean valid) {
        this.valid = valid;
    }

    public FormValidationStruct(@Nonnull Boolean valid, @Nullable Object data) {
        this.valid = valid;
        this.data = data;
    }

    public FormValidationStruct(@Nonnull Boolean valid, @Nullable Object data, @Nullable String message) {
        this.valid = valid;
        this.data = data;
        this.message = message;
    }
}
