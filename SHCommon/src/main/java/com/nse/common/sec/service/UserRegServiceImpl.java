/**
 * 
 */
package com.nse.common.sec.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nse.common.sec.entities.UserReg;
import com.nse.common.sec.model.UserModel;
import com.nse.common.sec.repo.UserRegRepository;

/**
 * @author sanjeevkumar 
 * 22-Mar-2024 
 * 12:40:24 pm 
 * Objective:
 */
@Service
public class UserRegServiceImpl implements UserRegService {

	@Autowired
	UserRegRepository userRegRepository;
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserReg addUserReg(UserModel userModel) {

		UserReg savedUserRegEntity = null;
		try {
			UserReg userRegEntity = new UserReg();
			userRegEntity.setLoginid(userModel.getName());
			userRegEntity.setEmail(userModel.getEmail());
			userRegEntity.setPwd(encoder.encode(userModel.getPwd()));
			userRegEntity.setUserCtgId(userModel.getUserContextId());
			userRegEntity.setCreatedOn(new Date());
			// userEntity.setCreatedBy(0);//Will be implemented latter. FK needs to be
			// adjusted
			userRegEntity.setActiveC("Y");
			savedUserRegEntity = userRegRepository.save(userRegEntity);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// Close I/O if required
		}
		return savedUserRegEntity;
	}// End of addUserReg

	@Override
	public UserReg updateUserReg(UserModel userModel) {

		UserReg userEntityToBeUpdated = null;
		try {
			Optional<UserReg> userEntityWrapper = userRegRepository.findById(userModel.getId());
			if (userEntityWrapper.isPresent()) {
				userEntityToBeUpdated = userEntityWrapper.get();
				userEntityToBeUpdated.setLoginid(userModel.getName());
				userEntityToBeUpdated.setEmail(userModel.getEmail());
				userEntityToBeUpdated.setPwd(encoder.encode(userModel.getPwd()));
				userEntityToBeUpdated.setUserCtgId(userModel.getUserContextId());
				userEntityToBeUpdated.setModifiedOn(new Date());
				// userEntityToBeUpdated.setModifiedBy(0);//Will be implemented latter. FK needs
				// to be adjusted
				userRegRepository.save(userEntityToBeUpdated);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// Close I/O if required
		}
		return userEntityToBeUpdated;
	}// End of updateUserReg

	@Override
	public boolean deleteUserByRecordId(Integer recordId) {
		boolean isRecordDeleted = true;
		try {
			userRegRepository.deleteById(recordId);
		} catch (Exception ex) {
			ex.printStackTrace();
			isRecordDeleted = false;
		} finally {
			// Close I/O if required
		}
		return isRecordDeleted;
	}// End of deleteUserByRecordId

	@Override
	public UserReg getUserByRecordId(Integer recordId) {
		UserReg userEntityFetched = null;
		try {
			Optional<UserReg> userEntityWrapper = userRegRepository.findById(recordId);
			if (userEntityWrapper.isPresent())
				userEntityFetched = userEntityWrapper.get();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// Close I/O if required
		}
		return userEntityFetched;
	}// End of getUserByRecordId

	@Override
	public List<UserReg> getAllUsers() {
		List<UserReg> userList = null;
		try {
			userList = userRegRepository.findAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// Close I/O if required
		}
		return userList;
	}// End of getAllUsers

}// End of UserServiceImpl
