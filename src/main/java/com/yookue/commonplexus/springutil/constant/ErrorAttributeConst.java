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

package com.yookue.commonplexus.springutil.constant;


/**
 * Constants for servlet error attributes
 *
 * @author David Hsing
 * @see jakarta.servlet.RequestDispatcher
 * @see org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
 * @see org.springframework.boot.web.servlet.error.DefaultErrorAttributes
 * @see org.springframework.web.util.WebUtils#clearErrorRequestAttributes
 */
@SuppressWarnings("unused")
public abstract class ErrorAttributeConst {
    public static final String STATUS = "status";    // $NON-NLS-1$
    public static final String ERROR = "error";    // $NON-NLS-1$
    public static final String ERRORS = "errors";    // $NON-NLS-1$
    public static final String EXCEPTION = "exception";    // $NON-NLS-1$
    public static final String MESSAGE = "message";    // $NON-NLS-1$
    public static final String PATH = "path";    // $NON-NLS-1$
    public static final String TRACE = "trace";    // $NON-NLS-1$
    public static final String TIMESTAMP = "timestamp";    // $NON-NLS-1$
}
