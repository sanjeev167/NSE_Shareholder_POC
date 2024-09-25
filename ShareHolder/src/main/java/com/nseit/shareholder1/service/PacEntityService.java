package com.nseit.shareholder1.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.dao.PacEntityDAO;
import com.nseit.shareholder1.dao.PacGroupMappingDAO;
import com.nseit.shareholder1.model.NseUserModel;
import com.nseit.shareholder1.model.PacEntityModel;
import com.nseit.shareholder1.model.PacGroupMappingModel;
import com.nseit.shareholder1.modelInterfaces.PacEntityInterface;
import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PacEntityService {

	@Autowired
	PacEntityDAO pacEntityDAO;

	@Autowired
	PacGroupMappingDAO pacGroupMappingDAO;

	@Autowired
	ResponseUtil response;

	@Autowired
	JwtUtil jwt;

//	public ResponseEntity<?> addEntity(String version, PacEntityModel pacEntityModel) {
//		// TODO Auto-generated method stub
//		try {
//			PacEntityModel pacEntity = pacEntityDAO.getEntity(pacEntityModel.getName());
//			PacGroupMappingModel pacEntityGroup = pacGroupMappingDAO
//					.getEntityBasedOnGroupId(pacEntityModel.getGroupId());
//
//			if (pacEntityGroup == null) {
//				return response.getAuthResponse("GROUP_NOT_EXIST", HttpStatus.OK, null, version);
//			}
//			if (pacEntityGroup.getAccept() != null) {
//				return response.getAuthResponse("ENTITY_NOT_ALLOWED", HttpStatus.OK, null, version);
//			}
//			if (pacEntity == null) {
//				pacEntityModel.setCreatedby(jwt.extractUsername());
//				pacEntityDAO.save(pacEntityModel);
//
//				if (pacEntityGroup.getPacEntityModel() == null && pacEntityGroup.getActive() == null) {
//					List<PacEntityModel> listId = new ArrayList<PacEntityModel>();
//					listId.add(pacEntityModel);
//					pacEntityGroup.setPacEntityModel(listId);
//					pacGroupMappingDAO.save(pacEntityGroup);
//				} else {
//					pacEntityGroup.getPacEntityModel().add(pacEntityModel);
//					pacGroupMappingDAO.save(pacEntityGroup);
//				}
////				if(pacEntityGroup==null) {
////					PacGroupMappingModel pacGroupMappingModel = new PacGroupMappingModel();
//////					pacGroupMappingModel.setPacEntityModel(listId);
////					pacGroupMappingDAO.save(pacGroupMappingModel);
////				}
//
//				return response.getAuthResponse("SUCCESS", HttpStatus.OK, pacEntityModel, version);
//			} else {
//				return response.getAuthResponse("ALREADY_ENTITY_EXIST", HttpStatus.OK, null, version);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
//		}
//	}
//
//	public ResponseEntity<?> addGroup(String version) {
//		// TODO Auto-generated method stub
//		// need to update this method by passing list of entity and run the for loop and
//		// save all the entity one by one.
//		try {
//			String maxGroup = pacGroupMappingDAO.getMaxGroup();
//			PacGroupMappingModel pacGroupMappingModel = new PacGroupMappingModel();
//			if (maxGroup == null) {
//				maxGroup = "0";
//			}
//			pacGroupMappingModel.setProposedChange("Y");
//			pacGroupMappingModel.setCreatedBy(jwt.extractUsername());
//			PacGroupMappingModel pg = pacGroupMappingDAO.save(pacGroupMappingModel);
//
//			pg.setGroupName(String.valueOf(Integer.parseInt(maxGroup) + 1));
//
//			pacGroupMappingDAO.save(pg);
//
//			return response.getAuthResponse("SUCCESS", HttpStatus.OK, pg, version);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
//		}
//	}

	public ResponseEntity<?> addEntityList(String version, List<PacEntityModel> pacEntityModel) {
		try {
			List<PacEntityModel> pacEntityModel1=new ArrayList<PacEntityModel>();
			String maxGroup = pacGroupMappingDAO.getMaxGroup();
			PacGroupMappingModel pacGroupMappingModel = new PacGroupMappingModel();
			if (maxGroup == null) {
				maxGroup = "0";
			}
			pacGroupMappingModel.setProposedChange("Y");
			pacGroupMappingModel.setCreatedBy(jwt.extractUsername());


			pacGroupMappingModel.setGroupName(String.valueOf(Integer.parseInt(maxGroup) + 1));

			pacGroupMappingDAO.save(pacGroupMappingModel);
			if (pacEntityModel.size() > 0) {
				
				for (PacEntityModel p : pacEntityModel) {
					PacEntityModel pacEntity = pacEntityDAO.getEntity(p.getName());
					if (pacEntity != null) {
						log.error("In addEntityList ALREADY_ENTITY_EXIST11111111-----------------------------------");
						return response.getAuthResponse("ALREADY_ENTITY_EXIST", HttpStatus.BAD_REQUEST, null, version);
					}
				}
				for (PacEntityModel p : pacEntityModel) {

					PacEntityModel pacEntity = pacEntityDAO.getEntity(p.getName());
					if (pacEntity != null) {
						log.error("In addEntityList ALREADY_ENTITY_EXIST2222222222222222-----------------------------------");
						return response.getAuthResponse("ALREADY_ENTITY_EXIST", HttpStatus.BAD_REQUEST, null, version);
					} else {
						p.setCreatedby(jwt.extractUsername());
						p.setGroupId(pacGroupMappingModel.getGroupName());
						PacEntityModel pacEntityModel2=pacEntityDAO.save(p);
						pacEntityModel1.add(pacEntityModel2);
					}

				}
				
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, pacEntityModel1, version);
			} else {
				log.error("In addEntityList NO_DATA-----------------------------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In addEntityList INTERNAL_SERVER_ERROR-----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> proposedChange(String version, String groupId, String proposedChange) {

		try {
			PacGroupMappingModel pacGroupId = pacGroupMappingDAO.proposedChange(groupId);

			if (pacGroupId != null) {
				if (pacGroupId.getActive().equalsIgnoreCase(proposedChange)) {
					return response.getAuthResponse("PROPOSED_CHANGE_EXIST", HttpStatus.OK, null, version);
				}
				PacGroupMappingModel pacGroupMappingModel1 = (PacGroupMappingModel) pacGroupId.clone();
				pacGroupMappingModel1.setId(null);
				pacGroupMappingModel1.setAccept(null);
				pacGroupMappingModel1.setProposedChange(proposedChange);
				pacGroupMappingModel1.setPacEntityModel(null);
				pacGroupMappingDAO.save(pacGroupMappingModel1);

//				for(PacEntityModel pe : pacGroupId.getPacEntityModel()) {
//					PacEntityModel pacEntityNew = (PacEntityModel)pe.clone();
//					pacEntityNew.setId(null);
//					pacEntityNew.setGroupId(p.getGroupName());
//					pacEntityDAO.save(pacEntityNew);
//				}
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, pacGroupMappingModel1, version);
			} else {
				log.error("In proposedChange GROUP_NOT_EXIST-----------------------------------");
				return response.getAuthResponse("GROUP_NOT_EXIST", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In proposedChange INTERNAL_SERVER_ERROR-----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> isAccept(String version, String groupId, String accept) {

		PacGroupMappingModel pacGroupId = pacGroupMappingDAO.isAccept(groupId);
		try {
			if (pacGroupId != null) {
				if (accept.equals("Y")) {
//					PacGroupMappingModel pacGroupMappingModel1 = (PacGroupMappingModel) pacGroupId.clone();
//					pacGroupMappingModel1.setId(null);
//					pacGroupMappingModel1.setAccept(accept);
//					pacGroupMappingModel1.setActive(pacGroupMappingModel1.getProposedChange());
//					pacGroupMappingModel1.setPacEntityModel(null);
					pacGroupId.setAccept(accept);
					Timestamp instant = Timestamp.from(Instant.now());
					pacGroupId.setModifiedOn(instant); 
					pacGroupMappingDAO.save(pacGroupId);

//					for(PacEntityModel pe : pacGroupId.getPacEntityModel()) {
//						PacEntityModel pacEntityNew = (PacEntityModel)pe.clone();
//						pacEntityNew.setId(null);
//						pacEntityNew.setGroupId(p.getGroupName());
//						pacEntityDAO.save(pacEntityNew);
//					}
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, pacGroupId, version);
				} else {
					pacGroupId.setAccept("N");
					Timestamp instant = Timestamp.from(Instant.now());
					pacGroupId.setModifiedOn(instant); 
					pacGroupMappingDAO.save(pacGroupId);
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, pacGroupId, version);
				}
			} else {
				log.error("In isAccept GROUP_NOT_EXIST-----------------------------------");
				return response.getAuthResponse("GROUP_NOT_EXIST", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In isAccept INTERNAL_SERVER_ERROR-----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getCurrentUser(String version) {
		try {
			List<PacEntityInterface> pacEntityModelList = pacEntityDAO.getCurrentUser();
			if (pacEntityModelList != null && pacEntityModelList.size() > 0) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, pacEntityModelList, version);
			} else {
				log.error("In getCurrentUser ALREADY_ENTITY_EXIST-----------------------------------");
				return response.getAuthResponse("ALREADY_ENTITY_EXIST", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In getCurrentUser INTERNAL_SERVER_ERROR-----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> getUserBasedOnAccept(String version) {
		try {
			List<PacEntityInterface> pacEntityModelList = pacEntityDAO.getUserBasedOnAccept();
			if (pacEntityModelList.size() > 0) {
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, pacEntityModelList, version);
			} else {
				log.error("In getUserBasedOnAccept ALREADY_ENTITY_EXIST-----------------------------------");
				return response.getAuthResponse("ALREADY_ENTITY_EXIST", HttpStatus.OK, null, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("In getUserBasedOnAccept INTERNAL_SERVER_ERROR-----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

}
