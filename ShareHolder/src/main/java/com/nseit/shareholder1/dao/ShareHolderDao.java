package com.nseit.shareholder1.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.nseit.shareholder1.model.User;
import com.nseit.shareholder1.model.UserToken;



public interface ShareHolderDao extends JpaRepository<User, Integer> {


	@Query(value="select u.ID,u.PASS_HASH,u.CREATED_ON,u.UPDATED_ON,u.USER_ID from USER_PASSWORD_MASTER u INNER JOIN USER_MASTER um ON u.USER_ID=um.ID and um.USERNAME=:username ORDER BY um.CREATED_ON desc", nativeQuery=true)
	public User userExist(@Param("username") String username);
	
	@Query(value="select * from USER_PASSWORD_MASTER where PASS_HASH=:oldpassword", nativeQuery=true)
	public User isPasswordExist(@Param("oldpassword") String oldpassword);
	
	
	
	


}
