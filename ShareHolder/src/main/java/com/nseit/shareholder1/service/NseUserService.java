package com.nseit.shareholder1.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.dao.LoginOtpDAO;
import com.nseit.shareholder1.dao.NseUserDAO;
import com.nseit.shareholder1.dao.ShareHolderDao;
import com.nseit.shareholder1.dao.UserMasterDao;
import com.nseit.shareholder1.duoweb.DuoWeb;
import com.nseit.shareholder1.model.DuoModel;
import com.nseit.shareholder1.model.LoginOTP;
import com.nseit.shareholder1.model.NseUserModel;
import com.nseit.shareholder1.model.User;
import com.nseit.shareholder1.model.UserMaster;
import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.KeycloakClientAuthExample;
import com.nseit.shareholder1.util.ResponseUtil;
import com.nseit.shareholder1.web.rest.errors.RequiredActionError;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NseUserService {

	@Autowired
	UserMasterDao userMasterDao;

	@Autowired
	KeycloakService keycloakService;

	@Autowired
	LoginOtpDAO loginOtpDAO;

	@Autowired
	ShareHolderDao shareHolderDao;

	@Autowired
	NseUserDAO nseUserDAO;

	@Autowired
	ResponseUtil response;

	@Autowired
	KeycloakClientAuthExample keycloakClientAuthExample;

	@Autowired
	JwtUtil jwt;

	@Value("${keycloak.realm}")
	private String REALM;

	@Value("${keycloak.resource}")
	private String resourceClientId;
	
	static final HashMap<String, Object> hm = new HashMap<String, Object>();

	public ResponseEntity<?> nseLogin(String email, String password, String version) {
		// TODO Auto-generated method stub
		// UserMaster userMaster = userMasterDao.isEmailExist(email);
		// UserMaster user = userMasterDao.isEmailExist(email);
		
		NseUserModel nseuser = nseUserDAO.isNseUserExist(email);
		try {
			if (nseuser != null) {

				log.error("In nseLogin inside if isUserExist " + nseuser);
				/*
				 * Random rand1 = new Random(); int Otp=rand1.nextInt(10000);
				 * 
				 * log.error("otp---->"+Otp);
				 * 
				 */

				// String token = keycloakService.generateToken(email, password);
				AccessTokenResponse token = keycloakService.generateTokenObject(email, password);
				log.error("token value------------>" + token);
				int otp = 1234;
				LoginOTP loginOTP = new LoginOTP();
				// loginOTP.setOtp(otp);
				loginOTP.setUserName(email);
				loginOTP.setToken(token.getToken());
				loginOTP.setRefreshToken(token.getRefreshToken());
				log.error("loginOTP---------------> " + loginOTP);
				loginOtpDAO.save(loginOTP);
				// hm.put("id", loginOTP.getId());
				hm.put("userName", email);
				hm.put("token", token.getToken());
				hm.put("fullName", nseuser.getFull_name());
				hm.put("refreshToken", loginOTP.getRefreshToken());
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, hm, version);
				// return createAuthenticationToken(userMaster, version);
			} else {
				log.error("In nseLogin USER_NOT_EXIST-----------------------------");
				return response.getAuthResponse("USER_NOT_EXIST", HttpStatus.BAD_REQUEST, false, version);
			}
		} catch (RequiredActionError re) {
            log.error("In nseLogin RequiredActionError-----------------------------");
			return response.getAuthResponse(re.getMessage(), HttpStatus.BAD_REQUEST, false, version);
		} catch (Exception e) {
			log.error("In nseLogin Inside Exception INVALID_CREDENTIAL-----------------------------");
			return response.getAuthResponse("INVALID_CREDENTIAL", HttpStatus.BAD_REQUEST, false, version);
		}
	}

	public ResponseEntity<?> addNseUser(String version, String username, String role) {
		// TODO Auto-generated method stub
		try {
			NseUserModel nseUserModel1 = nseUserDAO.addNseUser(username);
//			if(Objects.equal(nseUserModel1, null)) {
			if (nseUserModel1==null) {
				Keycloak k = keycloakClientAuthExample.newKeycloakWithClientCredentials();
				// RealmRepresentation r = k.realm(REALM).toRepresentation();
				// String id =
				// k.realm(REALM).clients().findByClientId(resourceClientId).get(0).getId();

				String keycloak_id = keycloakService.ldapUserCreate(username, role);
				log.error("Keycloak User Created id " + keycloak_id);
				UserRepresentation userRepresentation = k.realm(REALM).users().search(username).get(0);
				String firstName = userRepresentation.getFirstName();
				String lastName = userRepresentation.getLastName();
				String fullName = firstName + " " + lastName;
				String active = null;
				// Boolean enable = userRepresentation.isEnabled();
				userRepresentation.setEnabled(false);
				String email = userRepresentation.getEmail();
				NseUserModel nseUserModel = new NseUserModel();
				nseUserModel.setKeycloak_id(keycloak_id);
				nseUserModel.setFull_name(fullName);
				nseUserModel.setEmail(email);
				nseUserModel.setUsername(username);
				nseUserModel.setRole(role);
				// newly added
				// nseUserModel.setCreated_by(jwt.extractUsername());
				nseUserModel.setProposed_change("Y");
				// if (enable == true) {
				// active = "Y";
				// } else {
				// active = "N";
				// }
				// nseUserModel.setActive(active);
				nseUserDAO.save(nseUserModel);

				return response.getAuthResponse("SUCCESS", HttpStatus.OK, nseUserModel, version);
			} else {
				log.error("In addNseUser ALREADY_USER_EXIST----------------------");
				return response.getAuthResponse("ALREADY_USER_EXIST", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In addNseUser Inside Exception INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getAllNseUser(String version) {
		try {
			List nseUserList = nseUserDAO.getAllNseUser();

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, nseUserList, version);
		} catch (Exception e) {
			log.error("In getAllNseUser Inside Exception INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getCurrentUser(String version) {
		try {
			List<NseUserModel> nseUserModelList = nseUserDAO.getCurrentUser();
			if (nseUserModelList != null && nseUserModelList.size() > 0) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, nseUserModelList, version);
			} else {
				log.error("In getCurrentUser USER_NOT_EXIST-------------------------------");
				return response.getAuthResponse("USER_NOT_EXIST", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In getCurrentUser Inside Exception INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> getNseUserBasedOnAccept(String version) {
		try {
			List<NseUserModel> nseUserModel = nseUserDAO.getNseUserBasedOnAccept();
			if (nseUserModel.size() > 0) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, nseUserModel, version);
			} else {
				log.error("In getNseUserBasedOnAccept USER_NOT_EXIST-------------------------------");
				return response.getAuthResponse("USER_NOT_EXIST", HttpStatus.OK, nseUserModel, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Inside Exception INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("In getNseUserBasedOnAccept INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> isAccept(String version, String username, String accept) {
		try {
			NseUserModel nseUserModel1 = nseUserDAO.getProposedUser(username);
			String jwtUser=jwt.extractUsername();
			if(jwtUser.equals(username)) {
				return response.getAuthResponse("SAME_USER", HttpStatus.BAD_REQUEST, null, version);
			}
			if (nseUserModel1 != null) {
				if (accept.equalsIgnoreCase("Y")) {
					NseUserModel nseUserModel2 = (NseUserModel) nseUserModel1.clone();
					nseUserModel2.setId(null);
					nseUserModel2.setActive(nseUserModel1.getProposed_change());
					nseUserModel2.setAccept(accept);
					nseUserDAO.save(nseUserModel2);
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, nseUserModel2, version);
				} else {
					nseUserModel1.setAccept("N");
					Timestamp instant = Timestamp.from(Instant.now());
					nseUserModel1.setModifiedOn(instant);
					nseUserDAO.save(nseUserModel1);
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, nseUserModel1, version);
				}

			} else {
				log.error("In isAccept NO_DATA----------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, nseUserModel1, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In isAccept Inside Exception INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> proposedChange(String version, String username, String proposedChange) {
		try {
			NseUserModel nseUserModel1 = nseUserDAO.getAcceptedUser(username);
			if (nseUserModel1 != null) {
				// nseUserModel1.setActive(active);
				NseUserModel nseUserModel2 = (NseUserModel) nseUserModel1.clone();
				nseUserModel2.setId(null);
				nseUserModel2.setAccept(null);
				nseUserModel2.setProposed_change(proposedChange);
				nseUserDAO.save(nseUserModel2);
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, nseUserModel2, version);
			} else {
				log.error("In proposedChange NO_DATA----------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, nseUserModel1, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In proposedChange Inside Exception INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	
	
	
	@Value(value = "${ikey}")
	private String ikey;

	@Value(value = "${skey}")
	private String skey;

	@Value(value = "${akey}")
	private String akey;

	@Value(value = "${host}")
	private String host;

	@Autowired
	DuoWeb duoWeb;
	
	public ResponseEntity<?> duoResponse(DuoModel duoModel,String version) {
		String duoResponse = duoModel.getSigResponse();

		try {
			if (duoResponse == null) {

				return response.getAuthResponse("NO_DATA", HttpStatus.BAD_REQUEST, null, version);

			} else {
				String username = DuoWeb.verifyResponse(ikey, skey, akey, duoResponse);

				// success responseEntity
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, hm, version);

			}
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
			// error responseEntity
		}
	}
}
