/**
 * 
 */
package com.nse.common.pvt.sec.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nse.common.exception.ResourceNotFoundException;
import com.nse.common.sec.entities.UserReg;
import com.nse.common.sec.model.UserModel;
import com.nse.common.sec.service.UserRegService;

/**
 * @author sanjeevkumar 
 * 21-Mar-2024 
 * 1:46:04 am 
 * Objective:
 */
@RestController
@RequestMapping("/sec/v1")
public class UserRegController extends ApiBaseCtrl {
	
	@Autowired
	private UserRegService userRegService;

	@PostMapping(value = "/users", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> addUserReg(@RequestBody UserModel userModel) {
		UserReg userReg = userRegService.addUserReg(userModel);
		try {
			if (userReg != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(userModel);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(userReg, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No ApiUser is added.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of addUserReg

	@GetMapping(value = "/users/{id}", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> getUserByRecordId(@PathVariable("id") Integer id) {
		UserReg userReg = userRegService.getUserByRecordId(id);
		try {
			if (userReg != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(id);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(userReg, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No UserReg with id(" + id + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of getUserByRecordId

	@GetMapping(value = "/users", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> getAllUsers() {
		List<UserReg> userRegList = userRegService.getAllUsers();
		try {
			if (userRegList != null) {
				apiReq = makeApiMetaData();
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(userRegList, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No UserReg is yet defined!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of getAllUsers

	@DeleteMapping(value = "/users/{id}", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> deleteUserById(@PathVariable("id") Integer id) {
		boolean isDeleted = userRegService.deleteUserByRecordId(id);
		try {
			if (isDeleted) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(id);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(true, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No UserReg with id(" + id + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of deleteUserById

	@PutMapping(value = "/users", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> updateUserReg(@RequestBody UserModel userModel) {

		UserReg userReg = userRegService.updateUserReg(userModel);
		try {
			if (userReg != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(userModel);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(userReg, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No UserReg with id(" + userModel.getId() + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of updateUserReg

}// End of ApiUserController
