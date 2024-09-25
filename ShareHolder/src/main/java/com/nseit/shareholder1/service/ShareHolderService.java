package com.nseit.shareholder1.service;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.dao.BrokerRegistrationDAO;
import com.nseit.shareholder1.dao.ClientMasterDao;
import com.nseit.shareholder1.dao.LoginOtpDAO;
import com.nseit.shareholder1.dao.OTPRegistrationVerificationDAO;
import com.nseit.shareholder1.dao.ShareHolderDao;
import com.nseit.shareholder1.dao.UserDao;
import com.nseit.shareholder1.dao.UserMasterDao;
import com.nseit.shareholder1.dao.UserTokenLogDao;
import com.nseit.shareholder1.dao.VerifyQuestionDao;
import com.nseit.shareholder1.emailphone.EmailApplication;
import com.nseit.shareholder1.model.BenposeDataMasterModel;
import com.nseit.shareholder1.model.BrokerMaster;
import com.nseit.shareholder1.model.ChangePassword;
//import com.nseit.shareholder1.model.ClientUser;
import com.nseit.shareholder1.model.LoginOTP;
import com.nseit.shareholder1.model.LoginRequest;
import com.nseit.shareholder1.model.MailModel;
import com.nseit.shareholder1.model.OTPRegistrationVerification;
import com.nseit.shareholder1.model.ResetPassword;
import com.nseit.shareholder1.model.User;
import com.nseit.shareholder1.model.UserMaster;
import com.nseit.shareholder1.model.UserToken;
import com.nseit.shareholder1.model.VerifyQuestion;
import com.nseit.shareholder1.util.JwtUtil;
//import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.KeycloakClientAuthExample;
import com.nseit.shareholder1.util.ResponseUtil;
import com.nseit.shareholder1.web.rest.errors.CheckUserSecurityException;
import com.nseit.shareholder1.web.rest.errors.ClientNotExistException;
import com.nseit.shareholder1.web.rest.errors.OTPVerifyException;
import com.nseit.shareholder1.web.rest.errors.PhoneAndEmailAlreadyExist;
import com.nseit.shareholder1.web.rest.errors.RequiredActionError;
import com.nseit.shareholder1.web.rest.errors.UsernameAlreadyUsedException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShareHolderService {

	List<String> questionSetList1 = new ArrayList<String>() {
		{
			add("PAN");
			add("PINCODE");
			add("SHARES");
		}
	};

	List<String> questionSetList2 = new ArrayList<String>() {
		{
			add("ACCOUNT_NUM");
			add("BANK_NAME");
//			add("IFSC");
//			add("NOMINEE");
		}
	};
	
	List<String> questionSetList3 = new ArrayList<String>() {
		{
			add("FATHER_HUSBAND_NAME");
		}
	};
	
	@Autowired
	EmailApplication emailApplication;
	
	@Autowired
	ShareHolderDao shareHolderDao;

	@Autowired
	ResponseUtil response;

//	@Autowired
//	private JwtUtil jwtTokenUtil;

	@Autowired
	UserMasterDao userMasterDao;

	@Autowired
	UserTokenLogDao userTokenLogDao;

	@Autowired
	UserDao userDao;

	@Autowired
	ClientMasterDao clientMasterDao;
	@Autowired
	VerifyQuestionDao verifyQuestionDao;

	@Autowired
	OTPRegistrationVerificationDAO otpRegistrationVerificationDAO;

	@Autowired
	LoginOtpDAO loginOtpDAO;

	@Autowired
	KeycloakService keycloakService;

	@Autowired
	KeycloakClientAuthExample keycloakClientAuthExample;

	@Value("${keycloak.realm}")
	private String REALM;

	@Value("${emailtoggle}")
	private String emailtoggle;
	
	@Autowired
	BrokerRegistrationDAO brokerRegistrationDAO;
	
	@Autowired
	JwtUtil jwt;

	public ResponseEntity<?> login(LoginRequest loginRequest, String version) throws NoSuchAlgorithmException {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		log.error("loginRequest-------------->" + loginRequest);
//		BCryptPasswordEncoder b = new BCryptPasswordEncoder();

//		User user = shareHolderDao.userExist(loginRequest.getUsername());
		UserMaster user = userMasterDao.loadUserByUserName(loginRequest.getUsername());
//		log.error("User value ---------- " + user);
//		UserMaster userMaster = userMasterDao.getById(user.getUserMaster().getId());
//
//		log.error("UserMaster value ---------- " + userMaster);
		try {
			if (user != null) {
				log.error("In login inside if isUserExist " + user);
				/*
				 * Random rand1 = new Random(); int Otp=rand1.nextInt(10000);
				 * 
				 * log.error("otp---->"+Otp);
				 * 
				 */

				AccessTokenResponse token = keycloakService.generateTokenObject(loginRequest.getUsername(),
						loginRequest.getPassword());
				// String token = keycloakService.generateToken(loginRequest.getUsername(),
				// loginRequest.getPassword());
				log.error("In login token value------------>" + token);
				int otp = 123456;
				log.error("In login emailtoggle--------------->"+emailtoggle);
				if(emailtoggle.equals("on")) {
//					TODO phone otp random
					Random rand1 = new Random();
					otp = rand1.nextInt(999999+1-100000);
					MailModel mailModel=new MailModel();
					mailModel.setContent(String.valueOf(otp));
					mailModel.setSubject("emailotp");
					mailModel.setName(user.getFullName());
					mailModel.setTo(user.getEmail());
					emailApplication.sendEmail(mailModel);
					
				}
				LoginOTP loginOTP = new LoginOTP();
				loginOTP.setOtp(otp);
				loginOTP.setUserName(loginRequest.getUsername());
				loginOTP.setToken(token.getToken());
				loginOTP.setRefreshToken(token.getRefreshToken());
				log.error("In login loginOTP---------------> " + loginOTP);
				loginOtpDAO.save(loginOTP);
				hm.put("id", loginOTP.getId());
				hm.put("userName", loginOTP.getUserName());
				hm.put("fullName", user.getFullName());
				hm.put("refereshToken", token.getRefreshToken());
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, hm, version);
				// return createAuthenticationToken(userMaster, version);
			} else {
				log.error("In login INVALID_CREDENTIAL-----------------------------------");
				return response.getAuthResponse("INVALID_CREDENTIAL", HttpStatus.BAD_REQUEST, false, version);
			}
		} catch (RequiredActionError re) {

			re.printStackTrace();
			log.error("In login In RequiredActionError--------------------------");
			return response.getAuthResponse(re.getMessage(), HttpStatus.BAD_REQUEST, false, version);
		} catch (HttpResponseException err) {
			err.printStackTrace();
			if (err.toString().contains("Account is not fully set up")) {
				log.error("In login In HttpResponseException UPDATE_PASSWORD--------");
				return response.getAuthResponse("UPDATE_PASSWORD", HttpStatus.BAD_REQUEST, false, version);

			} else if (err.toString().contains("Invalid user credentials")) {
				log.error("In login In HttpResponseException INVALID_PASSWORD--------");
				return response.getAuthResponse("INVALID_PASSWORD", HttpStatus.BAD_REQUEST, null,
						version);
			} else {
				log.error("In login In Exception INTERNAL_SERVER_ERROR--------");
				return response.getAuthResponse("INTERNAL_SERVER_ERROR",
						HttpStatus.INTERNAL_SERVER_ERROR, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("In login In Exception INVALID_CREDENTIAL------------------");
			return response.getAuthResponse("INVALID_CREDENTIAL", HttpStatus.BAD_REQUEST, false, version);
		}
	}

	public ResponseEntity<?> checkLoginOtp(LoginOTP loginOTP, String version) {
		try {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			LoginOTP otpExist = loginOtpDAO.getByUserName(loginOTP.getUserName());
			UserMaster user = userMasterDao.loadUserByUserName(loginOTP.getUserName());
			// User user = shareHolderDao.userExist(loginOTP.getUserName());
			// UserMaster userMaster = userMasterDao.getById(user.getUserMaster().getId());
			log.error("In checkLoginOtp otpExist---------------> " + otpExist);
			if (loginOTP.getOtp().equals(otpExist.getOtp())) {
				hm.put("token", otpExist.getToken());
				hm.put("refreshToken", otpExist.getRefreshToken());
				hm.put("userName", otpExist.getUserName());
				hm.put("fullName", user.getFullName());
				// log.error("value of token generated-------------->" + token);
				// return createAuthenticationToken(userMaster, version);
				otpExist.setOtp(0);
				loginOtpDAO.save(otpExist);
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, hm, version);
			} else {
				log.error("In checkLoginOtp INVALID_LOGIN_OTP----------------------------");
				return response.getAuthResponse("INVALID_LOGIN_OTP", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In checkLoginOtp In Exception LOGIN_OTP_TIMEOUT------------------");
			return response.getAuthResponse("LOGIN_OTP_TIMEOUT", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	// public ResponseEntity<?> createAuthenticationToken(UserMaster user, String
	// version) {
	// final String token = jwtTokenUtil.generateToken(user);
	// String userName = user.getUsername();
	// HashMap<String, Object> userdetails = new HashMap<String, Object>();
	// userdetails.put("token", token);
	// log.error("jwt : " + token);
	// ResponseEntity authenticationResponse = response.getAuthResponse("SUCCESS",
	// HttpStatus.OK, userdetails,
	// version);
	// boolean value = isUserExistInTokenDb(userName);
	// log.error("value--------------- " + value);
	//
	// Timestamp instant = Timestamp.from(Instant.now());
	// UserToken userToken = new UserToken();
	//// userToken.setCreatedOn(instant);
	//// userToken.setModifiedOn(instant);
	// userToken.setUserId(user.getId());
	// userToken.setToken(token);
	// userTokenLogDao.save(userToken);
	// return authenticationResponse;
	// }

	private boolean isUserExistInTokenDb(String userName) {
		return userTokenLogDao.isUserExist(userName).size() >= 0 ? true : false;
	}

	public UserMaster loadUserByUserName(String userName) {
		return userMasterDao.loadUserByUserName(userName);
	}

	public int isUserTokenExist(Long userId, String token) {
		return userTokenLogDao.isUserTokenExist(userId, token);
	}

	public ResponseEntity<?> logout(String version, String token) {
		// TODO Auto-generated method stub
		// int count;
		// Timestamp instant = Timestamp.from(Instant.now());

		try {
			keycloakService.logout(token);
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, version);
			// log.error("Inside Shareholder service logout method token: " + token);
			// String username = jwtTokenUtil.extractUsername(token);
			// log.error("Value of Username: " + username);
			// count = userTokenLogDao.logout("", username, token);
			// log.error("Value of count: " + count);
			// if (count > 0) {
			// return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, version);
			// } else {
			// return response.getAuthResponse("FORBIDDEN", HttpStatus.FORBIDDEN, null,
			// version);
			// }
		} catch (Exception e) {
			e.printStackTrace();

			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	@Value("${spring.ldap.url}")
	private String ldapUrl;

	@Value("${spring.ldap.base}")
	private String ldapBase;

	private boolean authenticateActiveDirectory(String userName, String password) {
		try {
			Hashtable<String, String> environment = new Hashtable<String, String>();
			environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			environment.put(Context.PROVIDER_URL, ldapUrl);
			environment.put(Context.SECURITY_AUTHENTICATION, "simple");
			environment.put(Context.SECURITY_PRINCIPAL, userName);
			environment.put(Context.SECURITY_CREDENTIALS, password);
			DirContext context = new InitialDirContext(environment);
			if (context != null)
				return true;
			else
				return false;
		} catch (AuthenticationException e) {
			log.error("___________________ LDAP AuthenticationException _________________", e);
			return false;
		} catch (NamingException e) {
			log.error("___________________ LDAP NamingException _________________", e);
			return false;
		}

	}

	public ResponseEntity<?> register(String version, String clientId, OTPRegistrationVerification userMaster) {

		try {
			BenposeDataMasterModel clientExist = clientMasterDao.getClientDetails(clientId);
			// UserMaster isUserExist =
			// userMasterDao.isUserExistUserMaster(clientExist.getPan());
			OTPRegistrationVerification otpRegistration = otpRegistrationVerificationDAO.getByClientId(clientId);
			ResponseEntity otpVerifyRegisterResult = otpVerify(version, userMaster);
			int statusCode = otpVerifyRegisterResult.getStatusCodeValue();
			log.error("statusCode----------------> " + statusCode);
			HashMap<String, Object> userdetails = new HashMap<String, Object>();
			userdetails.put("userName", clientId);

			String password = getpassword();

			userdetails.put("password", password);
			if (clientExist != null) {
				UserMaster isUserExist = userMasterDao.loadUserByUserName(clientExist.getClientId());
				String phone = otpRegistration.getPhone();
				String email = otpRegistration.getEmail();
				boolean phoneExist = userMaster.getPhone().equalsIgnoreCase(phone);
				boolean emailExist = userMaster.getEmail().equalsIgnoreCase(email);
				if (statusCode == HttpStatus.OK.value()) {
					if (isUserExist == null) {
						if (phoneExist == true && emailExist == true) {
							String firstName="";
							String lastName="";
							String[] username = clientExist.getShareholdername().split(" ");
							if(username.length==2) {
							firstName = username[0];
							lastName = username[1];}
							else {
								firstName = username[0];
								
								
							}
							log.error("In register fullname---------------->" + firstName + " " + lastName);
							UserMaster userMaster1 = new UserMaster();
							try {
								String keycloakUserId = keycloakService.createAccount(clientId, password, firstName,
										lastName);
								userMaster1.setKeycloakId(keycloakUserId);
							} catch (Exception e) {
								e.printStackTrace();
								log.error("In register in Exception IDAM_USER_NOT_CREATED------------------");
								return response.getAuthResponse("IDAM_USER_NOT_CREATED", HttpStatus.BAD_REQUEST, null,
										version);
							}
							// UserRepresentation
							// userResources=keycloakClientAuthExample.newKeycloakWithClientCredentials().realm(REALM).users().search(clientId).get(0);
							// userResources.setFirstName(firstName);
							// userResources.setLastName(lastName);

							userMaster1.setUsername(clientExist.getClientId());
							userMaster1.setEmail(userMaster.getEmail());
							// userMaster1.setFirstName(userMaster.getFirstName());
							userMaster1.setFullName(clientExist.getShareholdername());
							// userMaster1.setPan(userMaster.getPan());
							userMaster1.setPan(clientExist.getPan());
							userMaster1.setPhone(userMaster.getPhone());
							
							userMaster1 = userMasterDao.save(userMaster1);
							// User user1 = new User();
							// user1.setUserMaster(userMaster1);
							// BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
							// String hashPassword = bCryptPasswordEncoder.encode(password);
							// user1.setPassword(hashPassword);
							// user1 = userDao.save(user1);
							//
							// boolean password1 = bCryptPasswordEncoder.matches(password,
							// user1.getPassword());
							// log.error("password 1 boolean value: " + password1);
							// log.error("value of hash password: " + hashPassword);
							return response.getAuthResponse("SUCCESS", HttpStatus.OK, userdetails, version);

						} else {
							log.error("In register Error here------------------------->");
							log.error("In register in Exception IDAM_USER_NOT_CREATED------------------");
							return response.getAuthResponse("PHONE_EMAIL_INCORRECT", HttpStatus.BAD_REQUEST, null,
									version);
						}
					} else {
						log.error("In register in ALREADY_USER_EXIST-------------------------------------");
						return response.getAuthResponse("ALREADY_USER_EXIST", HttpStatus.BAD_REQUEST, null, version);
					}
				} else {
					log.error("In register in OTP_VERIFICATION_FAILED----------------------------------");
					return response.getAuthResponse("OTP_VERIFICATION_FAILED", HttpStatus.BAD_REQUEST, null, version);
				}
			} else {
				log.error("In register in INVALID_CLIENT_ID------------------------------");
				return response.getAuthResponse("INVALID_CLIENT_ID", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (ClientNotExistException clientNotExistException) {
			return response.getAuthResponse("INVALID_CLIENT_ID", HttpStatus.BAD_REQUEST, null, version);
		} catch (UsernameAlreadyUsedException usernameAlreadyUsedException) {
			return response.getAuthResponse("ALREADY_USER_EXIST", HttpStatus.BAD_REQUEST, null, version);
		} catch (PhoneAndEmailAlreadyExist phoneAndEmailAlreadyExist) {
			phoneAndEmailAlreadyExist.printStackTrace();
			return response.getAuthResponse("PHONE_EMAIL_INCORRECT", HttpStatus.BAD_REQUEST, null, version);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In register in Exception INTERNAL_SERVER_ERROR------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public String getpassword() {
		String password = RandomStringUtils.randomAlphabetic(5);
		System.out.println("Value of password: " + password);
		return password;
	}

	public ResponseEntity<?> checkUserClientId(String version, String clientId) {
		// TODO Auto-generated method stub
		try {
			log.error("In checkUserClientId clientId---------> " + clientId);
			BenposeDataMasterModel clientUser = clientMasterDao.getClientDetails(clientId);
			log.error("In checkUserClientId clientUser---------> " + clientUser);
			UserMaster isUserExist = userMasterDao.loadUserByUserName(clientId);
			if (isUserExist == null) {
				if (clientUser != null) {
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, clientUser, version);
				} else {
					log.error("In checkUserClientId INVALID_CLIENT_ID--------------------------------------");
					return response.getAuthResponse("INVALID_CLIENT_ID", HttpStatus.BAD_REQUEST, null, version);
				}
			} else {
				log.error("In checkUserClientId ALREADY_USER_EXIST----------------------------------------");
				return response.getAuthResponse("ALREADY_USER_EXIST", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (ClientNotExistException clientNotExistException) {
			log.error("In checkUserClientId in ClientNotExistException INVALID_CLIENT_ID------------------");
			return response.getAuthResponse("INVALID_CLIENT_ID", HttpStatus.BAD_REQUEST, null, version);

		}

	}

	public ResponseEntity<?> checkUserClientEmailAndPhone(String version, BenposeDataMasterModel client) {

		try {
			BenposeDataMasterModel clientUser = clientMasterDao.getClientDetails(client.getClientId());
			boolean isEmailExist = clientUser.getEmail().equalsIgnoreCase(client.getEmail());
			boolean isPhoneExist = clientUser.getPhone().equalsIgnoreCase(client.getPhone());
			UserMaster isUserExist = userMasterDao.loadUserByUserName(client.getClientId());
			if (isUserExist == null) {
				if (isEmailExist == true && isPhoneExist == true) {
					/*
					 * Random rand1 = new Random(); int emailRandomOtp=rand1.nextInt(10000); int
					 * phoneRandomOtp=rand1.nextInt(10000);
					 * log.error("email otp---->"+emailRandomOtp);
					 * log.error("phone otp---->"+phoneRandomOtp);
					 */

					int emailotp = 123456;
					int phoneotp = 567890;
					if(emailtoggle.equals("on")) {
//						TODO phone otp random
						Random rand1 = new Random();
						emailotp = rand1.nextInt(999999+1-100000);
						
						MailModel mailModel=new MailModel();
						mailModel.setContent(String.valueOf(emailotp));
						mailModel.setSubject("emailotp");
         				mailModel.setTo(clientUser.getEmail());
         				mailModel.setName(clientUser.getShareholdername());
						emailApplication.sendEmail(mailModel);
				
						
					}
					OTPRegistrationVerification otpRegistrationVerification = new OTPRegistrationVerification();
					otpRegistrationVerification.setClientId(client.getClientId());
					otpRegistrationVerification.setEmail(client.getEmail());
					otpRegistrationVerification.setPhone(client.getPhone());
					otpRegistrationVerification.setEmailOtp(emailotp);
					otpRegistrationVerification.setPhoneOtp(phoneotp);
					HashMap<String, Object> otpValues = new HashMap<String, Object>();
					otpValues.put("CLIENT_ID", otpRegistrationVerification.getClientId());
					otpValues.put("EMAIL", otpRegistrationVerification.getEmail());
					otpValues.put("PHONE", otpRegistrationVerification.getPhone());
					otpRegistrationVerificationDAO.save(otpRegistrationVerification);
					log.error("In checkUserClientEmailAndPhone in otpRegistrationVerification values------->" + otpRegistrationVerification);

					// need not to send otpRegistrationVerification object
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, otpValues, version);
				}

				else {
					log.error("In checkUserClientEmailAndPhone in PHONE_EMAIL_INCORRECT----------------------------------------");
					return response.getAuthResponse("PHONE_EMAIL_INCORRECT", HttpStatus.BAD_REQUEST, null, version);
				}
			} else {
				log.error("In checkUserClientEmailAndPhone in ALREADY_USER_EXIST----------------------------------------");
				return response.getAuthResponse("ALREADY_USER_EXIST", HttpStatus.BAD_REQUEST, null, version);
			}

		} catch (Exception e) {
//			e.printStackTrace();
			log.error("In checkUserClientEmailAndPhone in Exception INTERNAL_SERVER_ERROR----------------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> otpVerify(String version, OTPRegistrationVerification otpRegistrationVerification) {
		try {
			OTPRegistrationVerification otpExist = otpRegistrationVerificationDAO
					.getByClientId(otpRegistrationVerification.getClientId());

			log.error("otpExist------------> " + otpExist);
			if (otpExist != null) {

				if (otpExist.getEmailOtp() == otpRegistrationVerification.getEmailOtp()
						&& otpExist.getPhoneOtp() == otpRegistrationVerification.getPhoneOtp()) {
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, otpRegistrationVerification, version);
				} else {
					log.error("In otpVerify in OTP_VERIFICATION_FAILED ----------------------------------------");
					return response.getAuthResponse("OTP_VERIFICATION_FAILED", HttpStatus.BAD_REQUEST, null, version);
				}
			} else {
				log.error("In otpVerify in OTP_VERIFICATION_TIMEOUT ----------------------------------------");
				return response.getAuthResponse("OTP_VERIFICATION_TIMEOUT", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (OTPVerifyException otpVerifyException) {
			log.error("In otpVerify in OTPVerifyException INTERNAL_SERVER_ERROR----------------------------------------");
			return response.getAuthResponse("OTP_VERIFICATION_FAILED", HttpStatus.BAD_REQUEST, null, version);
		}

	}

	public ResponseEntity<?> checkUserSecurityQuestion(String version, BenposeDataMasterModel client) {
		try {
			BenposeDataMasterModel clientUser = clientMasterDao.getClientDetails(client.getClientId());

			VerifyQuestion verifyQuestion = verifyQuestionDao.getByClientId(client.getClientId());

			List<String> allValues = new ArrayList<String>();
			// need to check individuals with group1 and group 2 values
			if (verifyQuestion != null) {
//				String[] group1 = verifyQuestion.getQuestionGroup1().split(",");
				String[] group1 = verifyQuestion.getQuestionGroup1().split(",");
				log.error("In checkUserSecurityQuestion group1-------------> " + group1);
//				String group2 = verifyQuestion.getQuestionGroup2();
				String group2 = verifyQuestion.getQuestionGroup1();
				log.error("In checkUserSecurityQuestion group2-------------> " + group2);
				
				//Final Questions
				String group1Question1 = group1[0];
				log.error("In checkUserSecurityQuestion group1Question1-------------> " + group1Question1);
				String group1Question2 = group1[1];
				log.error("In checkUserSecurityQuestion group1Question2-------------> " + group1Question2);
				
				String group1Question3 = group1[2];
				log.error("In checkUserSecurityQuestion group1Question2-------------> " + group1Question3);
				/* if (verifyQuestion != null) { */
				boolean pan = clientUser.getPan().equalsIgnoreCase(client.getPan());
				if (pan == true
						&& (group1Question1.equalsIgnoreCase("PAN") || group1Question2.equalsIgnoreCase("PAN")|| group1Question3.equalsIgnoreCase("PAN"))) {
					String s1 = client.getPan();
					allValues.add(s1);
				}
				boolean accountNum = clientUser.getAccountNum().equalsIgnoreCase(client.getAccountNum());
				if (accountNum == true &&
//						group2.equalsIgnoreCase("ACCOUNT_NUM")) {
						(group1Question1.equalsIgnoreCase("ACCOUNT_NUM") || group1Question2.equalsIgnoreCase("ACCOUNT_NUM")|| group1Question3.equalsIgnoreCase("ACCOUNT_NUM"))){
					String s2 = client.getAccountNum();
					allValues.add(s2);
				}
				boolean bankName = clientUser.getBankName().equalsIgnoreCase(client.getBankName());
				if (bankName == true && 
//						group2.equalsIgnoreCase("BANK_NAME")) {
						(group1Question1.equalsIgnoreCase("BANK_NAME") || group1Question2.equalsIgnoreCase("BANK_NAME")|| group1Question3.equalsIgnoreCase("BANK_NAME"))){
					String s3 = client.getBankName();
					allValues.add(s3);
				}
//				boolean ifsc = clientUser.getIfsc().equalsIgnoreCase(client.getIfsc());
//				if (ifsc == true && 
//						group2.equalsIgnoreCase("IFSC")) {
//					String s4 = client.getIfsc();
//					allValues.add(s4);
//				}
				boolean shares = clientUser.getShares() == client.getShares();
				if (shares == true
						&& (group1Question1.equalsIgnoreCase("SHARES") || group1Question2.equalsIgnoreCase("SHARES") || group1Question3.equalsIgnoreCase("SHARES"))) {
					String s7 = Double.toString(client.getShares());
					allValues.add(s7);
				}
//				boolean nominee = clientUser.getNomName().equalsIgnoreCase(client.getNomName());
//				if (nominee == true && group2.equalsIgnoreCase("NOMINEE")) {
//					String s5 = client.getNomName();
//					allValues.add(s5);
//				}
				boolean pincode = clientUser.getPincode().equalsIgnoreCase(client.getPincode());
				if (pincode == true && (group1Question1.equalsIgnoreCase("PINCODE")
						|| group1Question2.equalsIgnoreCase("PINCODE") || group1Question3.equalsIgnoreCase("PINCODE"))) {
					String s6 = client.getPincode();
					allValues.add(s6);
				}
				boolean fatherHusbandName = clientUser.getFatherName().equalsIgnoreCase(client.getFatherName());
				if (fatherHusbandName == true && (group1Question1.equalsIgnoreCase("FATHER_HUSBAND_NAME")
						|| group1Question2.equalsIgnoreCase("FATHER_HUSBAND_NAME") || group1Question3.equalsIgnoreCase("FATHER_HUSBAND_NAME"))) {
					String s7 = client.getFatherName();
					allValues.add(s7);
				}
				if (allValues.size() == 3) {
					/*
					 * * Random rand1 = new Random(); int emailRandomOtp=rand1.nextInt(10000); int
					 * phoneRandomOtp=rand1.nextInt(10000);
					 * log.error("email otp---->"+emailRandomOtp);
					 * log.error("phone otp---->"+phoneRandomOtp);
					 */

					int emailotp = 123456;
					int phoneotp = 567890;
					if(emailtoggle.equals("on")) {
//						TODO phone otp random
						Random rand1 = new Random();
						emailotp = rand1.nextInt(999999+1-100000);
						MailModel mailModel=new MailModel();
						mailModel.setContent(String.valueOf(emailotp));
						mailModel.setSubject("emailotp");
						mailModel.setName(clientUser.getShareholdername());
						mailModel.setTo(clientUser.getEmail());
						emailApplication.sendEmail(mailModel);
						
					}
					OTPRegistrationVerification otpRegistrationVerification = new OTPRegistrationVerification();
					otpRegistrationVerification.setClientId(client.getClientId());
					otpRegistrationVerification.setEmail(client.getEmail());
					otpRegistrationVerification.setPhone(client.getPhone());
					otpRegistrationVerification.setEmailOtp(emailotp);
					otpRegistrationVerification.setPhoneOtp(phoneotp);
					otpRegistrationVerificationDAO.save(otpRegistrationVerification); // need not
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, otpRegistrationVerification, version);
				} else {
                    log.error("In checkUserSecurityQuestion INVALID_ANSWER--------------------------------");
					return response.getAuthResponse("INVALID_ANSWER", HttpStatus.BAD_REQUEST, null, version);

				}

			}

			else {
				log.error("In checkUserSecurityQuestion SECURITY_VERIFICATION_TIMEOUT--------------------------------");
				return response.getAuthResponse("SECURITY_VERIFICATION_TIMEOUT", HttpStatus.BAD_REQUEST, null, version);
			}

		} catch (CheckUserSecurityException checkUserSecurityException) {
			log.error("In CheckUserSecurityException INVALID_ANSWER--------------------------------");
			return response.getAuthResponse("INVALID_ANSWER", HttpStatus.BAD_REQUEST, null, version);

		}

	}

	public List<String> getRandomElement(List<String> list, int totalCounts) {
		Random rand = new Random();

		// create a temporary list for storing
		// selected element
		List<String> newList = new ArrayList<>();
		List<String> newList2 = new ArrayList<>();
		newList2.addAll(list);
		for (int i = 0; i < totalCounts; i++) {
			log.error("newlist--------->+" + newList);
			// take a random index between 0 to size
			// of given List
			int randomIndex = rand.nextInt(newList2.size());

			// add element in temporary list
			String list1 = newList2.get(randomIndex);
			if (newList.contains(list1) == false) {
				newList.add(list1);
				newList2.remove(list1);
			}

			log.error("In getRandomElement newlist--------->+" + newList);
		}
		return newList;

	}

	public ResponseEntity<?> setSecurityQuestions(BenposeDataMasterModel client, String version) {
		try {
			BenposeDataMasterModel clientUser = clientMasterDao.getClientDetails(client.getClientId());

//			log.error("In setSecurityQuestions added values to hashmap-------->" + questionSetList1);

//			List<String> newList1 = getRandomElement(questionSetList1, 2);

//			log.error("In setSecurityQuestions questionSet1-------->" + newList1);

//			log.error("In setSecurityQuestions keyset1--->" + questionSetList2);

//			List<String> newList2 = getRandomElement(questionSetList2, 1);

//			log.error("In setSecurityQuestions questionSet2-------->" + newList2);
			
			List<String> finalList = new ArrayList<String>();
			Stream.of(questionSetList1,questionSetList2,questionSetList3).forEach(finalList::addAll);
			
			List<String> finalList1 = getRandomElement(finalList, 3);
			
			VerifyQuestion verifyQuestion = new VerifyQuestion();
			verifyQuestion.setClientId(clientUser.getClientId());
//			String questionQroup1 = newList1.get(0) + "," + newList1.get(1);
//			String questionQroup2 = newList2.get(0);
			String questionQroup3 = finalList1.get(0) + "," + finalList1.get(1) + "," + finalList1.get(2);
//			verifyQuestion.setQuestionGroup1(questionQroup1);
//			verifyQuestion.setQuestionGroup2(questionQroup2);
			verifyQuestion.setQuestionGroup1(questionQroup3);
			log.error("In setSecurityQuestions verifyQuestions--------->" + verifyQuestion);
			verifyQuestion = verifyQuestionDao.save(verifyQuestion);
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, verifyQuestion, version);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In setSecurityQuestions in Exception FORBIDDEN--------------------------------");
			return response.getAuthResponse("BAD_REQUEST", HttpStatus.BAD_REQUEST, null, version);

		}

	}

	public ResponseEntity<?> changePassword(ChangePassword changePassword, String version) {
		try {

			// String token1 = token.substring(7);
			// String username = jwtTokenUtil.extractUsername(token1);
			// User isUserExist = shareHolderDao.userExist(changePassword.getUsername());
			UserMaster isUserExist = userMasterDao.loadUserByUserName(changePassword.getUsername());
			BrokerMaster brokerMaster = brokerRegistrationDAO.checkBroker(changePassword.getUsername());
			// String dbPassword = isUserExist.getPassword();
			// BCryptPasswordEncoder b = new BCryptPasswordEncoder();
			if (isUserExist != null || brokerMaster != null) {
				if (changePassword.getOldPassword() != null && changePassword.getNewPassword() != null
						&& changePassword.getConfirmPassword() != null) {
					if (changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
						// boolean passwordMatch = b.matches(changePassword.getOldPassword(),
						// dbPassword);
						// if (passwordMatch == true) {
						if ((changePassword.getOldPassword().equals(changePassword.getNewPassword())) == false) {
							// String newHashPassword = b.encode(changePassword.getNewPassword());
							List<UserRepresentation> userResources = keycloakClientAuthExample
									.newKeycloakWithClientCredentials().realm(REALM).users()
									.search(changePassword.getUsername());
							List<String> requiredList = keycloakClientAuthExample.newKeycloakWithClientCredentials()
									.realm(REALM).users().search(changePassword.getUsername()).get(0)
									.getRequiredActions();

							// requiredList.contains("UPDATE_PASSWORD");
							log.error("In changePassword requiredList ------------------------>" + requiredList);
							AuthzClient ac = keycloakClientAuthExample.getAuthzClient();
							AuthorizationRequest ar = new AuthorizationRequest();
							try {
								AuthorizationResponse user = ac
										.authorization(changePassword.getUsername(), changePassword.getOldPassword())
										.authorize();
								return keycloakService.resetPassword(changePassword.getUsername(),
										changePassword.getNewPassword(), changePassword.getConfirmPassword());
							} catch (RuntimeException e) {
								log.error("In changePassword RuntimeException--------------------------");
								e.getStackTrace();
								HttpResponseException err = (HttpResponseException) e.getCause();

								log.error("In changePassword e.getMessage()------------------->" + e.getMessage());
								log.error("In changePassword e.getCause()------------------->" + e.getCause());
								log.error("In changePassword err.getMessage()------------------->"
										+ err.toString().matches("(.*)Account is not fully set up(.*)"));
								log.error("In changePassword messages------------>" + err.getLocalizedMessage());
								log.error("In changePassword err.getmessages------------>" + err.getMessage());

								log.error("In changePassword err.getReasonPhrase()------------------->" + err.getReasonPhrase());
								if (err.toString().contains("Account is not fully set up")) {
									return keycloakService.resetPassword(changePassword.getUsername(),
											changePassword.getNewPassword(), changePassword.getConfirmPassword());
								} else if (err.toString().contains("Invalid user credentials")) {
									log.error("In changePassword RuntimeException INVALID_PASSWORD--------------------------");
									return response.getAuthResponse("INVALID_PASSWORD", HttpStatus.BAD_REQUEST, null,
											version);
								} else {
									log.error("In changePassword RuntimeException INTERNAL_SERVER_ERROR--------------------------");
									return response.getAuthResponse("INTERNAL_SERVER_ERROR",
											HttpStatus.INTERNAL_SERVER_ERROR, null, version);
								}

							}
							// isUserExist.setPassword(newHashPassword);
							// shareHolderDao.save(isUserExist);
							// HashMap<String, Object> userResponse = new HashMap<String, Object>();
							//// userResponse.put("Id", isUserExist.getId());
							// userResponse.put("UserName", changePassword.getUsername());
							// return response.getAuthResponse("SUCCESS", HttpStatus.OK, userResponse,
							// version);
						} else {
							log.error("In changePassword OLD_NEW_PASSWORD_SAME--------------------------");
							return response.getAuthResponse("OLD_NEW_PASSWORD_SAME", HttpStatus.BAD_REQUEST, null,
									version);
						}
						// }
						// else {
						// return response.getAuthResponse("INVALID_PASSWORD", HttpStatus.FORBIDDEN,
						// null, version);
						// }
					} else {
						log.error("In changePassword NEW_CONFIRM_PASSWORD_MISMATCH--------------------------");
						return response.getAuthResponse("NEW_CONFIRM_PASSWORD_MISMATCH", HttpStatus.BAD_REQUEST, null,
								version);
					}
				} else {
					log.error("In changePassword PASSWORD_IS_NULL--------------------------");
					return response.getAuthResponse("PASSWORD_IS_NULL", HttpStatus.BAD_REQUEST, null, version);
				}
			} else {
				log.error("In changePassword INVALID_CREDENTIAL--------------------------");
				return response.getAuthResponse("INVALID_CREDENTIAL", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In changePassword in Exception INTERNAL_SERVER_ERROR--------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> checkUserId(String version, UserMaster user) {
		try {
			log.error("In checkUserId userId---------> " + user.getUsername());
			UserMaster userMaster = userMasterDao.loadUserByUserName(user.getUsername());
			log.error("In checkUserId userMaster ---------> " + userMaster);

			if (userMaster != null) {
				HashMap<String, Object> userMasterDetails = new HashMap<String, Object>();
				userMasterDetails.put("Id", userMaster.getId());
				userMasterDetails.put("UserName", userMaster.getUsername());
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, userMasterDetails, version);
			} else {
				log.error("In checkUserId INVALID_USER_ID--------------------------");
				return response.getAuthResponse("INVALID_USER_ID", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In checkUserId Exception INTERNAL_SERVER_ERROR--------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);

		}
	}

	public ResponseEntity<?> resetPassword(ResetPassword resetPassword, String version) {
		try {
			User isUserExist = shareHolderDao.userExist(resetPassword.getUserName());
			if (isUserExist != null) {
				if (resetPassword.getConfirmPassword() != null && resetPassword.getNewPassword() != null) {
					if (resetPassword.getNewPassword().equals(resetPassword.getConfirmPassword())) {
						BCryptPasswordEncoder b = new BCryptPasswordEncoder();
						String newHashPassword = b.encode(resetPassword.getNewPassword());
						isUserExist.setPassword(newHashPassword);
						shareHolderDao.save(isUserExist);
						HashMap<String, Object> userResponse = new HashMap<String, Object>();
						userResponse.put("Id", isUserExist.getId());
						userResponse.put("UserName", isUserExist.getUserMaster().getUsername());
						return response.getAuthResponse("SUCCESS", HttpStatus.OK, userResponse, version);
					} else {
						log.error("In resetPassword NEW_CONFIRM_PASSWORD_MISMATCH--------------------------");
						return response.getAuthResponse("NEW_CONFIRM_PASSWORD_MISMATCH", HttpStatus.BAD_REQUEST, null,
								version);
					}
				} else {
					log.error("In resetPassword PASSWORD_IS_NULL--------------------------");
					return response.getAuthResponse("PASSWORD_IS_NULL", HttpStatus.BAD_REQUEST, null, version);
				}
			} else {
				log.error("In resetPassword INVALID_USER_ID--------------------------");
				return response.getAuthResponse("INVALID_USER_ID", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In resetPassword in Exception INTERNAL_SERVER_ERROR--------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

}
