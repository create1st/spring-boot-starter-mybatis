package com.create.mybatis.repository.support;

import com.create.mybatis.repository.query.MyBatisEntityInformation;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.support.AbstractEntityInformation;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * MyBatis class for implementations of {@link EntityInformation} using a {@link MyBatisEntityInformation} instance
 * to lookup the necessary information.
 */
public class MappingMyBatisEntityInformation<T, ID extends Serializable> extends AbstractEntityInformation<T, ID>
        implements MyBatisEntityInformation<T, ID> {

    private final Field idField;

    /**
     * Creates a new {@link MappingMyBatisEntityInformation} from the given domain class.
     *
     * @param domainClass must not be {@literal null}.
     */
    public MappingMyBatisEntityInformation(final Class<T> domainClass) {
        super(domainClass);
        this.idField = getIdField(domainClass);
        Assert.notNull(this.idField, String.format("Domain class %s must have ID annotated property", domainClass));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ID getId(final T entity) {
        try {
            return (ID) idField.get(entity);
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<ID> getIdType() {
        return (Class<ID>) idField.getType();
    }

    private Field getIdField(final Class<T> domainClass) {
        Field idField = null;
        for (final Field field : domainClass.getDeclaredFields()) {
            final Class type = field.getType();
            final String name = field.getName();
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                idField.setAccessible(true);
                break;
            }
        }
        return idField;
    }

    @Override
    public String getIdAttribute() {
        return idField.getName();
    }

    @Override
    public void setId(final T entity, final ID id) {
        try {
            idField.set(entity, id);
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
