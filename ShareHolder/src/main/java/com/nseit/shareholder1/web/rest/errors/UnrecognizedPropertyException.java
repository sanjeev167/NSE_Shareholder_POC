package com.nseit.shareholder1.web.rest.errors;

public class UnrecognizedPropertyException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UnrecognizedPropertyException(String msg){
		super(msg);
	}
}
