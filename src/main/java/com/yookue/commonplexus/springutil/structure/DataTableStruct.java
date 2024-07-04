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
import java.util.List;
import java.util.Map;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Structure for DataTables
 *
 * @author David Hsing
 * @reference "https://www.datatables.net"
 * @reference "http://datatables.club"
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings({"unused", "UnusedReturnValue", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class DataTableStruct implements Serializable {
    @JsonProperty(value = "draw")
    private Integer drawTimes = 0;

    private List<Map<String, Object>> recordsDetails;
    private Long recordsTotal = 0L;
    private Long recordsFiltered = 0L;
    private Integer recordsDisplay = 0;

    public DataTableStruct setDrawTimes(@Nullable Integer drawTimes) {
        this.drawTimes = drawTimes;
        return this;
    }

    public DataTableStruct setDrawTimes(@Nullable String drawTimes) {
        this.drawTimes = NumberUtils.toInt(drawTimes);
        return this;
    }
}
