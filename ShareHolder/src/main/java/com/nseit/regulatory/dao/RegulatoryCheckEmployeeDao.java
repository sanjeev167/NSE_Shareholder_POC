package com.nseit.regulatory.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.regulatory.model.RegulatoryCheckUpEmployee;
@Qualifier("integrationDataSource")
public interface RegulatoryCheckEmployeeDao extends JpaRepository<RegulatoryCheckUpEmployee, Integer> {

	@Query(value = "select * from REGULATORY_CHECK_EMPLOYEE where EMPLOYEE_PAN  in (:totalList)", nativeQuery = true)
	public List<RegulatoryCheckUpEmployee> getExchangeComplianceDataList(List<String> totalList);
	
//	@Query(value="select EMPLOYEE_PAN from REGULATORY_CHECK_EMPLOYEE where ID in (select MAX(ID) from SHARE_TRANSFER_MASTER@sharerepodbnew where UUID=:uuid group by UUID)", nativeQuery = true)
//	public List<String> getRegPan(@Param("uuid") Long uuid);
}
