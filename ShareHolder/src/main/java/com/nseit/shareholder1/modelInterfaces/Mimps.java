package com.nseit.shareholder1.modelInterfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.BenposeDataMasterModel;
import com.nseit.shareholder1.modelInterfaces.CafListing;

public interface Mimps {
	
	
	public String getName();
	
	public Integer getShares();
	
	public Double getPercentageShare();
	
	
	
	
}
