package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

public class CmlDetailsBuyerDetails implements Serializable{
	
	private List<String> cmlTable;
	
	private String nameOfBuyerCml;

	public List<String> getCmlTable() {
		return cmlTable;
	}

	public void setCmlTable(List<String> cmlTable) {
		this.cmlTable = cmlTable;
	}

	public String getNameOfBuyerCml() {
		return nameOfBuyerCml;
	}

	public void setNameOfBuyerCml(String nameOfBuyerCml) {
		this.nameOfBuyerCml = nameOfBuyerCml;
	}
	
	

}

