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
import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yookue.commonplexus.springutil.support.HeaderCapableRequestWrapper;


/**
 * {@link javax.servlet.Filter} with header modifiable
 *
 * @author David Hsing
 * @see com.yookue.commonplexus.springutil.support.HeaderCapableRequestWrapper
 * @see org.springframework.web.filter.OncePerRequestFilter
 */
@SuppressWarnings("unused")
public class HeaderCapableRequestFilter extends AbstractExcludableRequestFilter {
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws ServletException, IOException {
        HeaderCapableRequestWrapper wrapper = new HeaderCapableRequestWrapper(request);
        chain.doFilter(wrapper, response);
    }
}
