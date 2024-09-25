package com.nseit.shareholder1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.metadatamodel.MetadataMaster;


public interface MetadataMasterDao extends JpaRepository<MetadataMaster, Integer>{
    @Query(value="select * from SHARE_TRANSFER_METADATA_MASTER where METADATA_VERSION=:metadataVersion",nativeQuery=true)
	public MetadataMaster getMetdataDetails(@Param("metadataVersion") int metadataVersion);

}
