package com.nseit.shareholder1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.User;
import com.nseit.shareholder1.model.UserMaster;

public interface UserMasterDao extends JpaRepository<UserMaster, Long>{
	

	
	@Query(value="select * from USER_MASTER WHERE PAN=:pan", nativeQuery=true)
	public UserMaster isUserExistUserMaster (@Param("pan") String pan);
	
	@Query(value="select * from USER_MASTER WHERE USERNAME=:username", nativeQuery=true)
	public UserMaster loadUserByUserName (@Param("username") String username);
	
	@Query(value="select * from USER_MASTER um where um.EMAIL=:email", nativeQuery = true)
	public UserMaster isEmailExist(@Param("email") String email);

}
