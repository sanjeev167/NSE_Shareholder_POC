package com.nseit.shareholder1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nseit.shareholder1.service.KeycloakService;
import com.nseit.shareholder1.util.ResponseUtil;

@RestController
@RequestMapping("/keycloak")
public class KeycloakController {
	@Autowired
	KeycloakService keycloakService;
	@Autowired
	ResponseUtil responseUtil;
	
	
	
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	ResponseEntity<?> resetPassword(@RequestBody Map<String, String>json){
//		String id = keycloakService.resetPassword(json.get("username"), json.get("password"), json.get("confirmPassword"));
		return keycloakService.resetPassword(json.get("username"), json.get("password"),json.get("confirmPassword"));
//		return ResponseEntity.ok("Created------------------->"+id);
		}
	
	
	
	  @RequestMapping(value = "/updateRoleMapping/{version}", method = RequestMethod.POST)
	    public ResponseEntity<?> updateRoleMapping( @PathVariable String version, @RequestBody Map<String, Map> jsonRequestModel) throws JsonMappingException, JsonProcessingException {
	        System.out.println("json value------------>"+jsonRequestModel);
	        return keycloakService.updateRoleMapping(version,jsonRequestModel);
	    }
	  
	  
	
	
}
