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
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationEvent;
import com.yookue.commonplexus.javaseutil.util.ObjectUtilsWraps;


@SuppressWarnings("unused")
public class HttpSessionIdledEvent extends ApplicationEvent {
    public HttpSessionIdledEvent(@Nonnull HttpSession session) {
        super(session);
    }

    public HttpSession getSession() {
        return ObjectUtilsWraps.castAs(super.getSource(), HttpSession.class);
    }
}
