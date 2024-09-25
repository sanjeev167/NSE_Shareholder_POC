package com.nseit.shareholder1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.model.DownloadFileModel;
import com.nseit.shareholder1.service.ShareTransferRoleTypeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ShareTransferRoleTypeController")
@Slf4j
public class ShareTransferRoleTypeController {

	@Autowired
	ShareTransferRoleTypeService shareTransferRoleTypeService;
	
	@RequestMapping(value = "/getRevisionList/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getRevisionList(@PathVariable String version, @RequestBody Map<String,Long> uuid){
		return shareTransferRoleTypeService.getRevisionList(version, uuid.get("uuid"));
	}
	
	@RequestMapping(value = "/getRequestByRevision/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getRequestByRevision(@PathVariable String version, @RequestBody DownloadFileModel downloadFileModel){
		return shareTransferRoleTypeService.getRequestByRevision(version, downloadFileModel.getId());
	}
}
