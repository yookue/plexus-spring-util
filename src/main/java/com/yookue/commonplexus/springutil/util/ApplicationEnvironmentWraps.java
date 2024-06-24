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


import java.util.Map;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.RegexUtilsWraps;


/**
 * Utilities for {@link org.springframework.core.env.Environment}
 *
 * @author David Hsing
 * @see org.springframework.core.env.Environment
 * @see org.springframework.core.env.AbstractEnvironment
 * @see org.springframework.core.env.ConfigurableEnvironment
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ApplicationEnvironmentWraps {
    /**
     * Returns whether the environment contains the specified active profile or not
     *
     * @param environment the Spring environment
     * @param profile the profile name, case-sensitive
     *
     * @return whether the environment contains the specified active profile or not
     */
    public static boolean containsActiveProfile(@Nullable Environment environment, @Nullable String profile) {
        return environment != null && StringUtils.isNotBlank(profile) && ArrayUtilsWraps.equalsString(getActiveProfiles(environment), profile);
    }

    /**
     * Returns whether the environment contains the specified active profile or not
     *
     * @param environment the Spring environment
     * @param profile the profile name, case-insensitive
     *
     * @return whether the environment contains the specified active profile or not
     */
    public static boolean containsActiveProfileIgnoreCase(@Nullable Environment environment, @Nullable String profile) {
        return environment != null && StringUtils.isNotBlank(profile) && ArrayUtilsWraps.equalsStringIgnoreCase(getActiveProfiles(environment), profile);
    }

    /**
     * Returns whether the environment contains the specified default profile or not
     *
     * @param environment the Spring environment
     * @param profile the profile name, case-sensitive
     *
     * @return whether the environment contains the specified default profile or not
     */
    public static boolean containsDefaultProfile(@Nullable Environment environment, @Nullable String profile) {
        return environment != null && StringUtils.isNotBlank(profile) && ArrayUtilsWraps.equalsString(getDefaultProfiles(environment), profile);
    }

    /**
     * Returns whether the environment contains the specified default profile or not
     *
     * @param environment the Spring environment
     * @param profile the profile name, case-insensitive
     *
     * @return whether the environment contains the specified default profile or not
     */
    public static boolean containsDefaultProfileIgnoreCase(@Nullable Environment environment, @Nullable String profile) {
        return environment != null && StringUtils.isNotBlank(profile) && ArrayUtilsWraps.equalsStringIgnoreCase(getDefaultProfiles(environment), profile);
    }

    /**
     * Return the array of profiles explicitly made active for this environment
     *
     * @param environment the Spring environment
     *
     * @return the array of profiles explicitly made active for this environment
     */
    public static String[] getActiveProfiles(@Nullable Environment environment) {
        return (environment == null) ? null : environment.getActiveProfiles();
    }

    /**
     * Return the array of profiles to be active by default when no active profiles have been set explicitly
     *
     * @param environment the Spring environment
     *
     * @return the array of profiles to be active by default when no active profiles have been set explicitly
     */
    public static String[] getDefaultProfiles(@Nullable Environment environment) {
        return (environment == null) ? null : environment.getDefaultProfiles();
    }

    public static ConfigurableEnvironment getConfigurableEnvironment(@Nullable Environment environment) {
        return (environment instanceof ConfigurableEnvironment) ? (ConfigurableEnvironment) environment : null;
    }

    public static Integer getLocalServerPort(@Nullable Environment environment) {
        return getLocalServerPort(getConfigurableEnvironment(environment));
    }

    /**
     * Returns the local server port with the specified environment
     *
     * @return the local server port with the specified environment
     *
     * @see org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer#onApplicationEvent
     */
    @SuppressWarnings("unchecked")
    public static Integer getLocalServerPort(@Nullable ConfigurableEnvironment environment) {
        if (environment == null) {
            return null;
        }
        PropertySource<?> source = getServerPortsPropertySource(environment);
        if (source == null || !(source.getSource() instanceof Map)) {
            return null;
        }
        Map<String, Object> properties = (Map<String, Object>) source.getSource();
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            if (RegexUtilsWraps.matches(entry.getKey(), "^local\\.\\S+\\.port$")) {    // $NON-NLS-1$
                return ObjectUtilsWraps.castAs(entry.getValue(), Integer.class);
            }
        }
        return null;
    }

    public static PropertySource<?> getServerPortsPropertySource(@Nullable Environment environment) {
        return getServerPortsPropertySource(getConfigurableEnvironment(environment));
    }

    /**
     * @see "org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer#setPortProperty"
     */
    public static PropertySource<?> getServerPortsPropertySource(@Nullable ConfigurableEnvironment environment) {
        return (environment == null) ? null : environment.getPropertySources().get("server.ports");    // $NON-NLS-1$
    }
}
