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


import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;


/**
 * Utilities for {@link org.springframework.session.Session}
 *
 * @author David Hsing
 * @see org.springframework.web.server.session.WebSessionIdResolver
 * @see org.springframework.web.server.session.CookieWebSessionIdResolver
 * @see org.springframework.web.server.session.HeaderWebSessionIdResolver
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class SessionUtilsWraps {
    @Nullable
    public static String getSessionId(@Nullable WebSessionIdResolver resolver) {
        if (resolver instanceof CookieWebSessionIdResolver) {
            return getCookieName(resolver);
        } else if (resolver instanceof HeaderWebSessionIdResolver) {
            return getHeaderName(resolver);
        }
        return null;
    }

    @Nullable
    public static String getCookieName(@Nullable WebSessionIdResolver resolver) {
        return (resolver instanceof CookieWebSessionIdResolver alias) ? alias.getCookieName() : null;
    }

    @Nullable
    public static String getHeaderName(@Nullable WebSessionIdResolver resolver) {
        return (resolver instanceof HeaderWebSessionIdResolver alias) ? alias.getHeaderName() : null;
    }

    @SuppressWarnings("unchecked")
    public static SessionRepository<? extends Session> getSessionRepository(@Nullable HttpServletRequest request) {
        return WebUtilsWraps.getSessionAttributeAs(request, SessionRepositoryFilter.SESSION_REPOSITORY_ATTR, SessionRepository.class);
    }

    public static SessionRepository<? extends Session> getContextSessionRepository() {
        return getSessionRepository(WebUtilsWraps.getContextServletRequest());
    }
}
