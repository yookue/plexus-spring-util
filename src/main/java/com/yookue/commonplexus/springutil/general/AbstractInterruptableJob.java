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

package com.yookue.commonplexus.springutil.general;


import org.quartz.InterruptableJob;
import org.springframework.scheduling.quartz.QuartzJobBean;
import lombok.Getter;


/**
 * Abstract interruptable job for Spring quartz
 *
 * @author David Hsing
 * @see org.quartz.InterruptableJob
 * @see org.springframework.scheduling.quartz.QuartzJobBean
 */
@Getter
@SuppressWarnings("unused")
public abstract class AbstractInterruptableJob extends QuartzJobBean implements InterruptableJob {
    private boolean interrupted = false;

    @Override
    public void interrupt() {
        interrupted = true;
    }
}
