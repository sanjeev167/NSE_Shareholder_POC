package com.nseit.shareholder1.modelInterfaces;

import java.sql.Timestamp;

public interface SalesPurchaseInterface {
	public Long getUuids();

	public Long getNoOfShares();

	public Long getPricePerShare();

	public Timestamp getModifiedOn();

	public String getTransactionParty();

	public String getCategoryTransfer();
	
	public String getModeOfTransfer();
}
