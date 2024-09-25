package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.service.BrokerDashboardService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/brokerDashboard")
@Slf4j
public class BrokerDashboardController {
	@Autowired
	BrokerDashboardService brokerDashboardService;
	
	@RequestMapping(value = "/getBrokerAllShareTransferDetails/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getBrokerAllShareTransferDetails(@PathVariable String version){
		return brokerDashboardService.getBrokerAllShareTransferDetails(version);
		
	}
}
