package com.create.mybatis.repository.support;

import org.apache.ibatis.mapping.ResultMap;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * Helper class for finding out MyBatis mapper for domain class.
 */
public class MyBatisMapperProvider {
    private final SqlSessionTemplate sqlSessionTemplate;

    public MyBatisMapperProvider(final SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public <T> T getMapper(final Class<T> domainClass) {
        T mapper = null;
        for (final ResultMap resultMap : sqlSessionTemplate.getConfiguration().getResultMaps()) {
            if (resultMap.getType().equals(domainClass)) {
                mapper = getMapper(resultMap.getId());
                if (mapper != null) break;
            }
        }
        return mapper;
    }

    @SuppressWarnings("unchecked")
    private <T> T getMapper(final String resultMapId) {
        T mapper = null;
        final int resultMapNameIndex = resultMapId.lastIndexOf('.');
        if (resultMapNameIndex != -1) {
            final String mapperClassName = resultMapId.substring(0, resultMapNameIndex);
            final Class<T> mapperClass;
            try {
                mapperClass = (Class<T>) Class.forName(mapperClassName);
                if (sqlSessionTemplate.getConfiguration().hasMapper(mapperClass)) {
                    mapper = sqlSessionTemplate.getMapper(mapperClass);
                }
            } catch (final Exception e) {
                // Do ignore as this is expected to happen.
            }
        }
        return mapper;
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }
}
