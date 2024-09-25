/**
 * 
 */
package com.nse.common.sec.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nse.common.sec.entities.UserCatGroup;
import com.nse.common.sec.model.UserCatGroupModel;
import com.nse.common.sec.repo.UserCatGroupRepository;

/**
 * @author sanjeevkumar
 * 23-Mar-2023 
 * 5:20:54 pm 
 * Objective: 
 */
@Service
public class UserCatGroupServiceImpl implements UserCatGroupService{

	@Autowired
	UserCatGroupRepository userCatGroupRepository;
	
	@Override
	public UserCatGroup addUserCatGroup(UserCatGroupModel userCatGroupModel) {
		UserCatGroup savedUserCatGroupEntity = null;
		try {
			UserCatGroup userCatGroupEntity = new UserCatGroup();			
			userCatGroupEntity.setUserCatId(userCatGroupModel.getUserId());			
			userCatGroupEntity.setGroupId(userCatGroupModel.getGroupId());			
			userCatGroupEntity.setCreatedOn(new Date());
			// userCatGroupEntity.setCreatedBy(0);//Will be implemented latter. FK needs to be adjusted
			userCatGroupEntity.setActiveC("Y");
			savedUserCatGroupEntity = userCatGroupRepository.save(userCatGroupEntity);
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return savedUserCatGroupEntity;
	}//End of addUserCatGroup

	@Override
	public UserCatGroup updateUserCatGroup(UserCatGroupModel userCatGroupModel) {
		UserCatGroup userCatGroupEntityToBeUpdated = null;
		try {
			Optional<UserCatGroup> userCatGroupEntityWrapper = userCatGroupRepository.findById(userCatGroupModel.getId());
			if (userCatGroupEntityWrapper.isPresent()) {
				userCatGroupEntityToBeUpdated = userCatGroupEntityWrapper.get();
				userCatGroupEntityToBeUpdated.setUserCatId(userCatGroupModel.getUserId());
				userCatGroupEntityToBeUpdated.setGroupId(userCatGroupModel.getGroupId());
				userCatGroupEntityToBeUpdated.setModifiedOn(new Date());
				// userCatGroupEntityToBeUpdated.setModifiedBy(0);//Will be implemented latter. FK needs to be adjusted
				userCatGroupEntityToBeUpdated.setActiveC(userCatGroupModel.getActiveC());
				userCatGroupRepository.save(userCatGroupEntityToBeUpdated);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return userCatGroupEntityToBeUpdated;
	}//End of updateUserCatGroup

	@Override
	public boolean deleteUserCatGroupByRecordId(Integer recordId) {
		boolean isRecordDeleted = true;
		try {
			userCatGroupRepository.deleteById(recordId);
		} catch (Exception ex) {
			ex.printStackTrace();
			isRecordDeleted = false;
		}finally {
			// Close I/O if required
		}
		return isRecordDeleted;
	}//End of deleteUserCatGroupByRecordId

	@Override
	public UserCatGroup getUserCatGroupByRecordId(Integer recordId) {
		UserCatGroup userCatGroupEntityFetched = null;
		try {
			Optional<UserCatGroup> userCatGroupEntityWrapper = userCatGroupRepository.findById(recordId);
			if (userCatGroupEntityWrapper.isPresent())
				userCatGroupEntityFetched = userCatGroupEntityWrapper.get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return userCatGroupEntityFetched;
	}//End of getUserCatGroupByRecordId

	@Override
	public List<UserCatGroup> getAllUserCatGroups() {
		List<UserCatGroup> userCatGroupList = null;
		try {
			userCatGroupList = userCatGroupRepository.findAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return userCatGroupList;
	}//End of getAllUserCatGroups

}//End of UserGroupServiceImpl
