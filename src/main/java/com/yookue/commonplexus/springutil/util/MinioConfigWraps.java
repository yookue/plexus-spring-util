/*
 * Copyright (c) 2020 Yookue Ltd. All rights reserved.
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


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationUtils;
import com.yookue.commonplexus.javaseutil.util.CollectionPlainWraps;
import com.yookue.commonplexus.javaseutil.util.ListPlainWraps;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.javaseutil.util.StringUtilsWraps;
import com.yookue.commonplexus.springutil.property.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.GetBucketPolicyArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;


/**
 * Utilities for {@link io.minio.MinioClient}
 *
 * @author David Hsing
 *
 * @see io.minio.MinioClient
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class MinioConfigWraps {
    @Nonnull
    public static MinioClient minioClient(@Nonnull MinioProperties properties) {
        MinioClient.Builder clientBuilder = MinioClient.builder();
        clientBuilder.endpoint(properties.getEndpoint(), ObjectUtils.defaultIfNull(properties.getPort(), 9000), BooleanUtils.isTrue(properties.getSecureHttp()));
        if (StringUtils.isNotBlank(properties.getAccessKey()) || StringUtils.isNotBlank(properties.getSecretKey())) {
            clientBuilder.credentials(properties.getAccessKey(), properties.getSecretKey());
        }
        if (StringUtils.isNotBlank(properties.getRegion())) {
            clientBuilder.region(properties.getRegion());
        }
        long connectTimeout = DurationUtils.isPositive(properties.getConnectTimeout()) ? properties.getConnectTimeout().toMillis() : 0L;
        long writeTimeout = DurationUtils.isPositive(properties.getWriteTimeout()) ? properties.getWriteTimeout().toMillis() : 0L;
        long readTimeout = DurationUtils.isPositive(properties.getReadTimeout()) ? properties.getReadTimeout().toMillis() : 0L;
        MinioClient minioClient = clientBuilder.build();
        minioClient.setTimeout(connectTimeout, writeTimeout, readTimeout);
        if (StringUtils.isNoneBlank(properties.getUserAgentName(), properties.getUserAgentVersion())) {
            minioClient.setAppInfo(properties.getUserAgentName(), properties.getUserAgentVersion());
        }
        if (BooleanUtils.isTrue(properties.getDualStackEnabled())) {
            minioClient.enableDualStackEndpoint();
        } else {
            minioClient.disableDualStackEndpoint();
        }
        if (BooleanUtils.isTrue(properties.getVirtualStyleEnabled())) {
            minioClient.enableVirtualStyleEndpoint();
        } else {
            minioClient.disableVirtualStyleEndpoint();
        }
        if (StringUtils.isNotBlank(properties.getBucketName())) {
            if (BooleanUtils.isTrue(properties.getAutoCreateBucket()) && !isBucketExist(minioClient, properties.getBucketName())) {
                makeBucket(minioClient, properties.getBucketName(), properties.getRegion());
            }
            if (BooleanUtils.isTrue(properties.getBucketPublic()) && isBucketExist(minioClient, properties.getBucketName()) && !isBucketPublic(minioClient, properties.getBucketName())) {
                makeBucketPublic(minioClient, properties.getBucketName());
            }
        }
        return minioClient;
    }

    public static boolean isBucketExist(@Nonnull MinioClient minioClient, @Nonnull String bucketName) {
        if (StringUtils.isBlank(bucketName)) {
            return false;
        }
        try {
            BucketExistsArgs existsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
            return minioClient.bucketExists(existsArgs);
        } catch (Exception ignored) {
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static boolean isBucketPublic(@Nonnull MinioClient minioClient, @Nonnull String bucketName) {
        if (StringUtils.isBlank(bucketName)) {
            return false;
        }
        try {
            GetBucketPolicyArgs policyArgs = GetBucketPolicyArgs.builder().bucket(bucketName).build();
            String policyJson = minioClient.getBucketPolicy(policyArgs);
            if (StringUtils.isBlank(policyJson)) {
                return false;
            }
            Map<String, Object> policyMap = JsonParserWraps.parseJsonToMap(policyJson);
            if (MapPlainWraps.isEmpty(policyMap)) {
                return false;
            }
            List<Map<String, Object>> statements = (List<Map<String, Object>>) policyMap.get("Statement");
            if (ListPlainWraps.isEmpty(statements)) {
                return false;
            }
            for (Map<String, Object> statement : statements) {
                if (!StringUtils.equalsIgnoreCase((String) statement.get("Effect"), "Allow")) {    // $NON-NLS-1$ // $NON-NLS-2$
                    continue;
                }
                if (!CollectionPlainWraps.containsString((List<String>) statement.get("Action"), "s3:GetObject")) {    // $NON-NLS-1$ // $NON-NLS-2$
                    continue;
                }
                if (!CollectionPlainWraps.containsString((List<String>) statement.get("Resource"), "arn:aws:s3:::" + bucketName + "/*")) {    // $NON-NLS-1$ // $NON-NLS-2$ // $NON-NLS-3$
                    continue;
                }
                Map<String, Object> principal = (Map<String, Object>) statement.get("Principal");    // $NON-NLS-1$
                List<?> aws = (principal == null) ? null : (List<?>) principal.get("AWS");    // $NON-NLS-1$
                if (StringUtils.equals(Objects.toString(ListPlainWraps.getFirst(aws)), "*")) {    // $NON-NLS-1$
                    return true;
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean makeBucket(@Nonnull MinioClient minioClient, @Nonnull String bucketName, @Nullable String region) {
        if (StringUtils.isBlank(bucketName)) {
            return false;
        }
        try {
            MakeBucketArgs.Builder argsBuilder = MakeBucketArgs.builder().bucket(bucketName);
            StringUtilsWraps.ifNotBlank(region, argsBuilder::region);
            minioClient.makeBucket(argsBuilder.objectLock(true).build());
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public static boolean makeBucketPublic(@Nonnull MinioClient minioClient, @Nonnull String bucketName) {
        if (StringUtils.isBlank(bucketName)) {
            return false;
        }
        Map<String, Object> readonlyPolicy = new HashMap<>();
        // Fixed version, only "2012-10-17" and "2008-10-17" available
        readonlyPolicy.put("Version", "2012-10-17");    // $NON-NLS-1$ // $NON-NLS-2$
        Map<String, Object> policyStatement = new HashMap<>();
        policyStatement.put("Effect", "Allow");    // $NON-NLS-1$ // $NON-NLS-2$
        policyStatement.put("Principal", Collections.singletonMap("AWS", Collections.singletonList("*")));    // $NON-NLS-1$ // $NON-NLS-2$ // $NON-NLS-3$
        policyStatement.put("Action", Collections.singletonList("s3:GetObject"));    // $NON-NLS-1$ // $NON-NLS-2$
        policyStatement.put("Resource", Collections.singletonList("arn:aws:s3:::" + bucketName + "/*"));    // $NON-NLS-1$ // $NON-NLS-2$ // $NON-NLS-3$
        readonlyPolicy.put("Statement", Collections.singletonList(policyStatement));    // $NON-NLS-1$
        String readonlyJson = JsonParserWraps.toJsonString(readonlyPolicy);
        if (StringUtils.isBlank(readonlyJson)) {
            return false;
        }
        try {
            SetBucketPolicyArgs policyArgs = SetBucketPolicyArgs.builder().bucket(bucketName).config(readonlyJson).build();
            minioClient.setBucketPolicy(policyArgs);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }
}
