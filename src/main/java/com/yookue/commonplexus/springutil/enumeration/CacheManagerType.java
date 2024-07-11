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

package com.yookue.commonplexus.springutil.enumeration;


import com.yookue.commonplexus.javaseutil.support.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


/**
 * Enumerations of Spring cache types
 *
 * @author David Hsing
 * @see org.springframework.boot.autoconfigure.cache.CacheProperties
 */
@AllArgsConstructor
@Getter
@ToString
@SuppressWarnings("unused")
public enum CacheManagerType implements ValueEnum<String> {
    CAFFEINE("org.springframework.cache.caffeine.CaffeineCacheManager"),    // $NON-NLS-1$
    COUCHBASE("org.springframework.data.couchbase.cache.CouchbaseCacheManager"),    // $NON-NLS-1$
    JCACHE("org.springframework.cache.jcache.JCacheCacheManager"),    // $NON-NLS-1$
    INFINISPAN("org.infinispan.manager.DefaultCacheManager"),    // $NON-NLS-1$
    REDIS("org.springframework.data.redis.cache.RedisCacheManager");    // $NON-NLS-1$

    private final String value;
}
