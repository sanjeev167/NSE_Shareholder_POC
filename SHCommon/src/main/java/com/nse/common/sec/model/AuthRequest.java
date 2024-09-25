/**
 * 
 */
package com.nse.common.sec.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author sanjeevkumar
 * 22-Mar-2024
 * 7:43:02 pm 
 * Objective: 
 */
public class AuthRequest {

	@NotNull(message = "username can't be null")
	@NotBlank(message = "username is mandatory")
	private String username; 
	
	@NotNull(message = "password can't be null")
	@NotBlank(message = "Password is mandatory")
    private String password;   
	
	
	
	public AuthRequest() {
		super();		
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
		
	
}//End of AuthRequest
