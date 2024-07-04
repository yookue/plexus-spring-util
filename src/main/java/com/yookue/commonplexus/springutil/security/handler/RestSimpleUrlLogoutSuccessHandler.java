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

package com.yookue.commonplexus.springutil.security.handler;


import java.io.IOException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import com.yookue.commonplexus.springutil.util.WebUtilsWraps;
import lombok.NoArgsConstructor;


/**
 * {@link org.springframework.security.web.authentication.logout.LogoutSuccessHandler} for both rest and url based requests
 *
 * @author David Hsing
 * @see org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler
 * @see org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
 */
@NoArgsConstructor
@SuppressWarnings("unused")
public abstract class RestSimpleUrlLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    public RestSimpleUrlLogoutSuccessHandler(@Nullable String defaultTargetUrl) {
        super.setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onLogoutSuccess(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Authentication authentication) throws ServletException, IOException {
        preLogout(request, response, authentication);
        if (WebUtilsWraps.isRestRequest(request)) {
            restLogoutSuccess(request, response, authentication);
        } else {
            super.onLogoutSuccess(request, response, authentication);
        }
        postLogout(request, response, authentication);
    }

    @SuppressWarnings("RedundantThrows")
    protected abstract void restLogoutSuccess(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Authentication authentication) throws ServletException, IOException;

    @SuppressWarnings("EmptyMethod")
    protected void preLogout(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Authentication authentication) {
    }

    @SuppressWarnings("EmptyMethod")
    protected void postLogout(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Authentication authentication) {
    }
}
