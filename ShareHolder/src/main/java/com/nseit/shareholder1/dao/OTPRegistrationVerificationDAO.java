package com.nseit.shareholder1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.OTPRegistrationVerification;

public interface OTPRegistrationVerificationDAO extends JpaRepository<OTPRegistrationVerification, Long>{
	@Query(value="select * from(select * from OTP_REGISTRATION_VERIFY WHERE CLIENT_ID=:clientId AND CREATED_ON >= CURRENT_TIMESTAMP - INTERVAL '5' minute AND    CREATED_ON<=  CURRENT_TIMESTAMP order By CREATED_ON DESC) T WHERE  ROWNUM=1" , nativeQuery=true)
	public OTPRegistrationVerification getByClientId(String clientId);

}
