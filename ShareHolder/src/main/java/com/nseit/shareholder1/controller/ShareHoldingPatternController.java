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

import com.nseit.shareholder1.service.ShareHoldingPatternService;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/shareHoldingPattern")
public class ShareHoldingPatternController {
	
	
	@Autowired
	ShareHoldingPatternService shareHoldingPatternService;
	
	
	@Autowired
	 ResponseUtil response;
	
	
	@RequestMapping(value = "/importExcel/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> importExcel(@PathVariable String version,
			@RequestParam("workbook") MultipartFile multiPartFile) {
		
		try {
			return shareHoldingPatternService.importExcelData(version, multiPartFile);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

}
