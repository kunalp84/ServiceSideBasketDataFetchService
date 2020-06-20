package com.basket.app.service;

import com.basket.app.pojo.BasketUser;
import com.basket.app.pojo.USERTYPE;
import com.basket.app.repo.BasketUserRepo;
import com.basket.app.solr.EsRepo;
import com.datastax.driver.core.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Savepoint;
import java.util.Date;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements IProfileService {
    @Autowired
    BasketUserRepo basketUserRepo;

    @Autowired
    EsRepo esRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Override
    public BasketUser getUserByname(String userId) {
        BasketUser basketUser = basketUserRepo.findByUserName(userId);
        return basketUser;
    }

    @Override
    public BasketUser updateOnlyIfExists(BasketUser basketUser) {
        LOGGER.info("**** INSIDDE updateOnlyIfExists");

        BasketUser existingUser = basketUserRepo.findByUserName(basketUser.getName());

        LOGGER.info("Got data back "+existingUser);
        if(existingUser!=null)
        {
           // BCryptPasswordEncoder encoder =   new BCryptPasswordEncoder(10);
            basketUser.setPassword(existingUser.getPassword());
            basketUser.setConfirmpassword(basketUser.getPassword());
            basketUser.setEnabled(true);
            basketUser.setCreated(existingUser.getCreated());
            basketUser.setUpdated( LocalDate.fromMillisSinceEpoch(new Date().getTime())  );


            BasketUser savedObj =  basketUserRepo.save(basketUser);

            if(savedObj!=null)
            {
                LOGGER.info("Saving data in Elastic Search server for "+basketUser.getUserType());
                if(basketUser.getUserType().equals("TEACHER"))
                    esRepo.postBasketUserOnElasticServer(basketUser, USERTYPE.PROFESSOR);
                else if (!basketUser.getUserType().equals("TEACHER"))
                    esRepo.postBasketUserOnElasticServer(basketUser, USERTYPE.STUDENT);
            }

        }
        else
        {
            LOGGER.info("Existing user not found - so cannot update");
            return null;
        }

        return basketUser;
    }

    @Override
    public BasketUser recordVerificationOfUser(String userName, String token)
    {
        BasketUser existingUser = basketUserRepo.findByUserName(userName);

        if(existingUser!=null)
        {

            existingUser.setEnabled(true);
            existingUser.setCreated(existingUser.getCreated());
            existingUser.setUpdated( LocalDate.fromMillisSinceEpoch(new Date().getTime())  );


            BasketUser savedObj =  basketUserRepo.save(existingUser);

            if(savedObj!=null)
            {
                LOGGER.info("Saving data in Elastic Search server for "+existingUser.getUserType());
                if(existingUser.getUserType().equals("TEACHER"))
                    esRepo.postBasketUserOnElasticServer(existingUser, USERTYPE.PROFESSOR);
                else if (!existingUser.getUserType().equals("TEACHER"))
                    esRepo.postBasketUserOnElasticServer(existingUser, USERTYPE.STUDENT);
            }
            return existingUser;
        }
        else
        {
            LOGGER.info("Existing user not found - so cannot update");
            return null;
        }
    }




    @Override
    public BasketUser createNewUser(BasketUser basketUser) {
        BasketUser existingUser = basketUserRepo.findByUserName(basketUser.getName());
        if(existingUser==null)
        {
            BCryptPasswordEncoder encoder =   new BCryptPasswordEncoder(10);
            basketUser.setPassword(encoder.encode(basketUser.getPassword()));
            basketUser.setConfirmpassword(basketUser.getPassword());
            basketUser.setEnabled(false);
            basketUser.setCreated(  LocalDate.fromMillisSinceEpoch(new Date().getTime())    );
           // basketUser.setUpdated(LocalDate.now());
            String verificationToken = UUID.randomUUID().toString();
            basketUser.setVertoken(verificationToken);

           BasketUser savedObj =  basketUserRepo.save(basketUser);


            //send email  to give the activation link - the sending utility must first store ( user, Token in a table)
            EmailSendingUtility.sendVerificationEmail(basketUser);

        /*    if(savedObj!=null)
           {
               System.out.println("Saving data in Elastic Search server for "+basketUser.getUserType());
               if(basketUser.getUserType().equals("TEACHER"))
                   esRepo.postBasketUserOnElasticServer(basketUser, USERTYPE.PROFESSOR);
               else if (!basketUser.getUserType().equals("TEACHER"))
                   esRepo.postBasketUserOnElasticServer(basketUser, USERTYPE.STUDENT);


           } */


            LOGGER.info("User details created ");

        }
        else
        {
            LOGGER.info("User already Exists - failed to created ");
            return null;
        }
        return basketUser;
    }
}
