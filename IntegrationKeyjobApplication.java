package com.tiddev.integrationkeyjob;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class IntegrationKeyjobApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntegrationKeyjobApplication.class, args);
    }

}
