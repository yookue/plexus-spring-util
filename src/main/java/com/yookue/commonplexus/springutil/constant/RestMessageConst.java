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


@SuppressWarnings("unused")
public abstract class RestMessageConst {
    private static final String PREFIX = "RestMessage.";    // $NON-NLS-1$
    public static final String ILLEGAL_REQUEST = PREFIX + "illegalRequest";    // $NON-NLS-1$
    public static final String OPERATE_SUCCESS = PREFIX + "operateSuccess";    // $NON-NLS-1$
    public static final String OPERATE_FAILURE = PREFIX + "operateFailure";    // $NON-NLS-1$
    public static final String OPERATE_REFUSAL = PREFIX + "operateRefusal";    // $NON-NLS-1$
    public static final String OPERATE_TIMEOUT = PREFIX + "operateTimeout";    // $NON-NLS-1$
}
