package com.create.mybatis.application.configuration;

import com.create.mybatis.repository.config.EnableMyBatisRepositories;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableMyBatisRepositories({
        "com.create.mybatis.repository"
})
@EnableAutoConfiguration
public class MyBatisConfiguration {

//    @Bean
//    public SqlSessionFactory sqlSessionFactory(final DataSource dataSource) {
//        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        return sqlSessionFactoryBean;
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(final SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
}
