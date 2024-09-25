package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.ShareHoldingPattern;
import com.nseit.shareholder1.modelInterfaces.ShareHoldingPatternInterface;

public interface ShareHoldingPatternDao extends JpaRepository<ShareHoldingPattern, Long> {
	@Query(value = "select * from SHARE_HOLDING_PATTERN", nativeQuery = true)
	List<ShareHoldingPattern> checkValues();
	
	@Query(value = "select sub_category_two as subCategoryTwo, count(sr_no) as shareholderNo, sum(no_of_shares) as totalShares, trunc(100*(sum(no_of_shares)/(select * from total_shares)),2) as percentageShare from share_holding_pattern group by sub_category_two", nativeQuery = true)
	List<ShareHoldingPatternInterface> shareHoldingDistribution();

}
