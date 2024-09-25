package com.nseit.shareholder1.modelInterfaces;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;

import com.nseit.shareholder1.metadatamodel.MetadataPojo;

public interface DashboardInterface {


	


	public Long getId();


	public String getSellerClientId();


	public String getSellerName();


	public String getBuyerClientID();


	public String getBuyerName();


	public Double getPricePerShares();


	public Integer getNoOfShares();

	public Double getAmount();


	public String getPersonConcert();


	public Long getUuid();

	public String getCreatedBy();

	public String getStatus();


	public String getCreatedOn();
	
	public String getSellerPan();
	
	public String getBuyerPan();
	
	
}
