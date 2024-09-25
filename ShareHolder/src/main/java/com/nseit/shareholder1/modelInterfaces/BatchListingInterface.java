package com.nseit.shareholder1.modelInterfaces;

public interface BatchListingInterface {
 
	

//	Long uuid;
//	String sellerName;
//	String buyername;
//	String noOfShares;
//	Double pricePerShares;
//	Double saleValue;
//	String personConcert;
//	Double percentage;
	public Long getUuid();
	public String getSellerName();
	public String getBuyername();
	public Long getPreviousShares();
	public Long getNoOfShares();
	public Double getPricePerShares();
	public Double getSaleValue();
	public Double getPercentage();
	public String getPersonConcert();
	public Double getProposedShares();
	public Double getPercentageHolding();

	
	
}
