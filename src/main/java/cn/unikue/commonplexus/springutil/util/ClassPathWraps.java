/*
 * Copyright (c) 2016 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.util;


import java.io.InputStream;
import java.nio.charset.Charset;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ClassPathUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import cn.unikue.commonplexus.javaseutil.constant.StringVariantConst;
import cn.unikue.commonplexus.javaseutil.util.IoUtilsWraps;
import cn.unikue.commonplexus.javaseutil.util.StringUtilsWraps;


/**
 * Utilities for {@link org.apache.commons.lang3.ClassPathUtils}
 *
 * @author David Hsing
 *
 * @see org.apache.commons.lang3.ClassPathUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ClassPathWraps {
    public static boolean existsResource(@Nullable String path) {
        return existsResource(path, null);
    }

    public static boolean existsResource(@Nullable String path, @Nullable ClassLoader loader) {
        String pathToUse = removeClasspathPrefix(path);
        return StringUtils.isNotBlank(pathToUse) && new ClassPathResource(pathToUse, loader).exists();
    }

    @Nullable
    public static InputStream readResourceToInputStream(@Nullable String path, @Nullable ClassLoader loader) {
        String pathToUse = removeClasspathPrefix(path);
        ClassPathResource resource = new ClassPathResource(pathToUse, loader);
        try {
            return resource.getInputStream();
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static byte[] readResourceToByteArray(@Nullable String path, @Nullable ClassLoader loader) {
        try (InputStream stream = readResourceToInputStream(path, loader)) {
            return IoUtilsWraps.toByteArray(stream);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static String readResourceToString(@Nullable String path, @Nullable ClassLoader loader, @Nullable Charset charset) {
        try (InputStream stream = readResourceToInputStream(path, loader)) {
            return IoUtilsWraps.toString(stream, charset);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return the path content after the prefix of 'classpath:' or 'classpath*:'
     *
     * @param path The absolute path within the class path
     *
     * @return the path content after the prefix of 'classpath:' or 'classpath*:'
     */
    public static String removeClasspathPrefix(@Nullable String path) {
        return StringUtilsWraps.removeStartIgnoreCase(path, StringVariantConst.CLASSPATH_COLON, StringVariantConst.CLASSPATH_STAR_COLON);
    }

    public static boolean startsWithAnyClasspath(@Nullable String path) {
        return startsWithClasspath(path) || startsWithClasspathStar(path);
    }

    public static boolean startsWithClasspath(@Nullable String path) {
        return StringUtils.startsWithIgnoreCase(path, StringVariantConst.CLASSPATH_COLON);
    }

    public static boolean startsWithClasspathStar(@Nullable String path) {
        return StringUtils.startsWithIgnoreCase(path, StringVariantConst.CLASSPATH_STAR_COLON);
    }

    @Nullable
    public static String toFullyQualifiedName(@Nullable Class<?> context, @Nullable String resourceName) {
        return (context == null || StringUtils.isBlank(resourceName)) ? null : ClassPathUtils.toFullyQualifiedName(context, resourceName);
    }

    @Nullable
    public static String toFullyQualifiedName(@Nullable Package context, @Nullable String resourceName) {
        return (context == null || StringUtils.isBlank(resourceName)) ? null : ClassPathUtils.toFullyQualifiedName(context, resourceName);
    }

    @Nullable
    public static String toFullyQualifiedPath(@Nullable Class<?> context, @Nullable String resourceName) {
        return (context == null || StringUtils.isBlank(resourceName)) ? null : ClassPathUtils.toFullyQualifiedPath(context, resourceName);
    }

    @Nullable
    public static String toFullyQualifiedPath(@Nullable Package context, @Nullable String resourceName) {
        return (context == null || StringUtils.isBlank(resourceName)) ? null : ClassPathUtils.toFullyQualifiedPath(context, resourceName);
    }
}
