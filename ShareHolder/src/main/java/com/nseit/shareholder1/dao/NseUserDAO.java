package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.NseUserModel;

public interface NseUserDAO extends JpaRepository<NseUserModel, Long> {

	@Query(value = "select * from (select distinct * from NSE_USER nu where nu.USERNAME=:username and nu.ACCEPT='Y' and nu.ACTIVE='Y' order by  nu.CREATED_ON desc) T where ROWNUM=1", nativeQuery = true)
	public NseUserModel isNseUserExist(@Param("username") String username);
	
	@Query(value = "select * from (select * from (select distinct * from NSE_USER nu where nu.USERNAME=:username  order by  nu.CREATED_ON desc) T where ROWNUM=1) where ACCEPT is null or ACCEPT='Y'", nativeQuery = true)
	public NseUserModel addNseUser(@Param("username") String username);

	@Query(value = "select * from NSE_USER", nativeQuery = true)
	public List<NseUserModel> getAllNseUser();

	@Query(value = "SELECT * from nse_USER where id in(SELECT MAX(id) from NSE_USER where  ACTIVE is not null  group by username)", nativeQuery = true)
	public List<NseUserModel> getCurrentUser();

	@Query(value = "SELECT * from nse_USER where id in(SELECT MAX(id) from NSE_USER  group by username) and ACCEPT is null ", nativeQuery = true)
	public List<NseUserModel> getNseUserBasedOnAccept();

	// @Query(value = "select distinct * from NSE_USER where ACCEPT='Y' and ACTIVE
	// is not null order by CREATED_ON desc", nativeQuery = true)
	// public List<NseUserModel> getCurrentUser();

	// @Query(value = "select distinct * from NSE_USER where ACCEPT is null order by
	// CREATED_ON desc", nativeQuery = true)
	// public List<NseUserModel> getNseUserBasedOnAccept();

	@Query(value = "select * from(select * from NSE_USER where USERNAME=:username and ACCEPT='Y' and id in(SELECT MAX(id) from NSE_USER  group by username) order by  CREATED_ON desc) T where ROWNUM=1", nativeQuery = true)
	public NseUserModel getAcceptedUser(@Param("username") String username);

	@Query(value = "select * from(select * from NSE_USER where USERNAME=:username and ACCEPT is null and id in(SELECT MAX(id) from NSE_USER  group by username) order by  CREATED_ON desc) T where ROWNUM=1", nativeQuery = true)
	public NseUserModel getProposedUser(@Param("username") String username);
}
