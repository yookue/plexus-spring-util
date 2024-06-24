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


import org.springframework.boot.context.config.Profiles;
import org.springframework.core.env.AbstractEnvironment;


/**
 * Constants for Spring properties
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class SpringPropertyConst {
    /**
     * @see org.springframework.core.env.AbstractEnvironment
     * @see org.springframework.boot.context.config.Profiles
     */
    public static final String PROFILES_ACTIVE = AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;
    public static final String PROFILES_DEFAULT = AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME;
    public static final String PROFILES_GROUP = "spring.profiles.group";    // $NON-NLS-1$
    public static final String PROFILES_INCLUDE = Profiles.INCLUDE_PROFILES_PROPERTY_NAME;

    /**
     * @see org.springframework.boot.context.ContextIdApplicationContextInitializer
     */
    public static final String APPLICATION_NAME = "spring.application.name";    // $NON-NLS-1$

    /**
     * @see org.springframework.boot.SpringApplication
     */
    public static final String BANNER_LOCATION = "spring.banner.location";    // $NON-NLS-1$

    public static final String MANAGEMENT_CONTEXTPATH = "management.context-path";    // $NON-NLS-1$
    public static final String MANAGEMENT_PORT = "management.port";    // $NON-NLS-1$
    public static final String MESSAGES_BASENAME = "spring.messages.basename";    // $NON-NLS-1$
}
