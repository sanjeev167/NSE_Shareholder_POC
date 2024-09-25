/**
 * 
 */
package com.nse.common.sec.model;

/**
 * @author sanjeevkumar
 * 22-Mar-2024
 * 1:09:19 pm 
 * Objective : 
 */

public class JwtResponse {
	
	private String accessToken;
	private String refreshToken;
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}
	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
    
    
	
	
}//End of JwtResponse
