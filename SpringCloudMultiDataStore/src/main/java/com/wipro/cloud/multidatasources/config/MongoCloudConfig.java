package com.wipro.cloud.multidatasources.config;

import com.mongodb.Mongo;


import org.springframework.context.annotation.Bean;



import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import org.springframework.transaction.annotation.EnableTransactionManagement;



@EnableMongoRepositories(
        basePackages = "com.wipro.cloud.mongos.repository"
)
@EnableTransactionManagement
public class MongoCloudConfig {

    public @Bean
    Mongo mongo() throws Exception {
        return new Mongo("localhost");
    }

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "randev");
    }
}