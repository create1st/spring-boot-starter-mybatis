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
