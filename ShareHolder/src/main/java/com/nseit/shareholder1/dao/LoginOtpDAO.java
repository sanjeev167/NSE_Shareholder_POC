package com.nseit.shareholder1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.LoginOTP;
import com.nseit.shareholder1.model.UserMaster;

public interface LoginOtpDAO extends JpaRepository<LoginOTP, Long>{
	@Query(value="select * from(select * from LOGIN_OTP WHERE USERNAME=:userName AND CREATED_ON >= CURRENT_TIMESTAMP - INTERVAL '5' minute AND    CREATED_ON<=  CURRENT_TIMESTAMP order By CREATED_ON DESC) T WHERE  ROWNUM=1" , nativeQuery=true)
	public LoginOTP getByUserName(String userName);

}
