package com.nseit.shareholder1.modelInterfaces;

import java.sql.Timestamp;

public interface TransferDetailsInterface {
	
	public Timestamp getCreatedOn();
	public String getBuyername();
	public String getBuyerClientId();
	public String getSellername();
	public String getSellerClientId();
	public Long getShares();
	public Double getPricePerShare();
	public String getModeoftransfer();
}
