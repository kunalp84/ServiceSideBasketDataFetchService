package com.basket.app.service;

import com.basket.app.pojo.BasketUser;

public interface IProfileService {

    BasketUser getUserByname(String userId);

    BasketUser updateOnlyIfExists(BasketUser basketUser);

    BasketUser createNewUser(BasketUser basketUser);

    public BasketUser recordVerificationOfUser(String userName, String token);
}
