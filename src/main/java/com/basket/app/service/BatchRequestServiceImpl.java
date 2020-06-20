package com.basket.app.service;

import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Category;
import com.basket.app.pojo.Status;
import com.basket.app.repo.BatchRequestRepo;
import com.basket.app.solr.EsRepo;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.extras.codecs.enums.EnumNameCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BatchRequestServiceImpl implements IBatchRequestService {
    @Autowired
    BatchRequestRepo batchRequestRepo;
    @Autowired
    Cluster cluster;

    @Autowired
    EsRepo esRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchRequestServiceImpl.class);
    @Override
    public Status addBatch(BatchRequest batchRequest) {
        cluster.getConfiguration().getCodecRegistry().register(new EnumNameCodec<Category>(Category.class));

       BatchRequest savedObjAck = batchRequestRepo.save(batchRequest);

       esRepo.postBatchRequestOnElasticServer(batchRequest);




        if(!Objects.isNull(savedObjAck))
            return Status.OK;
        return Status.NOK;

    }

    @Override
    public List<BatchRequest> getBatches(int start, int size, String optionalString) {

        return batchRequestRepo.findAllBatches(start,size);
    }



    @Override
    public Status deleteBatch(String id , String postedBy) {
        cluster.getConfiguration().getCodecRegistry().register(new EnumNameCodec<Category>(Category.class));

        batchRequestRepo.delete(postedBy, UUID.fromString(id));

        esRepo.deleteBatchRequestOnElasticServer(id);
         return Status.OK;

    }

    @Override
    public BatchRequest getSingleBatch(String userName , UUID id) {
        return batchRequestRepo.find( userName,id  );
    }
}
