/**
 * 
 */
package com.nse.common.sec.model;

/**
 * @author sanjeevkumar
 * 22-Mar-2024
 * 5:17:34 pm 
 * Objective: 
 */
public class UserCatGroupModel extends BaseModel{

	 private Integer    userId ;
	 private Integer    groupId ;
	 
	 
	public UserCatGroupModel() {
		super();		
	}


	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	/**
	 * @return the groupId
	 */
	public Integer getGroupId() {
		return groupId;
	}


	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	
	
}//End of ApiUserGroupModel
