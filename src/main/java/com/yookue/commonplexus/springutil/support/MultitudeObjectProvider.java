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

package com.yookue.commonplexus.springutil.support;


import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.ObjectProvider;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;


/**
 * Multitude elements for {@link org.springframework.beans.factory.ObjectProvider}
 *
 * @param <T> the type of the elements to hold
 *
 * @author David Hsing
 * @see org.springframework.beans.factory.support.StaticListableBeanFactory
 */
@SuppressWarnings("unused")
public class MultitudeObjectProvider<T> implements ObjectProvider<T> {
    private static final MultitudeObjectProvider<?> EMPTY = new MultitudeObjectProvider<>();
    private final T[] values;

    @SafeVarargs
    private MultitudeObjectProvider(@Nullable T... values) {
        this.values = values;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public static <T> MultitudeObjectProvider<T> empty() {
        return (MultitudeObjectProvider<T>) EMPTY;
    }

    @Nonnull
    @SafeVarargs
    public static <T> MultitudeObjectProvider<T> of(@Nonnull T... values) {
        return new MultitudeObjectProvider<>(values);
    }

    @Nonnull
    @SafeVarargs
    public static <T> MultitudeObjectProvider<T> ofNullable(@Nullable T... values) {
        return new MultitudeObjectProvider<>(values);
    }

    @Nonnull
    @Override
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public T getObject() throws BeansException {
        if (ArrayUtils.isEmpty(values)) {
            throw new FatalBeanException("No inside objects available");
        }
        return ArrayUtilsWraps.getFirst(values);
    }

    @Nonnull
    @Override
    public T getObject(@Nullable Object... args) throws BeansException {
        return getObject();
    }

    @Nullable
    @Override
    public T getIfAvailable() {
        return ArrayUtilsWraps.getFirst(values);
    }

    @Nullable
    @Override
    public T getIfUnique() {
        return (ArrayUtils.getLength(values) == 1) ? ArrayUtilsWraps.getFirst(values) : null;
    }

    public boolean isPresent() {
        return ArrayUtils.isNotEmpty(values);
    }

    public boolean isNotPresent() {
        return ArrayUtils.isEmpty(values);
    }

    @Nonnull
    @Override
    @SuppressWarnings({"DataFlowIssue", "RedundantSuppression"})
    public Stream<T> stream() {
        return ArrayUtils.isEmpty(values) ? Stream.empty() : Stream.of(values);
    }

    @Nonnull
    @Override
    public Stream<T> orderedStream() {
        return stream().sorted();
    }
}
