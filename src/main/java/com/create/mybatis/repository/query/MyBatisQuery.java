/*
 * Copyright 2016 Sebastian Gil.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.create.mybatis.repository.query;

import com.create.mybatis.repository.support.MyBatisMapperProvider;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * Base class for {@link RepositoryQuery} implementations for MyBatis.
 */
public class MyBatisQuery implements RepositoryQuery {
    private final MyBatisQueryMethod queryMethod;
    private final MyBatisMapperProvider mapperProvider;

    /**
     * Creates a new {@link MyBatisQuery} from the given domain class.
     *
     * @param queryMethod must not be {@literal null}.
     * @param mapperProvider has to be {@literal null} of {@literal Key.USE_DECLARED_QUERY}.
     */
    public MyBatisQuery(final MyBatisQueryMethod queryMethod, final MyBatisMapperProvider mapperProvider) {
        Assert.notNull(queryMethod);
        Assert.notNull(mapperProvider);
        this.queryMethod = queryMethod;
        this.mapperProvider = mapperProvider;
    }

    @Override
    public Object execute(final Object[] parameters) {
        final Class<?> domainClass = queryMethod.getEntityInformation().getJavaType();
        final Object mapper = mapperProvider.getMapper(domainClass);
        return ReflectionUtils.invokeMethod(queryMethod.getMethod(), mapper, parameters);
    }

    @Override
    public QueryMethod getQueryMethod() {
        return queryMethod;
    }
}
