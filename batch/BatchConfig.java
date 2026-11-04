package com.tiddev.integrationkeyjob.batch;

import com.tiddev.integrationkeyjob.dto.ServiceCacheMongo;
import com.tiddev.integrationkeyjob.dto.ServiceCacheOracle;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration

public class BatchConfig {


/*    @Bean
    public JobBuilderFactory jobBuilderFactory(JobRepository jobRepository) {
        return new JobBuilderFactory(jobRepository);
    }

    @Bean
    public StepBuilderFactory stepBuilderFactory(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilderFactory(jobRepository, transactionManager);
    }*/

    @Autowired
    private  JobBuilderFactory jobBuilderFactory;

    @Autowired
    private  StepBuilderFactory stepBuilderFactory;



    @Bean
    public Job mongoToOracleJob(Step mongoToOracleStep) {
        return jobBuilderFactory.get("mongo-to-oracle-job")
                .start(mongoToOracleStep)
                .build();
    }

    @Bean
    public Step mongoToOracleStep(
            MongoItemReader<ServiceCacheMongo> reader,
            DynamicOracleWriter writer
    ) {
        return stepBuilderFactory.get("mongo-to-oracle-step")
                .<ServiceCacheMongo, ServiceCacheOracle>chunk(500)
                .reader(reader)
                .processor((ItemProcessor<? super ServiceCacheMongo, ? extends ServiceCacheOracle>) this:: convert)
                .writer(writer)
                .build();
    }

    private ServiceCacheOracle convert(ServiceCacheMongo mongo) {
        ServiceCacheOracle oracle = new ServiceCacheOracle();
        oracle.setKey1(mongo.getKey1());
        oracle.setKey2(mongo.getKey2());
        oracle.setKey3(mongo.getKey3());
        oracle.setUsername(mongo.getUsername());
        oracle.setStatus(mongo.getStatus());
        oracle.setCreatedAt(mongo.getCreatedAt());
        oracle.setLastUpdate(mongo.getLastUpdate());
        oracle.setResponse2(mongo.getResponse2());
        oracle.setRequestId(mongo.getRequestId());
        oracle.setResponse(mongo.getResponse());
        oracle.setDescription(mongo.getDescription());
        oracle.setResponse1(mongo.getResponse1());

        return oracle;
    }
}
