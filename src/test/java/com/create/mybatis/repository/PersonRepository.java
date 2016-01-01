package com.create.mybatis.repository;

import com.create.mybatis.model.Person;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * {@link MyBatisRepository} for {@link Person} object.
 */
public interface PersonRepository extends MyBatisRepository<Person, Long> {

    List<Person> findByFirstName(final String firstName);

    /**
     * Example of usage {@link Select} annotation for query construction.
     */
    @Select("SELECT id, firstName, lastName FROM person WHERE lastName = #{lastName}")
    List<Person> findByLastName(final String lastName);
}
