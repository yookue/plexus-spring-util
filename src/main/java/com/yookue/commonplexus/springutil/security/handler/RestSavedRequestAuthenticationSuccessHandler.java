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
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import com.yookue.commonplexus.springutil.util.WebUtilsWraps;
import lombok.NoArgsConstructor;


/**
 * {@link org.springframework.security.web.authentication.AuthenticationSuccessHandler} for both rest and url based requests
 *
 * @author David Hsing
 * @see org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
 */
@NoArgsConstructor
@SuppressWarnings("unused")
public abstract class RestSavedRequestAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public RestSavedRequestAuthenticationSuccessHandler(@Nullable String defaultTargetUrl) {
        super.setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Authentication authentication) throws ServletException, IOException {
        preAuthentication(request, response, authentication);
        if (WebUtilsWraps.isRestRequest(request)) {
            restAuthenticationSuccess(request, response, authentication);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
        postAuthentication(request, response, authentication);
    }

    @SuppressWarnings("RedundantThrows")
    protected abstract void restAuthenticationSuccess(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Authentication authentication) throws ServletException, IOException;

    @SuppressWarnings("EmptyMethod")
    protected void preAuthentication(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Authentication authentication) {
    }

    @SuppressWarnings("EmptyMethod")
    protected void postAuthentication(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Authentication authentication) {
    }
}