package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

public class OtherDocuments implements Serializable{
    private List<File> otherList;

	public List<File> getOtherList() {
		return otherList;
	}

	public void setOtherList(List<File> otherList) {
		this.otherList = otherList;
	}
    
    
}
