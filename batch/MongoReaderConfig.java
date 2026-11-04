package com.tiddev.integrationkeyjob.batch;

import com.tiddev.integrationkeyjob.dto.ServiceCacheMongo;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.batch.item.data.MongoItemReader;

import java.util.Map;

@Configuration
public class MongoReaderConfig {
    @Bean
    @StepScope
    public MongoItemReader<ServiceCacheMongo> mongoReader(
            MongoTemplate mongoTemplate,
            @Value("#{jobParameters['collectionName']}") String collectionName
    ) {

        MongoItemReader<ServiceCacheMongo> reader =
                new MongoItemReader<>();

        reader.setTemplate(mongoTemplate);
        reader.setCollection(collectionName);
        reader.setTargetType(ServiceCacheMongo.class);
        reader.setQuery("{}");
        reader.setPageSize(500);
        reader.setSort(Map.of("_id", Sort.Direction.ASC));



        return reader;
    }
}

