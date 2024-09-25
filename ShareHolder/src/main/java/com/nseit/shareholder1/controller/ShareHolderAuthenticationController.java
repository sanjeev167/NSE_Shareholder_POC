package com.nseit.shareholder1.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.service.ShareHolderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shareHolderAuthentication")
@Slf4j
public class ShareHolderAuthenticationController {
	@Autowired
	ShareHolderService shareHolderService;

	@RequestMapping(value = "/logout/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> logout(@PathVariable String version, HttpServletRequest httpServletRequest) {
		log.info("in  logout " + version);
		String authorization = httpServletRequest.getHeader("Authorization");
		if (authorization != null) {
			String token = authorization.substring(7);
			return shareHolderService.logout(version, token);
		} else

			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(true);
	}
}
