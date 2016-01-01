package com.create.mybatis.repository.query;

import com.create.mybatis.repository.support.MyBatisMapperProvider;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * Base class for {@link RepositoryQuery} implementations for MyBatis.
 */
public class MyBatisQuery implements RepositoryQuery {
    private final MyBatisQueryMethod queryMethod;
    private final MyBatisMapperProvider provider;

    public MyBatisQuery(final MyBatisQueryMethod queryMethod, final MyBatisMapperProvider provider) {
        Assert.notNull(queryMethod);
        Assert.notNull(provider);
        this.queryMethod = queryMethod;
        this.provider = provider;
    }

    @Override
    public Object execute(final Object[] parameters) {
        final Class<?> domainClass = queryMethod.getEntityInformation().getJavaType();
        final Object mapper = provider.getMapper(domainClass);
        return ReflectionUtils.invokeMethod(queryMethod.getMethod(), mapper, parameters);
    }

    @Override
    public QueryMethod getQueryMethod() {
        return queryMethod;
    }
}
