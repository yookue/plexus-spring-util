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


import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.ObjectProvider;
import com.yookue.commonplexus.javaseutil.util.IteratorPlainWraps;


/**
 * Utilities for {@link org.springframework.beans.factory.ObjectProvider}
 *
 * @author David Hsing
 * @see org.springframework.beans.factory.ObjectProvider
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ObjectProviderWraps {
    @Nullable
    public static <E> E getIfAvailable(@Nullable ObjectProvider<E> provider) {
        return getIfAvailable(provider, null);
    }

    @Nullable
    public static <E> E getIfAvailable(@Nullable ObjectProvider<E> provider, @Nullable Supplier<E> supplier) {
        if (provider == null) {
            return null;
        }
        try {
            return (supplier == null) ? provider.getIfAvailable() : provider.getIfAvailable(supplier);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <E> E getIfUnique(@Nullable ObjectProvider<E> provider) {
        return getIfUnique(provider, null);
    }

    @Nullable
    public static <E> E getIfUnique(@Nullable ObjectProvider<E> provider, @Nullable Supplier<E> supplier) {
        if (provider == null) {
            return null;
        }
        try {
            return (supplier == null) ? provider.getIfUnique() : provider.getIfUnique(supplier);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static <E> Iterator<E> getIterator(@Nullable ObjectProvider<E> provider) {
        try {
            return (provider == null) ? null : provider.iterator();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <E, T extends ObjectProvider<E>> void ifAvailable(@Nullable T provider, @Nullable Consumer<E> action) {
        ifAvailable(provider, action, null);
    }

    public static <E, T extends ObjectProvider<E>> void ifAvailable(@Nullable T provider, @Nullable Consumer<E> action, @Nullable Predicate<E> filter) {
        if (provider == null || action == null) {
            return;
        }
        E value = getIfAvailable(provider);
        if (value != null && (filter == null || filter.test(value))) {
            action.accept(value);
        }
    }

    public static <T extends ObjectProvider<?>> void ifAvailable(@Nullable T provider, @Nullable Runnable action) {
        if (isAvailable(provider) && action != null) {
            action.run();
        }
    }

    /**
     * If a value is available, performs the given action with the value, otherwise performs the given empty-based action
     *
     * @param presentAction the action to be performed, if a value is available
     * @param absentAction the empty-based action to be performed, if no value is available
     */
    public static <E, T extends ObjectProvider<E>> void ifAvailableOrElse(@Nullable T provider, @Nullable Consumer<E> presentAction, @Nullable Runnable absentAction) {
        E value = getIfAvailable(provider);
        if (value != null) {
              if (presentAction != null) {
                 presentAction.accept(value);
             }
        } else {
             if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    public static <T extends ObjectProvider<?>> void ifAvailableOrElse(@Nullable T provider, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isAvailable(provider)) {
             if (presentAction != null) {
                presentAction.run();
            }
        } else {
             if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    public static <T extends ObjectProvider<?>> void ifNotAvailable(@Nullable T provider, @Nullable Runnable action) {
        if (!isAvailable(provider) && action != null) {
            action.run();
        }
    }

    public static <E, T extends ObjectProvider<E>> void ifMultitude(@Nullable T provider, @Nullable Consumer<Iterator<E>> action) {
        ifMultitudeOrElse(provider, action, null);
    }

    public static <E, T extends ObjectProvider<E>> void ifMultitude(@Nullable T provider, @Nullable Runnable action) {
        ifMultitudeOrElse(provider, action, null);
    }

    public static <E, T extends ObjectProvider<E>> void ifMultitudeOrElse(@Nullable T provider, @Nullable Consumer<Iterator<E>> presentAction, @Nullable Consumer<E> absentAction) {
        if (provider == null) {
            return;
        }
        if (isMultitude(provider)) {
             if (presentAction != null) {
                presentAction.accept(provider.iterator());
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(getIfAvailable(provider));
            }
        }
    }

    public static <E, T extends ObjectProvider<E>> void ifMultitudeOrElse(@Nullable T provider, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (provider == null) {
            return;
        }
        if (isMultitude(provider)) {
             if (presentAction != null) {
                presentAction.run();
            }
        } else {
             if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    public static <E, T extends ObjectProvider<E>> void ifUnique(@Nullable T provider, @Nullable Consumer<E> action) {
        ifUnique(provider, action, null);
    }

    public static <E, T extends ObjectProvider<E>> void ifUnique(@Nullable T provider, @Nullable Consumer<E> action, @Nullable Predicate<E> filter) {
        if (provider == null || action == null) {
            return;
        }
        E value = getIfUnique(provider);
        if (value != null && (filter == null || filter.test(value))) {
            action.accept(value);
        }
    }

    public static <T extends ObjectProvider<?>> void ifUnique(@Nullable T provider, @Nullable Runnable action) {
        if (isUnique(provider) && action != null) {
            action.run();
        }
    }

    /**
     * If a value is unique, performs the given action with the value, otherwise performs the given empty-based action
     *
     * @param presentAction the action to be performed, if a value is unique
     * @param absentAction the empty-based action to be performed, if no value is unique
     */
    public static <E, T extends ObjectProvider<E>> void ifUniqueOrElse(@Nullable T provider, @Nullable Consumer<E> presentAction, @Nullable Consumer<Iterator<E>> absentAction) {
        E value = getIfUnique(provider);
        if (value != null) {
             if (presentAction != null) {
                presentAction.accept(value);
            }
        } else {
             if (absentAction != null) {
                absentAction.accept(getIterator(provider));
            }
        }
    }

    public static <T extends ObjectProvider<?>> void ifUniqueOrElse(@Nullable T provider, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isUnique(provider)) {
             if (presentAction != null) {
                presentAction.run();
            }
        } else {
             if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    public static <E, T extends ObjectProvider<E>> void ifNotUnique(@Nullable T provider, @Nullable Consumer<T> action) {
        if (!isUnique(provider) && action != null) {
            action.accept(provider);
        }
    }

    public static <T extends ObjectProvider<?>> void ifNotUnique(@Nullable T provider, @Nullable Runnable action) {
        if (!isUnique(provider) && action != null) {
            action.run();
        }
    }

    public static boolean isAvailable(@Nullable ObjectProvider<?> provider) {
        if (provider == null) {
            return false;
        }
        try {
            return provider.getIfAvailable() != null;
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean isEmpty(@Nullable ObjectProvider<?> provider) {
        try {
            return provider == null || IteratorPlainWraps.isEmpty(provider.iterator());
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean isNotEmpty(@Nullable ObjectProvider<?> provider) {
        return !isEmpty(provider);
    }

    public static boolean isMultitude(@Nullable ObjectProvider<?> provider) {
        if (provider == null) {
            return false;
        }
        try {
            return IteratorPlainWraps.isMultitude(provider.iterator());
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean isUnique(@Nullable ObjectProvider<?> provider) {
        if (provider == null) {
            return false;
        }
        try {
            return provider.getIfUnique() != null;
        } catch (Exception ignored) {
        }
        return false;
    }
}
