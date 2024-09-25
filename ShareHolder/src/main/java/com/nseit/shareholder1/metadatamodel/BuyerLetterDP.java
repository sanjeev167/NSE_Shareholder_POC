package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

public class BuyerLetterDP implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private File letterFromDpofBuyer;
	private File buyerBoardResolution;
	public File getLetterFromDpofBuyer() {
		return letterFromDpofBuyer;
	}
	public void setLetterFromDpofBuyer(File letterFromDpofBuyer) {
		this.letterFromDpofBuyer = letterFromDpofBuyer;
	}
	public File getBuyerBoardResolution() {
		return buyerBoardResolution;
	}
	public void setBuyerBoardResolution(File buyerBoardResolution) {
		this.buyerBoardResolution = buyerBoardResolution;
	}
	
	
	
}
