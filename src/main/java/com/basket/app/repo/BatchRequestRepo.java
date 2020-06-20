package com.basket.app.repo;

import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Category;
import com.basket.app.solr.EsRepo;
import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.Ordering;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.*;

@Repository
public class BatchRequestRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchRequestRepo.class);

    private static final String TABLE ="batchrequest";


    protected Mapper<BatchRequest> mapper;
    protected Session session;


    public BatchRequestRepo(MappingManager mappingManager ) {
        this.mapper = mappingManager.mapper(BatchRequest.class);
        this.session = mappingManager.getSession();

        LOGGER.info("Session created "+session);
    }



    public BatchRequest save(BatchRequest obj) {
        LOGGER.info("Inside save");

        mapper.save(obj);

        LOGGER.info("Completed save");
        return obj;
    }

    @Autowired
    EsRepo esRepo;



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
        LOGGER.info("Here-----");
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
                LOGGER.info("Going to ES");
                esRepo.postBatchRequestOnElasticServer(objectRow);
                LOGGER.info("Back from  ES");
                batchRequests.add(objectRow);
            }

        LOGGER.info("End-----");
        return batchRequests;
    }


}
