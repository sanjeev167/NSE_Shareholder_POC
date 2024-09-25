package com.nseit.shareholder1.modelInterfaces;

import java.sql.Timestamp;

public interface CafListingNsdl {

	public Integer getUuid();
	public Integer getNoOfShares();
	public String getSellername(); 
	public String getSellerClientId();
	public String getBuyername();
	public String getBuyerClientId(); 
	public Float getPricePerShare();
	public Timestamp getCreatedOn();

}
