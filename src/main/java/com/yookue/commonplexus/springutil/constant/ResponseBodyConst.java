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


import org.springframework.http.HttpStatus;


/**
 * Constants for REST response
 *
 * @author David Hsing
 * @see org.springframework.http.HttpStatus
 */
@SuppressWarnings("unused")
public abstract class ResponseBodyConst {
    public static final int CODE_REFUSAL = HttpStatus.FORBIDDEN.value();
    public static final int CODE_ILLEGAL = HttpStatus.BAD_REQUEST.value();
    public static final int CODE_SUCCESS = HttpStatus.OK.value();
    public static final int CODE_FAILURE = HttpStatus.EXPECTATION_FAILED.value();
    public static final int CODE_TIMEOUT = HttpStatus.REQUEST_TIMEOUT.value();

    public static final String HTML_STATUS = "errorStatus";    // $NON-NLS-1$
    public static final String HTML_PHRASE = "errorPhrase";    // $NON-NLS-1$
    public static final String HTML_MESSAGE = "errorMessage";    // $NON-NLS-1$
    public static final String HTML_DATA = "errorData";    // $NON-NLS-1$
    public static final String HTML_TIMESTAMP = "errorTimestamp";    // $NON-NLS-1$

    public static final String HTML_SUBJECT = "errorSubject";    // $NON-NLS-1$
    public static final String HTML_CONTENT = "errorContent";    // $NON-NLS-1$

    public static final String REST_STATUS = "status";    // $NON-NLS-1$
    public static final String REST_DATA = "data";    // $NON-NLS-1$
    public static final String REST_MESSAGE = "message";    // $NON-NLS-1$
    public static final String REST_ADDITIVE = "additive";    // $NON-NLS-1$
    public static final String REST_REMINDER = "reminder";    // $NON-NLS-1$
    public static final String REST_SOLUTION = "solution";    // $NON-NLS-1$
    public static final String REST_TIMESTAMP = "timestamp";    // $NON-NLS-1$
}
