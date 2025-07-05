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
import com.yookue.commonplexus.javaseutil.converter.apache.JdkDateConverter;
import com.yookue.commonplexus.javaseutil.converter.apache.LocalDateConverter;
import com.yookue.commonplexus.javaseutil.converter.apache.LocalDateTimeConverter;
import com.yookue.commonplexus.javaseutil.converter.apache.LocalTimeConverter;
import com.yookue.commonplexus.javaseutil.converter.apache.SqlDateConverter;
import com.yookue.commonplexus.javaseutil.util.BeanUtilsWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * Registers object converters for apache bean utils
 *
 * @author David Hsing
 */
@Getter
@Setter
public class ApacheBeanConverterListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        BeanUtilsWraps.registerNullConverter();
        BeanUtilsWraps.registerClassConverter(java.util.Date.class, new JdkDateConverter());
        BeanUtilsWraps.registerClassConverter(java.sql.Date.class, new SqlDateConverter());
        BeanUtilsWraps.registerClassConverter(java.time.LocalDate.class, new LocalDateConverter());
        BeanUtilsWraps.registerClassConverter(java.time.LocalDateTime.class, new LocalDateTimeConverter());
        BeanUtilsWraps.registerClassConverter(java.time.LocalTime.class, new LocalTimeConverter());
    }
}
