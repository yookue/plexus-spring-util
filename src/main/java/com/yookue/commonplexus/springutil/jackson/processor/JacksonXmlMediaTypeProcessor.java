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

package com.yookue.commonplexus.springutil.jackson.processor;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import com.yookue.commonplexus.javaseutil.constant.HttpMimeConst;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;


/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor} for support extra xml media types
 * <p>
 * Supports "application/xml", "application/atom+xml", "application/problem+xml", "application/rss+xml", "application/xhtml+xml", "application/soap+xml", "application/*+xml"
 *
 * @author David Hsing
 * @reference "https://dzone.com/articles/customizing"
 * @reference "https://stackoverflow.com/questions/34172163/spring-boot-how-to-custom-httpmessageconverter"
 */
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class JacksonXmlMediaTypeProcessor implements BeanPostProcessor {
    @Override
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        if (!(bean instanceof MappingJackson2HttpMessageConverter instance)) {
            return bean;
        }
        MediaType soapXml = new MediaType(HttpMimeConst.APPLICATION, HttpMimeConst.SOAP_XML);
        MediaType starXml = new MediaType(HttpMimeConst.APPLICATION, HttpMimeConst.STAR_XML);
        List<MediaType> addTypes = Arrays.asList(MediaType.APPLICATION_XML, MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_PROBLEM_XML, MediaType.APPLICATION_RSS_XML, MediaType.APPLICATION_XHTML_XML, soapXml, starXml);
        List<MediaType> allTypes = new ArrayList<>();
        CollectionPlainWraps.addAllIfNotContains(allTypes, instance.getSupportedMediaTypes(), addTypes);
        instance.setSupportedMediaTypes(allTypes);
        return bean;
    }
}
