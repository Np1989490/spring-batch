package com.tiddev.integrationkeyjob.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobRunner implements CommandLineRunner {


    private final JobLauncher jobLauncher;
    private final Job mongoToOracleJob;

    @Override
    public void run(String... args) throws Exception {

        List<String> collections = List.of(
                "microservice_aastan_post",
                "microservice_shahkar",
                "microservice_afra"
        );

        for (String collection : collections) {

        JobParameters params = new JobParametersBuilder()
                .addString("collectionName", collection)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(mongoToOracleJob, params);
    }
}
    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                System.out.println("Job Started");
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                System.out.println("Job Status: " + jobExecution.getStatus());
            }
        };
    }
}