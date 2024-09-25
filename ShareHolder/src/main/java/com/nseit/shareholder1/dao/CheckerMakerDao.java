package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.CheckerMakerModel;

public interface CheckerMakerDao extends JpaRepository<CheckerMakerModel, Long> {
	
	@Query(value="select * from CHECKER_MAKER_MASTER where ID in (select max(id) from CHECKER_MAKER_MASTER group by menus )order by CREATED_ON desc", nativeQuery=true)
	public List<CheckerMakerModel> getAllCheckerMakerList();
	
	@Query(value="select * from (select * from CHECKER_MAKER_MASTER where MENUS=:menus order by CREATED_ON desc) T where ROWNUM=1" , nativeQuery = true)
	public CheckerMakerModel getCheckerMakerData(String menus);

	
	
}
