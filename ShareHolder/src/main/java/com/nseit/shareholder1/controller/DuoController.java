package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.model.DuoModel;
import com.nseit.shareholder1.service.DuoService;
import com.nseit.shareholder1.service.NseUserService;

@RestController
@RequestMapping("/duo")
public class DuoController {
	
	
    @Autowired
    DuoService duoService;
    
    @Autowired
    NseUserService nseUserService;
	
	@RequestMapping(value ="/generateDuoFrame/{version}/{username}", method=RequestMethod.GET)
	public ResponseEntity<?> serveDuoFrame(@PathVariable("version") String version, @PathVariable("username")String username)  {
	      
		return duoService.serveDuoFrame(username,version);
		
	}

	@RequestMapping(value ="/verifyDuoFrame/{version}", method=RequestMethod.POST)
	public ResponseEntity<?> duoResponse( @PathVariable("version") String version , @RequestBody DuoModel duoModel)  {
		return nseUserService.duoResponse(duoModel,version);
	      
	}
	
}
