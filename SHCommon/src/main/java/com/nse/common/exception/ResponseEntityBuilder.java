/**
 * 
 */
package com.nse.common.exception;

import org.springframework.http.ResponseEntity;

import com.nse.common.api.struct.ApiError;

/**
 * @author sanjeevkumar
 * 19-Mar-2024 
 * 1:49:46 am 
 * Objective : 
 */
public class ResponseEntityBuilder {

	public static ResponseEntity<Object> build(ApiError apiError) {
	      return new ResponseEntity<>(apiError, apiError.getHead().getHttpStatus());
	}
}//End of ResponseEntityBuilder 
