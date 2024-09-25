package com.nse.common.sec.model;

/**
 * @author sanjeevkumar
 * @Project Share_Holder
 * 09-Apr-2024
 * 12:17:38 am
 * @Objective: 
 *
 */
public class GroupRoleModel extends BaseModel{
	
	 private Integer    groupId ;
	 private Integer    roleId ;
	 
	 
	 
	public GroupRoleModel() {
		super();		
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



	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}



	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	 
}//End of GroupRoleModel
