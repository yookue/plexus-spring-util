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

package com.yookue.commonplexus.springutil.event.listener;


import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.annotation.Nonnull;
import jakarta.servlet.ServletContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.util.Assert;
import com.yookue.commonplexus.javaseutil.constant.AssertMessageConst;
import com.yookue.commonplexus.javaseutil.constant.CharVariantConst;
import com.yookue.commonplexus.javaseutil.constant.InetAddressConst;
import com.yookue.commonplexus.javaseutil.constant.SymbolVariantConst;
import com.yookue.commonplexus.javaseutil.enumeration.InetProtocolType;
import com.yookue.commonplexus.javaseutil.util.InetAddressWraps;
import com.yookue.commonplexus.javaseutil.util.LocalDateWraps;
import com.yookue.commonplexus.springutil.util.ApplicationContextWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * Listens events of {@link org.springframework.context.ApplicationEvent}
 *
 * @author David Hsing
 * @see org.springframework.context.event.GenericApplicationListener
 */

@SuppressWarnings("unused")
public abstract class AbstractApplicationEventListener<E extends ApplicationEvent> implements ApplicationListener<E>, ApplicationContextAware, Ordered {
    @Getter
    private String applicationName;

    @Getter
    private String container;

    @Getter
    private String contextPath;

    @Getter
    private String serverHost;

    @Getter
    private Integer serverPort;

    @Getter
    private String accessUrl;

    @Getter
    private LocalDateTime startupTime;

    @Getter
    @Setter
    private int order = 0;

    @Setter
    protected ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(@Nonnull E event) {
        detectEnvironment(event);
        preApplicationEvent(event);
        handleApplicationEvent(event);
        postApplicationEvent(event);
    }

    private void detectEnvironment(@Nonnull E event) {
        serverHost = StringUtils.defaultIfBlank(InetAddressWraps.getLocalIpAddressQuietly(), InetAddressConst.LOCALHOST_IPV4);
        Assert.notNull(applicationContext, AssertMessageConst.NOT_NULL);
        applicationName = ApplicationContextWraps.getApplicationName(applicationContext);
        ServletContext servletContext = ApplicationContextWraps.getServletContext(applicationContext);
        Assert.notNull(servletContext, AssertMessageConst.NOT_NULL);
        container = servletContext.getServerInfo();
        contextPath = StringUtils.trimToNull(servletContext.getContextPath());
        startupTime = LocalDateWraps.fromEpochMillis(applicationContext.getStartupDate());
        if (event.getSource() instanceof WebServer alias) {
            serverPort = alias.getPort();
        }
        if (serverPort == null) {
            serverPort = ApplicationContextWraps.getLocalServerPort(applicationContext);
        }
        if (serverPort == null || serverPort <= 0) {
            return;
        }
        // Build server url
        StringBuilder builder = new StringBuilder();
        builder.append(Objects.equals(serverPort, 443) ? InetProtocolType.HTTPS.getValue() : InetProtocolType.HTTP.getValue());
        builder.append(SymbolVariantConst.PROTOCOL_DELIMITER).append(serverHost);
        if (serverPort != 80 && serverPort != 443) {
            builder.append(CharVariantConst.COLON).append(serverPort);
        }
        if (StringUtils.isNotBlank(contextPath)) {
            builder.append(contextPath);
        }
        accessUrl = builder.toString();
    }

    protected abstract void handleApplicationEvent(@Nonnull E event);

    @SuppressWarnings("EmptyMethod")
    protected void preApplicationEvent(@Nonnull E event) {
    }

    @SuppressWarnings("EmptyMethod")
    protected void postApplicationEvent(@Nonnull E event) {
    }
}
