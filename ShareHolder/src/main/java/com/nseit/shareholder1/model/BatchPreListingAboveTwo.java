package com.nseit.shareholder1.model;

import java.util.List;

import com.nseit.shareholder1.metadatamodel.NetWorthCibilScore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BatchPreListingAboveTwo {
	
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
//	private NetWorthCibilScore netWorth;
//	private List<Double> networth;
//	private List<Float> cibil;
	private String categoryTransfer;
	private NetWorthCibilScore netWorth;
}
