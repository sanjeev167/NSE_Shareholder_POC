package com.nseit.shareholder1.util;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.nseit.shareholder1.model.ShareHolderResponse;
import com.nseit.shareholder1.web.rest.errors.EnumResponseCode;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class ResponseUtil {
	
	public ResponseEntity<?> getAuthResponse(String strCode, HttpStatus httpStatus, Object data,
			String version) {
		EnumResponseCode responseCode = EnumResponseCode.valueOf(strCode);
		log.info("strCode--->" + strCode);
		return ResponseEntity.status(httpStatus).body(
				new ShareHolderResponse(new Date(), version, data, responseCode.getCode(), responseCode.getMsg(),null));
	}
	public ResponseEntity<Object> getCustomException(String strCode, HttpStatus httpStatus, Object error,
			String version) {
		EnumResponseCode responseCode = EnumResponseCode.valueOf(strCode);
		log.info("strCode--->" + strCode);
		return ResponseEntity.status(httpStatus).body(
				new ShareHolderResponse(new Date(), version, null, responseCode.getCode(), responseCode.getMsg(), error));
	}


}
