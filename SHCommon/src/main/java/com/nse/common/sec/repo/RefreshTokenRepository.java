/**
 * 
 */
package com.nse.common.sec.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nse.common.sec.entities.RefreshToken;

/**
 * @author sanjeevkumar
 * 20-Mar-2024
 * 12:41:24 pm 
 * Objective : 
 */


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	
	Optional<RefreshToken> findByRefreshToken(String token);

}//End of RefreshTokenRepository
