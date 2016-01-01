package com.create.mybatis.repository.config;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

/**
 * {@link NamespaceHandler} for MyBatis configuration.
 */
public class MyBatisRepositoryNamespaceHandler extends NamespaceHandlerSupport {

    public static final String REPOSITORIES = "repositories";

    @Override
    public void init() {
        final RepositoryConfigurationExtension extension = new MyBatisRepositoryConfigExtension();
        final RepositoryBeanDefinitionParser repositoryBeanDefinitionParser = new RepositoryBeanDefinitionParser(extension);
        registerBeanDefinitionParser(REPOSITORIES, repositoryBeanDefinitionParser);
    }
}
