package com.nseit.regulatory.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.regulatory.model.RegulatoryCheckSebiDebarred;
import com.nseit.regulatory.model.RegulatoryCheckUpEmployee;

public interface RegulatoryCheckSebiDebarredDao extends JpaRepository<RegulatoryCheckSebiDebarred, String> {
	
	//@Query(value = "select * from REGULATORY_CHECK_SEBI_DEBARRED where DEM_PAN_NO  in (select json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.panOfBuyer') as BuyerPan from share_transfer_master@sharerepodbnew where ID in (select MAX(ID) from SHARE_TRANSFER_MASTER@sharerepodbnew where UUID=:uuid group by UUID))", nativeQuery = true)
	@Query(value = "select * from REGULATORY_CHECK_SEBI_DEBARRED where DEM_PAN_NO  in (:totalList)", nativeQuery = true)
	public List<RegulatoryCheckSebiDebarred> getSebiDebarredList(List<String> totalList);

}
