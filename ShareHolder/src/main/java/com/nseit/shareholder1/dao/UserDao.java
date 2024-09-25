package com.nseit.shareholder1.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.User;

public interface UserDao extends JpaRepository<User, Integer>{
	
	@Modifying
	  @Transactional
	  @Query(value="update USER_PASSWORD_MASTER u set u.PASS_HASH=:password,u.UPDATED_ON=CURRENT_TIMESTAMP where u.USER_ID=:userid", nativeQuery=true)
	public int register(@Param("password") String password, @Param("userid") Long userid);
    
	
	
}
