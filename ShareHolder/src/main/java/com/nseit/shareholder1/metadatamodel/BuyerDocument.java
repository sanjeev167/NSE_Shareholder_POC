package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;
import java.util.List;

//Buyer Documents
public class BuyerDocument implements Serializable{
	
    private BuyerDocuments buyerDocuments;
    
    private AboveTwoPercent aboveTwoPercent;
    
    private AboveFivePercent aboveFivePercent;

    private SrcResolutionDraft srcResolutionDraft;
    
    
    

	public SrcResolutionDraft getSrcResolutionDraft() {
		return srcResolutionDraft;
	}

	public void setSrcResolutionDraft(SrcResolutionDraft srcResolutionDraft) {
		this.srcResolutionDraft = srcResolutionDraft;
	}

	public BuyerDocuments getBuyerDocuments() {
		return buyerDocuments;
	}

	public void setBuyerDocuments(BuyerDocuments buyerDocuments) {
		this.buyerDocuments = buyerDocuments;
	}

	public AboveTwoPercent getAboveTwoPercent() {
		return aboveTwoPercent;
	}

	public void setAboveTwoPercent(AboveTwoPercent aboveTwoPercent) {
		this.aboveTwoPercent = aboveTwoPercent;
	}

	public AboveFivePercent getAboveFivePercent() {
		return aboveFivePercent;
	}

	public void setAboveFivePercent(AboveFivePercent aboveFivePercent) {
		this.aboveFivePercent = aboveFivePercent;
	}
    
    
	

}
