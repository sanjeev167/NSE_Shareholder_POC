/**
 * 
 */
package com.nse.common.sec.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nse.common.sec.entities.GroupMstr;
import com.nse.common.sec.entities.GroupRole;
import com.nse.common.sec.entities.RoleMstr;
import com.nse.common.sec.entities.UserCatGroup;
import com.nse.common.sec.entities.UserCatMstr;
import com.nse.common.sec.entities.UserReg;

/**
 * @author sanjeevkumar 
 * 23-Mar-2023 
 * 8:06:22 pm 
 * Objective: LocalUserDetails contains all the details that a spring based security object i.e. UserDetails holds. So, here we are preparing those stuffs which 
 * are required for implementation. We need to keep username and password at username and password variables only. In addition to this, all the roles assigned to this 
 * user need to be stuffed into List<GrantedAuthority> authorities so that spring could use while authentication and authorization. 
 */
public class LocalUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	//Following properties are available in UserReg
	private String username;
	private String password;	
	private String email;	
	private boolean is2fEnabled;
	private String phone;	
	public String userContext;	
	private String otpShared;
	private String secrete;
	
	private List<GrantedAuthority> authorities;

	public LocalUserDetails(UserReg userReg) {
        
		username = userReg.getLoginid();
		password = userReg.getPwd();
		userContext=userReg.getUserCatMstr().getName();
		email=userReg.getEmail();
		phone=userReg.getMobile();
		is2fEnabled=userReg.getIs2fEnabled();		
		secrete=userReg.getSecrete();
		UserCatMstr userCatMstr = userReg.getUserCatMstr();
		//System.out.println("\n\n LocalUserDetails => Category => "+userCatMstr.getName());
		List<UserCatGroup> listOfUserCatGroup = userCatMstr.getListOfUserCatGroup();
				
		String allAssignedGroupRoles = null;String roleName;
		//Prepare user's access rights
		for(UserCatGroup userCatGroup :listOfUserCatGroup) {
			GroupMstr groupMstr= userCatGroup.getGroupMstr();
			//System.out.println("\tGroup => "+groupMstr.getGroupName());
			List<GroupRole> ListOfGroupRole=groupMstr.getListOfGroupRole();			
			for(GroupRole groupRole:ListOfGroupRole) {
				RoleMstr roleMstr= groupRole.getRoleMstr();
				roleName = roleMstr.getRoleName();	
				//System.out.println("\t\tRole => "+roleName);
				if (allAssignedGroupRoles == null)
					allAssignedGroupRoles = roleName;
				else
					allAssignedGroupRoles = allAssignedGroupRoles + "," + roleName;
			}
		}	
		//System.out.println("\n");
		authorities = Arrays.stream(allAssignedGroupRoles.split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}	

	/**
	 * @return the userContext
	 */
	public String getUserContext() {
		return userContext;
	}

	/**
	 * @param userContext the userContext to set
	 */
	public void setUserContext(String userContext) {
		this.userContext = userContext;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the is2fEnabled
	 */
	public boolean isIs2fEnabled() {
		return is2fEnabled;
	}

	/**
	 * @param is2fEnabled the is2fEnabled to set
	 */
	public void setIs2fEnabled(boolean is2fEnabled) {
		this.is2fEnabled = is2fEnabled;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	

	/**
	 * @return the otpShared
	 */
	public String getOtpShared() {
		return otpShared;
	}

	/**
	 * @param otpShared the otpShared to set
	 */
	public void setOtpShared(String otpShared) {
		this.otpShared = otpShared;
	}
	
	

	/**
	 * @return the secrete
	 */
	public String getSecrete() {
		return secrete;
	}

	/**
	 * @param secrete the secrete to set
	 */
	public void setSecrete(String secrete) {
		this.secrete = secrete;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}// End of LocalUserDetails
