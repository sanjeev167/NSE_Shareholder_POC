package com.nse.common.sec.model;

/**
 * @author sanjeevkumar
 * @Project Share_Holder
 * 09-Apr-2024
 * 12:41:18 am
 * @Objective: 
 *
 */
public class GroupModel extends BaseModel{	
	 private String     groupName ;	 

	public GroupModel() {
		super();		
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}	 
	 
}//End of GroupModel
