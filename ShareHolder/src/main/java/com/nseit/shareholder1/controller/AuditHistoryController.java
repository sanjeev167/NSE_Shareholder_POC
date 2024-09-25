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
import com.nseit.shareholder1.service.AuditHistoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auditHistory")
@Slf4j
public class AuditHistoryController {

	@Autowired
	AuditHistoryService auditHistoryService;
	
	@RequestMapping(value = "/getShareTransferRequestById/{version}",method = RequestMethod.POST)
	public ResponseEntity<?> getShareTransferRequestById(@PathVariable String version,@RequestBody DownloadFileModel downloadFileModel){
		return auditHistoryService.getShareTransferRequestById(version,downloadFileModel.getId());
	}
	
	@RequestMapping(value = "/getShareTransferRequestByUuid/{version}",method = RequestMethod.POST)
	public ResponseEntity<?> getShareTransferRequestByUuid(@PathVariable String version,@RequestBody Map<String,Long> uuid){
		return auditHistoryService.getShareTransferRequestByUuid(version,uuid.get("uuid"));
	}
}
