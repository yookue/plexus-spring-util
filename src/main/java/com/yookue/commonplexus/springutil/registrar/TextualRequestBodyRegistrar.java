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


import java.util.List;
import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.yookue.commonplexus.springutil.resolver.TextualRequestBodyResolver;


/**
 * Registrar of a {@link com.yookue.commonplexus.springutil.resolver.TextualRequestBodyResolver}
 *
 * @author David Hsing
 */
public class TextualRequestBodyRegistrar implements WebMvcConfigurer {
    @Bean
    @ConditionalOnMissingBean
    public TextualRequestBodyResolver textualRequestBodyResolver() {
        return new TextualRequestBodyResolver();
    }

    @Override
    public void addArgumentResolvers(@Nonnull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(textualRequestBodyResolver());
    }
}
