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
