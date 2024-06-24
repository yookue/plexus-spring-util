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
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import com.github.pagehelper.PageRowBounds;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.springutil.structure.AntTableStruct;


/**
 * Utilities for Ant Design Pro Table
 *
 * @author David Hsing
 * @reference "https://ant.design/components/table"
 * @reference "https://procomponents.ant.design/components/table#request"
 * @see com.yookue.commonplexus.springutil.structure.AntTableStruct
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public abstract class AntTableWraps {
    private static final String CURRENT_PAGE_PARAM = "current";    // $NON-NLS-1$
    private static final String PAGE_SIZE_PARAM = "pageSize";    // $NON-NLS-1$

    @Nullable
    public static AntTableStruct queryForAntTable(@Nonnull SqlSession sqlSession, @Nonnull String statementId) {
        return queryForAntTable(sqlSession, statementId, null, null);
    }

    @Nullable
    public static AntTableStruct queryForAntTable(@Nonnull SqlSession sqlSession, @Nonnull String statementId, @Nullable Map<String, Object> params) {
        return queryForAntTable(sqlSession, statementId, params, null);
    }

    @Nullable
    public static AntTableStruct queryForAntTable(@Nonnull SqlSession sqlSession, @Nonnull String statementId, @Nullable Map<String, Object> params, @Nullable PageRowBounds bounds) {
        if (StringUtils.isBlank(statementId)) {
            return null;
        }
        if (bounds == null && MapPlainWraps.containsAllKeys(params, CURRENT_PAGE_PARAM, PAGE_SIZE_PARAM)) {
            int currentPage = Math.max(1, MapPlainWraps.getInteger(params, CURRENT_PAGE_PARAM, 1));
            int pageSize = Math.max(0, MapPlainWraps.getInteger(params, CURRENT_PAGE_PARAM, 0));
            bounds = MybatisPageWraps.newPageRowBounds((currentPage - 1) * pageSize, pageSize, true);
        }
        AntTableStruct struct = new AntTableStruct();
        if (bounds == null) {
            Map<String, Object> cloneParams = new LinkedHashMap<>(params);
            MybatisPageWraps.putPageSizeZero(cloneParams);
            List<Map<String, Object>> resultSets = sqlSession.selectList(statementId, cloneParams);
            struct.setRecordsDetails(resultSets);
            struct.setRecordsTotal((long) CollectionPlainWraps.size(resultSets));
        } else {
            List<Map<String, Object>> resultSets = sqlSession.selectList(statementId, params, bounds);
            struct.setRecordsDetails(resultSets);
            struct.setRecordsTotal(bounds.getTotal());
        }
        return struct;
    }

    @Nullable
    public static AntTableStruct queryForAnyTableWithContextParameterized(@Nonnull SqlSession sqlSession, @Nonnull String statementId) {
        return queryForAnyTableWithContextParameterized(sqlSession, statementId, false, null);
    }

    @Nullable
    public static AntTableStruct queryForAnyTableWithContextParameterized(@Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean payloadParam) {
        return queryForAnyTableWithContextParameterized(sqlSession, statementId, payloadParam, null);
    }

    @Nullable
    public static AntTableStruct queryForAnyTableWithContextParameterized(@Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean payloadParam, @Nullable UnaryOperator<Map<String, Object>> paramsAction) {
        HttpServletRequest request = WebUtilsWraps.getContextServletRequest();
        return (request == null) ? null : queryForAntTableWithRequestParameterized(request, sqlSession, statementId, payloadParam, paramsAction);
    }

    @Nullable
    public static AntTableStruct queryForAntTableWithRequestParameterized(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId) {
        return queryForAntTableWithRequestParameterized(request, sqlSession, statementId, false, null);
    }

    @Nullable
    public static AntTableStruct queryForAntTableWithRequestParameterized(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean payloadParam) {
        return queryForAntTableWithRequestParameterized(request, sqlSession, statementId, payloadParam, null);
    }

    @Nullable
    public static AntTableStruct queryForAntTableWithRequestParameterized(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean payloadParam, @Nullable UnaryOperator<Map<String, Object>> paramsAction) {
        Map<String, Object> params = RequestParamWraps.getParameterObjectMap(request, true, payloadParam);
        if (paramsAction != null) {
            params = paramsAction.apply(params);
        }
        return queryForAntTable(sqlSession, statementId, params, null);
    }
}
