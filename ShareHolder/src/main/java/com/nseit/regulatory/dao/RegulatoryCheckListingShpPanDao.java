package com.nseit.regulatory.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.regulatory.model.RegulatoryCheckUpListingShpPan;
@Qualifier("integrationDataSource")
public interface RegulatoryCheckListingShpPanDao extends JpaRepository<RegulatoryCheckUpListingShpPan, String> {
	
	//@Query(value = "select * from REGULATORY_CHECK_VW_SHP_PAN_DATA_FOR_MEMBERSHIP where SPDFM_PAN_NO in (select json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.panOfBuyer') as BuyerPan from share_transfer_master@sharerepodbnew where ID in (select MAX(ID) from SHARE_TRANSFER_MASTER@sharerepodbnew where UUID=:uuid group by UUID))", nativeQuery = true)
	@Query(value = "select * from REGULATORY_CHECK_VW_SHP_PAN_DATA_FOR_MEMBERSHIP where SPDFM_PAN_NO  in (:totalList)", nativeQuery = true)
	public List<RegulatoryCheckUpListingShpPan> getListingComplianceDataList(List<String> totalList);
}
