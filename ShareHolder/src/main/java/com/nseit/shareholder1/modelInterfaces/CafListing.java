package com.nseit.shareholder1.modelInterfaces;

import java.sql.Timestamp;

public interface CafListing {

public Integer getUuid();
public Integer getNoOfShares();
public String getSellername(); 
public String getSellerClientId();
public String getBuyername();
public String getBuyerClientId(); 
public Float getPricePerShare();
public Timestamp getCreatedOn();

public String getSellerNameOfDP();
public String getSellerDpClientId();
public String getBuyerNameOfDP();
public String getBuyerDpClientId();


} 