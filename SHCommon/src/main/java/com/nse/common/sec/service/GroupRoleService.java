/**
 * 
 */
package com.nse.common.sec.service;

import java.util.List;

import com.nse.common.sec.entities.GroupRole;
import com.nse.common.sec.model.GroupRoleModel;

/**
 * @author sanjeevkumar
 * 22-Mar-2024
 * 5:53:12 pm 
 * Objective: 
 */
public interface GroupRoleService {

	public GroupRole addGroupRole(GroupRoleModel groupRoleModel);

	public GroupRole updateGroupRole(GroupRoleModel groupRoleModel);

	public boolean deleteGroupRoleByRecordId(Integer recordId);

	public GroupRole getGroupRoleByRecordId(Integer recordId);

	public List<GroupRole> getAllGroupRoles();
}//End of ApiGroupRoleService
