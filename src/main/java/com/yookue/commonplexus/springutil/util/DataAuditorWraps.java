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


import java.util.Optional;
import javax.annotation.Nullable;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.AuditorAware;
import com.yookue.commonplexus.javaseutil.util.FieldUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.OptionalPlainWraps;


/**
 * Utilities for Spring DATA JPA {@link org.springframework.data.auditing.Auditor}
 *
 * @author David Hsing
 * @see org.springframework.data.domain.AuditorAware
 * @see org.springframework.data.auditing.AuditingHandler
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue", "JavadocReference"})
public abstract class DataAuditorWraps {
    @Nullable
    public static AuditorAware<?> getAuditorAware(@Nullable AuditingHandler handler) {
        if (handler == null) {
            return null;
        }
        Optional<?> value = FieldUtilsWraps.readDeclaredFieldAs(handler, "auditorAware", true, Optional.class);    // $NON-NLS-1$
        return OptionalPlainWraps.unwrapAs(value, AuditorAware.class);
    }
}
