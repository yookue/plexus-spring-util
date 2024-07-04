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

package com.yookue.commonplexus.springutil.security.detail;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * {@link org.springframework.security.core.userdetails.UserDetails} for loading mobile user details
 *
 * @author David Hsing
 * @see org.springframework.security.core.userdetails.UserCache
 */
@SuppressWarnings("unused")
public interface MobileUserCache {
    /**
     * Returns a {@link org.springframework.security.core.userdetails.UserDetails} from the cache
     *
     * @param mobile the mobile number
     * @param dial the mobile dial code, may be null
     *
     * @return a {@link org.springframework.security.core.userdetails.UserDetails} from the cache
     */
    @SuppressWarnings("SameReturnValue")
    UserDetails getUserFromCache(@Nonnull String mobile, @Nullable String dial);

    /**
     * Places a {@link org.springframework.security.core.userdetails.UserDetails} in the cache
     *
     * @param details the fully populated {@link org.springframework.security.core.userdetails.UserDetails} to place in the cache
     */
    @SuppressWarnings("EmptyMethod")
    void putUserInCache(@Nonnull UserDetails details);

    /**
     * Removes the specified {@link org.springframework.security.core.userdetails.UserDetails} from the cache
     * <p>
     * The <code>mobile</code> is the key
     *
     * @param mobile the mobile number
     * @param dial the mobile dial code, may be null
     */
    @SuppressWarnings("EmptyMethod")
    void removeUserFromCache(@Nonnull String mobile, @Nullable String dial);
}
