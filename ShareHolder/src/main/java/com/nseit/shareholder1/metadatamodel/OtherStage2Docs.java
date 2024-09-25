package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

public class OtherStage2Docs implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stampDuty;
	private String utrId;
	private File stampDoc;
	private File sellerCml;
	public String getStampDuty() {
		return stampDuty;
	}
	public void setStampDuty(String stampDuty) {
		this.stampDuty = stampDuty;
	}
	public String getUtrId() {
		return utrId;
	}
	public void setUtrId(String utrId) {
		this.utrId = utrId;
	}
	public File getStampDoc() {
		return stampDoc;
	}
	public void setStampDoc(File stampDoc) {
		this.stampDoc = stampDoc;
	}
	public File getSellerCml() {
		return sellerCml;
	}
	public void setSellerCml(File sellerCml) {
		this.sellerCml = sellerCml;
	}
	
	
	
	
	
}
