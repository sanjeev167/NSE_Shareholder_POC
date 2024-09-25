package com.nseit.shareholder1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nseit.shareholder1.model.ShareTransferFiles;
import com.nseit.shareholder1.model.ShareTransferMaster;
import org.springframework.data.jpa.repository.Query;
public interface ShareTransferFileDao extends JpaRepository<ShareTransferFiles, Integer> {
	
	
	@Query(value="select * from SHARE_TRANSFER_FILE_MASTER where ID=:id", nativeQuery=true)
	public ShareTransferFiles getShareTransferFilesDetails(Long id);

}
