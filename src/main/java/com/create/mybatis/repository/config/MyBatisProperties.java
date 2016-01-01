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

package com.create.mybatis.repository.config;

import org.apache.ibatis.session.ExecutorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

/**
 * MyBatis {@link ConfigurationProperties}
 */
@ConfigurationProperties(prefix = MyBatisProperties.MYBATIS_PREFIX)
public class MyBatisProperties {
    public static final String MYBATIS_PREFIX = "spring.mybatis";

    private ExecutorType executorType = ExecutorType.SIMPLE;
    private String aliasesPackage;
    private String typeHandlersPackage;
    @Value("classpath*:mapper/**/*.xml")
    private Resource[] mapperLocations;

    public ExecutorType getExecutorType() {
        return executorType;
    }

    public void setExecutorType(final ExecutorType executorType) {
        this.executorType = executorType;
    }

    public String getAliasesPackage() {
        return aliasesPackage;
    }

    public void setAliasesPackage(final String aliasesPackage) {
        this.aliasesPackage = aliasesPackage;
    }

    public String getTypeHandlersPackage() {
        return typeHandlersPackage;
    }

    public void setTypeHandlersPackage(final String typeHandlersPackage) {
        this.typeHandlersPackage = typeHandlersPackage;
    }

    public Resource[] getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(final Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }
}
