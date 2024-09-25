/**
 * 
 */
package com.nse.common.sec.service;

import java.util.List;

import com.nse.common.sec.entities.RoleMstr;
import com.nse.common.sec.model.RoleModel;

/**
 * @author sanjeevkumar
 * 22-Mar-2024
 * 4:10:34 pm 
 * Objective: 
 */
public interface RoleService {

	public RoleMstr addRole(RoleModel roleModel);
	public RoleMstr updateRole(RoleModel roleModel);
	public boolean deleteRoleByRecordId(Integer recordId);
	public RoleMstr getRoleByRecordId(Integer recordId);
	public List<RoleMstr> getAllRoles();
	
}//End of ApiRoleService
