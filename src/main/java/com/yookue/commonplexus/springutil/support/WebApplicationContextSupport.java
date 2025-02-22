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

package com.yookue.commonplexus.springutil.support;


import jakarta.annotation.Nullable;
import jakarta.servlet.ServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationObjectSupport;


/**
 * Supports for {@link org.springframework.web.context.support.WebApplicationObjectSupport}
 *
 * @author David Hsing
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.web.context.support.WebApplicationObjectSupport
 * @see org.springframework.web.context.support.WebApplicationContextUtils
 */
@SuppressWarnings("unused")
public class WebApplicationContextSupport extends WebApplicationObjectSupport {
    @Nullable
    public WebApplicationContext getApplicationContextQuietly() {
        try {
            return super.getWebApplicationContext();
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public ServletContext getServletContextQuietly() {
        try {
            return super.getServletContext();
        } catch (Exception ignored) {
        }
        return null;
    }
}
