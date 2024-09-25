package com.nseit.shareholder1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.model.PacEntityAccept;
import com.nseit.shareholder1.model.PacEntityModel;
import com.nseit.shareholder1.model.PacEntityProposedChange;
import com.nseit.shareholder1.service.PacEntityService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/pacEntity")
public class PacEntityController {

	@Autowired
	PacEntityService pacEntityService;
	
//	@RequestMapping(value = "/addEntity/{version}", method = RequestMethod.POST)
//	public ResponseEntity<?> addEntity(@PathVariable String version,@Valid @RequestBody PacEntityModel pacEntityModel){
//		return pacEntityService.addEntity(version,pacEntityModel);
//	}
//	
//	@RequestMapping(value = "/addGroup/{version}", method = RequestMethod.POST)
//	public ResponseEntity<?> addGroup(@PathVariable String version){
//		return pacEntityService.addGroup(version);
//	}
	
	@RequestMapping(value = "/addEntityList/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> addEntityList(@PathVariable String version,@Valid @RequestBody List<PacEntityModel> pacEntityModel){
		return pacEntityService.addEntityList(version,pacEntityModel);
	}
	
	@RequestMapping(value = "/proposedChange/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> proposedChange(@PathVariable String version, @Valid @RequestBody PacEntityProposedChange pacEntityProposedChange){
		return pacEntityService.proposedChange(version,pacEntityProposedChange.getGroupId(),pacEntityProposedChange.getProposedChange());
	}
	
	@RequestMapping(value = "/isAccept/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> isAccept(@PathVariable String version, @Valid @RequestBody PacEntityAccept pacEntityAccept){
		return pacEntityService.isAccept(version, pacEntityAccept.getGroupId(),pacEntityAccept.getAccept());
	}
	
	@RequestMapping(value = "/getCurrentUser/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> getCurrentUser(@PathVariable String version){
		return pacEntityService.getCurrentUser(version);
	}
	
	@RequestMapping(value = "/getUserBasedOnAccept/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserBasedOnAccept(@PathVariable String version){
		return pacEntityService.getUserBasedOnAccept(version);
	}
}
