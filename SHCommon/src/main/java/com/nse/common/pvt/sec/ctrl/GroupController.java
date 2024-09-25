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
import com.nse.common.sec.entities.GroupMstr;
import com.nse.common.sec.model.GroupModel;
import com.nse.common.sec.service.GroupService;

/**
 * @author sanjeevkumar 
 * 21-Mar-2024 
 * 1:48:35 am 
 * Objective:
 */
@RestController
@RequestMapping("/sec/v1")
public class GroupController extends ApiBaseCtrl {
	@Autowired
	private GroupService groupService;

	@PostMapping(value = "/groups", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> addGroup(@RequestBody GroupModel groupModel) {
		GroupMstr groupMstr = groupService.addGroup(groupModel);
		try {
			if (groupMstr != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(groupModel);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(groupMstr, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No Group is added");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of addGroup

	@GetMapping(value = "/groups", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> getAllGroups() {
		List<GroupMstr> groupList = groupService.getAllGroups();
		try {
			if (groupList != null) {
				apiReq = makeApiMetaData();
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(groupList, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No Group is yet defined!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of getAllGroups

	@GetMapping(value = "/groups/{id}", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> getGroupByRecordId(@PathVariable("id") Integer id) {
		GroupMstr groupMstr = groupService.getGroupByRecordId(id);
		try {
			if (groupMstr != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(id);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(groupMstr, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No Group with id (" + id + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of getGroupByRecordId

	@DeleteMapping(value = "/groups/{id}", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> deleteGroupByRecordId(@PathVariable("id") Integer id) {
		boolean isDeleted = groupService.deleteGroupByRecordId(id);
		try {
			if (isDeleted) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(id);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(true, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No Group with id (" + id + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of deleteGroupByRecordId

	@PutMapping(value = "/groups", produces = "application/json", consumes = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
	public ResponseEntity<Object> updateGroup(@RequestBody GroupModel groupModel) {

		GroupMstr groupMstr = groupService.updateGroup(groupModel);
		try {
			if (groupMstr != null) {
				apiReq = makeApiMetaData();
				apiReq.setPayLoad(groupModel);
				// Return response in a pre-defined format
				apiResponse = makeSuccessResponse(groupMstr, apiReq);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new ResourceNotFoundException("No Group with id(" + groupModel.getId() + ") is found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// Close I/O if opened
		}
	}// End of deleteGroupByRecordId

}// End of updateGroup
