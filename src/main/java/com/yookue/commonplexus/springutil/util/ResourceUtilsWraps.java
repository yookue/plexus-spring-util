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


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.support.ServletContextResource;
import com.yookue.commonplexus.javaseutil.constant.StringVariantConst;
import com.yookue.commonplexus.javaseutil.constant.SymbolVariantConst;
import com.yookue.commonplexus.javaseutil.exception.UnsupportedResourceException;
import com.yookue.commonplexus.javaseutil.util.InetProtocolWraps;


/**
 * Utilities for {@link org.springframework.util.ResourceUtils}
 *
 * @author David Hsing
 * @see org.springframework.util.ResourceUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ResourceUtilsWraps {
    @Nullable
    public static Resource determineResource(@Nullable String location) {
        return determineResource(location, null);
    }

    @Nullable
    public static Resource determineResource(@Nullable String location, @Nullable ClassLoader loader) {
        if (StringUtils.isBlank(location)) {
            return null;
        }
        if (ClassPathWraps.startsWithClasspath(location)) {
            return newClassPathResource(location, loader);
        }
        if (ClassPathWraps.startsWithClasspathStar(location)) {
            return ArrayUtils.get(resolveClassPathResourcesQuietly(location, new DefaultResourceLoader(loader)), 0);
        }
        if (InetProtocolWraps.startsWithProtocol(location)) {
            return newUrlResource(location);
        }
        return new FileSystemResource(location);
    }

    @Nullable
    public static Resource getResource(@Nullable String location, @Nullable ResourceLoader loader) {
        return (StringUtils.isBlank(location) || loader == null) ? null : loader.getResource(location);
    }

    @Nullable
    public static URL getUrl(@Nullable String location) {
        if (StringUtils.isBlank(location)) {
            return null;
        }
        try {
            return ResourceUtils.getURL(location);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static URI toUri(@Nullable URL resourceUrl) {
        if (resourceUrl == null) {
            return null;
        }
        try {
            return ResourceUtils.toURI(resourceUrl);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static URI toUri(@Nullable String location) {
        if (StringUtils.isBlank(location)) {
            return null;
        }
        try {
            return ResourceUtils.toURI(location);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static File getFile(@Nullable URI fileUri) {
        return getFile(fileUri, StringVariantConst.URI);
    }

    @Nullable
    public static File getFile(@Nullable URI fileUri, @Nullable String description) {
        if (fileUri == null) {
            return null;
        }
        try {
            return ResourceUtils.getFile(fileUri, description);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static File getFile(@Nullable URL fileUrl) {
        return getFile(fileUrl, StringVariantConst.URL);
    }

    @Nullable
    public static File getFile(@Nullable URL fileUrl, @Nullable String description) {
        if (fileUrl == null) {
            return null;
        }
        try {
            return ResourceUtils.getFile(fileUrl, description);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static File getFile(@Nullable String location) {
        if (StringUtils.isBlank(location)) {
            return null;
        }
        try {
            return ResourceUtils.getFile(location);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static URL extractArchiveUrl(@Nullable URL jarUrl) {
        if (jarUrl == null) {
            return null;
        }
        try {
            return ResourceUtils.extractArchiveURL(jarUrl);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static URL extractJarFileUrl(@Nullable URL jarUrl) {
        if (jarUrl == null) {
            return null;
        }
        try {
            return ResourceUtils.extractJarFileURL(jarUrl);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static String getResourcePath(@Nullable Resource resource) throws UnsupportedResourceException {
        if (resource == null) {
            return null;
        }
        if (resource instanceof ClassPathResource instance) {
            return instance.getPath();
        } else if (resource instanceof FileSystemResource instance) {
            return instance.getPath();
        } else if (resource instanceof PathResource instance) {
            return instance.getPath();
        } else if (resource instanceof ServletContextResource instance) {
            return instance.getPath();
        } else if (resource instanceof UrlResource instance) {
            return instance.getURL().toString();
        }
        throw new UnsupportedResourceException();
    }

    @Nullable
    public static String getResourcePathQuietly(@Nullable Resource resource) {
        try {
            return getResourcePath(resource);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static boolean isFilePath(@Nullable String location) {
        return StringUtils.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_FILE, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    public static boolean isJarPath(@Nullable String location) {
        return StringUtils.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_JAR, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    public static boolean isWarPath(@Nullable String location) {
        return StringUtils.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_WAR, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    public static boolean isZipPath(@Nullable String location) {
        return StringUtils.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_ZIP, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    public static boolean isWsjarPath(@Nullable String location) {
        return StringUtils.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_WSJAR, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    public static boolean isVfsPath(@Nullable String location) {
        return StringUtils.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_VFS, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    public static boolean isVfszipPath(@Nullable String location) {
        return StringUtils.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_VFSZIP, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    public static boolean isVfsfilePath(@Nullable String location) {
        return StringUtils.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_VFSFILE, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    public static boolean existsClassPathResource(@Nullable String location) {
        return existsClassPathResource(location, (ClassLoader) null);
    }

    public static boolean existsClassPathResource(@Nullable String path, @Nullable ClassLoader loader) {
        ClassPathResource resource = newClassPathResource(path, loader);
        return resource != null && resource.exists();
    }

    public static boolean existsClassPathResource(@Nullable String path, @Nullable Class<?> clazz) {
        ClassPathResource resource = newClassPathResource(path, clazz);
        return resource != null && resource.exists();
    }

    public static boolean existsUrlResource(@Nullable String location) {
        UrlResource resource = newUrlResource(location);
        return resource != null && resource.exists();
    }

    public static boolean existsUrlResource(@Nullable String protocol, @Nullable String location) {
        UrlResource resource = newUrlResource(protocol, location);
        return resource != null && resource.exists();
    }

    public static boolean existsUrlResource(@Nullable String protocol, @Nullable String location, @Nullable String fragment) {
        UrlResource resource = newUrlResource(protocol, location, fragment);
        return resource != null && resource.exists();
    }

    @Nullable
    public static ClassPathResource newClassPathResource(@Nullable String path) {
        return newClassPathResource(path, (ClassLoader) null);
    }

    @Nullable
    public static ClassPathResource newClassPathResource(@Nullable String path, @Nullable ClassLoader loader) {
        String pathToUse = ClassPathWraps.startsWithClasspath(path) ? ClassPathWraps.removeClasspathPrefix(path) : path;
        return StringUtils.isBlank(path) ? null : new ClassPathResource(pathToUse, loader);
    }

    @Nullable
    public static ClassPathResource newClassPathResource(@Nullable String path, @Nullable Class<?> clazz) {
        String pathToUse = ClassPathWraps.startsWithClasspath(path) ? ClassPathWraps.removeClasspathPrefix(path) : path;
        return StringUtils.isBlank(path) ? null : new ClassPathResource(pathToUse, clazz);
    }

    @Nullable
    public static UrlResource newUrlResource(@Nullable String location) {
        try {
            return StringUtils.isBlank(location) ? null : new UrlResource(location);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static UrlResource newUrlResource(@Nullable String protocol, @Nullable String location) {
        return newUrlResource(protocol, location, null);
    }

    @Nullable
    public static UrlResource newUrlResource(@Nullable String protocol, @Nullable String location, @Nullable String fragment) {
        try {
            return StringUtils.isAnyBlank(protocol, location) ? null : new UrlResource(protocol, location, fragment);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static Resource[] resolveClassPathResources(@Nullable String path) throws IOException {
        return resolveClassPathResources(path, null);
    }

    /**
     * @see "https://www.cnblogs.com/gqymy/p/10450466.html"
     */
    @Nullable
    public static Resource[] resolveClassPathResources(@Nullable String path, @Nullable ResourceLoader loader) throws IOException {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(loader != null ? loader : new DefaultResourceLoader());
        return resolver.getResources(path);
    }

    @Nullable
    public static Resource[] resolveClassPathResourcesQuietly(@Nullable String path) {
        return resolveClassPathResourcesQuietly(path, null);
    }

    @Nullable
    public static Resource[] resolveClassPathResourcesQuietly(@Nullable String path, @Nullable ResourceLoader loader) {
        try {
            return resolveClassPathResources(path, loader);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    public static byte[] toByteArray(@Nullable Resource resource) throws IOException {
        if (resource == null) {
            return null;
        }
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            FileCopyUtils.copy(resource.getInputStream(), output);
            return output.toByteArray();
        }
    }

    @Nullable
    public static byte[] toByteArrayQuietly(@Nullable Resource resource) {
        try {
            return toByteArray(resource);
        } catch (Exception ignored) {
        }
        return null;
    }
}
