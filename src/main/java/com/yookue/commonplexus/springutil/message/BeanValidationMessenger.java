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


import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.constant.LogMessageConst;
import com.yookue.commonplexus.javaseutil.constant.RegexVariantConst;
import com.yookue.commonplexus.javaseutil.constant.SymbolVariantConst;
import com.yookue.commonplexus.javaseutil.structure.BooleanTextStruct;
import com.yookue.commonplexus.javaseutil.util.ClassUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.javaseutil.util.LocalePlainWraps;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.RegexUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;
import com.yookue.commonplexus.springutil.util.MessageSourceWraps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * {@link org.springframework.context.support.MessageSourceAccessor} for resolving {@link jakarta.validation.Validator} messages
 *
 * @author David Hsing
 * @see org.springframework.context.MessageSource
 * @see org.springframework.context.support.MessageSourceAccessor
 * @see org.springframework.validation.beanvalidation.SpringValidatorAdapter
 * @see "org.hibernate.validator.internal.engine.ConstraintViolationImpl"
 * @see "hibernate-validator.jar!/org/hibernate/validator/ValidationMessages.properties"
 */
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings({"unused", "JavadocReference"})
public class BeanValidationMessenger {
    private final MessageSource messageSource;
    private final Validator validator;

    @Nullable
    public List<String> getBindingErrors(@Nonnull BindingResult binding) {
        return getBindingErrors(binding, true, false);
    }

    @Nullable
    public List<String> getBindingErrors(@Nonnull BindingResult binding, boolean fieldName, boolean nameLocalized) {
        return getBindingErrors(binding, fieldName, nameLocalized, SymbolVariantConst.ORDER_SQUARES_SPACE);
    }

    @Nullable
    public List<String> getBindingErrors(@Nonnull BindingResult binding, boolean fieldName, boolean nameLocalized, @Nullable String orderFormat) {
        return getBindingErrors(binding, fieldName, nameLocalized, orderFormat, null);
    }

    /**
     * Return a string list that contains violation error messages from a binding result
     *
     * @param binding a binding result object
     * @param fieldName indicate using field name or not
     * @param nameLocalized indicate using localized field name or not, must be coordinated with {@code fieldName} true
     * @param orderFormat a template format used to format order string (e.g. "[%d]")
     * @param locale the locale in which to resolve the error message, may be {@code null}
     *
     * @return a string list that contains violation error messages from a binding result
     */
    @Nullable
    public List<String> getBindingErrors(@Nonnull BindingResult binding, boolean fieldName, boolean nameLocalized, @Nullable String orderFormat, @Nullable Locale locale) {
        if (!binding.hasErrors()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        List<ObjectError> objectErrors = binding.getAllErrors();
        for (int i = 0; i < objectErrors.size(); i++) {
            ObjectError error = objectErrors.get(i);
            StringBuilder builder = new StringBuilder();
            if (objectErrors.size() > 1 && StringUtils.isNotBlank(orderFormat)) {
                try {
                    builder.append(String.format(orderFormat, i + 1));
                } catch (IllegalFormatException ex) {
                    if (log.isWarnEnabled()) {
                        log.warn(LogMessageConst.EXCEPTION_OCCURRED, ex);
                    }
                }
            }
            if (error instanceof FieldError alias) {
                List<String> arrayIndexes = RegexUtilsWraps.extractMatched(alias.getField(), RegexVariantConst.ARRAY_INDEX);
                if (CollectionPlainWraps.isNotEmpty(arrayIndexes)) {
                    builder.append(StringUtilsWraps.joinRoughly(arrayIndexes)).append(StringUtils.SPACE);
                }
                String plainName = RegexUtilsWraps.removeAll(alias.getField(), RegexVariantConst.ARRAY_INDEX);
                if (fieldName) {
                    String localeName = getFieldLocaleName(alias, binding, locale);
                    if (nameLocalized && StringUtils.isNotEmpty(localeName)) {
                        builder.append(localeName);
                    } else {
                        builder.append(plainName);
                    }
                    if (!LocalePlainWraps.isChineseLanguage(LocaleContextHolder.getLocale())) {
                        builder.append(StringUtils.SPACE);
                    }
                }
                builder.append(getErrorMessage(alias, locale));
            } else {
                builder.append(getErrorMessage(error, locale));
            }
            if (!builder.isEmpty()) {
                result.add(builder.toString());
            }
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    public String getFieldLocaleName(@Nonnull FieldError error, @Nullable BindingResult binding) {
        return getFieldLocaleName(error, binding, null);
    }

    /**
     * @see org.springframework.validation.beanvalidation.SpringValidatorAdapter.ViolationFieldError
     */
    public String getFieldLocaleName(@Nonnull FieldError error, @Nullable BindingResult binding, @Nullable Locale locale) {
        Set<String> lookupCodes = new LinkedHashSet<>();
        if (StringUtils.isNotBlank(error.getObjectName())) {
            String fieldName = RegexUtilsWraps.removeAll(error.getField(), RegexVariantConst.ARRAY_INDEX);
            lookupCodes.add(StringUtils.join(error.getObjectName(), CharVariantConst.DOT, fieldName));
        }
        Set<Class<?>> lookupTypes = new LinkedHashSet<>();
        if (binding != null && binding.getTarget() != null) {
            lookupTypes.add(binding.getTarget().getClass());
        }
        if (error.contains(ConstraintViolation.class)) {
            ConstraintViolation<?> violation = error.unwrap(ConstraintViolation.class);
            ObjectUtilsWraps.ifNotNull(violation.getRootBeanClass(), lookupTypes::add);
            ObjectUtilsWraps.ifNotNull(violation.getLeafBean(), element -> lookupTypes.add(element.getClass()));
        }
        if (CollectionPlainWraps.isNotEmpty(lookupTypes)) {
            String fieldName = StringUtils.substringAfterLast(error.getField(), CharVariantConst.DOT);
            for (Class<?> lookupType : lookupTypes) {
                lookupCodes.add(StringUtils.join(ClassUtils.getCanonicalName(lookupType), CharVariantConst.DOT, fieldName));
                lookupCodes.add(StringUtils.join(ClassUtils.getShortCanonicalName(lookupType), CharVariantConst.DOT, fieldName));
                List<Class<?>> superclasses = ClassUtilsWraps.getSuperclassesUntilObject(lookupType);
                if (CollectionUtils.isEmpty(superclasses)) {
                    continue;
                }
                for (Class<?> superclass : superclasses) {
                    lookupCodes.add(StringUtils.join(ClassUtils.getCanonicalName(superclass), CharVariantConst.DOT, fieldName));
                    lookupCodes.add(StringUtils.join(ClassUtils.getShortCanonicalName(superclass), CharVariantConst.DOT, fieldName));
                }
            }
        }
        return MessageSourceWraps.firstMessageLookup(messageSource, lookupCodes, locale);
    }

    public String getErrorMessage(@Nonnull ObjectError error) {
        return getErrorMessage(error, null);
    }

    /**
     * @see org.springframework.validation.beanvalidation.SpringValidatorAdapter#determineErrorCode
     */
    public String getErrorMessage(@Nonnull ObjectError error, @Nullable Locale locale) {
        if (error.contains(ConstraintViolation.class)) {
            return error.unwrap(ConstraintViolation.class).getMessage();
        }
        return MessageSourceWraps.getMessageLookup(messageSource, error, locale);
    }

    @Nonnull
    public <T> BooleanTextStruct validateObject(@Nonnull T object, @Nullable Class<?>... groups) {
        BooleanTextStruct struct = new BooleanTextStruct(true);
        Set<ConstraintViolation<T>> violations = validator.validate(object, groups);
        return processViolationMessages(struct, violations);
    }

    @Nonnull
    public <T> BooleanTextStruct validateProperty(@Nonnull T object, @Nonnull String property, @Nullable Class<?>... groups) {
        if (StringUtils.isBlank(property)) {
            return BooleanTextStruct.newFailure();
        }
        Set<ConstraintViolation<T>> violations = validator.validateProperty(object, property, groups);
        return processViolationMessages(BooleanTextStruct.newSuccess(), violations);
    }

    @Nonnull
    public <T> BooleanTextStruct validateValue(@Nonnull Class<T> clazz, @Nonnull String property, @Nullable Object value, @Nullable Class<?>... groups) {
        if (StringUtils.isBlank(property)) {
            return BooleanTextStruct.newFailure();
        }
        Set<ConstraintViolation<T>> violations = validator.validateValue(clazz, property, value, groups);
        return processViolationMessages(BooleanTextStruct.newSuccess(), violations);
    }

    @Nonnull
    protected <T> BooleanTextStruct processViolationMessages(@Nonnull BooleanTextStruct struct, @Nullable Set<ConstraintViolation<T>> violations) {
        if (CollectionUtils.isEmpty(violations)) {
            return struct;
        }
        struct.setSuccess(false);
        for (ConstraintViolation<T> violation : violations) {
            // String field = CaseFormat.LOWER_CAMEL.to(ObjectUtils.defaultIfNull(format, CaseFormat.UPPER_UNDERSCORE), violation.getPropertyPath().toString());
            String field = StringUtils.capitalize(violation.getPropertyPath().toString());
            struct.addText(StringUtils.join(field, StringUtils.SPACE, violation.getMessage()));
        }
        return struct;
    }
}
