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

package com.yookue.commonplexus.springutil.security.event;


import java.util.Properties;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpSession;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import lombok.Getter;


/**
 * {@link org.springframework.security.web.session.HttpSessionCreatedEvent} with properties capable
 *
 * @author David Hsing
 * @see org.springframework.security.web.session.HttpSessionCreatedEvent
 */
@Getter
@SuppressWarnings("unused")
public class PropertiesHttpSessionCreatedEvent extends HttpSessionCreatedEvent {
    private final Properties properties = new Properties();

    public PropertiesHttpSessionCreatedEvent(@Nonnull HttpSessionCreatedEvent event) {
        super(event.getSession());
    }

    public PropertiesHttpSessionCreatedEvent(@Nonnull HttpSessionCreatedEvent event, @Nullable Properties properties) {
        super(event.getSession());
        MapPlainWraps.ifNotEmpty(properties, this.properties::putAll);
    }

    public PropertiesHttpSessionCreatedEvent(@Nonnull HttpSession session) {
        super(session);
    }

    public PropertiesHttpSessionCreatedEvent(@Nonnull HttpSession session, @Nullable Properties properties) {
        super(session);
        MapPlainWraps.ifNotEmpty(properties, this.properties::putAll);
    }
}