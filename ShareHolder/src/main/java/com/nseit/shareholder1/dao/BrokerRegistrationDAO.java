package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.AuthorisedLetter;
import com.nseit.shareholder1.model.BrokerMaster;
import com.nseit.shareholder1.modelInterfaces.BrokerDetails;

public interface BrokerRegistrationDAO extends JpaRepository<BrokerMaster, Long> {

	@Query(value = "select * from BROKER_MASTER b where b.NAME=:brokername and b.EMAIL=:brokeremail and b.PHONE=:phone", nativeQuery = true)
	public List<BrokerMaster> getBrokerDetails(String brokername, String brokeremail, String phone);

	@Query(value = "select * from BROKER_MASTER b where b.USERNAME=:brokerUserName and b.NAME=:brokername and b.EMAIL=:brokeremail and b.PHONE=:phone", nativeQuery = true)
	public BrokerMaster isBrokerExist(String brokerUserName, String brokername, String brokeremail, String phone);

	@Query(value="select * from(select * from BROKER_MASTER  WHERE USERNAME=:brokerUserName AND MODIFIED_ON >= CURRENT_TIMESTAMP - INTERVAL '15' minute AND    MODIFIED_ON<=  CURRENT_TIMESTAMP order By CREATED_ON DESC) T WHERE  ROWNUM=1" , nativeQuery=true)
	public BrokerMaster getBrokerOtp(@Param("brokerUserName") String brokerUserName);
	
	
	@Query(value = "select * from BROKER_MASTER b INNER JOIN BROKER_CLIENT_MAPPING bm ON b.ID = bm.BROKER_ID AND b.USERNAME=:brokerUserName AND bm.APPROVED = 'Y' ORDER BY b.CREATED_ON desc", nativeQuery = true)
	public BrokerMaster checkBroker(String brokerUserName);
	
	@Query(value = "select * from(select * from BROKER_MASTER b INNER JOIN BROKER_CLIENT_MAPPING bm ON b.ID = bm.BROKER_ID AND bm.CLIENT_ID=:brokerUserName AND bm.APPROVED is null ORDER BY b.CREATED_ON desc) T WHERE  ROWNUM=1", nativeQuery = true)
	public BrokerMaster checkPendingBroker(String brokerUserName);

	@Query(value = "select b.ID as BrokerId,USERNAME,NAME,EMAIL,PHONE,AUTHORISED_PERSON_NAME as AuthorisedPersonName,OTP_VERIFIED,b.CREATED_ON AS BrokerCreated,b.MODIFIED_ON as BrokerModified,bm.ID as BrokerMapppingId,CLIENT_ID as ClientId,bm.CREATED_ON as BrokerClientMappingCreated, APPROVED, bm.MODIFIED_ON as BrokerClientModified,al.ID as AuthorisedId, al.FILE_NAME as FileName, al.CREATED_ON as AuthorisedLetterCreated from BROKER_MASTER b INNER JOIN BROKER_CLIENT_MAPPING bm ON b.ID = bm.BROKER_ID INNER JOIN AUTHORISED_LETTER al ON al.ID=bm.AUTHORISED_LETTER and bm.CLIENT_ID=:clientId", nativeQuery = true)
	public List<BrokerDetails> getAllBrokerDetials(String clientId);
	// @Query(value = "select * from AUTHORISED_LETTER u INNER JOIN BROKER_MASTER um
	// ON u.ID=um.AUTHORISED_LETTER ", nativeQuery = true)
	/*
	 * @Query(value =
	 * "select * from AUTHORISED_LETTER u WHERE u.ID=:authorisedLetter", nativeQuery
	 * = true) public AuthorisedLetter
	 * getAuthorisedLetter(@Param("authorisedLetter") Long authorisedLetter);
	 */
	
	@Query(value="select bm.client_id from BROKER_CLIENT_MAPPING bm inner join BROKER_MASTER b on b.ID = bm.BROKER_ID AND bm.APPROVED = 'Y' and b.username=:username", nativeQuery=true)
	public List<String> getAllClientIdBasedOnBroker(String username);

}
