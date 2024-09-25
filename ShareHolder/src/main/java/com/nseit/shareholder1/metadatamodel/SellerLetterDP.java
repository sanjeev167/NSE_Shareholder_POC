package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

public class SellerLetterDP implements Serializable {

	private static final long serialVersionUID = 1L;

	private File letterFromDpofSeller;
	private File sellerBoardResolution;
	private File dis;
	public File getLetterFromDpofSeller() {
		return letterFromDpofSeller;
	}
	public void setLetterFromDpofSeller(File letterFromDpofSeller) {
		this.letterFromDpofSeller = letterFromDpofSeller;
	}
	public File getSellerBoardResolution() {
		return sellerBoardResolution;
	}
	public void setSellerBoardResolution(File sellerBoardResolution) {
		this.sellerBoardResolution = sellerBoardResolution;
	}
	public File getDis() {
		return dis;
	}
	public void setDis(File dis) {
		this.dis = dis;
	}
	
	
	
	
	
}
