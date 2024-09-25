package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.model.BatchModel;
import com.nseit.shareholder1.model.PostCal;
import com.nseit.shareholder1.service.ForeignResidentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/foreignResident")
@Slf4j
public class ForeignResidentController {

	@Autowired
	ForeignResidentService foreignResidentService;

//	@RequestMapping(value = "/getForeignResidentCal/{version}", method = RequestMethod.POST)
//	public ResponseEntity<?> getForeignResidentCal(@PathVariable("version") String version,
//			@RequestBody PostCal postCal) {
//		return foreignResidentService.getForeignResidentCal(version, postCal);
//	}
//
//	@RequestMapping(value = "/getPreTmToPCal/{version}", method = RequestMethod.GET)
//	public ResponseEntity<?> getPreTmToPCal(@PathVariable("version") String version) {
//		return foreignResidentService.getPreTmToPCal(version);
//	}
//
//	@RequestMapping(value = "/getPostTmToPCal/{version}", method = RequestMethod.POST)
//	public ResponseEntity<?> getPostTmToPCal(@PathVariable("version") String version, @RequestBody PostCal postCal) {
//		return foreignResidentService.getPostTmToPCal(version, postCal);
//	}
//
//	@RequestMapping(value = "/getPostTmToPCalOfBatch/{version}", method = RequestMethod.POST)
//	public ResponseEntity<?> getPostTmToPCalOfBatch(@PathVariable String version, @RequestBody BatchModel batchModel) {
//		return foreignResidentService.getPostTmToPCalOfBatch(version, batchModel.getId());
//	}
//	
//	@RequestMapping(value = "/getForeignResidentCalOfBatch/{version}", method = RequestMethod.POST)
//	public ResponseEntity<?> getForeignResidentCalOfBatch(@PathVariable String version, @RequestBody BatchModel batchModel) {
//		return foreignResidentService.getForeignResidentCalOfBatch(version, batchModel.getId());
//	}
	
	@RequestMapping(value = "/getPrePost/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getPrePost(@PathVariable("version") String version, @RequestBody PostCal postCal) {
		return foreignResidentService.getPrePost(version, postCal);
	}
	
	@RequestMapping(value = "/getPrePostOfBatch/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getPrePostOfBatch(@PathVariable String version, @RequestBody BatchModel batchModel) {
		return foreignResidentService.getPrePostOfBatch(version, batchModel.getId());
	}
	
	@RequestMapping(value = "/getTotalShares/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> getTotalShares(@PathVariable("version") String version) {
		return foreignResidentService.getTotalShares(version);
	}
}
