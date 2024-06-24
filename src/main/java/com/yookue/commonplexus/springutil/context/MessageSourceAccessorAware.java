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

package com.yookue.commonplexus.springutil.context;


import javax.annotation.Nonnull;
import org.springframework.beans.factory.Aware;
import org.springframework.context.support.MessageSourceAccessor;


/**
 * {@link org.springframework.beans.factory.Aware} for injecting a {@link org.springframework.context.support.MessageSourceAccessor} for beans
 *
 * @author David Hsing
 * @see org.springframework.context.MessageSourceAware
 * @see com.yookue.commonplexus.springutil.processor.MessageSourceAccessorProcessor
 */
public interface MessageSourceAccessorAware extends Aware {
    void setMessageSourceAccessor(@Nonnull MessageSourceAccessor accessor);
}