package com.nseit.shareholder1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.model.AcceptNseUserRequest;
import com.nseit.shareholder1.model.LoginRequest;
import com.nseit.shareholder1.model.NseUserModel;
import com.nseit.shareholder1.model.ProposedChangeRequest;
import com.nseit.shareholder1.modelInterfaces.AddNseUserInterface;
import com.nseit.shareholder1.service.NseUserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/nseuser")
public class NseUserController {
	
	@Autowired
	NseUserService nseUserService;

	@RequestMapping(value = "/nseLogin/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> nseLogin(@PathVariable String version,@Valid @RequestBody LoginRequest loginRequest)
			throws Exception {
		log.info("Value coming as request are Version :" + version + " LoginRequest :" + loginRequest);
		return nseUserService.nseLogin(loginRequest.getUsername(), loginRequest.getPassword(),version);
	}
	
//	@RequestMapping(value = "/nseCheckLoginOtp/{version}", method = RequestMethod.POST)
//	private ResponseEntity<?> nseCheckLoginOtp(@PathVariable String version, @RequestBody LoginOTP loginOTP) {
//		log.info("in  register controller " + version + " UserMaster value is: " + loginOTP);
//
//		return nseUserService.nseCheckLoginOtp(loginOTP, version);
//	}
	
	
	@RequestMapping(value = "/addNseUser/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> addNseUser(@PathVariable String version,@Validated(AddNseUserInterface.class) @RequestBody NseUserModel nseUserModel){
		return nseUserService.addNseUser(version, nseUserModel.getUsername(), nseUserModel.getRole());
	}
	
	@RequestMapping(value = "/getAllNseUser/{version}", method = RequestMethod.GET)
	private ResponseEntity<?> getAllNseUser(@PathVariable String version){
		return nseUserService.getAllNseUser(version);
	}
	
	@RequestMapping(value = "/getCurrentUser/{version}", method = RequestMethod.GET)
	private ResponseEntity<?> getCurrentUser(@PathVariable String version){
		return nseUserService.getCurrentUser(version);
	}
	
	@RequestMapping(value = "/getNseUserBasedOnAccept/{version}", method = RequestMethod.GET)
	private ResponseEntity<?> getNseUserBasedOnAccept(@PathVariable String version){
		return nseUserService.getNseUserBasedOnAccept(version);
	}
	
	@RequestMapping(value = "/isAccept/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> isAccept(@PathVariable String version, @Valid @RequestBody AcceptNseUserRequest acceptNseUserRequest){
		return nseUserService.isAccept(version,acceptNseUserRequest.getUsername(),acceptNseUserRequest.getAccept());
	}
	
	@RequestMapping(value = "/proposedChange/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> proposedChange(@PathVariable String version, @Valid @RequestBody ProposedChangeRequest proposedChangeRequest){
		return nseUserService.proposedChange(version,proposedChangeRequest.getUsername(),proposedChangeRequest.getProposedChange());
	}
	
	
}
