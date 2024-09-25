package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nseit.shareholder1.service.BenposeMasterService;

import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/benposeMaster")
@Slf4j
public class BenposeMasterController {
	
	@Autowired
	private BenposeMasterService benposeMasterService;
	
	@RequestMapping(value = "/uploadBenposeFile/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> uploadBenposeFile(@RequestParam("File") MultipartFile file,@PathVariable String version){
		return benposeMasterService.uploadBenposeFile(file, version);
	}

}
