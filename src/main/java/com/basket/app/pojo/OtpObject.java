package com.basket.app.pojo;

public class OtpObject {

    private String userName;
    private String message;
private String  otp;
private String newPassword;
private String confirmPassword;
    public String getUserName() {
        return userName;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public String toString() {
        return "OtpObject{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", otp='" + otp + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", okFlag=" + okFlag +
                '}';
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private boolean okFlag;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOkFlag() {
        return okFlag;
    }

    public void setOkFlag(boolean okFlag) {
        this.okFlag = okFlag;
    }
}
