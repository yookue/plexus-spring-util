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

package com.yookue.commonplexus.springutil.security.session;


import java.util.function.BiFunction;
import java.util.function.Function;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import org.springframework.security.core.session.AbstractSessionEvent;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.WebApplicationContext;
import com.yookue.commonplexus.springutil.security.event.PropertiesHttpSessionCreatedEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesHttpSessionDestroyedEvent;
import com.yookue.commonplexus.springutil.security.event.PropertiesHttpSessionIdChangedEvent;
import com.yookue.commonplexus.springutil.util.ApplicationContextWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link org.springframework.security.web.session.HttpSessionEventPublisher} that publishes {@link org.springframework.security.core.session.AbstractSessionEvent} to the container
 *
 * @author David Hsing
 * @see org.springframework.security.web.session.HttpSessionEventPublisher
 * @see org.springframework.security.core.session.AbstractSessionEvent
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class PropertiesHttpSessionEventPublisher extends HttpSessionEventPublisher {
    private Function<HttpSessionEvent, PropertiesHttpSessionCreatedEvent> sessionCreatedTransition;
    private Function<HttpSessionEvent, PropertiesHttpSessionDestroyedEvent> sessionDestroyedTransition;
    private BiFunction<HttpSessionEvent, String, PropertiesHttpSessionIdChangedEvent> sessionIdChangedTransition;

    @Override
    public void sessionCreated(@Nonnull HttpSessionEvent event) {
        AbstractSessionEvent transition = (sessionCreatedTransition != null) ? sessionCreatedTransition.apply(event) : new PropertiesHttpSessionCreatedEvent(event.getSession());
        publishEvent(event.getSession(), transition);
    }

    @Override
    public void sessionDestroyed(@Nonnull HttpSessionEvent event) {
        AbstractSessionEvent transition = (sessionDestroyedTransition != null) ? sessionDestroyedTransition.apply(event): new PropertiesHttpSessionDestroyedEvent(event.getSession());
        publishEvent(event.getSession(), transition);
    }

    @Override
    public void sessionIdChanged(@Nonnull HttpSessionEvent event, @Nullable String previousId) {
        AbstractSessionEvent transition = (sessionIdChangedTransition != null) ? sessionIdChangedTransition.apply(event, previousId) : new PropertiesHttpSessionIdChangedEvent(event.getSession(), previousId);
        publishEvent(event.getSession(), transition);
    }

    protected void publishEvent(@Nonnull HttpSession session, @Nullable AbstractSessionEvent event) {
        if (event == null) {
            return;
        }
        WebApplicationContext context = ApplicationContextWraps.getWebApplicationContext(session.getServletContext());
        if (context != null) {
            context.publishEvent(event);
        }
    }
}
