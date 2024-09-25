package com.nseit.shareholder1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.dao.ShareTransferDAO;
import com.nseit.shareholder1.modelInterfaces.DashboardInterface;
import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BrokerDashboardService {

	@Autowired
	JwtUtil jwt;

	@Autowired
	ShareTransferDAO shareTransferDAO;

	@Autowired
	ResponseUtil response;

	public ResponseEntity<?> getBrokerAllShareTransferDetails(String version) {
		try {
			List<DashboardInterface> shareHolderTransferDetails = shareTransferDAO
					.getShareholderAllShareTransferDetails(jwt.extractUsername());
			if (shareHolderTransferDetails.size() != 0) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareHolderTransferDetails, version);
			} else {
				log.error("In getBrokerAllShareTransferDetails NO_DATA-------------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			log.error("In getBrokerAllShareTransferDetails INTERNAL_SERVER_ERROR-------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
}
