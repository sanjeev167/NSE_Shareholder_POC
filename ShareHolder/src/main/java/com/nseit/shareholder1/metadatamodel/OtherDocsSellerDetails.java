package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

public class OtherDocsSellerDetails implements Serializable{
	
	private List<File> otherDocList;
	private File otherDocument;
	public List<File> getOtherDocList() {
		return otherDocList;
	}
	public void setOtherDocList(List<File> otherDocList) {
		this.otherDocList = otherDocList;
	}
	public File getOtherDocument() {
		return otherDocument;
	}
	public void setOtherDocument(File otherDocument) {
		this.otherDocument = otherDocument;
	}
	
	

}
