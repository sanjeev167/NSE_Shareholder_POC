/**
 * 
 */
package com.nse.common.api.struct;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author sanjeevkumar
 * 19-Mar-2024 
 * 11:18:53 pm 
 * Objective : 
 */
public class ApiHead {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	private HttpStatus httpStatus;
	
	private String statusCode;
	
	private String message;	
	
	
	
	public ApiHead(LocalDateTime now, HttpStatus httpStatus, String message,String statusCode) {
		this.timestamp = now;
		this.httpStatus = httpStatus;
		this.message = message;
		this.statusCode = statusCode;
	}

	/**
	 * @return the timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the httpStatus
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * @param httpStatus the httpStatus to set
	 */
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
