package com.nseit.shareholder1.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ResourcePermissionRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
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
public class KeycloakRoleMappingService {
	@Value("${keycloak.realm}")
	private String REALM;

	@Autowired
	ResponseUtil responseUtil;

	@Value("${keycloak.resource}")
	private String RESOURCE;

	@Autowired
	KeycloakClientAuthExample keycloakClientAuthExample;

	public ResponseEntity<?> resourceList(String version) {
		try {
			List<ResourceRepresentation> resourceList;
			List<String> permissionAllList = new ArrayList<String>();
			List<String> resourcepolicyNewList = new ArrayList<String>();
			// List<Object> responseObject = new ArrayList<Object>();
			// Map<String, Object> resourceMap = new HashMap<String, Object>();
			Map<String, Map> permissionMap = new HashMap<String, Map>();

			List<ResourcePermissionRepresentation> al = new ArrayList<ResourcePermissionRepresentation>();
			// List<PolicyRepresentation> al1 = new ArrayList<PolicyRepresentation>();

			Keycloak k = keycloakClientAuthExample.newKeycloakWithClientCredentials();
			// RealmRepresentation r = k.realm(REALM).toRepresentation();
			String id = k.realm(REALM).clients().findByClientId(RESOURCE).get(0).getId();
			System.out.println("id----------------->" + id);
			resourceList = k.realm(REALM).clients().get(id).authorization().resources().resources();

			System.out.println("resourceAllList-------->" + resourceList);

			for (ResourceRepresentation r1 : resourceList) {
				String displayName = r1.getName();
				System.out.println("displayName----------->" + displayName);
				System.out.println("displayName----------->" + displayName);
				if (displayName.equalsIgnoreCase("default Resource"))
					continue;
				ResourcePermissionRepresentation permissionList = k.realm(REALM).clients().get(id).authorization()
						.permissions().resource().findByName(displayName + "-access");

				al.add(permissionList);
				permissionAllList = al.stream().map(x -> x.getName()).collect(Collectors.toList());
				List<String> listed=new ArrayList<String>();
				String[] newPermission=null;
				for(String p:permissionAllList) {
					 newPermission=p.split("-");
					
				}
				for(String p1:newPermission) {
					listed.add(p1);
				}
				 System.out.println("listed----------------->"+listed);

				System.out.println("permissionAllList-------------------->" + permissionAllList);

				Map<String, Object> policyMap = new HashMap<String, Object>();
				

				List<PolicyRepresentation> resourcepolicyList = k.realm(REALM).clients().get(id).authorization()
						.permissions().resource().findById(permissionList.getId()).associatedPolicies();
				System.out.println("resourcepolicyList-------------->"+resourcepolicyList);
				for (PolicyRepresentation policy : resourcepolicyList) {
System.out.println("policy--------------->"+policy);
					policyMap.put(policy.getName().substring(0, policy.getName().indexOf("-")), true);

				}
				if (displayName.contains("-")) {
					//permissionMap.put(displayName.substring(0, r1.getName().indexOf("-")), policyMap);
					if(permissionMap.get(listed.get(0))==null) {
						permissionMap.put(listed.get(0).toString(), new HashMap<String, Object>());
					}
					if(permissionMap.get(listed.get(0)).get(listed.get(1))==null) {
						
						permissionMap.get(listed.get(0).toString()).put(listed.get(1).toString(), policyMap);
					}
					//permissionMap.get(listed1.get(0)).get(listed1.get(1).toString().pu);
					//permissionMap.put(listed1.get(1), policyMap);

					System.out.println("resourcepolicyNewList---------------->" + resourcepolicyNewList);

				} else {
					permissionMap.put(displayName, policyMap);
				}
			}
			System.out.println("resourceAllList-------->" + resourceList);
			System.out.println("permissionAllList-------->" + permissionAllList);
			System.out.println("resourcepolicyNewList-------->" + resourcepolicyNewList);

			return responseUtil.getAuthResponse("SUCCESS", HttpStatus.OK, permissionMap, null);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("In resourceList INTERNAL_SERVER_ERROR----------------------------------------");
			return responseUtil.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, null);
		}

	}
}
