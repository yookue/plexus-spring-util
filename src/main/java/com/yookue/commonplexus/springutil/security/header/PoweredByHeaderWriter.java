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

package com.yookue.commonplexus.springutil.security.header;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.header.HeaderWriter;
import com.yookue.commonplexus.javaseutil.constant.HttpHeaderConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * {@link org.springframework.security.web.header.HeaderWriter} for "X-Powered-By" header
 *
 * @author David Hsing
 * @see org.springframework.security.web.header.HeaderWriter
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public class PoweredByHeaderWriter implements HeaderWriter {
    private String headerValue;

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(headerValue)) {
            response.setHeader(HttpHeaderConst.X_POWERED_BY, headerValue);
        }
    }
}
