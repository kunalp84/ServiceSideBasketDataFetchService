package com.basket.app.controller;

import com.basket.app.pojo.BasketResponse;
import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Status;
import com.basket.app.service.IBatchRequestService;
import com.basket.app.solr.EsRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/batchRequestController")
@CrossOrigin(origins = "http://localhost:3000")
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

        System.out.println("Input Enriched="+mapper.writeValueAsString(batchRequest)    );

        Status status = batchRequestService.addBatch(batchRequest);
        response.setItems(status);
        String json = mapper.writeValueAsString(response);
        System.out.println(json);
        return response;
    }
// ideally this end point will not be used - it will go to elastic search
    @RequestMapping(value="/getBatchPage", method= RequestMethod.GET)
    public @ResponseBody List<BatchRequest> getAllBatchRequests(int start, int size, String optionalString) {
        List<BatchRequest> batches = batchRequestService.getBatches(start,size, optionalString);
        return batches;
    }

}
