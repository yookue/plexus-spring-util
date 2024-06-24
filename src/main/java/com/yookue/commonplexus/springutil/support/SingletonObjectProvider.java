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
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.ObjectProvider;


/**
 * Singleton element for {@link org.springframework.beans.factory.ObjectProvider}
 *
 * @param <T> the type of the element to hold
 *
 * @author David Hsing
 * @see org.springframework.util.function.SingletonSupplier
 * @see org.springframework.beans.factory.support.StaticListableBeanFactory
 */
@SuppressWarnings("unused")
public class SingletonObjectProvider<T> implements ObjectProvider<T> {
    private static final SingletonObjectProvider<?> EMPTY = new SingletonObjectProvider<>(null);
    private final T value;

    private SingletonObjectProvider(@Nullable T value) {
        this.value = value;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public static <T> SingletonObjectProvider<T> empty() {
        return (SingletonObjectProvider<T>) EMPTY;
    }

    @Nonnull
    public static <T> SingletonObjectProvider<T> of(@Nonnull T value) {
        return new SingletonObjectProvider<>(value);
    }

    @Nonnull
    public static <T> SingletonObjectProvider<T> ofNullable(@Nullable T value) {
        return new SingletonObjectProvider<>(value);
    }

    @Nonnull
    @Override
    public T getObject() throws BeansException {
        if (value == null) {
            throw new FatalBeanException("No inside object available");
        }
        return value;
    }

    @Nonnull
    @Override
    public T getObject(@Nullable Object... args) throws BeansException {
        return getObject();
    }

    @Nullable
    @Override
    public T getIfAvailable() {
        return value;
    }

    @Nullable
    @Override
    public T getIfUnique() {
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public boolean isNotPresent() {
        return !isPresent();
    }

    @Nonnull
    @Override
    public Stream<T> stream() {
        return (value == null) ? Stream.empty() : Stream.of(value);
    }

    @Nonnull
    @Override
    public Stream<T> orderedStream() {
        return stream();
    }
}
