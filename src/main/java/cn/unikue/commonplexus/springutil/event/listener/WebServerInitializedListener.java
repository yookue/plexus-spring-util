/*
 * Copyright (c) 2016 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.event.listener;


import jakarta.annotation.Nonnull;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;


/**
 * Listens event of {@link org.springframework.boot.web.context.WebServerInitializedEvent}
 *
 * @author David Hsing
 */
@Getter
@Setter
@Slf4j
public class WebServerInitializedListener<T extends WebServerInitializedEvent> extends AbstractApplicationEventListener<T> {
    private boolean logMessage = true;

    @Override
    protected void handleApplicationEvent(@Nonnull T event) {
        if (logMessage && log.isInfoEnabled()) {
            log.info("Application '{}' server initialized on {} ({}) with {}", super.getApplicationName(), super.getAccessUrl(), super.getPrivateUrl(), super.getContainer());
        }
    }
}
