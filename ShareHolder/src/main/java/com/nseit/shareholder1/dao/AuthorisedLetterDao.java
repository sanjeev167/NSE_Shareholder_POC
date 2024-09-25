package com.nseit.shareholder1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.AuthorisedLetter;

public interface AuthorisedLetterDao extends JpaRepository<AuthorisedLetter, Long> {
	/*
	 * @Query(value =
	 * "select * from AUTHORISED_LETTER u INNER JOIN BROKER_MASTER um ON u.ID=um.AUTHORISED_LETTER"
	 * , nativeQuery = true) //@Query(value =
	 * "select * from AUTHORISED_LETTER u WHERE u.ID=:authorisedLetter", nativeQuery
	 * = true) public AuthorisedLetter getAuthorisedLetter();
	 */
	
	@Query(value = "select * from AUTHORISED_LETTER u WHERE u.FILE_PATH=:pathName", nativeQuery = true)
	//use path insteadOf authorisedLetterName
	public AuthorisedLetter getAuthorisedLetter(@Param("pathName") String pathName);
	
	@Query(value = "select * from AUTHORISED_LETTER u WHERE u.ID=:id", nativeQuery = true)
	//use path insteadOf authorisedLetterName
	public AuthorisedLetter getAuthorisedLetterById(Long id);
}
