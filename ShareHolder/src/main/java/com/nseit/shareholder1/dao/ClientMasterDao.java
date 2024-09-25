package com.nseit.shareholder1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.BenposeDataMasterModel;

public interface ClientMasterDao extends JpaRepository<BenposeDataMasterModel, String>{
	
	@Query(value="select * from BENPOSE_DATA_MASTER  where CLIENT_ID=:clientId",nativeQuery=true)
	public BenposeDataMasterModel getClientDetails(String clientId);
    
	


	

	
}
