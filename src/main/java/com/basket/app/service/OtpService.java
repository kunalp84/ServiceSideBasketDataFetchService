package com.basket.app.service;

import com.basket.app.pojo.BasketUser;
import com.basket.app.pojo.OtpObject;
import com.basket.app.pojo.OtpStorage;
import com.basket.app.repo.BasketUserRepo;
import com.basket.app.repo.OtpRepo;
import com.datastax.driver.core.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
@Service
public class OtpService implements IOtpService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OtpService.class);
    @Autowired
    OtpRepo otpRepo;

    @Autowired
    BasketUserRepo basketUserRepo;

    @Override
    public OtpObject getOtpForUser(String userId) {

       int randomOtp = gen();

        OtpStorage otpStorage = new OtpStorage();
        otpStorage.setName(userId);
        otpStorage.setOtpKey(String.valueOf(randomOtp));
        otpStorage.setCreated(LocalDate.fromMillisSinceEpoch(new Date().getTime()) );
        LOGGER.info("Saved New OTP++++++"+randomOtp);
        //persist the otpStorage
          otpRepo.save(otpStorage);

        //send email
        EmailSendingUtility.sendOtp(userId,String.valueOf(randomOtp));

        OtpObject otpObject = new OtpObject();
        otpObject.setUserName(userId);
        otpObject.setMessage("OTPSENT");
        otpObject.setOkFlag(true);


        LOGGER.info("COPLETED SERVICE ");
        return otpObject;
    }

    private  int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    public OtpStorage retrieveGeneratedOtp(String userId)
    {
        return otpRepo.findByUserName(userId);
    }

    @Override
    public OtpObject resetPassword(OtpObject otpObject) {

        LOGGER.info("&&&&&&&&&&& inside Service reset passeor" + otpObject);

         OtpStorage otpStorage =    otpRepo.findByUserName(otpObject.getUserName());
        LOGGER.info("User name ="+otpObject.getUserName());
         LOGGER.info("OTP from cassadnra= "+otpStorage);
            if(otpStorage==null)
            {
                return createFailOtpObject(otpObject,"OTP EXPIRED");
            }
            else
            {
                //compare
                if(otpStorage.getOtpKey().equals(otpObject.getOtp()))
                {

                    LOGGER.info("Key mactched ");

                    if(otpObject.getConfirmPassword().equals(otpObject.getNewPassword()))
                    {
                        LOGGER.info("Password matched ");

                        //save
                        BasketUser basketUser = basketUserRepo.findByUserName(otpObject.getUserName());

                        BCryptPasswordEncoder encoder =   new BCryptPasswordEncoder(10);
                        basketUser.setPassword(encoder.encode(otpObject.getConfirmPassword()));
                        basketUser.setConfirmpassword(basketUser.getPassword());
                        basketUser.setEnabled(true);
                        basketUser.setCreated(  LocalDate.fromMillisSinceEpoch(new Date().getTime())    );
                        basketUserRepo.save(basketUser);

                        LOGGER.info("SAVED**********"+basketUser);
                        return createSuccessOtpObject(otpObject);
                    }
                    else
                    {
                        return createFailOtpObject(otpObject, "Passwords do not match");
                    }


                }
                else
                {
                    return createFailOtpObject(otpObject,"OTP DOES NOT MATCH");
                }

            }


    }

    private OtpObject createFailOtpObject(OtpObject otpObject, String message)
    {
            OtpObject otpObject1 = new OtpObject();
            otpObject1.setUserName(otpObject.getUserName());
        LOGGER.info("&&&&&& user ID "+otpObject.getUserName());
        otpObject1.setOkFlag(false);
        otpObject1.setMessage(message);
        return otpObject1;
    }


    private OtpObject createSuccessOtpObject(OtpObject otpObject)
    {
        OtpObject otpObject1 = new OtpObject();
        otpObject1.setUserName(otpObject.getUserName());
        LOGGER.info("&&&&&& user ID "+otpObject.getUserName());
        otpObject1.setOkFlag(true);
        otpObject1.setMessage("OTP FETCH SUCCESSFUL");
        return otpObject1;
    }

}
