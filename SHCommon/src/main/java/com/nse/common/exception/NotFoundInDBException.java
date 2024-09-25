/**
 * 
 */
package com.nse.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author sanjeevkumar
 *
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundInDBException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NotFoundInDBException(String message) {
		super(message);
	}

}//End of NotFoundInDBException
