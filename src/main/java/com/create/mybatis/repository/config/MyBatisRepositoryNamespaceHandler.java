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
