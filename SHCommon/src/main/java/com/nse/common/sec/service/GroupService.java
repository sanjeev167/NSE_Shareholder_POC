/**
 * 
 */
package com.nse.common.sec.service;

import java.util.List;

import com.nse.common.sec.entities.GroupMstr;
import com.nse.common.sec.model.GroupModel;

/**
 * @author sanjeevkumar 
 * 22-Mar-2024
 * 4:58:14 pm 
 * Objective:
 */
public interface GroupService {

	public GroupMstr addGroup(GroupModel apiGroupModel);

	public GroupMstr updateGroup(GroupModel apiGroupModel);

	public boolean deleteGroupByRecordId(Integer recordId);

	public GroupMstr getGroupByRecordId(Integer recordId);

	public List<GroupMstr> getAllGroups();

}// End of ApiGroupService
