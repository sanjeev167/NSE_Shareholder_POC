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
import com.nse.common.sec.entities.RoleMstr;
import com.nse.common.sec.model.RoleModel;
import com.nse.common.sec.service.RoleService;

/**
 * @author sanjeevkumar 
 * 21-Mar-2024 
 * 1:47:54 am 
 * Objective:
 */
@RestController
@RequestMapping("/sec/v1")
public class RoleController extends ApiBaseCtrl {

	@Autowired
	private RoleService roleService;

	@PostMapping(value = "/roles", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> addRole(@RequestBody RoleModel roleModel) {
		RoleMstr roleMstr = roleService.addRole(roleModel);
		try {
			if (roleMstr != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(roleModel);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(roleMstr, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No Role is added.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of addRole

	@GetMapping(value = "/roles", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> getAllRoles() {
		List<RoleMstr> roleMstrList = roleService.getAllRoles();
		try {
			if (roleMstrList != null) {
				apiReq = makeApiMetaData();
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(roleMstrList, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No Role is yet defined!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of getAllRoles

	@GetMapping(value = "/roles/{id}", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> getApiRoleById(@PathVariable("id") Integer id) {
		RoleMstr roleMstr = roleService.getRoleByRecordId(id);
		try {
			if (roleMstr != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(id);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(roleMstr, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No Role with id(" + id + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	} // End of deleteRoleByRecordId

	@DeleteMapping(value = "/roles/{id}", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> deleteRoleByRecordId(@PathVariable("id") Integer id) {
		boolean isDeleted = roleService.deleteRoleByRecordId(id);
		try {
			if (isDeleted) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(id);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(true, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No Role with id(" + id + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	} // End of deleteRoleByRecordId

	@PutMapping(value = "/roles", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> updateRole(@RequestBody RoleModel roleModel) {
		RoleMstr roleMstr = roleService.updateRole(roleModel);
		try {
			if (roleMstr != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(roleModel);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(roleMstr, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No Role with id(" + roleModel.getId() + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of updateRole

}// End of ApiRoleController