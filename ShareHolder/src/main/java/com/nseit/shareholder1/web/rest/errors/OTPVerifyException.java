package com.nseit.shareholder1.web.rest.errors;

public class OTPVerifyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OTPVerifyException() {
        super("OTP Entered is Incorrect");
    }
}
