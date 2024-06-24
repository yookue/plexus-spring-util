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
 * Constants for misc messages
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class MiscMessageConst {
    private static final String PREFIX = "MiscMessage.";    // $NON-NLS-1$
    public static final String CONNECT_TIMEOUT_TRY = PREFIX + "connectTimeoutTry";    // $NON-NLS-1$
    public static final String MALICIOUS_ACCESS_LOG = PREFIX + "maliciousAccessLog";    // $NON-NLS-1$
    public static final String NETWORK_UNSTABLE_TRY = PREFIX + "networkUnstableTry";    // $NON-NLS-1$
    public static final String SERVER_ERROR_TRY = PREFIX + "serverErrorTry";    // $NON-NLS-1$
    public static final String SOMETHING_ERROR_TRY = PREFIX + "somethingErrorTry";    // $NON-NLS-1$
    public static final String TRY_LATER = PREFIX + "tryAgainLater";    // $NON-NLS-1$
    public static final String LOADING = PREFIX + "loading";    // $NON-NLS-1$
    public static final String WAIT_FIRST_TIME = PREFIX + "waitFirstTime";    // $NON-NLS-1$
    public static final String WAIT_FEW_SECONDS = PREFIX + "waitFewSeconds";    // $NON-NLS-1$
    public static final String WAIT_FEW_MINUTES = PREFIX + "waitFewMinutes";    // $NON-NLS-1$
}
