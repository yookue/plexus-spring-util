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


import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.yookue.commonplexus.javaseutil.util.ConstructorUtilsWraps;
import com.yookue.commonplexus.springutil.event.PropertiesCapableEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesAuthenticationFailureAmbiguousEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesAuthenticationFailureBadCredentialsEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesAuthenticationFailureCredentialsExpiredEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesAuthenticationFailureDisabledEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesAuthenticationFailureExpiredEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesAuthenticationFailureLockedEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesAuthenticationFailureProviderNotFoundEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesAuthenticationFailureProxyUntrustedEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesAuthenticationFailureServiceExceptionEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesAuthenticationSuccessEvent;
import com.yookue.commonplexus.springutil.security.exception.BadCaptchaException;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link org.springframework.security.authentication.AuthenticationEventPublisher} with properties capable
 *
 * @author David Hsing
 * @see org.springframework.security.authentication.DefaultAuthenticationEventPublisher
 */
@SuppressWarnings("unused")
public class PropertiesAuthenticationEventPublisher implements AuthenticationEventPublisher, ApplicationEventPublisherAware, InitializingBean {
    private final HashMap<String, Constructor<? extends AbstractAuthenticationEvent>> exceptionEvents = new LinkedHashMap<>();
    private Constructor<? extends AbstractAuthenticationFailureEvent> defaultFailureConstructor;

    @Getter
    @Setter
    private Consumer<PropertiesCapableEvent> authenticationSuccessTransition;

    @Getter
    @Setter
    private Consumer<PropertiesCapableEvent> authenticationFailureTransition;

    @Setter
    protected ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void afterPropertiesSet() {
        initExceptionEvents();
        setDefaultAuthenticationFailureEvent(PropertiesAuthenticationFailureAmbiguousEvent.class);
    }

    @Override
    public void publishAuthenticationSuccess(@Nonnull Authentication authentication) {
        PropertiesAuthenticationSuccessEvent event = new PropertiesAuthenticationSuccessEvent(authentication);
        if (authenticationSuccessTransition != null) {
            authenticationSuccessTransition.accept(event);
        }
        publishEvent(event);
    }

    @Override
    public void publishAuthenticationFailure(@Nonnull AuthenticationException exception, @Nonnull Authentication authentication) {
        Constructor<? extends AbstractAuthenticationEvent> constructor = exceptionEvents.getOrDefault(exception.getClass().getName(), defaultFailureConstructor);
        if (constructor == null) {
            throw new IllegalArgumentException(String.format("Authentication exception class %s has no suitable event", exception.getClass().getName()));    // $NON-NLS-1$
        }
        AbstractAuthenticationEvent event = ConstructorUtilsWraps.newInstance(constructor, authentication, exception);
        if (event instanceof PropertiesCapableEvent instance && authenticationFailureTransition != null) {
            authenticationFailureTransition.accept(instance);
        }
        publishEvent(event);
    }

    public void addExceptionEvent(@Nonnull Class<? extends AuthenticationException> exception, @Nonnull Class<? extends AbstractAuthenticationEvent> event) {
        addExceptionEvent(exception.getName(), event);
    }

    public void addExceptionEvent(@Nonnull String exception, @Nonnull Class<? extends AbstractAuthenticationEvent> event) {
        Constructor<? extends AbstractAuthenticationEvent> constructor = ConstructorUtils.getAccessibleConstructor(event, Authentication.class, AuthenticationException.class);
        if (constructor == null) {
            throw new IllegalArgumentException(String.format("Authentication event class %s has no suitable constructor", event.getName()));    // $NON-NLS-1$
        }
        exceptionEvents.put(exception, constructor);
    }

    public Class<? extends AbstractAuthenticationFailureEvent> getDefaultAuthenticationFailureEvent() {
        return (defaultFailureConstructor == null) ? null : defaultFailureConstructor.getDeclaringClass();
    }

    public void setDefaultAuthenticationFailureEvent(@Nullable Class<? extends AbstractAuthenticationFailureEvent> event) {
        defaultFailureConstructor = ConstructorUtilsWraps.getAccessibleConstructor(event, Authentication.class, AuthenticationException.class);
        if (event != null && defaultFailureConstructor == null) {
            throw new IllegalArgumentException(String.format("Authentication event class %s has no suitable constructor", event.getName()));    // $NON-NLS-1$
        }
    }

    private void initExceptionEvents() {
        addExceptionEvent(BadCaptchaException.class, PropertiesAuthenticationFailureBadCredentialsEvent.class);
        addExceptionEvent(BadCredentialsException.class, PropertiesAuthenticationFailureBadCredentialsEvent.class);
        addExceptionEvent(UsernameNotFoundException.class, PropertiesAuthenticationFailureBadCredentialsEvent.class);
        addExceptionEvent(AccountExpiredException.class, PropertiesAuthenticationFailureExpiredEvent.class);
        addExceptionEvent(ProviderNotFoundException.class, PropertiesAuthenticationFailureProviderNotFoundEvent.class);
        addExceptionEvent(DisabledException.class, PropertiesAuthenticationFailureDisabledEvent.class);
        addExceptionEvent(LockedException.class, PropertiesAuthenticationFailureLockedEvent.class);
        addExceptionEvent(AuthenticationServiceException.class, PropertiesAuthenticationFailureServiceExceptionEvent.class);
        addExceptionEvent(CredentialsExpiredException.class, PropertiesAuthenticationFailureCredentialsExpiredEvent.class);
        addExceptionEvent("org.springframework.security.authentication.cas.ProxyUntrustedException", PropertiesAuthenticationFailureProxyUntrustedEvent.class);    // $NON-NLS-1$
        addExceptionEvent("org.springframework.security.oauth2.server.resource.InvalidBearerTokenException", PropertiesAuthenticationFailureBadCredentialsEvent.class);    // $NON-NLS-1$
    }

    @SuppressWarnings("DataFlowIssue")
    protected void publishEvent(@Nullable AbstractAuthenticationEvent event) {
        if (ObjectUtils.allNotNull(event, applicationEventPublisher)) {
            applicationEventPublisher.publishEvent(event);
        }
    }
}
