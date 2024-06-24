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

package com.yookue.commonplexus.springutil.audit;


import javax.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.yookue.commonplexus.springutil.constant.SpringBeanConst;


/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor} for rdbms audit handler
 *
 * @author David Hsing
 * @reference "https://stackoverflow.com/questions/22368414/how-can-i-customize-the-auditinghandler-injected-by-spring-data-when-using-audit"
 * @reference "https://github.com/macdao/customize-auditing-handler"
 * @see org.springframework.data.auditing.config.AuditingBeanDefinitionRegistrarSupport
 * @see org.springframework.data.mapping.context.MappingContext
 * @see org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
 * @see org.springframework.data.jpa.repository.config.AuditingBeanDefinitionParser
 * @see org.springframework.data.jpa.repository.config.JpaAuditingRegistrar
 */
@SuppressWarnings({"unused", "JavadocReference", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class RdbmsDataAuditProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(@Nonnull ConfigurableListableBeanFactory factory) throws BeansException {
        if (factory.containsBeanDefinition(SpringBeanConst.JPA_AUDITING_HANDLER)) {
            BeanDefinition definition = factory.getBeanDefinition(SpringBeanConst.JPA_AUDITING_HANDLER);
            definition.setBeanClassName(RdbmsDataAuditHandler.class.getName());
            definition.setFactoryMethodName(null);
        }
    }
}
