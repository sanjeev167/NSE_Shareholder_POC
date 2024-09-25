package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.sql.Timestamp;

public class DocumentUpload implements Serializable {

	private static final long serialVersionUID = 1L;

	private BuyerLetter buyerLetter;
	private BuyerLetterDP buyerLetterDP;
	private SellerLetterDP sellerLetterDP;
	private OtherStage2Docs otherStage2Docs;
	private ConfirmationReceipt confirmationReceipt;
	private String comments;
	private String preparedBy;
	private Timestamp preparedOn;
	private String approvedBy;
	private Timestamp approvedOn;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPreparedBy() {
		return preparedBy;
	}

	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	public Timestamp getPreparedOn() {
		return preparedOn;
	}

	public void setPreparedOn(Timestamp preparedOn) {
		this.preparedOn = preparedOn;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Timestamp getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Timestamp approvedOn) {
		this.approvedOn = approvedOn;
	}

	public BuyerLetter getBuyerLetter() {
		return buyerLetter;
	}

	public void setBuyerLetter(BuyerLetter buyerLetter) {
		this.buyerLetter = buyerLetter;
	}

	public BuyerLetterDP getBuyerLetterDP() {
		return buyerLetterDP;
	}

	public void setBuyerLetterDP(BuyerLetterDP buyerLetterDP) {
		this.buyerLetterDP = buyerLetterDP;
	}

	public SellerLetterDP getSellerLetterDP() {
		return sellerLetterDP;
	}

	public void setSellerLetterDP(SellerLetterDP sellerLetterDP) {
		this.sellerLetterDP = sellerLetterDP;
	}

	public OtherStage2Docs getOtherStage2Docs() {
		return otherStage2Docs;
	}

	public void setOtherStage2Docs(OtherStage2Docs otherStage2Docs) {
		this.otherStage2Docs = otherStage2Docs;
	}

	public ConfirmationReceipt getConfirmationReceipt() {
		return confirmationReceipt;
	}

	public void setConfirmationReceipt(ConfirmationReceipt confirmationReceipt) {
		this.confirmationReceipt = confirmationReceipt;
	}
}
