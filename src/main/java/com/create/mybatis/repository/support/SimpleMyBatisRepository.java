package com.create.mybatis.repository.support;

import com.create.mybatis.repository.MyBatisRepository;
import com.create.mybatis.repository.query.MyBatisEntityInformation;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of the {@link MyBatisRepository} interface. This will offer
 * you a more sophisticated interface than the plain {@link SqlSessionTemplate} access.
 *
 * @param <T>  the type of the entity to handle
 * @param <ID> the type of the entity's identifier
 */
@Repository
@Transactional
public class SimpleMyBatisRepository<T, ID extends Serializable> implements MyBatisRepository<T, ID> {
    public static final String SELECT_STATEMENT = "find";
    public static final String DELETE_STATEMENT = "delete";
    public static final String INSERT_STATEMENT = "save";
    public static final String RETURNED_ID = "returnedId";
    public static final String ENTITY = "entity";
    public static final String UPDATE_STATEMENT = "update";
    private final MyBatisEntityInformation<T, ID> metadata;
    private final SqlSessionTemplate sqlSessionTemplate;

    /**
     * Creates a new {@link SimpleMyBatisRepository} to manage objects of the given {@link SqlSessionTemplate}.
     *
     * @param metadata           must not be {@literal null}.
     * @param sqlSessionTemplate must not be {@literal null}.
     */
    public SimpleMyBatisRepository(final MyBatisEntityInformation<T, ID> metadata, final SqlSessionTemplate sqlSessionTemplate) {
        Assert.notNull(metadata);
        Assert.notNull(sqlSessionTemplate);
        this.metadata = metadata;
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <S extends T> S save(final S entity) {
        Assert.notNull(entity, "The given entity must not be null!");
        final Map<String, Object> params = new HashMap<>();
        params.put(ENTITY, entity);
        if (metadata.isNew(entity)) {
            params.put(RETURNED_ID, 0);
            sqlSessionTemplate.insert(INSERT_STATEMENT, params);
            metadata.setId(entity, (ID) params.get(RETURNED_ID));
        } else {
            sqlSessionTemplate.insert(UPDATE_STATEMENT, params);
        }
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> save(final Iterable<S> entities) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        final List<S> savedEntities = new ArrayList<>();
        for (final T entity : entities) {
            save(entity);
        }
        return savedEntities;
    }

    @Override
    public T findOne(final ID id) {
        Assert.notNull(id, "The given id must not be null!");
        final Map<String, ID> params = new HashMap<>();
        params.put(metadata.getIdAttribute(), id);
        return sqlSessionTemplate.selectOne(SELECT_STATEMENT, params);
    }

    @Override
    public boolean exists(ID id) {
        Assert.notNull(id, "The given id must not be null!");
        return findOne(id) != null;
    }

    @Override
    public List<T> findAll() {
        return sqlSessionTemplate.selectList(SELECT_STATEMENT);
    }

    @Override
    public Iterable<T> findAll(final Iterable<ID> ids) {
        Assert.notNull(ids, "The given Iterable of entities not be null!");
        final List<T> entities = new ArrayList<>();
        for (final ID id : ids) {
            findOne(id);
        }
        return entities;
    }

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    public void delete(final ID id) {
        Assert.notNull(id, "The given id must not be null!");
        final Map<String, ID> params = new HashMap<>();
        params.put(metadata.getIdAttribute(), id);
        sqlSessionTemplate.delete(DELETE_STATEMENT, params);
    }

    @Override
    public void delete(final T entity) {
        Assert.notNull(entity, "The given entity must not be null!");
        delete(metadata.getId(entity));
    }

    @Override
    public void delete(final Iterable<? extends T> entities) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        for (final T entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        sqlSessionTemplate.delete(DELETE_STATEMENT);
    }
}


