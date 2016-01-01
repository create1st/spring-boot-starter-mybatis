package com.create.mybatis.repository;

import com.create.mybatis.application.configuration.MyBatisConfiguration;
import com.create.mybatis.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Before
    public void setup() {
        System.out.println(personRepository.save(new Person("Jan", "Kowalski")));
        System.out.println(personRepository.save(new Person("Karol", "Nowak")));
        System.out.println(personRepository.save(new Person("Renata", "Nowak")));
    }

    @Test
    public void testFindOnePerson() {
        // given
        final List<Person> persons = personRepository.findAll();

        // when
        final Person person = personRepository.findOne(persons.get(0).getId());
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
        final List<Person> persons = personRepository.findAll();
        // when
        personRepository.delete(persons.get(0).getId());
        // then
        assertThat(personRepository.findOne(persons.get(0).getId()), nullValue());
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
