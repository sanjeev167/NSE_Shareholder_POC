/**
 * 
 */
package com.nse.common.pvt.sec.ctrl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nse.common.api.struct.ApiHead;
import com.nse.common.api.struct.ApiRequest;
import com.nse.common.api.struct.ApiResponse;
import com.nse.common.sec.config.LocalUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author sanjeevkumar 
 * 22-Mar-2024
 * 12:28:47 pm 
 * Objective : This class is a sharable class which is used to prepare Api-Meta-Data and a success response for all the APIs.
 */
public class ApiBaseCtrl {
	@Autowired
	protected LocalUserDetailsService localUserDetailsService;
	
	protected String dateFormatUsed = "dd-MM-yyyy HH:mm:ss";
	protected ApiRequest apiReq = null;
	protected String contentTypeRsp;
	protected Integer httpStatus;
	protected String apiResMsg;
	protected String errorMsg;
	protected Object resObj = null;
	protected ApiResponse apiResponse = new ApiResponse();
	protected String gridNodeCode =null;
	protected String apiUser =null;
	Authentication auth = null;	
	
	protected ApiRequest makeApiMetaData() {
			 
		try {			
		auth = SecurityContextHolder.getContext().getAuthentication();		
		apiUser = auth.getName();			
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		String contentTypeRcvd=request.getContentType();		
		String methodName=request.getMethod();
		String apiUrl=request.getRequestURI();
		// do something
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormatUsed);
		LocalDateTime now = LocalDateTime.now();
		String apiCallReceived = dtf.format(now);
		String apiCallResponded =  dtf.format(LocalDateTime.now());
		
		
	    apiReq = new ApiRequest( apiUrl,
	    		                methodName, 	    		                
	    		                contentTypeRcvd, 
	    		                contentTypeRsp, 
	    		                apiCallReceived,
	    		                apiCallResponded);
		}catch(Exception ex) {ex.printStackTrace();}
		return apiReq;
		
	}// End of makeApiMetaData
	
	protected ApiRequest makeApiMetaDataWhileLogin(String userName) {
			 
		try {				
		apiUser = userName;	
		
		//LocalUserDetails localUserDetails = (LocalUserDetails) localUserDetailsService.loadUserByUsername(apiUser);				
		
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		String contentTypeRcvd=request.getContentType();		
		String methodName=request.getMethod();
		String apiUrl=request.getRequestURI();
		
		// do something
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormatUsed);
		LocalDateTime now = LocalDateTime.now();
		String apiCallReceived = dtf.format(now);
		String apiCallResponded =  dtf.format(LocalDateTime.now());
		
		
	    apiReq = new ApiRequest( apiUrl,
	    		                methodName, 	    		                
	    		                contentTypeRcvd, 
	    		                contentTypeRsp, 
	    		                apiCallReceived,
	    		                apiCallResponded);
		}catch(Exception ex) {ex.printStackTrace();}
		return apiReq;
		
	}// End of makeApiMetaData
	
	protected ApiResponse makeSuccessResponse(Object object, ApiRequest apiReq) {
		ApiResponse apiResponse = new ApiResponse();			
		ApiHead head = new ApiHead(LocalDateTime.now(), HttpStatus.OK, "Successful","200");			
		apiResponse.setMeta(apiReq);
		apiResponse.setBody(object); 
		apiResponse.setHead(head);
		return apiResponse;
	}

}// End of ApiBaseCtrl
