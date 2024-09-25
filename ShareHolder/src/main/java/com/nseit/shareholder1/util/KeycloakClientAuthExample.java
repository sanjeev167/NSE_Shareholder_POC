package com.nseit.shareholder1.util;

import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;

import org.keycloak.OAuth2Constants;
import org.keycloak.RSATokenVerifier;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.common.VerificationException;
import org.keycloak.jose.jws.JWSHeader;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.KeysMetadataRepresentation.KeyMetadataRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nseit.shareholder1.config.KeycloakConfiguration;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KeycloakClientAuthExample {

	@Autowired
	KeycloakConfiguration kConfiguration;

	// @Value(value = "${keycloak.auth-server-url}")
	private String serverurl;
	// @Value("${keycloak.resource}")
	private String resource;
	// @Value("${keycloak.credentials.secret}")
	private String creds;
	// @Value("${keycloak.realm}")
	private String realm;

	// public static void main(String[] args) {
	//
	// KeycloakClientFacade facade = new KeycloakClientFacade( //
	// "http://localhost:8081/auth" //
	// , "admin-client-demo" //
	// , "demo-client-1" //
	// , "da6947c2-6559-4c37-b219-d37bb72ec2fa" //
	// );
	//
	// // Get raw AccessToken string for client credentials grant
	// System.out.println(facade.getAccessTokenString());
	//
	// // Get decoded AccessToken for client credentials grant
	//// System.out.println(facade.getAccessToken());
	//
	// // Get decoded AccessToken for password credentials grant
	// AccessToken accessToken = facade.getAccessToken("tester", "test");
	// System.out.println(accessToken.getSubject());
	// }
	//
	// static class KeycloakClientFacade {

	// public KeycloakClientAuthExample(String serverUrl, String realmId, String
	// resource, String clientSecret) {
	// this.serverurl = serverUrl;
	// this.realm = realmId;
	// this.resource = resource;
	// this.creds = clientSecret;
	// }
	// TODO:Unused
	public AccessToken getAccessToken() {
		return getAccessToken(newKeycloakBuilderWithClientCredentials().build());
	}

	// TODO:Unused
	public String getAccessTokenString() {
		return getAccessTokenString(newKeycloakBuilderWithClientCredentials().build());
	}

	// TODO:Unused
	public AccessToken getAccessToken(String username, String password) {
		return getAccessToken(newKeycloakBuilderWithPasswordCredentials(username, password).build());
	}

	// TODO:Unused
	public String getAccessTokenString(String username, String password) {
		log.info("username------------>" + username + " password------------------>" + password);
		// TokenManager tokenManager=newKeycloakBuilderWithPasswordCredentials(username,
		// password).build().tokenManager();
		// log.info("Error--------------->"+tokenManager.getAccessToken().getError());
		// log.info("getErrorDescription------------>"+tokenManager.getAccessToken().getErrorDescription());
		// log.info("tokenManger----------------------->"+
		// tokenManager.getAccessToken().getToken());
		//
		//
		// String token = tokenManager.getAccessTokenString();
		// log.info("token----------------------------------->"+token);
		return getAccessTokenString(newKeycloakBuilderWithPasswordCredentials(username, password).build());
		// return token;
	}

	// TODO:Unused
	private AccessToken getAccessToken(Keycloak keycloak) {
		return extractAccessTokenFrom(keycloak, getAccessTokenString(keycloak));
	}

	// TODO:Unused
	private String getAccessTokenString(Keycloak keycloak) {
		AccessTokenResponse tokenResponse = getAccessTokenResponse(keycloak);
		return tokenResponse == null ? null : tokenResponse.getToken();
	}

	// TODO:Unused
	private AccessToken extractAccessTokenFrom(Keycloak keycloak, String token) {
		if (token == null) {
			return null;
		}

		try {
			RSATokenVerifier verifier = RSATokenVerifier.create(token);
			PublicKey publicKey = getRealmPublicKey(keycloak, verifier.getHeader());
			return verifier.realmUrl(getRealmUrl()) //
					.publicKey(publicKey) //
					.verify() //
					.getToken();
		} catch (VerificationException e) {
			return null;
		}
	}

	private KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(String username, String password) {
		return newKeycloakBuilderWithClientCredentials() //
				.username(username) //
				.password(password) //
				.grantType(OAuth2Constants.PASSWORD);
	}

	public KeycloakBuilder newKeycloakBuilderWithClientCredentials() {
		return KeycloakBuilder.builder() //
				.realm(realm) //
				.serverUrl(serverurl)//
				.clientId(resource) //
				.clientSecret(creds) //
				.grantType(OAuth2Constants.CLIENT_CREDENTIALS);
	}

	// TODO:Unused
	public KeycloakBuilder newKeycloakBuilderWithToken() {
		return KeycloakBuilder.builder() //
				.realm(realm) //
				.serverUrl(serverurl)//
				.clientId(resource) //
				.clientSecret(creds)
				//
				.grantType(OAuth2Constants.CLIENT_CREDENTIALS);
	}

	
	public Keycloak newKeycloakWithClientCredentials() {
		return KeycloakBuilder.builder() //
				.realm(realm) //
				.serverUrl(serverurl)//
				.clientId(resource) //
				.clientSecret(creds) //
				.username(adminUserName).password(adminPassword).grantType(OAuth2Constants.PASSWORD).build();
	}

	// TODO:Unused
	private AccessTokenResponse getAccessTokenResponse(Keycloak keycloak) {
		try {

			return keycloak.tokenManager().getAccessToken();
		} catch (Exception ex) {
			return null;
		}
	}

	// TODO:Unused
	public String getRealmUrl() {
		return serverurl + "/realms/" + realm;
	}

	// TODO:Unused
	public String getRealmCertsUrl() {
		return getRealmUrl() + "/protocol/openid-connect/certs";
	}

	// TODO:Unused
	private PublicKey getRealmPublicKey(Keycloak keycloak, JWSHeader jwsHeader) {

		// Variant 1: use openid-connect /certs endpoint
		return retrievePublicKeyFromCertsEndpoint(jwsHeader);

		// Variant 2: use the Public Key referenced by the "kid" in the JWSHeader
		// in order to access realm public key we need at least realm role... e.g.
		// view-realm
		// return retrieveActivePublicKeyFromKeysEndpoint(keycloak, jwsHeader);

		// Variant 3: use the active RSA Public Key exported by the PublicRealmResource
		// representation
		// return retrieveActivePublicKeyFromPublicRealmEndpoint();
	}

	// TODO:Unused
	private PublicKey retrievePublicKeyFromCertsEndpoint(JWSHeader jwsHeader) {
		try {
			ObjectMapper om = new ObjectMapper();
			@SuppressWarnings("unchecked")
			Map<String, Object> certInfos = om.readValue(new URL(getRealmCertsUrl()).openStream(), Map.class);

			List<Map<String, Object>> keys = (List<Map<String, Object>>) certInfos.get("keys");

			Map<String, Object> keyInfo = null;
			for (Map<String, Object> key : keys) {
				String kid = (String) key.get("kid");

				if (jwsHeader.getKeyId().equals(kid)) {
					keyInfo = key;
					break;
				}
			}

			if (keyInfo == null) {
				return null;
			}

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			String modulusBase64 = (String) keyInfo.get("n");
			String exponentBase64 = (String) keyInfo.get("e");

			// see org.keycloak.jose.jwk.JWKBuilder#rs256
			Decoder urlDecoder = Base64.getUrlDecoder();
			BigInteger modulus = new BigInteger(1, urlDecoder.decode(modulusBase64));
			BigInteger publicExponent = new BigInteger(1, urlDecoder.decode(exponentBase64));

			return keyFactory.generatePublic(new RSAPublicKeySpec(modulus, publicExponent));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// TODO:Unused
	private PublicKey retrieveActivePublicKeyFromPublicRealmEndpoint() {

		try {
			ObjectMapper om = new ObjectMapper();
			@SuppressWarnings("unchecked")
			Map<String, Object> realmInfo = om.readValue(new URL(getRealmUrl()).openStream(), Map.class);
			return toPublicKey((String) realmInfo.get("public_key"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// TODO:Unused
	private PublicKey retrieveActivePublicKeyFromKeysEndpoint(Keycloak keycloak, JWSHeader jwsHeader) {

		List<KeyMetadataRepresentation> keys = keycloak.realm(realm).keys().getKeyMetadata().getKeys();

		String publicKeyString = null;
		for (KeyMetadataRepresentation key : keys) {
			if (key.getKid().equals(jwsHeader.getKeyId())) {
				publicKeyString = key.getPublicKey();
				break;
			}
		}

		return toPublicKey(publicKeyString);
	}

	// TODO:Unused
	public PublicKey toPublicKey(String publicKeyString) {
		try {
			byte[] publicBytes = Base64.getDecoder().decode(publicKeyString);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			return null;
		}
	}

	public void setAuthzClient() {
		System.out.println("all values------------>" + serverurl);
		Map<String, Object> credential = new HashMap<String, Object>();
		credential.put("secret", creds);
		AuthzClient authzClient = AuthzClient.create(new Configuration(serverurl, realm, resource, credential, null));

		System.out.println("authzClient------------------------->" + authzClient);
		this.authzClient = authzClient;
	}

	public AuthzClient getAuthzClient() {
		System.out.println("AUthzclient outside if------------------>");
		if (this.authzClient == null) {
			System.out.println("AUthzclient inside if------------------>");
			this.setAuthzClient();
		}
		return this.authzClient;
	}

	public KeycloakClientAuthExample() {
		super();
		// TODO Auto-generated constructor stub
	}

	private AuthzClient authzClient;

	public static String getCreatedId(Response response) {
		URI location = response.getLocation();
		if (!response.getStatusInfo().equals(Response.Status.CREATED)) {
			Response.StatusType statusInfo = response.getStatusInfo();
			throw new RuntimeException("Create method returned status " + statusInfo.getReasonPhrase() + " (Code: "
					+ statusInfo.getStatusCode() + "); expected status: Created (201)");
		}
		if (location == null) {
			return null;
		}
		String path = location.getPath();
		return path.substring(path.lastIndexOf('/') + 1);
	}
	@Value("${adminusername}")
    private String adminUserName;
   
    @Value("${adminpassword}")
	private String adminPassword;

	@PostConstruct
	public void afterPropertiesSet() {
		// TODO Auto-generated method stub
		serverurl = kConfiguration.getAuthServerUrl();
		realm = kConfiguration.getRealm();
		resource = kConfiguration.getResource();
		creds = kConfiguration.getCredentials().getSecret();
		 
//		adminUserName=kConfiguration.getAdminUserName();
//		adminPassword=kConfiguration.getAdminPassword();
		System.out.println("Initialize Properties of KeycloakClientAuthEx-------------------->" + serverurl + "admin user password----------->"+adminPassword + "admin user name----------->"+adminUserName);
	}

}
// }