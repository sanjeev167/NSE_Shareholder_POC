package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.service.BrokerRegistrationService;
import com.nseit.shareholder1.service.ShareHolderService;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/brokerDetails")
@Slf4j
@Validated
public class GetAllBrokerDetailsontroller {

	// private static final RequestMethod[] POST = null;

	@Autowired
	ShareHolderService shareHolderService;

	@Autowired
	BrokerRegistrationService brokerRegistrationService;

	@Autowired
	ResponseUtil response;

	@RequestMapping(value = "/getAllBrokerDetails/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getAllBrokerDetails(@PathVariable String version) {
		return brokerRegistrationService.getAllBrokerDetails(version);
	}

}
