package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

public class Report implements Serializable{
	
	private File annualReport;

	public File getAnnualReport() {
		return annualReport;
	}

	public void setAnnualReport(File annualReport) {
		this.annualReport = annualReport;
	}

}
