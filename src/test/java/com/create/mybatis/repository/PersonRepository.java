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

package com.create.mybatis.repository;

import com.create.mybatis.model.Person;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * {@link MyBatisRepository} for {@link Person} object.
 */
public interface PersonRepository extends MyBatisRepository<Person, Long> {

    /**
     * Example of usage XML based mappings.
     */
    List<Person> findByFirstName(final String firstName);

    /**
     * Example of usage {@link Select} annotation for query construction.
     */
    @Select("SELECT id, firstName, lastName FROM person WHERE lastName = #{lastName}")
    List<Person> findByLastName(final String lastName);
}
