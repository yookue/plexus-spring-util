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
 * Constants for ant paths
 *
 * @author David Hsing
 * @see org.springframework.util.AntPathMatcher
 */
@SuppressWarnings("unused")
public abstract class AntPathConst {
    public static final String SLASH_STAR = "/*";    // $NON-NLS-1$
    public static final String SLASH_STARS = "/**";    // $NON-NLS-1$
    public static final String STAR_SLASH = "*/";    // $NON-NLS-1$
    public static final String STARS_SLASH = "**/";    // $NON-NLS-1$

    public static final String STARS_FAVICON_STAR = "**/favicon.*";    // $NON-NLS-1$

    public static final String SLASH_ADMIN = "/admin";    // $NON-NLS-1$
    public static final String SLASH_ADMIN_STARS = SLASH_ADMIN + SLASH_STARS;
    public static final String SLASH_ASSET = "/asset";    // $NON-NLS-1$
    public static final String SLASH_ASSET_STARS = SLASH_ASSET + SLASH_STARS;
    public static final String SLASH_COMMON = "/common";    // $NON-NLS-1$
    public static final String SLASH_COMMON_STARS = SLASH_COMMON + SLASH_STARS;
    public static final String SLASH_CONSUMER = "/consumer";    // $NON-NLS-1$
    public static final String SLASH_CONSUMER_STARS = SLASH_CONSUMER + SLASH_STARS;
    public static final String SLASH_CONSOLE = "/console";    // $NON-NLS-1$
    public static final String SLASH_CONSOLE_STARS = SLASH_CONSOLE + SLASH_STARS;
    public static final String SLASH_DASHBOARD = "/dashboard";    // $NON-NLS-1$
    public static final String SLASH_DASHBOARD_STARS = SLASH_DASHBOARD + SLASH_STARS;
    public static final String SLASH_ERROR = "/error";    // $NON-NLS-1$
    public static final String SLASH_ERROR_STARS = SLASH_ERROR + SLASH_STARS;
    public static final String SLASH_PARTNER = "/partner";    // $NON-NLS-1$
    public static final String SLASH_PARTNER_STARS = SLASH_PARTNER + SLASH_STARS;
    public static final String SLASH_PORTAL = "/portal";    // $NON-NLS-1$
    public static final String SLASH_PORTAL_STARS = SLASH_PORTAL + SLASH_STARS;
    public static final String SLASH_PUBLIC = "/public";    // $NON-NLS-1$
    public static final String SLASH_PUBLIC_STARS = SLASH_PUBLIC + SLASH_STARS;
    public static final String SLASH_STATIC = "/static";    // $NON-NLS-1$
    public static final String SLASH_STATIC_STARS = SLASH_STATIC + SLASH_STARS;
    public static final String SLASH_STORAGE = "/storage";    // $NON-NLS-1$
    public static final String SLASH_STORAGE_STARS = SLASH_STORAGE + SLASH_STARS;
    public static final String SLASH_VROOT = "/vroot";    // $NON-NLS-1$
    public static final String SLASH_VROOT_STARS = SLASH_VROOT + SLASH_STARS;
    public static final String SLASH_WEBJARS = "/webjars";    // $NON-NLS-1$
    public static final String SLASH_WEBJARS_STARS = SLASH_WEBJARS + SLASH_STARS;

    public static final String SLASH_INDEX = "/index";    // $NON-NLS-1$
    public static final String SLASH_LOGIN = "/login";    // $NON-NLS-1$
    public static final String SLASH_DO_LOGIN = "/do-login";    // $NON-NLS-1$
    public static final String SLASH_LOGOUT = "/logout";    // $NON-NLS-1$
    public static final String SLASH_DO_LOGOUT = "/do-logout";    // $NON-NLS-1$
}
