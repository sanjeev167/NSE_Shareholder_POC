package com.nseit.shareholder1.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.nseit.shareholder1.ENUM.StatusCode;
import com.nseit.shareholder1.dao.BatchDAO;
import com.nseit.shareholder1.dao.CagDAO;
import com.nseit.shareholder1.dao.CheckerMakerDao;
import com.nseit.shareholder1.dao.ForeignResidentDAO;
import com.nseit.shareholder1.dao.ShareTransferDAO;
import com.nseit.shareholder1.dao.ShareTransferRoleTypeDao;
import com.nseit.shareholder1.dao.UserMasterDao;
import com.nseit.shareholder1.emailphone.EmailApplication;
import com.nseit.shareholder1.metadatamodel.NetWorth;
import com.nseit.shareholder1.metadatamodel.NetWorthCibilScore;
import com.nseit.shareholder1.model.BatchModel;
import com.nseit.shareholder1.model.BatchPreListingAboveTwo;
import com.nseit.shareholder1.model.BatchPreListingBelowTwo;
import com.nseit.shareholder1.model.CagModel;
import com.nseit.shareholder1.model.MailModel;
import com.nseit.shareholder1.model.PostCal;
import com.nseit.shareholder1.model.ShareTransferMaster;
import com.nseit.shareholder1.model.ShareTransferRoleType;
import com.nseit.shareholder1.model.UserMaster;
import com.nseit.shareholder1.modelInterfaces.BatchListingAboveTwo;
import com.nseit.shareholder1.modelInterfaces.BatchListingInterface;
import com.nseit.shareholder1.modelInterfaces.MovementInterface;
import com.nseit.shareholder1.modelInterfaces.NetWorthCibilScoreInterface;
import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BatchService {

	@Autowired
	ResponseUtil response;

	@Autowired
	BatchDAO batchDAO;

	@Autowired
	JwtUtil jwt;

	@Autowired
	ShareTransferRoleTypeDao shareTransferRoleTypeDao;

	@Autowired
	ShareTransferDAO shareTransferDAO;

	@Autowired
	ForeignResidentDAO foreignResidentDAO;

	@Autowired
	CheckerMakerDao checkerMakerDao;

	@Value("${emailtoggle}")
	private String emailToggle;

	@Autowired
	EmailApplication emailApplication;

	@Autowired
	ReportsService reportService;

	@Autowired
	UserMasterDao userMasterDao;

	@Autowired
	CagDAO cagDAO;

	public ResponseEntity<?> prebatchListingBelowTwo(String version) {
		try {
			List<BatchListingInterface> batchModel1 = batchDAO.getListingForBatch();
			List<BatchPreListingBelowTwo> batchModel = new ArrayList<BatchPreListingBelowTwo>();
			for (BatchListingInterface b1 : batchModel1) {
				MovementInterface fr = foreignResidentDAO.getMovementForeignResident(b1.getUuid());
				MovementInterface tmp = foreignResidentDAO.getMovementTmToP(b1.getUuid());
				String movement = tmp.getCategoryTransfer() + " " + fr.getCategoryTransfer();
				BatchPreListingBelowTwo batchPreListingBelowTwo = new BatchPreListingBelowTwo();
				batchPreListingBelowTwo.setUuid(b1.getUuid());
				batchPreListingBelowTwo.setBuyername(b1.getBuyername());
				batchPreListingBelowTwo.setSellerName(b1.getSellerName());
				batchPreListingBelowTwo.setPreviousShares(b1.getPreviousShares());
				batchPreListingBelowTwo.setNoOfShares(b1.getNoOfShares());
				batchPreListingBelowTwo.setPercentage(b1.getPercentage());
				batchPreListingBelowTwo.setPercentageHolding(b1.getPercentageHolding());
				batchPreListingBelowTwo.setPersonConcert(b1.getPersonConcert());
				batchPreListingBelowTwo.setPricePerShares(b1.getPricePerShares());
				batchPreListingBelowTwo.setProposedShares(b1.getProposedShares());
				batchPreListingBelowTwo.setSaleValue(b1.getSaleValue());
				batchPreListingBelowTwo.setCategoryTransfer(movement);
				batchModel.add(batchPreListingBelowTwo);
			}
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, batchModel, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> prebatchListingAboveTwo(String version) {
		try {
			List<BatchListingAboveTwo> batchModel1 = batchDAO.getPreListingAboveTwo();
			List<BatchPreListingAboveTwo> batchModel = new ArrayList<BatchPreListingAboveTwo>();
			for (BatchListingAboveTwo b1 : batchModel1) {
				MovementInterface fr = foreignResidentDAO.getMovementForeignResident(b1.getUuid());
				MovementInterface tmp = foreignResidentDAO.getMovementTmToP(b1.getUuid());
				String movement = tmp.getCategoryTransfer() + " " + fr.getCategoryTransfer();
				BatchPreListingAboveTwo batchPreListingAboveTwo = new BatchPreListingAboveTwo();
				batchPreListingAboveTwo.setUuid(b1.getUuid());
				batchPreListingAboveTwo.setBuyername(b1.getBuyername());
				batchPreListingAboveTwo.setSellerName(b1.getSellerName());
				batchPreListingAboveTwo.setPreviousShares(b1.getPreviousShares());
				batchPreListingAboveTwo.setNoOfShares(b1.getNoOfShares());
				batchPreListingAboveTwo.setPercentage(b1.getPercentage());
				batchPreListingAboveTwo.setPercentageHolding(b1.getPercentageHolding());
				batchPreListingAboveTwo.setPersonConcert(b1.getPersonConcert());
				batchPreListingAboveTwo.setPricePerShares(b1.getPricePerShares());
				batchPreListingAboveTwo.setProposedShares(b1.getProposedShares());
				batchPreListingAboveTwo.setSaleValue(b1.getSaleValue());

				String abc = b1.getNetWorth().get(0).toString();
				NetWorthCibilScore netWorth = new Gson().fromJson(abc, NetWorthCibilScore.class);
				batchPreListingAboveTwo.setNetWorth(netWorth);
				// for (NetWorth n : netWorth.getNetWorthList()) {
				// List<Float> cibilList = new ArrayList<Float>();
				// List<Double> networthList = new ArrayList<Double>();
				// cibilList.add(n.getCibilScore());
				// networthList.add(n.getNetWorth());
				// batchPreListingAboveTwo.setCibil(cibilList);
				// batchPreListingAboveTwo.setNetworth(networthList);
				// }
				batchPreListingAboveTwo.setCategoryTransfer(movement);
				batchModel.add(batchPreListingAboveTwo);
			}
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, batchModel, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> createBatchBelowTwo(String version, List<Integer> batch) {
		try {

			List<BatchListingInterface> b1 = batchDAO.getListingForBatch();
			List<Long> uuids = new ArrayList<Long>();
			for (BatchListingInterface b : b1) {
				uuids.add(b.getUuid());
			}

			if (uuids.containsAll(batch.stream().mapToLong(Integer::longValue).boxed().collect(Collectors.toList()))) {
				String strBatch = batch.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(","));
				String username = jwt.extractUsername();
				BatchModel batchModel1 = new BatchModel();
				batchModel1.setBatch(strBatch);
				batchModel1.setCreatedBy(username);
				batchModel1.setCategory("BELOW_LIMIT_LEVEL_ONE");
				BatchModel batchModelSave = batchDAO.save(batchModel1);

				List<ShareTransferMaster> shareTransfer = shareTransferDAO.getUuidsDetails1(batch);

				ShareTransferMaster shareTransferMaster = new ShareTransferMaster();
				List<ShareTransferRoleType> shareTransferRoleType = shareTransferRoleTypeDao
						.getAllShareTransfersRoleTypeBatch(batch);
				ShareTransferRoleType shareTransferRoleType1 = new ShareTransferRoleType();
				for (ShareTransferRoleType srt : shareTransferRoleType) {
					shareTransferRoleType1 = (ShareTransferRoleType) srt.clone();
					shareTransferRoleType1.setId(null);
					shareTransferRoleType1.setBatchCreated("Y");
					shareTransferRoleType1.setCreatedby(username);
					shareTransferRoleType1.setStatus(StatusCode.valueOf("IN_PRINCIPAL_PENDING").getMsg());
					shareTransferRoleTypeDao.save(shareTransferRoleType1);
				}
				for (ShareTransferMaster s1 : shareTransfer) {
					shareTransferMaster = (ShareTransferMaster) s1.clone();
					shareTransferMaster.setId(null);
					shareTransferMaster.setStatus(StatusCode.valueOf("IN_PRINCIPAL_PENDING").getMsg());
					shareTransferMaster.setBatchId(batchModelSave);
					shareTransferDAO.save(shareTransferMaster);
				}

				return response.getAuthResponse("SUCCESS", HttpStatus.OK, batchModel1, version);
			} else {
				return response.getAuthResponse("INCORRECT_UUID", HttpStatus.FORBIDDEN, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> createBatchAboveTwo(String version, List<Integer> batch) {
		try {

			List<BatchListingAboveTwo> b1 = batchDAO.getPreListingAboveTwo();
			List<Long> uuids = new ArrayList<Long>();
			for (BatchListingAboveTwo b : b1) {
				log.info("----------BATCH ABOVE 2 : UUID in prelisting " + b.getUuid());
				uuids.add(b.getUuid());
			}
			log.info("----------BATCH ABOVE 2 : UUID list : " + uuids);

			log.info("----------BATCH ABOVE 2 : UUID requested : " + batch);

			if (uuids.containsAll(batch.stream().mapToLong(Integer::longValue).boxed().collect(Collectors.toList()))) {
				String strBatch = batch.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(","));
				String username = jwt.extractUsername();
				BatchModel batchModel1 = new BatchModel();
				batchModel1.setBatch(strBatch);
				batchModel1.setCreatedBy(username);
				batchModel1.setCategory("ABOVE_LIMIT_LEVEL_ONE");
				BatchModel batchModelSave = batchDAO.save(batchModel1);

				List<ShareTransferMaster> shareTransfer = shareTransferDAO.getUuidsDetails1(batch);

				ShareTransferMaster shareTransferMaster = new ShareTransferMaster();
				List<ShareTransferRoleType> shareTransferRoleType = shareTransferRoleTypeDao
						.getAllShareTransfersRoleTypeBatch(batch);
				ShareTransferRoleType shareTransferRoleType1 = new ShareTransferRoleType();
				for (ShareTransferRoleType srt : shareTransferRoleType) {
					shareTransferRoleType1 = (ShareTransferRoleType) srt.clone();
					shareTransferRoleType1.setId(null);
					shareTransferRoleType1.setBatchCreated("Y");
					shareTransferRoleType1.setCreatedby(username);
					shareTransferRoleType1.setStatus(StatusCode.valueOf("IN_PRINCIPAL_PENDING").getMsg());
					shareTransferRoleTypeDao.save(shareTransferRoleType1);
				}
				for (ShareTransferMaster s1 : shareTransfer) {
					shareTransferMaster = (ShareTransferMaster) s1.clone();
					shareTransferMaster.setId(null);
					shareTransferMaster.setStatus(StatusCode.valueOf("IN_PRINCIPAL_PENDING").getMsg());
					shareTransferMaster.setBatchId(batchModelSave);
					shareTransferDAO.save(shareTransferMaster);
				}

				return response.getAuthResponse("SUCCESS", HttpStatus.OK, batchModel1, version);
			} else {
				return response.getAuthResponse("INCORRECT_UUID", HttpStatus.FORBIDDEN, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> batchListingBelowTwo(String version) {
		try {

			log.info("----------BATCH batchListingBelowTwo listing Start : ");
			List<BatchModel> batchList = batchDAO.getBatchs("BELOW_LIMIT_LEVEL_ONE");
			List<Map<String, Object>> resBatchListing = new ArrayList<Map<String, Object>>();
			for (BatchModel b : batchList) {
				Map<String, Object> batchListing = new HashMap<String, Object>();
				String batch = b.getBatch();
				List<Integer> uuid = Arrays.asList(batch.split(",")).stream().map(Integer::parseInt)
						.collect(Collectors.toList());

				List<BatchListingInterface> batchModel1 = batchDAO.batchListingBelowTwo(uuid);

				List<BatchPreListingBelowTwo> batchModel = new ArrayList<BatchPreListingBelowTwo>();
				for (BatchListingInterface b1 : batchModel1) {
					MovementInterface fr = foreignResidentDAO.getMovementForeignResident(b1.getUuid());
					MovementInterface tmp = foreignResidentDAO.getMovementTmToP(b1.getUuid());
					String movement = tmp.getCategoryTransfer() + " " + fr.getCategoryTransfer();
					BatchPreListingBelowTwo batchPreListingBelowTwo = new BatchPreListingBelowTwo();
					batchPreListingBelowTwo.setUuid(b1.getUuid());
					batchPreListingBelowTwo.setBuyername(b1.getBuyername());
					batchPreListingBelowTwo.setSellerName(b1.getSellerName());
					if (b1.getPreviousShares() == null) {
						batchPreListingBelowTwo.setPreviousShares((long) 0);
					} else {
						batchPreListingBelowTwo.setPreviousShares(b1.getPreviousShares());
					}
					batchPreListingBelowTwo.setNoOfShares(b1.getNoOfShares());
					batchPreListingBelowTwo.setPercentage(b1.getPercentage());
					batchPreListingBelowTwo.setPercentageHolding(b1.getPercentageHolding());
					batchPreListingBelowTwo.setPersonConcert(b1.getPersonConcert());
					batchPreListingBelowTwo.setPricePerShares(b1.getPricePerShares());
					batchPreListingBelowTwo.setProposedShares(b1.getProposedShares());
					batchPreListingBelowTwo.setSaleValue(b1.getSaleValue());
					batchPreListingBelowTwo.setCategoryTransfer(movement);
					batchModel.add(batchPreListingBelowTwo);
				}

				Object id = b.getId();
				batchListing.put("id", id);
				batchListing.put("data", batchModel);
				Object createdBy = b.getCreatedBy();
				batchListing.put("createdBy", createdBy);
				Timestamp.valueOf(b.getCreatedOn().toString());
				batchListing.put("createdOn", Timestamp.valueOf(b.getCreatedOn().toString()));
				resBatchListing.add(batchListing);
			}

			log.info("----------BATCH batchListingBelowTwo listing End : ");
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, resBatchListing, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> batchListingAboveTwo(String version) {
		try {

			List<BatchModel> batchList = batchDAO.getBatchs("ABOVE_LIMIT_LEVEL_ONE");
			List<Map<String, Object>> resBatchListing = new ArrayList<Map<String, Object>>();
			for (BatchModel b : batchList) {
				Map<String, Object> batchListing = new HashMap<String, Object>();
				String batch = b.getBatch();
				List<Integer> uuid = Arrays.asList(batch.split(",")).stream().map(Integer::parseInt)
						.collect(Collectors.toList());

				List<BatchListingAboveTwo> batchModel1 = batchDAO.batchListingAboveTwo(uuid);
				List<BatchPreListingAboveTwo> batchModel = new ArrayList<BatchPreListingAboveTwo>();
				for (BatchListingAboveTwo b1 : batchModel1) {
					MovementInterface fr = foreignResidentDAO.getMovementForeignResident(b1.getUuid());
					MovementInterface tmp = foreignResidentDAO.getMovementTmToP(b1.getUuid());
					String movement = tmp.getCategoryTransfer() + " " + fr.getCategoryTransfer();
					BatchPreListingAboveTwo batchPreListingAboveTwo = new BatchPreListingAboveTwo();
					batchPreListingAboveTwo.setUuid(b1.getUuid());
					batchPreListingAboveTwo.setBuyername(b1.getBuyername());
					batchPreListingAboveTwo.setSellerName(b1.getSellerName());
					if (b1.getPreviousShares() == null) {
						batchPreListingAboveTwo.setPreviousShares((long) 0);
					} else {
						batchPreListingAboveTwo.setPreviousShares(b1.getPreviousShares());
					}
					batchPreListingAboveTwo.setNoOfShares(b1.getNoOfShares());
					batchPreListingAboveTwo.setPercentage(b1.getPercentage());
					batchPreListingAboveTwo.setPercentageHolding(b1.getPercentageHolding());
					batchPreListingAboveTwo.setPersonConcert(b1.getPersonConcert());
					batchPreListingAboveTwo.setPricePerShares(b1.getPricePerShares());
					batchPreListingAboveTwo.setProposedShares(b1.getProposedShares());
					batchPreListingAboveTwo.setSaleValue(b1.getSaleValue());

					String abc = b1.getNetWorth().get(0).toString();
					NetWorthCibilScore netWorth = new Gson().fromJson(abc, NetWorthCibilScore.class);
					batchPreListingAboveTwo.setNetWorth(netWorth);
					// for (NetWorth n : netWorth.getNetWorthList()) {
					// List<Float> cibilList = new ArrayList<Float>();
					// List<Double> networthList = new ArrayList<Double>();
					// cibilList.add(n.getCibilScore());
					// networthList.add(n.getNetWorth());
					// batchPreListingAboveTwo.setCibil(cibilList);
					// batchPreListingAboveTwo.setNetworth(networthList);
					// }
					batchPreListingAboveTwo.setCategoryTransfer(movement);
					batchModel.add(batchPreListingAboveTwo);
				}
				Object id = b.getId();
				batchListing.put("id", id);
				batchListing.put("data", batchModel);
				Object createdBy = b.getCreatedBy();
				batchListing.put("createdBy", createdBy);
				Timestamp.valueOf(b.getCreatedOn().toString());
				batchListing.put("createdOn", Timestamp.valueOf(b.getCreatedOn().toString()));
				resBatchListing.add(batchListing);
			}
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, resBatchListing, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> batchApprove(String version, BatchModel batchModel1) {
		// TODO Auto-generated method stub
		try {
			log.info("----------BATCH batchApprove Start :  ID -  " + batchModel1.getId());
			// List<String> roles = jwt.extractRolename();
			String username = jwt.extractUsername();
			Long id = batchModel1.getId();
			BatchModel batchModel = batchDAO.getBatch(id);

			String batch = batchModel.getBatch();
			List<Integer> uuid = Arrays.asList(batch.split(",")).stream().map(Integer::parseInt)
					.collect(Collectors.toList());

			if ((Objects.equal(batchModel.getApproved(), null)) || batchModel.getApproved().equals("N")) {
				batchModel.setApproved("Y");
				batchModel.setApprovedBy(username);
				batchModel.setComments(batchModel1.getComments());
				Timestamp instant = Timestamp.from(Instant.now());
				batchModel.setApprovedOn(instant);
				batchDAO.save(batchModel);
				List<ShareTransferMaster> sr = shareTransferDAO.getUuidsDetails1(uuid);
				ShareTransferMaster shareTransferMaster = new ShareTransferMaster();
				ShareTransferMaster s = null;
				for (ShareTransferMaster s1 : sr) {
					shareTransferMaster = (ShareTransferMaster) s1.clone();
					ShareTransferRoleType shareTransferRoleType1 = shareTransferRoleTypeDao
							.getAllShareTransferRoleType(s1.getUuid());
					ShareTransferRoleType shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
					// newly only if condition added
					if (batchModel.getCategory().equals("BELOW_LIMIT_LEVEL_ONE")) {
						shareTransferRoleType2.setId(null);
					shareTransferRoleType2.setStatus("STAGE_TWO_SUBMISSION_PENDING");
					shareTransferRoleType2.setCreatedby(username);
					shareTransferRoleType2.setChecker(null);
					shareTransferRoleType2.setMaker(null);

					shareTransferMaster.setId(null);
					shareTransferMaster.setStatus((StatusCode.valueOf("STAGE_TWO_SUBMISSION_PENDING").getMsg()));
					s = shareTransferDAO.save(shareTransferMaster);
					shareTransferRoleType2.setShareTransferId(s);
					shareTransferRoleTypeDao.save(shareTransferRoleType2);
					}
					else {
						shareTransferRoleType2.setId(null);
						shareTransferRoleType2.setCreatedby(username);
						shareTransferRoleType2.setChecker(null);
						shareTransferRoleType2.setMaker(null);
						shareTransferMaster.setId(null);
						s = shareTransferDAO.save(shareTransferMaster);
						shareTransferRoleType2.setShareTransferId(s);
						shareTransferRoleTypeDao.save(shareTransferRoleType2);
					}

					String isGovtCompany = batchDAO.getBuyerGovtCompany(s1.getUuid());
					if (isGovtCompany == null || isGovtCompany.equalsIgnoreCase("no")) {
						continue;
					} else {

						CagModel isExist = cagDAO.isExist(s1.getBuyerClientID());
						if (isExist != null) {
							CagModel cagModel = new CagModel();
							cagModel.setClientId(s1.getBuyerClientID());
							// cagModel.setNoOfShares((long) s1.getNoOfShares());
							cagModel.setShareholderName(s1.getBuyerName());

							cagDAO.save(cagModel);
						}
					}
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("clientId", s.getSellerClientId());
				map.put("uuid", String.valueOf(s.getUuid()));
				map.put("status", "Approved");
				String file = reportService.sendApprovalStatus(version, sr);

				for (ShareTransferMaster i : sr) {
					UserMaster user = userMasterDao.loadUserByUserName(i.getSellerClientId());
					if (emailToggle.equals("on")) {
						MailModel mailModel = new MailModel();

						mailModel.setSubject("Status of Application");
						mailModel.setTo(user.getEmail());
						// for(ShareTransferMaster i:sr) {
						//
						// UserMaster userMaster=
						// userMasterDao.loadUserByUserName(i.getSellerClientId());
						// mailModel.setTo(userMaster.getEmail());
						emailApplication.sendStatusEmail1(mailModel, map, file);
					}
				}

				return response.getAuthResponse("SUCCESS", HttpStatus.OK, batchModel, version);
			} else {
				log.info("----------BATCH batchApprove Not Allowed :  ID - " + batchModel1.getId());
				return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null, version);
			}

		} catch (Exception e) {
			log.error("----------BATCH batchApprove Exception ", e);
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> batchReject(String version, BatchModel batchModel1) {
		// TODO Auto-generated method stub
		try {
			// List<String> roles = jwt.extractRolename();
			String username = jwt.extractUsername();
			Long id = batchModel1.getId();
			BatchModel batchModel = batchDAO.getBatch(id);

			String batch = batchModel.getBatch();
			List<Integer> uuid = Arrays.asList(batch.split(",")).stream().map(Integer::parseInt)
					.collect(Collectors.toList());

			if (batchModel.getApproved() == null) {
				batchModel.setApproved("N");
				batchModel.setApprovedBy(username);
				batchModel.setComments(batchModel1.getComments());
				Timestamp instant = Timestamp.from(Instant.now());
				batchModel.setApprovedOn(instant);
				batchDAO.save(batchModel);
				List<ShareTransferMaster> sr = shareTransferDAO.getUuidsDetails1(uuid);
				ShareTransferMaster shareTransferMaster = new ShareTransferMaster();
				for (ShareTransferMaster s1 : sr) {
					shareTransferMaster = (ShareTransferMaster) s1.clone();
					shareTransferMaster.setId(null);
					shareTransferMaster.setStatus((StatusCode.valueOf("STAGE_ONE_VERIFIED").getMsg()));
					shareTransferDAO.save(shareTransferMaster);
				}
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, batchModel, version);
			} else {
				return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> rejectedbatchListingBelowTwo(String version) {
		try {

			List<BatchModel> batchList = batchDAO.getRejectedBatchs("BELOW_LIMIT_LEVEL_ONE");
			List<Map<String, Object>> resBatchListing = new ArrayList<Map<String, Object>>();
			for (BatchModel b : batchList) {
				Map<String, Object> batchListing = new HashMap<String, Object>();
				String batch = b.getBatch();
				List<Integer> uuid = Arrays.asList(batch.split(",")).stream().map(Integer::parseInt)
						.collect(Collectors.toList());

				List<BatchListingInterface> batchModel1 = batchDAO.batchListingBelowTwo(uuid);

				List<BatchPreListingBelowTwo> batchModel = new ArrayList<BatchPreListingBelowTwo>();
				for (BatchListingInterface b1 : batchModel1) {
					MovementInterface fr = foreignResidentDAO.getMovementForeignResident(b1.getUuid());
					MovementInterface tmp = foreignResidentDAO.getMovementTmToP(b1.getUuid());
					String movement = tmp.getCategoryTransfer() + " " + fr.getCategoryTransfer();
					BatchPreListingBelowTwo batchPreListingBelowTwo = new BatchPreListingBelowTwo();
					batchPreListingBelowTwo.setUuid(b1.getUuid());
					batchPreListingBelowTwo.setBuyername(b1.getBuyername());
					batchPreListingBelowTwo.setSellerName(b1.getSellerName());
					if (b1.getPreviousShares() == null) {
						batchPreListingBelowTwo.setPreviousShares((long) 0);
					} else {
						batchPreListingBelowTwo.setPreviousShares(b1.getPreviousShares());
					}
					batchPreListingBelowTwo.setNoOfShares(b1.getNoOfShares());
					batchPreListingBelowTwo.setPercentage(b1.getPercentage());
					batchPreListingBelowTwo.setPercentageHolding(b1.getPercentageHolding());
					batchPreListingBelowTwo.setPersonConcert(b1.getPersonConcert());
					batchPreListingBelowTwo.setPricePerShares(b1.getPricePerShares());
					batchPreListingBelowTwo.setProposedShares(b1.getProposedShares());
					batchPreListingBelowTwo.setSaleValue(b1.getSaleValue());
					batchPreListingBelowTwo.setCategoryTransfer(movement);
					batchModel.add(batchPreListingBelowTwo);
				}

				Object id = b.getId();
				batchListing.put("id", id);
				batchListing.put("data", batchModel);
				batchListing.put("comments", b.getComments());
				Object createdBy = b.getCreatedBy();
				batchListing.put("createdBy", createdBy);
				Timestamp.valueOf(b.getCreatedOn().toString());
				batchListing.put("createdOn", Timestamp.valueOf(b.getCreatedOn().toString()));
				resBatchListing.add(batchListing);
			}
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, resBatchListing, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> rejectedbatchListingAboveTwo(String version) {
		try {

			List<BatchModel> batchList = batchDAO.getRejectedBatchs("ABOVE_LIMIT_LEVEL_ONE");
			List<Map<String, Object>> resBatchListing = new ArrayList<Map<String, Object>>();
			for (BatchModel b : batchList) {
				Map<String, Object> batchListing = new HashMap<String, Object>();
				String batch = b.getBatch();
				List<Integer> uuid = Arrays.asList(batch.split(",")).stream().map(Integer::parseInt)
						.collect(Collectors.toList());

				List<BatchListingAboveTwo> batchModel1 = batchDAO.batchListingAboveTwo(uuid);
				List<BatchPreListingAboveTwo> batchModel = new ArrayList<BatchPreListingAboveTwo>();
				for (BatchListingAboveTwo b1 : batchModel1) {
					MovementInterface fr = foreignResidentDAO.getMovementForeignResident(b1.getUuid());
					MovementInterface tmp = foreignResidentDAO.getMovementTmToP(b1.getUuid());
					String movement = tmp.getCategoryTransfer() + " " + fr.getCategoryTransfer();
					BatchPreListingAboveTwo batchPreListingAboveTwo = new BatchPreListingAboveTwo();
					batchPreListingAboveTwo.setUuid(b1.getUuid());
					batchPreListingAboveTwo.setBuyername(b1.getBuyername());
					batchPreListingAboveTwo.setSellerName(b1.getSellerName());
					if (b1.getPreviousShares() == null) {
						batchPreListingAboveTwo.setPreviousShares((long) 0);
					} else {
						batchPreListingAboveTwo.setPreviousShares(b1.getPreviousShares());
					}
					batchPreListingAboveTwo.setNoOfShares(b1.getNoOfShares());
					batchPreListingAboveTwo.setPercentage(b1.getPercentage());
					batchPreListingAboveTwo.setPercentageHolding(b1.getPercentageHolding());
					batchPreListingAboveTwo.setPersonConcert(b1.getPersonConcert());
					batchPreListingAboveTwo.setPricePerShares(b1.getPricePerShares());
					batchPreListingAboveTwo.setProposedShares(b1.getProposedShares());
					batchPreListingAboveTwo.setSaleValue(b1.getSaleValue());

					String abc = b1.getNetWorth().get(0).toString();
					NetWorthCibilScore netWorth = new Gson().fromJson(abc, NetWorthCibilScore.class);
					batchPreListingAboveTwo.setNetWorth(netWorth);
					// for (NetWorth n : netWorth.getNetWorthList()) {
					// List<Float> cibilList = new ArrayList<Float>();
					// List<Double> networthList = new ArrayList<Double>();
					// cibilList.add(n.getCibilScore());
					// networthList.add(n.getNetWorth());
					// batchPreListingAboveTwo.setCibil(cibilList);
					// batchPreListingAboveTwo.setNetworth(networthList);
					// }
					batchPreListingAboveTwo.setCategoryTransfer(movement);
					batchModel.add(batchPreListingAboveTwo);
				}
				Object id = b.getId();
				batchListing.put("id", id);
				batchListing.put("data", batchModel);
				batchListing.put("comments", b.getComments());
				Object createdBy = b.getCreatedBy();
				batchListing.put("createdBy", createdBy);
				Timestamp.valueOf(b.getCreatedOn().toString());
				batchListing.put("createdOn", Timestamp.valueOf(b.getCreatedOn().toString()));
				resBatchListing.add(batchListing);
			}
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, resBatchListing, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
}
