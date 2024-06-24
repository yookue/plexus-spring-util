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


import java.io.IOException;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.yookue.commonplexus.javaseutil.util.FieldUtilsWraps;
import com.yookue.commonplexus.springutil.security.exception.IllegalAuthenticationException;
import com.yookue.commonplexus.springutil.util.JsonParserWraps;
import com.yookue.commonplexus.springutil.util.RequestParamWraps;
import com.yookue.commonplexus.springutil.util.WebUtilsWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link org.springframework.security.core.Authentication} filter for a mobile with a captcha
 *
 * @author David Hsing
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class MobileCaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter implements BeanFactoryAware {
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";    // $NON-NLS-1$
    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";    // $NON-NLS-1$
    public static final String SPRING_SECURITY_FORM_DIAL_KEY = "dial";    // $NON-NLS-1$
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", HttpMethod.POST.name());    // $NON-NLS-1$

    @Getter
    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;

    @Getter
    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;

    @Getter
    private String dialParameter = SPRING_SECURITY_FORM_DIAL_KEY;

    @Getter
    private boolean postOnly = true;

    @Getter
    private boolean restCompatible = false;

    @Getter
    private Function<InteractiveAuthenticationSuccessEvent, AbstractAuthenticationEvent> authenticationSuccessTransition;

    protected BeanFactory beanFactory;

    public MobileCaptchaAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public MobileCaptchaAuthenticationFilter(@Nonnull AuthenticationManager manager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, manager);
    }

    /**
     * Returns the actual authentication through the given request
     * <p>
     * The implementation should do one of the following:
     * <ol>
     * <li>Return a populated authentication token for the authenticated user, indicating successful authentication</li>
     * <li>Return null, indicating that the authentication process is still in progress</li>
     * <li>Throw an <tt>AuthenticationException</tt> if the authentication process fails</li>
     * </ol>
     *
     * @param request from which to extract parameters and perform the authentication
     * @param response which may be needed if the implementation has to do a redirect as part of a multi-stage authentication process (such as OpenID)
     *
     * @return the actual authentication through the given request
     *
     * @throws org.springframework.security.core.AuthenticationException if authentication fails
     * @see org.springframework.security.authentication.ProviderManager
     */
    @Override
    public Authentication attemptAuthentication(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException(super.messages.getMessage("MiscMessage.unsupportedHttpMethod", new Object[]{request.getMethod()}, "Unsupported method"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
        HttpServletRequest wrapper = WebUtilsWraps.wrapWithContentCaching(request);
        String mobile = obtainMobile(wrapper), captcha = obtainCaptcha(wrapper), dial = obtainDial(wrapper);
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalAuthenticationException(super.messages.getMessage("MobileCaptchaAuthenticationFilter.illegalMobile", "Illegal mobile"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
        if (StringUtils.isBlank(captcha)) {
            throw new IllegalAuthenticationException(super.messages.getMessage("MobileCaptchaAuthenticationFilter.illegalCaptcha", "Illegal captcha"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
        preAuthentication(wrapper, mobile, captcha, dial);
        MobileCaptchaAuthenticationToken token = MobileCaptchaAuthenticationToken.unauthenticated(mobile, captcha, dial);
        setDetails(wrapper, token);
        return super.getAuthenticationManager().authenticate(token);
    }

    protected String obtainMobile(@Nonnull HttpServletRequest request) {
        String result = RequestParamWraps.getStringParameterTrimming(request, mobileParameter);
        if (StringUtils.isEmpty(result) && restCompatible) {
            String content = WebUtilsWraps.getContentAsStringTrimmingQuietly(request, true);
            result = JsonParserWraps.findNodeValueAsText(content, mobileParameter, beanFactory);
        }
        return result;
    }

    protected String obtainCaptcha(@Nonnull HttpServletRequest request) {
        String result = RequestParamWraps.getStringParameterTrimming(request, captchaParameter);
        if (StringUtils.isEmpty(result) && restCompatible) {
            String content = WebUtilsWraps.getContentAsStringTrimmingQuietly(request, true);
            result = JsonParserWraps.findNodeValueAsText(content, captchaParameter, beanFactory);
        }
        return result;
    }

    protected String obtainDial(@Nonnull HttpServletRequest request) {
        String result = RequestParamWraps.getStringParameterTrimming(request, dialParameter);
        if (StringUtils.isEmpty(result) && restCompatible) {
            String content = WebUtilsWraps.getContentAsStringTrimmingQuietly(request, true);
            result = JsonParserWraps.findNodeValueAsText(content, dialParameter, beanFactory);
        }
        return result;
    }

    /**
     * Perform actions before authentication
     * <p>
     * Override to perform additional actions, such as logging, etc
     */
    @SuppressWarnings("EmptyMethod")
    protected void preAuthentication(@Nonnull HttpServletRequest request, @Nonnull String mobile, @Nonnull String captcha, @Nullable String dial) throws AuthenticationException {
    }

    protected void setDetails(@Nonnull HttpServletRequest request, @Nonnull MobileCaptchaAuthenticationToken token) {
        token.setDetails(super.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Behaviour for successful authentication
     * <p>
     * With {@code authenticationSuccessTransition}, this can publish a different authentication event, not only {@link org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent}
     */
    @Override
    @SuppressWarnings("DuplicatedCode")
    protected void successfulAuthentication(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain, @Nonnull Authentication authentication) throws IOException, ServletException {
        if (authenticationSuccessTransition == null) {
            super.successfulAuthentication(request, response, chain, authentication);
            return;
        }
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        SecurityContextRepository repository = FieldUtilsWraps.readDeclaredFieldAs(this, "securityContextRepository", true, SecurityContextRepository.class);    // $NON-NLS-1$
        if (repository != null) {
            repository.saveContext(context, request, response);
        }
        super.getRememberMeServices().loginSuccess(request, response, authentication);
        AbstractAuthenticationEvent event = authenticationSuccessTransition.apply(new InteractiveAuthenticationSuccessEvent(authentication, this.getClass()));
        if (ObjectUtils.allNotNull(event, super.eventPublisher)) {
            super.eventPublisher.publishEvent(event);
        }
        super.getSuccessHandler().onAuthenticationSuccess(request, response, authentication);
    }
}
