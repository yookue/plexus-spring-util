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

package com.yookue.commonplexus.springutil.message;


import java.util.Locale;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import com.yookue.commonplexus.springutil.structure.FormValidationStruct;
import com.yookue.commonplexus.springutil.util.MessageSourceWraps;
import lombok.RequiredArgsConstructor;


/**
 * {@link org.springframework.context.support.MessageSourceAccessor} for resolving FormValidation messages
 *
 * @author David Hsing
 * @reference "https://formvalidation.io/"
 * @reference "https://old.formvalidation.io/"
 * @see org.springframework.context.MessageSource
 * @see org.springframework.context.support.MessageSourceAccessor
 */
@RequiredArgsConstructor
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class FormValidationMessenger {
    private final MessageSource messageSource;

    @Nonnull
    public FormValidationStruct newValid() {
        return newValid(null, null, null, null);
    }

    @Nonnull
    public FormValidationStruct newValid(@Nullable String code) {
        return newValid(code, null, null, null);
    }

    @Nonnull
    public FormValidationStruct newValid(@Nullable String code, @Nullable Object[] args) {
        return newValid(code, args, null, null);
    }

    @Nonnull
    public FormValidationStruct newValid(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newValid(code, args, defaultMessage, null);
    }

    @Nonnull
    public FormValidationStruct newValid(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        FormValidationStruct struct = new FormValidationStruct();
        setValid(struct, code, args, defaultMessage, locale);
        return struct;
    }

    @Nonnull
    public FormValidationStruct newInvalid() {
        return newInvalid(null, null, null, null);
    }

    @Nonnull
    public FormValidationStruct newInvalid(@Nullable String code) {
        return newInvalid(code, null, null, null);
    }

    @Nonnull
    public FormValidationStruct newInvalid(@Nullable String code, @Nullable Object[] args) {
        return newInvalid(code, args, null, null);
    }

    @Nonnull
    public FormValidationStruct newInvalid(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newInvalid(code, args, defaultMessage, null);
    }

    @Nonnull
    public FormValidationStruct newInvalid(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        FormValidationStruct struct = new FormValidationStruct();
        setInvalid(struct, code, args, defaultMessage, locale);
        return struct;
    }

    public void setValid(@Nonnull FormValidationStruct struct) {
        setValid(struct, null, null, null, null);
    }

    public void setValid(@Nonnull FormValidationStruct struct, @Nullable String code) {
        setValid(struct, code, null, null, null);
    }

    public void setValid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args) {
        setValid(struct, code, args, null, null);
    }

    public void setValid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setValid(struct, code, args, defaultMessage, null);
    }

    public void setValid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setValid(true);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    public void setInvalid(@Nonnull FormValidationStruct struct) {
        setInvalid(struct, null, null, null, null);
    }

    public void setInvalid(@Nonnull FormValidationStruct struct, @Nullable String code) {
        setInvalid(struct, code, null, null, null);
    }

    public void setInvalid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args) {
        setInvalid(struct, code, args, null, null);
    }

    public void setInvalid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setInvalid(struct, code, args, defaultMessage, null);
    }

    public void setInvalid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setValid(false);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    public void setMessage(@Nonnull FormValidationStruct struct, @Nullable String code) {
        setMessage(struct, code, null, null);
    }

    public void setMessage(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args) {
        setMessage(struct, code, args, null);
    }

    public void setMessage(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setMessage(struct, code, args, defaultMessage, null);
    }

    public void setMessage(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        if (StringUtils.isBlank(code)) {
            struct.setMessage(defaultMessage);
        } else {
            struct.setMessage(MessageSourceWraps.getMessageLookup(messageSource, code, args, defaultMessage, locale));
        }
    }
}
