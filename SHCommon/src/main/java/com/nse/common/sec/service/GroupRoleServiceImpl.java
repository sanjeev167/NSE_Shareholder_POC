/**
 * 
 */
package com.nse.common.sec.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nse.common.sec.entities.GroupRole;
import com.nse.common.sec.model.GroupRoleModel;
import com.nse.common.sec.repo.GroupRoleRepository;

/**
 * @author sanjeevkumar
 * 22-Mar-2024
 * 5:53:35 pm 
 * Objective: 
 */
@Service
public class GroupRoleServiceImpl implements GroupRoleService{
	
	@Autowired
	GroupRoleRepository groupRoleRepository;

	@Override
	public GroupRole addGroupRole(GroupRoleModel groupRoleModel) {
		   GroupRole savedGroupRoleEntity = null;
		try {
			GroupRole groupRoleEntity = new GroupRole();
			groupRoleEntity.setRoleId(groupRoleModel.getRoleId());
			groupRoleEntity.setGroupId(groupRoleModel.getGroupId());			
			groupRoleEntity.setCreatedOn(new Date());
			// groupRoleEntity.setCreatedBy(0);//Will be implemented latter. FK needs to be adjusted
			groupRoleEntity.setActiveC("Y");
			savedGroupRoleEntity = groupRoleRepository.save(groupRoleEntity);
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return savedGroupRoleEntity;
	}//End of addGroupRole

	@Override
	public GroupRole updateGroupRole(GroupRoleModel groupRoleModel) {
		GroupRole groupRoleEntityToBeUpdated = null;
		try {
			Optional<GroupRole> groupRoleEntityWrapper = groupRoleRepository.findById(groupRoleModel.getId());
			if (groupRoleEntityWrapper.isPresent()) {
				groupRoleEntityToBeUpdated = groupRoleEntityWrapper.get();
				groupRoleEntityToBeUpdated.setRoleId(groupRoleModel.getRoleId());
				groupRoleEntityToBeUpdated.setGroupId(groupRoleModel.getGroupId());
				groupRoleEntityToBeUpdated.setModifiedOn(new Date());
				// groupRoleEntityToBeUpdated.setModifiedBy(0);//Will be implemented latter. FK needs to be adjusted
				groupRoleEntityToBeUpdated.setActiveC(groupRoleModel.getActiveC());
				groupRoleRepository.save(groupRoleEntityToBeUpdated);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return groupRoleEntityToBeUpdated;
	}//End of updateGroupRole

	@Override
	public boolean deleteGroupRoleByRecordId(Integer recordId) {
		boolean isRecordDeleted = true;
		try {
			groupRoleRepository.deleteById(recordId);
		} catch (Exception ex) {
			ex.printStackTrace();
			isRecordDeleted = false;
		}finally {
			// Close I/O if required
		}
		return isRecordDeleted;
	}//End of deleteGroupRoleByRecordId

	@Override
	public GroupRole getGroupRoleByRecordId(Integer recordId) {
		GroupRole groupRoleEntityFetched = null;
		try {
			Optional<GroupRole> groupRoleEntityWrapper = groupRoleRepository.findById(recordId);
			if (groupRoleEntityWrapper.isPresent())
				groupRoleEntityFetched = groupRoleEntityWrapper.get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return groupRoleEntityFetched;
	}//End of getGroupRoleByRecordId

	@Override
	public List<GroupRole> getAllGroupRoles() {
		List<GroupRole> groupRoleList = null;
		try {
			groupRoleList = groupRoleRepository.findAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return groupRoleList;
	}//End of getAllGroupRoles

}//End of GroupRoleServiceImpl
