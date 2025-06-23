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

package com.yookue.commonplexus.springutil.structure;


import java.io.Serializable;
import org.apache.commons.lang3.tuple.Pair;
import com.yookue.commonplexus.springutil.enumeration.SliderTriggerType;
import lombok.experimental.Accessors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Structure for slider captcha
 *
 * @author David Hsing
 *
 * @reference "https://github.com/caijf/rc-slider-captcha"
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class SliderCaptchaStruct implements Serializable {
    private Integer x;
    private Integer y;
    private Integer duration;
    private Integer errorCount;
    private Integer sliderOffsetX;
    private Pair<Integer, Integer>[] trail;
    private SliderTriggerType targetType;
}
