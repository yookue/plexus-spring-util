/*
 * Copyright (c) 2025 Yookue Ltd. All rights reserved.
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

package com.yookue.commonplexus.springutil.aspect;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.session.Session;
import org.springframework.session.data.redis.SessionRepositoryUtils;
import org.springframework.session.web.http.SessionAdapterUtils;
import com.yookue.commonplexus.javaseutil.constant.StringVariantConst;
import com.yookue.commonplexus.javaseutil.util.MapPlainWraps;
import com.yookue.commonplexus.springutil.constant.SpringAttributeConst;
import com.yookue.commonplexus.springutil.event.HttpSessionSavedEvent;
import com.yookue.commonplexus.springutil.util.ApplicationContextWraps;
import com.yookue.commonplexus.springutil.util.ReflectionUtilsWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * Aspect when {@link jakarta.servlet.http.HttpSession} saved
 *
 * @author David Hsing
 *
 * @see com.yookue.commonplexus.springutil.event.HttpSessionSavedEvent
 * @see "org.springframework.session.web.http.HttpSessionAdapter"
 */
@Aspect
public class SessionRepositoryAspect implements ApplicationContextAware, InitializingBean {
    private final ThreadLocal<Map<String, Object>> originAttributes = new ThreadLocal<>();
    private final ThreadLocal<Map<String, Object>> redisAttributes = new ThreadLocal<>();
    private ServletContext servletContext;

    @Setter
    private ApplicationContext applicationContext;

    @Getter
    @Setter
    private boolean fireChangedOnly;

    @Getter
    @Setter
    private String[] ignoredAttributes;

    @Getter
    @Setter
    private boolean removeAttributePrefix;

    @Override
    public void afterPropertiesSet() {
        servletContext = ApplicationContextWraps.getServletContext(applicationContext);
    }

    @Before("execution(* org.springframework.session.SessionRepository.save(..)) && args(session)")
    @SuppressWarnings({"unchecked", "unused"})
    public void beforeSave(@Nonnull JoinPoint point, @Nonnull Session session) {
        if (SessionRepositoryUtils.isRedisSession(session)) {
            Object delta = ReflectionUtilsWraps.getField(session, StringVariantConst.DELTA, true);
            Map<String, Object> clonedDelta = MapPlainWraps.newHashMapWithin((Map<String, Object>) delta);
            filterIgnoredAttributes(clonedDelta);
            if (MapPlainWraps.isNotEmpty(clonedDelta) && removeAttributePrefix) {
                Map<String, Object> changedAttrs = new LinkedHashMap<>(clonedDelta.size());
                for (Map.Entry<String, Object> entry : clonedDelta.entrySet()) {
                    changedAttrs.put(StringUtils.removeStartIgnoreCase(entry.getKey(), SpringAttributeConst.SESSION_ATTR), entry.getValue());
                }
                redisAttributes.set(changedAttrs);
            } else {
                redisAttributes.set(clonedDelta);
            }
            return;
        }
        Map<String, Object> beforeAttrs = new HashMap<>();
        for (String attributeName : session.getAttributeNames()) {
            beforeAttrs.put(attributeName, session.getAttribute(attributeName));
        }
        originAttributes.set(beforeAttrs);
    }

    @After("execution(* org.springframework.session.SessionRepository.save(..)) && args(session)")
    @SuppressWarnings("unused")
    public void afterSave(@Nonnull JoinPoint point, @Nonnull Session session) {
        HttpSession alias = SessionAdapterUtils.toHttpSession(session, servletContext);
        if (SessionRepositoryUtils.isRedisSession(session)) {
            HttpSessionSavedEvent event = new HttpSessionSavedEvent(alias, redisAttributes.get());
            if (!fireChangedOnly || MapPlainWraps.isNotEmpty(redisAttributes.get())) {
                applicationContext.publishEvent(event);
            }
            redisAttributes.remove();
            return;
        }
        try {
            Map<String, Object> beforeAttrs = originAttributes.get();
            Map<String, Object> changedAttrs = calcChangedAttributes(session, beforeAttrs);
            HttpSessionSavedEvent event = new HttpSessionSavedEvent(alias, changedAttrs);
            if (!fireChangedOnly || MapPlainWraps.isNotEmpty(changedAttrs)) {
                applicationContext.publishEvent(event);
            }
        } finally {
            originAttributes.remove();
        }
    }

    @Nonnull
    private Map<String, Object> calcChangedAttributes(@Nonnull Session session, @Nonnull Map<String, Object> beforeAttributes) {
        Map<String, Object> changed = new HashMap<>();
        // Attributes that have been added or modified
        for (String attributeName : session.getAttributeNames()) {
            Object currentValue = session.getAttribute(attributeName);
            Object previousValue = beforeAttributes.get(attributeName);
            if (!Objects.equals(currentValue, previousValue)) {
                changed.put(attributeName, currentValue);
            }
        }
        // Attributes that have been removed
        for (String attributeName : beforeAttributes.keySet()) {
            if (!session.getAttributeNames().contains(attributeName)) {
                changed.put(attributeName, null);
            }
        }
        filterIgnoredAttributes(changed);
        return changed;
    }

    private void filterIgnoredAttributes(@Nullable Map<String, Object> attributes) {
        if (ArrayUtils.isNotEmpty(ignoredAttributes) && MapPlainWraps.isNotEmpty(attributes)) {
            Arrays.stream(ignoredAttributes).forEach(attributes::remove);
        }
    }
}
