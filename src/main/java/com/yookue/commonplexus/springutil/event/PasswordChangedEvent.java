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

package com.yookue.commonplexus.springutil.event;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEvent;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;
import lombok.Getter;


/**
 * Event when cipher is changed
 *
 * @author David Hsing
 */
@Getter
@SuppressWarnings("unused")
public class PasswordChangedEvent extends ApplicationEvent {
    private String password;

    public PasswordChangedEvent(@Nonnull HttpServletRequest request) {
        super(request);
    }

    public PasswordChangedEvent(@Nonnull HttpServletRequest request, @Nullable String password) {
        super(request);
        this.password = password;
    }

    @Nonnull
    public HttpServletRequest getRawSource() {
        return ObjectUtilsWraps.castAs(super.getSource(), HttpServletRequest.class);
    }
}
