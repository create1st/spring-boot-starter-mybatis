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
     * @param mapperProvider must not be {@literal null}.
     * @param key has to be {@literal null} of {@literal Key.USE_DECLARED_QUERY}.
     */
    public MyBatisQueryLookupStrategy(final MyBatisMapperProvider mapperProvider, final Key key) {
        Assert.isTrue(key == null || Key.USE_DECLARED_QUERY.equals(key), "MyBatis query strategy is supported only for declared statements");
        Assert.notNull(mapperProvider);
        this.provider = mapperProvider;
    }

    @Override
    public RepositoryQuery resolveQuery(final Method method, final RepositoryMetadata metadata, NamedQueries namedQueries) {
        final MyBatisQueryMethod queryMethod = new MyBatisQueryMethod(method, metadata);
        return new MyBatisQuery(queryMethod, provider);
    }
}
