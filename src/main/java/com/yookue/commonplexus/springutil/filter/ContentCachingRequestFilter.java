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
import java.lang.reflect.Constructor;
import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import com.yookue.commonplexus.javaseutil.constant.AssertMessageConst;
import com.yookue.commonplexus.javaseutil.constant.RegexVariantConst;
import com.yookue.commonplexus.javaseutil.util.RegexUtilsWraps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * {@link javax.servlet.Filter} for caching request contents
 *
 * @author David Hsing
 * @see org.springframework.web.util.ContentCachingRequestWrapper
 * @see org.springframework.web.filter.OncePerRequestFilter
 * @see org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuppressWarnings("unused")
public class ContentCachingRequestFilter extends AbstractExcludableRequestFilter {
    private boolean cacheMultipart = false;
    private int cacheLimit = -1;
    private Class<? extends ContentCachingRequestWrapper> requestWrapperClass = ContentCachingRequestWrapper.class;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws ServletException, IOException {
        if (cacheMultipart || RegexUtilsWraps.findAnyIgnoreCase(request.getContentType(), RegexVariantConst.APPLICATION_JSON, RegexVariantConst.APPLICATION_XML)) {
            ContentCachingRequestWrapper wrapper;
            if (requestWrapperClass == ContentCachingRequestWrapper.class) {
                wrapper = (cacheLimit > 0) ? new ContentCachingRequestWrapper(request, cacheLimit) : new ContentCachingRequestWrapper(request);
            } else {
                if (cacheLimit > 0) {
                    Constructor<? extends ContentCachingRequestWrapper> constructor = ClassUtils.getConstructorIfAvailable(requestWrapperClass, HttpServletRequest.class, Integer.class);
                    Assert.notNull(constructor, AssertMessageConst.NOT_NULL);
                    wrapper = BeanUtils.instantiateClass(constructor, request, cacheLimit);
                } else {
                    Constructor<? extends ContentCachingRequestWrapper> constructor = ClassUtils.getConstructorIfAvailable(requestWrapperClass, HttpServletRequest.class);
                    Assert.notNull(constructor, AssertMessageConst.NOT_NULL);
                    wrapper = BeanUtils.instantiateClass(constructor, request);
                }
            }
            chain.doFilter(wrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
