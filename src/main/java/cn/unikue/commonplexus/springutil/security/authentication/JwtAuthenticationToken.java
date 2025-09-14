/*
 * Copyright (c) 2016 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.security.authentication;


import java.util.Collection;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.security.core.GrantedAuthority;
import com.auth0.jwt.interfaces.DecodedJWT;
import cn.unikue.commonplexus.springutil.util.JwtAuthWraps;
import lombok.Getter;


/**
 * {@link org.springframework.security.core.Authentication} token for jwt
 *
 * @author David Hsing
 *
 * @see org.springframework.security.authentication.AbstractAuthenticationToken
 */
@Getter
@SuppressWarnings("unused")
public class JwtAuthenticationToken extends AbstractAdditiveAuthenticationToken {
    public JwtAuthenticationToken(@Nonnull Object principal) {
        super(principal, null, null);
    }

    public JwtAuthenticationToken(@Nonnull Object principal, @Nullable Object credentials) {
        super(principal, credentials, null);
    }

    public JwtAuthenticationToken(@Nonnull Object principal, @Nullable Object credentials, @Nullable Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public DecodedJWT getDecodedJwt() {
        return JwtAuthWraps.decodeToken(super.getCredentialsAs(String.class));
    }
}
