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

package com.yookue.commonplexus.springutil.util;


import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;


/**
 * Utilities for {@link org.springframework.validation.ValidationUtils}
 *
 * @author David Hsing
 * @see org.apache.commons.lang3.Validate
 * @see org.springframework.validation.ValidationUtils
 * @see org.springframework.validation.BindingResultUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ValidationUtilsWraps {
    @Nullable
    public static String formatReason(@Nullable ObjectError error) {
        return formatReason(error, null, null);
    }

    /**
     * Returns the formatted reason phrase for {@link org.springframework.validation.ObjectError}
     *
     * @param error the object error to be detected
     * @param prefix the prefix for field, if {@code error} is a {@link org.springframework.validation.FieldError}
     * @param suffix the suffix for field, if {@code error} is a {@link org.springframework.validation.FieldError}
     *
     * @return the formatted reason phrase for {@link org.springframework.validation.ObjectError}
     */
    @Nullable
    public static String formatReason(@Nullable ObjectError error, @Nullable CharSequence prefix, @Nullable CharSequence suffix) {
        if (error == null) {
            return null;
        }
        String result = error.contains(ConstraintViolation.class) ? error.unwrap(ConstraintViolation.class).getMessage() : error.getDefaultMessage();
        if (error instanceof FieldError alias) {
            return StringUtils.join(prefix, alias.getField(), suffix, LocaleHolderWraps.getOptionalSpace(), result);
        }
        return result;
    }

    @Nullable
    public static String formatReason(@Nullable ObjectError error, @Nullable Function<FieldError, String> action) {
        if (error == null || action == null) {
            return null;
        }
        String result = error.contains(ConstraintViolation.class) ? error.unwrap(ConstraintViolation.class).getMessage() : error.getDefaultMessage();
        return (error instanceof FieldError alias) ? action.apply(alias) : result;
    }

    @Nullable
    public static List<String> formatReasons(@Nullable Collection<ObjectError> errors) {
        return formatReasons(errors, null, null);
    }

    @Nullable
    public static List<String> formatReasons(@Nullable Collection<ObjectError> errors, @Nullable CharSequence prefix, @Nullable CharSequence suffix) {
        return CollectionUtils.isEmpty(errors) ? null : errors.stream().filter(Objects::nonNull).map(error -> formatReason(error, prefix, suffix)).collect(Collectors.toList());
    }

    @Nullable
    public static List<String> formatReasons(@Nullable Collection<ObjectError> errors, @Nullable Function<FieldError, String> action) {
        return CollectionUtils.isEmpty(errors) ? null : errors.stream().filter(Objects::nonNull).map(error -> formatReason(error, action)).collect(Collectors.toList());
    }
}
