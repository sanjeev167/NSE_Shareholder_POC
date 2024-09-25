package com.nseit.shareholder1.controller;

import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nseit.shareholder1.metadatamodel.MetadataMaster;
import com.nseit.shareholder1.model.DownloadFileModel;
import com.nseit.shareholder1.model.ShareTansferApprovalRejectSubmitRequest;
import com.nseit.shareholder1.model.ShareTransferMaster;
import com.nseit.shareholder1.model.ShareTransferSearchRequest;
import com.nseit.shareholder1.service.ShareTransferMasterSearch;
import com.nseit.shareholder1.service.ShareTransferService;
import com.nseit.shareholder1.util.JwtUtil;
//import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shareTransfer")
@Slf4j
public class ShareTransferController {

	@Autowired
	ShareTransferService shareTransferService;
	
	@Autowired
	ResponseUtil response;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	ShareTransferMasterSearch shareTransferMasterSearch;

	
	//,@RequestHeader("Authorization") String token
	@RequestMapping(value = "/putShareTransferDetails/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> putShareTransferDetails(@PathVariable String version,@Valid @RequestBody ShareTransferMaster shareTransferMaster){
		

		
		String username=jwtUtil.extractUsername();
		log.info("username --------------->"+username);
		
		log.info("millisecond--------------->"+new Date(System.currentTimeMillis()+1000*60));
//		if (token1 != null) {
			log.info("Value coming as request are Version :" + version + " shareTransferMaster :" + shareTransferMaster);
			return shareTransferService.putShareTransferDetails(version, shareTransferMaster);
//		} else {
//			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
//		}
	}
	@RequestMapping(value = "/fileMaster/{version}", method = RequestMethod.POST,produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<?> fileMaster(@PathVariable String version,@RequestParam("File") MultipartFile multiPartFile, @RequestParam("UUID") Long uuid){
		log.info("multipart values-------> ");
		return shareTransferService.fileMaster(version,multiPartFile,uuid);
	}
	@RequestMapping(value = "/putDataDetails/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> putDataDetails(@PathVariable String version, @RequestBody MetadataMaster metaDataMaster){
		return shareTransferService.putDataDetails(version, metaDataMaster);
	}
	
	@RequestMapping(value ="/getUuidDetails/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getUuidDetails(@PathVariable String version, @RequestBody ShareTansferApprovalRejectSubmitRequest shareTansferApprovalRejectSubmitRequest){
		log.info("uuid values---------->"+shareTansferApprovalRejectSubmitRequest.getUuid());
		return shareTransferService.getUuidDetails(version, shareTansferApprovalRejectSubmitRequest);
	} 
	
	
	@RequestMapping(value = "/shareTransferDownloadFile/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> shareTransferDownloadFile(@PathVariable String version,@Valid @RequestBody DownloadFileModel shareTransferDownloadFileModel){
		log.info("Map----------->"+shareTransferDownloadFileModel.getId());
		return shareTransferService.shareTransferDownloadFile(version, shareTransferDownloadFileModel.getId() );
	}
	
	@RequestMapping(value = "/shareTransferSubmit/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> shareTransferSubmit(@PathVariable String version, @Valid @RequestBody ShareTransferMaster shareTransferMaster){
		log.info("Map----------->"+shareTransferMaster.getUuid());
		return shareTransferService.shareTransferSubmit(version, shareTransferMaster);
	}
	
	@RequestMapping(value = "/shareTransferApproval/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> shareTransferApproval(@PathVariable String version, @Valid @RequestBody ShareTansferApprovalRejectSubmitRequest shareTansferApprovalRejectSubmitRequest){
		log.info("Map----------->"+shareTansferApprovalRejectSubmitRequest.getUuid());
		return shareTransferService.shareTransferApproval(version, shareTansferApprovalRejectSubmitRequest.getUuid(),shareTansferApprovalRejectSubmitRequest.getComments());
	}
	
	@RequestMapping(value = "/shareTransferReject/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> shareTransferReject(@PathVariable String version, @Valid @RequestBody ShareTansferApprovalRejectSubmitRequest shareTansferApprovalRejectSubmitRequest){
		log.info("Map----------->"+shareTansferApprovalRejectSubmitRequest.getUuid());
		return shareTransferService.shareTransferReject(version, shareTansferApprovalRejectSubmitRequest.getUuid(),shareTansferApprovalRejectSubmitRequest.getComments());
	}
	
	@RequestMapping(value ="/searchShareTransferDetails/{version}", method = RequestMethod.POST)
	
	public ResponseEntity<?> searchShareTransferDetails(@PathVariable String version, @RequestBody ShareTransferSearchRequest shareTransferSearchRequest){
//	public ResponseEntity<?> searchShareTransferDetails(@PathVariable String version, @RequestBody Map<String,String> json){
		log.info("uuid values---------->"+shareTransferSearchRequest);
//		return shareTransferService.searchShareTransferDetails(version, json.get("keyword"));
		return shareTransferMasterSearch.searchShareTransferDetails(version, shareTransferSearchRequest);
	}
	
	@RequestMapping(value = "/shareTransferRejectStatus/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> shareTransferRejectStatus(@PathVariable String version,@Valid @RequestBody ShareTansferApprovalRejectSubmitRequest shareTansferApprovalRejectSubmitRequest){
		log.info("Map----------->"+shareTansferApprovalRejectSubmitRequest.getUuid());
		return shareTransferService.shareTransferRejectStatus(version, shareTansferApprovalRejectSubmitRequest.getUuid());
	}
	
	@RequestMapping(value = "/getShareTransferRoleType/{version}", method = RequestMethod.GET)
	public ResponseEntity<?> getShareTransferRoleType(@PathVariable String version){
		
		return shareTransferService.getShareTransferRoleType(version);
	}

	@RequestMapping(value = "/getFileDetails/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getFileDetails(@PathVariable String version,@Valid @RequestBody DownloadFileModel shareTransferDownloadFileModel){
		log.info("Map----------->"+shareTransferDownloadFileModel.getId());
		return shareTransferService.getFileDetails(version, shareTransferDownloadFileModel.getId() );
	}
	
	@RequestMapping(value = "/shareTransferReverse/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> shareTransferReverse(@PathVariable String version, @Valid @RequestBody ShareTansferApprovalRejectSubmitRequest shareTansferApprovalRejectSubmitRequest){
		log.info("Map----------->"+shareTansferApprovalRejectSubmitRequest.getUuid());
		return shareTransferService.shareTransferReverse(version, shareTansferApprovalRejectSubmitRequest.getUuid(),shareTansferApprovalRejectSubmitRequest.getComments());
	}

	@RequestMapping(value = "/sharesCheck/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> sharesCheck(@PathVariable String version, @RequestBody Map<String,String> clientId){
		log.info("Map----------->"+clientId.get("clientId"));
		return shareTransferService.sharesCheck(version, clientId.get("clientId"));
	}
	
	@RequestMapping(value = "/reCheckApi/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> reCheckApi(@PathVariable String version, @RequestBody Map<String,Long> uuid) {

		return shareTransferService.reCheckApi(version, uuid.get("uuid"));
	}
	
	@RequestMapping(value = "/getBenposeHolding/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> getBenposeHolding(@PathVariable String version, @RequestBody Map<String,String> clientId) {

		return shareTransferService.getBenposeHolding(version, clientId.get("clientId"));
	}
	
	@RequestMapping(value = "/shareTransferRequest/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> shareTransferRequest(@PathVariable String version,@Valid @RequestBody ShareTransferMaster shareTransferMaster){
		return shareTransferService.shareTransferRequest(version, shareTransferMaster);
	}

	@RequestMapping(value = "/shareTransferExecutionDate/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> shareTransferExecutionDate(@PathVariable String version, @RequestBody Map<String,Object> uuid){
		return shareTransferService.shareTransferExecutionDate(version,Long.parseLong(uuid.get("uuid").toString()), Long.parseLong(uuid.get("date").toString()));
	}
	
	@RequestMapping(value = "/srcApproval/{version}", method = RequestMethod.POST)
    public ResponseEntity<?> srcApproval(@PathVariable String version, @RequestBody ShareTransferMaster shareTransferMaster) {
        return shareTransferService.srcApproval(version,shareTransferMaster);
    }
	
	@RequestMapping(value = "/sebiApproval/{version}", method = RequestMethod.POST)
    public ResponseEntity<?> sebiApproval(@PathVariable String version, @RequestBody ShareTransferMaster shareTransferMaster) {
        return shareTransferService.sebiApproval(version,shareTransferMaster);
    }
}
