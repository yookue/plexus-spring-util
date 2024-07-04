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


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;


/**
 * Utilities for configuring {@link org.apache.logging.log4j.core.LoggerContext}
 *
 * @author David Hsing
 * @see org.apache.logging.log4j.core.LoggerContext
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class Log4jConfigWraps {
    @Nullable
    public static LoggerContext initContextByClassPath(@Nullable String xmlPath) throws IOException {
        return initContextByClassPath(xmlPath, null);
    }

    @Nullable
    public static LoggerContext initContextByClassPath(@Nullable String xmlPath, @Nullable ClassLoader loader) throws IOException {
        if (StringUtils.isBlank(xmlPath)) {
            return null;
        }
        ClassPathResource resource = new ClassPathResource(xmlPath);
        if (resource.exists()) {
            ConfigurationSource source = new ConfigurationSource(resource.getInputStream());
            return Configurator.initialize(ObjectUtils.defaultIfNull(loader, ClassUtils.getDefaultClassLoader()), source);
        }
        return null;
    }

    @Nullable
    public static LoggerContext initContextByClassPathQuietly(@Nullable String xmlPath) {
        return initContextByClassPathQuietly(xmlPath, null);
    }

    @Nullable
    public static LoggerContext initContextByClassPathQuietly(@Nullable String xmlPath, @Nullable ClassLoader loader) {
        try {
            return initContextByClassPath(xmlPath, loader);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static LoggerContext initContextByFile(@Nullable File xmlFile) throws IOException {
        return initContextByFile(xmlFile, null);
    }

    @Nullable
    public static LoggerContext initContextByFile(@Nullable File xmlFile, @Nullable ClassLoader loader) throws IOException {
        if (xmlFile == null || !xmlFile.exists() || !xmlFile.isFile() || xmlFile.canRead()) {
            return null;
        }
        try (InputStream stream = Files.newInputStream(xmlFile.toPath())) {
            return initContextByStream(stream, loader);
        }
    }

    public static LoggerContext initContextByFile(@Nullable String xmlPath) throws IOException {
        return initContextByFile(xmlPath, null);
    }

    /**
     * @reference "http://blog.csdn.net/yucaifu1989/article/details/48178281"
     */
    @Nullable
    @SuppressWarnings({"JavadocDeclaration", "JavadocLinkAsPlainText"})
    public static LoggerContext initContextByFile(@Nullable String xmlPath, @Nullable ClassLoader loader) throws IOException {
        if (StringUtils.isBlank(xmlPath)) {
            return null;
        }
        try (InputStream stream = Files.newInputStream(Paths.get(xmlPath))) {
            return initContextByStream(stream, loader);
        }
    }

    @Nullable
    public static LoggerContext initContextByFileQuietly(@Nullable File xmlFile) {
        return initContextByFileQuietly(xmlFile, null);
    }

    @Nullable
    public static LoggerContext initContextByFileQuietly(@Nullable File xmlFile, @Nullable ClassLoader loader) {
        try {
            return initContextByFile(xmlFile, loader);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static LoggerContext initContextByFileQuietly(@Nullable String xmlPath) {
        return initContextByFileQuietly(xmlPath, null);
    }

    @Nullable
    public static LoggerContext initContextByFileQuietly(@Nullable String xmlPath, @Nullable ClassLoader loader) {
        try {
            return initContextByFile(xmlPath, loader);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static LoggerContext initContextByStream(@Nullable InputStream stream) throws IOException {
        return initContextByStream(stream, null);
    }

    @Nullable
    public static LoggerContext initContextByStream(@Nullable InputStream stream, @Nullable ClassLoader loader) throws IOException {
        if (stream == null) {
            return null;
        }
        ConfigurationSource source = new ConfigurationSource(stream);
        return Configurator.initialize(ObjectUtils.defaultIfNull(loader, ClassUtils.getDefaultClassLoader()), source);
    }

    @Nullable
    public static LoggerContext initContextByStreamQuietly(@Nullable InputStream stream) {
        return initContextByStreamQuietly(stream, null);
    }

    @Nullable
    public static LoggerContext initContextByStreamQuietly(@Nullable InputStream stream, @Nullable ClassLoader loader) {
        try {
            return initContextByStream(stream, loader);
        } catch (Exception ignored) {
        }
        return null;
    }
}
