/**
 * 
 */
package com.nse.common.sec.service;

import java.util.List;

import com.nse.common.sec.entities.UserCatGroup;
import com.nse.common.sec.model.UserCatGroupModel;

/**
 * @author sanjeevkumar
 * 22-Mar-2024
 * 5:20:27 pm 
 * Objective: 
 */
public interface UserCatGroupService {

	public UserCatGroup addUserCatGroup(UserCatGroupModel userCatGroupModel);

	public UserCatGroup updateUserCatGroup(UserCatGroupModel userCatGroupModel);

	public boolean deleteUserCatGroupByRecordId(Integer recordId);

	public UserCatGroup getUserCatGroupByRecordId(Integer recordId);

	public List<UserCatGroup> getAllUserCatGroups();
	
}//End of ApiUserGroupService
