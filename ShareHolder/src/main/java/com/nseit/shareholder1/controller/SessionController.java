package com.nseit.shareholder1.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.model.LoginOTP;
import com.nseit.shareholder1.modelInterfaces.LogoutInterface;
import com.nseit.shareholder1.modelInterfaces.RefreshToken;
import com.nseit.shareholder1.service.SessionService;
import com.nseit.shareholder1.service.ShareHolderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/Session")
@Slf4j
public class SessionController {
	@Autowired
	ShareHolderService shareHolderService;
	
	@Autowired
	SessionService sessionService;
	@RequestMapping(value = "/logout/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> logout(@PathVariable String version, HttpServletRequest httpServletRequest,@Validated(LogoutInterface.class) @RequestBody LoginOTP loginOtp) {
		log.info("in  logout " + version);
		String authorization = httpServletRequest.getHeader("Authorization");
//		if (authorization != null) {
			String token = authorization.substring(7);
			return sessionService.logout(version, token,loginOtp);
//		} else
//
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(true);
	}
	
	@RequestMapping(value = "/refreshToken/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> refreshToken(@PathVariable String version,@Validated(RefreshToken.class) @RequestBody LoginOTP loginOtp) {
		
			return sessionService.refreshToken(loginOtp,version);
	
	}

}
