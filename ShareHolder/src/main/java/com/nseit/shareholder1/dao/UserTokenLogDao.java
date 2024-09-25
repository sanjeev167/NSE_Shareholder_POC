package com.nseit.shareholder1.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.UserToken;

public interface UserTokenLogDao extends JpaRepository<UserToken, Long> {
	@Query(value = "select u.ID,u.USERID,u.TOKEN,u.CREATED_ON,u.MODIFIED_ON from SESSION_MASTER u INNER JOIN USER_MASTER um ON u.USERID=um.ID and um.USERNAME=username", nativeQuery = true)
	public List<UserToken> isUserExist(@Param("username") String username);

	  @Modifying
	  @Transactional
	  @Query(value="update SESSION_MASTER u set u.TOKEN=:token,u.MODIFIED_ON=CURRENT_TIMESTAMP where u.USERID in (select um.ID from USER_MASTER um where um.USERNAME=:username) and u.TOKEN=:tokenid", nativeQuery=true)
	  public int logout(@Param("token") String token,@Param("username") String username, @Param("tokenid") String tokenid);
	  
	  @Query(value="SELECT count(*) FROM SESSION_MASTER  WHERE USERID=:userId and TOKEN=:token",nativeQuery=true)
		public int isUserTokenExist(@Param("userId") Long userId,@Param("token") String token);
}
