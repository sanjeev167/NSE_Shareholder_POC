package com.nseit.shareholder1.modelInterfaces;

import java.util.List;

import com.nseit.shareholder1.metadatamodel.PacTable;

public interface PacTableInterface {

	public List<PacTable> getPacDetailsList();

	public String getDpClientIdPac();

	public String getName();

}
