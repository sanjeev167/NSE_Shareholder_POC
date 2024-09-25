package com.nseit.shareholder1.modelInterfaces;

import java.util.List;

public interface BatchListingAboveTwo {
	
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
	
	public List<NetWorthCibilScoreInterface> getNetWorth();
}
