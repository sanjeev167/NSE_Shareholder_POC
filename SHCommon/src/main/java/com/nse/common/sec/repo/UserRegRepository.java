package com.nse.common.sec.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nse.common.sec.entities.UserReg;

/**
 * @author sanjeevkumar
 * @Project Share_Holder
 * 09-Apr-2024
 * 12:07:28 am
 * @Objective: 
 *
 */
@Repository
public interface UserRegRepository extends JpaRepository<UserReg, Integer>{
	Optional<UserReg> findByLoginid(String username);	
}//End of UserRegRepository
