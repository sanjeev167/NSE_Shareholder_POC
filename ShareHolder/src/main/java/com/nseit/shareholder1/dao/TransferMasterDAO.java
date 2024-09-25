package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.TransferMasterModel;

public interface TransferMasterDAO extends JpaRepository<TransferMasterModel, Long>{

	@Query(value = "select * from TRANSFER_MASTER", nativeQuery = true)
	public List<TransferMasterModel> checkValues();
}
