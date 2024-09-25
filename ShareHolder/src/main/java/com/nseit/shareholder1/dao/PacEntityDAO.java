package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.PacEntityModel;
import com.nseit.shareholder1.modelInterfaces.PacEntityInterface;
import com.nseit.shareholder1.modelInterfaces.PacReportInterface;

public interface PacEntityDAO extends JpaRepository<PacEntityModel, Long> {

	@Query(value = "select * from (select * from PAC_ENTITY where NAME=:name and GROUP_ID in (select ID from PAC_GROUP_MAPPING where ACTIVE='Y' and ACCEPT='Y') order by CREATED_ON desc) T where ROWNUM=1", nativeQuery = true)
	public PacEntityModel getEntity(String name);
	
	@Query(value = "select distinct pe.id,name,percentage_holding percentageHolding,pe.created_on peCreatedOn,pe.group_id group_id,GROUP_NAME groupName,accept,active,proposed_change proposedChange,pg.created_by createdBy,pg.created_on  pgCreatedOn from PAC_ENTITY pe INNER JOIN PAC_GROUP_MAPPING pg on pe.group_id=pg.group_name where pg.active is not null and pg.id in (select MAX(id) from PAC_GROUP_MAPPING group by group_name) order by pe.group_id", nativeQuery = true)
	public List<PacEntityInterface> getCurrentUser();
	
	@Query(value = "select distinct pe.id,name,percentage_holding percentageHolding,pe.created_on peCreatedOn,pe.group_id group_id,GROUP_NAME groupName,accept,active,proposed_change proposedChange,pg.created_by createdBy,pg.created_on  pgCreatedOn from PAC_ENTITY pe INNER JOIN PAC_GROUP_MAPPING pg on pe.group_id=pg.group_name where pg.accept is null and pg.id in (select MAX(id) from PAC_GROUP_MAPPING group by group_name) order by pe.group_id", nativeQuery = true)
	public List<PacEntityInterface> getUserBasedOnAccept();
	@Query(value = "select GROUP_ID as groupId,pe.NAME as name,bm.shares as shares, trunc(100*((bm.shares)/(select * from total_shares)),2) as percentageShare, pe.category as category from pac_entity pe INNER JOIN BENPOSE_DATA_MASTER bm on pe.client_id = bm.client_id", nativeQuery = true)
	public List<PacReportInterface> getPacReport();
}
