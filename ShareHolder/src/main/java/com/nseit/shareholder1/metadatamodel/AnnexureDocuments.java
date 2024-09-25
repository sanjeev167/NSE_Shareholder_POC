package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

public class AnnexureDocuments implements Serializable{
	
	private File listOfSignatories;
    private File fipbApproval;
    private File sebiApproval;
    private File rbiApproval;
    private File annexure8;
    private File annexure9_i;
    private File domesticInvestment;
    private File shareHoldingPattern;
    private File sebiRegistrationCertificate;
	public File getListOfSignatories() {
		return listOfSignatories;
	}
	public void setListOfSignatories(File listOfSignatories) {
		this.listOfSignatories = listOfSignatories;
	}
	public File getFipbApproval() {
		return fipbApproval;
	}
	public void setFipbApproval(File fipbApproval) {
		this.fipbApproval = fipbApproval;
	}
	public File getSebiApproval() {
		return sebiApproval;
	}
	public void setSebiApproval(File sebiApproval) {
		this.sebiApproval = sebiApproval;
	}
	public File getRbiApproval() {
		return rbiApproval;
	}
	public void setRbiApproval(File rbiApproval) {
		this.rbiApproval = rbiApproval;
	}
	public File getAnnexure8() {
		return annexure8;
	}
	public void setAnnexure8(File annexure8) {
		this.annexure8 = annexure8;
	}
	public File getAnnexure9_i() {
		return annexure9_i;
	}
	public void setAnnexure9_i(File annexure9_i) {
		this.annexure9_i = annexure9_i;
	}
	public File getDomesticInvestment() {
		return domesticInvestment;
	}
	public void setDomesticInvestment(File domesticInvestment) {
		this.domesticInvestment = domesticInvestment;
	}
	public File getShareHoldingPattern() {
		return shareHoldingPattern;
	}
	public void setShareHoldingPattern(File shareHoldingPattern) {
		this.shareHoldingPattern = shareHoldingPattern;
	}
	public File getSebiRegistrationCertificate() {
		return sebiRegistrationCertificate;
	}
	public void setSebiRegistrationCertificate(File sebiRegistrationCertificate) {
		this.sebiRegistrationCertificate = sebiRegistrationCertificate;
	}
    
	
    

}
