package com.wipro.cloud.multidatasources.config;



import com.mongodb.MongoClient;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.cloud.config.java.AbstractCloudConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static java.util.Collections.singletonMap;

public class CloudConfig {
@Configuration
@Profile("cloud")
@EnableMongoRepositories(
        basePackages = "com.wipro.cloud.mongos.repository"
)
static class CloudConfiguration extends AbstractCloudConfig{

    @Bean
    public MongoDbFactory mongoDbFactory() {
        return connectionFactory().mongoDbFactory("mongodb");
    }

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

   @Bean("postgre-db")
    public DataSource dataSource() {
        return connectionFactory().dataSource("postrgre_test");
    }



}

@Configuration
@Profile("default")
@EnableMongoRepositories(
        basePackages = "com.wipro.cloud.mongos.repository"
)
static class LocalConfiguration {

    public @Bean
    MongoClient mongo() throws Exception {
        return new MongoClient("localhost");
    }

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "randev");
    }

    @Bean("local-db")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:mem:test");
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }


}

}
