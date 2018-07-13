package com.wipro.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.aws.context.config.annotation.EnableContextCredentials;
import org.springframework.cloud.aws.context.config.annotation.EnableContextRegion;
import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableJpaRepositories(basePackages="com.wipro.cloud.repository")
@EntityScan(basePackages="com.wipro.cloud.model")
@ComponentScan(basePackages="com.wipro.cloud")
//@EnableAutoConfiguration
@EnableRdsInstance(databaseName = "ebdb", dbInstanceIdentifier = "aaugbibr0elj2x" ,username = "*", password = "*")
@EnableContextCredentials(accessKey = "*", secretKey ="*" )
@EnableContextRegion(region = "us-east-2")
public class SpringApp extends SpringBootServletInitializer {
    public static void main (String ...args){
        SpringApplication.run(SpringApp.class, args);
    }
}
