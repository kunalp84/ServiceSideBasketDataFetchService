package com.basket.app.service;

import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Status;

import java.util.List;

public interface IBatchRequestService {
    Status addBatch(BatchRequest batchRequest);

    List<BatchRequest> getBatches(int start, int size, String optionalString);
}
