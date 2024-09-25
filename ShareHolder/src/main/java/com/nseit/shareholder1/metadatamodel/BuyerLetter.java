package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

public class BuyerLetter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private File letterFromBuyer;
	
	
	
	public File getLetterFromBuyer() {
		return letterFromBuyer;
	}
	public void setLetterFromBuyer(File letterFromBuyer) {
		this.letterFromBuyer = letterFromBuyer;
	}
	

}
