package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.CagModel;
import com.nseit.shareholder1.modelInterfaces.Mimps;

public interface CagDAO extends JpaRepository<CagModel, Long>{

	@Query(value = "select c.shareholder_name as name, bm.shares as shares, trunc(100*(bm.shares/(select * from total_shares)),2) as percentageShare from cag_master c INNER JOIN\r\n"
			+ "benpose_data_master bm on c.client_id = bm.client_id", nativeQuery = true)
	public List<Mimps> getCag();
	
	@Query(value = "select * from cag_master where client_id=:clientId", nativeQuery = true)
	public CagModel isExist(String clientId);
}
