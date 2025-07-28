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


import java.util.function.Consumer;
import jakarta.annotation.Nullable;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * Utilities for {@link org.springframework.transaction.support.TransactionSynchronization}
 *
 * @author David Hsing
 *
 * @see org.springframework.transaction.support.TransactionSynchronization
 * @see org.springframework.transaction.support.TransactionSynchronizationUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class TransactionSyncWraps {
    /**
     * Trigger callback after transaction commit
     *
     * @param callback The callback function to be triggered
     * 
     * @see org.springframework.transaction.support.TransactionSynchronization#afterCommit
     */
    public static void triggerAfterCommit(@Nullable Runnable callback) {
        if (callback == null) {
            return;
        }
        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    callback.run();
                }
            }
        );
    }

    /**
     * Trigger callback after transaction commit/rollback
     *
     * @param callback The callback function to be triggered, the generic type source is {@link com.yookue.commonplexus.springutil.enumeration.TransactionStatusType}
     * 
     * @see org.springframework.transaction.support.TransactionSynchronization#afterCompletion
     * @see com.yookue.commonplexus.springutil.enumeration.TransactionStatusType
     */
    public static void triggerAfterCompletion(@Nullable Consumer<Integer> callback) {
        if (callback == null) {
            return;
        }
        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    callback.accept(status);
                }
            }
        );
    }

    /**
     * Trigger callback before transaction commit
     *
     * @param callback The callback function to be triggered, the generic type source is `readonly`, indicates whether the transaction is defined as read-only transaction
     * 
     * @see org.springframework.transaction.support.TransactionSynchronization#beforeCommit
     */
    public static void triggerBeforeCommit(@Nullable Consumer<Boolean> callback) {
        if (callback == null) {
            return;
        }
        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronization() {
                @Override
                public void beforeCommit(boolean readonly) {
                    callback.accept(readonly);
                }
            }
        );
    }

    /**
     * Trigger callback before transaction commit/rollback
     *
     * @param callback The callback function to be triggered
     * 
     * @see org.springframework.transaction.support.TransactionSynchronization#beforeCompletion
     */
    public static void triggerBeforeCompletion(@Nullable Runnable callback) {
        if (callback == null) {
            return;
        }
        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronization() {
                @Override
                public void beforeCompletion() {
                    callback.run();
                }
            }
        );
    }
}
