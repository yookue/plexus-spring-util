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

package com.yookue.commonplexus.springutil.processor;


import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.core.Ordered;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.UrlTemplateResolver;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor} for {@link org.thymeleaf.templateresolver.ITemplateResolver}
 *
 * @author David Hsing
 * @reference "https://ultraq.github.io/thymeleaf-layout-dialect/"
 */
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class ThymeleafTemplateResolverProcessor implements BeanPostProcessor, Ordered {
    private TemplateEngine engine;
    private ThymeleafProperties properties;
    private boolean beanProcessed = false;

    @Getter
    @Setter
    private int order = 0;

    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        if (beanProcessed) {
            return bean;
        }
        if (bean instanceof TemplateEngine) {
            engine = (TemplateEngine) bean;
        }
        if (bean instanceof ThymeleafProperties) {
            properties = (ThymeleafProperties) bean;
        }
        if (engine != null && properties != null) {
            UrlTemplateResolver resolver = new UrlTemplateResolver();
            resolver.setTemplateMode(properties.getMode());
            resolver.setCharacterEncoding(properties.getEncoding().name());
            resolver.setCacheable(properties.isCache());
            engine.addTemplateResolver(resolver);
            beanProcessed = true;
        }
        return bean;
    }
}
