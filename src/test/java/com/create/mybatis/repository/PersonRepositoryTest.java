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

import com.create.mybatis.application.configuration.MyBatisConfiguration;
import com.create.mybatis.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@SpringApplicationConfiguration(classes = {MyBatisConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testCount() {
        // given
        // when
        final long count = personRepository.count();
        // then
        assertThat(count, is(3L));
    }

    @Test
    public void testSaveNewPerson() {
        // given
        // when
        final Person person = personRepository.save(new Person("Leon", "Kowalski"));
        // then
        assertThat(person, notNullValue());
        assertThat(person.getId(), notNullValue());
        assertThat(personRepository.count(), is(4L));
    }

    @Test
    public void testSaveExistingPerson() {
        // given
        // when
        final Person person = personRepository.save(new Person(0L, "Jan", "Nowak"));
        // then
        assertThat(person, notNullValue());
        assertThat(person.getId(), notNullValue());
        assertThat(personRepository.count(), is(3L));
        assertThat(personRepository.findOne(person.getId()).getLastName(), is("Nowak"));
    }

    @Test
    public void testFindOnePerson() {
        // given
        // when
        final Person person = personRepository.findOne(0L);
        // then
        assertThat(person, notNullValue());
    }

    @Test
    public void testFindAllPersons() {
        // given
        // when
        final List<Person> persons = personRepository.findAll();
        // then
        assertThat(persons, notNullValue());
        assertThat(persons.size(), is(3));
    }

    @Test
    public void testFindPersonsByFirstName() {
        // given
        // when
        final List<Person> persons = personRepository.findByFirstName("Jan");
        // then
        assertThat(persons, notNullValue());
        assertThat(persons.size(), is(1));
    }

    @Test
    public void testFindPersonsByLastName() {
        // given
        // when
        final List<Person> persons = personRepository.findByLastName("Nowak");
        // then
        assertThat(persons, notNullValue());
        assertThat(persons.size(), is(2));
    }

    @Test
    public void testDeleteOnePerson() {
        // given
        // when
        personRepository.delete(0L);
        // then
        assertThat(personRepository.findOne(0L), nullValue());
    }

    @Test
    public void testDeleteAllPersons() {
        // given
        // when
        personRepository.deleteAll();
        // then
        assertThat(personRepository.findAll(), empty());
    }
}
