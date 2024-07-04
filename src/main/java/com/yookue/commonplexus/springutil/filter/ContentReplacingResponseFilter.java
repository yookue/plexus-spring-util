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

package com.yookue.commonplexus.springutil.filter;


import java.io.IOException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * {@link jakarta.servlet.Filter} for replacing response contents
 *
 * @author David Hsing
 * @see org.springframework.web.util.ContentCachingResponseWrapper
 * @see org.springframework.web.filter.OncePerRequestFilter
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuppressWarnings("unused")
public class ContentReplacingResponseFilter extends AbstractExcludableRequestFilter {
    private String content;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws ServletException, IOException {
        if (StringUtils.isNotEmpty(content)) {
            ContentCachingResponseWrapper wrapper = new ContentCachingResponseWrapper(response);
            wrapper.getWriter().write(content);
            wrapper.copyBodyToResponse();
            chain.doFilter(request, wrapper);
        } else {
            chain.doFilter(request, response);
        }
    }
}
