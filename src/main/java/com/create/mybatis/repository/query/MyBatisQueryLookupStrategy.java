package com.create.mybatis.repository.query;

import com.create.mybatis.repository.support.MyBatisMapperProvider;
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
    private final MyBatisMapperProvider provider;

    /**
     * Creates a new {@link MyBatisQueryLookupStrategy} from the given domain class.
     *
     * @param provider must not be {@literal null}.
     * @param key has to be {@literal null} of {@literal Key.USE_DECLARED_QUERY}.
     */
    public MyBatisQueryLookupStrategy(final MyBatisMapperProvider provider, final Key key) {
        Assert.isTrue(key == null || Key.USE_DECLARED_QUERY.equals(key), "MyBatis query strategy is supported only for declared statements");
        Assert.notNull(provider);
        this.provider = provider;
    }

    @Override
    public RepositoryQuery resolveQuery(final Method method, final RepositoryMetadata metadata, NamedQueries namedQueries) {
        final MyBatisQueryMethod queryMethod = new MyBatisQueryMethod(method, metadata);
        return new MyBatisQuery(queryMethod, provider);
    }
}
