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
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;


/**
 * Utilities for {@link org.springframework.web.servlet.DispatcherServlet}
 *
 * @author David Hsing
 * @see org.springframework.web.servlet.DispatcherServlet
 * @see org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class DispatcherServletWraps {
    public static String[] getRelativePaths(@Nullable DispatcherServletPath dispatcher, @Nullable String... paths) {
        return getRelativePaths(dispatcher, ArrayUtilsWraps.asList(paths));
    }

    public static String[] getRelativePaths(@Nullable DispatcherServletPath dispatcher, @Nullable Collection<String> paths) {
        return (dispatcher == null || CollectionUtils.isEmpty(paths)) ? null : paths.stream().filter(StringUtils::isNotBlank).map(dispatcher::getRelativePath).toArray(String[]::new);
    }
}
