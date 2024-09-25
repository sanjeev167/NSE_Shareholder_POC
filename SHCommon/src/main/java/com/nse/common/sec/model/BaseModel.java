/**
 * 
 */
package com.nse.common.sec.model;

import java.util.Date;

/**
 * @author sanjeevkumar
 * 22-Mar-2024
 * 4:49:40 pm 
 * Objective: 
 */
public class BaseModel {
	 private Integer    id ;	 
	 private Date       createdOn ;
	 private Integer    createdBy ;
	 private Date       modifiedOn ;
	 private Integer    modifiedBy ;
	 private String     activeC = "Y" ;
	
	 /**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}
	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the modifiedOn
	 */
	public Date getModifiedOn() {
		return modifiedOn;
	}
	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	/**
	 * @return the modifiedBy
	 */
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the activeC
	 */
	public String getActiveC() {
		return activeC;
	}
	/**
	 * @param activeC the activeC to set
	 */
	public void setActiveC(String activeC) {
		this.activeC = activeC;
	}
	 
	 
}//End of BaseModel
