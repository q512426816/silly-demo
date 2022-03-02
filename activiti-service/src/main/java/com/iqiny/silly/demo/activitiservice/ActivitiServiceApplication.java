package com.iqiny.silly.demo.activitiservice;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ActivitiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiServiceApplication.class, args);
    }

}
