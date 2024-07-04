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
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import com.yookue.commonplexus.springutil.util.MessageSourceWraps;
import lombok.Setter;


/**
 * Default post authentication checker of {@link org.springframework.security.core.userdetails.UserDetailsChecker}
 *
 * @author David Hsing
 * @see "org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider.DefaultPostAuthenticationChecks"
 */
@Setter
@SuppressWarnings("unused")
public class DefaultPostAuthenticationChecker implements UserDetailsChecker, MessageSourceAware {
    protected MessageSource messageSource;

    @Override
    public void check(@Nonnull UserDetails details) {
        if (!details.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageSourceWraps.getMessage(messageSource, "AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
    }
}
