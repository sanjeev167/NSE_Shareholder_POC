package com.nseit.shareholder1.service;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.dao.CheckerMakerDao;
import com.nseit.shareholder1.model.CheckerMakerModel;
import com.nseit.shareholder1.util.KeycloakClientAuthExample;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CheckerMakerService {

	@Autowired
	CheckerMakerDao checkerMakerDao;

	@Autowired
	ResponseUtil response;

	@Autowired
	KeycloakClientAuthExample keycloakClientAuthExample;

	@Value("${keycloak.realm}")
	private String REALM;

	@Value("${keycloak.resource}")
	private String resource;

	@Autowired
	ResponseUtil responseUtil;

	public ResponseEntity<?> getAllCheckerMakerList(String version) {
		try {

			List<CheckerMakerModel> checkerMakerDetails = checkerMakerDao.getAllCheckerMakerList();
			if (checkerMakerDetails.size() != 0) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, checkerMakerDetails, version);
			} else {
				log.error("In getAllCheckerMakerList NO_DATA----------------------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			return response.getAuthResponse("In getAllCheckerMakerList INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> addCheckerMakerList(String version, List<CheckerMakerModel> checkerMakerModel) {
		try {
			CheckerMakerModel checkerMakerclone=null;
			List<CheckerMakerModel> checkerMakerModelresponse=new ArrayList<CheckerMakerModel>(); 
			Keycloak k = keycloakClientAuthExample.newKeycloakWithClientCredentials();
			String id = k.realm(REALM).clients().findByClientId(resource).get(0).getId();
			List<RoleRepresentation> roleList = k.realm(REALM).clients().get(id).roles().list();
			List<String> role=new ArrayList<String>();
			for(RoleRepresentation r:roleList) {
				role.add(r.getName());
			}
			log.error("roleList value---------->" + roleList);
			if (checkerMakerModel.size() != 0) {
				for (CheckerMakerModel c : checkerMakerModel) {
					if (c.getApprover() != null && c.getChecker() != null && c.getMaker() != null) {
                        String approver=c.getApprover();
                        String checker=c.getChecker();
                        String maker=c.getMaker();
                        log.error("In addCheckerMakerList "+"Approver---- "+approver+" Checker "+checker+" Verifier "+maker);
                        
						if (role.contains(approver) && role.contains(checker)
								&& role.contains(maker)) {
							CheckerMakerModel checkerMakerModel1 = checkerMakerDao.getCheckerMakerData(c.getMenus());
							if (checkerMakerModel1 != null) {
								checkerMakerclone=(CheckerMakerModel) c.clone();
								checkerMakerDao.save(checkerMakerclone);
								checkerMakerModelresponse.add(checkerMakerclone);
//							return response.getAuthResponse("SUCCESS", HttpStatus.OK, checkerMakerModel1, version);
							} else {
								log.error("In addCheckerMakerList NO_DATA----------------------------------");
								return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
							}
						} else {
							log.error("In addCheckerMakerList INVALID_ROLE----------------------------------");
							return response.getAuthResponse("INVALID_ROLE", HttpStatus.FORBIDDEN, null, version);
						}
					} else {
						log.error("In addCheckerMakerList INVALID_ROLE----------------------------------");
						return response.getAuthResponse("INVALID_ROLE", HttpStatus.FORBIDDEN, null, version);
					}
				}
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, checkerMakerModelresponse, version);
			} else {
				log.error("In addCheckerMakerList INTERNAL_SERVER_ERROR----------------------------------");
				return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null,
						version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In addCheckerMakerList INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

}
