package com.wipro.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class SpringApp extends SpringBootServletInitializer {
    public static void main (String args[]){
        SpringApplication.run(SpringApp.class, args);
    }
}
