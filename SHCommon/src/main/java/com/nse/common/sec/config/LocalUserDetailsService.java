/**
 * 
 */
package com.nse.common.sec.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nse.common.sec.entities.UserReg;
import com.nse.common.sec.repo.UserRegRepository;

/**
 * @author sanjeevkumar 
 * 23-Mar-2023 
 * 8:09:46 pm 
 * Objective: This service loads a coming user details. It loads using login id.
 */
@Service
public class LocalUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRegRepository userRegRepository;	
     
	@Override
	public UserDetails loadUserByUsername(String comingUserForLogin) throws UsernameNotFoundException {
		
		//System.out.println("Sanjeev LocalUserDetailsService => comingUserForLogin "+comingUserForLogin);
		String username=comingUserForLogin.split("@")[0].trim();		
		Optional<UserReg> userReg = userRegRepository.findByLoginid(username); 	
		//System.out.println("Sanjeev LocalUserDetailsService => userReg fetched from database "+userReg);
		// Converting userReg to LocalUserDetails. 
		return userReg.map(LocalUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(username+" not found in database"));	
	}
}// End of LocalUserDetailsService
