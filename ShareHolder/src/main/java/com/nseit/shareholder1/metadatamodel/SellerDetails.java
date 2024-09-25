package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

public class SellerDetails implements Serializable{
	private String nameOfSeller;
	private String nameOfDP;
	private String dpClientIdSeller;
	private File annexure7i;
	private File annexure7ii;
	private File annexure9ii;
	private File annexure4ii;
	private File certifiedResolution;
	private OtherDocsSellerDetails otherDocsSellerDetails;
	public String getNameOfSeller() {
		return nameOfSeller;
	}
	public void setNameOfSeller(String nameOfSeller) {
		this.nameOfSeller = nameOfSeller;
	}
	public String getNameOfDP() {
		return nameOfDP;
	}
	public void setNameOfDP(String nameOfDP) {
		this.nameOfDP = nameOfDP;
	}
	public String getDpClientIdSeller() {
		return dpClientIdSeller;
	}
	public void setDpClientIdSeller(String dpClientIdSeller) {
		this.dpClientIdSeller = dpClientIdSeller;
	}
	public File getAnnexure7i() {
		return annexure7i;
	}
	public void setAnnexure7i(File annexure7i) {
		this.annexure7i = annexure7i;
	}
	public File getAnnexure7ii() {
		return annexure7ii;
	}
	public void setAnnexure7ii(File annexure7ii) {
		this.annexure7ii = annexure7ii;
	}
	public File getAnnexure9ii() {
		return annexure9ii;
	}
	public void setAnnexure9ii(File annexure9ii) {
		this.annexure9ii = annexure9ii;
	}
	public File getAnnexure4ii() {
		return annexure4ii;
	}
	public void setAnnexure4ii(File annexure4ii) {
		this.annexure4ii = annexure4ii;
	}
	public File getCertifiedResolution() {
		return certifiedResolution;
	}
	public void setCertifiedResolution(File certifiedResolution) {
		this.certifiedResolution = certifiedResolution;
	}
	public OtherDocsSellerDetails getOtherDocsSellerDetails() {
		return otherDocsSellerDetails;
	}
	public void setOtherDocsSellerDetails(OtherDocsSellerDetails otherDocsSellerDetails) {
		this.otherDocsSellerDetails = otherDocsSellerDetails;
	}
	
	
}
