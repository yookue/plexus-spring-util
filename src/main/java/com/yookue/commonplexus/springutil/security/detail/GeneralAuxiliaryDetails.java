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

package com.yookue.commonplexus.springutil.security.detail;


import java.time.LocalDateTime;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import com.yookue.commonplexus.javaseutil.util.FieldUtilsWraps;
import com.yookue.commonplexus.javaseutil.util.LocalDateWraps;
import com.yookue.commonplexus.springutil.util.WebUtilsWraps;
import lombok.Getter;
import lombok.ToString;


/**
 * {@link org.springframework.security.web.authentication.WebAuthenticationDetails} for general auxiliary details
 *
 * @author David Hsing
 * @reference "https://www.cnblogs.com/phoenix-smile/p/5666686.html"
 */
@Getter
@ToString(callSuper = true)
@SuppressWarnings({"JavadocDeclaration", "JavadocLinkAsPlainText"})
public class GeneralAuxiliaryDetails extends WebAuthenticationDetails {
    private final LocalDateTime timestamp;

    public GeneralAuxiliaryDetails(@Nonnull HttpServletRequest request) {
        super(request);
        String address = WebUtilsWraps.getRemoteAddressQuietly(request);
        if (StringUtils.isNotBlank(address)) {
            FieldUtilsWraps.writeField(this, "remoteAddress", address, true);    // $NON-NLS-1$
        }
        timestamp = LocalDateWraps.getCurrentDateTime();
    }
}
