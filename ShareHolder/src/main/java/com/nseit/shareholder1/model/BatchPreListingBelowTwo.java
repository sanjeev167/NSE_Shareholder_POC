package com.nseit.shareholder1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BatchPreListingBelowTwo {
	private Long uuid;
	private String sellerName;
	private String buyername;
	private Long previousShares;
	private Long noOfShares;
	private Double pricePerShares;
	private Double saleValue;
	private Double percentage;
	private String personConcert;
	private Double proposedShares;
	private Double percentageHolding;
	private String categoryTransfer;
	
}
