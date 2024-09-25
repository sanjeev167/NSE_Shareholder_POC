package com.nseit.shareholder1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.model.CheckerMakerModel;
import com.nseit.shareholder1.service.CheckerMakerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/checkerMaker")
@Slf4j
public class CheckerMakerController {
	
	@Autowired
	CheckerMakerService checkerMakerService;
	
	@RequestMapping(value = "/getAllCheckerMakerList/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCheckerMakerList(@PathVariable String version){
		return checkerMakerService.getAllCheckerMakerList(version);
	}
	
	@RequestMapping(value = "/addCheckerMakerList/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> addCheckerMakerList(@PathVariable String version,@RequestBody List<CheckerMakerModel> checkerMakerModel){
		return checkerMakerService.addCheckerMakerList(version,checkerMakerModel);
	}

}
