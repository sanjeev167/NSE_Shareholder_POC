package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

//Above 2% Third Party Checks
public class AboveTwoPercent implements Serializable{
	
	private NetWorthCibilScore netWorthCibilScore;
	
	private AnnualReport annualReport;
	
	private OtherDocsAboveTwoPercent otherDocs;

	public NetWorthCibilScore getNetWorthCibilScore() {
		return netWorthCibilScore;
	}

	public void setNetWorthCibilScore(NetWorthCibilScore netWorthCibilScore) {
		this.netWorthCibilScore = netWorthCibilScore;
	}

	public AnnualReport getAnnualReport() {
		return annualReport;
	}

	public void setAnnualReport(AnnualReport annualReport) {
		this.annualReport = annualReport;
	}

	public OtherDocsAboveTwoPercent getOtherDocs() {
		return otherDocs;
	}

	public void setOtherDocs(OtherDocsAboveTwoPercent otherDocs) {
		this.otherDocs = otherDocs;
	}
	
	
	
	
	

}
