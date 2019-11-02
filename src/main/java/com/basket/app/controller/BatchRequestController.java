package com.basket.app.controller;

import com.basket.app.pojo.BasketResponse;
import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Status;
import com.basket.app.service.IBatchRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/batchRequestController")
public class BatchRequestController {

    @Autowired
    IBatchRequestService batchRequestService;

    @RequestMapping(value="/submitBatchRequest", method= RequestMethod.POST)
    public @ResponseBody BasketResponse<Status> submitBatchRequst(@RequestBody BatchRequest batchRequest) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String inputJson = mapper.writeValueAsString(batchRequest);
        System.out.println("Input="+inputJson);

        BasketResponse<Status> response = new BasketResponse<>();
        UUID basedOnTime = UUID.randomUUID();
        batchRequest.setId(basedOnTime);

        Status status = batchRequestService.addBatch(batchRequest);
        response.setItems(status);
        String json = mapper.writeValueAsString(response);
        System.out.println(json);
        return response;
    }

}
