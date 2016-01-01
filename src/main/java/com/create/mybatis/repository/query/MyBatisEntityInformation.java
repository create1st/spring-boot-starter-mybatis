/*
 * Copyright 2016 Sebastian Gil.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.create.mybatis.repository.query;

import org.springframework.data.repository.core.EntityInformation;

import java.io.Serializable;

/**
 * MyBatis specific {@link EntityInformation}.
 */
public interface MyBatisEntityInformation<T, ID extends Serializable> extends EntityInformation<T, ID> {
    /**
     * Returns the attribute that the id will be persisted to.
     *
     * @return Id attribute name.
     */
    String getIdAttribute();

    /**
     * Set ID value for created entity.
     *
     * @param entity Entity
     * @param id New ID value
     */
    void setId(final T entity, final ID id);
}
