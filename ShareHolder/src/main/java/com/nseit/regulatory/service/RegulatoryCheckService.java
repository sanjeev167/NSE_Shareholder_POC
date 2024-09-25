package com.nseit.regulatory.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nseit.regulatory.dao.RegulatoryCheckEmployeeDao;
import com.nseit.regulatory.dao.RegulatoryCheckHhidDao;
import com.nseit.regulatory.dao.RegulatoryCheckListingCgPanDao;
import com.nseit.regulatory.dao.RegulatoryCheckListingShpPanDao;
import com.nseit.regulatory.dao.RegulatoryCheckMemberDao;
import com.nseit.regulatory.dao.RegulatoryCheckSebiDebarredDao;
import com.nseit.regulatory.model.RegulatoryCheckSebiDebarred;
import com.nseit.regulatory.model.RegulatoryCheckUpEmployee;
import com.nseit.regulatory.model.RegulatoryCheckUpHhid;
import com.nseit.regulatory.model.RegulatoryCheckUpListingCGPan;
import com.nseit.regulatory.model.RegulatoryCheckUpListingShpPan;
import com.nseit.regulatory.model.RegulatoryCheckUpMember;
import com.nseit.regulatory.regInterface.ListingRegulatoryCheckInterface;
import com.nseit.shareholder1.dao.ShareTransferDAO;
import com.nseit.shareholder1.metadatamodel.CategoryDetails;
import com.nseit.shareholder1.metadatamodel.CategoryTable;
import com.nseit.shareholder1.modelInterfaces.CategoryTableInterface;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegulatoryCheckService {
	@Autowired
	RegulatoryCheckEmployeeDao regulatoryCheckEmployeeDao;

	@Autowired
	RegulatoryCheckHhidDao regulatoryChechHhidDao;

	@Autowired
	RegulatoryCheckListingCgPanDao regulatoryCheckListingCgPanDao;

	@Autowired
	RegulatoryCheckListingShpPanDao regulatoryCheckListingShpPanDao;

	@Autowired
	RegulatoryCheckMemberDao regulatoryCheckMemberDao;
	
	@Autowired
	RegulatoryCheckSebiDebarredDao regulatoryCheckSebiDebarredDao;

	@Autowired
	ResponseUtil response;
	
	@Autowired
	ShareTransferDAO shareTransferDAO;

	public ResponseEntity<?> getExchangeComplianceDataList(String version, Long uuid) {
		try {
//			Map<String, Object> regulatoryMap = new HashMap<String, Object>();
			List<String> panList = shareTransferDAO.getAllBuyerPan(uuid);
			
			List<String> catPanList = new ArrayList<String>();
			List<String> totalList = new ArrayList<String>();
			List<String> catList=shareTransferDAO.getAllCategoryPan(uuid);
		
		   
			for(String c:catList) {
				
				JSONArray json=new JSONArray(c);
				for(int i=0;i<json.length();i++) {
					String pan1=json.getJSONObject(i).getString("pan");
					catPanList.add(pan1);
					
				}
	
			}
			totalList.addAll(panList);
			totalList.addAll(catPanList);
			List<RegulatoryCheckUpHhid> regulatoryCheckHhidData = regulatoryChechHhidDao.getHhidDataList(totalList);
			List<RegulatoryCheckUpEmployee> regulatoryCheckData = regulatoryCheckEmployeeDao.getExchangeComplianceDataList(totalList);
			String hhid = null;

			if (regulatoryCheckData.size() != 0) {
				
					for(RegulatoryCheckUpEmployee r: regulatoryCheckData) {
						if(regulatoryCheckHhidData.stream().filter(x->x.getCcdPan().matches(r.getEmployeePan())).count()>0) {
							
							hhid = "Yes";
						}
						else {
							hhid = "No";

						}
						r.setHhid(hhid);
						
					}
					
				
				
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, regulatoryCheckData, version);

			} else {
				log.info("In getExchangeComplianceDataList NO_DATA------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In getExchangeComplianceDataList INTERNAL_SERVER_ERROR------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getHhidDataList(String version, Long uuid) {
		try {
List<String> panList = shareTransferDAO.getAllBuyerPan(uuid);
			
			List<String> catPanList = new ArrayList<String>();
			List<String> totalList = new ArrayList<String>();
			List<String> catList=shareTransferDAO.getAllCategoryPan(uuid);
		
		   
			for(String c:catList) {
				
				JSONArray json=new JSONArray(c);
				for(int i=0;i<json.length();i++) {
					String pan1=json.getJSONObject(i).getString("pan");
					catPanList.add(pan1);
					
				}
	
			}
			totalList.addAll(panList);
			totalList.addAll(catPanList);
			Map<String, Object> regulatoryMap = new HashMap<String, Object>();
			List<RegulatoryCheckUpHhid> regulatoryCheckData = regulatoryChechHhidDao.getHhidDataList(totalList);
			regulatoryMap.put("data", regulatoryCheckData);
			if (regulatoryCheckData.size() != 0) {

				return response.getAuthResponse("SUCCESS", HttpStatus.OK, regulatoryCheckData, version);
			} else {
				log.info("In getHhidDataList NO_DATA------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In getHhidDataList INTERNAL_SERVER_ERROR------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getListingComplianceCgDataList(String version, Long uuid) {
		try {
List<String> panList = shareTransferDAO.getAllBuyerPan(uuid);
			
			List<String> catPanList = new ArrayList<String>();
			List<String> totalList = new ArrayList<String>();
			List<String> catList=shareTransferDAO.getAllCategoryPan(uuid);
		
		   
			for(String c:catList) {
				
				JSONArray json=new JSONArray(c);
				for(int i=0;i<json.length();i++) {
					String pan1=json.getJSONObject(i).getString("pan");
					catPanList.add(pan1);
					
				}
	
			}
			totalList.addAll(panList);
			totalList.addAll(catPanList);
			Map<String, Object> regulatoryMap = new HashMap<String, Object>();
			List<ListingRegulatoryCheckInterface> regulatoryCheckData = regulatoryCheckListingCgPanDao
					.getListingComplianceDataList(totalList);
//			List<ListingRegulatoryCheckInterface> regulatoryCheckData1=new ArrayList<>();
//			//List<String> buyerPan = shareTransferDAO.getAllBuyerPan(uuid);
//			
			if (regulatoryCheckData.size() != 0 ) {
//				for(ListingRegulatoryCheckInterface r:regulatoryCheckData) {
//					if(totalList.contains(r.getPan())) {
//						regulatoryCheckData1.add(r);
//					}
//					
//				}
				regulatoryMap.put("data", regulatoryCheckData);
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, regulatoryCheckData, version);
			} else {
				log.info("In getListingComplianceCgDataList NO_DATA222222222222222------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In getListingComplianceCgDataList INTERNAL_SERVER_ERROR------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getListingComplianceShpDataList(String version, Long uuid) {
		try {
List<String> panList = shareTransferDAO.getAllBuyerPan(uuid);
			
			List<String> catPanList = new ArrayList<String>();
			List<String> totalList = new ArrayList<String>();
			List<String> catList=shareTransferDAO.getAllCategoryPan(uuid);
		
		   
			for(String c:catList) {
				
				JSONArray json=new JSONArray(c);
				for(int i=0;i<json.length();i++) {
					String pan1=json.getJSONObject(i).getString("pan");
					catPanList.add(pan1);
					
				}
	
			}
			totalList.addAll(panList);
			totalList.addAll(catPanList);
			Map<String, Object> regulatoryMap = new HashMap<String, Object>();
			List<RegulatoryCheckUpListingShpPan> regulatoryCheckData = regulatoryCheckListingShpPanDao
					.getListingComplianceDataList(totalList);
			regulatoryMap.put("data", regulatoryCheckData);
			if (regulatoryCheckData.size() != 0) {

				return response.getAuthResponse("SUCCESS", HttpStatus.OK, regulatoryMap, version);
			} else {
				log.info("In getListingComplianceShpDataList NO_DATA------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In getListingComplianceShpDataList INTERNAL_SERVER_ERROR------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getMembershipComplianceDataList(String version, Long uuid) {
		try {
			 List<String> panList = shareTransferDAO.getAllBuyerPan(uuid);
				
				List<String> catPanList = new ArrayList<String>();
				List<String> totalList = new ArrayList<String>();
				List<String> catList=shareTransferDAO.getAllCategoryPan(uuid);
			
			   
				for(String c:catList) {
					
					JSONArray json=new JSONArray(c);
					for(int i=0;i<json.length();i++) {
						String pan1=json.getJSONObject(i).getString("pan");
						catPanList.add(pan1);
						
					}
		
				}
				totalList.addAll(panList);
				totalList.addAll(catPanList);
				
			Map<String, Object> regulatoryMap = new HashMap<String, Object>();
			List<RegulatoryCheckUpMember> regulatoryCheckData = regulatoryCheckMemberDao
					.getMembershipComplianceDataList(totalList);
			regulatoryMap.put("data", regulatoryCheckData);
			if (regulatoryCheckData.size() != 0) {

				return response.getAuthResponse("SUCCESS", HttpStatus.OK, regulatoryCheckData, version);
			} else {
				log.info("In getMembershipComplianceDataList NO_DATA------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In getMembershipComplianceDataList INTERNAL_SERVER_ERROR------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
	
	public ResponseEntity<?> getSebiDebarredList(String version, Long uuid) {
		try {
List<String> panList = shareTransferDAO.getAllBuyerPan(uuid);
			
			List<String> catPanList = new ArrayList<String>();
			List<String> totalList = new ArrayList<String>();
			List<String> catList=shareTransferDAO.getAllCategoryPan(uuid);
		
		   
			for(String c:catList) {
				
				JSONArray json=new JSONArray(c);
				for(int i=0;i<json.length();i++) {
					String pan1=json.getJSONObject(i).getString("pan");
					catPanList.add(pan1);
					
				}
	
			}
			totalList.addAll(panList);
			totalList.addAll(catPanList);
			Map<String, Object> regulatoryMap = new HashMap<String, Object>();
			List<RegulatoryCheckSebiDebarred> regulatoryCheckData = regulatoryCheckSebiDebarredDao.getSebiDebarredList(totalList);
			regulatoryMap.put("data", regulatoryCheckData);
			if (regulatoryCheckData.size() != 0) {

				return response.getAuthResponse("SUCCESS", HttpStatus.OK, regulatoryCheckData, version);
			} else {
				log.info("In getSebiDebarredList NO_DATA------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In getSebiDebarredList INTERNAL_SERVER_ERROR------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

}
