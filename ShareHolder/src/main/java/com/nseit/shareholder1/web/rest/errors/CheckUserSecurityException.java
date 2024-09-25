package com.nseit.shareholder1.web.rest.errors;

public class CheckUserSecurityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CheckUserSecurityException() {
        super("Security Answer Incorrect");
    }
}
