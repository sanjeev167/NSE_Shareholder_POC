package com.nseit.regulatory.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.regulatory.model.RegulatoryCheckUpHhid;
@Qualifier("integrationDataSource")
public interface RegulatoryCheckHhidDao extends JpaRepository<RegulatoryCheckUpHhid, Long> {
	//@Query(value = "select * from REGULATORY_MATCHES_HHID where CCD_PANNO in (select json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.panOfBuyer') as BuyerPan from share_transfer_master@sharerepodbnew where ID in (select MAX(ID) from SHARE_TRANSFER_MASTER@sharerepodbnew where UUID=:uuid group by UUID))", nativeQuery = true)
	@Query(value = "select * from REGULATORY_MATCHES_HHID where CCD_PANNO  in (:totalList)", nativeQuery = true)
	public List<RegulatoryCheckUpHhid> getHhidDataList(List<String> totalList);
}
