package com.nseit.shareholder1.modelInterfaces;

import java.sql.Timestamp;

public interface ShareTransferRoleInterface {

	

	public Long getId();

	public String getMaker();

	public String getChecker();

	public String getBatchCreated();

	public String getCreatedby();

	public Long getUuid();

	public String getStatus();
	
	public Long getShareTransferId();
	
	public Timestamp getCreatedOn();
	
	public String getComments();
	
	
}
