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

package com.yookue.commonplexus.springutil.validation.sequence;


import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;
import com.yookue.commonplexus.springutil.validation.group.BindingGroup;


/**
 * Validation sequence for default and controller binding
 *
 * @author David Hsing
 * @see jakarta.validation.Validator
 * @see org.springframework.validation.Validator
 * @see jakarta.validation.Valid
 * @see org.springframework.validation.annotation.Validated
 */
@GroupSequence(value = {Default.class, BindingGroup.class})
@SuppressWarnings("unused")
public interface DefaultBindingSequence {
}
