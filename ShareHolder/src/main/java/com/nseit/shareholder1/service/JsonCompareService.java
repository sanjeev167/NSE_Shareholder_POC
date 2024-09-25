package com.nseit.shareholder1.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonPatch;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nseit.shareholder1.dao.ShareTransferDAO;
import com.nseit.shareholder1.metadatamodel.MetadataPojo;
import com.nseit.shareholder1.model.DashboardSearch;
import com.nseit.shareholder1.model.ShareTransferMaster;
import com.nseit.shareholder1.model.ShareTransferRoleType;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JsonCompareService {
	@Autowired
	ShareTransferDAO shareTransferDAO;
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	ResponseUtil response;
	public ResponseEntity<?> jsonCompare(Long uuid,String version){
		try {
//			CriteriaBuilder cb=entityManager.getCriteriaBuilder();
//			CriteriaQuery<ShareTransferMaster> cq=cb.createQuery(ShareTransferMaster.class);
//			Root<ShareTransferMaster> root=cq.from(ShareTransferMaster.class);
////			List<Predicate> predicates = new ArrayList<>();
////			cq=cq.multiselect(root.get("id"),root.get("metadata"));
//			cq.select(root);
////			CriteriaQuery<Object> cq2 = cb.createQuery();
//			Subquery<Long> subquery=cq.subquery(Long.class);
//			
//			 Root<ShareTransferRoleType> subroot=cq.from(ShareTransferRoleType.class);
//			 
//			 cq.where(cb.equal(subroot.get("uuid"), uuid)).where(cb.equal(root.get("id"),subroot.get("shareTransferId")));
////			    cq.where(cb.in(root.get("id")).value(cq));  
//			cq.orderBy(cb.desc(root.get("id")));
//			Query typedQuery = entityManager.createNativeQuery("select metadata from Share_transfer_master where id in (select sharetransfer_id from share_transfer_workflow where uuid=:uuid) order by id desc fetch first 2 rows only", ShareTransferMaster.class);
////			typedQuery.setFirstResult(0);
//			typedQuery.setParameter("uuid", uuid);
////			typedQuery.setMaxResults(2);
//			List<ShareTransferMaster> response1=typedQuery.getResultList();
//			System.out.println("len-----------------"+response1.size());
			
			
			
		ShareTransferMaster response1 = shareTransferDAO.getMetadataDetails(uuid);
		ShareTransferMaster response2 = shareTransferDAO.getpreviousMetadataDetails(uuid);
		if(response2!=null) {
		MetadataPojo json1 = response1.getMetadata();
		MetadataPojo json2 = response2.getMetadata();
        System.out.println("value of json1-------"+json1);
        System.out.println("value of json1 with string-------"+json1.toString());
		ObjectMapper m=new ObjectMapper();
		String latestJson = m.writeValueAsString(json1);
		String previousJson = m.writeValueAsString(json2);
		JsonValue source = Json.createReader(new StringReader(latestJson)).readValue();
		JsonValue target = Json.createReader(new StringReader(previousJson)).readValue();
		
		JsonPatch diff = Json.createDiff(source.asJsonObject(), target.asJsonObject());
		String value=diff.toString();
		System.out.println("----------------------------------");
		System.out.println(diff);
		return response.getAuthResponse("SUCCESS", HttpStatus.OK, value, version);
//		return ResponseEntity.ok("success");
		}
		else{
			return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
		}}
		catch(Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
}
