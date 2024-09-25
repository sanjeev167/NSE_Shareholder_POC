package com.nseit.shareholder1.metadatamodel;

import java.io.Serializable;

public class BuyerDocuments implements Serializable {
   private Annexure annexure;
   
   private AnnexureDocuments annexureDocuments;
   
   private OtherDocuments otherDocuments;
   
   

public Annexure getAnnexure() {
	return annexure;
}

public void setAnnexure(Annexure annexure) {
	this.annexure = annexure;
}

public AnnexureDocuments getAnnexureDocuments() {
	return annexureDocuments;
}

public void setAnnexureDocuments(AnnexureDocuments annexureDocuments) {
	this.annexureDocuments = annexureDocuments;
}

public OtherDocuments getOtherDocuments() {
	return otherDocuments;
}

public void setOtherDocuments(OtherDocuments otherDocuments) {
	this.otherDocuments = otherDocuments;
}
   
   
}
