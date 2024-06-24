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
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import lombok.Getter;


/**
 * {@link org.springframework.security.authentication.event.LogoutSuccessEvent} with properties capable
 *
 * @author David Hsing
 * @see org.springframework.security.authentication.event.LogoutSuccessEvent
 */
@Getter
@SuppressWarnings("unused")
public class PropertiesLogoutSuccessEvent extends AbstractPropertiesAuthenticationEvent {
    public PropertiesLogoutSuccessEvent(@Nonnull LogoutSuccessEvent event) {
        super(event.getAuthentication());
    }

    public PropertiesLogoutSuccessEvent(@Nonnull LogoutSuccessEvent event, @Nullable Properties properties) {
        super(event.getAuthentication());
        MapPlainWraps.ifNotEmpty(properties, super.properties::putAll);
    }

    public PropertiesLogoutSuccessEvent(@Nonnull Authentication authentication) {
        super(authentication);
    }

    public PropertiesLogoutSuccessEvent(@Nonnull Authentication authentication, @Nullable Properties properties) {
        super(authentication);
        MapPlainWraps.ifNotEmpty(properties, super.properties::putAll);
    }
}
