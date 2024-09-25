package com.nseit.shareholder1.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ResourcePermissionRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nseit.shareholder1.model.ResetPassword;
import com.nseit.shareholder1.util.KeycloakClientAuthExample;
import com.nseit.shareholder1.util.ResponseUtil;
import com.nseit.shareholder1.web.rest.errors.RequiredActionError;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KeycloakService {

	@Autowired
	KeycloakClientAuthExample keycloakClientAuthExample;

	@Value("${keycloak.realm}")
	private String REALM;

	@Autowired
	ResponseUtil responseUtil;

	public String createAccount(final String username, final String password, String firstName, String lastName) {
		// ldapUserCreate(username,"initator");

		// try {
		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue(password);
		credential.setTemporary(true);
		UserRepresentation user = new UserRepresentation();
		user.setUsername(username);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.singleAttribute("customAttribute", "customAttribute");
		user.setCredentials(Arrays.asList(credential));
		user.setEnabled(true);
		UsersResource ur = keycloakClientAuthExample.newKeycloakWithClientCredentials().realm(REALM).users();
		log.error("In createAccount User COunt ------------------------------------->" + ur.count());

		javax.ws.rs.core.Response response = ur.create(user);

		log.error("In createAccount Response--------------------------------->" + response);
		final int status = response.getStatus();
		log.error("In createAccount Status----------------------------->" + status);
		if (status != HttpStatus.CREATED.value()) {
			// throw new UserNotCreatedException;
			log.error("In createAccount User Not Created-------------------->" + status);
		}
		final String createdId = keycloakClientAuthExample.getCreatedId(response);
		UsersResource ur1 = keycloakClientAuthExample.newKeycloakWithClientCredentials().realm(REALM).users();
		Keycloak k = keycloakClientAuthExample.newKeycloakWithClientCredentials();
		String id = k.realm(REALM).clients().findByClientId("shareholder-spring-client").get(0).getId();
		log.error("In createAccount username------------->"+username);
		if(username.startsWith("AR")) {
			List<RoleRepresentation> userRole = k.realm(REALM).clients().get(id).roles().list().stream()
					.filter(r -> r.getName().equalsIgnoreCase("authorizedrep")).collect(Collectors.toList());
			ur1.get(createdId).roles().clientLevel(id).add(userRole);
			log.error("In createAccount username------------->"+username);

			return createdId;
		}
		List<RoleRepresentation> userRole = k.realm(REALM).clients().get(id).roles().list().stream()
				.filter(r -> r.getName().equalsIgnoreCase("shareholder")).collect(Collectors.toList());
		ur1.get(createdId).roles().clientLevel(id).add(userRole);

		return createdId;
		// return responseUtil.getAuthResponse("SUCCESS", HttpStatus.OK, createdId);
		// } catch (Exception e) {
		// return responseUtil.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED,
		// null);
		// }
	}

	public ResponseEntity<?> resetPassword(String userName, String newPassword, String confirmPasswor) {
		// Reset password didnt find any validation for password
		try {
			AuthzClient ac = keycloakClientAuthExample.getAuthzClient();
			AuthorizationRequest ar = new AuthorizationRequest();
			// AuthorizationResponse user = ac.authorization(username,
			// oldpassword).authorize();
			log.error("ac----------------->" + ac);
			if (newPassword.equals(confirmPasswor)) {
				CredentialRepresentation newCredential = new CredentialRepresentation();
				List<UserRepresentation> userResources = keycloakClientAuthExample.newKeycloakWithClientCredentials()
						.realm(REALM).users().search(userName);
				String userPass = userResources.get(0).getId();
				newCredential.setType(CredentialRepresentation.PASSWORD);
				newCredential.setValue(newPassword);
				newCredential.setTemporary(false);
				keycloakClientAuthExample.newKeycloakWithClientCredentials().realm(REALM).users().get(userPass)
						.resetPassword(newCredential);
				// return "Password reset successfully";
				return responseUtil.getAuthResponse("SUCCESS", HttpStatus.OK, "Password changed successfully", null);
			} else {
                log.error("In resetPassword INVALID_PASSWORD----------------------------");
				return responseUtil.getAuthResponse("INVALID_PASSWORD", HttpStatus.UNAUTHORIZED, null, null);

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In resetPassword UNAUTHORIZED----------------------------");
			return responseUtil.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, null, null);
		}

	}

	public String ldapUserCreate(String username, String role_name) {

		// try {
		Keycloak k = keycloakClientAuthExample.newKeycloakWithClientCredentials();
		RealmRepresentation r = k.realm(REALM).toRepresentation();
		String id = k.realm(REALM).clients().findByClientId("shareholder-spring-client").get(0).getId();
		List<RoleRepresentation> roleList = k.realm(REALM).clients().get(id).roles().list();

		List<UserRepresentation> userName = k.realm(REALM).users().search(username, 0, 10, true);
		if (userName.size() > 0) {
			UserRepresentation userRepresentation = userName.get(0);
            log.error("In ldapUserCreate Roles list---------->" + k.realm(REALM).clients().get(id).roles().list().get(0));
			System.out.println("Roles list---------->" + k.realm(REALM).clients().get(id).roles().list().get(0));
			log.error("In ldapUserCreate user first name----------->" + userRepresentation.getFirstName());
			System.out.println(
					"user first name----------->" + userRepresentation.getFirstName());
			log.error("In ldapUserCreate user last name----------->" + userRepresentation.getLastName());
			System.out.println(
					"user last name----------->" + userRepresentation.getLastName());
			log.error("In ldapUserCreate user ----------->" + userRepresentation.isEnabled());
			System.out.println("user ----------->" + userRepresentation.isEnabled());
			log.error("In ldapUserCreate Federated users ------------------>" + r);
			System.out.println("Federated users ------------------>" + r);
			log.error("In ldapUserCreate r---------------->" + r.getRealm());
			System.out.println("r---------------->" + r.getRealm());
			for (RoleRepresentation r1 : roleList) {
				if (r1.getName().equalsIgnoreCase(role_name)) {
					log.error("role name------->" + r1.getId());
					System.out.println("role name------->" + r1.getId());
					Map<String, List<String>> assignRoles = new HashMap<String, List<String>>();
					List setRole = new ArrayList();
					setRole.add(r1.getName());
					log.error("Set Roles----------->" + setRole);
					System.out.println("Set Roles----------->" + setRole);
					assignRoles.put(id, setRole);
					List<RoleRepresentation> userRole = k.realm(REALM).clients().get(id).roles().list().stream()
							.filter(x -> x.getName().equalsIgnoreCase(role_name)).collect(Collectors.toList());
					// userRepresentation.setClientRoles(assignRoles);
					UsersResource ur1 = keycloakClientAuthExample.newKeycloakWithClientCredentials().realm(REALM)
							.users();
					k.realm(REALM).users().get(userRepresentation.getId()).roles().clientLevel(id).add(userRole);
					return userRepresentation.getId();
					// return responseUtil.getAuthResponse("SUCCESS", HttpStatus.OK, "Ldap user
					// created successfully");

				}

			}
			return null;
			// return responseUtil.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED,
			// "Invalid Role");
		} else {
			return null;
		}
		//
		// return responseUtil.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED,
		// "Invalid User");}
		// }}catch(Exception e) {
		// return responseUtil.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED,
		// null);
		// }

		// List<UserFederationProviderRepresentation>
		// userFederation=r.getUserFederationProviders();
		// System.out.println("userFederation---------------->"+userFederation.size());
		// for(UserFederationProviderRepresentation u: userFederation) {
		// System.out.println("UserFederationProviderRepresentation------------>"+u);
		// }

		// TODO Auto-generated method stub

	}

	public List<RoleRepresentation> userRoleList(String username) {
		Keycloak k = keycloakClientAuthExample.newKeycloakWithClientCredentials();
		String id = k.realm(REALM).clients().findByClientId("shareholder-spring-client").get(0).getId();
		UserRepresentation userRepresentation = k.realm(REALM).users().search(username).get(0);
		List<RoleRepresentation> userrolelist = k.realm(REALM).users().get(userRepresentation.getId()).roles()
				.clientLevel(id).listAll();
		log.error("In userRoleList userrolelist----------------->" + userrolelist);
		System.out.println("userrolelist----------------->" + userrolelist);
		// return responseUtil.getAuthResponse("SUCCESS", HttpStatus.OK, userrolelist);
		return userrolelist;
	}

	// try catch block
	// TODO:Unused
	public String generateToken(String username, String password) throws RequiredActionError {

		// try {
		String token = keycloakClientAuthExample.getAccessTokenString(username, password);
		List<String> requiredList = keycloakClientAuthExample.newKeycloakWithClientCredentials().realm(REALM).users()
				.search(username).get(0).getRequiredActions();
		log.error("In generateToken requiredList ------------------------>" + requiredList);
		System.out.println("requiredList ------------------------>" + requiredList);
		if (requiredList.size() > 0) {
			throw new RequiredActionError(requiredList.get(0));
		}
		return token;
		// if (token != null) {
		// return responseUtil.getAuthResponse("SUCCESS", HttpStatus.OK, token);
		// } else {
		// return responseUtil.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED,
		// null);
		// }
		// } catch (Exception e) {
		// return responseUtil.getAuthResponse("UNAUTHORIZED", HttpStatus.UNAUTHORIZED,
		// null);
		// }

	}

	public AccessTokenResponse generateTokenObject(String username, String password)
			throws RequiredActionError, HttpResponseException {
		AccessTokenResponse accesstoken = keycloakClientAuthExample.getAuthzClient().obtainAccessToken(username,
				password);
		log.error("ACCESS TOKEN ERROR----------------->" + accesstoken.getError());
		System.out.println("ACCESS TOKEN ERROR----------------->" + accesstoken.getError());
		log.error("ACCESS TOKEN ERROR DISC----------------->" + accesstoken.getErrorDescription());
		System.out.println("ACCESS TOKEN ERROR DISC----------------->" + accesstoken.getErrorDescription());
		if (accesstoken.getError() != null) {
			throw new RequiredActionError(accesstoken.getError());
		}
		String token = accesstoken.getToken();
		String refreshToken = accesstoken.getRefreshToken();
		log.error("In generateTokenObject New TOken----------------->" + token);
		System.out.println("New TOken----------------->" + token);
		List<String> requiredList = keycloakClientAuthExample.newKeycloakWithClientCredentials().realm(REALM).users()
				.search(username).get(0).getRequiredActions();
		log.error("In generateTokenObject requiredList ------------------------>" + requiredList);
		System.out.println("requiredList ------------------------>" + requiredList);
		if (requiredList.size() > 0) {
			log.error("In generateTokenObject RequiredActionError ------------------------>" );
			throw new RequiredActionError(requiredList.get(0));
		}
		return accesstoken;
	}

	public ResponseEntity<?> updateRoleMapping(String version, Map<String, Map> jsonRequestModel)
			throws JsonMappingException, JsonProcessingException {
		Keycloak k = keycloakClientAuthExample.newKeycloakWithClientCredentials();
		String id = k.realm(REALM).clients().findByClientId("shareholder-spring-client").get(0).getId();
		List<PolicyRepresentation> policyList = k.realm(REALM).clients().get(id).authorization().policies().policies();
		for (Map.Entry<String, Map> category : jsonRequestModel.entrySet()) {
			String view;
			List<String> viewList = new ArrayList<String>();
			String categoryName = category.getKey();
			log.error("In updateRoleMapping category-------------------------->" + category.getKey());
			System.out.println("category-------------------------->" + category.getKey());
			log.error("In updateRoleMapping category Typeof Entryset------------------------>" + category.getValue().entrySet());
			System.out.println("category Typeof Entryset------------------------>" + category.getValue().entrySet());
			Map<String, Map> values = category.getValue();
			Set<Entry<String, Map>> entryvalues = values.entrySet();

			// Object category;
			for (Entry<String, Map> access : entryvalues) {
				String accessName = access.getKey();
				System.out.println("PermissionCategoryValue-------------------------->" + access.getKey());
				log.error("In updateRoleMapping PermissionCategoryValue-------------------------->" + access.getKey());
				Map cval = access.getValue();
				Set<Entry<String, Object>> accessvalues = cval.entrySet();
				view = categoryName + "-" + accessName;
				viewList.add(view);
				log.error("In updateRoleMapping View------------------------->" + view);
				System.out.println("View------------------------->" + view);
				log.error("In updateRoleMapping ViewList------------------------->" + viewList);
				System.out.println("ViewList------------------------->" + viewList);
				ResourcePermissionRepresentation permission = k.realm(REALM).clients().get(id).authorization()
						.permissions().resource().findByName(view + "-access");
				System.out.println("policyList1-------->" + permission.getName());
				log.error("In updateRoleMapping policyList1-------->" + permission.getName());
				List<PolicyRepresentation> resourcepolicyList = k.realm(REALM).clients().get(id).authorization()
						.permissions().resource().findById(permission.getId()).associatedPolicies();

				for (Entry<String, Object> role : accessvalues) {
					log.error("In updateRoleMapping role Name-------------------------->" + role.getKey());
					System.out.println("role Name-------------------------->" + role.getKey());
					log.error("In updateRoleMapping role Permission-------------------------->" + role.getValue());
					System.out.println("role Permission-------------------------->" + role.getValue());

					if (role.getValue().equals(false)) {
						log.error("In updateRoleMapping Inside if condition---------->" + role.getValue());
						System.out.println("Inside if condition---------->" + role.getValue());

						permission.removePolicy(role.getKey() + "-policy-positive");
					} else if (role.getValue().equals(true)) {
						log.error("In updateRoleMapping Inside else if condition---------->" + role.getValue());
						System.out.println("Inside else if condition---------->" + role.getValue());
						permission.addPolicy(role.getKey() + "-policy-positive");

					}

				}
				k.realm(REALM).clients().get(id).authorization().permissions().resource().findById(permission.getId())
						.update(permission);
				log.error("In updateRoleMapping PermissionCategoryKey-------------------------->" + category.getKey());
				System.out.println("PermissionCategoryKey-------------------------->" + category.getKey());
				log.error("In updateRoleMapping ViewListOutside------------------------->" + viewList);
				System.out.println("ViewListOutside------------------------->" + viewList);
			}
		}

		return responseUtil.getAuthResponse("SUCCESS", HttpStatus.OK, "Permission Updated", null);
	}

	public void logout(String token) {
		// TODO Auto-generated method stub
		// Keycloak k = keycloakClientAuthExample.newKeycloakWithClientCredentials();
		Keycloak k = keycloakClientAuthExample.newKeycloakWithClientCredentials();
		String id = k.realm(REALM).clients().findByClientId("shareholder-spring-client").get(0).getId();
		// k.realm(REALM).clients().get(id).
		String userSessionId = k.realm(REALM).clients().get(id).getUserSessions(null, null).get(0).getId();
		k.realm(REALM).deleteSession(userSessionId);
		log.error("user session list---------------->" + userSessionId);
		k.tokenManager().invalidate(token);
	}

}
