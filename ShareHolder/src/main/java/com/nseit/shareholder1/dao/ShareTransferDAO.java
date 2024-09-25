package com.nseit.shareholder1.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.metadatamodel.CategoryTable;
import com.nseit.shareholder1.metadatamodel.MetadataPojo;
import com.nseit.shareholder1.model.ShareTransferFiles;
import com.nseit.shareholder1.model.ShareTransferMaster;
import com.nseit.shareholder1.modelInterfaces.ApprovalEmailStatus;
import com.nseit.shareholder1.modelInterfaces.AverageWeightedReportInterface;
import com.nseit.shareholder1.modelInterfaces.BeneficiaryDetailsInterface;
import com.nseit.shareholder1.modelInterfaces.BenposeHoldingInterface;
import com.nseit.shareholder1.modelInterfaces.CafListing;
import com.nseit.shareholder1.modelInterfaces.CategoryTableInterface;
import com.nseit.shareholder1.modelInterfaces.DashboardInterface;
import com.nseit.shareholder1.modelInterfaces.ReportsModel;
import com.nseit.shareholder1.modelInterfaces.SalesPurchaseInterface;
import com.nseit.shareholder1.modelInterfaces.ShareHoldingPatternTemplateInterface;
import com.nseit.shareholder1.modelInterfaces.ShareTransferInterface;
import com.nseit.shareholder1.modelInterfaces.TaxTeamInterface;
import com.nseit.shareholder1.modelInterfaces.TransferDetailsInterface;
import com.nseit.shareholder1.modelInterfaces.TransferMasterExportInterface;

public interface ShareTransferDAO extends JpaRepository<ShareTransferMaster, Long> {
	@Query(value = "select * from(select * from SHARE_TRANSFER_MASTER WHERE SELLER_CLIENT_ID=:sellerClientId AND BUYER_CLIENT_ID=:buyerClientId AND PRICE_PER_SHARES=:pricePerShares AND NO_OF_SHARES=:noOfShares  order by CREATED_ON DESC) T WHERE  ROWNUM=1", nativeQuery = true)
	public ShareTransferMaster getDetails(@Param("sellerClientId") String sellerClientId,
			@Param("buyerClientId") String buyerClientId, @Param("pricePerShares") Long pricePerShares,
			@Param("noOfShares") int noOfShares);

	@Query(value = "select MAX(UUID) from SHARE_TRANSFER_MASTER", nativeQuery = true)
	public Long getMaxUuidDetail();

	@Query(value = "select count(*) from (select * from SHARE_TRANSFER_MASTER  order by CREATED_ON desc)  T WHERE  ROWNUM=1", nativeQuery = true)
	public Integer getShareTranferDetails();

	@Query(value = "select * from (select * from SHARE_TRANSFER_MASTER WHERE UUID=:uuid order by ID DESC)T WHERE  ROWNUM=1", nativeQuery = true)
	public ShareTransferMaster getUuidDetails(Long uuid);
	
	@Query(value = "select * from SHARE_TRANSFER_MASTER WHERE ID=:id order by CREATED_ON DESC", nativeQuery = true)
	public List<ShareTransferMaster> getShareTranferDetailsBasedOnId(Long id);

	// @Query(value = "SELECT ID ,SELLER_CLIENT_ID as
	// sellerClientId,SELLERNAME,BUYER_CLIENT_ID as
	// buyerClientId,BUYERNAME,NO_OF_SHARES as noOfShares,PRICE_PER_SHARES as
	// pricePerShares,AMOUNT,PERSON_CONCERT as personConcert,UUID,STATUS,CREATED_BY
	// as createdBy,CREATED_ON as
	// createdOn,json_Query(METADATA,'$.shareTransferRequestDetails.buyerDetails.panOfBuyer')
	// as BuyerPan,
	// json_Query(METADATA,'$.shareTransferRequestDetails.buyerDetails.categoryDetails.pan')
	// as SellerPan FROM SHARE_TRANSFER_MASTER p WHERE p.UUID LIKE %?1%"
	// + " OR p.SELLER_CLIENT_ID LIKE %?1%"
	// + " OR p.BUYER_CLIENT_ID LIKE %?1%"
	// + " OR p.SELLERNAME LIKE %?1%"
	// + " OR p.BUYERNAME LIKE %?1%"
	// + " OR p.STATUS LIKE %?1%", nativeQuery = true)
	// public List<DashboardInterface> search(String keyword);
	//
	// @Query(value = "select ID ,SELLER_CLIENT_ID as
	// sellerClientId,SELLERNAME,BUYER_CLIENT_ID as
	// buyerClientId,BUYERNAME,NO_OF_SHARES as noOfShares,PRICE_PER_SHARES as
	// pricePerShares,AMOUNT,PERSON_CONCERT as personConcert,UUID,STATUS,CREATED_BY
	// as createdBy,CREATED_ON as createdOn from SHARE_TRANSFER_MASTER WHERE
	// UUID=:uuid AND STATUS=:status AND BUYER_CLIENT_ID=:buyerClientId AND
	// SELLER_CLIENT_ID=:sellerClientId AND SELLERNAME=:sellerName AND
	// BUYERNAME=:buyerName", nativeQuery = true)
	// public List<DashboardInterface> searchByExampleMatcher(Long uuid, String
	// status,String buyerClientId,String sellerClientId,String sellerName,String
	// buyerName);

	@Query(value = "select ID ,SELLER_CLIENT_ID as sellerClientId,SELLERNAME,BUYER_CLIENT_ID as buyerClientId,BUYERNAME,NO_OF_SHARES as noOfShares,PRICE_PER_SHARES as pricePerShares,AMOUNT,PERSON_CONCERT as personConcert,UUID,STATUS,CREATED_BY as createdBy,CREATED_ON as createdOn from SHARE_TRANSFER_MASTER", nativeQuery = true)
	public List<DashboardInterface> getAllShareTransferData();

	@Query(value = "select ID ,SELLER_CLIENT_ID as sellerClientId,SELLERNAME,BUYER_CLIENT_ID as buyerClientId,BUYERNAME,NO_OF_SHARES as noOfShares,PRICE_PER_SHARES as pricePerShares,AMOUNT,PERSON_CONCERT as personConcert,UUID,STATUS,CREATED_BY as createdBy,CREATED_ON as createdOn from SHARE_TRANSFER_MASTER where CREATED_BY=:createdby and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER   group by UUID)", nativeQuery = true)
	public List<DashboardInterface> getShareholderAllShareTransferDetails(String createdby);

	@Query(value = "select s.ID ,SELLER_CLIENT_ID as sellerClientId,SELLERNAME,BUYER_CLIENT_ID as buyerClientId,BUYERNAME,NO_OF_SHARES as noOfShares,PRICE_PER_SHARES as pricePerShares,AMOUNT,PERSON_CONCERT as personConcert,s.UUID,s.STATUS,s.CREATED_BY as createdBy,s.CREATED_ON as createdOn from share_transfer_master s  where s.STATUS='STAGE_ONE_VERIFICATION_PENDING' and \r\n"
			+ "s.id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER   group by UUID) and \r\n"
			+ "s.id not in (SELECT MAX(sharetransfer_id) from SHARE_TRANSFER_WORKFLOW where uuid=s.uuid  group by uuid)", nativeQuery = true)
	public List<DashboardInterface> getVerifierShareTransferDetailsStageOneForMaker();

	@Query(value = "select s.ID ,SELLER_CLIENT_ID as sellerClientId,SELLERNAME,BUYER_CLIENT_ID as buyerClientId,BUYERNAME,NO_OF_SHARES as noOfShares,PRICE_PER_SHARES as pricePerShares,AMOUNT,PERSON_CONCERT as personConcert,s.UUID,s.STATUS,s.CREATED_BY as createdBy,s.CREATED_ON as createdOn from share_transfer_master s inner join SHARE_TRANSFER_WORKFLOW st on s.id=st.sharetransfer_id where st.maker='Y' and st.checker is null and s.STATUS='STAGE_ONE_VERIFICATION_PENDING' and \r\n"
			+ "s.id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID) and  s.id  in (SELECT MAX(sharetransfer_id) from SHARE_TRANSFER_WORKFLOW group by uuid)", nativeQuery = true)
	public List<DashboardInterface> getVerifierShareTransferDetailsStageOneForChecker();

	// @Query(value = "select ID ,SELLER_CLIENT_ID as
	// sellerClientId,SELLERNAME,BUYER_CLIENT_ID as
	// buyerClientId,BUYERNAME,NO_OF_SHARES as noOfShares,PRICE_PER_SHARES as
	// pricePerShares,AMOUNT,PERSON_CONCERT as personConcert,UUID,STATUS,CREATED_BY
	// as createdBy,CREATED_ON as createdOn from SHARE_TRANSFER_MASTER where
	// STATUS='STAGE_TWO_VERIFICATION_PENDING' and id in(SELECT MAX(id) from
	// SHARE_TRANSFER_MASTER group by UUID)", nativeQuery = true)
	// public List<DashboardInterface> getVerifierShareTransferDetailsStageTwo();

	@Query(value = "select s.ID ,SELLER_CLIENT_ID as sellerClientId,SELLERNAME,BUYER_CLIENT_ID as buyerClientId,BUYERNAME,NO_OF_SHARES as noOfShares,PRICE_PER_SHARES as pricePerShares,AMOUNT,PERSON_CONCERT as personConcert,s.UUID,s.STATUS,s.CREATED_BY as createdBy,s.CREATED_ON as createdOn from share_transfer_master s  where s.STATUS='STAGE_TWO_VERIFICATION_PENDING' and \r\n"
			+ "s.id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER   group by UUID) and \r\n"
			+ "s.id not in (SELECT MAX(sharetransfer_id) from SHARE_TRANSFER_WORKFLOW where uuid=s.uuid  group by uuid)", nativeQuery = true)
	public List<DashboardInterface> getVerifierShareTransferDetailsStageTwoForMaker();

	@Query(value = "select s.ID ,SELLER_CLIENT_ID as sellerClientId,SELLERNAME,BUYER_CLIENT_ID as buyerClientId,BUYERNAME,NO_OF_SHARES as noOfShares,PRICE_PER_SHARES as pricePerShares,AMOUNT,PERSON_CONCERT as personConcert,s.UUID,s.STATUS,s.CREATED_BY as createdBy,s.CREATED_ON as createdOn from share_transfer_master s inner join SHARE_TRANSFER_WORKFLOW st on s.id=st.sharetransfer_id where st.maker='Y' and st.checker is null and s.STATUS='STAGE_TWO_VERIFICATION_PENDING' and \r\n"
			+ "s.id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER   group by UUID) and  s.id  in (SELECT MAX(sharetransfer_id) from SHARE_TRANSFER_WORKFLOW  group by uuid)", nativeQuery = true)
	public List<DashboardInterface> getVerifierShareTransferDetailsStageTwoForChecker();

	@Query(value = "select ID ,SELLER_CLIENT_ID as sellerClientId,SELLERNAME,BUYER_CLIENT_ID as buyerClientId,BUYERNAME,NO_OF_SHARES as noOfShares,PRICE_PER_SHARES as pricePerShares,AMOUNT,PERSON_CONCERT as personConcert,UUID,STATUS,CREATED_BY as createdBy,CREATED_ON as createdOn from SHARE_TRANSFER_MASTER where STATUS='TRANSFER_PENDING_FROM_DEPOSITORY' and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER   group by UUID)", nativeQuery = true)
	public List<DashboardInterface> getShareTransferDetailsStageTwoConfirmation();

	@Query(value = "select ID ,SELLER_CLIENT_ID as sellerClientId,SELLERNAME,BUYER_CLIENT_ID as buyerClientId,BUYERNAME,NO_OF_SHARES as noOfShares,PRICE_PER_SHARES as pricePerShares,AMOUNT,PERSON_CONCERT as personConcert,UUID,STATUS,CREATED_BY as createdBy,CREATED_ON as createdOn from SHARE_TRANSFER_MASTER where CREATED_BY=:createdby and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER   group by UUID)", nativeQuery = true)
	public List<DashboardInterface> getInternalApplication(String createdby);

	// @Query(value = "select sum(NO_OF_SHARES) as totalShares,category from(select
	// no_of_shares,json_value(METADATA,
	// '$.shareTransferRequestDetails.buyerDetails.buyerResidentType') as category
	// from share_transfer_master where id in(SELECT MAX(id) from
	// SHARE_TRANSFER_MASTER group by UUID)) group by category", nativeQuery = true)
	// public List<ReportsModel> totalShareTransferShares();

	@Query(value = "select  typeOfStatus,sum(NO_OF_SHARES) as totalShares,trunc(100*(sum(NO_OF_SHARES)/sum(sum(NO_OF_SHARES)) over()),2) as percentageShares from(select no_of_shares,json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerResidentType') as typeOfStatus from share_transfer_master where id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER   group by UUID)) group by typeOfStatus", nativeQuery = true)
	public List<ReportsModel> totalShareTransferShares();

	@Query(value = "select  typeOfStatus,sum(NO_OF_SHARES) as totalShares,trunc(100*(sum(NO_OF_SHARES)/sum(sum(NO_OF_SHARES)) over()),2) as percentageShares from "
			+ "( "
			+ "select no_of_shares,json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerResidentType') as typeOfStatus from share_transfer_master "
			+ "where UUID in (:uuid) and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER   group by UUID) "
			+ ") "
			+ "group by typeOfStatus", nativeQuery = true)
	public List<ReportsModel> postCal(@Param("uuid") List<Integer> uuid);

	@Query(value = "select  typeOfStatus, sum(NO_OF_SHARES) as totalShares,trunc(100*(sum(NO_OF_SHARES)/sum(sum(NO_OF_SHARES)) over()),2) as percentageShares  from\r\n"
			+ "(\r\n"
			+ "    select no_of_shares,json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerResidentType') as typeOfStatus from share_transfer_master \r\n"
			+ "    where status like '%TWO%' or status='TRANSFER_PENDING_FROM_DEPOSITORY' and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER   group by UUID)\r\n"
			+ ") \r\n"
			+ "group by typeOfStatus", nativeQuery = true)
	public List<ReportsModel> preCal();

	@Query(value = "select uuid, sellername, seller_client_id as sellerClientId, buyername, buyer_client_id as buyerClientId, no_of_shares as noOfShares, price_per_shares as pricePerShare,created_on as createdOn from share_transfer_master\r\n"
			+ "where status = 'TRANSFER_PENDING_FROM_DEPOSITORY'", nativeQuery = true)
	public List<CafListing> cafListing();

	// @Query(value="select uuid, sellername, seller_client_id as sellerClientId,
	// buyername, buyer_client_id as buyerClientId, no_of_shares as noOfShares,
	// price_per_shares as pricePerShare,created_on as createdOn from
	// share_transfer_master\r\n"
	// + "where status = 'TRANSFER_PENDING_FROM_DEPOSITORY' and UUID in
	// (:uuid)",nativeQuery=true)
	// public List<CafListing> cafGeneration(@Param("uuid") List<Integer> uuid);

	@Query(value = "select uuid, sellername, seller_client_id as sellerClientId, buyername, buyer_client_id as buyerClientId, no_of_shares as noOfShares, price_per_shares as pricePerShare,\r\n"
			+ "json_value(METADATA,'$.shareTransferRequestDetails.buyerDetails.buyerDpClientId') as buyerDpClientId,\r\n"
			+ "json_value(METADATA,'$.shareTransferRequestDetails.buyerDetails.nameOfDP') as buyerNameOfDP,\r\n"
			+ "json_value(METADATA,'$.shareTransferRequestDetails.sellerDetails.dpClientIdSeller') as sellerDpClientId,\r\n"
			+ "json_value(METADATA,'$.shareTransferRequestDetails.sellerDetails.nameOfDP') as sellerNameOfDP,\r\n"
			+ "created_on as createdOn from share_transfer_master \r\n"
			+ "where status = 'TRANSFER_PENDING_FROM_DEPOSITORY' and UUID in (:uuid)", nativeQuery = true)
	public List<CafListing> cafGeneration(@Param("uuid") List<Integer> uuid);
	
	
	
	@Query(value = "select * from (select * from SHARE_TRANSFER_MASTER WHERE UUID in (:uuid) order by ID DESC)T WHERE  ROWNUM=1", nativeQuery = true)
	public ShareTransferMaster getUuidsDetails(@Param("uuid") List<Integer> uuid);
	
	@Query(value = "select * from SHARE_TRANSFER_MASTER WHERE ID in (select MAX(ID) from SHARE_TRANSFER_MASTER where UUID in (:uuid) group by UUID)", nativeQuery = true)
	public List<ShareTransferMaster> getUuidsDetails1(@Param("uuid") List<Integer> uuid);
	//need to fetch pan from category table :add query like json_Query(METADATA,'$.shareTransferRequestDetails.buyerDetails.panOfBuyer') as BuyerPan
	@Query(value="select json_value(METADATA,'$.shareTransferRequestDetails.buyerDetails.panOfBuyer') as BuyerPan from SHARE_TRANSFER_MASTER where ID in (select MAX(ID) from SHARE_TRANSFER_MASTER where UUID=:uuid group by UUID)", nativeQuery = true)
	
	 public List<String> getAllBuyerPan(Long uuid);
	

	
	@Query(value="select json_query(METADATA,'$.shareTransferRequestDetails.buyerDetails.categoryDetails.categoryTable')  from SHARE_TRANSFER_MASTER where ID in (select MAX(ID) from SHARE_TRANSFER_MASTER where UUID=:uuid group by UUID)", nativeQuery = true)
	 public List<String> getAllCategoryPan(Long uuid);
	
	@Query(value="select client_id as clientId,shares as Shares,sum(shares) as totalShares, trunc(100*((shares)/(select * from total_shares)),2) as percentageShares from benpose_data_master where client_id=:clientId group by client_id,shares", nativeQuery = true)
	public BenposeHoldingInterface getBenposeHolding(String clientId);
	
//	@Query(value = "select * from("
//			+ "select json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares,status from share_transfer_master \r\n"
//			+ "where json_value(METADATA, '$.shareTransferRequestDetails.sellerDetails.dpClientIdSeller') = :clientId \r\n"
//			+ "and status not in ('REJECTED','SHARE_TRANSFER_COMPLETED')\r\n"
//			+ "\r\n"
//			+ "order by created_on desc \r\n"
//			+ "\r\n"
//			+ ")T where ROWNUM=1", nativeQuery = true)
	
	@Query(value = "select sum(noOfShares) as totalShares from(\r\n"
			+ "\r\n"
			+ "select id,uuid,json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares,status from share_transfer_master \r\n"
			+ "where json_value(METADATA, '$.shareTransferRequestDetails.sellerDetails.dpClientIdSeller') = :clientId  \r\n"
			+ "and status not in ('REJECTED','SHARE_TRANSFER_COMPLETED')\r\n"
			+ "and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)\r\n"
			+ ")", nativeQuery = true)
	public ShareTransferInterface getSharesCheck(String clientId);
	
	@Query(value = "select uuids,noOfShares,pricePerShare,modifiedOn,transactionParty,modeOfTransfer, ( case\r\n"
			+ "			 			    when buyerResidentType in ('domesticResident') and sellerResidentType in ('resident') then\r\n"
			+ "			 			         'R to R'\r\n"
			+ "			 			    when buyerResidentType in ('foreignResident') and sellerResidentType in ('resident') then\r\n"
			+ "			 			        'R to R*'    \r\n"
			+ "			 			    when buyerResidentType in ('foreignResident') and sellerResidentType in ('foreign') then\r\n"
			+ "			 			      'R* to R*'        ELSE        'R* to R'\r\n"
			+ "			 			 end) as categoryTransfer from (\r\n"
			+ "			 			    select json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares,\r\n"
			+ "			                 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.pricePerShare') as pricePerShare,\r\n"
			+ "			                 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.modeoftransfer') as modeOfTransfer,\r\n"
			+ "			                 modified_on as modifiedOn,\r\n"
			+ "			                 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.transactionParty') as transactionParty,\r\n"
			+ "			                 sr.uuid as uuids, json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerResidentType') as buyerResidentType,\r\n"
			+ "			 			        (        select  			 ( \r\n"
			+ "			 			 			 case when status in ('RI','DC') then  			 'resident' \r\n"
			+ "			 			 			 else  			 'foreign' \r\n"
			+ "			 			 			 END) as typeOfStatus from benpose_data_master where  \r\n"
			+ "			 			                client_id in (\r\n"
			+ "			 			                  select json_value(METADATA, '$.shareTransferRequestDetails.sellerDetails.dpClientIdSeller') from SHARE_TRANSFER_MASTER where UUID in (sr.uuid) \r\n"
			+ "			 			                     and\r\n"
			+ "			 			                  id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)\r\n"
			+ "			 			                )             ) as sellerResidentType\r\n"
			+ "			 			             from share_transfer_master sr where modified_on between TO_DATE(:dt1, 'dd-MM-yyyy') and TO_DATE(:dt2, 'dd-MM-yyyy') and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID) and STATUS  = 'SHARE_TRANSFER_COMPLETED'\r\n"
			+ "			 			    )", nativeQuery = true)
	public List<SalesPurchaseInterface> getSalePurchase(String dt1, String dt2);
	
	@Query(value = " select uuid,  seller_client_id as sellerClientId, buyername, buyer_client_id as buyerClientId,\r\n"
			+ " json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares,\r\n"
			+ "                json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.pricePerShare') as pricePerShare,\r\n"
			+ "                json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.amount') as amount,\r\n"
			+ "             \r\n"
			+ "			json_value(METADATA,'$.shareTransferRequestDetails.stageTwo.documentUpload.otherStage2Docs.utrId') as utrId,\r\n"
			+ "            json_value(METADATA,'$.shareTransferRequestDetails.stageTwo.documentUpload.otherStage2Docs.stampDuty') as stampDuty \r\n"
			+ "            from share_transfer_master \r\n"
			+ "			 where status = 'TRANSFER_PENDING_FROM_DEPOSITORY' and UUID in (:uuid)  ", nativeQuery = true)
	public List<BeneficiaryDetailsInterface> getBeneficiaryDetails(@Param("uuid") List<Integer> uuid);
	
	@Query(value="select * from Share_transfer_master where id in (select sharetransfer_id from share_transfer_workflow where uuid=:uuid) order by id desc fetch first  rows only", nativeQuery = true)
	public ShareTransferMaster getMetadataDetails(Long uuid);
	
	@Query(value="select * from(select * from Share_transfer_master where UUID=:uuid and id >\r\n"
			+ "(select max(id) from share_transfer_master where UUID=:uuid and status in ('STAGE_ONE_SUBMISSION_PENDING','STAGE_TWO_SUBMISSION_PENDING','STAGE_ONE_DOCUMENT_DEFICIENCY','STAGE_TWO_DOCUMENT_DEFICIENCY') group by uuid) and status in ('STAGE_ONE_VERIFICATION_PENDING','STAGE_TWO_VERIFICATION_PENDING') order by created_on desc)\r\n"
			+ "T where ROWNUM=1", nativeQuery = true)
	public ShareTransferMaster getpreviousMetadataDetails(Long uuid);
	
	@Query(value = "SELECT\r\n"
			+ "    created_on as createdOn,buyername, buyer_client_id as buyerClientId,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.sellerDetails.nameOfSeller')      AS sellername,\r\n"
			+ "    seller_client_id as sellerClientId,\r\n"
			+ "    no_of_shares AS shares,\r\n"
			+ "    price_per_shares as pricePerShare,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.modeoftransfer') as modeoftransfer\r\n"
			+ "    \r\n"
			+ "FROM\r\n"
			+ "    share_transfer_master\r\n"
			+ "WHERE modified_on < TO_DATE(:dt1, 'dd-MM-yyyy') \r\n"
			+ "AND\r\n"
			+ "    id IN (\r\n"
			+ "    SELECT\r\n"
			+ "        MAX(id)\r\n"
			+ "    FROM\r\n"
			+ "        share_transfer_master\r\n"
			+ "    GROUP BY\r\n"
			+ "        uuid\r\n"
			+ ")\r\n"
			+ "    AND status = 'SHARE_TRANSFER_COMPLETED'", nativeQuery = true)
	public List<TransferDetailsInterface> getTransferDetials(String dt1);
	
//	@Query(value = "select * from (select * from share_transfer_master where status = 'TRANSFER_PENDING_FROM_DEPOSITORY' and uuid =:uuid order by ID DESC)T WHERE  ROWNUM=1", nativeQuery = true)
	@Query(value = "select * from share_transfer_master where status = 'TRANSFER_PENDING_FROM_DEPOSITORY' and uuid =:uuid and id in (select max(id) from share_transfer_master group by uuid)", nativeQuery = true)
	public ShareTransferMaster shareTransferExecutionDate(Long uuid);
	
	@Query(value = "select uuid,buyername,sellername,json_value(metadata,'$.shareTransferRequestDetails.shareTransferDetails.pricePerShare') as pricePerShare,\r\n"
			+ "json_value(metadata,'$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares,\r\n"
			+ "json_value(metadata,'$.shareTransferRequestDetails.buyerDetails.buyerPAC') as pac,\r\n"
			+ "trunc(100*((json_value(metadata,'$.shareTransferRequestDetails.shareTransferDetails.pricePerShare'))/(select * from total_shares)),2) as percentageShare,\r\n"
			+ "(Select * from(select modified_on from share_transfer_master where status = 'STAGE_ONE_VERIFICATION_PENDING' and uuid = sr.uuid)T Where ROWNUM=1  ) as stageOneDocs,\r\n"
			+ "(select comments from share_transfer_workflow where uuid = sr.uuid and id in (select max(id) from share_transfer_workflow group by uuid) ) as comments,\r\n"
			+ "(Select * from(select modified_on from share_transfer_master where status = 'IN_PRINCIPAL_PENDING')T Where ROWNUM=1) as principalPending,\r\n"
			+ "(Select * from(select modified_on from share_transfer_master where status = 'STAGE_TWO_VERIFICATION_PENDING' and uuid = sr.uuid )T Where ROWNUM=1 ) as stageTwoDocs,\r\n"
			+ "date_of_execution as dateOfExecution\r\n"
			+ "from share_transfer_master sr where status = 'SHARE_TRANSFER_COMPLETED' and id in (select max(id) from share_transfer_master group by uuid)", nativeQuery = true)
	public List<TransferMasterExportInterface> transferMasterExport();

	@Query(value = "select buyername,\r\n"
			+ "json_value(metadata,'$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares, \r\n"
			+ "json_value(metadata,'$.shareTransferRequestDetails.shareTransferDetails.categoryOfBuyer') as categoryOfBuyer, \r\n"
			+ "json_value(metadata,'$.shareTransferRequestDetails.shareTransferDetails.subCategory') as subCategory, \r\n"
			+ "json_value(metadata,'$.shareTransferRequestDetails.shareTransferDetails.corpListed') as corpListed, \r\n"
			+ "json_value(metadata,'$.shareTransferRequestDetails.buyerDetails.buyerTypeTCCM') as buyerTypeTCCM, \r\n"
			+ "json_value(metadata,'$.shareTransferRequestDetails.buyerDetails.buyerNRIType') as buyerNRIType,\r\n"
			+ "json_value(metadata,'$.shareTransferRequestDetails.buyerDetails.buyerDpClientId') as clientId,\r\n"
			+ "date_of_execution as dateOfExecution\r\n"
			+ "from share_transfer_master sr\r\n"
			+ "where status = 'SHARE_TRANSFER_COMPLETED' and id in (select max(id) from share_transfer_master group by uuid)", nativeQuery = true)
	public List<ShareHoldingPatternTemplateInterface> getShareHoldingPatternTemplate();
	
	@Query(value = "SELECT\r\n"
			+ "    ( (\r\n"
			+ "        SELECT\r\n"
			+ "            totalshares\r\n"
			+ "        FROM\r\n"
			+ "            total_shares\r\n"
			+ "    ) * (\r\n"
			+ "        SELECT\r\n"
			+ "            total_holding_limit\r\n"
			+ "        FROM\r\n"
			+ "            shareholding_limit_master\r\n"
			+ "        WHERE\r\n"
			+ "                category = 'Level 1 Limit'\r\n"
			+ "            AND active = 'Y'\r\n"
			+ "            AND accept = 'Y'\r\n"
			+ "        ORDER BY\r\n"
			+ "            id DESC\r\n"
			+ "        FETCH NEXT 1 ROWS ONLY\r\n"
			+ "    ) ) / 100 AS levelOneShares\r\n"
			+ "FROM\r\n"
			+ "    dual", nativeQuery = true)
	public Long getLevelOneShares();
	
	@Query(value = "SELECT\r\n"
			+ "    ( (\r\n"
			+ "        SELECT\r\n"
			+ "            totalshares\r\n"
			+ "        FROM\r\n"
			+ "            total_shares\r\n"
			+ "    ) * (\r\n"
			+ "        SELECT\r\n"
			+ "            total_holding_limit\r\n"
			+ "        FROM\r\n"
			+ "            shareholding_limit_master\r\n"
			+ "        WHERE\r\n"
			+ "                category = 'Level 2 Limit'\r\n"
			+ "            AND active = 'Y'\r\n"
			+ "            AND accept = 'Y'\r\n"
			+ "        ORDER BY\r\n"
			+ "            id DESC\r\n"
			+ "        FETCH NEXT 1 ROWS ONLY\r\n"
			+ "    ) ) / 100 AS levelTwoShares\r\n"
			+ "FROM\r\n"
			+ "    dual", nativeQuery = true)
	public Long getLevelTwoShares();

	@Query(value = "select * from share_transfer_master where uuid =:uuid", nativeQuery = true)
	public List<ShareTransferMaster> getByUuid(Long uuid);
	
	@Query(value = "   SELECT\r\n"
			+ "            *\r\n"
			+ "        FROM\r\n"
			+ "            (\r\n"
			+ "                SELECT\r\n"
			+ "                    sr.*, LAG(sr.status)\r\n"
			+ "                          OVER(\r\n"
			+ "                        ORDER BY\r\n"
			+ "                            modified_on ASC\r\n"
			+ "                          ) AS previousstatus\r\n"
			+ "                FROM\r\n"
			+ "                    share_transfer_master sr\r\n"
			+ "                WHERE\r\n"
			+ "                    uuid =:uuid\r\n"
			+ "            ) t1\r\n"
			+ "        WHERE\r\n"
			+ "--            previousstatus IS NOT NULL AND\r\n"
			+ "             status != previousstatus order by id",nativeQuery = true)
	public List<ShareTransferMaster> getBySubmitUuid(Long uuid);
	
	@Query(value = "SELECT\r\n"
			+ "    sellername,\r\n"
			+ "    noofshares,\r\n"
			+ "    buyername,\r\n"
			+ "    sellerresidenttype,\r\n"
			+ "    sellerpan,\r\n"
			+ "    buyerresidenttype,\r\n"
			+ "    buyerpan,\r\n"
			+ "    modeoftransfer,\r\n"
			+ "    dateOfExecution\r\n"
			+ "    \r\n"
			+ "FROM\r\n"
			+ "    (\r\n"
			+ "        SELECT\r\n"
			+ "        sr.uuid as uuids,\r\n"
			+ "        sr.id as ids,\r\n"
			+ "            sr.sellername,\r\n"
			+ "            JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares')       AS noofshares,\r\n"
			+ "            sr.buyername,\r\n"
			+ "            JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.modeoftransfer')   AS modeoftransfer,\r\n"
			+ "            sr.date_of_execution                                                                        AS dateOfExecution,\r\n"
			+ "            (select pan from benpose_data_master where client_id=sr.seller_client_id)                   AS sellerpan,\r\n"
			+ "            JSON_VALUE(metadata, '$.shareTransferRequestDetails.buyerDetails.panOfBuyer')               AS buyerpan,\r\n"
			+ "            JSON_VALUE(metadata, '$.shareTransferRequestDetails.buyerDetails.buyerResidentType')        AS buyerresidenttype,\r\n"
			+ "            (\r\n"
			+ "                SELECT\r\n"
			+ "                    (\r\n"
			+ "                        CASE\r\n"
			+ "                            WHEN status IN ( 'RI', 'DC' ) THEN\r\n"
			+ "                                'Resident'\r\n"
			+ "                            ELSE\r\n"
			+ "                                'Foreign'\r\n"
			+ "                        END\r\n"
			+ "                    ) AS typeofstatus\r\n"
			+ "                FROM\r\n"
			+ "                    benpose_data_master\r\n"
			+ "                WHERE\r\n"
			+ "                    client_id IN (\r\n"
			+ "                        SELECT\r\n"
			+ "                            JSON_VALUE(metadata, '$.shareTransferRequestDetails.sellerDetails.dpClientIdSeller')\r\n"
			+ "                        FROM\r\n"
			+ "                            share_transfer_master\r\n"
			+ "                        WHERE\r\n"
			+ "                            uuid IN ( sr.uuid )\r\n"
			+ "                            AND id IN (\r\n"
			+ "                                SELECT\r\n"
			+ "                                    MAX(id)\r\n"
			+ "                                FROM\r\n"
			+ "                                    share_transfer_master\r\n"
			+ "                                GROUP BY\r\n"
			+ "                                    uuid\r\n"
			+ "                            )\r\n"
			+ "                    )\r\n"
			+ "            )                                                                                           AS sellerresidenttype\r\n"
			+ "        FROM\r\n"
			+ "            share_transfer_master sr\r\n"
			+ "        WHERE\r\n"
			+ "--            modified_on BETWEEN TO_DATE(:dt1, 'dd-MM-yyyy') AND TO_DATE(:dt2, 'dd-MM-yyyy') AND \r\n"
			+ "            id IN (\r\n"
			+ "                SELECT\r\n"
			+ "                    MAX(id)\r\n"
			+ "                FROM\r\n"
			+ "                    share_transfer_master\r\n"
			+ "                GROUP BY\r\n"
			+ "                    uuid\r\n"
			+ "            )\r\n"
			+ "            AND status = 'SHARE_TRANSFER_COMPLETED'\r\n"
			+ "    )", nativeQuery = true)
	public List<TaxTeamInterface> getTaxTeamReport1();
	
	@Query(value = "SELECT\r\n"
			+ "    sr.uuid,\r\n"
			+ "    sellername,\r\n"
			+ "    buyername,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares')        AS noofshares,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.pricePerShare')     AS pricepershare,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.amount')            AS amount,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.categoryOfBuyer')   AS categoryofbuyer,\r\n"
			+ "    (\r\n"
			+ "        SELECT\r\n"
			+ "            *\r\n"
			+ "        FROM\r\n"
			+ "            (\r\n"
			+ "                SELECT\r\n"
			+ "                    modified_on\r\n"
			+ "                FROM\r\n"
			+ "                    share_transfer_master\r\n"
			+ "                WHERE\r\n"
			+ "                        status = 'STAGE_TWO_SUBMISSION_PENDING'\r\n"
			+ "                    AND uuid = sr.uuid\r\n"
			+ "                ORDER BY\r\n"
			+ "                    modified_on DESC\r\n"
			+ "            ) t\r\n"
			+ "        WHERE\r\n"
			+ "            ROWNUM = 1\r\n"
			+ "    )                                                                                            AS dateofapproval,\r\n"
			+ "    date_of_execution                                                                            AS dateofexecution,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.buyerDetails.buyerPAC')                  AS buyerpac,\r\n"
			+ "    JSON_QUERY(metadata, '$.shareTransferRequestDetails.buyerDetails.pacDetails') AS pacdetailslist\r\n"
			+ "FROM\r\n"
			+ "    share_transfer_master sr\r\n"
			+ "WHERE\r\n"
			+ "    id IN (\r\n"
			+ "        SELECT\r\n"
			+ "            MAX(id)\r\n"
			+ "        FROM\r\n"
			+ "            share_transfer_master\r\n"
			+ "        GROUP BY\r\n"
			+ "            uuid\r\n"
			+ "    )\r\n"
			+ "    AND status = 'SHARE_TRANSFER_COMPLETED' and JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.categoryOfBuyer') = 'individual' and date_of_execution between TO_DATE(:dt1, 'dd-MM-yyyy') and TO_DATE(:dt2, 'dd-MM-yyyy')", nativeQuery = true)
	public List<AverageWeightedReportInterface> getWeightedAverage(String dt1, String dt2);
	
	
	@Query(value = "SELECT\r\n"
			+ "    sr.uuid,\r\n"
			+ "    sellername,\r\n"
			+ "    buyername,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares')        AS noofshares,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.pricePerShare')     AS pricepershare,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.amount')            AS amount,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.categoryOfBuyer')   AS categoryofbuyer,\r\n"
			+ "    (\r\n"
			+ "        SELECT\r\n"
			+ "            *\r\n"
			+ "        FROM\r\n"
			+ "            (\r\n"
			+ "                SELECT\r\n"
			+ "                    modified_on\r\n"
			+ "                FROM\r\n"
			+ "                    share_transfer_master\r\n"
			+ "                WHERE\r\n"
			+ "                        status = 'STAGE_TWO_SUBMISSION_PENDING'\r\n"
			+ "                    AND uuid = sr.uuid\r\n"
			+ "                ORDER BY\r\n"
			+ "                    modified_on DESC\r\n"
			+ "            ) t\r\n"
			+ "        WHERE\r\n"
			+ "            ROWNUM = 1\r\n"
			+ "    )                                                                                            AS dateofapproval,\r\n"
			+ "    date_of_execution                                                                            AS dateofexecution,\r\n"
			+ "    JSON_VALUE(metadata, '$.shareTransferRequestDetails.buyerDetails.buyerPAC')                  AS buyerpac,\r\n"
			+ "    JSON_QUERY(metadata, '$.shareTransferRequestDetails.buyerDetails.pacDetails') AS pacdetailslist\r\n"
			+ "FROM\r\n"
			+ "    share_transfer_master sr\r\n"
			+ "WHERE\r\n"
			+ "    id IN (\r\n"
			+ "        SELECT\r\n"
			+ "            MAX(id)\r\n"
			+ "        FROM\r\n"
			+ "            share_transfer_master\r\n"
			+ "        GROUP BY\r\n"
			+ "            uuid\r\n"
			+ "    )\r\n"
			+ "    AND status = 'SHARE_TRANSFER_COMPLETED' and JSON_VALUE(metadata, '$.shareTransferRequestDetails.shareTransferDetails.categoryOfBuyer') != 'individual' and date_of_execution between TO_DATE(:dt1, 'dd-MM-yyyy') and TO_DATE(:dt2, 'dd-MM-yyyy')", nativeQuery = true)
	public List<AverageWeightedReportInterface> getWeightedAverageNonIndividuals(String dt1, String dt2);
}
