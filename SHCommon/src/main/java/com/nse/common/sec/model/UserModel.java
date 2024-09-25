/**
 * 
 */
package com.nse.common.sec.model;

/**
 * @author sanjeevkumar 
 * 22-Mar-2024 
 * 12:52:00 pm 
 * Objective:
 */

public class UserModel extends BaseModel{
	
	private String name;
	private String email;
	private String pwd;
	
	private Integer userContextId;
		
	public UserModel() {
		super();
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	

	/**
	 * @return the userContext
	 */
	public Integer getUserContextId() {
		return userContextId;
	}

	/**
	 * @param userContext the userContext to set
	 */
	public void setUserContextId(Integer userContextId) {
		this.userContextId = userContextId;
	}
	
	

		
}// End of ApiUserModel
