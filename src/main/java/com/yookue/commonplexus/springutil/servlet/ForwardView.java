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

package com.yookue.commonplexus.springutil.servlet;


import java.util.Map;
import jakarta.annotation.Nonnull;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;


/**
 * {@link org.springframework.web.servlet.View} for forwarding request url
 *
 * @author David Hsing
 * @see org.springframework.web.servlet.View
 * @see org.springframework.web.servlet.view.AbstractView
 * @see org.springframework.web.servlet.view.RedirectView
 * @see org.springframework.web.servlet.view.UrlBasedViewResolver
 */
@SuppressWarnings("unused")
public class ForwardView extends AbstractUrlBasedView {
    @Override
    protected void renderMergedOutputModel(@Nonnull Map<String, Object> model, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) throws Exception {
        String path = StringUtils.removeStartIgnoreCase(super.getUrl(), UrlBasedViewResolver.FORWARD_URL_PREFIX);
        if (StringUtils.isBlank(path)) {
            return;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}
