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


import com.yookue.commonplexus.javaseutil.structure.ChildrenTreeStruct;
import lombok.experimental.Accessors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;






/**
 * Structure for Tree of Ant Design
 *
 * @author David Hsing
 *
 * @reference "https://ant.design/components/tree"
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings({"unused", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class AntTreeStruct extends ChildrenTreeStruct<AntTreeStruct> {
    private String key;
    private Boolean checkable;
    private Boolean disableCheckbox;
    private Boolean disabled;
    private String icon;
    private Boolean isLeaf;
    private Boolean selectable;
    private String title;
    private String value;
}
