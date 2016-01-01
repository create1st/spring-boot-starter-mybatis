package com.create.mybatis.repository.query;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * {@link QueryLookupStrategy} to create {@link MyBatisQuery} instances.
 */
public class MyBatisQueryLookupStrategy implements QueryLookupStrategy {
    private final SqlSessionTemplate sqlSessionTemplate;

    public MyBatisQueryLookupStrategy(final SqlSessionTemplate sqlSessionTemplate, final Key key) {
        Assert.isTrue(key == null || Key.USE_DECLARED_QUERY.equals(key), "MyBatis query strategy is supported only for declared statements");
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public RepositoryQuery resolveQuery(final Method method, final RepositoryMetadata metadata, NamedQueries namedQueries) {
        final MyBatisQueryMethod queryMethod = new MyBatisQueryMethod(method, metadata);
        return new MyBatisQuery(queryMethod, sqlSessionTemplate);
    }
}
