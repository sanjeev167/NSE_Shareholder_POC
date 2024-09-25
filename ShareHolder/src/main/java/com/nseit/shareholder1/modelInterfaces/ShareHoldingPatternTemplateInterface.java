package com.nseit.shareholder1.modelInterfaces;

import java.sql.Timestamp;

import javax.persistence.Column;

public interface ShareHoldingPatternTemplateInterface {

	
//	private String buyername;
//	
//	private Long noOfShares;
//	
//	private String categoryOfBuyer;
//	
//	private String subCategory;
//	
//	private String corpListed;
//	
//	private String buyerTypeTCCM;
//	
//	private String buyerNRIType;
//
//	private String clientId;
//	
//	private Timestamp dateOfExecution;

	public String getBuyername();

	public Long getNoOfShares();

	public String getCategoryOfBuyer();

	public String getSubCategory();

	public String getCorpListed();

	public String getBuyerTypeTCCM();

	public String getBuyerNRIType();

	public String getClientId();

	public Timestamp getDateOfExecution();
	
	
}
