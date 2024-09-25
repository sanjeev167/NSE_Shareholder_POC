package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

public class NetWorth implements Serializable{
	
	private String buyerName;
	
	private Double netWorth;
	
	private File netWorthCertificate;
	
	private Float cibilScore;
	
	private File cibilReport;

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Double getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(Double netWorth) {
		this.netWorth = netWorth;
	}

	public File getNetWorthCertificate() {
		return netWorthCertificate;
	}

	public void setNetWorthCertificate(File netWorthCertificate) {
		this.netWorthCertificate = netWorthCertificate;
	}

	public Float getCibilScore() {
		return cibilScore;
	}

	public void setCibilScore(Float cibilScore) {
		this.cibilScore = cibilScore;
	}

	public File getCibilReport() {
		return cibilReport;
	}

	public void setCibilReport(File cibilReport) {
		this.cibilReport = cibilReport;
	}
	
	
	
	

}
