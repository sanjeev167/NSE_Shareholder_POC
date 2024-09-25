package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

public class ShareTransferRequestDetails implements Serializable{
	
	private ShareTransferDetails shareTransferDetails;
	
	private SellerDetails sellerDetails;
	
	private BuyerDetails buyerDetails;

	public ShareTransferDetails getShareTransferDetails() {
		return shareTransferDetails;
	}

	public void setShareTransferDetails(ShareTransferDetails shareTransferDetails) {
		this.shareTransferDetails = shareTransferDetails;
	}

	public SellerDetails getSellerDetails() {
		return sellerDetails;
	}

	public void setSellerDetails(SellerDetails sellerDetails) {
		this.sellerDetails = sellerDetails;
	}

	public BuyerDetails getBuyerDetails() {
		return buyerDetails;
	}

	public void setBuyerDetails(BuyerDetails buyerDetails) {
		this.buyerDetails = buyerDetails;
	}

}
