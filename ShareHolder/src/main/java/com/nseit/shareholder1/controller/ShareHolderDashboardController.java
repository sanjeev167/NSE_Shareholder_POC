package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.service.ShareHolderDashboardService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shareHolderDashboard")
@Slf4j
public class ShareHolderDashboardController {
	
	@Autowired
	ShareHolderDashboardService shareHolderDashboardService;
	
	@RequestMapping(value = "/getShareholderAllShareTransferDetails/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getShareholderAllShareTransferDetails(@PathVariable String version){
		return shareHolderDashboardService.getShareholderAllShareTransferDetails(version);
		
	}
	
	

}
