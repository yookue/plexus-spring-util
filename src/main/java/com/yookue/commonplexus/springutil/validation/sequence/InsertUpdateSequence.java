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


import javax.validation.GroupSequence;
import com.yookue.commonplexus.springutil.validation.group.InsertGroup;
import com.yookue.commonplexus.springutil.validation.group.UpdateGroup;


/**
 * Validation sequence for persistence insert and update
 *
 * @author David Hsing
 * @see javax.validation.Validator
 * @see org.springframework.validation.Validator
 * @see javax.validation.Valid
 * @see org.springframework.validation.annotation.Validated
 */
@GroupSequence(value = {InsertGroup.class, UpdateGroup.class})
@SuppressWarnings("unused")
public interface InsertUpdateSequence {
}
