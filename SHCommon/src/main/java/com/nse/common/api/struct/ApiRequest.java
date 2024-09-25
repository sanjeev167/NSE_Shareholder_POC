/**
 * 
 */
package com.nse.common.api.struct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author sanjeevkumar
 * 19-Mar-2024 
 * 11:02:45 pm 
 * Objective: 
 */
@JsonPropertyOrder({
	"api_url",
	"method",
	"payLoad",
	"requested_at",
	"responded_at"
})
public class ApiRequest {

	
	private String api_url;
	private String method;
	private Object payLoad;
	 @JsonIgnore
	private String inContent_Type= "application/json";
	 @JsonIgnore
	private String outContent_Type= "application/json";
	private String requested_at;
	private String responded_at;
	
		
	public ApiRequest( String api_url, String method, 
			String inContent_Type, String outContent_Type,String requested_at,String responded_at) {
		super();		
		this.api_url = api_url;
		this.method = method;		
		this.inContent_Type = inContent_Type;
		this.outContent_Type = outContent_Type;
		this.requested_at=requested_at;
		this.responded_at=responded_at;
	}
	
	public ApiRequest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the api_url
	 */
	public String getApi_url() {
		return api_url;
	}
	/**
	 * @param api_url the api_url to set
	 */
	public void setApi_url(String api_url) {
		this.api_url = api_url;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the payLoad
	 */
	public Object getPayLoad() {
		return payLoad;
	}
	/**
	 * @param payLoad the payLoad to set
	 */
	public void setPayLoad(Object payLoad) {
		this.payLoad = payLoad;
	}
	/**
	 * @return the inContent_Type
	 */
	public String getInContent_Type() {
		return inContent_Type;
	}
	/**
	 * @param inContent_Type the inContent_Type to set
	 */
	public void setInContent_Type(String inContent_Type) {
		this.inContent_Type = inContent_Type;
	}
	/**
	 * @return the outContent_Type
	 */
	public String getOutContent_Type() {
		return outContent_Type;
	}
	/**
	 * @param outContent_Type the outContent_Type to set
	 */
	public void setOutContent_Type(String outContent_Type) {
		this.outContent_Type = outContent_Type;
	}
	/**
	 * @return the requested_at
	 */
	public String getRequested_at() {
		return requested_at;
	}
	/**
	 * @param requested_at the requested_at to set
	 */
	public void setRequested_at(String requested_at) {
		this.requested_at = requested_at;
	}
	/**
	 * @return the responded_at
	 */
	public String getResponded_at() {
		return responded_at;
	}
	/**
	 * @param responded_at the responded_at to set
	 */
	public void setResponded_at(String responded_at) {
		this.responded_at = responded_at;
	}
	
	
	
}//End of ApiRequest
