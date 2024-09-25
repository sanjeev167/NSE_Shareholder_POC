/**
 * 
 */
package com.nse.common.sec.service;

import java.util.List;

import com.nse.common.sec.entities.UserReg;
import com.nse.common.sec.model.UserModel;

/**
 * @author sanjeevkumar
 * 23-Mar-2023 
 * 12:40:50 pm 
 * Objective: 
 */
public interface UserRegService {
	
	public UserReg addUserReg(UserModel apiUserModel);
	public UserReg updateUserReg(UserModel apiUserModel);
	public boolean deleteUserByRecordId(Integer recordId);
	public UserReg getUserByRecordId(Integer recordId);
	public List<UserReg> getAllUsers();

}//End of ApiUserService
