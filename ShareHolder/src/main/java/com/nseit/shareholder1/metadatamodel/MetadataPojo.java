package com.nseit.shareholder1.metadatamodel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class MetadataPojo {

	private ShareTransferRequestDetails shareTransferRequestDetails;
	
	private BuyerDocument buyerDocument;
	
	private StageTwo stageTwo;
	
	private DocumentUpload documentUpload;
	
	private Integer metadataVersion;
	
	private RegulatoryChecks regulatoryChecks;
	
	
	
	public RegulatoryChecks getRegulatoryChecks() {
		return regulatoryChecks;
	}

	public void setRegulatoryChecks(RegulatoryChecks regulatoryChecks) {
		this.regulatoryChecks = regulatoryChecks;
	}

	public DocumentUpload getDocumentUpload() {
		return documentUpload;
	}

	public void setDocumentUpload(DocumentUpload documentUpload) {
		this.documentUpload = documentUpload;
	}

	public ShareTransferRequestDetails getShareTransferRequestDetails() {
		return shareTransferRequestDetails;
	}

	public void setShareTransferRequestDetails(ShareTransferRequestDetails shareTransferRequestDetails) {
		this.shareTransferRequestDetails = shareTransferRequestDetails;
	}

	public BuyerDocument getBuyerDocument() {
		return buyerDocument;
	}

	public void setBuyerDocument(BuyerDocument buyerDocument) {
		this.buyerDocument = buyerDocument;
	}

	public StageTwo getStageTwo() {
		return stageTwo;
	}

	public void setStageTwo(StageTwo stageTwo) {
		this.stageTwo = stageTwo;
	}

	
	

	public Integer getMetadataVersion() {
		return metadataVersion;
	}

	public void setMetadataVersion(Integer metadataVersion) {
		this.metadataVersion = metadataVersion;
	}

}
