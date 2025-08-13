/*
 * Copyright (c) 2025 Yookue Ltd. All rights reserved.
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

package com.yookue.commonplexus.springutil.aspect;


import jakarta.annotation.Nonnull;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.session.Session;
import org.springframework.session.web.http.SessionAdapterUtils;
import com.yookue.commonplexus.springutil.event.HttpSessionSavedEvent;
import com.yookue.commonplexus.springutil.util.ApplicationContextWraps;
import lombok.Setter;


/**
 * Aspect when {@link jakarta.servlet.http.HttpSession} saved
 *
 * @author David Hsing
 *
 * @see com.yookue.commonplexus.springutil.event.HttpSessionSavedEvent
 * @see "org.springframework.session.web.http.HttpSessionAdapter"
 */
@Aspect
public class SessionRepositoryAspect implements ApplicationContextAware, InitializingBean {
    @Setter
    private ApplicationContext applicationContext;

    private ServletContext servletContext;

    @Override
    public void afterPropertiesSet() {
        servletContext = ApplicationContextWraps.getServletContext(applicationContext);
    }

    @After("execution(* org.springframework.session.SessionRepository.save(..)) && args(session)")
    @SuppressWarnings("unused")
    public void afterSave(@Nonnull JoinPoint point, @Nonnull Session session) {
        HttpSession alias = SessionAdapterUtils.toHttpSession(session, servletContext);
        applicationContext.publishEvent(new HttpSessionSavedEvent(alias));
    }
}
