package com.basket.app.service;


import com.basket.app.pojo.BasketUser;
import com.basket.app.repo.BasketUserRepo;
import org.springframework.beans.factory.annotation.Autowired; import
        org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired private BasketUserRepo userRepository;

    public BasketUser getUserByName(String userName) {

        BasketUser basketUser = userRepository.findByUserName(userName);

       // basketUser.setPassword(PasswordManager.uiPasswordToPlainText(basketUser));

        return basketUser;

         }

}

