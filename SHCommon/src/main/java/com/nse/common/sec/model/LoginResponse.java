/**
 * 
 */
package com.nse.common.sec.model;

/**
 * @author sanjeevkumar
 *
 */
public class LoginResponse {
	
	private String username;	
	private String userPhone;
	private String userType;
	private boolean is2FaEnabled=false;
	private boolean isOtpSent=false;	
	private String cr_code;
	private String pid;
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
	 * @return the userPhone
	 */
	public String getUserPhone() {
		return userPhone;
	}
	/**
	 * @param userPhone the userPhone to set
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
	
	
	/**
	 * @return the is2FaEnabled
	 */
	public boolean isIs2FaEnabled() {
		return is2FaEnabled;
	}
	/**
	 * @param is2FaEnabled the is2FaEnabled to set
	 */
	public void setIs2FaEnabled(boolean is2FaEnabled) {
		this.is2FaEnabled = is2FaEnabled;
	}
	/**
	 * @return the cr_code
	 */
	public String getCr_code() {
		return cr_code;
	}
	/**
	 * @param cr_code the cr_code to set
	 */
	public void setCr_code(String cr_code) {
		this.cr_code = cr_code;
	}
	/**
	 * @return the isOtpSent
	 */
	public boolean isOtpSent() {
		return isOtpSent;
	}
	/**
	 * @param isOtpSent the isOtpSent to set
	 */
	public void setOtpSent(boolean isOtpSent) {
		this.isOtpSent = isOtpSent;
	}
	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
	
}
