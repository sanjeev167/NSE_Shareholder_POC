package com.nse.common.sec.config;


import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.stereotype.Service;

import com.nse.common.sec.config.ldap.LdapConfig;

/**
 * @author sanjeevkumar
 * @Project Share_Holder
 * 01-Apr-2024
 * 10:39:00 pm
 * @Objective: 
 *
 */
@Service
public class MultiSourceAuthenticationProvider implements AuthenticationProvider {

private LdapAuthenticationProvider ldapAuthenticationProvider;
	
	private BindAuthenticator bindAuthenticator;
	
	private DaoAuthenticationProvider daoAuthenticationProvider;
	
	@Autowired
	LdapContextSource ldapContextSource;
	
	@Autowired
	UserDetailsService localUserDetailsService;
	
	@Autowired
	LdapConfig ldapConfig;
    
	@PostConstruct
	public void init() {
		
		this.daoAuthenticationProvider = new DaoAuthenticationProvider();
		this.daoAuthenticationProvider.setUserDetailsService(localUserDetailsService);
		this.daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());		
		
		
		LdapUserSearch userSearch = new FilterBasedLdapUserSearch("", buildUserSearchFilter(),ldapContextSource);		
		this.bindAuthenticator = new BindAuthenticator(ldapContextSource);
		this.bindAuthenticator.setUserSearch(userSearch);		
		DefaultLdapAuthoritiesPopulator authoritiesPopulator = new DefaultLdapAuthoritiesPopulator(ldapContextSource, "");
		authoritiesPopulator.setIgnorePartialResultException(true);
		authoritiesPopulator.setConvertToUpperCase(true);
		authoritiesPopulator.setGroupSearchFilter("(& (objectclass=group) (member={0}))");
		
		this.ldapAuthenticationProvider = new LdapAuthenticationProvider(this.bindAuthenticator, authoritiesPopulator);
	}
	
	private String buildUserSearchFilter() {
		StringBuilder builder = new StringBuilder();
		builder.append("(& ");
		builder.append("(objectclass=organizationalPerson)");
		builder.append("(sAMAccountName={0})");
		builder.append("(memberOf=");
		builder.append(ldapConfig.getAuthorizedUsersSearchBase());
		builder.append(")");
		builder.append(")");
		
		return builder.toString();
	}
	
	public void updateUserSearchFilter() {
		LdapUserSearch userSearch = new FilterBasedLdapUserSearch("", buildUserSearchFilter(),ldapContextSource);
		this.bindAuthenticator.setUserSearch(userSearch);
	}
	
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String comingUserForLogin = authentication.getPrincipal().toString();
		
		if(comingUserForLogin.endsWith("@local")) {//This will check whether the coming user is application local user or not
			System.out.println("Sanjeev MultiSourceAuthenticationProvider: Coming user is local application user.");
			return this.daoAuthenticationProvider.authenticate(authentication); 
		}else {
		System.out.println("Sanjeev MultiSourceAuthenticationProvider : Coming user is NSE domain user.");
		return this.ldapAuthenticationProvider.authenticate(authentication);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}//End of MultiSourceAuthenticationProvider 
