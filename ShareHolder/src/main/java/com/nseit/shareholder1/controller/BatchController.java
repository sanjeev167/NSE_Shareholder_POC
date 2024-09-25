package com.nseit.shareholder1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.model.BatchModel;
import com.nseit.shareholder1.model.PostCal;
import com.nseit.shareholder1.service.BatchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/batch")
@Slf4j
public class BatchController {

	@Autowired
	BatchService batchService;

	@RequestMapping(value = "/prebatchListingBelowTwo/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> prebatchListingBelowTwo(@PathVariable String version) {
		return batchService.prebatchListingBelowTwo(version);
	}
	
	@RequestMapping(value = "/prebatchListingAboveTwo/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> prebatchListingAboveTwo(@PathVariable String version) {
		return batchService.prebatchListingAboveTwo(version);
	}

	@RequestMapping(value = "/createBatchBelowTwo/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> createBatchBelowTwo(@PathVariable String version, @Valid @RequestBody PostCal postCal) {
		return batchService.createBatchBelowTwo(version, postCal.getUuid());
	}
	
	@RequestMapping(value = "/createBatchAboveTwo/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> createBatchAboveTwo(@PathVariable String version, @Valid @RequestBody PostCal postCal) {
		return batchService.createBatchAboveTwo(version, postCal.getUuid());
	}

	@RequestMapping(value = "/batchListingBelowTwo/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> batchListingBelowTwo(@PathVariable String version) {
		return batchService.batchListingBelowTwo(version);
	}	
	
	@RequestMapping(value = "/batchListingAboveTwo/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> batchListingAboveTwo(@PathVariable String version) {
		return batchService.batchListingAboveTwo(version);
	}

	@RequestMapping(value = "/batchApprove/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> batchApprove(@PathVariable String version, @RequestBody BatchModel batchModel) {
		return batchService.batchApprove(version, batchModel);
	}

	@RequestMapping(value = "/batchReject/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> batchReject(@PathVariable String version, @RequestBody BatchModel batchModel) {
		return batchService.batchReject(version, batchModel);
	}
	
	@RequestMapping(value = "/rejectedbatchListingBelowTwo/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> rejectedbatchListingBelowTwo(@PathVariable String version) {
		return batchService.rejectedbatchListingBelowTwo(version);
	}	
	
	@RequestMapping(value = "/rejectedbatchListingAboveTwo/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> rejectedbatchListingAboveTwo(@PathVariable String version) {
		return batchService.rejectedbatchListingAboveTwo(version);
	}
}
