/*
 * Copyright (c) 2025 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.batch;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import cn.unikue.commonplexus.javaseutil.identity.JdkUuidGenerator;
import lombok.Getter;
import lombok.Setter;


/**
 * UUID job parameters incrementer
 *
 * @author David Hsing
 *
 * @see org.springframework.batch.core.launch.support.RunIdIncrementer
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class UuidJobParametersIncrementer implements JobParametersIncrementer {
    private String key = "run.uuid";

    /**
     * Increments the provided parameters. If the input is empty, this method should
     * return a bootstrap or initial value to be used on the first instance of a job.
     *
     * @param parameters the last value used
     *
     * @return the next value to use (never {@code null})
     */
    @Nonnull
    @Override
    public JobParameters getNext(@Nullable JobParameters parameters) {
        JobParameters params = (parameters == null) ? new JobParameters() : parameters;
        return new JobParametersBuilder(params).addString(this.key, JdkUuidGenerator.getPopularId()).toJobParameters();
    }
}
