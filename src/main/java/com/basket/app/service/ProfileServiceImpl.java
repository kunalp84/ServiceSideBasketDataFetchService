package com.basket.app.service;

import com.basket.app.pojo.BasketUser;
import com.basket.app.pojo.USERTYPE;
import com.basket.app.repo.BasketUserRepo;
import com.basket.app.solr.EsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Savepoint;
@Service
public class ProfileServiceImpl implements IProfileService {
    @Autowired
    BasketUserRepo basketUserRepo;

    @Autowired
    EsRepo esRepo;

    @Override
    public BasketUser getUserByname(String userId) {
        BasketUser basketUser = basketUserRepo.findByUserName(userId);
        return basketUser;
    }

    @Override
    public BasketUser updateOnlyIfExists(BasketUser basketUser) {
        System.out.println("**** INSIDDE updateOnlyIfExists");

        BasketUser existingUser = basketUserRepo.findByUserName(basketUser.getName());

        System.out.println("Got data back "+existingUser);
        if(existingUser!=null)
        {

            BasketUser savedObj =  basketUserRepo.save(basketUser);

            if(savedObj!=null)
            {
                System.out.println("Saving data in Elastic Search server for "+basketUser.getUserType());
                if(basketUser.getUserType().equals("TEACHER"))
                    esRepo.postBasketUserOnElasticServer(basketUser, USERTYPE.PROFESSOR);
                else if (!basketUser.getUserType().equals("TEACHER"))
                    esRepo.postBasketUserOnElasticServer(basketUser, USERTYPE.STUDENT);
            }

        }
        else
        {
            System.out.println("Existing user not found - so cannot update");
            return null;
        }

        return basketUser;
    }

    @Override
    public BasketUser createNewUser(BasketUser basketUser) {
        BasketUser existingUser = basketUserRepo.findByUserName(basketUser.getName());
        if(existingUser==null)
        {


           BasketUser savedObj =  basketUserRepo.save(basketUser);

           if(savedObj!=null)
           {
               System.out.println("Saving data in Elastic Search server for "+basketUser.getUserType());
               if(basketUser.getUserType().equals("TEACHER"))
                   esRepo.postBasketUserOnElasticServer(basketUser, USERTYPE.PROFESSOR);
               else if (!basketUser.getUserType().equals("TEACHER"))
                   esRepo.postBasketUserOnElasticServer(basketUser, USERTYPE.STUDENT);
           }


            System.out.println("User details created ");

        }
        else
        {
            System.out.println("User already Exists - failed to created ");
            return null;
        }
        return basketUser;
    }
}
