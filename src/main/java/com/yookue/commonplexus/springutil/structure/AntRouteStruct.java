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
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * Structure for Route of Ant Design Pro
 *
 * @author David Hsing
 * @reference "https://umijs.org/docs/guides/routes"
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings({"unused", "UnusedReturnValue", "JavadocDeclaration", "JavadocLinkAsPlainText"})
public class AntRouteStruct implements Serializable {
    private String path;
    private String name;
    private String icon;
    private String locale;
    private String redirect;
    private String target;
    private String component;

    private Boolean disabled;
    private Boolean flatMenu;
    private Boolean layout;
    private Boolean hideInMenu;
    private Boolean hideInBreadcrumb;
    private Boolean hideChildrenInMenu;

    @JsonProperty(value = "authority")
    private List<String> authorities;

    private List<AntRouteStruct> routes;
    private List<String> wrappers;
}