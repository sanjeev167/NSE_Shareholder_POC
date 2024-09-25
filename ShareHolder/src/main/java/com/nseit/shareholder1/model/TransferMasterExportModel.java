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
public class TransferMasterExportModel {
	
	private Long uuid;
	private String buyername;
	private String sellername;
	private Double pricePerShare;
	private Double percentageShare;
	private Timestamp stageOneDocs;
	private String comments;
	private Timestamp principalPending;
	private Timestamp stageTwoDocs;
	private Timestamp dateOfExecution;
	private String movement;
	private Timestamp sendToRta;
	private String pac;
	private Long noOfShares;
	private String belowFive;
}
