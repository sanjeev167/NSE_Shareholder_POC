package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

public class File implements Serializable {
	
	private Integer id;
	private String fileName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
	

}
