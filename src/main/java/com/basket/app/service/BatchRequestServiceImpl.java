package com.basket.app.service;

import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Category;
import com.basket.app.pojo.Status;
import com.basket.app.repo.BatchRequestRepo;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.extras.codecs.enums.EnumNameCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BatchRequestServiceImpl implements IBatchRequestService {
    @Autowired
    BatchRequestRepo batchRequestRepo;
    @Autowired
    Cluster cluster;
    @Override
    public Status addBatch(BatchRequest batchRequest) {
        cluster.getConfiguration().getCodecRegistry().register(new EnumNameCodec<Category>(Category.class));

       BatchRequest savedObjAck = batchRequestRepo.save(batchRequest);
        if(!Objects.isNull(savedObjAck))
            return Status.OK;
        return Status.NOK;

    }
}
