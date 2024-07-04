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
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.security.authentication.event.AuthenticationFailureProviderNotFoundEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import lombok.Getter;


/**
 * {@link org.springframework.security.authentication.event.AuthenticationFailureProviderNotFoundEvent} with properties capable
 *
 * @author David Hsing
 * @see org.springframework.security.authentication.event.AuthenticationFailureProviderNotFoundEvent
 */
@Getter
@SuppressWarnings("unused")
public class PropertiesAuthenticationFailureProviderNotFoundEvent extends AbstractPropertiesAuthenticationFailureEvent {
    public PropertiesAuthenticationFailureProviderNotFoundEvent(@Nonnull AuthenticationFailureProviderNotFoundEvent event) {
        super(event.getAuthentication(), event.getException());
    }

    public PropertiesAuthenticationFailureProviderNotFoundEvent(@Nonnull AuthenticationFailureProviderNotFoundEvent event, @Nullable Properties properties) {
        super(event.getAuthentication(), event.getException());
        MapPlainWraps.ifNotEmpty(properties, super.properties::putAll);
    }

	public PropertiesAuthenticationFailureProviderNotFoundEvent(@Nonnull Authentication authentication, @Nonnull AuthenticationException exception) {
		super(authentication, exception);
	}

    public PropertiesAuthenticationFailureProviderNotFoundEvent(@Nonnull Authentication authentication, @Nonnull AuthenticationException exception, @Nullable Properties properties) {
        super(authentication, exception);
        MapPlainWraps.ifNotEmpty(properties, super.properties::putAll);
    }
}
