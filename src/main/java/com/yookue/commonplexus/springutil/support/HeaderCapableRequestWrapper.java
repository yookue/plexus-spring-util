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


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import com.yookue.commonplexus.javaseutil.util.ListPlainWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.UtilDateWraps;
import com.yookue.commonplexus.springutil.util.HttpHeaderWraps;
import com.yookue.commonplexus.springutil.util.MultiMapWraps;


/**
 * {@link javax.servlet.http.HttpServletRequestWrapper} with header modifiable
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public class HeaderCapableRequestWrapper extends HttpServletRequestWrapper {
    private final HttpHeaders httpHeaders = new HttpHeaders();

    /**
     * Constructs a request object wrapping the given request
     *
     * @param request the {@link javax.servlet.http.HttpServletRequest} to be wrapped
     */
    public HeaderCapableRequestWrapper(@Nonnull HttpServletRequest request) {
        super(request);
        MultiMapWraps.addAll(httpHeaders, HttpHeaderWraps.getHeaders(request));
    }

    public void addHeader(@Nonnull String name, @Nullable String value) {
        if (StringUtils.isNotEmpty(name)) {
            httpHeaders.add(name, value);
        }
    }

    public void addHeaders(@Nonnull String name, @Nonnull List<? extends String> values) {
        if (StringUtils.isNotEmpty(name) && !CollectionUtils.isEmpty(values)) {
            httpHeaders.addAll(name, values);
        }
    }

    public void addHeaders(@Nonnull MultiValueMap<String, String> map) {
        if (!CollectionUtils.isEmpty(map)) {
            httpHeaders.addAll(map);
        }
    }

    @Override
    public String getHeader(@Nonnull String name) {
        return ListPlainWraps.getFirst(httpHeaders.get(name));
    }

    @Override
    public Enumeration<String> getHeaders(@Nonnull String name) {
        List<String> values = httpHeaders.get(name);
        return CollectionUtils.isEmpty(values) ? null : Collections.enumeration(values);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return MapPlainWraps.getKeysToEnumeration(httpHeaders);
    }

    @Override
    public long getDateHeader(@Nonnull String name) {
        String value = getHeader(name);
        if (StringUtils.isBlank(value)) {
            return 0L;
        }
        if (NumberUtils.isParsable(value)) {
            return NumberUtils.toLong(getHeader(name));
        } else {
            Date date = UtilDateWraps.parseDateTimeGuessing(value);
            if (date != null) {
                return date.getTime();
            }
        }
        return 0L;
    }

    @Override
    public int getIntHeader(@Nonnull String name) {
        return NumberUtils.toInt(getHeader(name));
    }

    public void removeHeader(@Nonnull String name) {
        if (StringUtils.isNotEmpty(name)) {
            httpHeaders.remove(name);
        }
    }

    public void removeHeaders(@Nonnull String... names) {
        removeHeaders(Arrays.asList(names));
    }

    public void removeHeaders(@Nonnull Collection<String> names) {
        names.forEach(this::removeHeader);
    }

    public void removeHeaders(@Nonnull MultiValueMap<String, String> map) {
        if (!CollectionUtils.isEmpty(map)) {
            removeHeaders(map.keySet());
        }
    }

    public void setHeader(@Nonnull String name, @Nullable String value) {
        if (StringUtils.isNotEmpty(name)) {
            httpHeaders.set(name, value);
        }
    }
}
