package com.nseit.shareholder1.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler { 
	@Autowired
	ResponseUtil response;

	//@ExceptionHandler(MethodArgumentNotValidException.class)
	/*
	 * public ResponseEntity<?> handleException(MethodArgumentNotValidException ex)
	 * {
	 * 
	 * return response.getCustomException("VALIDATION_ERROR",
	 * HttpStatus.BAD_REQUEST, ex.getBindingResult(),null); }
	 */
	public class ValidationError {
		
		public ValidationError(String field, String error) {
			super();
			this.field = field;
			this.error = error;
		}
		String field;
		String error;
		 public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
		
		
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		BindingResult result=ex.getBindingResult();
		List<ValidationError> resultList=result.getFieldErrors().stream().map(x ->new ValidationError(x.getField(),x.getDefaultMessage())).collect(Collectors.toList());
		//List<ValidationError> resultList=result.getFieldErrors().stream().

		
		return response.getCustomException("VALIDATION_ERROR", HttpStatus.BAD_REQUEST, resultList,null);
	}
	
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleAllExceptions(ConstraintViolationException ex, WebRequest request){
		
		String msg = ex.getMessage();
		String[] msgs= msg.split(",");
		List<Object>errorList = new ArrayList<Object>();
		
		for(String m:msgs) {
			
			String key = m.substring(0,m.indexOf(":")).substring(m.indexOf(".")+1).trim();
			String value = m.substring(m.indexOf(":")+1).trim();
			errorList.add(	new ValidationError(key, value));
		}
		
		return response.getCustomException("VALIDATION_ERROR", HttpStatus.BAD_REQUEST, errorList,null);
	}
	
	
}
