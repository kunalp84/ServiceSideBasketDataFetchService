package com.basket.app.service;

import org.jasypt.util.text.BasicTextEncryptor;

public class PasswordManager {

    public static String uiPasswordToPlainText(String uiPassword)
    {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray("password".toCharArray());
        //System.out.println("password is "+);
        return textEncryptor.decrypt(uiPassword);
    }


}
