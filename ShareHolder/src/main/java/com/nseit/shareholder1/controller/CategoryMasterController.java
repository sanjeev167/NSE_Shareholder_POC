package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.service.CategoryMasterService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/categoryMaster")
public class CategoryMasterController {
	@Autowired
	CategoryMasterService categoryMasterService;
	
	@RequestMapping(value = "/getAllSubCategoryList/{version}", method = RequestMethod.GET)
	private ResponseEntity<?> getAllSubCategoryList(@PathVariable String version){
		return categoryMasterService.getAllSubCategoryList(version);
	}

}
