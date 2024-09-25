package com.nseit.shareholder1.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.nseit.shareholder1.dao.ShareTransferDAO;
import com.nseit.shareholder1.dao.ShareTransferRoleTypeDao;
import com.nseit.shareholder1.model.ShareTransferMaster;
import com.nseit.shareholder1.model.ShareTransferRoleType;
import com.nseit.shareholder1.modelInterfaces.ShareTransferRoleInterface;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShareTransferRoleTypeService {

	@Autowired
	ShareTransferRoleTypeDao shareTransferRoleTypeDao;

	@Autowired
	ResponseUtil response;

	@Autowired
	ShareTransferDAO shareTransferDao;

	public ResponseEntity<?> getRevisionList(@PathVariable String version, @RequestBody Long uuid) {
		try {
			List<ShareTransferRoleInterface> shareTransferRoleType = shareTransferRoleTypeDao
					.getAllShareTransferRoleType1(uuid);
			List<ShareTransferRoleInterface> result = new ArrayList<ShareTransferRoleInterface>();
           
			if (shareTransferRoleType.size() > 0) {
				for (ShareTransferRoleInterface i : shareTransferRoleType) {
					result.add(i);
					
				}
				
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, result, version);
			} else {
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("In getRevisionList in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> getRequestByRevision(String version, Long id) {
		try {
			List<ShareTransferMaster> shareTransfer = shareTransferDao.getShareTranferDetailsBasedOnId(id);
			List<ShareTransferMaster> result = new ArrayList<ShareTransferMaster>();
			if (shareTransfer.size() > 0) {
				for (ShareTransferMaster i : shareTransfer) {
					result.add(i);
					
				}
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, result, version);
			} else {
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("In getRequestByRevision in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

}
