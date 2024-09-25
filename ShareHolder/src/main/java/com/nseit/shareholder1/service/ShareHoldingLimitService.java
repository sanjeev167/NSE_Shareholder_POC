package com.nseit.shareholder1.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.dao.ShareHoldingLimitDao;

import com.nseit.shareholder1.model.ShareHoldingLimitMaster;
import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShareHoldingLimitService {
	
	@Autowired
	ResponseUtil response;
	
	@Autowired
	ShareHoldingLimitDao shareHoldingLimitDao;
	
	@Autowired
	JwtUtil jwt;
	public ResponseEntity<?> getCurrentHoldingLimits(String version) {
		try {
			List<ShareHoldingLimitMaster> shareHoldingLimitList = shareHoldingLimitDao.getCurrentHoldingLimit();
			if (shareHoldingLimitList != null && shareHoldingLimitList.size() > 0) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareHoldingLimitList, version);
			} else {
				log.error("In getCurrentHoldingLimits NO_DATA----------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, shareHoldingLimitList, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In getCurrentHoldingLimits in Exception INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getPendingLimits(String version) {
		try {
			List<ShareHoldingLimitMaster> shareHoldingPendingLimitList = shareHoldingLimitDao.getPendingLimit();
			if (shareHoldingPendingLimitList.size() > 0) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareHoldingPendingLimitList, version);
			} else {
				log.error("In getPendingLimits NO_DATA----------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, shareHoldingPendingLimitList, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In getPendingLimits Exception INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> isShareHoldingLimitAccept(String version, String category, String accept) {
		try {
			ShareHoldingLimitMaster shareHoldingLimitMaster1 = shareHoldingLimitDao.getProposedLimit(category);
			if (shareHoldingLimitMaster1 != null) {
				if (accept.equalsIgnoreCase("Y")) {
					ShareHoldingLimitMaster shareHoldingLimitMaster2 = (ShareHoldingLimitMaster) shareHoldingLimitMaster1.clone();
					shareHoldingLimitMaster2.setId(null);
					shareHoldingLimitMaster2.setTotalHoldingLimit(shareHoldingLimitMaster1.getProposedChange());
					shareHoldingLimitMaster2.setAccept(accept);
					shareHoldingLimitMaster2.setCreatedBy(jwt.extractUsername());
					shareHoldingLimitDao.save(shareHoldingLimitMaster2);
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareHoldingLimitMaster2, version);
				} else {
					shareHoldingLimitMaster1.setAccept("N");
					Timestamp instant = Timestamp.from(Instant.now());
					shareHoldingLimitMaster1.setModifiedOn(instant);
					shareHoldingLimitMaster1.setCreatedBy(jwt.extractUsername());
					shareHoldingLimitDao.save(shareHoldingLimitMaster1);
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareHoldingLimitMaster1, version);
				}

			} else {
				log.error("In isShareHoldingLimitAccept NO_DATA----------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, shareHoldingLimitMaster1, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In isShareHoldingLimitAccept in Exception INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> proposedShareHoldingLimitChange(String version, String category, Float proposedChange) {
		try {
			ShareHoldingLimitMaster shareHoldingLimitMaster1 = shareHoldingLimitDao.getAcceptedShareHoldingLimit(category);
			if (shareHoldingLimitMaster1 != null ) {
				// nseUserModel1.setActive(active);
				ShareHoldingLimitMaster shareHoldingLimitMaster2 = (ShareHoldingLimitMaster) shareHoldingLimitMaster1.clone();
				shareHoldingLimitMaster2.setId(null);
				shareHoldingLimitMaster2.setAccept(null);
				shareHoldingLimitMaster2.setProposedChange(proposedChange);
				shareHoldingLimitMaster2.setCreatedBy(jwt.extractUsername());
				shareHoldingLimitDao.save(shareHoldingLimitMaster2);
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareHoldingLimitMaster2, version);
			} else {
				log.error("In proposedShareHoldingLimitChange in NO_DATA----------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, shareHoldingLimitMaster1, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In proposedShareHoldingLimitChange in Exception INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
	
	
	

}
