package com.nseit.shareholder1.modelInterfaces;

import java.sql.Timestamp;
import java.util.Date;

import com.nseit.shareholder1.model.BatchModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
public interface TransferMasterExportInterface {

//	private Long uuid;
//	private String buyername;
//	private String sellername;
//	private Double pricePerShare;
//	private Double percentageShare;
//	private Timestamp stageOneDocs;
//	private String comments;
//	private Timestamp principalPending;
//	private Timestamp stageTwoDocs;
//	private Timestamp dateOfExecution;
//	private String movement;
//	private Timestamp sendToRta;
	
	public Long getUuid();
	public String getBuyername();
	public String getSellername();
	public Double getPricePerShare();
	public Double getPercentageShare();
	public Timestamp getStageOneDocs();
	public String getComments();
	public String getPac();
	public Long getnoOfShares();
	public Timestamp getPrincipalPending();
	public Timestamp getStageTwoDocs();
	public Timestamp getDateOfExecution();
	
	
	
}
