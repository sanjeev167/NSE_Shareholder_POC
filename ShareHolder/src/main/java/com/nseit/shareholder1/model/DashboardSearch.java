package com.nseit.shareholder1.model;

import java.io.Serializable;

import com.nseit.shareholder1.metadatamodel.MetadataPojo;

public class DashboardSearch implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String sellerClientId;

	private String sellerName;

	private String buyerClientID;

	private String buyerName;

	private Double pricePerShares;

	private Integer noOfShares;

	private Long amount;

	private String personConcert;

	private Long uuid;

	private String createdBy;

	private String status;

	private String createdOn;
	
	private String buyerPan;
	
	private String sellerPan;
	
	public String getSellerPan() {
		return sellerPan;
	}

	public void setSellerPan(String sellerPan) {
		this.sellerPan = sellerPan;
	}

	private MetadataPojo metadata;

	
	public MetadataPojo getMetadata() {
		return metadata;
	}

	public void setMetadata(MetadataPojo metadata) {
		this.metadata = metadata;
	}

	public String getBuyerPan() {
		return buyerPan;
	}

	public void setBuyerPan(String buyerPan) {
		this.buyerPan = buyerPan;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSellerClientId() {
		return sellerClientId;
	}

	public void setSellerClientId(String sellerClientId) {
		this.sellerClientId = sellerClientId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getBuyerClientID() {
		return buyerClientID;
	}

	public void setBuyerClientID(String buyerClientID) {
		this.buyerClientID = buyerClientID;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Double getPricePerShares() {
		return pricePerShares;
	}

	public void setPricePerShares(Double pricePerShares) {
		this.pricePerShares = pricePerShares;
	}

	public Integer getNoOfShares() {
		return noOfShares;
	}

	public void setNoOfShares(Integer noOfShares) {
		this.noOfShares = noOfShares;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getPersonConcert() {
		return personConcert;
	}

	public void setPersonConcert(String personConcert) {
		this.personConcert = personConcert;
	}

	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public DashboardSearch(Long id, String sellerClientId, String sellerName, String buyerClientID, String buyerName,
			Double pricePerShares, Integer noOfShares, Long amount, String personConcert, Long uuid, String createdBy,
			String status, String createdOn, Object metadata,String pan) {
		super();
		this.id = id;
		this.sellerClientId = sellerClientId;
		this.sellerName = sellerName;
		this.buyerClientID = buyerClientID;
		this.buyerName = buyerName;
		this.pricePerShares = pricePerShares;
		this.noOfShares = noOfShares;
		this.amount = amount;
		this.personConcert = personConcert;
		this.uuid = uuid;
		this.createdBy = createdBy;
		this.status = status;
		this.createdOn = createdOn;

		
		this.metadata=(MetadataPojo)metadata;
		this.buyerPan=this.metadata.getShareTransferRequestDetails().getBuyerDetails().getPanOfBuyer();
		this.metadata=null;
		this.sellerPan=pan;
	}

}
