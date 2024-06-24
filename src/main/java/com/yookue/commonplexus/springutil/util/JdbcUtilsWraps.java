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


import java.lang.reflect.InvocationTargetException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.constant.SymbolVariantConst;


/**
 * Utilities for {@link org.springframework.jdbc.support.JdbcUtils}
 *
 * @author David Hsing
 * @see org.springframework.boot.jdbc.DatabaseDriver
 * @see org.springframework.jdbc.support.JdbcUtils
 * @see org.springframework.jdbc.datasource.DataSourceUtils
 * @see org.springframework.jdbc.datasource.init.ScriptUtils
 * @see "com.alibaba.druid.util.JdbcUtils"
 * @see "com.alibaba.druid.util.DruidDataSourceUtils"
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JdbcUtilsWraps {
    /**
     * Call the specified method on DatabaseMetaData for the given DataSource, and extract the invocation result.
     *
     * @param dataSource the DataSource to extract meta-data for
     * @param metaMethod the name of the DatabaseMetaData method to call
     *
     * @return the object returned by the specified DatabaseMetaData method
     *
     * @throws org.springframework.jdbc.support.MetaDataAccessException if this couldn't access the DatabaseMetaData or failed to invoke the specified method
     * @see java.sql.DatabaseMetaData
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getDatabaseMetaData(@Nonnull DataSource dataSource, @Nonnull String metaMethod) throws MetaDataAccessException {
        if (StringUtils.isBlank(metaMethod)) {
            return null;
        }
        return (T) JdbcUtils.extractDatabaseMetaData(dataSource, metaData -> {
            try {
                return DatabaseMetaData.class.getMethod(metaMethod).invoke(metaData);
            } catch (NoSuchMethodException ex) {
                throw new MetaDataAccessException(String.format("No method named '%s' found on DatabaseMetaData '%s'", metaMethod, metaData), ex);
            } catch (IllegalAccessException ex) {
                throw new MetaDataAccessException(String.format("Could not access method '%s' on DatabaseMetaData '%s'", metaMethod, metaData), ex);
            } catch (InvocationTargetException ex) {
                if (ex.getTargetException() instanceof SQLException) {
                    throw (SQLException) ex.getTargetException();
                }
                throw new MetaDataAccessException(String.format("Invocation method '%s' failed on DatabaseMetaData '%s'", metaMethod, metaData), ex);
            }
        });
    }

    @Nullable
    public static <T> T getDatabaseMetaDataQuietly(@Nonnull DataSource dataSource, @Nonnull String metaMethod) {
        try {
            return getDatabaseMetaData(dataSource, metaMethod);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getDatabaseProductName(@Nullable DataSource dataSource) throws MetaDataAccessException, IllegalStateException {
        return getDatabaseProductName(dataSource, false, false);
    }

    public static String getDatabaseProductName(@Nullable DataSource dataSource, boolean commonName) throws MetaDataAccessException, IllegalStateException {
        return getDatabaseProductName(dataSource, commonName, false);
    }

    /**
     * Returns the database product name
     *
     * @param dataSource the database source
     * @param commonName indicates use the common name instead, for example, "MariaDB" will be represented as "MySQL"
     * @param validateDriver whether to validate the database product driver or not, returns driver id if true
     *
     * @return the database product name
     *
     * @see org.springframework.boot.jdbc.AbstractDataSourceInitializer#getDatabaseName
     */
    @Nullable
    @SuppressWarnings({"JavadocReference", "deprecation"})
    public static String getDatabaseProductName(@Nullable DataSource dataSource, boolean commonName, boolean validateDriver) throws MetaDataAccessException, IllegalStateException {
        if (dataSource == null) {
            return null;
        }
        String productName = getDatabaseMetaData(dataSource, "getDatabaseProductName");    // $NON-NLS-1$
        if (StringUtils.isBlank(productName)) {
            return null;
        }
        if (commonName) {
            productName = JdbcUtils.commonDatabaseName(productName);
        }
        if (!validateDriver) {
            return productName;
        }
        DatabaseDriver databaseDriver = DatabaseDriver.fromProductName(productName);
        if (databaseDriver == DatabaseDriver.UNKNOWN) {
            throw new IllegalStateException("Unable to detect database type");
        }
        return databaseDriver.getId();
    }

    @Nullable
    public static String getDatabaseProductNameQuietly(@Nullable DataSource dataSource) {
        return getDatabaseProductNameQuietly(dataSource, false, false);
    }

    @Nullable
    public static String getDatabaseProductNameQuietly(@Nullable DataSource dataSource, boolean commonName) {
        return getDatabaseProductNameQuietly(dataSource, commonName, false);
    }

    @Nullable
    public static String getDatabaseProductNameQuietly(@Nullable DataSource dataSource, boolean commonName, boolean validateDriver) {
        try {
            return getDatabaseProductName(dataSource, commonName, validateDriver);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return the host name from a jdbc url
     * <pre>
     *     JdbcUtilsWraps.getHostName("jdbc:mysql://127.0.0.1:3306/schema?characterEncoding=UTF-8") = "127.0.0.1:3306"
     * </pre>
     *
     * @param jdbcUrl the url to detect
     *
     * @return the host name from a jdbc url
     */
    @Nullable
    public static String getHostName(@Nullable String jdbcUrl) {
        if (StringUtils.isBlank(jdbcUrl)) {
            return null;
        }
        int begin = StringUtils.indexOf(jdbcUrl, SymbolVariantConst.DOUBLE_SLASHES), end = StringUtils.lastIndexOf(jdbcUrl, CharVariantConst.SLASH);
        if (begin != -1) {
            return (end != -1) ? StringUtils.substring(jdbcUrl, begin + 2, end) : StringUtils.substring(jdbcUrl, begin + 2);
        }
        return null;
    }

    /**
     * Return the host and schema name from a jdbc url
     * <pre>
     *     JdbcUtilsWraps.getHostSchemaName("jdbc:mysql://127.0.0.1:3306/schema?characterEncoding=UTF-8") = "127.0.0.1:3306/schema"
     * </pre>
     *
     * @param jdbcUrl the url to detect
     *
     * @return the host and schema name from a jdbc url
     */
    @Nullable
    public static String getHostSchemaName(@Nullable String jdbcUrl) {
        if (StringUtils.isBlank(jdbcUrl)) {
            return null;
        }
        int begin = StringUtils.indexOf(jdbcUrl, SymbolVariantConst.DOUBLE_SLASHES), end = StringUtils.indexOf(jdbcUrl, CharVariantConst.QUESTION);
        if (begin != -1) {
            return (end != -1) ? StringUtils.substring(jdbcUrl, begin + 2, end) : StringUtils.substring(jdbcUrl, begin + 2);
        }
        return null;
    }

    /**
     * Return the schema name from a jdbc url
     * <pre>
     *     JdbcUtilsWraps.getSchemaName("jdbc:mysql://127.0.0.1:3306/schema?characterEncoding=UTF-8"") = "schema"
     * </pre>
     *
     * @param jdbcUrl the url to detect
     *
     * @return the schema name from a jdbc url
     */
    @Nullable
    public static String getSchemaName(@Nullable String jdbcUrl) {
        if (StringUtils.isBlank(jdbcUrl)) {
            return null;
        }
        int begin = StringUtils.lastIndexOf(jdbcUrl, CharVariantConst.SLASH), end = StringUtils.indexOf(jdbcUrl, CharVariantConst.QUESTION);
        if (begin != -1) {
            return (end != -1) ? StringUtils.substring(jdbcUrl, begin + 1, end) : StringUtils.substring(jdbcUrl, begin + 1);
        }
        return null;
    }
}
