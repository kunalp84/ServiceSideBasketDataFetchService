package com.basket.app.repo;

import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Category;
import com.basket.app.solr.EsRepo;
import com.basket.app.solr.SolarRepo;
import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.Ordering;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.extras.codecs.enums.EnumNameCodec;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

@Repository
public class BatchRequestRepo extends BaseRepo<BatchRequest> {

    private static final String TABLE ="batchrequest";

    @Autowired
    EsRepo esRepo;

    public BatchRequestRepo(MappingManager mappingManager, Class<BatchRequest> type) {
        super(mappingManager, type);
    }

    public BatchRequest find(String userName, UUID id) {
        return mapper.get(userName, id);
    }

    public List<BatchRequest> findAll() {
        final ResultSet result = session.execute(select().all().from(TABLE));
        return mapper.map(result).all();
    }

    public List<BatchRequest> findAllByUserName(String userName) {
        Ordering order = QueryBuilder.desc( "id" );

        final ResultSet result = session.execute(select().all().from(TABLE).where(eq("user_name", userName)).orderBy(order));
        return mapper.map(result).all();
    }

    public void delete(String userName, UUID id) {
        mapper.delete( userName,  id);
    }


    public List<BatchRequest> findAllBatches(int start, int size)
    {
        System.out.println("Here-----");
        Ordering order = QueryBuilder.desc( "id" );
        Statement select  = select().all().from("batchrequest_ct_id_un").where(eq("category", "ENGINEERING")).orderBy(order);
        CassandraPaging cassandraPaging = new CassandraPaging(session);
        List<Row> pageRows = cassandraPaging.fetchRowsWithPage(select, start, size);
            List<BatchRequest> batchRequests = new ArrayList<>();
            for(Row dataRow : pageRows)
            {
                BatchRequest objectRow = new BatchRequest();
                objectRow.setId(dataRow.getUUID("id"));
                objectRow.setCategory( Category.valueOf(dataRow.getString("category")));
                objectRow.setUserName(dataRow.getString("user_name"));
                objectRow.setSubjectName(dataRow.getString("subject_name"));
                objectRow.setRequirement(dataRow.getString("requirement"));
                System.out.println("Going to ES");
                esRepo.postBatchRequestOnElasticServer(objectRow);
                System.out.println("Back from  ES");
                batchRequests.add(objectRow);
            }


        return batchRequests;
    }


}
