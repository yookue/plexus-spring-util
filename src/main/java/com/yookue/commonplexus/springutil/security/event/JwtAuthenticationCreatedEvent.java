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
import jakarta.annotation.Nullable;
import org.springframework.context.ApplicationEvent;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;
import com.yookue.commonplexus.springutil.security.authentication.JwtAuthenticationToken;
import lombok.Getter;


/**
 * Event when the jwt authentication been created
 *
 * @author David Hsing
 *
 * @see com.yookue.commonplexus.springutil.security.authentication.JwtAuthenticationToken
 */
@Getter
@SuppressWarnings("unused")
public class JwtAuthenticationCreatedEvent extends ApplicationEvent {
    public JwtAuthenticationCreatedEvent(@Nonnull JwtAuthenticationToken token) {
        super(token);
    }

    public JwtAuthenticationCreatedEvent(@Nonnull String token) {
        super(token);
    }

    @Nullable
    public JwtAuthenticationToken getRawSourceAsToken() {
        return !(super.getSource() instanceof JwtAuthenticationToken) ? null : ObjectUtilsWraps.castAs(super.getSource(), JwtAuthenticationToken.class);
    }

    @Nullable
    public String getRawSourceAsString() {
        return !(super.getSource() instanceof String) ? null : ObjectUtilsWraps.castAs(super.getSource(), String.class);
    }
}
