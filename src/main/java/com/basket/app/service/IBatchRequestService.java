package com.basket.app.service;

import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Status;

import java.util.List;
import java.util.UUID;

public interface IBatchRequestService {
    Status addBatch(BatchRequest batchRequest);

    List<BatchRequest> getBatches(int start, int size, String optionalString);
     Status deleteBatch(String id , String postedBy);
    BatchRequest getSingleBatch(String userName , UUID id);

}
