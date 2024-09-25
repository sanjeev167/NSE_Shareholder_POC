package com.nseit.shareholder1.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.keycloak.authorization.client.util.HttpResponseException;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nseit.shareholder1.dao.AuthorisedLetterDao;
import com.nseit.shareholder1.dao.BrokerClientMappingDAO;
import com.nseit.shareholder1.dao.BrokerRegistrationDAO;
import com.nseit.shareholder1.dao.ClientMasterDao;
import com.nseit.shareholder1.dao.LoginOtpDAO;
import com.nseit.shareholder1.emailphone.EmailApplication;
import com.nseit.shareholder1.model.AuthorisedLetter;
import com.nseit.shareholder1.model.BenposeDataMasterModel;
import com.nseit.shareholder1.model.BrokerClientMapping;
import com.nseit.shareholder1.model.BrokerMaster;
//import com.nseit.shareholder1.model.ClientUser;
import com.nseit.shareholder1.modelInterfaces.BrokerDetails;
import com.nseit.shareholder1.model.LoginOTP;
import com.nseit.shareholder1.model.LoginRequest;
import com.nseit.shareholder1.model.MailModel;
import com.nseit.shareholder1.model.UserMaster;
import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.ResponseUtil;
import com.nseit.shareholder1.web.rest.errors.RequiredActionError;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BrokerRegistrationService {

	@Value("${pathnew}")
	private String path;

	@Value("${emailtoggle}")
	private String emailtoggle;

	@Autowired
	EmailApplication emailApplication;

	@Autowired
	ShareHolderService shareHolderService;

	@Autowired
	BrokerRegistrationDAO brokerRegistrationDAO;

	@Autowired
	ClientMasterDao clientMasterDao;

	@Autowired
	BenposeDataMasterService benposeDataMasterService;

	@Autowired
	BrokerClientMappingDAO brokerClientMappingDAO;

	@Autowired
	ResponseUtil response;
	@Autowired
	AuthorisedLetterDao authorisedLetterDao;

	@Autowired
	LoginOtpDAO loginOtpDAO;

	@Autowired
	KeycloakService keycloakService;

	@Autowired
	JwtUtil jwtUtil;

	int brokerCount = 1;
	int fileCount = 0;

	public ResponseEntity<?> brokerRegister(MultipartFile multipartFile, String version, String clientId,
			String brokerName, String brokerEmail, String phone, String authorisedPersonName) {

		// ClientUser clientExist =
		// clientMasterDao.getClientDetails(brokerMaster.getClientId());
		BenposeDataMasterModel clientExist = clientMasterDao.getClientDetails(clientId);
		log.error("clientUser-------------->" + clientExist);

		// int statuscode = shareHolderService.checkUserClientId(version,
		// brokerMaster.getClientId()).getStatusCodeValue();
		List<BrokerMaster> brokerMaster1 = brokerRegistrationDAO.getBrokerDetails(brokerName, brokerEmail, phone);
		List<BrokerClientMapping> brokerMasterList = new ArrayList<BrokerClientMapping>();
		for (BrokerMaster b : brokerMaster1) {
			brokerMasterList = b.getBrokerClientMapping();
		}

		List<String> clientList = new ArrayList<String>();
		for (BrokerClientMapping b : brokerMasterList) {
			clientList.add(b.getBenposeDataMasterModel().getClientId());

		}
		String name = multipartFile.getOriginalFilename();

		/* String path1 = "D://shareHolderTest//" + name; */
		String filePath = path + name;
		// AuthorisedLetter authorisedLetter
		// =authorisedLetterDao.getAuthorisedLetter(filePath);
		// brokerRegistrationDAO.findByBrokerName(brokerMaster.getBrokername());

		try {
			if (clientExist != null) {
				AuthorisedLetter authorisedLetter1 = authorisedLetter(multipartFile);
				// if (brokerMaster1 == null) {
				if (!clientList.contains(clientId)) {

					// String name = multipartFile.getOriginalFilename();
					// String originalName =
					// org.springframework.util.StringUtils.stripFilenameExtension(name);
					// AuthorisedLetter authorisedLetterName =
					// authorisedLetterDao.getAuthorisedLetter(originalName);
					BrokerMaster broker = new BrokerMaster();

					// broker.setAuthorityLetter(authorisedLetter1.getId());
					Timestamp instant = Timestamp.from(Instant.now());
					broker.setModifiedOn(instant);
					broker.setAuthorisedPersonName(authorisedPersonName);
					broker.setAuthorisedPersonEmail(brokerEmail);
					broker.setAuthorisedEntity(brokerName);
					broker.setAuthorisedPersonMobile(phone);
					broker.setOtpVerified("N");
					// String brokerId = "BR" + brokerCount;
					// brokerCount++;
					// broker.setBrokerId(brokerId);
					int emailotp = 123456;
					broker.setPhoneOtp(567890);
					broker.setEmailOtp(emailotp);
					if (emailtoggle.equals("on")) {
						// TODO phone otp random
						Random rand1 = new Random();
						emailotp = rand1.nextInt(999999 + 1 - 100000);

						broker.setEmailOtp(emailotp);
						MailModel mailModel = new MailModel();
						mailModel.setContent(String.valueOf(emailotp));
						mailModel.setSubject("emailotp");
						mailModel.setName(broker.getAuthorisedPersonName());
						mailModel.setTo(broker.getAuthorisedPersonEmail());
						emailApplication.sendEmail(mailModel);
					}
					BrokerMaster b = brokerRegistrationDAO.save(broker);
					broker.setUserName("AR" + String.format("%06d", b.getId()));
					b = brokerRegistrationDAO.save(broker);
					log.error("b---------------> " + b);
					List<BrokerClientMapping> brokerClientMapping = new ArrayList<BrokerClientMapping>();

					BrokerClientMapping brokerClientMapping1 = new BrokerClientMapping();
					brokerClientMapping1.setBrokerId(b.getId());
					brokerClientMapping1.setBenposeDataMasterModel(clientExist);
					// brokerClientMapping1.setClient_Id(clientExist.getClientId());
					// brokerClientMapping1.setApproved("N");

					brokerClientMapping1.setAuthorityLetter(authorisedLetter1);
					brokerClientMapping1.setModifiedOn(instant);
					brokerClientMappingDAO.save(brokerClientMapping1);

					// broker.setBrokerClientMapping(brokerClientMapping);
					// brokerRegistrationDAO.save(broker);

					return response.getAuthResponse("SUCCESS", HttpStatus.OK, broker, version);
				} else {

					// BrokerClientMapping brokerClientMapping1 = new BrokerClientMapping();
					// brokerClientMapping1.setBrokerId(brokerMaster1.getId());
					// brokerClientMapping1.setClient_Id(clientId);
					// brokerClientMappingDAO.save(brokerClientMapping1);
					// return response.getAuthResponse("SUCCESS", HttpStatus.OK, brokerMaster1,
					// version);
					log.error("In brokerRegister BROKER_ALREADY_EXIST------------------------");
					return response.getAuthResponse("BROKER_ALREADY_EXIST", HttpStatus.BAD_REQUEST, authorisedLetter1,
							version);

				}
			} else {
				log.error("In brokerRegister INVALID_CLIENT_ID------------------------");
				return response.getAuthResponse("INVALID_CLIENT_ID", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In brokerRegister INTERNAL_SERVER_ERROR--------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> brokerVerify(String version, BrokerMaster broker) {

		try {
			BrokerMaster brokerMaster2 = brokerRegistrationDAO.isBrokerExist(broker.getUserName(),
					broker.getAuthorisedEntity(), broker.getAuthorisedPersonEmail(),
					broker.getAuthorisedPersonMobile());
			AuthorisedLetter authorisedLetter = authorisedLetterDao
					.getAuthorisedLetterById(broker.getAuthorityLetter());
			BenposeDataMasterModel clientExist = clientMasterDao.getClientDetails(broker.getClientId());
			if (clientExist != null) {
				if (brokerMaster2 != null && authorisedLetter != null) {
					int phoneOtp = 123456;
					int emailOtp = 123456;
					brokerMaster2.setEmailOtp(emailOtp);
					if (emailtoggle.equals("on")) {
						// TODO phone otp random
						Random rand1 = new Random();
						emailOtp = rand1.nextInt(999999 + 1 - 100000);
						brokerMaster2.setEmailOtp(emailOtp);
						MailModel mailModel = new MailModel();
						mailModel.setContent(String.valueOf(emailOtp));
						mailModel.setSubject("emailotp");
						mailModel.setName(broker.getUserName());
						mailModel.setTo(broker.getAuthorisedPersonEmail());
						emailApplication.sendEmail(mailModel);

					}
					Timestamp instant = Timestamp.from(Instant.now());
					brokerMaster2.setModifiedOn(instant);
					brokerMaster2.setPhoneOtp(phoneOtp);

					BrokerClientMapping brokerClientMapping1 = new BrokerClientMapping();
					brokerClientMapping1.setBrokerId(brokerMaster2.getId());
					brokerClientMapping1.setBenposeDataMasterModel(clientExist);
					// brokerClientMapping1.setClient_Id(broker.getClientId());
					brokerClientMapping1.setApproved("N");
					brokerClientMapping1.setAuthorityLetter(authorisedLetter);
					brokerClientMapping1.setModifiedOn(instant);
					brokerClientMappingDAO.save(brokerClientMapping1);
					List<BrokerClientMapping> brokerClientMapping = brokerMaster2.getBrokerClientMapping();
					brokerClientMapping.add(brokerClientMapping1);
					brokerMaster2.setBrokerClientMapping(brokerClientMapping);
					brokerRegistrationDAO.save(brokerMaster2);
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, broker, version);
					// if(brokerMaster2.getPhoneOtp() == broker.getPhoneOtp() &&
					// brokerMaster2.getEmailOtp() == broker.getEmailOtp()) {
					// BrokerClientMapping brokerClientMapping1 = new BrokerClientMapping();
					// brokerClientMapping1.setBrokerId(brokerMaster1.getId());
					// brokerClientMapping1.setClient_Id(clientId);
					// brokerClientMappingDAO.save(brokerClientMapping1);
					// return response.getAuthResponse("SUCCESS", HttpStatus.OK, brokerMaster1,
					// version);
					// }
				} else {
					log.error("In brokerVerify INVALID_BROKER_DETAILS-------------------------------");
					return response.getAuthResponse("INVALID_BROKER_DETAILS", HttpStatus.BAD_REQUEST, null, version);
				}
			} else {
				log.error("In brokerVerify INVALID_CLIENT_ID-------------------------------");
				return response.getAuthResponse("INVALID_CLIENT_ID", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In brokerVerify INTERNAL_SERVER_ERROR-------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> brokerApproval(String version, BrokerMaster broker) {

		try {
			BrokerMaster brokerDetails = brokerRegistrationDAO.checkPendingBroker(broker.getClientId());
			log.error("In brokerApproval User value ---------- " + brokerDetails);

			if (brokerDetails != null) {
				if (brokerDetails.getOtpVerified().equals("Y")) {
					List<BrokerClientMapping> brokerMapping = brokerDetails.getBrokerClientMapping();
					BrokerClientMapping client = brokerMapping.stream()
							.filter(x -> x.getBenposeDataMasterModel().getClientId().equals(broker.getClientId()))
							.findFirst().get();
					client.setApproved(broker.getApproved()); // Validation single char

					// set modified_on
					String[] fullName = broker.getAuthorisedPersonName().split(" ", 2);
					// need to create broker password(Keycloak)
					String firstName = fullName[0];
					String lastName = (fullName.length > 1) ? fullName[1] : "";
					String password = shareHolderService.getpassword();
					try {
						String keycloakUserId = keycloakService.createAccount(broker.getUserName(), password, firstName,
								lastName);
						brokerDetails.setKeycloakId(keycloakUserId);
						log.error("id-------------->" + keycloakUserId);
					} catch (Exception e) {
						return response.getAuthResponse("IDAM_USER_NOT_CREATED", HttpStatus.BAD_REQUEST, null, version);
					}
					Timestamp instant = Timestamp.from(Instant.now());
					client.setModifiedOn(instant);
					brokerClientMappingDAO.save(client);
					HashMap<String, Object> hm = new HashMap<String, Object>();
					hm.put("username", brokerDetails.getUserName());
					hm.put("password", password);

					return response.getAuthResponse("SUCCESS", HttpStatus.OK, hm, version);
				} else {
					log.error("In brokerApproval OTP_NOT_VERIFIED----------------------------");
					return response.getAuthResponse("OTP_NOT_VERIFIED", HttpStatus.BAD_REQUEST, false, version);
				}
			} else {
				log.error("In brokerApproval INVALID_BROKER_DETAILS----------------------------");
				return response.getAuthResponse("INVALID_BROKER_DETAILS", HttpStatus.BAD_REQUEST, null, version);
			}

		} catch (NoSuchElementException ne) {
			log.error("In brokerApproval INVALID_BROKER_DETAILS----------------------");
			ne.printStackTrace();
			return response.getAuthResponse("INVALID_BROKER_DETAILS", HttpStatus.BAD_REQUEST, null, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("InbrokerApproval INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> brokerOtpVerify(String version, BrokerMaster broker) {
		try {
			System.out.println("broker----------------->" + broker);
			String name = broker.getUserName();
			BrokerMaster brokerMaster2 = brokerRegistrationDAO.getBrokerOtp(broker.getUserName());
			System.out.println("brokerMaster2-------------->" + brokerMaster2);

			if (brokerMaster2.getPhoneOtp().equals(broker.getPhoneOtp())
					&& brokerMaster2.getEmailOtp().equals(broker.getEmailOtp())) {
				// BrokerClientMapping brokerClientMapping1 = new BrokerClientMapping();
				// brokerClientMapping1.setBrokerId(brokerMaster2.getId());
				// brokerClientMapping1.setClient_Id(broker.getClientId());

				// brokerClientMappingDAO.save(brokerClientMapping1);
				brokerMaster2.setPhoneOtp(null);
				brokerMaster2.setEmailOtp(null);
				brokerMaster2.setOtpVerified("Y");
				Timestamp instant = Timestamp.from(Instant.now());
				brokerMaster2.setModifiedOn(instant);
				// List<BrokerClientMapping> brokerMapping = new ArrayList();
				// brokerMapping.add(brokerClientMapping1);
				// brokerMaster2.setBrokerClientMapping(brokerMapping);
				brokerRegistrationDAO.save(brokerMaster2);
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, brokerMaster2, version);
			} else {
				log.error("In brokerOtpVerify OTP_VERIFICATION_FAILED-------------------------------");
				return response.getAuthResponse("OTP_VERIFICATION_FAILED", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In brokerOtpVerify INTERNAL_SERVER_ERROR-------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public AuthorisedLetter authorisedLetter(MultipartFile multiPartFile) throws IOException {

		if (multiPartFile != null) {
			String name = multiPartFile.getOriginalFilename();
			String extension = name.substring(name.lastIndexOf("."));
			/* String path1 = "D://shareHolderTest//" + name; */
			String folder = path + File.separator + "authorisedLetter";
			String path1 = folder + File.separator + name;
			log.error("name-------->" + name);
			String originalName = org.springframework.util.StringUtils.stripFilenameExtension(name);
			log.error("orinalName-------->" + originalName);
			// NEED TO APPEND SOMETHING WITH EXISTING FILE NAME
			File f = new File(folder);
			Boolean existFolder = f.mkdir();
			Boolean existFile = new File(path1).exists();
			if (existFile == true) {
				fileCount = benposeDataMasterService.countSameFiles(originalName, folder);
				String newPath = folder + File.separator + originalName + fileCount + extension;
				File newFile = new File(newPath);
				FileOutputStream newFo = new FileOutputStream(newFile);
				newFo.write(multiPartFile.getBytes());
				log.error("fo---->" + newFo);
				AuthorisedLetter authorisedLetter = new AuthorisedLetter();
				authorisedLetter.setFileName(originalName + fileCount + extension);
				authorisedLetter.setFilePath(newPath);
				// authorisedLetter.setCreatedby(jwt.extractUsername());
				return authorisedLetterDao.save(authorisedLetter);
			}
			File file = new File(path1);

			log.error("path--------->" + path1);
			FileOutputStream fo = new FileOutputStream(file);
			fo.write(multiPartFile.getBytes());
			log.error("fo---->" + fo);
			AuthorisedLetter authorisedLetter = new AuthorisedLetter();
			authorisedLetter.setFileName(name);
			authorisedLetter.setFilePath(path1);
			// authorisedLetter.setCreatedby(jwt.extractUsername());
			return authorisedLetterDao.save(authorisedLetter);
		} else {
			return null;
		}

	}

	public ResponseEntity<?> brokerLogin(String version, LoginRequest loginRequest) {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		log.error("loginRequest-------------->" + loginRequest);
		// BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		BrokerMaster brokerDetails = brokerRegistrationDAO.checkBroker(loginRequest.getUsername());
		log.error("User value ---------- " + brokerDetails);

		try {
			if (brokerDetails != null) {
				if (brokerDetails.getOtpVerified().equals("Y")) {
					log.error("In brokerLogin inside if isUserExist " + brokerDetails);
					/*
					 * Random rand1 = new Random(); int Otp=rand1.nextInt(10000);
					 * 
					 * log.error("otp---->"+Otp);
					 * 
					 */

					// String token = keycloakService.generateToken(loginRequest.getUsername(),
					// loginRequest.getPassword());
					AccessTokenResponse token = keycloakService.generateTokenObject(loginRequest.getUsername(),
							loginRequest.getPassword());
					log.error("token value------------>" + token);
					int otp = 123456;
					if (emailtoggle.equals("on")) {
						// TODO phone otp random
						Random rand1 = new Random();
						otp = rand1.nextInt(999999 + 1 - 100000);
						MailModel mailModel = new MailModel();
						mailModel.setContent(String.valueOf(otp));
						mailModel.setSubject("emailotp");
						mailModel.setName(brokerDetails.getUserName());
						mailModel.setTo(brokerDetails.getAuthorisedPersonEmail());
						emailApplication.sendEmail(mailModel);

					}
					LoginOTP loginOTP = new LoginOTP();
					loginOTP.setOtp(otp);
					loginOTP.setUserName(loginRequest.getUsername());
					loginOTP.setToken(token.getToken());
					loginOTP.setRefreshToken(token.getRefreshToken());
					log.error("loginOTP---------------> " + loginOTP);
					loginOtpDAO.save(loginOTP);
					hm.put("id", loginOTP.getId());
					hm.put("userName", loginOTP.getUserName());
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, hm, version);
					// return createAuthenticationToken(userMaster, version);
				} else {
					log.error("In brokerLogin OTP_NOT_VERIFIED----------------------------");
					return response.getAuthResponse("OTP_NOT_VERIFIED", HttpStatus.BAD_REQUEST, false, version);
				}
			} else {
				log.error("In brokerLogin INVALID_CREDENTIAL----------------------------");
				return response.getAuthResponse("INVALID_CREDENTIAL", HttpStatus.BAD_REQUEST, false, version);
			}
		} catch (RequiredActionError re) {
			log.error("In brokerLogin RequiredActionError----------------------------");
			return response.getAuthResponse(re.getMessage(), HttpStatus.BAD_REQUEST, false, version);
		} catch (HttpResponseException err) {
			err.printStackTrace();
			log.error("In brokerLogin HttpResponseException----------------------------");
			if (err.toString().contains("Account is not fully set up")) {
				return response.getAuthResponse("UPDATE_PASSWORD", HttpStatus.BAD_REQUEST, false, version);

			} else if (err.toString().contains("Invalid user credentials")) {
				return response.getAuthResponse("INVALID_PASSWORD", HttpStatus.FORBIDDEN, null, version);
			} else {
				log.error("In brokerLogin INTERNAL_SERVER_ERROR----------------------------");
				return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null,
						version);
			}

		} catch (Exception e) {
			log.error("In brokerLogin Exception INVALID_CREDENTIAL----------------------------");
			return response.getAuthResponse("INVALID_CREDENTIAL", HttpStatus.BAD_REQUEST, false, version);
		}
	}

	public ResponseEntity<?> brokerLoginOtpVerified(String version, LoginOTP loginOTP) {
		try {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			LoginOTP otpExist = loginOtpDAO.getByUserName(loginOTP.getUserName());
			BrokerMaster broker = brokerRegistrationDAO.checkBroker(loginOTP.getUserName());
			log.error("otpExist---------------> " + otpExist);
			if (loginOTP.getOtp().equals(otpExist.getOtp())) {
				otpExist.setOtp(0);
				loginOtpDAO.save(otpExist);
				hm.put("token", otpExist.getToken());
				hm.put("refreshToken", otpExist.getRefreshToken());
				hm.put("userName", otpExist.getUserName());
				hm.put("fullName", broker.getAuthorisedPersonName());
				// String token = otpExist.getToken();
				// log.error("value of token generated-------------->" + token);
				// return createAuthenticationToken(userMaster, version);
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, hm, version);
			} else {
				log.error("In brokerLoginOtpVerified INVALID_LOGIN_OTP----------------------");
				return response.getAuthResponse("INVALID_LOGIN_OTP", HttpStatus.FORBIDDEN, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In brokerLoginOtpVerified LOGIN_OTP_TIMEOUT----------------------");
			return response.getAuthResponse("LOGIN_OTP_TIMEOUT", HttpStatus.FORBIDDEN, null, version);
		}
	}

	public ResponseEntity<?> getAllBrokerDetails(String version) {
		// TODO Auto-generated method stub
		String username = jwtUtil.extractUsername();
		log.error("username --------------->" + username);

		try {
			List<BrokerDetails> detials = brokerRegistrationDAO.getAllBrokerDetials(username);
			if (detials.size() != 0) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, detials, version);
			} else {
				log.error("In getAllBrokerDetails NO_DATA----------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, detials, version);
			}

		} catch (Exception e) {
			log.error("In getAllBrokerDetails INTERNAL_SERVER_ERROR--------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> brokerDownloadFile(String version, Long id) {
		try {
			log.error("id------->" + id);
			AuthorisedLetter authorisedLetter = authorisedLetterDao.getAuthorisedLetterById(id);
			String filePath = authorisedLetter.getFilePath();
			String fileName = authorisedLetter.getFileName();
			URI uri = new URI(filePath);
			log.error("URI------------>" + uri);
			File file = new File(filePath);
			// URL url = uri.toURL();
			// URL url=new
			// log.error("URL------------>" + url);
			Path path = Paths.get(file.getAbsolutePath());
			ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
			headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
					"Content-Disposition");

			return ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
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
			log.error("In brokerDownloadFile DOWNLOAD_FAIL-------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.FORBIDDEN, null, version);
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

	// public ResponseEntity<?> getbrokerDetails(String version, String clientId) {
	// try {
	// Object
	// brokerDetails=brokerRegistrationDAO.getBrokerDetailsByClientID(clientId);
	// return response.getAuthResponse("SUCCESS", HttpStatus.OK, brokerDetails,
	// version);}
	// catch(Exception e) {
	// e.printStackTrace();
	// return response.getAuthResponse("INTERNAL_SERVER_ERROR",
	// HttpStatus.INTERNAL_SERVER_ERROR, null, version);
	// }
	// }
}
