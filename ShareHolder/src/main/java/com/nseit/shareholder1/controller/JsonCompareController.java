package com.nseit.shareholder1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.service.JsonCompareService;

import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/JsonCompareController")
@Slf4j
public class JsonCompareController {
	@Autowired
	JsonCompareService jsonCompareService;
	@RequestMapping(value = "/jsonCompare/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> jsonCompare(@RequestBody Map<String,Long> uuid,@PathVariable String version){
		return jsonCompareService.jsonCompare(uuid.get("uuid"), version);
	}
}
