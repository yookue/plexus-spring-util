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


import java.util.Properties;
import javax.annotation.Nonnull;
import org.springframework.context.ApplicationEvent;
import lombok.Getter;


/**
 * Abstract application event with properties capable
 *
 * @author David Hsing
 */
@Getter
@SuppressWarnings("unused")
public abstract class AbstractPropertiesCapableEvent extends ApplicationEvent implements PropertiesCapableEvent {
    protected final Properties properties = new Properties();

    public AbstractPropertiesCapableEvent(@Nonnull Object source) {
        super(source);
    }
}
