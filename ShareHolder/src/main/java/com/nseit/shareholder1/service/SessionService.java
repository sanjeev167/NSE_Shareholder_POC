package com.nseit.shareholder1.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.authorization.client.util.Http;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nseit.shareholder1.dao.LoginOtpDAO;
import com.nseit.shareholder1.model.LoginOTP;
import com.nseit.shareholder1.util.KeycloakClientAuthExample;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SessionService {
	
	@Autowired
	KeycloakService keycloakService;
	
	@Autowired
	KeycloakClientAuthExample keycloakClientAuthExample;
	
	@Value("${keycloak.realm}")
	private String REALM;
	
	@Value(value = "${keycloak.auth-server-url}")
	private String authServer;
	
	@Value("${keycloak.resource}")
	private String resourceId;
	
	@Value("${keycloak.credentials.secret}")
	private String credential;
	
	@Autowired
	LoginOtpDAO loginOtpDAO;
	
	@Autowired
	ResponseUtil response;
	
	public ResponseEntity<?> refreshToken(LoginOTP loginOTP,String version) {
		try {
//		/auth/realm/(realm-name)/protocol/openid-connect/token
		LoginOTP otpExist = loginOtpDAO.getByUserName(loginOTP.getUserName());
//		    String url = kcProperties.getAuthServerUrl() + "/realms/" + kcProperties.getRealm() + "/protocol/openid-connect/token";
		    String url = authServer + "/realms/" + REALM + "/protocol/openid-connect/token";
//		    String clientId = kcProperties.getResource();
		    String clientId = resourceId;
//		    String secret = (String) kcProperties.getCredentials().get("secret");
		    String secret = credential;
		    Http http = new Http(keycloakClientAuthExample.getAuthzClient().getConfiguration(), (params, headers) -> {});
		    AccessTokenResponse accessResponse=http.<AccessTokenResponse>post(url)
            .authentication()
                .client()
            .form()
                .param("grant_type", "refresh_token")
                .param("refresh_token", loginOTP.getRefreshToken())
                .param("client_id", clientId)
                .param("client_secret", secret)
            .response()
                .json(AccessTokenResponse.class)
            .execute();
		    
		    LoginOTP login=new LoginOTP();
		    login.setOtp(null);
		    login.setUserName(loginOTP.getUserName());
		    login.setRefreshToken(accessResponse.getRefreshToken());
		    login.setToken(accessResponse.getToken());
		    loginOtpDAO.save(login);
		    return response.getAuthResponse("SUCCESS", HttpStatus.OK, login, version);
		}
		catch(Exception e) {
			log.error("In refreshToken Exception INTERNAL_SERVER_ERROR--------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR",
					HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
	
	public ResponseEntity<?> logout(String version, String token,LoginOTP loginOTP) {
		try {
//		/auth/realm/(realm-name)/protocol/openid-connect/token
//		LoginOTP otpExist = loginOtpDAO.getByUserName(loginOTP.getUserName());
//		    String url = kcProperties.getAuthServerUrl() + "/realms/" + kcProperties.getRealm() + "/protocol/openid-connect/token";
		    String url = authServer + "/realms/" + REALM + "/protocol/openid-connect/logout";
//		    String clientId = kcProperties.getResource();
		    String clientId = resourceId;
//		    String secret = (String) kcProperties.getCredentials().get("secret");
		    String secret = credential;
		    Http http = new Http(keycloakClientAuthExample.getAuthzClient().getConfiguration(), (params, headers) -> {});
		    AccessTokenResponse accessResponse=http.<AccessTokenResponse>post(url)
            .authentication()
                .client().authorizationBearer(token)
            .form()
                .param("grant_type", "refresh_token")
                .param("refresh_token", loginOTP.getRefreshToken())
                .param("client_id", clientId)
               .param("client_secret", secret)
            .response()
                .json(AccessTokenResponse.class)
            .execute();
		    log.error("In logout accessResponse----------->"+accessResponse);
//		    
		    return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, version);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error("In logout Exception INTERNAL_SERVER_ERROR--------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR",
					HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
	
//	@Bean
//	public org.keycloak.authorization.client.Configuration kcConfig() {
//	    return new org.keycloak.authorization.client.Configuration(
//	    		authServer,
//	    		REALM,
//	            resourceId,
//	            kcProperties.getCredentials(),
//	            null
//	    );
//	}

}
