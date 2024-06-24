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

package com.yookue.commonplexus.springutil.security.detail.impl;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import com.yookue.commonplexus.springutil.security.detail.MobileUserCache;


/**
 * {@link com.yookue.commonplexus.springutil.security.detail.MobileUserCache} that performing any caching
 *
 * @author David Hsing
 * @see org.springframework.security.core.userdetails.cache.NullUserCache
 */
@SuppressWarnings("unused")
public class NullMobileUserCache implements MobileUserCache {
    @Override
    public UserDetails getUserFromCache(@Nonnull String mobile, @Nullable String dial) {
        return null;
    }

    @Override
    public void putUserInCache(@Nonnull UserDetails details) {
    }

    @Override
    public void removeUserFromCache(@Nonnull String mobile, @Nullable String dial) {
    }
}
