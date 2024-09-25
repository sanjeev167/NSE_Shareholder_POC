package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.ShareTransferRoleType;
import com.nseit.shareholder1.modelInterfaces.ShareTransferRoleInterface;

public interface ShareTransferRoleTypeDao extends JpaRepository<ShareTransferRoleType, Long> {
	
	@Query(value="select * from (select * from SHARE_TRANSFER_WORKFLOW WHERE UUID=:uuid order by ID DESC)T WHERE  ROWNUM=1", nativeQuery = true)
	public ShareTransferRoleType getAllShareTransferRoleType(Long uuid);

	@Query(value = "select * from (select * from SHARE_TRANSFER_WORKFLOW WHERE UUID=:uuid order by ID DESC)T WHERE  ROWNUM=1", nativeQuery = true)
	public List<ShareTransferRoleType> getAllShareTransfersRoleType(@Param("uuid") List<Integer> uuid);
	@Query(value = "select * from share_transfer_workflow WHERE UUID in (:uuid)  AND id in(select max(id)from share_transfer_workflow group by uuid) ", nativeQuery = true)
	public List<ShareTransferRoleType> getAllShareTransfersRoleTypeBatch(@Param("uuid") List<Integer> uuid);
	
	@Query(value="select ID as id, MAKER as maker, CHECKER as checker, BATCH_CREATED as batchCreated, CREATED_BY as createdBY, UUID as uuid, STATUS as status, SHARETRANSFER_ID as sharetransferId from SHARE_TRANSFER_WORKFLOW where SHARETRANSFER_ID in (select id from SHARE_TRANSFER_MASTER)", nativeQuery = true)
	public List<ShareTransferRoleInterface> getAllShareTransferRoleDetails();
	
	@Query(value = "SELECT * FROM SHARE_TRANSFER_WORKFLOW WHERE maker is null or maker='N' and sharetransfer_id in (SELECT id from SHARE_TRANSFER_MASTER)",nativeQuery = true)
	public List<ShareTransferRoleInterface> getMakerDetails();
	
	@Query(value = "SELECT * FROM SHARE_TRANSFER_WORKFLOW WHERE checker is null or checker='N'",nativeQuery = true)
	public List<ShareTransferRoleInterface> getCheckerDetails();
	
	@Query(value="select id,MAKER as maker,CHECKER as checker,BATCH_CREATED as batchCreated,CREATED_BY as createdby,UUID as uuid,STATUS as status,COMMENTS as comments,CREATED_ON as createdOn from SHARE_TRANSFER_WORKFLOW WHERE UUID=:uuid order by ID DESC", nativeQuery = true)
	public List<ShareTransferRoleInterface> getAllShareTransferRoleType1(Long uuid);
}
