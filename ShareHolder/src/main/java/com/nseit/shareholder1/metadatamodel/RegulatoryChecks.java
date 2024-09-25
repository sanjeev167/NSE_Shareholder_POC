package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.sql.Timestamp;

public class RegulatoryChecks implements Serializable {

	private String comments;
	private String preparedBy;
	private Timestamp preparedOn;
	private String approvedBy;
	private Timestamp approvedOn;
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getPreparedBy() {
		return preparedBy;
	}
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}
	public Timestamp getPreparedOn() {
		return preparedOn;
	}
	public void setPreparedOn(Timestamp preparedOn) {
		this.preparedOn = preparedOn;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Timestamp getApprovedOn() {
		return approvedOn;
	}
	public void setApprovedOn(Timestamp approvedOn) {
		this.approvedOn = approvedOn;
	}
	
	
	
}
