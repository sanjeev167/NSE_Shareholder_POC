package com.nse.common.sec.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
import org.springframework.stereotype.Service;

import com.nse.common.sec.config.ldap.LdapConfig;

import jakarta.annotation.PostConstruct;

/**
 * @author sanjeevkumar
 * @Project Share_Holder 01-Apr-2024 10:33:04 pm
 * @Objective:
 *
 */
@Service
public class MultiSourceUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MultiSourceUserDetailsService.class);

	private UserDetailsService localUserDetailsService;

	private UserDetailsService ldapUserDetailsService;

	@Autowired
	LdapContextSource ldapContextSource;

	@Autowired
	LdapConfig ldapConfig;

	@PostConstruct
	public void init() {
		this.localUserDetailsService = initLocalUserDetailsService();
		this.ldapUserDetailsService = initLdapUserDetailsService();
	}

	@Override
	public UserDetails loadUserByUsername(String comingUserForLogin) throws UsernameNotFoundException {
		//Local user login id will end with @local and domain user id will end with @ldap or @domain		
		System.out.println("MultiSourceUserDetailsService comingUserForLogin = "+comingUserForLogin);
		if (comingUserForLogin.endsWith("@local")) {			
			return localUserDetailsService.loadUserByUsername(comingUserForLogin);
		}
		return ldapUserDetailsService.loadUserByUsername(comingUserForLogin);
	}

	//It needs to be modified as per DAO
	private UserDetailsService initLocalUserDetailsService() {
		System.out.println("Sanjeev: Intializing  Dao user");
		UserDetailsService userDetailsService=null;
		try {
			LOGGER.debug("Looking up from local database userDetailsService");
			userDetailsService=getLocalUserDetailsService();
		} catch (Exception e) {
			LOGGER.error("Error while loading user details from local database userDetailsService.", e);
		}
		return userDetailsService;
	}

	private UserDetailsService initLdapUserDetailsService() {
		System.out.println("Sanjeev: Intializing  Ldap user");
		LdapUserSearch ldapUserSearch = new FilterBasedLdapUserSearch("", buildUserSearchFilter(), ldapContextSource);
		return new LdapUserDetailsService(ldapUserSearch);
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
		LdapUserSearch userSearch = new FilterBasedLdapUserSearch("", buildUserSearchFilter(), ldapContextSource);
		this.ldapUserDetailsService = new LdapUserDetailsService(userSearch);
	}

	private UserDetailsService getLocalUserDetailsService() {
		return new LocalUserDetailsService();
	}

	private UserDetailsService getLdapUserDetailsService() {
		return ldapUserDetailsService;
	}

}// End of MultiSourceUserDetailsService
