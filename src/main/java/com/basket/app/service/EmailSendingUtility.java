package com.basket.app.service;

import com.basket.app.pojo.BasketUser;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import java.util.Properties;

public class EmailSendingUtility {


    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSendingUtility.class);


    public static void sendVerificationEmail(BasketUser user)
    {
        System.out.println("Hello");
        final String username = "scholarbasketsolutions@gmail.com";
        final String password = "qwdpydiysaxshsmp";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("scholarbasketsolutions@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(user.getName())
            );
            message.setSubject("Verify your Scholar Basket Account");
            message.setText("Dear "+user.getEmailId()
                    + "\n\n Welcome to Scholar Basket. Thank you for registering with us. Please click on the link below to activate  your account.!" +
                    "\n\n" +
                    "http://localhost:8080/ScholarBasket/VerifyAccount?user="+user.getName()+"&vertoken="+user.getVertoken()+
            "\n\n"+
                    " Please reply to us if you have any questions. \n\n   Thank you , \nScholar Basket Team  ");

            Transport.send(message);

            LOGGER.info("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



    public static void sendOtp(String name, String otp)
    {
        LOGGER.info("Hello");
        final String username = "scholarbasketsolutions@gmail.com";
        final String password = "qwdpydiysaxshsmp";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("scholarbasketsolutions@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(name)
            );
            message.setSubject("OTP  to reset your Scholar Basket Password");
            message.setText("Dear User"
                    + "\n\n Please use the OTP " + otp +" to reset your password. This OTP is valid for 15 minutes only.Please do not share your OTP with anybody!\n\n Thank you \n\n Scholar Basket Team");

            Transport.send(message);

           LOGGER.info("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}




