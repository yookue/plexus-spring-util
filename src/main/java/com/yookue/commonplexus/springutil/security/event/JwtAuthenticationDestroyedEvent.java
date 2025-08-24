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

package com.yookue.commonplexus.springutil.security.event;


import jakarta.annotation.Nonnull;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;


/**
 * Event when the jwt authentication been destroyed
 *
 * @author David Hsing
 *
 * @see com.yookue.commonplexus.springutil.security.authentication.JwtAuthenticationToken
 */
@SuppressWarnings("unused")
public class JwtAuthenticationDestroyedEvent extends ApplicationEvent {
    public JwtAuthenticationDestroyedEvent(@Nonnull Authentication authentication) {
        super(authentication);
    }

    @Nonnull
    public Authentication getAuthentication() {
        return ObjectUtilsWraps.castAs(super.getSource(), Authentication.class);
    }
}
