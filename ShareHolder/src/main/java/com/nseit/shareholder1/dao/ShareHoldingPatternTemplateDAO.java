package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.ShareHoldingPatternTemplate;
import com.nseit.shareholder1.modelInterfaces.ShareHoldingDistributionPatternTemplateInterface;

public interface ShareHoldingPatternTemplateDAO extends JpaRepository<ShareHoldingPatternTemplate, Long>{

	@Query(value = "    SELECT\r\n"
			+ "    buyerTmCm,categoryOfBuyer,subCategory  ,buyerNriType,corpListed,\r\n"
			+ "    COUNT(buyername)      AS shareholderno,\r\n"
			+ "    SUM(noofshares) AS totalshares,\r\n"
			+ "    trunc(100 *(SUM(noofshares) /(\r\n"
			+ "        SELECT\r\n"
			+ "            *\r\n"
			+ "        FROM\r\n"
			+ "            total_shares\r\n"
			+ "    )), 2)            AS percentageShare\r\n"
			+ "FROM\r\n"
			+ "    share_holding_pattern_template\r\n"
			+ "GROUP BY\r\n"
			+ "    BUYERTMCM,categoryofbuyer,buyerNriType,subcategory,corpListed", nativeQuery = true)
	List<ShareHoldingDistributionPatternTemplateInterface> getShareHoldingDistribution();
	
	@Query(value = "    SELECT\r\n"
			+ "    BuyerTmCm,categoryOfBuyer,subCategory  ,buyerNriType,corpListed,\r\n"
			+ "    COUNT(buyername)      AS shareholderno,\r\n"
			+ "    SUM(noofshares) AS totalshares,\r\n"
			+ "    trunc(100 *(SUM(noofshares) /(\r\n"
			+ "        SELECT\r\n"
			+ "            *\r\n"
			+ "        FROM\r\n"
			+ "            total_shares\r\n"
			+ "    )), 2)            AS percentageShare\r\n"
			+ "FROM\r\n"
			+ "    share_holding_pattern_template where buyerTmCm = 'TM/CM'\r\n"
			+ "GROUP BY\r\n"
			+ "    BUYERTMCM,categoryofbuyer,buyerNriType,subcategory,corpListed", nativeQuery = true)
	List<ShareHoldingDistributionPatternTemplateInterface> getShareHoldingDistributionTM();
	
	@Query(value = "    SELECT\r\n"
			+ "    BuyerTmCm,categoryOfBuyer,subCategory  ,buyerNriType,corpListed,\r\n"
			+ "    COUNT(buyername)      AS shareholderno,\r\n"
			+ "    SUM(noofshares) AS totalshares,\r\n"
			+ "    trunc(100 *(SUM(noofshares) /(\r\n"
			+ "        SELECT\r\n"
			+ "            *\r\n"
			+ "        FROM\r\n"
			+ "            total_shares\r\n"
			+ "    )), 2)            AS percentageShare\r\n"
			+ "FROM\r\n"
			+ "    share_holding_pattern_template where buyerTmCm = 'public'\r\n"
			+ "GROUP BY\r\n"
			+ "    BUYERTMCM,categoryofbuyer,buyerNriType,subcategory,corpListed", nativeQuery = true)
	List<ShareHoldingDistributionPatternTemplateInterface> getShareHoldingDistributionPublic();
	
	@Query(value = "    SELECT\r\n"
			+ "    BuyerTmCm,categoryOfBuyer,subCategory  ,buyerNriType,corpListed,\r\n"
			+ "    COUNT(buyername)      AS shareholderno,\r\n"
			+ "    SUM(noofshares) AS totalshares,\r\n"
			+ "    trunc(100 *(SUM(noofshares) /(\r\n"
			+ "        SELECT\r\n"
			+ "            *\r\n"
			+ "        FROM\r\n"
			+ "            total_shares\r\n"
			+ "    )), 2)            AS percentageShare\r\n"
			+ "FROM\r\n"
			+ "    share_holding_pattern_template where buyerTmCm = 'associateTM/CM'\r\n"
			+ "GROUP BY\r\n"
			+ "    BUYERTMCM,categoryofbuyer,buyerNriType,subcategory,corpListed", nativeQuery = true)
	List<ShareHoldingDistributionPatternTemplateInterface> getShareHoldingDistributionAssociateTmCm();
}
