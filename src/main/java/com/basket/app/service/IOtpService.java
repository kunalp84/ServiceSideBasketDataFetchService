package com.basket.app.service;

import com.basket.app.pojo.OtpObject;
import com.basket.app.pojo.OtpStorage;

public interface IOtpService {
    OtpObject getOtpForUser(String userId);
    public OtpStorage retrieveGeneratedOtp(String userId);

    OtpObject resetPassword(OtpObject otpObject);
}
