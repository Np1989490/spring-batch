package com.tiddev.integrationkeyjob.dto;


import lombok.Data;

import java.lang.annotation.Documented;
import java.time.Instant;
import java.time.LocalDateTime;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class ServiceCacheMongo {

    @Id
    private ObjectId id;
    @Field(value = "KEY1")
    private String key1;
    @Field(value = "KEY2")
    private String key2;
    @Field(value = "KEY3")
    private String key3;
    @Field(value = "USERNAME")
    private String username;
    @Field(value = "STATUS")
    private String status;
    @Field(value = "LAST_UPDATE")
    private LocalDateTime lastUpdate;
    @Field(value = "RESPONSE2")
    private String response2;
    @Field(value = "RESPONSE1")
    private String response1;
    @Field(value = "REQUEST_ID")
    private String requestId;
    @Field(value = "RESPONSE")
    private String response;
    @Field(value = "DESCRIPTION")
    private String description;
    @Field(value = "CREATEAT")
    private LocalDateTime createdAt;
    @Field(value = "EXPIREAT")
    private LocalDateTime expireAt;
}
