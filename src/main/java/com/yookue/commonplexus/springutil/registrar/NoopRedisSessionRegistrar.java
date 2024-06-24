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

package com.yookue.commonplexus.springutil.registrar;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.ConfigureRedisAction;


/**
 * Registrar of a {@link org.springframework.session.data.redis.config.ConfigureRedisAction} with no operations
 *
 * @author David Hsing
 * @reference "http://www.jianshu.com/p/5d6c7772ad87"
 */
@SuppressWarnings({"JavadocDeclaration", "JavadocLinkAsPlainText"})
public class NoopRedisSessionRegistrar {
    @Bean
    @ConditionalOnMissingBean
    @SuppressWarnings("SameReturnValue")
    public ConfigureRedisAction noopConfigureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
