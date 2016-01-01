package com.create.mybatis.repository.support;

import com.create.mybatis.repository.MyBatisRepository;
import com.create.mybatis.repository.query.MyBatisEntityInformation;
import com.create.mybatis.repository.query.MyBatisQueryLookupStrategy;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.mapping.ResultMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.repository.core.EntityInformation;
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
    private final SqlSessionTemplate sqlSessionTemplate;
    private final MyBatisMapperProvider provider;

    /**
     * Creates a new {@link MyBatisRepositoryFactory} with the given {@link SqlSessionTemplate}.
     *
     * @param sqlSessionTemplate must not be {@literal null}.
     */
    public MyBatisRepositoryFactory(final SqlSessionTemplate sqlSessionTemplate) {
        Assert.notNull(sqlSessionTemplate);
        this.sqlSessionTemplate = sqlSessionTemplate;
        this.provider = new MyBatisMapperProvider(sqlSessionTemplate);
    }

    @Override
    public <T, ID extends Serializable> MyBatisEntityInformation<T, ID> getEntityInformation(final Class<T> domainClass) {
        final Object mapper = provider.getMapper(domainClass);
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
        return new SimpleMyBatisRepository(entityInformation, sqlSessionTemplate);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(final RepositoryMetadata metadata) {
        return SimpleMyBatisRepository.class;
    }

    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(final Key key, final EvaluationContextProvider evaluationContextProvider) {
        return new MyBatisQueryLookupStrategy(sqlSessionTemplate, key);
    }
}
