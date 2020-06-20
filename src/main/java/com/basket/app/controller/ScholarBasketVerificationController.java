package com.basket.app.controller;

import com.basket.app.pojo.BasketResponse;
import com.basket.app.pojo.BasketUser;
import com.basket.app.pojo.Status;
import com.basket.app.service.IProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/ScholarBasket")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ScholarBasketVerificationController {
    @Autowired
    IProfileService profileService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ScholarBasketVerificationController.class);




    @RequestMapping(value="/VerifyAccount", method= RequestMethod.GET)
    public  String verifyAccount(@RequestParam(name = "user") String user, @RequestParam(name = "vertoken") String vertoken) throws JsonProcessingException {
        LOGGER.info("Inside verification method user="+user +" Token ="+vertoken);
        // mode = EXISTING_USER_UPDATE
        // mode = NEW_USER_REGISTRATION
        String message ="In process-profileDataUpdate";
        Status status=Status.INPROCESS;
        ObjectMapper mapper = new ObjectMapper();
        //String inputJson = mapper.writeValueAsString(basketUser);


   // update user details

            BasketUser newUser = profileService.recordVerificationOfUser(user,vertoken);
        LOGGER.info("Back from service call ");
            message ="User details updated successfully";

            StringBuffer returnBuffer = new StringBuffer();

            returnBuffer.append("<HTML><BODY>");

            if (newUser!=null)
            {
                //return success HTML
                status= Status.OK;
                returnBuffer.append("<h5> Dear "+ newUser.getEmailId()  +
                        "Your account has been verified. You can continue using www.scholarbasket.com . You will be asked to Login when needed<h5>");
            }
            else
            {
                //return fail HTML
                status= Status.NOK;
                returnBuffer.append("<h5> Dear "+ newUser.getEmailId()  +"Your account CANNOT BE VERIFIED. Please contact system administrator if the problem persists.<h5>");

            }


        returnBuffer.append("</BODY></HTML>");
        LOGGER.info("returnBuffer="+returnBuffer);

return returnBuffer.toString();

      //  return createBasketReponseWithMessage(newUser,message,status);
    }

}
