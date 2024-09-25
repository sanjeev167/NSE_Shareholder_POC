package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

public class AnnualReport implements Serializable{
	private List<Report> annualReportList;

	public List<Report> getAnnualReportList() {
		return annualReportList;
	}

	public void setAnnualReportList(List<Report> annualReportList) {
		this.annualReportList = annualReportList;
	}

}
