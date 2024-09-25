/**
 * 
 */
package com.nse.common.sec.service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nse.common.exception.TokenRefreshException;
import com.nse.common.sec.entities.RefreshToken;
import com.nse.common.sec.repo.RefreshTokenRepository;
import com.nse.common.sec.repo.UserRegRepository;

/**
 * @author sanjeevkumar 
 * 16-Mar-2024 
 * 7:23:01 pm 2024 
 * Objective :
 */
@Service
public class RefreshTokenService {

	@Value("${nse.api.jwtRefreshExpirationInMin}")
	private Long jwtRefreshExpirationInMin;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRegRepository userRegRepository;

	

	public RefreshToken createRefreshToken(String loginId) {       
       
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUserRegId(userRegRepository.findByLoginid(loginId).get().getId());		
		Date javaDate = Date.from(Instant.now().plusMillis(jwtRefreshExpirationInMin*60000));

		refreshToken.setExpiryDate(javaDate);

		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		refreshToken.setCreatedOn(new Date());
		refreshToken.setActiveC("Y");
		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}
	
	public Optional<RefreshToken> findByRefreshToken(String token) {
		return refreshTokenRepository.findByRefreshToken(token);
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		Date currentDateTime = new Date();   
		
		if (token.getExpiryDate().compareTo(currentDateTime) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getRefreshToken(), "Refresh token is expired. Please make a new signin request");
		}

		return token;
	}	
}// End of RefreshTokenService
