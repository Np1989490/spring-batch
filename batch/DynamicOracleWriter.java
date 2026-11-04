package com.tiddev.integrationkeyjob.batch;

import com.mongodb.client.result.DeleteResult;
import com.tiddev.integrationkeyjob.dto.ServiceCacheOracle;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Set;

@Component
@StepScope
public class DynamicOracleWriter implements ItemWriter<ServiceCacheOracle> {


    private final JdbcTemplate jdbcTemplate;
    private final MongoTemplate mongoTemplate;
    private String collectionName;
    private static final Set<String> ALLOWED_TABLES =
            Set.of("MICROSERVICE_AASTAN_POST","MICROSERVICE_SHAHKAR","MICROSERVICE_AFRA");



    public DynamicOracleWriter(JdbcTemplate jdbcTemplate,
                               MongoTemplate mongoTemplate,
                               @Value("#{jobParameters['collectionName']}") String collectionName) {
        this.jdbcTemplate = jdbcTemplate;
        this.mongoTemplate = mongoTemplate;
        this.collectionName = collectionName;
    }


    @Override
    @Transactional
    public void  write(List<? extends ServiceCacheOracle> items) {

        if (collectionName == null || !ALLOWED_TABLES.contains(collectionName.toUpperCase())) {
            throw new IllegalArgumentException("Table not allowed: " + collectionName);
        }

        String tableName = collectionName.toUpperCase();

        String insertSql =
                "INSERT INTO " + tableName +
                        " (KEY1, KEY2, KEY3, USERNAME, STATUS, CREATEAT, LASTUPDATE, " +
                        " RESPONSE1, RESPONSE2, REQUESTID, RESPONSE, DESCRIPTION) " +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


     /*   String insertSql = String.format(
                "INSERT INTO %s (KEY1,KEY2, KEY3, USERNAME , STATUS , CREATEAT , LASTUPDATE , RESPONSE1  , RESPONSE2  , REQUESTID , RESPONSE , DESCRIPTION ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                collectionName
        );

        for (ServiceCacheOracle item : items) {
            jdbcTemplate.update(
                    insertSql,
                    item.getKey1(),
                    item.getKey2(),
                    item.getKey3(),
                    item.getUsername(),
                    item.getStatus(),
                    item.getCreatedAt(),
                    item.getLastUpdate(),
                    item.getResponse1(),
                    item.getResponse2(),
                    item.getRequestId(),
                    item.getResponse(),
                    item.getDescription()
            );
        }
*/

        jdbcTemplate.batchUpdate(
                insertSql,
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {

                        ServiceCacheOracle item = items.get(i);

                        ps.setString(1, item.getKey1());
                        ps.setString(2, item.getKey2());
                        ps.setString(3, item.getKey3());
                        ps.setString(4, item.getUsername());
                        ps.setString(5, item.getStatus());
                        if (item.getCreatedAt() != null) {
                            ps.setTimestamp(6, Timestamp.valueOf(item.getCreatedAt()));
                        } else {
                            ps.setNull(6, Types.TIMESTAMP);
                        }

                        if (item.getLastUpdate() != null) {
                            ps.setTimestamp(7, Timestamp.valueOf(item.getLastUpdate()));
                        } else {
                            ps.setNull(7, Types.TIMESTAMP);
                        }
                        ps.setString(8, item.getResponse1());
                        ps.setString(9, item.getResponse2());
                        ps.setString(10, item.getRequestId());
                        ps.setString(11, item.getResponse());
                        ps.setString(12, item.getDescription());
                    }

                    @Override
                    public int getBatchSize() {
                        return items.size();
                    }
                }
        );
        List<String> objectIds = items.stream()
                .map(ServiceCacheOracle::getObject_id)
                .toList();

        Query query = Query.query(
                Criteria.where("id").in(objectIds)
        );

        DeleteResult result = mongoTemplate.remove(query, collectionName);

        System.out.println("Deleted from Mongo: " + result.getDeletedCount());
    }
}
