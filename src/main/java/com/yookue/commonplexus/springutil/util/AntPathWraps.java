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


import java.util.Collection;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.util.ArrayUtilsWraps;


/**
 * Utilities for ant paths
 *
 * @author David Hsing
 * @see org.springframework.util.AntPathMatcher
 * @see org.springframework.security.web.util.matcher.AntPathRequestMatcher
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class AntPathWraps {
    public static boolean maybeAntPattern(@Nullable String path) {
        return StringUtils.containsAny(path, CharVariantConst.QUESTION, CharVariantConst.STAR);
    }

    public static <T> boolean matchAnyPaths(@Nullable String pattern, String... paths) {
        return matchAnyPaths(null, pattern, ArrayUtilsWraps.asList(paths));
    }

    public static <T> boolean matchAnyPaths(@Nullable String separator, @Nullable String pattern, String... paths) {
        return matchAnyPaths(separator, pattern, ArrayUtilsWraps.asList(paths));
    }

    public static <T> boolean matchAnyPaths(@Nullable String pattern, Collection<String> paths) {
        return matchAnyPaths(null, pattern, paths);
    }

    public static <T> boolean matchAnyPaths(@Nullable String separator, @Nullable String pattern, Collection<String> paths) {
        return matchAnyPaths(separator, true, false, pattern, paths);
    }

    public static <T> boolean matchAnyPaths(boolean caseSensitive, boolean trimTokens, @Nullable String pattern, @Nullable String... paths) {
        return matchAnyPaths(null, caseSensitive, trimTokens, pattern, ArrayUtilsWraps.asList(paths));
    }

    public static <T> boolean matchAnyPaths(@Nullable String separator, boolean caseSensitive, boolean trimTokens, @Nullable String pattern, @Nullable String... paths) {
        return matchAnyPaths(separator, caseSensitive, trimTokens, pattern, ArrayUtilsWraps.asList(paths));
    }

    public static <T> boolean matchAnyPaths(boolean caseSensitive, boolean trimTokens, @Nullable String pattern, @Nullable Collection<String> paths) {
        return matchAnyPaths(null, caseSensitive, trimTokens, pattern, paths);
    }

    /**
     * Return <code>true</code> if the given pattern matches the path in the collection
     *
     * @param separator the path separator to use, default to '/' if is empty
     * @param pattern the target ant path pattern
     * @param paths the collection of path to get the input from
     *
     * @return <code>true</code> if the given pattern matches the path in the collection
     */
    public static <T> boolean matchAnyPaths(@Nullable String separator, boolean caseSensitive, boolean trimTokens, @Nullable String pattern, @Nullable Collection<String> paths) {
        if (StringUtils.isEmpty(pattern) || CollectionUtils.isEmpty(paths)) {
            return false;
        }
        AntPathMatcher matcher = newAntPathMatcher(separator, caseSensitive, trimTokens);
        return paths.stream().anyMatch(path -> matcher.match(pattern, path));
    }

    public static <T> boolean matchAnyPathStarts(@Nullable String pattern, @Nullable String... paths) {
        return matchAnyPathStarts(null, pattern, ArrayUtilsWraps.asList(paths));
    }

    public static <T> boolean matchAnyPathStarts(@Nullable String separator, @Nullable String pattern, @Nullable String... paths) {
        return matchAnyPathStarts(separator, pattern, ArrayUtilsWraps.asList(paths));
    }

    public static <T> boolean matchAnyPathStarts(@Nullable String pattern, @Nullable Collection<String> paths) {
        return matchAnyPathStarts(null, pattern, paths);
    }

    public static <T> boolean matchAnyPathStarts(@Nullable String separator, @Nullable String pattern, @Nullable Collection<String> paths) {
        return matchAnyPathStarts(separator, true, false, pattern, paths);
    }

    public static <T> boolean matchAnyPathStarts(boolean caseSensitive, boolean trimTokens, @Nullable String pattern, @Nullable String... paths) {
        return matchAnyPathStarts(null, caseSensitive, trimTokens, pattern, ArrayUtilsWraps.asList(paths));
    }

    public static <T> boolean matchAnyPathStarts(@Nullable String separator, boolean caseSensitive, boolean trimTokens, @Nullable String pattern, @Nullable String... paths) {
        return matchAnyPathStarts(separator, caseSensitive, trimTokens, pattern, ArrayUtilsWraps.asList(paths));
    }

    public static <T> boolean matchAnyPathStarts(boolean caseSensitive, boolean trimTokens, @Nullable String pattern, @Nullable Collection<String> paths) {
        return matchAnyPathStarts(null, caseSensitive, trimTokens, pattern, paths);
    }

    /**
     * Return <code>true</code> if the given pattern matches the start of the path in the collection
     *
     * @param separator the path separator to use, default to '/' if is empty
     * @param pattern the target ant path pattern
     * @param paths the collection of path to get the input from
     *
     * @return <code>true</code> if the given pattern matches the start of the path in the collection
     */
    public static <T> boolean matchAnyPathStarts(@Nullable String separator, boolean caseSensitive, boolean trimTokens, @Nullable String pattern, @Nullable Collection<String> paths) {
        if (StringUtils.isEmpty(pattern) || CollectionUtils.isEmpty(paths)) {
            return false;
        }
        AntPathMatcher matcher = newAntPathMatcher(separator, caseSensitive, trimTokens);
        return paths.stream().anyMatch(path -> matcher.matchStart(pattern, path));
    }

    public static <T> boolean matchAnyPatterns(@Nullable String path, @Nullable String... patterns) {
        return matchAnyPatterns(null, path, ArrayUtilsWraps.asList(patterns));
    }

    public static <T> boolean matchAnyPatterns(@Nullable String separator, @Nullable String path, @Nullable String... patterns) {
        return matchAnyPatterns(separator, path, ArrayUtilsWraps.asList(patterns));
    }

    public static <T> boolean matchAnyPatterns(@Nullable String path, @Nullable Collection<String> patterns) {
        return matchAnyPatterns(null, path, patterns);
    }

    public static <T> boolean matchAnyPatterns(@Nullable String separator, @Nullable String path, @Nullable Collection<String> patterns) {
        return matchAnyPatterns(separator, true, false, path, patterns);
    }

    public static <T> boolean matchAnyPatterns(boolean caseSensitive, boolean trimTokens, @Nullable String path, @Nullable String... patterns) {
        return matchAnyPatterns(null, caseSensitive, trimTokens, path, ArrayUtilsWraps.asList(patterns));
    }

    public static <T> boolean matchAnyPatterns(@Nullable String separator, boolean caseSensitive, boolean trimTokens, @Nullable String path, @Nullable String... patterns) {
        return matchAnyPatterns(separator, caseSensitive, trimTokens, path, ArrayUtilsWraps.asList(patterns));
    }

    public static <T> boolean matchAnyPatterns(boolean caseSensitive, boolean trimTokens, @Nullable String path, @Nullable Collection<String> patterns) {
        return matchAnyPatterns(null, caseSensitive, trimTokens, path, patterns);
    }

    /**
     * Return <code>true</code> if any patterns in the collection matches the given path
     *
     * @param separator the path separator to use, default to '/' if is empty
     * @param path the target path
     * @param patterns the collection of pattern to get the input from
     *
     * @return <code>true</code> if any patterns in the collection matches the given path
     */
    public static <T> boolean matchAnyPatterns(@Nullable String separator, boolean caseSensitive, boolean trimTokens, @Nullable String path, @Nullable Collection<String> patterns) {
        if (StringUtils.isEmpty(path) || CollectionUtils.isEmpty(patterns)) {
            return false;
        }
        AntPathMatcher matcher = newAntPathMatcher(separator, caseSensitive, trimTokens);
        return patterns.stream().anyMatch(pattern -> matcher.match(pattern, path));
    }

    public static <T> boolean matchAnyPatternStarts(@Nullable String path, @Nullable String... patterns) {
        return matchAnyPatternStarts(null, path, ArrayUtilsWraps.asList(patterns));
    }

    public static <T> boolean matchAnyPatternStarts(@Nullable String separator, @Nullable String path, @Nullable String... patterns) {
        return matchAnyPatternStarts(separator, path, ArrayUtilsWraps.asList(patterns));
    }

    public static <T> boolean matchAnyPatternStarts(@Nullable String path, @Nullable Collection<String> patterns) {
        return matchAnyPatternStarts(null, path, patterns);
    }

    public static <T> boolean matchAnyPatternStarts(@Nullable String separator, @Nullable String path, @Nullable Collection<String> patterns) {
        return matchAnyPatternStarts(separator, true, false, path, patterns);
    }

    public static <T> boolean matchAnyPatternStarts(boolean caseSensitive, boolean trimTokens, @Nullable String path, @Nullable String... patterns) {
        return matchAnyPatternStarts(null, caseSensitive, trimTokens, path, ArrayUtilsWraps.asList(patterns));
    }

    public static <T> boolean matchAnyPatternStarts(@Nullable String separator, boolean caseSensitive, boolean trimTokens, @Nullable String path, @Nullable String... patterns) {
        return matchAnyPatternStarts(separator, caseSensitive, trimTokens, path, ArrayUtilsWraps.asList(patterns));
    }

    public static <T> boolean matchAnyPatternStarts(boolean caseSensitive, boolean trimTokens, @Nullable String path, @Nullable Collection<String> patterns) {
        return matchAnyPatternStarts(null, caseSensitive, trimTokens, path, patterns);
    }

    /**
     * Return <code>true</code> if any patterns in the collection matches the start of the given path
     *
     * @param separator the path separator to use, default to '/' if is empty
     * @param path the target path
     * @param patterns the collection of pattern to get the input from
     *
     * @return <code>true</code> if any patterns in the collection matches the start of the given path
     */
    public static <T> boolean matchAnyPatternStarts(@Nullable String separator, boolean caseSensitive, boolean trimTokens, @Nullable String path, @Nullable Collection<String> patterns) {
        if (StringUtils.isEmpty(path) || CollectionUtils.isEmpty(patterns)) {
            return false;
        }
        AntPathMatcher matcher = newAntPathMatcher(separator, caseSensitive, trimTokens);
        return patterns.stream().anyMatch(pattern -> matcher.matchStart(pattern, path));
    }

    @Nonnull
    public static AntPathMatcher newAntPathMatcher(char separator, boolean caseSensitive, boolean trimTokens) {
        return newAntPathMatcher(CharUtils.toString(separator), caseSensitive, trimTokens);
    }

    @Nonnull
    public static AntPathMatcher newAntPathMatcher(@Nullable String separator, boolean caseSensitive, boolean trimTokens) {
        AntPathMatcher matcher = new AntPathMatcher(StringUtils.defaultIfBlank(separator, AntPathMatcher.DEFAULT_PATH_SEPARATOR));
        matcher.setCaseSensitive(caseSensitive);
        matcher.setTrimTokens(trimTokens);
        return matcher;
    }
}
