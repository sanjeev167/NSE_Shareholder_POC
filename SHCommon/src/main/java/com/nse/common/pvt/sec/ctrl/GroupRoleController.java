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
import com.nse.common.sec.entities.GroupRole;
import com.nse.common.sec.model.GroupRoleModel;
import com.nse.common.sec.service.GroupRoleService;

/**
 * @author sanjeevkumar 
 * 21-Mar-2024 
 * 1:50:40 am 
 * Objective:
 */
@RestController
@RequestMapping("/sec/v1")
public class GroupRoleController extends ApiBaseCtrl {

	@Autowired
	private GroupRoleService groupRoleService;

	@PostMapping(value = "/grouproles", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> addGroupRole(@RequestBody GroupRoleModel groupRoleModel) {
		GroupRole groupRole = groupRoleService.addGroupRole(groupRoleModel);
		try {
			if (groupRole != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(groupRoleModel);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(groupRole, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No GroupRole is added");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}//End of addGroupRole

	@GetMapping(value = "/grouproles", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> getAllGroupRoles() {
		List<GroupRole> groupRoleList = groupRoleService.getAllGroupRoles();
		try {
			if (groupRoleList != null) {
				apiReq = makeApiMetaData();
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(groupRoleList, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No GroupRole is yet defined!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}

	}//End of getAllGroupRoles

	@GetMapping(value = "/grouproles/{id}", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> getGroupRoleByRecordId(@PathVariable("id") Integer id) {
		GroupRole groupRole = groupRoleService.getGroupRoleByRecordId(id);
		try {
			if (groupRole != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(id);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(groupRole, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No GroupRole with id (" + id + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}//End of getGroupRoleByRecordId

	@DeleteMapping(value = "/grouproles/{id}", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> deleteGroupRoleByRecordId(@PathVariable("id") Integer id) {
		boolean isDeleted = groupRoleService.deleteGroupRoleByRecordId(id);
		try {
			if (isDeleted) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(id);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(true, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No GroupRole with id (" + id + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}//End of deleteGroupRoleByRecordId

	@PutMapping(value = "/grouproles", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> updateGroupRole(@RequestBody GroupRoleModel groupRoleModel) {
		GroupRole groupRole = groupRoleService.updateGroupRole(groupRoleModel);
		try {
			if (groupRole != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(groupRoleModel);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(groupRole, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException(
						"No GroupRole with id(" + groupRoleModel.getId() + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}//End of updateGroupRole
}// End of ApiGroupRoleController
