package com.nseit.shareholder1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.dao.CheckerMakerDao;
import com.nseit.shareholder1.dao.ShareTransferDAO;
import com.nseit.shareholder1.dao.ShareTransferRoleTypeDao;
import com.nseit.shareholder1.model.CheckerMakerModel;
import com.nseit.shareholder1.model.ShareTransferRoleType;
import com.nseit.shareholder1.modelInterfaces.DashboardInterface;
import com.nseit.shareholder1.modelInterfaces.ShareTransferRoleInterface;
import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VerifierDashBoardService {

	@Autowired
	ShareTransferDAO shareTransferDAO;
	@Autowired
	ResponseUtil response;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	ShareTransferRoleTypeDao shareTransferRoleTypeDao;

	@Autowired
	CheckerMakerDao checkerMakerDao;

	public ResponseEntity<?> getVerifierShareTransferDetailsStageOne(String version) {
		try {

			List<String> roles = jwtUtil.extractRolename();
			String username = jwtUtil.extractUsername();
//			ShareTransferRoleType shareTransferRoleType1 = shareTransferRoleTypeDao.getAllShareTransferRoleType(uuid);
			CheckerMakerModel checkerMaker = checkerMakerDao.getCheckerMakerData("Share Transfer Request");
			if (roles.contains(checkerMaker.getMaker())) {
			List<DashboardInterface> shareTransferMaster = shareTransferDAO.getVerifierShareTransferDetailsStageOneForMaker();
			if (shareTransferMaster.size() != 0) {
				
//					List<ShareTransferRoleInterface> maker = shareTransferRoleTypeDao.getMakerDetails();
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster, version);
				}
			else {
				log.error("In getVerifierShareTransferDetailsStageOne NO_DATA11111111--------------------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
				
			}else if(roles.contains(checkerMaker.getChecker())) {
				List<DashboardInterface> shareTransferMaster = shareTransferDAO.getVerifierShareTransferDetailsStageOneForChecker();
				if (shareTransferMaster.size() != 0) {
					
//					List<ShareTransferRoleInterface> maker = shareTransferRoleTypeDao.getMakerDetails();
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster, version);
				}
			else {
				log.error("In getVerifierShareTransferDetailsStageOne NO_DATA22222222222--------------------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
				
			} else {
				log.error("In getVerifierShareTransferDetailsStageOne INVALID_ROLE--------------------------------");
				return response.getAuthResponse("INVALID_ROLE", HttpStatus.FORBIDDEN, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("In getVerifierShareTransferDetailsStageOne in Exception INTERNAL_SERVER_ERROR--------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

//	public ResponseEntity<?> getVerifierShareTransferDetailsStageTwo(String version) {
//		try {
//			List<DashboardInterface> shareTransferMaster = shareTransferDAO.getVerifierShareTransferDetailsStageTwo();
//			if (shareTransferMaster.size() != 0) {
//				return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster, version);
//			} else {
//				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
//			}
//
//		} catch (Exception e) {
//			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
//		}
//	}
	
	public ResponseEntity<?> getVerifierShareTransferDetailsStageTwo(String version) {
		try {

			List<String> roles = jwtUtil.extractRolename();
			String username = jwtUtil.extractUsername();
//			ShareTransferRoleType shareTransferRoleType1 = shareTransferRoleTypeDao.getAllShareTransferRoleType(uuid);
			CheckerMakerModel checkerMaker = checkerMakerDao.getCheckerMakerData("Share Transfer Request");
			if (roles.contains(checkerMaker.getMaker())) {
			List<DashboardInterface> shareTransferMaster = shareTransferDAO.getVerifierShareTransferDetailsStageTwoForMaker();
			if (shareTransferMaster.size() != 0) {
				
//					List<ShareTransferRoleInterface> maker = shareTransferRoleTypeDao.getMakerDetails();
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster, version);
				}
			else {
				log.error("In getVerifierShareTransferDetailsStageTwo in NO_DATA111111111111--------------------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
				
			}else if(roles.contains(checkerMaker.getChecker())) {
				List<DashboardInterface> shareTransferMaster = shareTransferDAO.getVerifierShareTransferDetailsStageTwoForChecker();
				if (shareTransferMaster.size() != 0) {
					
//					List<ShareTransferRoleInterface> maker = shareTransferRoleTypeDao.getMakerDetails();
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster, version);
				}
			else {
				log.error("In getVerifierShareTransferDetailsStageTwo NO_DATA222222222--------------------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
				
			} else {
				log.error("In getVerifierShareTransferDetailsStageTwo INVALID_ROLE--------------------------------");
				return response.getAuthResponse("INVALID_ROLE", HttpStatus.FORBIDDEN, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("In getVerifierShareTransferDetailsStageTwo in Exception INTERNAL_SERVER_ERROR--------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getShareTransferDetailsStageTwoConfirmation(String version) {
		try {
			List<DashboardInterface> shareTransferMaster = shareTransferDAO
					.getShareTransferDetailsStageTwoConfirmation();
			if (shareTransferMaster.size() != 0) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster, version);
			} else {
				log.error("In getShareTransferDetailsStageTwoConfirmation in NO_DATA--------------------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}

		} catch (Exception e) {
			log.error("In getShareTransferDetailsStageTwoConfirmation in Exception INTERNAL_SERVER_ERROR--------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getInternalApplication(String version) {
		try {
			String createdBy = jwtUtil.extractUsername();
			List<DashboardInterface> shareTransferMaster = shareTransferDAO.getInternalApplication(createdBy);

			if (shareTransferMaster.size() != 0) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster, version);
			} else {
				log.error("In getShareTransferDetailsStageTwoConfirmation NO_DATA--------------------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
			// return response.getAuthResponse("SUCCESS", HttpStatus.OK,
			// shareTransferMaster, version);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("In getShareTransferDetailsStageTwoConfirmation in Exception INTERNAL_SERVER_ERROR--------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

}
