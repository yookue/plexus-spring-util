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

package com.yookue.commonplexus.springutil.security.authentication;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.util.Assert;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;
import com.yookue.commonplexus.springutil.security.detail.DefaultPostAuthenticationChecker;
import com.yookue.commonplexus.springutil.security.detail.DefaultPreAuthenticationChecker;
import com.yookue.commonplexus.springutil.security.detail.MobileUserCache;
import com.yookue.commonplexus.springutil.security.detail.MobileUserDetailsService;
import com.yookue.commonplexus.springutil.security.detail.impl.NullMobileUserCache;
import com.yookue.commonplexus.springutil.security.exception.BadCaptchaException;
import com.yookue.commonplexus.springutil.security.exception.IllegalAuthenticationException;
import com.yookue.commonplexus.springutil.security.exception.MobileNotFoundException;
import com.yookue.commonplexus.springutil.util.MessageSourceWraps;
import com.yookue.commonplexus.springutil.util.SecurityUtilsWraps;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * {@link org.springframework.security.authentication.AuthenticationProvider} for a mobile with a captcha
 *
 * @author David Hsing
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
 * @see org.springframework.security.authentication.DefaultAuthenticationEventPublisher
 */
@NoArgsConstructor
@Setter
@SuppressWarnings("unused")
public abstract class MobileCaptchaAuthenticationProvider implements AuthenticationProvider, ApplicationEventPublisherAware, MessageSourceAware {
    @Getter
    private boolean forcePrincipalAsString = false;

    @Getter
    private MobileUserCache userCache = new NullMobileUserCache();

    @Getter
    private MobileUserDetailsService userDetailsService;

    @Getter
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Getter
    private UserDetailsChecker preAuthenticationChecker = new DefaultPreAuthenticationChecker();

    @Getter
    private UserDetailsChecker postAuthenticationChecker = new DefaultPostAuthenticationChecker();

    protected ApplicationEventPublisher applicationEventPublisher;
    protected MessageSource messageSource;

    @Override
    public Authentication authenticate(@Nonnull Authentication authentication) throws AuthenticationException {
        Assert.notNull(userDetailsService, "The user details service must be set");
        Assert.notNull(applicationEventPublisher, "The application event publisher must be set");
        Assert.notNull(messageSource, "The message source must be set");
        Assert.isInstanceOf(MobileCaptchaAuthenticationToken.class, authentication, "Only supported " + MobileCaptchaAuthenticationToken.class.getSimpleName());
        String mobile = determineMobile(authentication), dial = determineDial(authentication);
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalAuthenticationException(MessageSourceWraps.getMessage(messageSource, "MobileCaptchaAuthenticationFilter.illegalMobile", "Illegal mobile"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
        boolean cacheUsed = true;
        UserDetails details = (userCache == null) ? null : userCache.getUserFromCache(mobile, dial);
        if (details == null) {
            cacheUsed = false;
            details = retrieveUser(mobile, authentication, dial);
            Assert.notNull(details, "retrieveUser returned null - a violation of the interface contract");
        }
        try {
            if (preAuthenticationChecker != null) {
                preAuthenticationChecker.check(details);
            }
            additionalAuthenticationChecks(details, authentication);
        } catch (AuthenticationException ex) {
            if (!cacheUsed) {
                throw ex;
            }
            // There was a problem, so try again after checking. We're using latest data (i.e. not from the cache)
            cacheUsed = false;
            details = retrieveUser(mobile, authentication, dial);
            if (preAuthenticationChecker != null) {
                preAuthenticationChecker.check(details);
            }
            additionalAuthenticationChecks(details, authentication);
        }
        if (postAuthenticationChecker != null) {
            postAuthenticationChecker.check(details);
        }
        if (!cacheUsed && userCache != null) {
            userCache.putUserInCache(details);
        }
        Object principal = forcePrincipalAsString ? details.getUsername() : details;
        return createSuccessAuthentication(principal, authentication, details, dial);
    }

    @Override
    public boolean supports(@Nonnull Class<?> authentication) {
        return MobileCaptchaAuthenticationToken.class.isAssignableFrom(authentication);
    }

    protected String determineMobile(@Nullable Authentication authentication) {
        return (authentication == null) ? null : authentication.getName();
    }

    protected String determineDial(@Nullable Authentication authentication) {
        if (authentication instanceof MobileCaptchaAuthenticationToken alias) {
            return ObjectUtilsWraps.castAs(alias.getAuxiliary(), String.class);
        }
        return null;
    }

    protected UserDetails retrieveUser(@Nonnull String mobile, @Nonnull Authentication authentication, @Nullable String dial) throws AuthenticationException {
        try {
            return userDetailsService.loadUserByMobile(mobile, dial);
        } catch (MobileNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    protected Authentication createSuccessAuthentication(@Nonnull Object principal, @Nonnull Authentication authentication, @Nonnull UserDetails details, @Nullable Object dial) {
        Assert.notNull(authoritiesMapper, "The authorities mapper must be set");
        MobileCaptchaAuthenticationToken result = MobileCaptchaAuthenticationToken.authenticated(principal, authentication.getCredentials(), authoritiesMapper.mapAuthorities(details.getAuthorities()), dial);
        result.setDetails(authentication.getDetails());
        return result;
    }

    protected void additionalAuthenticationChecks(@Nonnull UserDetails details, @Nonnull Authentication authentication) throws AuthenticationException {
        String mobile = determineMobile(authentication), dial = determineDial(authentication);
        String income = SecurityUtilsWraps.getAuthenticationCredentialsAsString(authentication);
        String stored = getStoredCaptcha(mobile, dial);
        if (StringUtilsWraps.anyNotBlank(income, stored) && !StringUtils.equals(income, stored)) {
            throw new BadCaptchaException(MessageSourceWraps.getMessage(messageSource, "MobileCaptchaAuthenticationProvider.badMobileOrCaptcha", "Bad mobile or captcha"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
    }

    protected abstract String getStoredCaptcha(@Nonnull String mobile, @Nullable String dial);
}
