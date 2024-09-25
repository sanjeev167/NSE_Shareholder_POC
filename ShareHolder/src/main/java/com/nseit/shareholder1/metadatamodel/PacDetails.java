package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

public class PacDetails implements Serializable{
	
	private List<PacTable> pacDetailsList;
	
	private String dpClientIdPac;
	
	private String name;

	public List<PacTable> getPacDetailsList() {
		return pacDetailsList;
	}

	public void setPacDetailsList(List<PacTable> pacDetailsList) {
		this.pacDetailsList = pacDetailsList;
	}

	public String getDpClientIdPac() {
		return dpClientIdPac;
	}

	public void setDpClientIdPac(String dpClientIdPac) {
		this.dpClientIdPac = dpClientIdPac;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
