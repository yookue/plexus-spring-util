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

package com.yookue.commonplexus.springutil.audit;


import java.util.Optional;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.AuditorAware;
import com.yookue.commonplexus.springutil.util.SecurityUtilsWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * Implementation of {@link org.springframework.data.domain.AuditorAware} for providing current auditor name
 *
 * @author David Hsing
 *
 * @reference "https://docs.spring.io/spring-data/jpa/reference/auditing.html"
 */
@Getter
@Setter
@SuppressWarnings({"JavadocDeclaration", "JavadocLinkAsPlainText"})
public class SecurityContextAuditorAware implements AuditorAware<String> {
    private String defaultAuditor = StringUtils.EMPTY;

    @Nonnull
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(ObjectUtils.firstNonNull(SecurityUtilsWraps.getContextAuthenticationUsername(), defaultAuditor, StringUtils.EMPTY));
    }
}
