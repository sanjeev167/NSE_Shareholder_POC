package com.nseit.shareholder1.service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Objects;
import com.nseit.shareholder1.ENUM.StatusCode;
import com.nseit.shareholder1.dao.BrokerRegistrationDAO;
import com.nseit.shareholder1.dao.BenposeDataMasterDAO;
import com.nseit.shareholder1.dao.CheckerMakerDao;
import com.nseit.shareholder1.dao.MembershipPendingPanRegulatoryDao;
import com.nseit.shareholder1.dao.MetadataMasterDao;
import com.nseit.shareholder1.dao.PendingPanRegulatoryDao;
import com.nseit.shareholder1.dao.ShareTransferDAO;
import com.nseit.shareholder1.dao.ShareTransferFileDao;
import com.nseit.shareholder1.dao.ShareTransferRoleTypeDao;
import com.nseit.shareholder1.metadatamodel.AboveFivePercent;
import com.nseit.shareholder1.metadatamodel.CategoryTable;
import com.nseit.shareholder1.dao.UserMasterDao;
import com.nseit.shareholder1.emailphone.EmailApplication;
import com.nseit.shareholder1.metadatamodel.MetadataMaster;
import com.nseit.shareholder1.metadatamodel.MetadataPojo;
import com.nseit.shareholder1.model.CheckerMakerModel;
import com.nseit.shareholder1.model.MailModel;
import com.nseit.shareholder1.model.MembershipPendingPanRegulatory;
import com.nseit.shareholder1.model.PendingPanRegulatory;
import com.nseit.shareholder1.model.ShareTansferApprovalRejectSubmitRequest;
import com.nseit.shareholder1.model.ShareTransferFiles;
import com.nseit.shareholder1.model.ShareTransferMaster;
import com.nseit.shareholder1.model.ShareTransferRoleType;
import com.nseit.shareholder1.model.UserMaster;
import com.nseit.shareholder1.modelInterfaces.BenposeHoldingInterface;
import com.nseit.shareholder1.modelInterfaces.CategoryTableInterface;
import com.nseit.shareholder1.modelInterfaces.ShareTransferInterface;
import com.nseit.shareholder1.modelInterfaces.ShareTransferRoleInterface;
import com.nseit.shareholder1.util.JwtUtil;
//import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.ResponseUtil;
import com.nseit.shareholder1.web.rest.errors.UnrecognizedPropertyException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShareTransferService {

	@Value("${pathnew}")
	private String path;

	@Value("${metadataVersion}")
	private int metadataVersion;

	@Value("${emailtoggle}")
	private String emailToggle;

	@Autowired
	BenposeDataMasterDAO benposeDataMasterDAO;

	@Autowired
	ShareTransferDAO shareTransferDAO;

	@Autowired
	ResponseUtil response;

	// @Autowired
	// private JwtUtil jwtTokenUtil;

	@Autowired
	ShareTransferFileDao shareTransferFileDao;

	@Autowired
	MetadataMasterDao metadataMasterDao;

	@Autowired
	ShareTransferRoleTypeDao shareTransferRoleTypeDao;

	@Autowired
	JwtUtil jwt;

	@Autowired
	PendingPanRegulatoryDao pendingPanRegulatoryDao;

	@Autowired
	MembershipPendingPanRegulatoryDao membershipPendingPanRegulatoryDao;

	@Autowired
	CheckerMakerDao checkerMakerDao;

	@Autowired
	BrokerRegistrationDAO brokerRegistrationDAO;

	@Autowired
	EmailApplication emailApplication;

	@Autowired
	UserMasterDao userMasterDao;

	@Autowired
	ReportsService reportService;

	@Autowired
	BenposeDataMasterService benposeDataMasterService;
		
	public ResponseEntity<?> putShareTransferDetails(String version, ShareTransferMaster shareTransferMaster) {

		/*
		 * log.info("value of version-------->" + metadataVersion);
		 */
		ShareTransferMaster shareTransferresponse;
		String a = createDirectory();
		MetadataPojo metaDataPojo = new MetadataPojo();
		log.info(a);
		List<String> roles = jwt.extractRolename();
		// ShareTransferMaster shareTransferMaster1 =
		// shareTransferDAO.getDetails(shareTransferMaster.getSellerClientId(),
		// shareTransferMaster.getBuyerClientID(),
		// shareTransferMaster.getPricePerShares(),
		// shareTransferMaster.getNoOfShares());
		// ShareTransferMaster
		// shareTransferMaster1=shareTransferDAO.getUuidDetails(shareTransferMaster.getUuid());

		if (shareTransferMaster.getUuid() != null) {
			ShareTransferMaster shareTransferMaster1 = shareTransferDAO.getUuidDetails(shareTransferMaster.getUuid());
			shareTransferMaster.setUuid(shareTransferMaster1.getUuid());
			// adding metaversion functionalities

			/* metaDataPojo.setMetadataVersion(metadataVersion); */
			shareTransferMaster.getMetadata().setMetadataVersion(metadataVersion);

			log.info("value of updated metadataVersion-------->" + metaDataPojo.getMetadataVersion());
			/* shareTransferMaster.setMetadata(metaDataPojo); */
			String status = shareTransferMaster1.getStatus();
			StatusCode stageOneSubmissionPendingstatusCode = StatusCode.valueOf("STAGE_ONE_SUBMISSION_PENDING");
			StatusCode stageOneDocumentDeficiencystatusCode = StatusCode.valueOf("STAGE_ONE_DOCUMENT_DEFICIENCY");
			StatusCode stageTwoDocumentDeficiencystatusCode = StatusCode.valueOf("STAGE_TWO_DOCUMENT_DEFICIENCY");
			StatusCode stageTwoSubmissionPendingstatusCode = StatusCode.valueOf("STAGE_TWO_SUBMISSION_PENDING");
			StatusCode stageOneVerificationPendingstatusCode = StatusCode.valueOf("STAGE_ONE_VERIFICATION_PENDING");
			if (status.equalsIgnoreCase(stageOneSubmissionPendingstatusCode.getMsg())
					|| status.equalsIgnoreCase(stageTwoSubmissionPendingstatusCode.getMsg())
					|| status.equalsIgnoreCase(stageOneDocumentDeficiencystatusCode.getMsg())
					|| status.equalsIgnoreCase(stageTwoDocumentDeficiencystatusCode.getMsg())
					|| (status.equalsIgnoreCase(stageOneVerificationPendingstatusCode.getMsg())
							&& (roles.contains("verifier") || roles.contains("initiator")
									|| roles.contains("approver")))) {
				shareTransferMaster.setStatus(status);
				shareTransferMaster.setCreatedBy(jwt.extractUsername());
				shareTransferresponse = shareTransferDAO.save(shareTransferMaster);
			} else {
				log.info("In putShareTransferDetails in INVALID_STATUS_CODE----------------------------------");

				return response.getAuthResponse("INVALID_STATUS_CODE", HttpStatus.FORBIDDEN, null, version);
			}
			log.info("In putShareTransferDetails in full value of shareTransfer------->" + shareTransferMaster);
			String folder = a + File.separator + shareTransferMaster.getUuid();
			File fileFolder = new File(folder);
			fileFolder.mkdir();

		} else {

			Integer s1 = shareTransferDAO.getShareTranferDetails();
			MetadataPojo metadataPojo1 = new MetadataPojo();
			if (s1 > 0) {
				Long maxUuid = shareTransferDAO.getMaxUuidDetail();
				maxUuid++;
				shareTransferMaster.setUuid(maxUuid);
				metaDataPojo.setMetadataVersion(metadataVersion);
				log.info("In putShareTransferDetails in value of metadataVersion------->" + metadataVersion);
				StatusCode stageOneSubmissionPendingstatusCode = StatusCode.valueOf("STAGE_ONE_SUBMISSION_PENDING");
				shareTransferMaster.setStatus(stageOneSubmissionPendingstatusCode.getMsg());
				shareTransferMaster.setCreatedBy(jwt.extractUsername());
				if (shareTransferMaster.getMetadata() == null) {
					shareTransferMaster.setMetadata(metadataPojo1);
				} else {
					shareTransferMaster.setMetadata(shareTransferMaster.getMetadata());
				}
				// setcreateby
				shareTransferresponse = shareTransferDAO.save(shareTransferMaster);
				String folder = a + File.separator + shareTransferMaster.getUuid();
				File fileFolder1 = new File(folder);
				fileFolder1.mkdir();
			} else {
				Long uuid = (long) 1;
				log.info("value of uuid-------->" + uuid);

				shareTransferMaster.setUuid(uuid);
				metaDataPojo.setMetadataVersion(metadataVersion);
				StatusCode stageOneSubmissionPendingstatusCode = StatusCode.valueOf("STAGE_ONE_SUBMISSION_PENDING");
				shareTransferMaster.setStatus(stageOneSubmissionPendingstatusCode.getMsg());
				shareTransferMaster.setCreatedBy(jwt.extractUsername());
				if (shareTransferMaster.getMetadata() == null) {
					shareTransferMaster.setMetadata(metadataPojo1);
				} else {
					shareTransferMaster.setMetadata(shareTransferMaster.getMetadata());
				}
				// setcreateby
				log.info("In putShareTransferDetails in value of metadataVersion------->" + metadataVersion);

				shareTransferresponse = shareTransferDAO.save(shareTransferMaster);
				String folder = a + File.separator + shareTransferMaster.getUuid();
				File fileFolder2 = new File(folder);
				fileFolder2.mkdir();

			}
		}

		log.info("In putShareTransferDetails in shareTransferMaster1--------------------->" + shareTransferMaster);
		return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferresponse, version);
	}

	public ResponseEntity<?> shareTransferRequest(String version, ShareTransferMaster shareTransferMaster) {
		try {
			String username = jwt.extractUsername();
			List<String> roles = jwt.extractRolename();
			// if (username.startsWith("AR") || username.startsWith("ar")) {
			if (roles.contains("authorizedrep")) {
				List<String> clients = brokerRegistrationDAO.getAllClientIdBasedOnBroker(username.toUpperCase());
				if (clients.contains(shareTransferMaster.getSellerClientId())) {
					return putShareTransferDetails(version, shareTransferMaster);
					// return response.getAuthResponse("SUCCESS", HttpStatus.OK,
					// shareTransferMaster, version);
				} else {
					log.info("Invalid client id------------------------------------");
					return response.getAuthResponse("INVALID_CLIENT_ID", HttpStatus.BAD_REQUEST, null, null);
				}
			}
			// else if (username.endsWith(".com")) {
			else if (roles.contains("approver") || roles.contains("verifier") || roles.contains("initiator")) {
				return putShareTransferDetails(version, shareTransferMaster);
				// return response.getAuthResponse("SUCCESS", HttpStatus.OK,
				// shareTransferMaster, version);
			}
			// else if(username.equals(shareTransferMaster.getSellerClientId())){
			else if (roles.contains("shareholder")) {
				return putShareTransferDetails(version, shareTransferMaster);
				// return response.getAuthResponse("SUCCESS", HttpStatus.OK,
				// shareTransferMaster, version);
			} else {
				return response.getAuthResponse("INVALID_CLIENT_ID", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("In fileMaster in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, null);
		}

	}

	public String createDirectory() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String time = dateFormat.format(now);
		// String pathnew = "D:\\Shareholder_Repository_Project\\shareHolderTest";
		String pathnew = path;
		// File pathnew1 = new File(pathnew + "\\" + time);
		File pathnew1 = new File(pathnew + File.separator + time);
		System.out.println("new path" + pathnew1.getPath());
		pathnew1.mkdir();
		String pathnew2 = pathnew1.getPath();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMM");
		String time1 = dateFormat1.format(now);
		// File pathnew3 = new File(pathnew2 + "\\" + time1);
		File pathnew3 = new File(pathnew2 + File.separator + time1);
		System.out.println("new path" + pathnew3.getPath());
		pathnew3.mkdir();
		log.info("path of pathnew3-------->" + pathnew3.getPath());
		return pathnew3.getPath();

	}

	int fileCount = 0;

	public ResponseEntity<?> fileMaster(String version, MultipartFile multiPartFile, Long uuid) {
		log.info("UUID value------->" + uuid);
		HashMap<String, Object> fileMasterResponse = new HashMap<String, Object>();
		try {

			if (multiPartFile != null) {
				if (uuid > 0) {
					String originalName = "";
					String name = multiPartFile.getOriginalFilename();
					String path1 = createDirectory();
					File filePath = new File(path1 + File.separator + uuid);
					filePath.mkdir();
					String path2 = path1 + File.separator + uuid + File.separator + name;
					Boolean existFile = new File(path2).exists();
					System.out.println("existFile----------------->" + existFile + " path2--------->" + path2);
					if (existFile == true) {
						fileCount = benposeDataMasterService.countSameFiles(
								org.springframework.util.StringUtils.stripFilenameExtension(name),
								path1 + File.separator + uuid);
						System.out.println("fileCount------------->" + fileCount);
						originalName = org.springframework.util.StringUtils.stripFilenameExtension(name) + fileCount;
						String extension = name.substring(name.lastIndexOf("."));
						name = originalName + extension;
						path2 = path1 + File.separator + uuid + File.separator + name;
						System.out.println(
								"if cond existFile----------------->" + existFile + " path2--------->" + path2);
					} else {
						// String path1 = path + name;
						log.info("name-------->" + name);
						originalName = org.springframework.util.StringUtils.stripFilenameExtension(name);
					}
					log.info("orinalName-------->" + originalName);
					File file = new File(path2);
					log.info("path--------->" + path2);
					FileOutputStream fo = new FileOutputStream(file);
					fo.write(multiPartFile.getBytes());
					fo.close();
					log.info("fo---->" + fo);
					ShareTransferFiles shareTransferFiles = new ShareTransferFiles();
					shareTransferFiles.setFileName(name);
					shareTransferFiles.setFilePath(path2);
					shareTransferFiles.setCreatedby(jwt.extractUsername());
					shareTransferFileDao.save(shareTransferFiles);
					fileMasterResponse.put("id", shareTransferFiles.getId());
					fileMasterResponse.put("fileName", shareTransferFiles.getFileName());

					return response.getAuthResponse("SUCCESS", HttpStatus.OK, fileMasterResponse, version);
				} else {
					log.info("In fileMaster in INCORRECT_UUID----------------------------------");
					return response.getAuthResponse("INCORRECT_UUID", HttpStatus.BAD_REQUEST, null, version);
				}
			} else {
				log.info("In fileMaster in FILE_NOT_SELECTED----------------------------------");
				return response.getAuthResponse("FILE_NOT_SELECTED", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("In fileMaster in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> putDataDetails(String version, MetadataMaster metaDataMaster) {
		try {
			MetadataMaster metadataExist = metadataMasterDao.getMetdataDetails(metaDataMaster.getMetadataVersion());
			if (metadataExist == null) {
				metaDataMaster.setMetadataVersion(metadataVersion);
				log.info("In putDataDetails in MetadataMaster value--------> " + metaDataMaster.getMetadata());
				metaDataMaster.getMetadata().setMetadataVersion(metadataVersion);
				metaDataMaster.setCreatedby(jwt.extractUsername());
				metadataMasterDao.save(metaDataMaster);
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, metaDataMaster, null);
			} else {
				log.info("In putDataDetails in METADATA_VERSION_EXIST----------------------------------");
				return response.getAuthResponse("METADATA_VERSION_EXIST", HttpStatus.BAD_REQUEST, null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("In putDataDetails in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, null);
		}

	}

	public ResponseEntity<?> getUuidDetails(String version,
			ShareTansferApprovalRejectSubmitRequest shareTansferApprovalRejectSubmitRequest) {
		HashMap<String, Object> metaDataDetails = new HashMap<String, Object>();
		try {
			if (shareTansferApprovalRejectSubmitRequest.getUuid() > 0) {
				ShareTransferMaster uuidDetails = shareTransferDAO
						.getUuidDetails(shareTansferApprovalRejectSubmitRequest.getUuid());
				metaDataDetails.put("Metadata", uuidDetails.getMetadata());
				metaDataDetails.put("status", uuidDetails.getStatus());
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, metaDataDetails, version);
			} else {
				log.info("In getUuidDetails in INCORRECT_UUID----------------------------------");
				return response.getAuthResponse("INCORRECT_UUID", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (UnrecognizedPropertyException ue) {
			log.info("In getUuidDetails in UnrecognizedPropertyException-----------------------------------");
			return response.getAuthResponse(ue.getMessage(), HttpStatus.BAD_REQUEST, false, version);
		} catch (Exception e) {
			log.error("In getUuidDetails in Exception INTERNAL_SERVER_ERROR", e);
			log.info("In getUuidDetails in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> shareTransferDownloadFile(String version, Long id) {
		try {
			log.info("id------->" + id);
			ShareTransferFiles shareTransferFiles = shareTransferFileDao.getShareTransferFilesDetails(id);
			String filePath = shareTransferFiles.getFilePath();
			String fileName = shareTransferFiles.getFileName();
			// URI uri = new URI(filePath);
			// log.info("URI------------>" + uri);
			File file = new File(filePath);
			// URL url = uri.toURL();
			// URL url=new
			// log.info("URL------------>" + url);
			Path path = Paths.get(file.getAbsolutePath());
			ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
			// byte[] b=resource.toString().getBytes();
			log.info("resource-------------->" + resource);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
			headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
			return ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)

					// .body(new FileSystemResource(file));
					.body(resource);
			// try (InputStream in = url.openStream()) {
			// Files.copy(in, Paths.get(fileName));
			// return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, version);
			// } catch (Exception e) {
			// return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.FORBIDDEN, null,
			// version);
			// }
			//
			//// return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, version);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("In shareTransferDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
		// try (BufferedInputStream inputStream = new BufferedInputStream(new
		// URL(filePath).openStream());
		// FileOutputStream fileOS = new
		// FileOutputStream("D://Shareholder_Repository_Project//shareHolderDownloadTest"))
		// {
		// byte data[] = new byte[1024];
		// int byteContent;
		// while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
		// fileOS.write(data, 0, byteContent);
		// }
		// return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, version);
		// } catch (IOException e) {
		// return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.FORBIDDEN, null,
		// version);
		// }
		//
	}

	public ResponseEntity<?> shareTransferSubmit(String version, ShareTransferMaster shareTransferMaster) {
		try {
			if (shareTransferMaster.getUuid() == null) {
				return response.getAuthResponse("INVALID_UUID", HttpStatus.BAD_REQUEST, null, version);
			}

			ShareTransferMaster shareTransferMaster1 = shareTransferDAO.getUuidDetails(shareTransferMaster.getUuid());
			String status = shareTransferMaster1.getStatus();
			UserMaster userMaster = userMasterDao.loadUserByUserName(shareTransferMaster1.getSellerClientId());
			StatusCode stageOneSubmissionPendingstatusCode = StatusCode.valueOf("STAGE_ONE_SUBMISSION_PENDING");
			log.info("stageOneSubmissionPendingstatusCode-----------" + stageOneSubmissionPendingstatusCode.getMsg());
			StatusCode stageOneVerificationPendingstatusCode = StatusCode.valueOf("STAGE_ONE_VERIFICATION_PENDING");
			// StatusCode stageOneDocumentDeficiencystatusCode =
			// StatusCode.valueOf("STAGE_ONE_DOCUMENT_DEFICIENCY");
			// StatusCode stageTwoSubmissionPendingstatusCode =
			// StatusCode.valueOf("STAGE_TWO_SUBMISSION_PENDING");
			// StatusCode stageTwoDocumentDeficiencystatusCode =
			// StatusCode.valueOf("STAGE_TWO_DOCUMENT_DEFICIENCY");
			StatusCode stageTwoVerificationPendingstatusCode = StatusCode.valueOf("STAGE_TWO_VERIFICATION_PENDING");

			// if (shareTransferMaster1 != null &&
			// (status.equalsIgnoreCase(stageOneSubmissionPendingstatusCode.getMsg())
			// || status.equalsIgnoreCase(stageOneDocumentDeficiencystatusCode.getMsg())
			// || status.equalsIgnoreCase(stageTwoSubmissionPendingstatusCode.getMsg())
			// || status.equalsIgnoreCase(stageTwoDocumentDeficiencystatusCode.getMsg()))) {
			//// shareTransferMaster.setUuid(shareTransferMaster1.getUuid());
			// if (status.equalsIgnoreCase(stageOneSubmissionPendingstatusCode.getMsg())) {
			// shareTransferMaster1.setStatus(stageOneVerificationPendingstatusCode.getMsg());
			// } else if
			// (status.equalsIgnoreCase(stageOneDocumentDeficiencystatusCode.getMsg())) {
			// shareTransferMaster1.setStatus(stageOneVerificationPendingstatusCode.getMsg());
			// } else if
			// (status.equalsIgnoreCase(stageTwoSubmissionPendingstatusCode.getMsg())) {
			// shareTransferMaster1.setStatus(stageTwoVerificationPendingstatusCode.getMsg());
			// } else if
			// (status.equalsIgnoreCase(stageTwoDocumentDeficiencystatusCode.getMsg())) {
			// shareTransferMaster1.setStatus(stageTwoVerificationPendingstatusCode.getMsg());
			// }
			ShareTransferMaster shareTransferMaster2 = new ShareTransferMaster();

			List<String> panList = shareTransferDAO.getAllBuyerPan(shareTransferMaster.getUuid());

			List<String> catPanList = new ArrayList<String>();
			List<String> totalList = new ArrayList<String>();
			List<String> catList = shareTransferDAO.getAllCategoryPan(shareTransferMaster.getUuid());

			for (String c : catList) {

				JSONArray json = new JSONArray(c);
				for (int i = 0; i < json.length(); i++) {
					String pan1 = json.getJSONObject(i).getString("pan");
					catPanList.add(pan1);

				}

			}
			totalList.addAll(panList);
			totalList.addAll(catPanList);
			List<String> roles = jwt.extractRolename();

			boolean checkerList = roles.contains("verifier");
			System.out.println("checkerList------->" + checkerList);
			if (status.equalsIgnoreCase("REJECTED")) {
				return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null, version);
			}
			if (shareTransferMaster.getMetadata().getShareTransferRequestDetails().getShareTransferDetails()
					.getModeoftransfer().equalsIgnoreCase("requestChangeDematAc") && roles.contains("verifier")) {
				status = "STAGE_TWO_SUBMISSION_PENDING";
			}
			switch (StatusCode.valueOf(status)) {
				case STAGE_ONE_DOCUMENT_DEFICIENCY:
				case STAGE_ONE_SUBMISSION_PENDING:
					shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
					shareTransferMaster2.setStatus(stageOneVerificationPendingstatusCode.getMsg());
				shareTransferMaster2.setNoOfShares(shareTransferMaster.getMetadata().getShareTransferRequestDetails()
									.getShareTransferDetails().getNoOfShares());
					shareTransferMaster2.setMetadata(shareTransferMaster.getMetadata());
					shareTransferMaster2.setId(null);

					log.info("In shareTransferSubmit in sharetransfermaster2--------------->" + shareTransferMaster2);
					shareTransferDAO.save(shareTransferMaster2);

					// NEW CODE ADDED FOR REGULATORY CHECK
					if (totalList.size() > 0) {
						for (String p : totalList) {
							PendingPanRegulatory pendingPanRegulatory = new PendingPanRegulatory();
							pendingPanRegulatory.setPan(p);
							pendingPanRegulatory.setShareTransferId(shareTransferMaster2.getId());
							pendingPanRegulatory.setShareTransferUuid(shareTransferMaster2.getUuid());
							pendingPanRegulatoryDao.save(pendingPanRegulatory);
							MembershipPendingPanRegulatory membershipPendingPanRegulatory = new MembershipPendingPanRegulatory();
							membershipPendingPanRegulatory.setPan(p);
							membershipPendingPanRegulatory.setShareTransferId(shareTransferMaster1.getId());
							membershipPendingPanRegulatory.setShareTransferUuid(shareTransferMaster1.getUuid());
							membershipPendingPanRegulatoryDao.save(membershipPendingPanRegulatory);
						}

					} else {
						return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
					}
					Map<String, String> map = new HashMap<String, String>();
					map.put("clientId", shareTransferMaster2.getSellerClientId());
					map.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
					map.put("status", "Submit");
					if (emailToggle.equals("on")) {
						MailModel mailModel = new MailModel();

						mailModel.setSubject("Status of Application");

						mailModel.setTo(userMaster.getEmail());
						emailApplication.sendStatusEmail(mailModel, map);
					}

					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
				case STAGE_TWO_SUBMISSION_PENDING:
				case STAGE_TWO_DOCUMENT_DEFICIENCY:
					shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
					shareTransferMaster2.setStatus(stageTwoVerificationPendingstatusCode.getMsg());
					shareTransferMaster2.setId(null);
				shareTransferMaster2.setNoOfShares(shareTransferMaster.getMetadata().getShareTransferRequestDetails()
									.getShareTransferDetails().getNoOfShares());
					shareTransferMaster2.setMetadata(shareTransferMaster.getMetadata());
					log.info("In shareTransferSubmit in sharetransfermaster2--------------->" + shareTransferMaster2);
					shareTransferDAO.save(shareTransferMaster2);
					// NEW CODE ADDED FOR REGULATORY CHECK
					if (totalList.size() > 0) {
						for (String p : totalList) {
							PendingPanRegulatory pendingPanRegulatory = new PendingPanRegulatory();
							pendingPanRegulatory.setPan(p);
							pendingPanRegulatory.setShareTransferId(shareTransferMaster2.getId());
							pendingPanRegulatory.setShareTransferUuid(shareTransferMaster2.getUuid());
							pendingPanRegulatoryDao.save(pendingPanRegulatory);
							MembershipPendingPanRegulatory membershipPendingPanRegulatory = new MembershipPendingPanRegulatory();
							membershipPendingPanRegulatory.setPan(p);
							membershipPendingPanRegulatory.setShareTransferId(shareTransferMaster1.getId());
							membershipPendingPanRegulatory.setShareTransferUuid(shareTransferMaster1.getUuid());
							membershipPendingPanRegulatoryDao.save(membershipPendingPanRegulatory);
						}
					} else {
						return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
					}
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("clientId", shareTransferMaster2.getSellerClientId());
					map1.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
					map1.put("status", "Submit");
					if (emailToggle.equals("on")) {
						MailModel mailModel = new MailModel();

						mailModel.setSubject("Status of Application");

						mailModel.setTo(userMaster.getEmail());
						emailApplication.sendStatusEmail(mailModel, map1);
					}
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
				default:
					log.info("In shareTransferSubmit in status code------------->" + status);
					log.info("In shareTransferSubmit in INVALID_STATUS_CODE----------------------------------");
					return response.getAuthResponse("INVALID_STATUS_CODE", HttpStatus.FORBIDDEN, null, version);
			}

			// shareTransferDAO.save(shareTransferMaster1);
			// return response.getAuthResponse("SUCCESS", HttpStatus.OK,
			// shareTransferMaster1, version);
			//
			// } else {
			//
			// return response.getAuthResponse("INVALID_STATUS_CODE", HttpStatus.FORBIDDEN,
			// null, version);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			log.info("In shareTransferSubmit in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> shareTransferApproval(String version, Long uuid, String comments) {
		try {

			List<String> roles = jwt.extractRolename();
			String username = jwt.extractUsername();
			ShareTransferRoleType shareTransferRoleType2 = new ShareTransferRoleType();

			ShareTransferMaster shareTransferMaster1 = shareTransferDAO.getUuidDetails(uuid);
			UserMaster userMaster = userMasterDao.loadUserByUserName(shareTransferMaster1.getSellerClientId());
			ShareTransferRoleType shareTransferRoleType1 = shareTransferRoleTypeDao.getAllShareTransferRoleType(uuid);
			CheckerMakerModel checkerMaker = checkerMakerDao.getCheckerMakerData("Share Transfer Request");

			String status = shareTransferMaster1.getStatus();
			log.info("In shareTransferApproval in status-------------->" + status);

			// StatusCode verifiedstatusCode = StatusCode.valueOf("VERIFIED");
			// StatusCode transferPendingFromDepositorystatusCode =
			// StatusCode.valueOf("TRANSFER_PENDING_FROM_DEPOSITORY");
			if (status.equalsIgnoreCase("REJECTED")) {
				return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null, version);
			}
			ShareTransferMaster shareTransferMaster2 = new ShareTransferMaster();
			switch (StatusCode.valueOf(status)) {
				case STAGE_ONE_VERIFICATION_PENDING:

					shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
					// shareTransferMaster2.setStatus(verifiedstatusCode.getMsg());

					shareTransferMaster2.setId(null);

					log.info("In shareTransferApproval in sharetransfermaster2--------------->" + shareTransferMaster2);
					if (roles.contains(checkerMaker.getMaker())) {

						if (shareTransferRoleType1 == null) {
							shareTransferRoleType2.setCreatedby(username);
							shareTransferRoleType2.setMaker("Y");
							shareTransferRoleType2.setUuid(uuid);
							shareTransferRoleType2.setStatus(shareTransferMaster1.getStatus());

							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							Timestamp instant = Timestamp.from(Instant.now());
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedBy(username);
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setComments(comments); //
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedOn(instant);
							shareTransferRoleType2.setComment(comments);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);

						} else {
							shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
							shareTransferRoleType2.setId(null);
							if (shareTransferRoleType1.getMaker() == null
									|| shareTransferRoleType1.getMaker().equals("N")) {
								shareTransferRoleType2.setChecker(null);
								shareTransferRoleType2.setMaker("Y");
								shareTransferRoleType2.setStatus(shareTransferMaster1.getStatus());
								shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
								shareTransferRoleType2.setCreatedby(username);
								Timestamp instant = Timestamp.from(Instant.now());
								shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedBy(username);
								shareTransferMaster2.getMetadata().getRegulatoryChecks().setComments(comments); //
								shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedOn(instant);
								shareTransferRoleType2.setComment(comments);
								shareTransferRoleTypeDao.save(shareTransferRoleType2);

							}

							else {
								log.info(
										"In shareTransferApproval in STAGE_ONE_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED----------------------------------");
								return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
										version);
							}
						}
					} else if (roles.contains(checkerMaker.getChecker())) {
						if ((shareTransferRoleType1.getMaker().equals("Y"))
								&& (shareTransferRoleType1.getChecker() == null)) {
							shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
							shareTransferRoleType2.setId(null);
							shareTransferRoleType2.setChecker("Y");
							if (shareTransferMaster1.getMetadata().getShareTransferRequestDetails()
									.getShareTransferDetails()
									.getModeoftransfer().equalsIgnoreCase("requestChangeDematAc")) {
								shareTransferMaster2
										.setStatus(StatusCode.valueOf("STAGE_TWO_SUBMISSION_PENDING").getMsg());
								// status = "STAGE_TWO_SUBMISSION_PENDING";
							} else {
								shareTransferMaster2.setStatus(StatusCode.valueOf("STAGE_ONE_VERIFIED").getMsg());
							}
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							shareTransferRoleType2.setCreatedby(username);
							Timestamp instant = Timestamp.from(Instant.now());
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setApprovedBy(username);
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setComments(comments); //
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setApprovedOn(instant);
							shareTransferRoleType2.setComment(comments);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);
						} else {
							log.info(
									"In shareTransferApproval in STAGE_ONE_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED----------------------------------");
							return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
									version);
						}

					} else {
						log.info(
								"In shareTransferApproval in STAGE_ONE_VERIFICATION_PENDING UNAUTHORIZED----------------------------------");
						return response.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, null, version);
					}

					shareTransferMaster2 = shareTransferDAO.save(shareTransferMaster2);

					shareTransferRoleType2.setShareTransferId(shareTransferMaster2);
					// shareTransferRoleType2.setComments("Approval file");
					shareTransferRoleTypeDao.save(shareTransferRoleType2);
					// Map<String,String> map=new HashMap<String,String>();
					// map.put("clientId", shareTransferMaster2.getSellerClientId());
					// map.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
					// map.put("status", "Approved");
					// String file=reportService.sendApprovalStatus(version, shareTransferMaster1);
					// if(emailToggle.equals("on")) {
					// MailModel mailModel = new MailModel();
					//
					// mailModel.setSubject("Status of Application");
					//
					// mailModel.setTo("pshukla@nseit.com");
					//
					// emailApplication.sendStatusEmail1(mailModel, map,file);}
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
				case STAGE_TWO_VERIFICATION_PENDING:

					shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
					// shareTransferMaster2.setStatus(transferPendingFromDepositorystatusCode.getMsg());
					shareTransferMaster2.setId(null);

					log.info("In shareTransferApproval in sharetransfermaster2--------------->" + shareTransferMaster2);
					if (roles.contains(checkerMaker.getMaker())) {

						if (shareTransferRoleType1.getStatus() != shareTransferMaster1.getStatus()
								&& ((Objects.equal(shareTransferRoleType1.getChecker(), null)
										|| shareTransferRoleType1.getChecker().equals("N")))) {
							shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
							shareTransferRoleType2.setId(null);
							shareTransferRoleType2.setChecker(null);
							shareTransferRoleType2.setMaker("Y");
							shareTransferRoleType2.setStatus(shareTransferMaster1.getStatus());
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							shareTransferRoleType2.setCreatedby(username);
							Timestamp instant = Timestamp.from(Instant.now());
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedBy(username);
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setComments(comments); //
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedOn(instant);
							shareTransferRoleType2.setComment(comments);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);

						}

						else {
							log.info(
									"In shareTransferApproval in STAGE_TWO_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED----------------------------------");
							return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
									version);
						}

					} else if (roles.contains(checkerMaker.getChecker())) {
						if (Objects.equal(shareTransferRoleType1.getMaker(), "Y")
								&& (Objects.equal(shareTransferRoleType1.getChecker(), null)
										|| shareTransferRoleType1.getChecker().equals("N"))) {
							shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
							shareTransferRoleType2.setId(null);
							shareTransferRoleType2.setId(null);
							shareTransferRoleType2.setChecker("Y");
							shareTransferMaster2
									.setStatus(StatusCode.valueOf("TRANSFER_PENDING_FROM_DEPOSITORY").getMsg());
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							shareTransferRoleType2.setCreatedby(username);
							Timestamp instant = Timestamp.from(Instant.now());
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setApprovedBy(username);
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setComments(comments); //
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setApprovedOn(instant);
							shareTransferRoleType2.setComment(comments);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);
						} else {
							log.info(
									"In shareTransferApproval in STAGE_TWO_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED----------------------------------");
							return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
									version);
						}

					} else {
						log.info(
								"In shareTransferApproval in STAGE_TWO_VERIFICATION_PENDING UNAUTHORIZED----------------------------------");
						return response.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, null, version);
					}
					shareTransferMaster2 = shareTransferDAO.save(shareTransferMaster2);

					shareTransferRoleType2.setShareTransferId(shareTransferMaster2);
					// shareTransferRoleType2.setComments("Approval file");
					shareTransferRoleTypeDao.save(shareTransferRoleType2);
					// Map<String,String> map1=new HashMap<String,String>();
					// map1.put("clientId", shareTransferMaster2.getSellerClientId());
					// map1.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
					// map1.put("status", "Approved");
					// String file1=reportService.sendApprovalStatus(version, shareTransferMaster1);
					// if(emailToggle.equals("on")) {
					// MailModel mailModel = new MailModel();
					//
					// mailModel.setSubject("Status of Application");
					//
					// mailModel.setTo("pshukla@nseit.com");
					// emailApplication.sendStatusEmail1(mailModel, map1,file1);}
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
				default:
					log.info("In shareTransferApproval in status code------------->" + status);
					log.info(
							"In shareTransferApproval in STAGE_TWO_VERIFICATION_PENDING INVALID_STATUS_CODE----------------------------------");
					return response.getAuthResponse("INVALID_STATUS_CODE", HttpStatus.FORBIDDEN, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In shareTransferApproval in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> shareTransferReject(String version, Long uuid, String comments) {
		try {

			String username = jwt.extractUsername();
			List<String> roles = jwt.extractRolename();
			log.info("roles-------------->" + roles);
			CheckerMakerModel checkerMaker = checkerMakerDao.getCheckerMakerData("Share Transfer Request");
			ShareTransferMaster shareTransferMaster1 = shareTransferDAO.getUuidDetails(uuid);
			UserMaster userMaster = userMasterDao.loadUserByUserName(shareTransferMaster1.getSellerClientId());
			ShareTransferRoleType shareTransferRoleType1 = shareTransferRoleTypeDao.getAllShareTransferRoleType(uuid);
			ShareTransferRoleType shareTransferRoleType2 = new ShareTransferRoleType();
			String status = shareTransferMaster1.getStatus();
			log.info("status-------------->" + status);

			// StatusCode stageOneDocumentDeficiencystatusCode =
			// StatusCode.valueOf("STAGE_ONE_DOCUMENT_DEFICIENCY");
			// StatusCode stageTwoDocumentDeficiencystatusCode =
			// StatusCode.valueOf("STAGE_TWO_DOCUMENT_DEFICIENCY");
			if (status.equalsIgnoreCase("REJECTED")) {
				return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
						version);
			}
			ShareTransferMaster shareTransferMaster2 = new ShareTransferMaster();
			switch (StatusCode.valueOf(status)) {
				case STAGE_ONE_VERIFICATION_PENDING:

					shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
					// shareTransferMaster2.setStatus(stageOneDocumentDeficiencystatusCode.getMsg());

					shareTransferMaster2.setId(null);

					log.info("sharetransfermaster2--------------->" + shareTransferMaster2);
					if (roles.contains(checkerMaker.getMaker())) {
						if (shareTransferRoleType1 == null) {
							Timestamp instant = Timestamp.from(Instant.now());
							shareTransferRoleType2.setMaker("N");
							shareTransferRoleType2.setUuid(uuid);
							shareTransferRoleType2.setStatus(shareTransferMaster1.getStatus());
							shareTransferRoleType2.setCreatedby(username);
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedBy(username);
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setComments(comments); //
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedOn(instant);
							shareTransferRoleType2.setComment(comments);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);
						} else {
							shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
							shareTransferRoleType2.setId(null);
							if (shareTransferRoleType1.getMaker() == null
									|| shareTransferRoleType1.getMaker().equals("N")) {
								shareTransferRoleType2.setChecker(null);
								shareTransferRoleType2.setMaker("N");
								shareTransferRoleType2.setStatus(shareTransferMaster1.getStatus());
								shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
								shareTransferRoleType2.setCreatedby(username);///
								Timestamp instant = Timestamp.from(Instant.now());
								shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedBy(username);
								shareTransferMaster2.getMetadata().getRegulatoryChecks().setComments(comments); //
								shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedOn(instant);
								shareTransferRoleType2.setComment(comments);
								shareTransferRoleTypeDao.save(shareTransferRoleType2);
							}

							else {
								log.info(
										"In shareTransferReject in STAGE_ONE_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED----------------------------------");
								return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
										version);
							}
						}
					} else if (roles.contains(checkerMaker.getChecker())) {

						if (shareTransferRoleType1.getMaker().equals("Y")
								&& (Objects.equal(shareTransferRoleType1.getChecker(), null))) {
							Timestamp instant = Timestamp.from(Instant.now());
							shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
							shareTransferRoleType2.setId(null);
							shareTransferRoleType2.setChecker("N");
							// shareTransferMaster2.setStatus(StatusCode.valueOf("STAGE_ONE_VERIFIED").getMsg());
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							shareTransferRoleType2.setCreatedby(username);
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setApprovedBy(username);
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setComments(comments); //
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setApprovedOn(instant);
							shareTransferRoleType2.setComment(comments);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);
						} else {
							log.info(
									"In shareTransferReject in STAGE_ONE_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED----------------------------------");
							return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
									version);
						}

					} else {
						log.info(
								"In shareTransferReject in STAGE_ONE_VERIFICATION_PENDING UNAUTHORIZED----------------------------------");
						return response.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, null, version);
					}
					// shareTransferDAO.save(shareTransferMaster2);
					shareTransferMaster2 = shareTransferDAO.save(shareTransferMaster2);

					shareTransferRoleType2.setShareTransferId(shareTransferMaster2);
					//newly added for json compare
					shareTransferRoleTypeDao.save(shareTransferRoleType2);
					// shareTransferRoleType2.setComments("Approval file");
					Map<String, String> map = new HashMap<String, String>();
					map.put("clientId", shareTransferMaster2.getSellerClientId());
					map.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
					map.put("status", "Reject");
					if (emailToggle.equals("on")) {
						MailModel mailModel = new MailModel();

						mailModel.setSubject("Status of Application");

						mailModel.setTo(userMaster.getEmail());
						emailApplication.sendStatusEmail(mailModel, map);
					}
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
				case STAGE_TWO_VERIFICATION_PENDING:

					shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
					// shareTransferMaster2.setStatus(stageTwoDocumentDeficiencystatusCode.getMsg());
					shareTransferMaster2.setId(null);

					log.info("In shareTransferReject in sharetransfermaster2--------------->" + shareTransferMaster2);
					if (roles.contains(checkerMaker.getMaker())) {

						if (shareTransferRoleType1.getStatus() != shareTransferMaster1.getStatus()
								&& (Objects.equal(shareTransferRoleType1.getChecker(), null)
										|| shareTransferRoleType1.getChecker().equals("N"))) {
							shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
							shareTransferRoleType2.setId(null);
							shareTransferRoleType2.setChecker(null);
							shareTransferRoleType2.setMaker("N");
							shareTransferRoleType2.setStatus(shareTransferMaster1.getStatus());
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							shareTransferRoleType2.setCreatedby(username);
							Timestamp instant = Timestamp.from(Instant.now());
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedBy(username);
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setComments(comments); //
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedOn(instant);
							shareTransferRoleType2.setComment(comments);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);
						}

						else {
							log.info(
									"In shareTransferReject in STAGE_TWO_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED----------------------------------");
							return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
									version);
						}

					} else if (roles.contains(checkerMaker.getChecker())) {
						if (shareTransferRoleType1.getMaker().equals("Y")
								&& (Objects.equal(shareTransferRoleType1.getChecker(), null))) {
							shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
							shareTransferRoleType2.setId(null);

							shareTransferRoleType2.setChecker("N");

							// shareTransferMaster2.setStatus(StatusCode.valueOf("TRANSFER_PENDING_FROM_DEPOSITORY").getMsg());
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							shareTransferRoleType2.setCreatedby(username);
							Timestamp instant = Timestamp.from(Instant.now());
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setApprovedBy(username);
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setComments(comments); //
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setApprovedOn(instant);
							shareTransferRoleType2.setComment(comments);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);
						} else {
							log.info(
									"In shareTransferReject in STAGE_TWO_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED----------------------------------");
							return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
									version);
						}

					} else {
						log.info(
								"In shareTransferReject in STAGE_TWO_VERIFICATION_PENDING UNAUTHORIZED----------------------------------");
						return response.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, null, version);
					}
					// shareTransferDAO.save(shareTransferMaster2);
					shareTransferMaster2 = shareTransferDAO.save(shareTransferMaster2);

					shareTransferRoleType2.setShareTransferId(shareTransferMaster2);
					//newly added for json compare
					shareTransferRoleTypeDao.save(shareTransferRoleType2);
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("clientId", shareTransferMaster2.getSellerClientId());
					map1.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
					map1.put("status", "Reject");
					if (emailToggle.equals("on")) {
						MailModel mailModel = new MailModel();

						mailModel.setSubject("Status of Application");

						mailModel.setTo(userMaster.getEmail());
						emailApplication.sendStatusEmail(mailModel, map1);
					}
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
				default:
					log.info("In shareTransferReject in status code------------->" + status);
					log.info("In shareTransferReject in default INVALID_STATUS_CODE----------------------------------");
					return response.getAuthResponse("INVALID_STATUS_CODE", HttpStatus.FORBIDDEN, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In shareTransferReject in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> shareTransferRejectStatus(String version, Long uuid) {

		try {

			ShareTransferMaster shareTransferMaster1 = shareTransferDAO.getUuidDetails(uuid);
			UserMaster userMaster = userMasterDao.loadUserByUserName(shareTransferMaster1.getSellerClientId());
			String username = jwt.extractUsername();
			String status = shareTransferMaster1.getStatus();
			ShareTransferMaster shareTransferMaster2 = new ShareTransferMaster();
			if (status.equalsIgnoreCase("REJECTED")) {
				return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
						version);
			}
			switch (StatusCode.valueOf(status)) {
				case STAGE_ONE_VERIFICATION_PENDING:
					shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
					shareTransferMaster2.setId(null);
					shareTransferMaster2.setStatus(StatusCode.valueOf("REJECTED").getMsg());
					shareTransferDAO.save(shareTransferMaster2);
					Map<String, String> map = new HashMap<String, String>();
					map.put("clientId", shareTransferMaster2.getSellerClientId());
					map.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
					map.put("status", "Reject Status");
					if (emailToggle.equals("on")) {
						MailModel mailModel = new MailModel();

						mailModel.setSubject("Status of Application");

						mailModel.setTo(userMaster.getEmail());
						emailApplication.sendStatusEmail(mailModel, map);
					}
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
				case STAGE_TWO_VERIFICATION_PENDING:
					shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
					shareTransferMaster2.setId(null);
					shareTransferMaster2.setStatus(StatusCode.valueOf("REJECTED").getMsg());
					shareTransferDAO.save(shareTransferMaster2);
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("clientId", shareTransferMaster2.getSellerClientId());
					map1.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
					map1.put("status", "Reject Status");
					if (emailToggle.equals("on")) {
						MailModel mailModel = new MailModel();

						mailModel.setSubject("Status of Application");

						mailModel.setTo(userMaster.getEmail());
						emailApplication.sendStatusEmail(mailModel, map1);
					}
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
				default:
					log.info("In shareTransferRejectStatus in status code------------->" + status);
					log.info(
							"In shareTransferRejectStatus in default INVALID_STATUS_CODE----------------------------------");
					return response.getAuthResponse("INVALID_STATUS_CODE", HttpStatus.FORBIDDEN, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info(
					"In shareTransferRejectStatus in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getShareTransferRoleType(String version) {
		try {
			List<ShareTransferRoleInterface> sharetransferRoleType = new ArrayList<ShareTransferRoleInterface>();
			sharetransferRoleType = shareTransferRoleTypeDao.getAllShareTransferRoleDetails();
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, sharetransferRoleType, version);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(
					"In shareTransferRejectStatus in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getFileDetails(String version, Long id) {
		try {
			ShareTransferFiles shareTransferFiles = shareTransferFileDao.getShareTransferFilesDetails(id);
			if (shareTransferFiles != null) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferFiles, version);
			} else {
				log.info("In shareTransferRejectStatus in NO_DATA----------------------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			log.info(
					"In shareTransferRejectStatus in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> shareTransferReverse(String version, Long uuid, String comments) {
		// TODO Auto-generated method stub
		try {

			List<String> roles = jwt.extractRolename();
			String username = jwt.extractUsername();
			ShareTransferRoleType shareTransferRoleType2 = new ShareTransferRoleType();
			ShareTransferMaster shareTransferMaster1 = shareTransferDAO.getUuidDetails(uuid);
			UserMaster userMaster = userMasterDao.loadUserByUserName(shareTransferMaster1.getSellerClientId());
			ShareTransferRoleType shareTransferRoleType1 = shareTransferRoleTypeDao.getAllShareTransferRoleType(uuid);
			CheckerMakerModel checkerMaker = checkerMakerDao.getCheckerMakerData("Share Transfer Request");

			String status = shareTransferMaster1.getStatus();
			log.info("In shareTransferReverse status-------------->" + status);

			// StatusCode verifiedstatusCode = StatusCode.valueOf("VERIFIED");
			// StatusCode transferPendingFromDepositorystatusCode =
			// StatusCode.valueOf("TRANSFER_PENDING_FROM_DEPOSITORY");
			if (status.equalsIgnoreCase("REJECTED")) {
				return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
						version);
			}
			ShareTransferMaster shareTransferMaster2 = new ShareTransferMaster();
			switch (StatusCode.valueOf(status)) {
				case STAGE_ONE_VERIFICATION_PENDING:

					shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
					// shareTransferMaster2.setStatus(verifiedstatusCode.getMsg());

					shareTransferMaster2.setId(null);

					log.info("sharetransfermaster2--------------->" + shareTransferMaster2);
					if (roles.contains(checkerMaker.getMaker())) {
						if (shareTransferRoleType1 == null) {
							shareTransferRoleType2.setCreatedby(username);
							shareTransferRoleType2.setMaker("N");
							shareTransferRoleType2.setUuid(uuid);
							shareTransferRoleType2
									.setStatus(StatusCode.valueOf("STAGE_ONE_DOCUMENT_DEFICIENCY").getMsg());
							shareTransferRoleType2.setComment(comments);
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							Timestamp instant = Timestamp.from(Instant.now());
							log.info("username--------------" + username);

							shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedBy(username);
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setComments(comments); //
							shareTransferMaster2.getMetadata().getRegulatoryChecks().setPreparedOn(instant);
							shareTransferMaster2
									.setStatus(StatusCode.valueOf("STAGE_ONE_DOCUMENT_DEFICIENCY").getMsg());

							shareTransferRoleTypeDao.save(shareTransferRoleType2);
						shareTransferMaster2=shareTransferDAO.save(shareTransferMaster2);
						//newly added code
						shareTransferRoleType2.setShareTransferId(shareTransferMaster2);
						shareTransferRoleTypeDao.save(shareTransferRoleType2);
							Map<String, String> map = new HashMap<String, String>();
							map.put("clientId", shareTransferMaster2.getSellerClientId());
							map.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
							map.put("status", "Reverse");
							if (emailToggle.equals("on")) {
								MailModel mailModel = new MailModel();

								mailModel.setSubject("Status of Application");

								mailModel.setTo(userMaster.getEmail());
							emailApplication.sendStatusEmail(mailModel, map);
						}
							return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
						}

						else {
							if (shareTransferRoleType1.getMaker() == null
									|| shareTransferRoleType1.getMaker().equals("N")) {
								shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
								shareTransferRoleType2.setId(null);
								shareTransferRoleType2.setMaker("N");
								shareTransferRoleType2
										.setStatus(StatusCode.valueOf("STAGE_ONE_DOCUMENT_DEFICIENCY").getMsg());
								shareTransferMaster2
										.setStatus(StatusCode.valueOf("STAGE_ONE_DOCUMENT_DEFICIENCY").getMsg());
								shareTransferRoleType2.setComment(comments);
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
								shareTransferRoleTypeDao.save(shareTransferRoleType2);
							shareTransferMaster2=shareTransferDAO.save(shareTransferMaster2);
							//newly added code
							shareTransferRoleType2.setShareTransferId(shareTransferMaster2);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);
								Map<String, String> map = new HashMap<String, String>();
								map.put("clientId", shareTransferMaster2.getSellerClientId());
								map.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
								map.put("status", "Reverse");
								if (emailToggle.equals("on")) {
									MailModel mailModel = new MailModel();

									mailModel.setSubject("Status of Application");

									mailModel.setTo(userMaster.getEmail());
								emailApplication.sendStatusEmail(mailModel, map);
							}
							return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
							} else {
								log.info(
										"In shareTransferReverse in STAGE_ONE_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED----------------------------------");
								return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
										version);
							}
						}

					} else if (roles.contains(checkerMaker.getChecker())) {
						if (shareTransferRoleType1.getMaker().equals("Y")
								&& (Objects.equal(shareTransferRoleType1.getChecker(), null))) {
							shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
							shareTransferRoleType2.setId(null);
							shareTransferRoleType2.setMaker(null);
							shareTransferRoleType2.setChecker("N");
							shareTransferRoleType2.setComment(comments);
						shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);
							Map<String, String> map = new HashMap<String, String>();
							map.put("clientId", shareTransferMaster2.getSellerClientId());
							map.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
							map.put("status", "Reverse");
							if (emailToggle.equals("on")) {
								MailModel mailModel = new MailModel();

								mailModel.setSubject("Status of Application");

								mailModel.setTo(userMaster.getEmail());
							emailApplication.sendStatusEmail(mailModel, map);
						}
							return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
						} else {
							log.info(
									"In shareTransferReverse in STAGE_ONE_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED----------------------------------");
							return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
									version);
						}
					} else {
						log.info(
								"In shareTransferReverse in STAGE_ONE_VERIFICATION_PENDING INVALID_ROLE----------------------------------");
						return response.getAuthResponse("INVALID_ROLE", HttpStatus.FORBIDDEN, null, version);
					}

				case STAGE_TWO_VERIFICATION_PENDING:

					shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
					// shareTransferMaster2.setStatus(transferPendingFromDepositorystatusCode.getMsg());
					shareTransferMaster2.setId(null);

					log.info("In shareTransferReverse in sharetransfermaster2--------------->" + shareTransferMaster2);
					shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
					// shareTransferMaster2.setStatus(verifiedstatusCode.getMsg());

					shareTransferMaster2.setId(null);

					log.info("In shareTransferReverse in sharetransfermaster2--------------->" + shareTransferMaster2);
					if (roles.contains(checkerMaker.getMaker())) {
						if (((shareTransferRoleType1.getStatus().equalsIgnoreCase("STAGE_TWO_SUBMISSION_PENDING")
								|| shareTransferRoleType1.getStatus()
										.equalsIgnoreCase("STAGE_TWO_VERIFICATION_PENDING"))
								&& Objects.equal(shareTransferRoleType1.getMaker(), null))
								|| (shareTransferRoleType1.getStatus().equalsIgnoreCase("STAGE_TWO_DOCUMENT_DEFICIENCY")
										&& (Objects.equal(shareTransferRoleType1.getMaker(), null)
												|| Objects.equal(shareTransferRoleType1.getMaker(), "N")))) {
							shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
							shareTransferRoleType2.setId(null);
							shareTransferRoleType2.setMaker("N");
							shareTransferRoleType2
									.setStatus(StatusCode.valueOf("STAGE_TWO_DOCUMENT_DEFICIENCY").getMsg());
							shareTransferMaster2
									.setStatus(StatusCode.valueOf("STAGE_TWO_DOCUMENT_DEFICIENCY").getMsg());
							shareTransferRoleType2.setComment(comments);
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);
							shareTransferMaster2=shareTransferDAO.save(shareTransferMaster2);
							//newly added code
							shareTransferRoleType2.setShareTransferId(shareTransferMaster2);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);
							Map<String, String> map = new HashMap<String, String>();
							map.put("clientId", shareTransferMaster2.getSellerClientId());
							map.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
							map.put("status", "Reverse");
							if (emailToggle.equals("on")) {
								MailModel mailModel = new MailModel();

								mailModel.setSubject("Status of Application");

								mailModel.setTo(userMaster.getEmail());
								emailApplication.sendStatusEmail(mailModel, map);
							}
							return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
						} else {
							log.info(
									"In shareTransferReverse in STAGE_TWO_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED line 1057----------------------------------");
							return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null,
									version);
						}
					} else if (roles.contains(checkerMaker.getChecker())) {
						if (Objects.equal(shareTransferRoleType1.getMaker(), "Y")
								&& (Objects.equal(shareTransferRoleType1.getChecker(), null))) {
							shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
							shareTransferRoleType2.setId(null);
							shareTransferRoleType2.setMaker(null);
							shareTransferRoleType2.setChecker("N");
							shareTransferRoleType2.setComment(comments);
							shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
							shareTransferRoleTypeDao.save(shareTransferRoleType2);
							Map<String, String> map = new HashMap<String, String>();
							map.put("clientId", shareTransferMaster2.getSellerClientId());
							map.put("uuid", String.valueOf(shareTransferMaster2.getUuid()));
							map.put("status", "Reverse");
							if (emailToggle.equals("on")) {
								MailModel mailModel = new MailModel();

								mailModel.setSubject("Status of Application");

								mailModel.setTo(userMaster.getEmail());
								emailApplication.sendStatusEmail(mailModel, map);
							}
							return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareTransferMaster2, version);
						} else {
							log.info(
									"In shareTransferReverse in STAGE_TWO_VERIFICATION_PENDING WORKFLOW_NOT_ALLOWED line 1069----------------------------------");
						return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.FORBIDDEN, null, version);
						}
					} else {
						log.info(
								"In shareTransferReverse in STAGE_TWO_VERIFICATION_PENDING INVALID_ROLE----------------------------------");
						return response.getAuthResponse("INVALID_ROLE", HttpStatus.FORBIDDEN, null, version);
					}

				default:
					log.info("status code------------->" + status);
				log.info("In shareTransferReverse in default INVALID_STATUS_CODE-----------------------------------");
					return response.getAuthResponse("INVALID_STATUS_CODE", HttpStatus.FORBIDDEN, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In shareTransferReverse in Exception INTERNAL_SERVER_ERROR------------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> sharesCheck(String version, String clientId) {
		// TODO Auto-generated method stub
		try {
			Long benposeShares = benposeDataMasterDAO.getSharesCheckBenpose(clientId);
			ShareTransferInterface shareTransferShare = shareTransferDAO.getSharesCheck(clientId);
			Map<String, Long> responseMap = new HashMap<String, Long>();
			responseMap.put("benposeShares", benposeShares);

			if(benposeShares == null) {
				benposeShares = (long)0;
			}
			if (shareTransferShare == null) {
				Long currentShares = benposeShares - 0;
				responseMap.put("shareTransferShare", (long) 0);
				responseMap.put("currentShares", currentShares);
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, responseMap, version);
			} else if (benposeShares >= shareTransferShare.getTotalShares()) {
				Long currentShares = benposeShares - shareTransferShare.getTotalShares();
				responseMap.put("shareTransferShare", shareTransferShare.getTotalShares());
				responseMap.put("currentShares", currentShares);
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, responseMap, version);

			} else {
				return response.getAuthResponse("INSUFFICIENT_SHARES", HttpStatus.OK, responseMap, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> reCheckApi(String version, Long uuid) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		ShareTransferMaster shareTransferMaster1 = shareTransferDAO.getUuidDetails(uuid);

		List<String> panList = shareTransferDAO.getAllBuyerPan(uuid);

		List<String> catPanList = new ArrayList<String>();
		List<String> totalList = new ArrayList<String>();
		List<String> catList = shareTransferDAO.getAllCategoryPan(uuid);

		for (String c : catList) {

			JSONArray json = new JSONArray(c);
			for (int i = 0; i < json.length(); i++) {
				String pan1 = json.getJSONObject(i).getString("pan");
				catPanList.add(pan1);

			}

		}
		totalList.addAll(panList);
		totalList.addAll(catPanList);
		try {
			if (totalList.size() > 0) {
				for (String p : totalList) {
					PendingPanRegulatory pendingPanRegulatory = new PendingPanRegulatory();

					pendingPanRegulatory.setPan(p);
					pendingPanRegulatory.setShareTransferId(shareTransferMaster1.getId());
					pendingPanRegulatory.setShareTransferUuid(shareTransferMaster1.getUuid());
					pendingPanRegulatoryDao.save(pendingPanRegulatory);
					hm.put("data", pendingPanRegulatory);
					MembershipPendingPanRegulatory membershipPendingPanRegulatory = new MembershipPendingPanRegulatory();
					membershipPendingPanRegulatory.setPan(p);
					membershipPendingPanRegulatory.setShareTransferId(shareTransferMaster1.getId());
					membershipPendingPanRegulatory.setShareTransferUuid(shareTransferMaster1.getUuid());
					membershipPendingPanRegulatoryDao.save(membershipPendingPanRegulatory);
				}

				return response.getAuthResponse("SUCCESS", HttpStatus.OK, hm, version);
			} else {
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);

		}
	}

	public ResponseEntity<?> getBenposeHolding(String version, String clientId) {
		try {
			HashMap<String, Object> hm = new HashMap<String, Object>();

			BenposeHoldingInterface result = shareTransferDAO.getBenposeHolding(clientId);
			hm.put("Shares", result.getShares());
			hm.put("totalShares", result.getTotalShares());
			hm.put("PercentageShares", result.getPercentageShares());
			return ((ResponseUtil) response).getAuthResponse("SUCCESS", HttpStatus.OK, result, version);
		} catch (Exception e) {
			log.info("Inside getBenposeHolding method---------------");
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> shareTransferExecutionDate(String version, Long uuid, Long date) {
		// TODO Auto-generated method stub
		try {
			ShareTransferMaster shareTransferMaster = shareTransferDAO.shareTransferExecutionDate(uuid);
			ShareTransferMaster shareTransferMaster1 = shareTransferDAO.getUuidDetails(uuid);
			ShareTransferRoleType shareTransferRoleType1 = shareTransferRoleTypeDao.getAllShareTransferRoleType(uuid);
			
			if(shareTransferMaster!=null) {
				ShareTransferMaster shareTransferMaster2 = (ShareTransferMaster) shareTransferMaster1.clone();
				shareTransferMaster2.setId(null);
				shareTransferMaster2.setStatus(StatusCode.valueOf("SHARE_TRANSFER_COMPLETED").getMsg());

//				SimpleDateFormat dat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
//				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//				Date executionDate = formatter.parse(date);
//				Date executionDate1 = dat.parse(executionDate.toString());
//				System.out.println("executionDate1---------->" + executionDate);
//				System.out.println("executionDate1---------->" + executionDate1);
				Timestamp instant = new Timestamp(date);
//				System.out.println("executionDate1---------->" + instant.toString());
//				Timestamp instant = Timestamp.from(Instant.now());
				shareTransferMaster2.setDateOfExecution(instant);
				
				ShareTransferRoleType shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
				shareTransferRoleType2.setId(null);	
				shareTransferRoleType2.setStatus(StatusCode.valueOf("SHARE_TRANSFER_COMPLETED").getMsg());
				shareTransferRoleType2.setShareTransferId(shareTransferMaster1);
				shareTransferRoleTypeDao.save(shareTransferRoleType2);
				ShareTransferMaster model = shareTransferDAO.save(shareTransferMaster2);
				
				
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, model, version);

			}else {
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> srcApproval(String version, ShareTransferMaster shareTransferMaster) {
		// TODO Auto-generated method stub
//        List<ShareTransferMaster> sr = shareTransferDAO.getUuidsDetails1(shareTransferMaster.getUuid());
		try {
			ShareTransferMaster s1 = new ShareTransferMaster();
//            List<BatchListingAboveTwo> b1 = batchDAO.getPreListingAboveTwo();
//            for (BatchListingAboveTwo b : b1) {
//                if (b.getUuid().equals(shareTransferMaster.getUuid())) {
			Long levelOneShares = shareTransferDAO.getLevelOneShares();
			Long levelTwoShares = shareTransferDAO.getLevelTwoShares();
			ShareTransferMaster shareTransferMaster1 = shareTransferDAO.getUuidDetails(shareTransferMaster.getUuid());
			ShareTransferMaster s = (ShareTransferMaster) shareTransferMaster1.clone();
			ShareTransferRoleType shareTransferRoleType1 = shareTransferRoleTypeDao
					.getAllShareTransferRoleType(shareTransferMaster.getUuid());
			ShareTransferRoleType shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
			// newly only if condition added
			
			log.info("batch id----->"+s.getBatchId());
			
			if (s.getBatchId() != null && s.getBatchId().getApproved().equalsIgnoreCase("Y")) {
				if (Long.valueOf(s.getNoOfShares()) >= levelOneShares) {
					if (Long.valueOf(s.getNoOfShares()) >= levelTwoShares) {
						AboveFivePercent sebi = s.getMetadata().getBuyerDocument().getAboveFivePercent();
						com.nseit.shareholder1.metadatamodel.File approvalLetter = sebi.getApprovalLetter();
						Date sebiDate = sebi.getDate();
						boolean sebiApproval = sebi.getSebi().equalsIgnoreCase("Y");
						if (approvalLetter == null || sebiDate == null || sebiApproval == false) {
							log.info("Sebi approval pending for uuid: " + s.getUuid());
							return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.OK, null, version);
						}
					}
					shareTransferRoleType2.setId(null);
					shareTransferRoleType2.setStatus("STAGE_TWO_SUBMISSION_PENDING");
					shareTransferRoleType2.setCreatedby(jwt.extractUsername());
					shareTransferRoleType2.setChecker(null);
					shareTransferRoleType2.setMaker(null);

					s.setId(null);
					s.getMetadata().getBuyerDocument().setSrcResolutionDraft(shareTransferMaster.getMetadata().getBuyerDocument().getSrcResolutionDraft());
					s.setStatus((StatusCode.valueOf("STAGE_TWO_SUBMISSION_PENDING").getMsg()));
					s1 = shareTransferDAO.save(s);
					shareTransferRoleType2.setShareTransferId(s1);
					shareTransferRoleTypeDao.save(shareTransferRoleType2);

//                }
//                else {
//                    return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
//                }
//            }
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, s1, version);
				} else {
					log.info("SRC approval not required for uuid: " + s.getUuid());
					return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.OK, null, version);
				}
			} else {
				log.info("Batch approval pending for uuid: " + s.getUuid());
				return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(" In Batc Service inside aboveTwoStatusChangebatch-----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> sebiApproval(String version, ShareTransferMaster shareTransferMaster) {
		// TODO Auto-generated method stub
//        List<ShareTransferMaster> sr = shareTransferDAO.getUuidsDetails1(shareTransferMaster.getUuid());
		try {
			ShareTransferMaster s1 = new ShareTransferMaster();
//            List<BatchListingAboveTwo> b1 = batchDAO.getPreListingAboveTwo();
//            for (BatchListingAboveTwo b : b1) {
//                if (b.getUuid().equals(shareTransferMaster.getUuid())) {
			Long levelTwoShares = shareTransferDAO.getLevelTwoShares();
			ShareTransferMaster shareTransferMaster1 = shareTransferDAO.getUuidDetails(shareTransferMaster.getUuid());
			ShareTransferMaster s = (ShareTransferMaster) shareTransferMaster1.clone();
			ShareTransferRoleType shareTransferRoleType1 = shareTransferRoleTypeDao
					.getAllShareTransferRoleType(shareTransferMaster.getUuid());
			ShareTransferRoleType shareTransferRoleType2 = (ShareTransferRoleType) shareTransferRoleType1.clone();
			// newly only if condition added
			// if (s.getBatchId() != null && s.getBatchId().getApproved().equalsIgnoreCase("Y")) {
				if (Long.valueOf(s.getNoOfShares()) >= levelTwoShares) {

					shareTransferRoleType2.setId(null);
					shareTransferRoleType2.setStatus("STAGE_TWO_SUBMISSION_PENDING");
					shareTransferRoleType2.setCreatedby(jwt.extractUsername());
					shareTransferRoleType2.setChecker(null);
					shareTransferRoleType2.setMaker(null);

					s.setId(null);
					s.setStatus((StatusCode.valueOf("STAGE_TWO_SUBMISSION_PENDING").getMsg()));
					s.getMetadata().getBuyerDocument().setAboveFivePercent(shareTransferMaster.getMetadata().getBuyerDocument().getAboveFivePercent());
					s1 = shareTransferDAO.save(s);
					shareTransferRoleType2.setShareTransferId(s1);
					shareTransferRoleTypeDao.save(shareTransferRoleType2);

//                }
//                else {
//                    return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
//                }
//            }
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, s1, version);
				} else {
					log.info("Sebi approval not required for uuid: " + s.getUuid());
					return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.OK, null, version);
				}
			// } else {
			// 	log.info("Batch approval pending for uuid: "+s.getUuid());
			// 	return response.getAuthResponse("WORKFLOW_NOT_ALLOWED", HttpStatus.OK, null, version);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			log.info(" In Batch Service inside aboveTwoStatusChangebatch-----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}
}
