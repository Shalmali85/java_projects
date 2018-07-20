package com.wipro.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ImportAutoConfiguration(FeignAutoConfiguration.class)

public class SpringApp extends SpringBootServletInitializer {
    public static void main (String args[]){
        SpringApplication.run(SpringApp.class, args);
    }
}
