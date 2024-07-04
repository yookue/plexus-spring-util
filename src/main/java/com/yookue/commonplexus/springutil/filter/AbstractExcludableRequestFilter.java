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


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.springutil.util.AntPathWraps;
import com.yookue.commonplexus.springutil.util.UriUtilsWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link jakarta.servlet.Filter} with excluding ant paths capable
 *
 * @author David Hsing
 * @see org.springframework.web.filter.OncePerRequestFilter
 * @see org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter
 */
@Getter
@SuppressWarnings("unused")
public abstract class AbstractExcludableRequestFilter extends OncePerRequestFilter implements OrderedFilter {
    @Setter
    private int order = 0;

    private final List<String> excludedPaths = new ArrayList<>();

    /**
     * Excludes the specified path from the filter
     *
     * @param path a string that represents an ant path pattern
     */
    public void addExcludedPath(@Nullable String path) {
        CollectionPlainWraps.addAllIfNotBlank(excludedPaths, path);
    }

    /**
     * Excludes the specified paths from the filter
     *
     * @param paths a string array that contains ant path pattern
     */
    public void addExcludedPath(@Nullable String... paths) {
        CollectionPlainWraps.addAllIfNotBlank(excludedPaths, paths);
    }

    /**
     * Excludes the specified paths from the filter
     *
     * @param paths a string collection that contains ant path pattern
     */
    public void addExcludedPath(@Nullable Collection<String> paths) {
        CollectionPlainWraps.addAllIfNotBlank(excludedPaths, paths);
    }

    /**
     * Clears the excluded paths from the filter
     */
    public void clearExcludedPaths() {
        excludedPaths.clear();
    }

    @Override
    protected boolean shouldNotFilter(@Nonnull HttpServletRequest request) throws ServletException {
        if (!CollectionUtils.isEmpty(excludedPaths)) {
            String servletPath = UriUtilsWraps.getServletPath(request);
            return AntPathWraps.matchAnyPatterns(servletPath, excludedPaths);
        }
        return super.shouldNotFilter(request);
    }
}
