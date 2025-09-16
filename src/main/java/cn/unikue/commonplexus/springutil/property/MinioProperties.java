/*
 * Copyright (c) 2020 Unikue Ltd. All rights reserved.
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

package cn.unikue.commonplexus.springutil.property;


import java.io.Serializable;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.convert.DurationUnit;
import cn.unikue.commonplexus.springutil.enumeration.MinioAccessType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Properties of {@link io.minio.MinioClient}
 *
 * @author David Hsing
 */
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public class MinioProperties implements Serializable {
    /**
     * Indicates whether to automatically create bucket if not exists
     * <p>
     * Default is {@code true}
     */
    private Boolean autoCreateBucket = true;

    /**
     * Endpoint host, is a domain name, IPv4 or IPv6 address, such as
     * <pre>
     *     s3.amazonaws.com
     *     localhost
     *     127.0.0.1
     * </pre>
     */
    private String endpoint;

    /**
     * Endpoint port, range in [1, 65535]
     * <p>
     * Default is 9000
     */
    private Integer port = 9000;

    /**
     * Indicates the protocol to access endpoint, using HTTPS or HTTP
     */
    private Boolean sslEnabled;

    /**
     * Endpoint region
     */
    private String region;

    /**
     * Access key to access endpoint
     */
    private String accessKey;

    /**
     * Secret key to access endpoint
     */
    private String secretKey;

    /**
     * Default bucket for endpoint
     */
    private String bucketName;

    /**
     * The canned access control for accessing files
     * <p>
     * For example: "private", "public-read", "public-read-write"
     */
    private MinioAccessType accessControl;

    /**
     * Name for user agent
     */
    private String userAgentName;

    /**
     * Version for user agent
     */
    private String userAgentVersion;

    /**
     * Connect timeout to access endpoint
     */
    private Duration connectTimeout = Duration.ofSeconds(10L);

    /**
     * Read timeout to access endpoint
     * <p>
     * If a duration suffix is not specified, seconds will be used
     */
    @DurationUnit(value = ChronoUnit.SECONDS)
    private Duration readTimeout = Duration.ofSeconds(30L);

    /**
     * Write timeout to access endpoint
     * <p>
     * If a duration suffix is not specified, seconds will be used
     */
    @DurationUnit(value = ChronoUnit.SECONDS)
    private Duration writeTimeout = Duration.ofSeconds(30L);

    /**
     * Enables dual-stack endpoint for Amazon S3 endpoint
     */
    private Boolean dualStackEnabled;

    /**
     * Enables virtual-style endpoint
     */
    private Boolean virtualStyleEnabled;
}
