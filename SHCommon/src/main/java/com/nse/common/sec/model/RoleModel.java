package com.nse.common.sec.model;

/**
 * @author sanjeevkumar
 * @Project Share_Holder
 * 09-Apr-2024
 * 12:58:59 am
 * @Objective: 
 *
 */
public class RoleModel extends BaseModel{
	
	private String roleName;	
	
	public RoleModel() {
		super();		
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}//End of  RoleModel
