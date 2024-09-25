package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.NseUserModel;
import com.nseit.shareholder1.model.ShareHoldingLimitMaster;

public interface ShareHoldingLimitDao extends JpaRepository<ShareHoldingLimitMaster, Long> {

	@Query(value = "SELECT * from SHAREHOLDING_LIMIT_MASTER where id in(SELECT MAX(id) from SHAREHOLDING_LIMIT_MASTER where  ACTIVE is not null  group by CATEGORY)", nativeQuery = true)

	public List<ShareHoldingLimitMaster> getCurrentHoldingLimit();

	@Query(value = "SELECT * from SHAREHOLDING_LIMIT_MASTER where id in(SELECT MAX(id) from SHAREHOLDING_LIMIT_MASTER   group by CATEGORY) and ACCEPT is  null", nativeQuery = true)
	public List<ShareHoldingLimitMaster> getPendingLimit();

	@Query(value = "select * from(select * from SHAREHOLDING_LIMIT_MASTER WHERE  id in(SELECT MAX(id) from SHAREHOLDING_LIMIT_MASTER where CATEGORY=:category and ACCEPT is null   group by CATEGORY) order by  CREATED_ON desc) T where ROWNUM=1", nativeQuery = true)
	public ShareHoldingLimitMaster getProposedLimit(@Param("category") String category);

	@Query(value = "select * from(select * from SHAREHOLDING_LIMIT_MASTER WHERE id in(SELECT MAX(id) from SHAREHOLDING_LIMIT_MASTER where CATEGORY=:category and ACCEPT='Y'  group by CATEGORY) order by  CREATED_ON desc) T where ROWNUM=1", nativeQuery = true)
	public ShareHoldingLimitMaster getAcceptedShareHoldingLimit(@Param("category") String category);

}
