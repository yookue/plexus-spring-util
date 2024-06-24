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

package com.yookue.commonplexus.springutil.exception;


import javax.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.util.ClassUtils;


/**
 * Thrown to indicate that the requested bean type is mismatched
 *
 * @author David Hsing
 * @see org.springframework.beans.factory.BeanNotOfRequiredTypeException
 * @see org.springframework.beans.TypeMismatchException
 */
@SuppressWarnings("unused")
public class BeanTypeMismatchException extends BeansException {
    public BeanTypeMismatchException(@Nonnull String beanName, @Nonnull Class<?> expectedType) {
        super(String.format("Bean named '%s' is expected to be of type '%s'", beanName, ClassUtils.getQualifiedName(expectedType)));    // $NON-NLS-1$
    }

    public BeanTypeMismatchException(@Nonnull String beanName, @Nonnull String expectedType) {
        super(String.format("Bean named '%s' is expected to be of type '%s'", beanName, expectedType));    // $NON-NLS-1$
    }
}
