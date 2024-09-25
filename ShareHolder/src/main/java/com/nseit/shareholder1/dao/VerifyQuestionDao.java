package com.nseit.shareholder1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.VerifyQuestion;

public interface VerifyQuestionDao extends JpaRepository<VerifyQuestion, Integer> {
	@Query(value="select * from(select * from VERIFY_QUESTIONS WHERE CLIENT_ID=:clientId AND CREATED_ON >= CURRENT_TIMESTAMP - INTERVAL '10' minute AND    CREATED_ON<=  CURRENT_TIMESTAMP order By CREATED_ON DESC ) T WHERE  ROWNUM=1" , nativeQuery=true)
			
	public VerifyQuestion getByClientId(String clientId);

}
