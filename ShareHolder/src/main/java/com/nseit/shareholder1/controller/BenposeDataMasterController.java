package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nseit.shareholder1.service.BenposeDataMasterService;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/benposeDataMaster")
public class BenposeDataMasterController {

	@Autowired
	BenposeDataMasterService benposeDataMasterService;

	@Autowired
	ResponseUtil response;
	
	@RequestMapping(value = "/importExcel/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> importExcel(@PathVariable String version,
			@RequestParam("benposeSheet") MultipartFile multiPartFile) {
		System.out.println("inside benpose data mastes--------------------->");
		try {
			return benposeDataMasterService.importExcelData(version, multiPartFile);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
	
	@RequestMapping(value = "/transferMasterExcel/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> transferMasterExcel(@PathVariable String version,
			@RequestParam("transferMasterSheet") MultipartFile multiPartFile) {
		System.out.println("inside benpose data mastes--------------------->");
		try {
			return benposeDataMasterService.transferMasterExcel(version, multiPartFile);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
	
}
