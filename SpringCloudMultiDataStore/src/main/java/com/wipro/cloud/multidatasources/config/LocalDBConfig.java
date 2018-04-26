package com.wipro.cloud.multidatasources.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@Profile("default")

@EnableJpaRepositories(
        entityManagerFactoryRef = "relationalManagerFactory",
        transactionManagerRef = "relationalTransactionManager",
        basePackages = "com.wipro.cloud.relational.repository"
)
@EnableTransactionManagement
public class LocalDBConfig {

    @Primary
    @Bean(name = "relationalManagerFactory")
    public LocalContainerEntityManagerFactoryBean relationalManagerFactory(final EntityManagerFactoryBuilder builder,
                                                                           final @Qualifier("local-db") DataSource dataSource) {
        /*return builder
                .dataSource(dataSource)
                .packages("com.wipro.cloud.relational.model")
                .persistenceUnit("mysqldb")
                .properties(singletonMap("hibernate.hbm2ddl.auto", "create"))
                .build();*/
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);
        // Classpath scanning of @Component, @Service, etc annotated class
        entityManagerFactory.setPackagesToScan("com.wipro.cloud.relational");
        // Vendor adapter
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        // Hibernate properties
        Properties additionalProperties = new Properties();
        additionalProperties.put(
               "hibernate.dialect",
               "org.hibernate.dialect.H2Dialect");
       additionalProperties.put(
               "hibernate.show_sql",true
       );
       additionalProperties.put(
               "hibernate.hbm2ddl.auto",
               "create");
       entityManagerFactory.setJpaProperties(additionalProperties);
        System.out.println("table creation read--");

        return entityManagerFactory;
    }

    @Primary
    @Bean(name = "relationalTransactionManager")
    public PlatformTransactionManager relationalTransactionManager(@Qualifier("relationalManagerFactory")
                                                                           EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager =  new JpaTransactionManager(entityManagerFactory);

        System.out.println("table created--");
        return jpaTransactionManager;
    }
}

