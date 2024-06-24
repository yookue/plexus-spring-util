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
import org.springframework.security.web.session.HttpSessionIdChangedEvent;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;
import lombok.Getter;


/**
 * {@link org.springframework.security.web.session.HttpSessionIdChangedEvent} with properties capable
 *
 * @author David Hsing
 * @see org.springframework.security.web.session.HttpSessionIdChangedEvent
 */
@Getter
@SuppressWarnings("unused")
public class PropertiesHttpSessionIdChangedEvent extends HttpSessionIdChangedEvent {
    private final Properties properties = new Properties();

    public PropertiesHttpSessionIdChangedEvent(@Nonnull HttpSessionIdChangedEvent event) {
        super(ObjectUtilsWraps.castAs(event.getSource(), HttpSession.class), event.getOldSessionId());
    }

    public PropertiesHttpSessionIdChangedEvent(@Nonnull HttpSessionIdChangedEvent event, @Nullable Properties properties) {
        super(ObjectUtilsWraps.castAs(event.getSource(), HttpSession.class), event.getOldSessionId());
        MapPlainWraps.ifNotEmpty(properties, this.properties::putAll);
    }

    public PropertiesHttpSessionIdChangedEvent(@Nonnull HttpSession session, @Nullable String previousId) {
        super(session, previousId);
    }

    public PropertiesHttpSessionIdChangedEvent(@Nonnull HttpSession session, @Nullable String previousId, @Nullable Properties properties) {
        super(session, previousId);
        MapPlainWraps.ifNotEmpty(properties, this.properties::putAll);
    }
}
