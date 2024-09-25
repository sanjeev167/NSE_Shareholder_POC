/**
 * 
 */
package com.nse.common.api.struct;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author sanjeevkumar
 * 19-Mar-2024 
 * 1:57:15 am 
 * Objective : 
 */

public class ApiError {

	private ApiHead head;
	private List<String> errors;
	@JsonIgnore
	private  ApiRequest meta;// Will hold meta data of api.
	
	
	public ApiError(ApiHead head, List<String> details) {
		this.head = head;
		this.errors = details;		
	}
	public ApiError(ApiHead head, List<String> details, ApiRequest apiRequest) {
		this.head = head;
		this.errors = details;
		this.meta= apiRequest;
	}
	/**
	 * @return the head
	 */
	public ApiHead getHead() {
		return head;
	}
	/**
	 * @param head the head to set
	 */
	public void setHead(ApiHead head) {
		this.head = head;
	}
	/**
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}
	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	/**
	 * @return the meta
	 */
	public ApiRequest getMeta() {
		return meta;
	}
	/**
	 * @param meta the meta to set
	 */
	public void setMeta(ApiRequest meta) {
		this.meta = meta;
	}
	
	
}//End of ApiError
