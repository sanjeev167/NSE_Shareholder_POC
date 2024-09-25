package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.sql.Date;

public class SrcResolutionDraft implements Serializable {

	private File resolution;
	private Date resDate;
	private Long resNum;

	public File getResolution() {
		return resolution;
	}

	public void setResolution(File resolution) {
		this.resolution = resolution;
	}

	public Date getResDate() {
		return resDate;
	}

	public void setResDate(Date resDate) {
		this.resDate = resDate;
	}

	public Long getResNum() {
		return resNum;
	}

	public void setResNum(Long resNum) {
		this.resNum = resNum;
	}

}
