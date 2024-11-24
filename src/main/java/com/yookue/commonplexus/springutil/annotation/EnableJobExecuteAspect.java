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

package com.yookue.commonplexus.springutil.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation for marking a method need to be processed by aop
 * <p>
 * Expectations:
 * <ul>
 *     <li>Classes implements {@link org.quartz.Job} and annotated this on the method which overrides {@link org.quartz.Job#execute(org.quartz.JobExecutionContext)}</li>
 *     <li>Classes extends {@link org.springframework.scheduling.quartz.QuartzJobBean} and annotated this on the method which overrides {@link org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)}</li>
 * </ul>
 *
 * @author David Hsing
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
@SuppressWarnings({"unused", "JavadocReference"})
public @interface EnableJobExecuteAspect {
}
