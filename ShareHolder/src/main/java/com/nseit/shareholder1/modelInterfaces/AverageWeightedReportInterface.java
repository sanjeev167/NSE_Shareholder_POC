package com.nseit.shareholder1.modelInterfaces;

import java.sql.Timestamp;
import java.util.List;

import com.nseit.shareholder1.metadatamodel.PacTable;


public interface AverageWeightedReportInterface {

	public Long getUuid();
	public String getSellername();
	public String getBuyername();
	public Long getNoofshares();
	public Double getPricepershare();
	public Double getAmount();
	public String getCategoryofbuyer();
	public Timestamp getDateofapproval();
	public Timestamp getDateofexecution();
	public String getBuyerpac();
	public PacTableInterface getPacdetailslist();
	
}
