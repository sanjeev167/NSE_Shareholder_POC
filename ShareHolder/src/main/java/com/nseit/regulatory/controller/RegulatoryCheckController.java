package com.nseit.regulatory.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.regulatory.service.RegulatoryCheckService;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/regulatoryCheck")
@Slf4j
@RestController
public class RegulatoryCheckController {

	@Autowired
	RegulatoryCheckService regulatoryCheckService;
	@Autowired
	ResponseUtil response;

	@RequestMapping(value = "/getExchangeComplianceDataList/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getExchangeComplianceDataList(@PathVariable String version,
			@RequestBody Map<String, Long> uuid) {
		try {
			
			return regulatoryCheckService.getExchangeComplianceDataList(version, uuid.get("uuid"));
		} catch (Exception e) {

			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}
	@RequestMapping(value = "/getHhidDataList/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getHhidDataList(@PathVariable String version,
			@RequestBody Map<String, Long> uuid) {
		try {
			return regulatoryCheckService.getHhidDataList(version, uuid.get("uuid"));
		} catch (Exception e) {

			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}
	@RequestMapping(value = "/getListingComplianceCgDataList/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getListingComplianceCgDataList(@PathVariable String version,
			@RequestBody Map<String, Long> uuid) {
		try {
			return regulatoryCheckService.getListingComplianceCgDataList(version, uuid.get("uuid"));
		} catch (Exception e) {

			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}
	@RequestMapping(value = "/getListingComplianceShpDataList/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getListingComplianceShpDataList(@PathVariable String version,
			@RequestBody Map<String, Long> uuid) {
		try {
			return regulatoryCheckService.getListingComplianceShpDataList(version, uuid.get("uuid"));
		} catch (Exception e) {

			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}
	@RequestMapping(value = "/getMembershipComplianceDataList/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getMembershipComplianceDataList(@PathVariable String version,
			@RequestBody  Map<String, Long> uuid) {
		try {
			return regulatoryCheckService.getMembershipComplianceDataList(version, uuid.get("uuid"));
		} catch (Exception e) {

			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}
	@RequestMapping(value = "/getSebiDebarredList/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getSebiDebarredList(@PathVariable String version,
			@RequestBody  Map<String, Long> uuid) {
		try {
			return regulatoryCheckService.getSebiDebarredList(version, uuid.get("uuid"));
		} catch (Exception e) {

			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}
	
}