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
import java.util.TreeMap;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.util.CollectionUtils;
import com.github.pagehelper.PageRowBounds;
import com.yookue.commonplexus.javaseutil.constant.SymbolVariantConst;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.RegexUtilsWraps;
import com.yookue.commonplexus.springutil.structure.DataTableStruct;


/**
 * Utilities for DataTables
 *
 * @author David Hsing
 * @reference "https://www.datatables.net/"
 * @reference "http://datatables.club/"
 * @see com.yookue.commonplexus.springutil.structure.DataTableStruct
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public abstract class DataTableWraps {
    private static final String DRAW_PARAM = "draw";    // $NON-NLS-1$
    private static final String START_PARAM = "start";    // $NON-NLS-1$
    private static final String LENGTH_PARAM = "length";    // $NON-NLS-1$
    private static final String SORT_ORDER = "_sort_order_";    // $NON-NLS-1$
    private static final String SORT_ASC = "asc";    // $NON-NLS-1$
    private static final String SORT_DESC = "desc";    // $NON-NLS-1$

    /**
     * Return a map that contains the column name and the sort order
     *
     * @param params the parameters map, most likely servlet request parameters
     *
     * @return a map that contains the column name and the sort order
     */
    @Nullable
    public static Map<String, String> getOrderDirAsMap(@Nullable Map<String, Object> params) {
        if (CollectionUtils.isEmpty(params)) {
            return null;
        }
        Map<String, String> orderColumns = new TreeMap<>();
        Pattern pattern = Pattern.compile("^order\\[\\d]\\[column]$", Pattern.CASE_INSENSITIVE);    // $NON-NLS-1$
        params.forEach((key, value) -> {
            if (StringUtils.isNotBlank(key) && pattern.matcher(key).matches()) {
                orderColumns.put(RegexUtilsWraps.reserveNumeric(key), MapPlainWraps.getString(params, key));
            }
        });
        if (CollectionUtils.isEmpty(orderColumns)) {
            return null;
        }
        Map<String, String> result = new TreeMap<>();
        orderColumns.forEach((order, column) -> {
            String nameData = com.yookue.commonplexus.springutil.util.MapPlainWraps.firstNonBlankDisplayString(params, String.format("columns[%s][name]", column), String.format("columns[%s][data]", column));    // $NON-NLS-1$ // $NON-NLS-2$
            nameData = RegexUtilsWraps.reserveWord(nameData);
            String orderDir = MapPlainWraps.getString(params, String.format("order[%s][dir]", order));    // $NON-NLS-1$
            if (StringUtils.isNotBlank(nameData) && StringUtils.equalsAnyIgnoreCase(orderDir, SORT_ASC, SORT_DESC)) {
                result.put(nameData, orderDir);
            }
        });
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    @Nullable
    public static String getOrderDirAsString(@Nullable Map<String, Object> params) {
        if (CollectionUtils.isEmpty(params)) {
            return null;
        }
        Map<String, String> columnDirs = getOrderDirAsMap(params);
        return com.yookue.commonplexus.springutil.util.MapPlainWraps.toDelimitedString(columnDirs, StringUtils.SPACE, SymbolVariantConst.COMMA_SPACE);
    }

    @Nonnull
    public static DataTableStruct newDataTableWithContext() {
        return newDataTableWithRequest(WebUtilsWraps.getContextServletRequest());
    }

    @Nonnull
    @SuppressWarnings("DataFlowIssue")
    public static DataTableStruct newDataTableWithRequest(@Nullable HttpServletRequest request) {
        DataTableStruct struct = new DataTableStruct();
        struct.setDrawTimes(Math.max(0, RequestParamWraps.getIntegerParameter(request, DRAW_PARAM, 0)));
        return struct;
    }

    @Nullable
    public static DataTableStruct queryForDataTable(@Nonnull SqlSession sqlSession, @Nonnull String statementId, @Nullable Integer drawTimes) {
        return queryForDataTable(sqlSession, statementId, null, null, drawTimes);
    }

    @Nullable
    public static DataTableStruct queryForDataTable(@Nonnull SqlSession sqlSession, @Nonnull String statementId, @Nullable Map<String, Object> params, @Nullable Integer drawTimes) {
        return queryForDataTable(sqlSession, statementId, params, null, drawTimes);
    }

    @Nullable
    public static DataTableStruct queryForDataTable(@Nonnull SqlSession sqlSession, @Nonnull String statementId, @Nullable Map<String, Object> params, @Nullable PageRowBounds bounds, @Nullable Integer drawTimes) {
        if (StringUtils.isBlank(statementId)) {
            return null;
        }
        String orderDir = getOrderDirAsString(params);
        if (params != null && StringUtils.isNotBlank(orderDir)) {
            params.put(SORT_ORDER, orderDir);
        }
        DataTableStruct struct = new DataTableStruct();
        struct.setDrawTimes(ObjectUtils.defaultIfNull(drawTimes, 0));
        if (bounds == null && MapPlainWraps.containsAllKeys(params, START_PARAM, LENGTH_PARAM)) {
            bounds = MybatisPageWraps.getPageRowBounds(params, START_PARAM, LENGTH_PARAM, true);
        }
        if (bounds == null) {
            Map<String, Object> cloneParams = new LinkedHashMap<>(params);
            MybatisPageWraps.putPageSizeZero(cloneParams);
            List<Map<String, Object>> resultSets = sqlSession.selectList(statementId, cloneParams);
            struct.setRecordsDetails(resultSets);
            int resultSize = CollectionPlainWraps.size(resultSets);
            struct.setRecordsDisplay(resultSize);
            struct.setRecordsTotal((long) resultSize);
            struct.setRecordsFiltered((long) resultSize);
        } else {
            List<Map<String, Object>> resultSets = sqlSession.selectList(statementId, params, bounds);
            struct.setRecordsDetails(resultSets);
            struct.setRecordsDisplay(CollectionPlainWraps.size(resultSets));
            struct.setRecordsTotal(bounds.getTotal());
            struct.setRecordsFiltered(bounds.getTotal());
        }
        return struct;
    }

    @Nullable
    public static DataTableStruct queryForDataTableWithContext(@Nonnull SqlSession sqlSession, @Nonnull String statementId) {
        return queryForDataTableWithContext(sqlSession, statementId, null, null);
    }

    @Nullable
    public static DataTableStruct queryForDataTableWithContext(@Nonnull SqlSession sqlSession, @Nonnull String statementId, @Nullable Map<String, Object> params) {
        return queryForDataTableWithContext(sqlSession, statementId, params, null);
    }

    @Nullable
    public static DataTableStruct queryForDataTableWithContext(@Nonnull SqlSession sqlSession, @Nonnull String statementId, @Nullable Map<String, Object> params, @Nullable PageRowBounds bounds) {
        HttpServletRequest request = WebUtilsWraps.getContextServletRequest();
        return (request == null) ? null : queryForDataTableWithRequest(request, sqlSession, statementId, params, bounds);
    }

    @Nullable
    public static DataTableStruct queryForDataTableWithContextParameterized(@Nonnull SqlSession sqlSession, @Nonnull String statementId) {
        return queryForDataTableWithContextParameterized(sqlSession, statementId, false, null);
    }

    @Nullable
    public static DataTableStruct queryForDataTableWithContextParameterized(@Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean payloadParam) {
        return queryForDataTableWithContextParameterized(sqlSession, statementId, payloadParam, null);
    }

    @Nullable
    public static DataTableStruct queryForDataTableWithContextParameterized(@Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean payloadParam, @Nullable UnaryOperator<Map<String, Object>> paramsAction) {
        HttpServletRequest request = WebUtilsWraps.getContextServletRequest();
        return (request == null) ? null : queryForDataTableWithRequestParameterized(request, sqlSession, statementId, payloadParam, paramsAction);
    }

    @Nullable
    public static DataTableStruct queryForDataTableWithRequest(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId) {
        return queryForDataTableWithRequest(request, sqlSession, statementId, null, null);
    }

    @Nullable
    public static DataTableStruct queryForDataTableWithRequest(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId, @Nullable Map<String, Object> params) {
        return queryForDataTableWithRequest(request, sqlSession, statementId, params, null);
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static DataTableStruct queryForDataTableWithRequest(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId, @Nullable Map<String, Object> params, @Nullable PageRowBounds bounds) {
        int drawTimes = Math.max(0, RequestParamWraps.getIntegerParameter(request, DRAW_PARAM, 0));
        return queryForDataTable(sqlSession, statementId, params, bounds, drawTimes);
    }

    @Nullable
    public static DataTableStruct queryForDataTableWithRequestParameterized(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId) {
        return queryForDataTableWithRequestParameterized(request, sqlSession, statementId, false, null);
    }

    @Nullable
    public static DataTableStruct queryForDataTableWithRequestParameterized(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean payloadParam) {
        return queryForDataTableWithRequestParameterized(request, sqlSession, statementId, false, null);
    }

    @Nullable
    public static DataTableStruct queryForDataTableWithRequestParameterized(@Nonnull HttpServletRequest request, @Nonnull SqlSession sqlSession, @Nonnull String statementId, boolean payloadParam, @Nullable UnaryOperator<Map<String, Object>> paramsAction) {
        Map<String, Object> params = RequestParamWraps.getParameterObjectMap(request, true, payloadParam);
        if (paramsAction != null) {
            params = paramsAction.apply(params);
        }
        return queryForDataTableWithRequest(request, sqlSession, statementId, params, null);
    }
}
