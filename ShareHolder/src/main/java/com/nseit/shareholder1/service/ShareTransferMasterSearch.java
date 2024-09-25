package com.nseit.shareholder1.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.metadatamodel.MetadataPojo;
import com.nseit.shareholder1.model.BenposeDataMasterModel;
import com.nseit.shareholder1.model.DashboardSearch;
import com.nseit.shareholder1.model.ShareTransferMaster;
import com.nseit.shareholder1.model.ShareTransferSearchRequest;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class ShareTransferMasterSearch{
	
	@Autowired
//	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	ResponseUtil response;
	
	public ResponseEntity<?> searchShareTransferDetails(String version, ShareTransferSearchRequest shareTransferSearchRequest) {
		try {
		CriteriaBuilder cb=entityManager.getCriteriaBuilder();
		CriteriaQuery<DashboardSearch> cq=cb.createQuery(DashboardSearch.class);
//		CriteriaQuery<Tuple> cq=cb.createTupleQuery();
		Root<ShareTransferMaster> root=cq.from(ShareTransferMaster.class);
		List<Predicate> predicates = new ArrayList<>();
        Subquery<String> subquery1=cq.subquery(String.class);
	    
	    
	    Root<BenposeDataMasterModel> subroot1=subquery1.from(BenposeDataMasterModel.class);
	    subquery1.select(subroot1.get("pan")).where(cb.equal(root.get("sellerClientId"), subroot1.get("clientId")));
	    
		cq=cq.multiselect(root.get("id") ,root.get("sellerClientId"),root.get("sellerName"),root.get("buyerClientID"),root.get("buyerName"),root.get("pricePerShares"),root.get("noOfShares"),root.get("amount"),root.get("personConcert"),root.get("uuid"),root.get("createdBy"),root.get("status"),root.get("createdOn"),
				root.get("metadata").as(MetadataPojo.class),subquery1);
	   
		Boolean flag=false;
	    
	    if (shareTransferSearchRequest.getUuid() != null) {
	        predicates.add(cb.equal(root.get("uuid"), shareTransferSearchRequest.getUuid()));
	        flag=true;
	    }
	    
	    if (shareTransferSearchRequest.getBuyerClientID() != null) {
	        predicates.add(cb.like(root.get("buyerClientId"), "%"+shareTransferSearchRequest.getBuyerClientID()+"%"));
	        flag=true;
	    }
	    if (shareTransferSearchRequest.getBuyerName() != null) {
	        predicates.add(cb.like(root.get("buyerName"), "%"+shareTransferSearchRequest.getBuyerName() +"%"));
	        flag=true;
	    }
	    if (shareTransferSearchRequest.getSellerClientId() != null) {
	        predicates.add(cb.like(root.get("sellerClientId"), "%"+shareTransferSearchRequest.getSellerClientId() +"%"));
	        flag=true;
	    }
	    if (shareTransferSearchRequest.getSellerName() != null) {
	        predicates.add(cb.like(root.get("sellerName"), "%"+shareTransferSearchRequest.getSellerName() + "%"));
	        flag=true;
	    }
	    if (shareTransferSearchRequest.getStatus() != null) {
	        predicates.add(cb.equal(root.get("status"), shareTransferSearchRequest.getStatus()));
	        flag=true;
	    }
	    
	    if(flag==false) {
	    	log.info("In searchShareTransferDetails NO_DATA----------------------");
	    	return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, null);
	    }
	    
//	    predicates.add(c
	    
	    Subquery<Long> subquery=cq.subquery(Long.class);
//	    cq.multiselect(cb.max(root.get("id")));
	    
	    Root<ShareTransferMaster> subroot=subquery.from(ShareTransferMaster.class);
	    subquery.select(cb.max(subroot.get("id")));
	    subquery.groupBy(subroot.get("uuid"));
	    predicates.add(cb.in(root.get("id")).value(subquery));
		cq.where( predicates.toArray(new Predicate[predicates.size()]));
		cq.orderBy(cb.desc(root.get("id")));
		TypedQuery<DashboardSearch> typedQuery = entityManager.createQuery(cq);
		typedQuery.setFirstResult(shareTransferSearchRequest.getPageNumber()*shareTransferSearchRequest.getPageSize());
		typedQuery.setMaxResults(shareTransferSearchRequest.getPageSize());
		List<DashboardSearch> response1=typedQuery.getResultList();
		
		CriteriaQuery<Long> cq1=cb.createQuery(Long.class);
		Root<ShareTransferMaster> root1=cq1.from(ShareTransferMaster.class);
		cq1.select(cb.count(root1)).where(predicates.toArray(new Predicate[predicates.size()]));
		Long count=entityManager.createQuery(cq1).getSingleResult();
		Page<DashboardSearch> page=new PageImpl<DashboardSearch>(response1, PageRequest.of(shareTransferSearchRequest.getPageNumber(), shareTransferSearchRequest.getPageSize()), count);
		if(response1.size()==0) {
			return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, null);
		}
		
		log.info("In searchShareTransferDetails response1---------------->"+response1);
		return response.getAuthResponse("SUCCESS", HttpStatus.OK, page, null);}
		catch(Exception e) {
			e.printStackTrace();
			log.info("In searchShareTransferDetails in Exception INTERNAL_SERVER_ERROR----------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, null);
		}
		
	    
	
	}
}
