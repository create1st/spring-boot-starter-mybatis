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
