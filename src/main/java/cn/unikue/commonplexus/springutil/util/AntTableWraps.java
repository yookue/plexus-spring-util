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


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import com.github.pagehelper.PageRowBounds;
import cn.unikue.commonplexus.javaseutil.util.CollectionPlainWraps;
import cn.unikue.commonplexus.javaseutil.util.MapPlainWraps;
import cn.unikue.commonplexus.springutil.structure.AntTableStruct;


/**
 * Utilities for Ant Design Pro Table
 *
 * @author David Hsing
 *
 * @see cn.unikue.commonplexus.springutil.structure.AntTableStruct
 *
 * @reference "https://ant.design/components/table"
 * @reference "https://procomponents.ant.design/components/table#request"
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public abstract class AntTableWraps {
    private static final String CURRENT_PAGE_PARAM = "current";    // $NON-NLS-1$
    private static final String PAGE_SIZE_PARAM = "pageSize";    // $NON-NLS-1$

    @Nullable
    public static AntTableStruct queryForTable(@Nonnull SqlSession sqlSession, @Nonnull String statementId) {
        return queryForTable(sqlSession, statementId, null, null);
    }

    @Nullable
    public static AntTableStruct queryForTable(@Nonnull SqlSession sqlSession, @Nonnull String statementId, @Nullable Map<String, Object> params) {
        return queryForTable(sqlSession, statementId, params, null);
    }

    @Nullable
    public static AntTableStruct queryForTable(@Nonnull SqlSession sqlSession, @Nonnull String statementId, @Nullable Map<String, Object> params, @Nullable PageRowBounds bounds) {
        if (StringUtils.isBlank(statementId)) {
            return null;
        }
        PageRowBounds cloneBounds = bounds;
        if (cloneBounds == null && MapPlainWraps.containsAllKeys(params, CURRENT_PAGE_PARAM, PAGE_SIZE_PARAM)) {
            int currentPage = Math.max(1, MapPlainWraps.getInteger(params, CURRENT_PAGE_PARAM, 1));
            int pageSize = Math.max(0, MapPlainWraps.getInteger(params, PAGE_SIZE_PARAM, 10));
            cloneBounds = MybatisSqlWraps.ofRowBounds((currentPage - 1) * pageSize, pageSize, true);
        }
        AntTableStruct struct = new AntTableStruct();
        struct.setSuccess(true);
        if (cloneBounds == null) {
            Map<String, Object> cloneParams = new LinkedHashMap<>(params);
            MybatisSqlWraps.disablePagination(cloneParams);
            List<Map<String, Object>> resultSets = sqlSession.selectList(statementId, cloneParams);
            struct.setData(resultSets);
            struct.setTotal((long) CollectionPlainWraps.size(resultSets));
        } else {
            List<Map<String, Object>> resultSets = sqlSession.selectList(statementId, params, cloneBounds);
            struct.setData(resultSets);
            struct.setTotal(cloneBounds.getTotal());
        }
        return struct;
    }

    @Nullable
    public static AntTableStruct queryForTableWithContextParameterized(@Nonnull SqlSession sqlSession, @Nonnull String statementId) {
        return queryForTableWithContextParameterized(sqlSession, statementId, false, false, null);
    }

    @Nullable
    public static AntTableStruct queryForTableWithContextParameterized(@Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean includePayload, boolean unwrapSingleArray) {
        return queryForTableWithContextParameterized(sqlSession, statementId, includePayload, unwrapSingleArray, null);
    }

    @Nullable
    public static AntTableStruct queryForTableWithContextParameterized(@Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean includePayload, boolean unwrapSingleArray, @Nullable UnaryOperator<Map<String, Object>> paramsAction) {
        HttpServletRequest request = WebUtilsWraps.getContextServletRequest();
        return (request == null) ? null : queryForTableWithRequestParameterized(request, sqlSession, statementId, includePayload, unwrapSingleArray, paramsAction);
    }

    @Nullable
    public static AntTableStruct queryForTableWithRequestParameterized(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId) {
        return queryForTableWithRequestParameterized(request, sqlSession, statementId, false, false, null);
    }

    @Nullable
    public static AntTableStruct queryForTableWithRequestParameterized(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean includePayload) {
        return queryForTableWithRequestParameterized(request, sqlSession, statementId, includePayload, false, null);
    }

    @Nullable
    public static AntTableStruct queryForTableWithRequestParameterized(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean includePayload, boolean unwrapSingleArray) {
        return queryForTableWithRequestParameterized(request, sqlSession, statementId, includePayload, unwrapSingleArray, null);
    }

    @Nullable
    public static AntTableStruct queryForTableWithRequestParameterized(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean includePayload, boolean unwrapSingleArray, @Nullable UnaryOperator<Map<String, Object>> paramsAction) {
        Map<String, Object> params = RequestParamWraps.getParameterObjectMap(request, false, includePayload, unwrapSingleArray);
        if (paramsAction != null) {
            params = paramsAction.apply(params);
        }
        return queryForTable(sqlSession, statementId, params, null);
    }
}
