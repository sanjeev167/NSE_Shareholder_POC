package com.nseit.shareholder1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/role")
@Slf4j
@Validated
public class RoleController {

	@Autowired
	RoleService roleService;

	@RequestMapping(value = "/getAllRole/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllRole(@PathVariable String version) {
		return roleService.getAllRole(version);
	}

	@RequestMapping(value = "/addRole/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> addRole(@PathVariable String version,@RequestBody Map<String,String> json) {
		return roleService.addRole(version,json.get("roleName"));

	}
}
