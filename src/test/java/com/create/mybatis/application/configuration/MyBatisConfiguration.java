package com.create.mybatis.application.configuration;

import com.create.mybatis.repository.config.EnableMyBatisRepositories;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableMyBatisRepositories({
        "com.create.mybatis.repository"
})
@EnableAutoConfiguration
public class MyBatisConfiguration {

}
