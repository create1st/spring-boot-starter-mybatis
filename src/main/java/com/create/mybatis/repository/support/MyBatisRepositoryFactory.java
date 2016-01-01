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

package com.create.mybatis.repository.support;

import com.create.mybatis.repository.MyBatisRepository;
import com.create.mybatis.repository.query.MyBatisEntityInformation;
import com.create.mybatis.repository.query.MyBatisQueryLookupStrategy;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Factory to create {@link MyBatisRepository} instances.
 */
public class MyBatisRepositoryFactory extends RepositoryFactorySupport {
    private final MyBatisMapperProvider mapperProvider;

    /**
     * Creates a new {@link MyBatisRepositoryFactory} with the given {@link SqlSessionTemplate}.
     *
     * @param mapperProvider must not be {@literal null}.
     */
    public MyBatisRepositoryFactory(final MyBatisMapperProvider mapperProvider) {
        Assert.notNull(mapperProvider);
        this.mapperProvider = mapperProvider;
    }

    @Override
    public <T, ID extends Serializable> MyBatisEntityInformation<T, ID> getEntityInformation(final Class<T> domainClass) {
        final Object mapper = mapperProvider.getMapper(domainClass);
        if (mapper == null) {
            throw new MappingException(
                    String.format("Could not lookup mapping metadata for domain class %s!", domainClass.getName()));
        }
        return new MappingMyBatisEntityInformation<>(domainClass);
    }


    @Override
    @SuppressWarnings("unchecked")
    protected Object getTargetRepository(final RepositoryInformation metadata) {
        final MyBatisEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
        return new SimpleMyBatisRepository(entityInformation, mapperProvider.getSqlSessionTemplate());
    }

    @Override
    protected Class<?> getRepositoryBaseClass(final RepositoryMetadata metadata) {
        return SimpleMyBatisRepository.class;
    }

    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(final Key key, final EvaluationContextProvider evaluationContextProvider) {
        return new MyBatisQueryLookupStrategy(mapperProvider, key);
    }
}
