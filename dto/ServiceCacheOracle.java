package com.tiddev.integrationkeyjob.dto;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Data

public class ServiceCacheOracle {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(name="OBJECT_ID")
    private String object_id;
    @Column(name = "KEY1")
    private String key1;
    @Column(name = "KEY2")
    private String key2;
    @Column(name = "KEY3")
    private String key3;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "LAST_UPDATE")
    private LocalDateTime lastUpdate;
    @Column(name = "RESPONSE2")
    private String response2;
    @Column(name = "RESPONSE1")
    private String response1;
    @Column(name = "REQUEST_ID")
    private String requestId;
    @Column(name = "RESPONSE")
    private String response;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CREATEAT")
    private LocalDateTime createdAt;
    @Column(name = "EXPIREAT")
    private LocalDateTime expireAt;


}


