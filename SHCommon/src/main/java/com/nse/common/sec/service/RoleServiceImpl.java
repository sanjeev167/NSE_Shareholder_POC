/**
 * 
 */
package com.nse.common.sec.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nse.common.sec.entities.RoleMstr;
import com.nse.common.sec.model.RoleModel;
import com.nse.common.sec.repo.RoleMstrRepository;

/**
 * @author sanjeevkumar 
 * 22-Mar-2024
 * 4:10:52 pm 
 * Objective:
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleMstrRepository roleMstrRepository;

	@Override
	public RoleMstr addRole(RoleModel roleModel) {
		RoleMstr savedRoleEntity = null;
		try {
			RoleMstr roleEntity = new RoleMstr();
			roleEntity.setRoleName(roleModel.getRoleName());
			roleEntity.setCreatedOn(new Date());
			// roleEntity.setCreatedBy(0);//Will be implemented latter. FK needs to be adjusted
			roleEntity.setActiveC(roleModel.getActiveC());
			savedRoleEntity = roleMstrRepository.save(roleEntity);
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return savedRoleEntity;
	}//End of addRole

	@Override
	public RoleMstr updateRole(RoleModel roleModel) {
		RoleMstr roleEntityToBeUpdated = null;
		try {
			Optional<RoleMstr> apiRoleEntityWrapper = roleMstrRepository.findById(roleModel.getId());
			if (apiRoleEntityWrapper.isPresent()) {
				roleEntityToBeUpdated = apiRoleEntityWrapper.get();
				roleEntityToBeUpdated.setRoleName(roleModel.getRoleName());
				roleEntityToBeUpdated.setModifiedOn(new Date());
				// roleEntityToBeUpdated.setModifiedBy(0);//Will be implemented latter. FK needs to be adjusted				
				roleMstrRepository.save(roleEntityToBeUpdated);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return roleEntityToBeUpdated;
	}//End of updateRole

	@Override
	public boolean deleteRoleByRecordId(Integer recordId) {
		boolean isRecordDeleted = true;
		try {
			roleMstrRepository.deleteById(recordId);
		} catch (Exception ex) {
			ex.printStackTrace();
			isRecordDeleted = false;
		}finally {
			// Close I/O if required
		}
		return isRecordDeleted;
	}//End of deleteRoleByRecordId

	@Override
	public RoleMstr getRoleByRecordId(Integer recordId) {
		RoleMstr roleEntityFetched = null;
		try {
			Optional<RoleMstr> roleEntityWrapper = roleMstrRepository.findById(recordId);
			if (roleEntityWrapper.isPresent())
				roleEntityFetched = roleEntityWrapper.get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return roleEntityFetched;
	}//End of getRoleByRecordId

	@Override
	public List<RoleMstr> getAllRoles() {
		List<RoleMstr> roleList = null;
		try {
			roleList = roleMstrRepository.findAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			// Close I/O if required
		}
		return roleList;
	}//End of getAllRoles

}// End of RoleServiceImpl
