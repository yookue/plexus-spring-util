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
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;
import com.yookue.commonplexus.springutil.event.PropertiesCapableEvent;
import lombok.Getter;


/**
 * {@link org.springframework.security.authentication.event.AbstractAuthenticationEvent} with properties capable
 *
 * @author David Hsing
 * @see org.springframework.security.authentication.event.AbstractAuthenticationEvent
 */
@Getter
@SuppressWarnings("unused")
public abstract class AbstractPropertiesAuthenticationEvent extends AbstractAuthenticationEvent implements PropertiesCapableEvent {
    protected final Properties properties = new Properties();

    public AbstractPropertiesAuthenticationEvent(@Nonnull Authentication authentication) {
        super(authentication);
    }
}
