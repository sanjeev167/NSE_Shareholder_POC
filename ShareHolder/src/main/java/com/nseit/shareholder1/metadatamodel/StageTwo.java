package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

//Stage2
public class StageTwo implements Serializable{
//	Letter from Purchaser
	private File purchaseLetter;
//	Confirmation of receipt of sale consideration
	private File receiptConfirmation;
//	Letter from DP of Purchaser
    private File dpPurchaserLetter;
//    Letter to be given by transferror DP to NSE & NSDL alongwith DIS
    private File transferrorDpLetter;
//    Stamp Duty paid or not
    private String stampDuty;
//    UTR ID
    private String utrId;
	public File getPurchaseLetter() {
		return purchaseLetter;
	}
	public void setPurchaseLetter(File purchaseLetter) {
		this.purchaseLetter = purchaseLetter;
	}
	public File getReceiptConfirmation() {
		return receiptConfirmation;
	}
	public void setReceiptConfirmation(File receiptConfirmation) {
		this.receiptConfirmation = receiptConfirmation;
	}
	public File getDpPurchaserLetter() {
		return dpPurchaserLetter;
	}
	public void setDpPurchaserLetter(File dpPurchaserLetter) {
		this.dpPurchaserLetter = dpPurchaserLetter;
	}
	public File getTransferrorDpLetter() {
		return transferrorDpLetter;
	}
	public void setTransferrorDpLetter(File transferrorDpLetter) {
		this.transferrorDpLetter = transferrorDpLetter;
	}
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
    
	
    
    
	
    




}
