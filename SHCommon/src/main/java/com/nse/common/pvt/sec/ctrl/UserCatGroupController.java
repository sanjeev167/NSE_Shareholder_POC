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
import com.nse.common.sec.entities.UserCatGroup;
import com.nse.common.sec.model.UserCatGroupModel;
import com.nse.common.sec.service.UserCatGroupService;

/**
 * @author sanjeevkumar 21-Mar-2024 1:51:32 am Objective:
 */
@RestController
@RequestMapping("/sec/v1")
public class UserCatGroupController extends ApiBaseCtrl {

	@Autowired
	private UserCatGroupService userCatGroupService;

	@PostMapping(value = "/usergroups", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> addUserCatGroup(@RequestBody UserCatGroupModel userCatGroupModel) {
		UserCatGroup userCatGroup = userCatGroupService.addUserCatGroup(userCatGroupModel);
		try {
			if (userCatGroup != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(userCatGroupModel);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(userCatGroup, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No UserCatGroup is added.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}//End of addUserCatGroup

	@GetMapping(value = "/usergroups/{id}", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> getUserCatGroupByRecordId(@PathVariable("id") Integer id) {
		UserCatGroup userCatGroup = userCatGroupService.getUserCatGroupByRecordId(id);
		try {
			if (userCatGroup != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(id);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(userCatGroup, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No UserCatGroup with id(" + id + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}//End of getUserCatGroupByRecordId

	@GetMapping(value = "/usergroups", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> getAllUserCatGroups() {
		List<UserCatGroup> userCatGroupList = userCatGroupService.getAllUserCatGroups();
		try {
			if (userCatGroupList != null) {
				apiReq = makeApiMetaData();
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(userCatGroupList, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No UserCatGroup is yet defined!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}//End of getAllUserCatGroups

	@DeleteMapping(value = "/usergroups/{id}", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> deleteUserCatGroupByRecordId(@PathVariable("id") Integer id) {
		boolean isDeleted = userCatGroupService.deleteUserCatGroupByRecordId(id);
		try {
			if (isDeleted) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(id);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(true, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No UserCatGroup with id(" + id + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}//End of deleteUserCatGroupByRecordId

	@PutMapping(value = "/usergroups", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> updateUserCatGroup(@RequestBody UserCatGroupModel userCatGroupModel) {
		UserCatGroup userCatGroup = userCatGroupService.updateUserCatGroup(userCatGroupModel);
		try {
			if (userCatGroup != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(userCatGroupModel);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(userCatGroup, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException(
						"No UserCatGroup with id(" + userCatGroupModel.getId() + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}//End of updateApiUserGroup

}// End of UserCatGroupController
