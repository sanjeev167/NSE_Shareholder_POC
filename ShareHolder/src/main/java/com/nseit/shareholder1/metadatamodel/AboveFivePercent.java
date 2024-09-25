package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.Date;

//Above 5%  Checks
public class AboveFivePercent{
	
//	SEBI Approved or Not
	private String sebi;
//	Approval Date
	private Date date;
//	Approval Letter
	private File approvalLetter;
	public String getSebi() {
		return sebi;
	}
	public void setSebi(String sebi) {
		this.sebi = sebi;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public File getApprovalLetter() {
		return approvalLetter;
	}
	public void setApprovalLetter(File approvalLetter) {
		this.approvalLetter = approvalLetter;
	}
	
	

}
