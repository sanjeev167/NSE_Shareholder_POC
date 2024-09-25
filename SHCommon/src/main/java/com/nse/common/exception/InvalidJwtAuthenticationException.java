/**
 * 
 */
package com.nse.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * @author sanjeevkumar
 * 17-Dec-2023
 * 1:59:45 am 
 * Objective : This is a custom exception which can be thrown from any section of the code and be caught through a 
 * centralized controller advice. 
 * ExpiredJwtException
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidJwtAuthenticationException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InvalidJwtAuthenticationException(String message) {
		super(message);
	}
}//End of InvalidJwtAuthenticationException 
