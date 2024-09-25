package com.nseit.shareholder1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.dao.ShareTransferDAO;
import com.nseit.shareholder1.model.ShareTransferMaster;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuditHistoryService {

	@Autowired
	ShareTransferDAO shareTransferDAO;

	@Autowired
	ResponseUtil response;

	public ResponseEntity<?> getShareTransferRequestById(String version, Long id) {
		// TODO Auto-generated method stub
		try {
			ShareTransferMaster shareTransferresponse = shareTransferDAO.getById(id);
			if (shareTransferresponse != null) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferresponse, version);
			} else {
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(
					"In getShareTransferRequestById in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, null);
		}
	}

	public ResponseEntity<?> getShareTransferRequestByUuid(String version, Long uuid) {
		// TODO Auto-generated method stub
		try {
			List<ShareTransferMaster> shareTransferresponse = shareTransferDAO.getBySubmitUuid(uuid);
//			List<ShareTransferMaster> shareTransferresponse = shareTransferDAO.getByUuid(uuid);
			if (shareTransferresponse != null) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferresponse, version);
			} else {
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(
					"In getShareTransferRequestByUuid in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, null);
		}
	}
}
