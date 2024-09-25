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
public class AverageWeightedReportModel {

	private Long uuid;
	private String sellername;
	private String buyername;
	private Long noofshares;
	private Double pricepershare;
	private Double amount;
	private String categoryofbuyer;
	private Timestamp dateofapproval;
	private Timestamp dateofexecution;
	private String buyerpac;
//	private PacDetails pacdetailslist;
	private String pacdetailslist;
	private String movement;
	
}
