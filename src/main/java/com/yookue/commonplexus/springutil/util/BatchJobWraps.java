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

package com.yookue.commonplexus.springutil.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.function.Consumer;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;


/**
 * Utilities for Spring batch
 *
 * @author David Hsing
 * @see org.springframework.batch.core.JobParameters
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class BatchJobWraps {
    public static ExecutionContext getJobExecutionContext(@Nullable ChunkContext context) {
        return (context == null) ? null : context.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
    }

    public static ExecutionContext getStepExecutionContext(@Nullable ChunkContext context) {
        return (context == null) ? null : context.getStepContext().getStepExecution().getExecutionContext();
    }

    @Nonnull
    public static JobParameters newJobParameters(@Nullable Map<String, Object> params) {
        return newJobParametersBuilder(params).toJobParameters();
    }

    @Nonnull
    public static JobParametersBuilder newJobParametersBuilder(@Nullable Map<String, Object> params) {
        JobParametersBuilder builder = new JobParametersBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (StringUtils.isBlank(entry.getKey()) || entry.getValue() == null) {
                    continue;
                }
                if (entry.getValue() instanceof String alias) {
                    builder.addString(entry.getKey(), alias);
                } else if (entry.getValue() instanceof Integer alias) {
                    builder.addLong(entry.getKey(), alias.longValue());
                } else if (entry.getValue() instanceof Double alias) {
                    builder.addDouble(entry.getKey(), alias);
                } else if (entry.getValue() instanceof Long alias) {
                    builder.addLong(entry.getKey(), alias);
                } else if (entry.getValue() instanceof Date alias) {
                    builder.addDate(entry.getKey(), alias);
                } else if (entry.getValue() instanceof LocalDate alias) {
                    builder.addLocalDate(entry.getKey(), alias);
                } else if (entry.getValue() instanceof LocalTime alias) {
                    builder.addLocalTime(entry.getKey(), alias);
                } else if (entry.getValue() instanceof LocalDateTime alias) {
                    builder.addLocalDateTime(entry.getKey(), alias);
                } else {
                    builder.addJobParameter(entry.getKey(), entry.getValue(), Object.class);
                }
            }
        }
        return builder;
    }

    @Nonnull
    public static JobExecutionListener beforeJobExecutionListener(@Nullable Consumer<JobExecution> action) {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(@Nonnull JobExecution execution) {
                if (action != null) {
                    action.accept(execution);
                }
            }
        };
    }

    @Nonnull
    public static JobExecutionListener afterJobExecutionListener(@Nullable Consumer<JobExecution> action) {
        return new JobExecutionListener() {
            @Override
            public void afterJob(@Nonnull JobExecution execution) {
                if (action != null) {
                    action.accept(execution);
                }
            }
        };
    }
}
