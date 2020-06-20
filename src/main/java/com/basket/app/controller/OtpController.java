package com.basket.app.controller;

import com.basket.app.pojo.BasketResponse;
import com.basket.app.pojo.BasketUser;
import com.basket.app.pojo.OtpObject;
import com.basket.app.pojo.Status;
import com.basket.app.service.IOtpService;
import com.basket.app.service.IProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/otpController")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OtpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OtpController.class);

    @Autowired
    IOtpService otpService;

    @RequestMapping(value="/getOtp/{userId}", method= RequestMethod.GET)
    public @ResponseBody BasketResponse<OtpObject> getUserProfileData(@PathVariable String userId) throws JsonProcessingException {
        LOGGER.info("*****************Input getOtp getOtp path variable userId ="+userId);


        ObjectMapper mapper = new ObjectMapper();
        String inputJson = mapper.writeValueAsString(userId);
        LOGGER.info("***********************Input basketUser getUserProfileData path variable userId  ="+userId);

        OtpObject otpObject = otpService.getOtpForUser(userId);

        BasketResponse<OtpObject> response  = new BasketResponse<>();
        response.setItems(otpObject);
        response.setTimeOfResponse(new Date());
       // response.setSizeOfresponse();


        LOGGER.info("*********End Controller "+response);
        return response;
    }
    //passwordResest
    @RequestMapping(value="/passwordReset", method= RequestMethod.POST)
    public @ResponseBody BasketResponse<OtpObject> resetPassword(@RequestBody OtpObject otpObject) throws JsonProcessingException {
        LOGGER.info("Input getOtp getOtp path variable userId =" + otpObject.getUserName());


        OtpObject returnObject = otpService.resetPassword(otpObject);
        BasketResponse<OtpObject> response  = new BasketResponse<>();
        response.setItems(returnObject);
        response.setTimeOfResponse(new Date());
        return  response;
    }




    }
