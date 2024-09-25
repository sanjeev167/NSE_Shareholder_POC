package com.nseit.shareholder1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShareholdingDistributionPatternWordModel {

	
	private String srNo;

	
	private String category;


	private String noOfShares;
	
	private String shareholderno;
	
	private String percentage;
}
