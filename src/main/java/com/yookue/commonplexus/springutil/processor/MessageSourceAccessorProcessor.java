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


import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import com.yookue.commonplexus.springutil.context.MessageSourceAccessorAware;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor} for {@link com.yookue.commonplexus.springutil.context.MessageSourceAccessorAware}
 *
 * @author David Hsing
 * @see "org.springframework.context.support.ApplicationContextAwareProcessor"
 */
@SuppressWarnings("unused")
public class MessageSourceAccessorProcessor implements BeanPostProcessor, Ordered {
    private final ConfigurableApplicationContext applicationContext;
    private final MessageSourceAccessor messageAccessor;

    @Getter
    @Setter
    private int order = 0;

    public MessageSourceAccessorProcessor(@Nonnull ConfigurableApplicationContext context) {
        applicationContext = context;
        messageAccessor = new MessageSourceAccessor(applicationContext);
    }

    @SuppressWarnings("DataFlowIssue")
    public MessageSourceAccessorProcessor(@Nonnull ConfigurableApplicationContext context, @Nullable Locale defaultLocale) {
        applicationContext = context;
        messageAccessor = new MessageSourceAccessor(applicationContext, defaultLocale);
    }

    @Override
    public Object postProcessBeforeInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        if (!(bean instanceof MessageSourceAccessorAware)) {
            return bean;
        }
        AccessControlContext acc = (System.getSecurityManager() == null) ? null : applicationContext.getBeanFactory().getAccessControlContext();
        if (acc != null) {
            AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                invokeAwareInterfaces(bean);
                return null;
            }, acc);
        } else {
            invokeAwareInterfaces(bean);
        }
        return bean;
    }

    private void invokeAwareInterfaces(@Nonnull Object bean) {
        if (bean instanceof MessageSourceAccessorAware) {
            ((MessageSourceAccessorAware) bean).setMessageSourceAccessor(messageAccessor);
        }
    }
}
