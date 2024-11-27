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

package com.yookue.commonplexus.springutil.util;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasAuthenticationToken;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.exception.UnsupportedClassException;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;


/**
 * Utilities for Spring Security
 *
 * @author David Hsing
 * @see org.springframework.security.core.Authentication
 * @see org.springframework.security.core.authority.AuthorityUtils
 * @see org.springframework.security.core.context.SecurityContextHolder
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class SecurityUtilsWraps {
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> T getAuthenticationPrincipalAs(@Nullable Authentication authentication, @Nullable Class<T> expectedType) {
        return ObjectUtils.anyNull(authentication, expectedType) ? null : ObjectUtilsWraps.castAs(authentication.getPrincipal(), expectedType);
    }

    @Nullable
    public static String getAuthenticationPrincipalAsString(@Nullable Authentication authentication) {
        return getAuthenticationPrincipalAs(authentication, String.class);
    }

    /**
     * @see org.springframework.security.authentication.AbstractAuthenticationToken#getName
     */
    @Nullable
    public static String getAuthenticationPrincipalName(@Nullable Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails alias) {
            return alias.getUsername();
        } else if (principal instanceof AuthenticatedPrincipal alias) {
            return alias.getName();
        } else if (principal instanceof Principal alias) {
            return alias.getName();
        }
        return Objects.toString(principal, null);
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> T getAuthenticationCredentialsAs(@Nullable Authentication authentication, @Nullable Class<T> expectedType) {
        return ObjectUtils.anyNull(authentication, expectedType) ? null : ObjectUtilsWraps.castAs(authentication.getCredentials(), expectedType);
    }

    @Nullable
    public static String getAuthenticationCredentialsAsString(@Nullable Authentication authentication) {
        return getAuthenticationCredentialsAs(authentication, String.class);
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static <T> T getAuthenticationDetailsAs(@Nullable Authentication authentication, @Nullable Class<T> expectedType) {
        return ObjectUtils.anyNull(authentication, expectedType) ? null : ObjectUtilsWraps.castAs(authentication.getDetails(), expectedType);
    }

    @Nullable
    public static String getAuthenticationDetailsAsString(@Nullable Authentication authentication) {
        return getAuthenticationDetailsAs(authentication, String.class);
    }

    @Nullable
    public static Authentication getContextAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return (context == null) ? null : context.getAuthentication();
    }

    public static void setContextAuthentication(@Nullable Authentication authentication) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            context.setAuthentication(authentication);
        }
    }

    /**
     * @reference "https://github.com/szerhusenBC/jwt-spring-security-demo"
     * @see org.springframework.security.authentication.jaas.JaasNameCallbackHandler#handle
     */
    @Nullable
    @SuppressWarnings({"JavadocDeclaration", "JavadocLinkAsPlainText"})
    public static String getContextAuthenticationUsername() {
        Authentication authentication = getContextAuthentication();
        if (authentication == null) {
            return null;
        }
        if (authentication.getPrincipal() instanceof UserDetails alias) {
            return alias.getUsername();
        }
        return ObjectUtilsWraps.toString(authentication.getPrincipal());
    }

    @Nullable
    public static <T> T getContextAuthenticationPrincipalAs(@Nullable Class<T> expectedType) {
        return getAuthenticationPrincipalAs(getContextAuthentication(), expectedType);
    }

    @Nullable
    public static <T> T getContextAuthenticationDetailsAs(@Nullable Class<T> expectedType) {
        return getAuthenticationDetailsAs(getContextAuthentication(), expectedType);
    }

    @Nullable
    public static List<GrantedAuthority> getGrantedAuthorities(@Nullable String[] authorities) {
        return getGrantedAuthorities(ArrayUtilsWraps.asList(authorities));
    }

    @Nullable
    public static List<GrantedAuthority> getGrantedAuthorities(@Nullable Collection<String> authorities) {
        return CollectionUtils.isEmpty(authorities) ? null : authorities.stream().filter(StringUtils::isNotBlank).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Nullable
    public static List<String> getContextGrantedAuthoritiesNames() {
        return getGrantedAuthoritiesNames(getContextAuthentication());
    }

    @Nullable
    public static List<String> getGrantedAuthoritiesNames(@Nullable Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return CollectionUtils.isEmpty(authorities) ? null : authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public static List<SecurityContext> getSessionSecurityContexts(@Nullable HttpServletRequest request) {
        return (request == null) ? null : getSessionSecurityContexts(request.getSession());
    }

    /**
     * @see org.springframework.security.web.session.HttpSessionDestroyedEvent#getSecurityContexts
     */
    public static List<SecurityContext> getSessionSecurityContexts(@Nullable HttpSession session) {
        if (session == null) {
            return null;
        }
        Enumeration<String> attributes = session.getAttributeNames();
        ArrayList<SecurityContext> result = new ArrayList<>();
        while (attributes != null && attributes.hasMoreElements()) {
            String name = attributes.nextElement();
            Object value = session.getAttribute(name);
            if (value instanceof SecurityContext alias) {
                result.add(alias);
            }
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    public static List<Authentication> getSessionAuthentications(@Nullable HttpServletRequest request) {
        return (request == null) ? null : getSessionAuthentications(request.getSession());
    }

    public static List<Authentication> getSessionAuthentications(@Nullable HttpSession session) {
        if (session == null) {
            return null;
        }
        List<SecurityContext> contexts = getSessionSecurityContexts(session);
        return CollectionUtils.isEmpty(contexts) ? null : contexts.stream().map(SecurityContext::getAuthentication).collect(Collectors.toList());
    }

    public static boolean isAuthenticationAuthenticated(@Nullable Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    public static Authentication renewAuthentication(@Nullable Authentication authentication, @Nullable Object principal) throws IllegalArgumentException, IllegalAccessException {
        return renewAuthentication(authentication, principal, false, null, false, null, false, null);
    }

    public static Authentication renewAuthentication(@Nullable Authentication authentication, @Nullable Object principal, @Nullable Object details) throws IllegalArgumentException, IllegalAccessException {
        return renewAuthentication(authentication, principal, false, null, false, null, true, details);
    }

    @Nullable
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static Authentication renewAuthentication(@Nullable Authentication authentication, @Nullable Object principal, boolean renewCredentials, @Nullable Object credentials, boolean renewAuthorities, @Nullable Collection<? extends GrantedAuthority> authorities, boolean renewDetails, @Nullable Object details) throws IllegalAccessException, UnsupportedClassException {
        if (ObjectUtils.anyNull(authentication, principal)) {
            return authentication;
        }
        Authentication result;
        final String keyHash = "keyHash";    // $NON-NLS-1$
        if (authentication instanceof AnonymousAuthenticationToken alias) {
            result = new AnonymousAuthenticationToken(StringUtils.SPACE, principal, renewAuthorities ? authorities : authentication.getAuthorities());
            FieldUtils.writeField(result, keyHash, alias.getKeyHash(), true);
        } else if (authentication instanceof JaasAuthenticationToken alias) {
            result = new JaasAuthenticationToken(principal, renewCredentials ? credentials : authentication.getCredentials(), alias.getLoginContext());
        } else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            result = new PreAuthenticatedAuthenticationToken(principal, renewCredentials ? credentials : authentication.getCredentials(), renewAuthorities ? authorities : authentication.getAuthorities());
        } else if (authentication instanceof RememberMeAuthenticationToken alias) {
            result = new RememberMeAuthenticationToken(StringUtils.SPACE, principal, renewAuthorities ? authorities : authentication.getAuthorities());
            FieldUtils.writeField(result, keyHash, alias.getKeyHash(), true);
        } else if (authentication instanceof TestingAuthenticationToken) {
            List<GrantedAuthority> oldAuthorities = new ArrayList<>(authentication.getAuthorities());
            List<GrantedAuthority> newAuthorities = new ArrayList<>(authorities);
            result = new TestingAuthenticationToken(principal, renewCredentials ? credentials : authentication.getCredentials(), renewAuthorities ? newAuthorities : oldAuthorities);
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
            result = new UsernamePasswordAuthenticationToken(principal, renewCredentials ? credentials : authentication.getCredentials(), renewAuthorities ? authorities : authentication.getAuthorities());
        } else {
            throw new UnsupportedClassException("Unsupported authentication type: " + authentication.getClass().getName());
        }
        if (renewDetails) {
            setAuthenticationDetails(result, details);
        }
        return result;
    }

    @Nullable
    public static Authentication renewAuthenticationQuietly(@Nullable Authentication authentication, @Nullable Object principal) {
        try {
            return renewAuthentication(authentication, principal);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static Authentication renewAuthenticationQuietly(@Nullable Authentication authentication, @Nullable Object principal, @Nullable Object details) {
        try {
            return renewAuthentication(authentication, principal, details);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static Authentication renewAuthenticationQuietly(@Nullable Authentication authentication, @Nullable Object principal, boolean renewCredentials, @Nullable Object credentials, boolean renewAuthorities, @Nullable Collection<? extends GrantedAuthority> authorities, boolean renewDetails, @Nullable Object details) {
        try {
            return renewAuthentication(authentication, principal, renewCredentials, credentials, renewAuthorities, authorities, renewDetails, details);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * @see org.springframework.security.web.authentication.WebAuthenticationDetails
     */
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public static void setAuthenticationDetails(@Nullable Authentication authentication, @Nullable Object details) {
        if (ObjectUtils.anyNull(authentication, details)) {
            return;
        }
        if (!(authentication instanceof AbstractAuthenticationToken)) {
            throw new IllegalArgumentException("Unsupported authentication type: " + authentication.getClass().getName());
        }
        ((AbstractAuthenticationToken) authentication).setDetails(details);
    }

    public static void setAuthenticationDetailsQuietly(@Nullable Authentication authentication, @Nullable Object details) {
        try {
            setAuthenticationDetails(authentication, details);
        } catch (Exception ignored) {
        }
    }

    public static void setContextAuthenticationPrincipal(@Nullable Object principal) throws IllegalAccessException, IllegalArgumentException {
        if (principal != null) {
            Authentication authentication = renewAuthentication(getContextAuthentication(), principal);
            setContextAuthentication(authentication);
        }
    }

    public static void setContextAuthenticationPrincipal(@Nullable Object principal, boolean renewCredentials, @Nullable Object credentials, boolean renewAuthorities, @Nullable Collection<? extends GrantedAuthority> authorities) throws IllegalAccessException, IllegalArgumentException {
        if (principal != null) {
            Authentication authentication = renewAuthentication(getContextAuthentication(), principal, renewCredentials, credentials, renewAuthorities, authorities, false, null);
            setContextAuthentication(authentication);
        }
    }

    public static void setContextAuthenticationPrincipalQuietly(@Nullable Object principal) {
        try {
            setContextAuthenticationPrincipal(principal);
        } catch (Exception ignored) {
        }
    }

    public static void setContextAuthenticationPrincipalQuietly(@Nullable Object principal, boolean renewCredentials, @Nullable Object credentials, boolean renewAuthorities, @Nullable Collection<? extends GrantedAuthority> authorities) {
        try {
            setContextAuthenticationPrincipal(principal, renewCredentials, credentials, renewAuthorities, authorities);
        } catch (Exception ignored) {
        }
    }

    public static void setContextAuthenticationDetails(@Nullable WebAuthenticationDetails details) throws IllegalArgumentException {
        if (details != null) {
            Authentication authentication = getContextAuthentication();
            setAuthenticationDetails(authentication, details);
            setContextAuthentication(authentication);
        }
    }

    public static void setContextAuthenticationDetailsQuietly(@Nullable WebAuthenticationDetails details) {
        try {
            setContextAuthenticationDetails(details);
        } catch (Exception ignored) {
        }
    }
}
