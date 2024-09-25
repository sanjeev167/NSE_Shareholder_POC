package com.nseit.shareholder1.web.rest.errors;

public class PhoneAndEmailAlreadyExist extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public PhoneAndEmailAlreadyExist() {
		super("Incorrect Phone or Email");
	}
}
