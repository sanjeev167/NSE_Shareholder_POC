package com.nseit.shareholder1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.model.AcceptShareHoldingLimitRequest;
import com.nseit.shareholder1.model.ProposedShareHoldingLimitRequest;
import com.nseit.shareholder1.service.ShareHoldingLimitService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/shareholdinglimit")
public class ShareHoldingLimitController {
	
	@Autowired
	ShareHoldingLimitService shareHoldingLimitService;
	
	@RequestMapping(value = "/getCurrentHoldingLimits/{version}", method = RequestMethod.GET)
	private ResponseEntity<?> getCurrentHoldingLimits(@PathVariable String version){
		return shareHoldingLimitService.getCurrentHoldingLimits(version);
	}
	
	@RequestMapping(value = "/getPendingLimits/{version}", method = RequestMethod.GET)
	private ResponseEntity<?> getPendingLimits(@PathVariable String version){
		return shareHoldingLimitService.getPendingLimits(version);
	}
	
	@RequestMapping(value = "/isShareHoldingLimitAccept/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> isShareHoldingLimitAccept(@PathVariable String version, @Valid @RequestBody AcceptShareHoldingLimitRequest acceptShareHoldingLimitRequest){
		return shareHoldingLimitService.isShareHoldingLimitAccept(version,acceptShareHoldingLimitRequest.getCategory(),acceptShareHoldingLimitRequest.getAccept());
	}
	
	@RequestMapping(value = "/proposedShareHoldingLimitChange/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> proposedShareHoldingLimitChange(@PathVariable String version, @Valid @RequestBody ProposedShareHoldingLimitRequest proposedShareHoldingLimitRequest){
		return shareHoldingLimitService.proposedShareHoldingLimitChange(version,proposedShareHoldingLimitRequest.getCategory(),proposedShareHoldingLimitRequest.getProposedChange());
	}

}
