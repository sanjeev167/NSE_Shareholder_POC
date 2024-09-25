package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.service.KeycloakRoleMappingService;

@RestController
@RequestMapping("/roleMapping")
public class KeycloakRoleMappingController {
	
	@Autowired
	KeycloakRoleMappingService keycloakRoleMappingService;
	
	
	@RequestMapping(value = "/resourceList/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> resourceList(@PathVariable String version ) {
		return keycloakRoleMappingService.resourceList(version);
	}


}
