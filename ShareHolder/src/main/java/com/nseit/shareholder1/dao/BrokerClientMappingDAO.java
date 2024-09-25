package com.nseit.shareholder1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nseit.shareholder1.model.BrokerClientMapping;
import com.nseit.shareholder1.model.BrokerMaster;

public interface BrokerClientMappingDAO extends JpaRepository<BrokerClientMapping, Long>{
      
	
}
