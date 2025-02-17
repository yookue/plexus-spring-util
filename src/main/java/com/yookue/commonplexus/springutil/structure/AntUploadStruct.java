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
import java.math.BigInteger;
import org.springframework.http.MediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Structure for Upload of Ant Design
 *
 * @author David Hsing
 * @reference "https://ant.design/components/upload"
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings({"unused", "UnusedReturnValue", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class AntUploadStruct implements Serializable {
    private String uid;
    private String name;
    private BigInteger size;
    private Long lastModified;
    private Float percent;
    private String url;
    private String thumbUrl;
    private MediaType type;
}
