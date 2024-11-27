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
 * {@link org.springframework.beans.factory.config.BeanPostProcessor} for support of extra json media types
 * <p>
 * Supports "application/json", "application/problem+json", "application/x-ndjson", "application/soap+json", "application/*+json"
 *
 * @author David Hsing
 * @see "https://dzone.com/articles/customizing"
 * @see "https://stackoverflow.com/questions/34172163/spring-boot-how-to-custom-httpmessageconverter"
 */
@SuppressWarnings("unused")
public class JacksonJsonMediaTypeProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        if (!(bean instanceof MappingJackson2HttpMessageConverter alias)) {
            return bean;
        }
        MediaType soapJson = new MediaType(HttpMimeConst.APPLICATION, HttpMimeConst.SOAP_JSON);
        MediaType starJson = new MediaType(HttpMimeConst.APPLICATION, HttpMimeConst.STAR_JSON);
        List<MediaType> addTypes = Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_PROBLEM_JSON, MediaType.APPLICATION_NDJSON, soapJson, starJson);
        List<MediaType> allTypes = new ArrayList<>();
        CollectionPlainWraps.addAllIfNotContains(allTypes, alias.getSupportedMediaTypes(), addTypes);
        alias.setSupportedMediaTypes(allTypes);
        return bean;
    }
}
