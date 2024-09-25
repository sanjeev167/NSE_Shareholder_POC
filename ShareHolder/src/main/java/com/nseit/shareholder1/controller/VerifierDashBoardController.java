package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.service.VerifierDashBoardService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/verifierDashBoard")
@Slf4j

public class VerifierDashBoardController {

	@Autowired
	VerifierDashBoardService verifierDashBoardService;
	
	@RequestMapping(value = "/getVerifierShareTransferDetailsStageOne/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> getVerifierShareTransferDetailsStageOne(@PathVariable String version) {
		return verifierDashBoardService.getVerifierShareTransferDetailsStageOne(version);
	}
	@RequestMapping(value = "/getVerifierShareTransferDetailsStageTwo/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> getVerifierShareTransferDetailsStageTwo(@PathVariable String version) {
		return verifierDashBoardService.getVerifierShareTransferDetailsStageTwo(version);
	}
	@RequestMapping(value = "/getShareTransferDetailsStageTwoConfirmation/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> getShareTransferDetailsStageTwoConfirmation(@PathVariable String version) {
		return verifierDashBoardService.getShareTransferDetailsStageTwoConfirmation(version);
	}
	
	  @RequestMapping(value = "/getInternalApplication/{version}", method = RequestMethod.GET)
	  public ResponseEntity<?> getInternalApplication(@PathVariable String version) { return
	  verifierDashBoardService.getInternalApplication(version); }
	 
}
