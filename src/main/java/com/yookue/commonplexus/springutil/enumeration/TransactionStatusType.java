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

package com.yookue.commonplexus.springutil.enumeration;


import org.springframework.transaction.support.TransactionSynchronization;
import com.yookue.commonplexus.javaseutil.support.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Enumerations of transaction status types
 *
 * @author David Hsing
 *
 * @see org.springframework.transaction.support.TransactionSynchronization
 */
@AllArgsConstructor
@Getter
@SuppressWarnings("unused")
public enum TransactionStatusType implements ValueEnum<Integer> {
    COMMITTED(TransactionSynchronization.STATUS_COMMITTED),
    ROLLED_BACK(TransactionSynchronization.STATUS_ROLLED_BACK),
    UNKNOWN(TransactionSynchronization.STATUS_UNKNOWN);

    private final Integer value;
}
