package com.nseit.shareholder1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PacReportModel {

	private Integer srNo;
	private String name;
	private String category;
	private Long shares;
	private Double percentageShare;

	
	private String otherName;
	private String otherCategory;
	private Long otherShares;
	private Double otherPercentageShare;
	
}
