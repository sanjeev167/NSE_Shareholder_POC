package com.nseit.shareholder1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.PacEntityModel;
import com.nseit.shareholder1.model.PacGroupMappingModel;

public interface PacGroupMappingDAO extends JpaRepository<PacGroupMappingModel, Long> {

	@Query(value = "select * from (select * from PAC_GROUP_MAPPING where GROUP_NAME=:groupId order by CREATED_ON desc) T where ROWNUM=1", nativeQuery = true)
	public PacGroupMappingModel getEntityBasedOnGroupId(String groupId);

	@Query(value = "select MAX(group_name) from PAC_GROUP_MAPPING", nativeQuery = true)
	public String getMaxGroup();

	@Query(value = "SELECT * from(SELECT * from pac_group_mapping where GROUP_NAME =:groupId and accept = 'Y' order by created_on desc)T where ROWNUM = 1", nativeQuery = true)
	public PacGroupMappingModel proposedChange(String groupId);

	@Query(value = "SELECT * from(SELECT * from pac_group_mapping where GROUP_NAME =:groupId and accept is null order by created_on desc)T where ROWNUM = 1", nativeQuery = true)
	public PacGroupMappingModel isAccept(String groupId);
}
