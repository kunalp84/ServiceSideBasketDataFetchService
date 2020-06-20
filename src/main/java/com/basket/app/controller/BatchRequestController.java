package com.basket.app.controller;

import com.basket.app.pojo.BasketResponse;
import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Status;
import com.basket.app.service.IBatchRequestService;
import com.basket.app.solr.EsRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/batchRequestController")
//@CrossOrigin(origins = {"http://localhost:3000","https://moonlit-academy-276018.uc.r.appspot.com/"})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BatchRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchRequestController.class);

    @Autowired
    IBatchRequestService batchRequestService;



    @RequestMapping(value="/submitBatchRequest", method= RequestMethod.POST)
    public @ResponseBody BasketResponse<Status> submitBatchRequst(@RequestBody BatchRequest batchRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String inputJson = mapper.writeValueAsString(batchRequest);
        LOGGER.info("Input="+inputJson);
        LOGGER.info("SHOULD NOT BE HERE---1"+batchRequest.getPurpose());

        BasketResponse<Status> response = new BasketResponse<>();
        UUID basedOnTime = UUID.randomUUID();

        if(!"EDIT_BATCH_REQUEST".equals(batchRequest.getPurpose()))
        {
            //this is creating new
            batchRequest.setId(basedOnTime);
            LOGGER.info("SHOULD NOT BE HERE---2"+batchRequest.getPurpose());
        }
        else
        {
            //limit check - logic here
            LOGGER.info("Limit check logic will go here");
        }



        LOGGER.info("Input Enriched="+mapper.writeValueAsString(batchRequest)    );

        Status status = batchRequestService.addBatch(batchRequest);
        response.setItems(status);
        String json = mapper.writeValueAsString(response);
        LOGGER.info(""+json);
        return response;
    }
// ideally this end point will not be used - it will go to elastic search
    @RequestMapping(value="/getBatchPage", method= RequestMethod.GET)
    public @ResponseBody List<BatchRequest> getAllBatchRequests(int start, int size, String optionalString) {
        List<BatchRequest> batches = batchRequestService.getBatches(start,size, optionalString);
        return batches;
    }

    @RequestMapping(value="/deleteBatch/{postedBy}/{id}", method= RequestMethod.GET)
    public @ResponseBody BasketResponse<Status> getAllBatchRequests(@PathVariable  String postedBy, @PathVariable String id) {
         Status status =    batchRequestService.deleteBatch(id,postedBy);
        BasketResponse<Status> response = new BasketResponse<>();
        response.setItems(status);
        return response;
    }


    @RequestMapping(value="/findBatch/{postedBy}/{id}", method= RequestMethod.GET)
    public @ResponseBody BasketResponse<BatchRequest> getSingleBatch(@PathVariable  String postedBy, @PathVariable String id) {
        BatchRequest batchRequest =    batchRequestService.getSingleBatch(postedBy, UUID.fromString(id));
        BasketResponse<BatchRequest> response = new BasketResponse<>();
        response.setItems(batchRequest);
        return response;
    }
}
