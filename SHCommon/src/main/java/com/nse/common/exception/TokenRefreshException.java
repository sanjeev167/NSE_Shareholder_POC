/**
 * 
 */
package com.nse.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author sanjeevkumar
 * 19-Mar-2024 
 * 7:37:52 pm 
 * Objective : 
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public TokenRefreshException(String token, String message) {
	    super(String.format("Failed for [%s]: %s", token, message));
	  }
}//End of TokenRefreshException
