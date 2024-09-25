package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

public class PacTable implements Serializable{
	
    private String dpClientIdPac;
    private String name;
	
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
