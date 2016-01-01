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

import com.create.mybatis.repository.support.MyBatisRepositoryFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.data.config.ParsingUtils;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.config.XmlRepositoryConfigurationSource;
import org.w3c.dom.Element;

/**
 * {@link RepositoryConfigurationExtension} for MyBatis.
 */
public class MyBatisRepositoryConfigExtension extends RepositoryConfigurationExtensionSupport {
    private static final String SQL_SESSION_TEMPLATE_REF_XML = "sql-session-template-ref";
    private static final String TRANSACTION_MANAGER_REF_XML = "transaction-manager-ref";
    public static final String SQL_SESSION_TEMPLATE_REF = "sqlSessionTemplateRef";
    public static final String TRANSACTION_MANAGER_REF = "transactionManagerRef";
    public static final String SQL_SESSION_TEMPLATE = "sqlSessionTemplate";
    public static final String TRANSACTION_MANAGER = "transactionManager";
    public static final String NS_PREFIX = "mybatis";
    public static final String MODULE_NAME = "MyBatis";

    @Override
    public String getRepositoryFactoryClassName() {
        return MyBatisRepositoryFactoryBean.class.getName();
    }

    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }

    @Override
    protected String getModulePrefix() {
        return NS_PREFIX;
    }

    @Override
    public void postProcess(final BeanDefinitionBuilder builder, final AnnotationRepositoryConfigurationSource config) {
        final AnnotationAttributes attributes = config.getAttributes();
        builder.addPropertyReference(SQL_SESSION_TEMPLATE, attributes.getString(SQL_SESSION_TEMPLATE_REF));
        builder.addPropertyValue(TRANSACTION_MANAGER, attributes.getString(TRANSACTION_MANAGER_REF));
    }

    @Override
    public void postProcess(final BeanDefinitionBuilder builder, final XmlRepositoryConfigurationSource config) {
        final Element element = config.getElement();
        ParsingUtils.setPropertyReference(builder, element, SQL_SESSION_TEMPLATE_REF_XML, SQL_SESSION_TEMPLATE);
        ParsingUtils.setPropertyValue(builder, element, TRANSACTION_MANAGER_REF_XML, TRANSACTION_MANAGER);
    }
}
