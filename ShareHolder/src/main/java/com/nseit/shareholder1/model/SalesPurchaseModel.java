package com.nseit.shareholder1.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalesPurchaseModel {

	private String modifiedOn;
	
	private String categoryTransfer;
	
	private Long noOfShares;

	private String pricePerShare;

	private String transactionParty;

	
}
