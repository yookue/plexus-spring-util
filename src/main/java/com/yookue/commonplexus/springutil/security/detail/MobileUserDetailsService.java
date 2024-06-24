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


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import com.yookue.commonplexus.springutil.security.exception.MobileNotFoundException;


/**
 * {@link org.springframework.security.core.userdetails.UserDetails} for loading mobile user details
 *
 * @author David Hsing
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
@SuppressWarnings("unused")
@FunctionalInterface
public interface MobileUserDetailsService {
    /**
     * a detail instance that located user with the given mobile and dial code
     *
     * @param mobile the mobile number
     * @param dial the mobile dial code, may be null
     *
     * @return a detail instance that located user with the given mobile and dial code
     */
    UserDetails loadUserByMobile(@Nonnull String mobile, @Nullable String dial) throws MobileNotFoundException;
}
