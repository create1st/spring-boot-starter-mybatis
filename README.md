# spring-boot-starter-mybatis

This is unofficial Spring Boot Starter for MyBatis

In opposite to official [MyBatis Spring Boot support](https://github.com/mybatis/mybatis-spring-boot) it is following 
the convention of other Spring Boot data repositories and implements proper Spring Data interfaces.
Advantage of such approach is almost zero effort on switching the persistence layer.

## Issue Tracking

[GitHub Issues](https://github.com/create1st/spring-boot-starter-mybatis/issues)

## Maven dependency

```xml
<dependency>
	<groupId>com.create</groupId>
    <artifactId>spring-boot-starter-mybatis</artifactId>
    <version>1.3.0.RELEASE</version>
</dependency>
```

## Default properties configuration

```properties
spring.mybatis.mapperLocations=classpath*:mapper/**/*.xml       # mappers file
spring.mybatis.typeAliasesPackage=                              # domain object's package 
spring.mybatis.typeHandlersPackage=                             # handler's package
spring.mybatis.executorType=simple                              # mode of execution. Default is SIMPLE
```

## Sample Spring Configuration

```java
@Configuration
@EnableMyBatisRepositories({
        "com.create.mybatis.repository"
})
@EnableAutoConfiguration
public class MyBatisConfiguration {

}
```

## Entity definition

```java
public class Person {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    // Getters and setters
    // ...
}
```

## Sample repository

```java
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
```

## Mapper XML

In order to define queries in XML mapper file you have to follow the convention defined for query ID.
* All **find** methods should have statement definition id starting with **find**
* All **delete** methods should have statement definition id starting with **delete**
* All **save** methods should have statement definition id starting with **save** for persisting a new entity and with **update** for modification of already existing entity

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.create.mybatis.repository.PersonRepository">

    <resultMap id="personResultMap" type="com.create.mybatis.model.Person">
        <id property="id" column="id"/>
        <result property="firstName" column="first_name"/>
        <result property="lastName" column="last_name"/>
    </resultMap>

    <insert id="save" useGeneratedKeys="true"  keyProperty="id">
        INSERT INTO person(firstName, lastName)
        VALUES(#{entity.firstName}, #{entity.lastName})
        <selectKey resultType="long" order="AFTER" keyProperty="returnedId">
            SELECT TOP 1 id as returnedId
            FROM person
            ORDER BY returnedId DESC
        </selectKey>
    </insert>

    <update id="update">
        UPDATE person
        SET
        firstName = #{entity.firstName},
        lastName = #{entity.lastName}
        WHERE id = #{entity.id}
    </update>

    <select id="find" resultMap="personResultMap">
        SELECT id, firstName, lastName
        FROM person
        <if test="id != null">
            WHERE id = #{id}
        </if>
    </select>

    <select id="findByFirstName" resultMap="personResultMap" parameterType="string">
        SELECT id, firstName, lastName
        FROM person
        WHERE firstName = #{firstName}
    </select>

    <delete id="delete">
        DELETE
        FROM person
        <if test="id != null">
            WHERE id = #{id}
        </if>
    </delete>
</mapper>
```