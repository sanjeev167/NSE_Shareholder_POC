
package com.nse.common.api.struct;

/**
 * @author sanjeevkumar 
 * 19-Mar-2024  
 * 11:18:03 pm 
 * Objective:
 */

public class ApiResponse {
	
	private ApiHead head;
	private Object body;// Can hold ApiErrorView or  ApiResponseData object	
	private ApiRequest meta;// Will hold meta data of api.
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
	 * @return the body
	 */
	public Object getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(Object body) {
		this.body = body;
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

	
	
	
}// End of ApiResponse
