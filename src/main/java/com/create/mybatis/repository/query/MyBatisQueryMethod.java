package com.create.mybatis.repository.query;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * MyBatis specific implementation of {@link QueryMethod}.
 */
public class MyBatisQueryMethod extends QueryMethod {

    private final Method method;

    /**
     * Creates a {@link MyBatisQueryMethod}.
     *
     * @param method             must not be {@literal null}
     * @param metadata           must not be {@literal null}
     */
    public MyBatisQueryMethod(final Method method, final RepositoryMetadata metadata) {
        super(method, metadata);
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}
