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


import java.util.List;
import java.util.Locale;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import com.yookue.commonplexus.javaseutil.constant.AssertMessageConst;
import com.yookue.commonplexus.javaseutil.constant.SymbolVariantConst;
import com.yookue.commonplexus.javaseutil.util.HtmlEscapeWraps;
import com.yookue.commonplexus.springutil.constant.ResponseBodyConst;
import com.yookue.commonplexus.springutil.constant.RestMessageConst;
import com.yookue.commonplexus.springutil.structure.RestResponseStruct;
import com.yookue.commonplexus.springutil.util.MessageSourceWraps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * {@link org.springframework.context.support.MessageSourceAccessor} for resolving REST response messages
 *
 * @author David Hsing
 * @see org.springframework.context.MessageSource
 * @see org.springframework.context.support.MessageSourceAccessor
 * @see com.yookue.commonplexus.springutil.structure.RestResponseStruct
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
@SuppressWarnings("unused")
public class RestResponseMessenger {
    private final MessageSource messageSource;
    private BeanValidationMessenger messenger;

    @Getter
    @Setter
    private String illegalCode = RestMessageConst.ILLEGAL_REQUEST;

    @Getter
    @Setter
    private String successCode = RestMessageConst.OPERATE_SUCCESS;

    @Getter
    @Setter
    private String failureCode = RestMessageConst.OPERATE_FAILURE;

    @Getter
    @Setter
    private String refusalCode = RestMessageConst.OPERATE_REFUSAL;

    @Getter
    @Setter
    private String timeoutCode = RestMessageConst.OPERATE_TIMEOUT;

    public RestResponseMessenger(@Nonnull MessageSource source, @Nullable BeanValidationMessenger messenger) {
        this.messageSource = source;
        this.messenger = messenger;
    }

    public RestResponseMessenger(@Nonnull MessageSource source, @Nullable Validator validator) {
        this.messageSource = source;
        if (validator != null) {
            messenger = new BeanValidationMessenger(source, validator);
        }
    }

    @Nonnull
    public RestResponseStruct newIllegal() {
        return newIllegal(null, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newIllegal(@Nullable String code) {
        return newIllegal(code, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newIllegal(@Nullable String code, @Nullable Object[] args) {
        return newIllegal(code, args, null, null);
    }

    @Nonnull
    public RestResponseStruct newIllegal(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newIllegal(code, args, defaultMessage, null);
    }

    @Nonnull
    public RestResponseStruct newIllegal(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = new RestResponseStruct();
        setIllegal(struct, code, args, defaultMessage, locale);
        return struct;
    }

    @Nonnull
    public RestResponseStruct newSuccess() {
        return newSuccess(null, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newSuccess(@Nullable String code) {
        return newSuccess(code, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newSuccess(@Nullable String code, @Nullable Object[] args) {
        return newSuccess(code, args, null, null);
    }

    @Nonnull
    public RestResponseStruct newSuccess(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newSuccess(code, args, defaultMessage, null);
    }

    @Nonnull
    public RestResponseStruct newSuccess(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = new RestResponseStruct();
        setSuccess(struct, code, args, defaultMessage, locale);
        return struct;
    }

    @Nonnull
    public RestResponseStruct newSuccessOrFailure(@Nullable Object data) {
        return newSuccessOrFailure(data, null, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newSuccessOrFailure(@Nullable Object data, @Nullable String successCode) {
        return newSuccessOrFailure(data, successCode, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newSuccessOrFailure(@Nullable Object data, @Nullable String successCode, @Nullable Object[] successArgs) {
        return newSuccessOrFailure(data, successCode, successArgs, null, null);
    }

    @Nonnull
    public RestResponseStruct newSuccessOrFailure(@Nullable Object data, @Nullable String successCode, @Nullable Object[] successArgs, @Nullable String defaultMessage) {
        return newSuccessOrFailure(data, successCode, successArgs, defaultMessage, null);
    }

    /**
     * Returns a {@link com.yookue.commonplexus.springutil.structure.RestResponseStruct} with success if {@code data} is not empty, otherwise with failure
     *
     * @param data the object to be set to the "data" node of {@link com.yookue.commonplexus.springutil.structure.RestResponseStruct}
     * @param successCode the message source code to be used as success
     * @param successArgs the message source args to be used as success
     * @param defaultMessage the default success message if the message source {@code code} is not found
     * @param locale the locale in which to resolve the error message, may be {@code null}
     *
     * @return a {@link com.yookue.commonplexus.springutil.structure.RestResponseStruct} with success if {@code data} is not empty, otherwise with failure
     */
    @Nonnull
    public RestResponseStruct newSuccessOrFailure(@Nullable Object data, @Nullable String successCode, @Nullable Object[] successArgs, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = newSuccess(successCode, successArgs, defaultMessage, locale);
        setSuccessOrFailure(struct, data);
        return struct;
    }

    @Nonnull
    public RestResponseStruct newFailure() {
        return newFailure(null, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newFailure(@Nullable String code) {
        return newFailure(code, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newFailure(@Nullable String code, @Nullable Object[] args) {
        return newFailure(code, args, null, null);
    }

    @Nonnull
    public RestResponseStruct newFailure(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newFailure(code, args, defaultMessage, null);
    }

    @Nonnull
    public RestResponseStruct newFailure(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = new RestResponseStruct();
        setFailure(struct, code, args, defaultMessage, locale);
        return struct;
    }

    @Nonnull
    public RestResponseStruct newFailure(@Nonnull BindingResult binding) {
        return newFailure(binding, HtmlEscapeWraps.getBrAsString());
    }

    @Nonnull
    public RestResponseStruct newFailure(@Nonnull BindingResult binding, @Nullable String delimiter) {
        return newFailure(binding, delimiter, true, false, SymbolVariantConst.ORDER_SQUARES_SPACE, null);
    }

    @Nonnull
    public RestResponseStruct newFailure(@Nonnull BindingResult binding, @Nullable String delimiter, boolean fieldName, boolean nameLocalized, @Nullable String orderFormat) {
        return newFailure(binding, delimiter, fieldName, nameLocalized, orderFormat, null);
    }

    @Nonnull
    public RestResponseStruct newFailure(@Nonnull BindingResult binding, @Nullable String delimiter, boolean fieldName, boolean nameLocalized, @Nullable String orderFormat, @Nullable Locale locale) {
        Assert.isTrue(binding.hasErrors(), AssertMessageConst.IS_TRUE);
        RestResponseStruct struct = new RestResponseStruct();
        setFailure(struct, binding, delimiter, fieldName, nameLocalized, orderFormat, locale);
        return struct;
    }

    @Nonnull
    public RestResponseStruct newRefusal() {
        return newRefusal(null, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newRefusal(@Nullable String code) {
        return newRefusal(code, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newRefusal(@Nullable String code, @Nullable Object[] args) {
        return newRefusal(code, args, null, null);
    }

    @Nonnull
    public RestResponseStruct newRefusal(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newRefusal(code, args, defaultMessage, null);
    }

    @Nonnull
    public RestResponseStruct newRefusal(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = new RestResponseStruct();
        setRefusal(struct, code, args, defaultMessage);
        return struct;
    }

    @Nonnull
    public RestResponseStruct newTimeout() {
        return newTimeout(null, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newTimeout(@Nullable String code) {
        return newTimeout(code, null, null, null);
    }

    @Nonnull
    public RestResponseStruct newTimeout(@Nullable String code, @Nullable Object[] args) {
        return newTimeout(code, args, null, null);
    }

    @Nonnull
    public RestResponseStruct newTimeout(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newTimeout(code, args, defaultMessage, null);
    }

    @Nonnull
    public RestResponseStruct newTimeout(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = new RestResponseStruct();
        setTimeout(struct, code, args, defaultMessage, locale);
        return struct;
    }

    public void setIllegal(@Nonnull RestResponseStruct struct) {
        setIllegal(struct, illegalCode, null, null, null);
    }

    public void setIllegal(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setIllegal(struct, code, null, null, null);
    }

    public void setIllegal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setIllegal(struct, code, args, null, null);
    }

    public void setIllegal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setIllegal(struct, code, args, defaultMessage, null);
    }

    public void setIllegal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setStatus(ResponseBodyConst.CODE_ILLEGAL);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    public void setSuccess(@Nonnull RestResponseStruct struct) {
        setSuccess(struct, successCode, null, null, null);
    }

    public void setSuccess(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setSuccess(struct, code, null, null, null);
    }

    public void setSuccess(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setSuccess(struct, code, args, null, null);
    }

    public void setSuccess(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setSuccess(struct, code, args, defaultMessage, null);
    }

    public void setSuccess(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setStatus(ResponseBodyConst.CODE_SUCCESS);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    public void setSuccessOrFailure(@Nonnull RestResponseStruct struct, @Nullable Object data) {
        if (ObjectUtils.isEmpty(data)) {
            setFailure(struct);
        } else {
            struct.setData(data);
            setSuccess(struct);
        }
    }

    public void setSuccessOrFailure(@Nonnull RestResponseStruct struct, @Nullable Object data, @Nonnull String successCode, @Nonnull String failureCode) {
        setSuccessOrFailure(struct, data, successCode, failureCode, null);
    }

    public void setSuccessOrFailure(@Nonnull RestResponseStruct struct, @Nullable Object data, @Nonnull String successCode, @Nonnull String failureCode, @Nullable Locale locale) {
        if (ObjectUtils.isEmpty(data)) {
            setFailure(struct, failureCode, null, null, locale);
        } else {
            struct.setData(data);
            setSuccess(struct, successCode, null, null, locale);
        }
    }

    public void setSuccessOrFailure(@Nonnull RestResponseStruct struct, @Nullable Object data, @Nonnull String successCode, @Nullable Object[] successArgs, @Nonnull String failureCode, @Nullable Object[] failureArgs) {
        if (ObjectUtils.isEmpty(data)) {
            setFailure(struct, failureCode, failureArgs);
        } else {
            struct.setData(data);
            setSuccess(struct, successCode, successArgs);
        }
    }

    public void setFailure(@Nonnull RestResponseStruct struct) {
        setFailure(struct, failureCode, null, null, null);
    }

    public void setFailure(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setFailure(struct, code, null, null, null);
    }

    public void setFailure(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setFailure(struct, code, args, null, null);
    }

    public void setFailure(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setFailure(struct, code, args, defaultMessage, null);
    }

    public void setFailure(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setStatus(ResponseBodyConst.CODE_FAILURE);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    public void setFailure(@Nonnull RestResponseStruct struct, @Nonnull BindingResult binding) {
        setFailure(struct, binding, HtmlEscapeWraps.getBrAsString());
    }

    public void setFailure(@Nonnull RestResponseStruct struct, @Nonnull BindingResult binding, @Nullable String delimiter) {
        setFailure(struct, binding, delimiter, true, false, SymbolVariantConst.ORDER_SQUARES_SPACE, null);
    }

    public void setFailure(@Nonnull RestResponseStruct struct, @Nonnull BindingResult binding, @Nullable String delimiter, boolean fieldName, boolean nameLocalized, @Nullable String orderFormat) {
        setFailure(struct, binding, delimiter, fieldName, nameLocalized, orderFormat, null);
    }

    public void setFailure(@Nonnull RestResponseStruct struct, @Nonnull BindingResult binding, @Nullable String delimiter, boolean fieldName, boolean nameLocalized, @Nullable String orderFormat, @Nullable Locale locale) {
        Assert.isTrue(binding.hasErrors(), AssertMessageConst.IS_TRUE);
        struct.setStatus(ResponseBodyConst.CODE_FAILURE);
        Assert.notNull(messenger, AssertMessageConst.NOT_NULL);
        List<String> messages = messenger.getBindingErrors(binding, fieldName, nameLocalized, orderFormat, locale);
        struct.appendMessage(messages, delimiter);
    }

    public void setRefusal(@Nonnull RestResponseStruct struct) {
        setRefusal(struct, refusalCode, null, null, null);
    }

    public void setRefusal(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setRefusal(struct, code, null, null, null);
    }

    public void setRefusal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setRefusal(struct, code, args, null, null);
    }

    public void setRefusal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setRefusal(struct, code, args, defaultMessage, null);
    }

    public void setRefusal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setStatus(ResponseBodyConst.CODE_REFUSAL);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    public void setTimeout(@Nonnull RestResponseStruct struct) {
        setTimeout(struct, timeoutCode, null, null, null);
    }

    public void setTimeout(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setTimeout(struct, code, null, null, null);
    }

    public void setTimeout(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setTimeout(struct, code, args, null, null);
    }

    public void setTimeout(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setTimeout(struct, code, args, defaultMessage, null);
    }

    public void setTimeout(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setStatus(ResponseBodyConst.CODE_TIMEOUT);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    public void setMessage(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setMessage(struct, code, null, null);
    }

    public void setMessage(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setMessage(struct, code, args, null);
    }

    public void setMessage(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setMessage(struct, code, args, null, null);
    }

    public void setMessage(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        if (StringUtils.isBlank(code)) {
            struct.setMessage(defaultMessage);
        } else {
            struct.setMessage(MessageSourceWraps.getMessageLookup(messageSource, code, args, defaultMessage, locale));
        }
    }
}
