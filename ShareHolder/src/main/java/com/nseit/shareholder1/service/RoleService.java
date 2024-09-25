package com.nseit.shareholder1.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.util.KeycloakClientAuthExample;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleService {
	@Value("${keycloak.realm}")
	private String REALM;

	@Autowired
	ResponseUtil responseUtil;

	@Value("${keycloak.resource}")
	private String RESOURCE;

	@Autowired
	KeycloakClientAuthExample keycloakClientAuthExample;

	public ResponseEntity<?> getAllRole(String version) {

		Keycloak k = keycloakClientAuthExample.newKeycloakWithClientCredentials();
		RealmRepresentation r = k.realm(REALM).toRepresentation();
		String id = k.realm(REALM).clients().findByClientId(RESOURCE).get(0).getId();
		log.error("In getAllRole id----------------->" + id);
		List<RoleRepresentation> roleList = k.realm(REALM).clients().get(id).roles().list(false);
		log.error("In getAllRole roleList--------------------->" + roleList);
		log.error("In getAllRole roleList--------------------->" + roleList.get(0));

		return responseUtil.getAuthResponse("SUCCESS", HttpStatus.OK, roleList, null);
	}

	public ResponseEntity<?> addRole(String version, String roleName) {
		try {
			Keycloak k = keycloakClientAuthExample.newKeycloakWithClientCredentials();
			RealmRepresentation r = k.realm(REALM).toRepresentation();
			String id = k.realm(REALM).clients().findByClientId(RESOURCE).get(0).getId();
			System.out.println("id----------------->" + id);
			ClientRepresentation client = k.realm(REALM).clients().findByClientId(RESOURCE).get(0);
			List<String> roleList = k.realm(REALM).clients().get(id).roles().list().stream().map(x -> x.getName())
					.collect(Collectors.toList());
			if (!roleList.contains(roleName)) {
				RoleRepresentation roleRepresentation = new RoleRepresentation();
				roleRepresentation.setName(roleName);
				roleRepresentation.setDescription("roleName   " + roleName);
				k.realm(REALM).clients().get(id).roles().create(roleRepresentation);
				return responseUtil.getAuthResponse("SUCCESS", HttpStatus.OK, roleName, null);
			} else {
				return responseUtil.getAuthResponse("ROLENAME_ALREADY_EXISTS", HttpStatus.FORBIDDEN, roleName, null);
			}
		} catch (Exception e) {
			log.error("In addRole In Exception INTERNAL_SERVER_ERROR--------");
			return responseUtil.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, roleName,
					null);
		}

		/*
		 * List<RoleRepresentation> rolesOfUserActual =
		 * k.realm("api").users().get("95315cf6-b10f-4b6c-a8ac-f60ca4820307").roles().
		 * realmLevel().listAll(); List<RoleRepresentation> rolesOfUserActualNew =
		 * k.realm("api").users().get("95315cf6-b10f-4b6c-a8ac-f60ca4820307").roles().
		 * realmLevel().listAll();
		 * 
		 * RoleRepresentation newrole = new RoleRepresentation("ROLE_READ_GROUPS", null,
		 * false); // this role already exists in keycloak.
		 * rolesOfUserActualNew.add(newrole);
		 * 
		 * 
		 * List<RoleRepresentation> differences = rolesOfUserActual.stream()
		 * .filter(name -> !rolesOfUserActualNew.contains(name))
		 * .collect(Collectors.toList());
		 * 
		 * 
		 * List<RoleRepresentation> roleToAdd = new ArrayList();
		 * List<RoleRepresentation> roleToDelete = new ArrayList();
		 * 
		 * 
		 * differences.forEach((role) -> { if(rolesOfUserActual.contains(role)) {
		 * roleToDelete.add(role); }else { roleToAdd.add(role); } });
		 * 
		 * k.realm("api").users().get("95315cf6-b10f-4b6c-a8ac-f60ca4820307").roles().
		 * realmLevel().add(roleToAdd); //
		 * k.realm("api").users().get("95315cf6-b10f-4b6c-a8ac-f60ca4820307").roles().
		 * realmLevel().remove(roleToDelete);
		 */
	}

}
