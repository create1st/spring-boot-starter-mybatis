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
                mapper = getMapperForResultMapId(resultMap.getId());
                if (mapper != null) break;
            }
        }
        return mapper;
    }

    private <T> T getMapperForResultMapId(final String resultMapId) {
        T mapper = null;
        final int resultMapNameIndex = resultMapId.lastIndexOf('.');
        if (resultMapNameIndex != -1) {
            final String mapperClassName = resultMapId.substring(0, resultMapNameIndex);
            mapper = findMapperInstance(mapperClassName);
        }
        return mapper;
    }

    @SuppressWarnings("unchecked")
    private <T> T findMapperInstance(final String mapperClassName) {
        T mapper = null;
        try {
            final Class<T> mapperClass = (Class<T>) Class.forName(mapperClassName);
            if (sqlSessionTemplate.getConfiguration().hasMapper(mapperClass)) {
                mapper = sqlSessionTemplate.getMapper(mapperClass);
            }
        } catch (final Exception e) {
            // Do ignore as this is expected to happen as not all checked mapperClassNames are instances of mappers.
        }
        return mapper;
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }
}
