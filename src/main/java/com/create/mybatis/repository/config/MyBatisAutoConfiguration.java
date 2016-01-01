package com.create.mybatis.repository.config;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for MyBatis.
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(MyBatisProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableTransactionManagement
public class MyBatisAutoConfiguration {
    @Autowired(required = false)
    private Interceptor[] interceptors;

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(final DataSource dataSource,
                                               final MyBatisProperties properties) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(properties.getAliasesPackage());
        sessionFactory.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        sessionFactory.setMapperLocations(properties.getMapperLocations());
        sessionFactory.setPlugins(interceptors);
        return sessionFactory.getObject();
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate(final SqlSessionFactory sqlSessionFactory,
                                                 final MyBatisProperties properties) {
        return new SqlSessionTemplate(sqlSessionFactory, properties.getExecutorType());
    }


    @Bean
    @ConditionalOnMissingBean
    public PlatformTransactionManager transactionManager(final DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }
}
