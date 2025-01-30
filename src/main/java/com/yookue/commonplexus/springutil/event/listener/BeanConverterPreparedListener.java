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

package com.yookue.commonplexus.springutil.event.listener;


import jakarta.annotation.Nonnull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.yookue.commonplexus.javaseutil.converter.LocalDateConverter;
import com.yookue.commonplexus.javaseutil.converter.LocalDateTimeConverter;
import com.yookue.commonplexus.javaseutil.converter.LocalTimeConverter;
import com.yookue.commonplexus.javaseutil.converter.SqlDateConverter;
import com.yookue.commonplexus.javaseutil.converter.UtilDateConverter;
import com.yookue.commonplexus.javaseutil.util.ConverterUtilsWraps;
import com.yookue.commonplexus.springutil.converter.BeanStringConverter;
import com.yookue.commonplexus.springutil.event.BeanConverterRegisteredEvent;
import lombok.Getter;
import lombok.Setter;


/**
 * Registers object converters for apache bean utils and publishes a {@link com.yookue.commonplexus.springutil.event.BeanConverterRegisteredEvent}
 *
 * @author David Hsing
 */
@Getter
@Setter
public class BeanConverterPreparedListener implements ApplicationListener<ContextRefreshedEvent> {
    private boolean publishEvent = true;

    @Override
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        ConverterUtilsWraps.registerNullConverter();
        ConverterUtilsWraps.registerClassConverter(String.class, new BeanStringConverter());
        ConverterUtilsWraps.registerClassConverter(java.util.Date.class, new UtilDateConverter());
        ConverterUtilsWraps.registerClassConverter(java.sql.Date.class, new SqlDateConverter());
        ConverterUtilsWraps.registerClassConverter(java.time.LocalDate.class, new LocalDateConverter());
        ConverterUtilsWraps.registerClassConverter(java.time.LocalDateTime.class, new LocalDateTimeConverter());
        ConverterUtilsWraps.registerClassConverter(java.time.LocalTime.class, new LocalTimeConverter());
        if (publishEvent) {
            event.getApplicationContext().publishEvent(new BeanConverterRegisteredEvent(event.getApplicationContext()));
        }
    }
}
