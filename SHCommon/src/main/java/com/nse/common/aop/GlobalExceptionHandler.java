/**
 * 
 */
package com.nse.common.aop;


import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nse.common.api.struct.ApiError;
import com.nse.common.api.struct.ApiHead;

import com.nse.common.exception.InternalServerException;
import com.nse.common.exception.InvalidJwtAuthenticationException;
import com.nse.common.exception.NotFoundInDBException;
import com.nse.common.exception.ResourceNotFoundException;
import com.nse.common.exception.ResponseEntityBuilder;
import com.nse.common.exception.TokenRefreshException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
/**
 * @author sanjeevkumar 
 * 19-Mar-2024 
 * 1:47:19 am 
 * Objective :
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	String dateFormatUsed = "dd-MM-yyyy HH:mm:ss";
	
	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective:handleHttpMediaTypeNotSupported : It triggers when the received-JSON-input is invalid.
	 * 
	 * This error is of 400 series. We are assuming it as a 405
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

		List<String> details = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		details.add(builder.toString());

		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "nse notices => Invalid JSON","405");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}

	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective :
	 *         handleHttpMessageNotReadable : It triggers when the JSON-Input is
	 *         malformed.
	 *         This error is of 400 series. We are assuming it as a 410
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST,"nse notices => Malformed JSON request","410");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}

	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective :
	 *         handleMethodArgumentNotValid : It triggers when @Valid applied on
	 *         pay-load fails.\
	 *         
	 *          This error is of 400 series. We are assuming it as a 415
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getObjectName() + " : " + error.getDefaultMessage()).collect(Collectors.toList());
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "nse notices => Method-Argument-Validation-Error","415");
		
		ApiError err = new ApiError(head, details);

		return ResponseEntityBuilder.build(err);
	}

	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective:
	 *         HandleMissingServletRequestParameter : It triggers when there are
	 *         missing parameters in the Json-input
	 *         This error is of 400 series. We are assuming it as a 420
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getParameterName() + " parameter is missing");
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "nse notices => Missing Parameters","420");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}

	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective :
	 *         handleMethodArgumentTypeMismatch : it triggers when an input
	 *         parameter's type does not match.
	 *         
	 *         This error is of 400 series. We are assuming it as a 425
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "nse notices => Method-Argument-Mismatch Type","425");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}

	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective
	 *         :handleConstraintViolationException : it triggers when @Validated
	 *         fails.
	 *         This error is of 400 series. We are assuming it as a 430
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	private ResponseEntity<?> handleConstraintViolationException(Exception ex, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "nse notices => Constraint Violation","430");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective :
	 *         handleNoHandlerFoundException : it triggers when the handler method
	 *         is invalid
	 *         
	 *    This error is of 400 series. We are assuming it as a 435     
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "nse notices => Method Not Found","435");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}

	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective :
	 *         handleNoHandlerFoundException : it triggers when the handler method
	 *         is invalid
	 *         
	 *         This error is of 400 series. We are assuming it as a 440  
	 */
	@ExceptionHandler(NumberFormatException.class)
	protected ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(String.format("Could not find the %s method for URL %s"));
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST,"nse notices => Number Format Exception","440");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective : It triggers when either username or password is wrong
	 *  This error is of 400 series. We are assuming it as a 445  
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
	private ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "nse notices => User-Not-Found","445");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Status: Checked Successfully
	 * Objective : handleAll : It triggers when bad credential is supplied while logging
	 * 
	 *  This error is of 400 series. We are assuming it as a 450
	 * 
	 */
	@ExceptionHandler(BadCredentialsException.class)
	private ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {		
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "nse notices => Invalid-Credential","450");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	

	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective :
	 *         handleResourceNotFoundException : it triggers when there is not
	 *         resource with the specified ID in database.
	 *         
	 *         This error is of 404 series. We are assuming it as a 455
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	private ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		       
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.NOT_FOUND, "nse notices => Resource-URL-Not-Found","455");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	
	/**
	 * @param ex
	 * @param request
	 * @return
	 * 
	 *  This error is of 404 series. We are assuming it as a 460
	 */
	@ExceptionHandler(NotFoundInDBException.class)
	private ResponseEntity<Object> handleNotFoundInDBException(NotFoundInDBException ex, WebRequest request) {
		       
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.NOT_FOUND, "nse notices => Resource Not Found in DB","460");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}

	
	
	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Status: Checked Successfully
	 * Objective : handleAll : It triggers when unauthorized access is made
	 * 
	 *  This error is of 401 series. We are assuming it as a 465
	 * 
	 */
	@ExceptionHandler(AccessDeniedException.class)
	private ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {       
		List<String> details = new ArrayList<String>();
		ApiHead head = null ;
		details.add(ex.getMessage());		
		head = new ApiHead(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, "nse notices => Unauthorized-Access","465");		
		ApiError err =new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	/**
	 * @param ex
	 * @param request
	 * @return
	 * 
	 * This error is of 403 series. We are assuming it as a 470
	 */
	@ExceptionHandler(ExpiredJwtException.class)
	private ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
		
		List<String> details = new ArrayList<String>();		
		details.add(ex.getMessage());		
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.FORBIDDEN, "nse notices => JWT-Token-Expired","470");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	/**
	 * @param ex
	 * @param request
	 * @return
	 * This error is of 403 series. We are assuming it as a 475
	 */
	@ExceptionHandler(TokenRefreshException.class)
	private ResponseEntity<Object> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {		
		
		List<String> details = new ArrayList<String>();		
		details.add(ex.getMessage());
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.FORBIDDEN, "nse notices => JWT-Refresh-Token-Expired","475");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	/**
	 * @param ex
	 * @param request
	 * @return
	 * This error is of 403 series. We are assuming it as a 480
	 */
	
	@ExceptionHandler(SignatureException.class)
	private ResponseEntity<Object> handleSignatureException(SignatureException ex, WebRequest request) {		
		
		List<String> details = new ArrayList<String>();		
		details.add(ex.getMessage());		
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.FORBIDDEN, "nse notices => Invalid-JWT-Token","480");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	/**
	 * @param ex
	 * @param request
	 * @return
	 * This error is of 403 series. We are assuming it as a 485
	 */
	
	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	private ResponseEntity<Object> handleInvalidJwtAuthenticationException(InvalidJwtAuthenticationException ex, WebRequest request) {		
		
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());		
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.FORBIDDEN, "nse notices => Invalid-JWT-Token-Authentication","485");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	
	
	
	
	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective : handleAll : It triggers when no handler method is defined
	 * 
	 *  This error is of 400 series. We are assuming it as a 490
	 */
	@ExceptionHandler({ DateTimeException.class })
	private ResponseEntity<Object> handleAll(DateTimeException ex, WebRequest request) {
		
		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());		
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "nse notices => Invalid-Month-Value","490");		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	/**
	 * @author sanjeevkumar 
	 * 19-Mar-2024
	 * 22:47:19 pm 
	 * Objective : handleAll : It triggers when no handler method is defined
	 * 
	 *  This error is of 400 series. We are assuming it as a 490
	 */
	@ExceptionHandler({ Exception.class })
	private ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		
		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());		
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "nse notices => Error occurred","495");		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	
	/**
	 * @param ex
	 * @param request
	 * @return
	 * This error is of 500 series. We are assuming it as a 500
	 */
	
	@ExceptionHandler(InternalServerException.class)
	private ResponseEntity<Object> handleInternalServerException(InternalServerException ex, WebRequest request) {		
		
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());		
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, "nse notices => Internal Server Error","500");
		
		ApiError err = new ApiError(head, details);
		return ResponseEntityBuilder.build(err);
	}
	
	
	
	
	
	
	/*
	private ApiRequest getRequestMeta(WebRequest request) {
		//ApiError err = new ApiError(head, details,apiRequest);
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		
		ApiRequest apiRequest = new ApiRequest();
		ServletWebRequest servletWebRequest =  ((ServletWebRequest) request);
		HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
			
		//System.out.println("RemoteHost = "+httpServletRequest.getRemoteHost());
		//System.out.println("AuthType = "+httpServletRequest.getAuthType());
		
		apiRequest.setApi_url(httpServletRequest.getRequestURI());
		apiRequest.setInContent_Type(httpServletRequest.getContentType());
		apiRequest.setRequested_at(httpServletRequest.getRequestURI().toString());
		apiRequest.setResponded_at(null);
		apiRequest.setPayLoad(null);
		apiRequest.setMethod(httpServletRequest.getMethod());
		return apiRequest;
	}*/

}// End of GlobalExceptionHandler
