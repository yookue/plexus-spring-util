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


import java.util.Map;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationEvent;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;
import lombok.Getter;


/**
 * Event when {@link jakarta.servlet.http.HttpSession} saved
 *
 * @author David Hsing
 */
@Getter
@SuppressWarnings("unused")
public class HttpSessionSavedEvent extends ApplicationEvent {
    private final Map<String, Object> changedAttributes;

    public HttpSessionSavedEvent(@Nonnull HttpSession session) {
        this(session, null);
    }

    public HttpSessionSavedEvent(@Nonnull HttpSession session, @Nullable Map<String, Object> changedAttributes) {
        super(session);
        this.changedAttributes = changedAttributes;
    }

    @Nonnull
    public HttpSession getRawSource() {
        return ObjectUtilsWraps.castAs(super.getSource(), HttpSession.class);
    }
}
