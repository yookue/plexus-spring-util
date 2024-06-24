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


import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import lombok.Getter;


/**
 * {@link org.springframework.security.core.Authentication} token for a mobile with a captcha
 *
 * @author David Hsing
 * @see org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 */
@Getter
@SuppressWarnings("unused")
public class MobileCaptchaAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private Object credentials;
    private Object auxiliary;

    public MobileCaptchaAuthenticationToken(@Nonnull Object principal, @Nullable Object credentials) {
        this(principal, credentials, null);
    }

    public MobileCaptchaAuthenticationToken(@Nonnull Object principal, @Nullable Object credentials, @Nullable Object auxiliary) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.auxiliary = auxiliary;
        setAuthenticated(false);
    }

    public MobileCaptchaAuthenticationToken(@Nonnull Object principal, @Nullable Object credentials, @Nullable Collection<? extends GrantedAuthority> authorities) {
        this(principal, credentials, authorities, null);
    }

    public MobileCaptchaAuthenticationToken(@Nonnull Object principal, @Nullable Object credentials, @Nullable Collection<? extends GrantedAuthority> authorities, @Nullable Object auxiliary) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.auxiliary = auxiliary;
        super.setAuthenticated(true);
    }

    @Nonnull
    public static MobileCaptchaAuthenticationToken authenticated(@Nonnull Object principal, @Nullable Object credentials, @Nullable Collection<? extends GrantedAuthority> authorities) {
        return new MobileCaptchaAuthenticationToken(principal, credentials, authorities);
    }

    @Nonnull
    public static MobileCaptchaAuthenticationToken authenticated(@Nonnull Object principal, @Nullable Object credentials, @Nullable Collection<? extends GrantedAuthority> authorities, @Nullable Object auxiliary) {
        return new MobileCaptchaAuthenticationToken(principal, credentials, authorities, auxiliary);
    }

    @Nonnull
    public static MobileCaptchaAuthenticationToken unauthenticated(@Nonnull Object principal, @Nullable Object credentials) {
        return new MobileCaptchaAuthenticationToken(principal, credentials);
    }

    @Nonnull
    public static MobileCaptchaAuthenticationToken unauthenticated(@Nonnull Object principal, @Nullable Object credentials, @Nullable Object auxiliary) {
        return new MobileCaptchaAuthenticationToken(principal, credentials, auxiliary);
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        Assert.isTrue(!authenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
        auxiliary = null;
    }
}
