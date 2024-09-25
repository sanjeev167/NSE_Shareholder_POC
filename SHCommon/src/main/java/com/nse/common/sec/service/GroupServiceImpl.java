/**
 * 
 */
package com.nse.common.sec.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nse.common.sec.entities.GroupMstr;
import com.nse.common.sec.model.GroupModel;
import com.nse.common.sec.repo.GroupMstrRepository;

/**
 * @author sanjeevkumar 
 * 22-Mar-2024
 * 4:58:43 pm 
 * Objective:
 */
@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupMstrRepository groupMstrRepository;
	
	@Override
	public GroupMstr addGroup(GroupModel groupModel) {
		GroupMstr savedGroupEntity = null;
		try {
			GroupMstr groupEntity = new GroupMstr();
			groupEntity.setGroupName(groupModel.getGroupName());
			groupEntity.setCreatedOn(new Date());
			// groupEntity.setCreatedBy(0);//Will be implemented latter. FK needs to be adjusted
			groupEntity.setActiveC("Y");
			savedGroupEntity = groupMstrRepository.save(groupEntity);
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return savedGroupEntity;
	}//End of addGroup
	

	@Override
	public GroupMstr updateGroup(GroupModel groupModel) {
		GroupMstr groupEntityToBeUpdated = null;
		try {			
			Optional<GroupMstr> groupEntityWrapper = groupMstrRepository.findById(groupModel.getId());
			if (groupEntityWrapper.isPresent()) {
				groupEntityToBeUpdated = groupEntityWrapper.get();
				groupEntityToBeUpdated.setGroupName(groupModel.getGroupName());
				groupEntityToBeUpdated.setModifiedOn(new Date());
				// groupEntityToBeUpdated.setModifiedBy(0);//Will be implemented latter. FK needs to be adjusted
				groupEntityToBeUpdated.setActiveC(groupModel.getActiveC());
				groupMstrRepository.save(groupEntityToBeUpdated);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return groupEntityToBeUpdated;
	}

	@Override
	public boolean deleteGroupByRecordId(Integer recordId) {
		boolean isRecordDeleted = true;
		try {
			groupMstrRepository.deleteById(recordId);
		} catch (Exception ex) {
			ex.printStackTrace();
			isRecordDeleted = false;
		}finally {
			// Close I/O if required
		}
		return isRecordDeleted;
	}

	@Override
	public GroupMstr getGroupByRecordId(Integer recordId) {
		GroupMstr groupEntityFetched = null;
		try {
			Optional<GroupMstr> groupEntityWrapper = groupMstrRepository.findById(recordId);
			if (groupEntityWrapper.isPresent())
				groupEntityFetched = groupEntityWrapper.get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return groupEntityFetched;
	}

	@Override
	public List<GroupMstr> getAllGroups() {
		List<GroupMstr> groupList = null;
		try {
			groupList = groupMstrRepository.findAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return groupList;
	}

}// End of GroupServiceImpl
