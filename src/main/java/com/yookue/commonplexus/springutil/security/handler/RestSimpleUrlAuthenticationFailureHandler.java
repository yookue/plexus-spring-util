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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import com.yookue.commonplexus.springutil.util.WebUtilsWraps;
import lombok.NoArgsConstructor;


/**
 * {@link org.springframework.security.web.authentication.AuthenticationFailureHandler} for both rest and url based requests
 *
 * @author David Hsing
 * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
 */
@NoArgsConstructor
@SuppressWarnings("unused")
public abstract class RestSimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public RestSimpleUrlAuthenticationFailureHandler(@Nullable String defaultFailureUrl) {
        super(defaultFailureUrl);
    }

    @Override
    public void onAuthenticationFailure(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull AuthenticationException exception) throws ServletException, IOException {
        preAuthentication(request, response, exception);
        if (WebUtilsWraps.isRestRequest(request)) {
            restAuthenticationFailure(request, response, exception);
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
        postAuthentication(request, response, exception);
    }

    @SuppressWarnings("RedundantThrows")
    protected abstract void restAuthenticationFailure(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull AuthenticationException exception) throws ServletException, IOException;

    @SuppressWarnings("EmptyMethod")
    protected void preAuthentication(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull AuthenticationException exception) {
    }

    @SuppressWarnings("EmptyMethod")
    protected void postAuthentication(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull AuthenticationException exception) {
    }
}
