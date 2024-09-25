package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

//Share Transfer Request Details
public class ShareTransferDetails implements Serializable{
	private String modeoftransfer;
	private Integer noOfShares;
	private Float pricePerShare;
	private Float amount;
	private String categoryOfBuyer;
	private String subCategory;
	private String corpListed;
	private String transactionParty;
	public String getModeoftransfer() {
		return modeoftransfer;
	}
	public void setModeoftransfer(String modeoftransfer) {
		this.modeoftransfer = modeoftransfer;
	}
	public Integer getNoOfShares() {
		return noOfShares;
	}
	public void setNoOfShares(Integer noOfShares) {
		this.noOfShares = noOfShares;
	}
	public Float getPricePerShare() {
		return pricePerShare;
	}
	public void setPricePerShare(Float pricePerShare) {
		this.pricePerShare = pricePerShare;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getCategoryOfBuyer() {
		return categoryOfBuyer;
	}
	public void setCategoryOfBuyer(String categoryOfBuyer) {
		this.categoryOfBuyer = categoryOfBuyer;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getCorpListed() {
		return corpListed;
	}
	public void setCorpListed(String corpListed) {
		this.corpListed = corpListed;
	}
	public String getTransactionParty() {
		return transactionParty;
	}
	public void setTransactionParty(String transactionParty) {
		this.transactionParty = transactionParty;
	}
	



	




}
