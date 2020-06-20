package com.basket.app.controller;

import com.basket.app.pojo.BasketResponse;
import com.basket.app.pojo.BasketUser;
import com.basket.app.pojo.BatchRequest;
import com.basket.app.pojo.Status;
import com.basket.app.service.IProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/profileController")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProfileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);


    @Autowired
    IProfileService profileService;

        @RequestMapping(value="/getUserProfileData/{userId}", method= RequestMethod.GET)
    public @ResponseBody BasketResponse<BasketUser> getUserProfileData(@PathVariable String userId) throws JsonProcessingException {
        LOGGER.info("Input basketUser getUserProfileData path variable userId ="+userId);


        ObjectMapper mapper = new ObjectMapper();
        String inputJson = mapper.writeValueAsString(userId);
            LOGGER.info("Input basketUser getUserProfileData path variable userId  ="+userId);

        BasketUser basketUser = profileService.getUserByname(userId);

        BasketResponse<BasketUser> response  = new BasketResponse<>();
        response.setItems(basketUser);
        response.setTimeOfResponse(new Date());
       // response.setSizeOfresponse();

        return response;
    }


    @RequestMapping(value="/profileDataUpdate", method= RequestMethod.POST)
    public @ResponseBody BasketResponse<BasketUser> profileDataUpdate(@RequestBody BasketUser basketUser) throws JsonProcessingException {
        LOGGER.info("Input basketUser profileDataAddUpdate path variable userId ="+basketUser);
        // mode = EXISTING_USER_UPDATE
        // mode = NEW_USER_REGISTRATION
        String message ="In process-profileDataUpdate";
        Status status=Status.INPROCESS;
        ObjectMapper mapper = new ObjectMapper();
        String inputJson = mapper.writeValueAsString(basketUser);
        LOGGER.info("Input basketUser profileDataAddUpdate path variable basketUser  ="+basketUser);
        BasketUser newUser = null;

/*        if(!basketUser.getPassword().equals(basketUser.getConfirmpassword()))
        {
            System.out.println("Old and New password do not match.");
        message ="Old and New password do not match.";
        status= Status.NOK;
        return createBasketReponseWithMessage(basketUser,message,status);
        } */



        if(basketUser.getMode().equals("EXISTING_USER_UPDATE")) {
            // update user details

             newUser = profileService.updateOnlyIfExists(basketUser);
            LOGGER.info("Back from serbice call ");
            message ="User details updated successfully";
            status= Status.OK;

        }


        return createBasketReponseWithMessage(newUser,message,status);
    }

    private BasketResponse<BasketUser> createBasketReponseWithMessage(BasketUser newUser,String message, Status status) {
        BasketResponse<BasketUser> response = new BasketResponse<>();
        response.setItems(newUser);
        if (newUser == null) {
            response.setErrorMessage("Operation failed. Check if user ID exists if the problem still persist contact system Administrator");
            response.setStatus(Status.NOK);
        }
        else if(newUser==null && message!=null)
        {
            response.setErrorMessage(message);
            response.setStatus(Status.NOK);
        }
        response.setTimeOfResponse(new Date());
        // response.setSizeOfresponse();
        LOGGER.info("*************RESPONSE**********");
        LOGGER.info("Response= " +response);
        return response;
    }


    @RequestMapping(value="/profileDataAdd", method= RequestMethod.POST)
    public @ResponseBody BasketResponse<BasketUser> profileDataAdd(@RequestBody BasketUser basketUser) throws JsonProcessingException {
        LOGGER.info("Input basketUser profileDataAddUpdate path variable userId ="+basketUser);
        // mode = EXISTING_USER_UPDATE
        // mode = NEW_USER_REGISTRATION
        String message ="In process-profileDataAdd";
        Status status=Status.INPROCESS;
        ObjectMapper mapper = new ObjectMapper();
        String inputJson = mapper.writeValueAsString(basketUser);
        LOGGER.info("Input basketUser profileDataAddUpdate path variable basketUser  ="+basketUser);
        BasketUser newUser = null;

        if(!basketUser.getPassword().equals(basketUser.getConfirmpassword()))
        {
            message ="Old and New password do not match.";
            status= Status.NOK;
            return createBasketReponseWithMessage(basketUser,message,status);
        }

       if(basketUser.getMode().equals(("NEW_USER_REGISTRATION"))) {
            // create a new user
                newUser = profileService.createNewUser(basketUser);
           message ="User details updated successfully";
           status= Status.OK;

        }

        return createBasketReponseWithMessage(newUser,message,status);
    }
}
