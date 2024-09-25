package com.nseit.shareholder1.web.rest.errors;

public class ClientNotExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClientNotExistException() {
        super("Client Id does not exist");
    }
}
