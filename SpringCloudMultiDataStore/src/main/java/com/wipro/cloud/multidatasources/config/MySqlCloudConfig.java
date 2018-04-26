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

import static java.util.Collections.singletonMap;



@Configuration
@Profile("cloud")
@EnableJpaRepositories(
        entityManagerFactoryRef = "relationalManagerFactory",
        transactionManagerRef = "relationalTransactionManager",
        basePackages = "com.wipro.cloud.relational.repository"
)
@EnableTransactionManagement
public class MySqlCloudConfig {

   @Primary
    @Bean(name = "relationalManagerFactory")
    public LocalContainerEntityManagerFactoryBean relationalManagerFactory(final EntityManagerFactoryBuilder builder,
                                                                            final @Qualifier("postgre-db") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.wipro.cloud.relational.model")
                .persistenceUnit("test")
                .properties(singletonMap("hibernate.hbm2ddl.auto", "update"))
                .build();


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