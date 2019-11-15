package com.basket.app.pojo;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


import java.util.UUID;

@Table(name="batchrequest")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchRequest {
    @PartitionKey
    private String userName;
    @ClusteringColumn
    private UUID id;
    private String subjectName;
    private Category category;
    private String requirement;




}
