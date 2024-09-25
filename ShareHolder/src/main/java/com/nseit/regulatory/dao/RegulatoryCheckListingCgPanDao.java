package com.nseit.regulatory.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.regulatory.model.RegulatoryCheckUpListingCGPan;
import com.nseit.regulatory.regInterface.ListingRegulatoryCheckInterface;
@Qualifier("integrationDataSource")
public interface RegulatoryCheckListingCgPanDao extends JpaRepository<RegulatoryCheckUpListingCGPan, String>{

//	@Query(value = "select * from REGULATORY_CHECK_VW_CG_PAN_DATA_FOR_MEMBERSHIP where CPDFM_PAN in (select json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.panOfBuyer') as BuyerPan from share_transfer_master@sharerepodbnew where ID in (select MAX(ID) from SHARE_TRANSFER_MASTER@sharerepodbnew where UUID=:uuid group by UUID))", nativeQuery = true)
	@Query(value="select * from (select CPDFM_NAME AS Name,CPDFM_PAN AS Pan, CPDFM_CMPY_NAME AS CompanyName from regulatory_check_vw_cg_pan_data_for_membership \r\n"
			+ "union \r\n"
			+ "select SPDFM_SHAREHOLDER_NAME AS Name ,SPDFM_PAN_NO AS Pan,SPDFM_CMPY_NAME AS CompanyName  from regulatory_check_vw_shp_pan_data_for_membership) cg where cg.Pan in(:totalList) ", nativeQuery = true)
	public List<ListingRegulatoryCheckInterface> getListingComplianceDataList(List<String> totalList);

}
