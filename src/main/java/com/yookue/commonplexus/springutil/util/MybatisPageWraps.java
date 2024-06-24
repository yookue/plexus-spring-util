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


import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.CollectionUtils;
import com.github.pagehelper.PageRowBounds;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.springutil.constant.MybatisPageConst;


/**
 * Utilities for mybatis page helper
 *
 * @author David Hsing
 * @reference "https://github.com/pagehelper/Mybatis-PageHelper"
 * @reference "https://www.cnblogs.com/jinit/p/14841966.html"
 * @see "com.github.pagehelper.util.PageObjectUtil"
 */
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public abstract class MybatisPageWraps {
    @Nonnull
    public static RowBounds getRowBounds(@Nullable Map<String, Object> params, @Nullable String offsetParam, @Nullable String limitParam) {
        return getRowBounds(params, offsetParam, limitParam, null, null);
    }

    @Nonnull
    public static RowBounds getRowBounds(@Nullable Map<String, Object> params, @Nullable String offsetParam, @Nullable String limitParam, @Nullable Integer defaultOffset, @Nullable Integer defaultLimit) {
        if (CollectionUtils.isEmpty(params)) {
            return RowBounds.DEFAULT;
        }
        int offset = Math.max(0, MapPlainWraps.getInteger(params, offsetParam, ObjectUtils.defaultIfNull(defaultOffset, RowBounds.NO_ROW_OFFSET)));
        int limit = Math.max(0, MapPlainWraps.getInteger(params, limitParam, ObjectUtils.defaultIfNull(defaultLimit, RowBounds.NO_ROW_LIMIT)));
        return new RowBounds(offset, limit);
    }

    @Nonnull
    public static PageRowBounds getPageRowBounds(@Nullable Map<String, Object> params, @Nullable String offsetParam, @Nullable String limitParam, boolean count) {
        return getPageRowBounds(params, offsetParam, limitParam, null, null, count);
    }

    @Nonnull
    public static PageRowBounds getPageRowBounds(@Nullable Map<String, Object> params, @Nullable String offsetParam, @Nullable String limitParam, @Nullable Integer defaultOffset, @Nullable Integer defaultLimit, boolean count) {
        RowBounds bounds = getRowBounds(params, offsetParam, limitParam, defaultOffset, defaultLimit);
        PageRowBounds result = new PageRowBounds(bounds.getOffset(), bounds.getLimit());
        result.setCount(count);
        return result;
    }

    @Nonnull
    public static PageRowBounds newPageRowBounds() {
        return newPageRowBounds(RowBounds.NO_ROW_OFFSET, 10, true);
    }

    @Nonnull
    public static PageRowBounds newPageRowBounds(boolean count) {
        return newPageRowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT, count);
    }

    @Nonnull
    public static PageRowBounds newPageRowBounds(int offset, int limit, boolean count) {
        PageRowBounds result = new PageRowBounds(Math.max(0, offset), Math.max(0, limit));
        result.setCount(count);
        return result;
    }

    public static void putPageSizeZero(@Nullable Map<String, Object> params) {
        if (params == null) {
            params = new LinkedHashMap<>(1);
        }
        params.put(MybatisPageConst.PAGE_SIZE_ZERO, true);
    }
}
