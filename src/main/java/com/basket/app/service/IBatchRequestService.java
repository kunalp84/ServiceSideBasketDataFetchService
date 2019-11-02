package com.basket.app.service;

import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Status;

public interface IBatchRequestService {
    Status addBatch(BatchRequest batchRequest);
}
