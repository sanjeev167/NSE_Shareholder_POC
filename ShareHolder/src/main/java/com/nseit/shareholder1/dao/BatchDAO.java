package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.BatchModel;
import com.nseit.shareholder1.modelInterfaces.BatchListingAboveTwo;
import com.nseit.shareholder1.modelInterfaces.BatchListingInterface;

public interface BatchDAO extends JpaRepository<BatchModel, Long> {

	// @Query(value = "select uuid, sellerName, buyername, NO_OF_SHARES as
	// noOfShares,PRICE_PER_SHARES as pricePerShares,(no_of_shares*PRICE_PER_SHARES)
	// as saleValue,PERSON_CONCERT \r\n"
	// + "as personConcert,\r\n"
	// + "trunc(100*(NO_OF_SHARES/(select totalshares from TOTAL_SHARES)),2) as
	// percentage\r\n"
	// + "from SHARE_TRANSFER_MASTER where UUID in (:uuid) and id in(SELECT MAX(id)
	// from SHARE_TRANSFER_MASTER group by UUID)", nativeQuery = true)

	@Query(value = "select uuid, sellername, buyername,previousShares, noOfShares,pricePerShares,saleValue,personConcert,trunc(100*(noOfShares/(select totalshares from TOTAL_SHARES)),2) as percentage,\r\n"
			+ "noOfShares + previousShares as proposedShares,trunc(100*((noOfShares + previousShares)/(select totalshares from TOTAL_SHARES)),2) as percentageHolding\r\n"
			+ "			 from(select id,sr.uuid ,status, sellerName, buyername, \r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares,\r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.pricePerShare') as pricePerShares,\r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.amount') as saleValue, \r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerPAC') as personConcert,\r\n"
			+ "       \r\n"
			+ "            (select shares from benpose_data_master\r\n"
			+ "			 where client_id in (select json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerDpClientId') from share_transfer_master \r\n"
			+ "			 where uuid = sr.uuid and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID))) as previousShares\r\n"
			+ "			\r\n"
			+ "			 from SHARE_TRANSFER_MASTER sr )\r\n"
			+ "             where uuid in (:uuid) and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID) ", nativeQuery = true)
	public List<BatchListingInterface> batchListingBelowTwo(@Param("uuid") List<Integer> uuid);

	@Query(value = "select uuid, sellername,netWorth, buyername,previousShares, noOfShares,pricePerShares,saleValue,personConcert,trunc(100*(noOfShares/(select totalshares from TOTAL_SHARES)),2) as percentage,\r\n"
			+ "noOfShares + previousShares as proposedShares,trunc(100*((noOfShares + previousShares)/(select totalshares from TOTAL_SHARES)),2) as percentageHolding\r\n"
			+ "			 from(select id,sr.uuid ,status, sellerName, buyername, \r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares,\r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.pricePerShare') as pricePerShares,\r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.amount') as saleValue, \r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerPAC') as personConcert,\r\n"
			+ "            json_query(METADATA, '$.buyerDocument.aboveTwoPercent.netWorthCibilScore') as netWorth,\r\n"
			+ "            (select shares from benpose_data_master\r\n"
			+ "			 where client_id in (select json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerDpClientId') from share_transfer_master \r\n"
			+ "			 where uuid = sr.uuid and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID))) as previousShares\r\n"
			+ "			\r\n"
			+ "			 from SHARE_TRANSFER_MASTER sr )\r\n"
			+ "             where uuid in (:uuid) and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)", nativeQuery = true)
	public List<BatchListingAboveTwo> batchListingAboveTwo(@Param("uuid") List<Integer> uuid);

	@Query(value = "select * from BATCH_MASTER where id = :ID", nativeQuery = true)
	public BatchModel getBatch(Long ID);

	@Query(value = "select * from BATCH_MASTER where CATEGORY in (:category) and approved is null", nativeQuery = true)
	public List<BatchModel> getBatchs(@Param("category") String category);
	
	@Query(value = "select * from BATCH_MASTER where CATEGORY in (:category) and approved = 'N'", nativeQuery = true)
	public List<BatchModel> getRejectedBatchs(@Param("category") String category);

	@Query(value = "select uuid, sellername, buyername,previousShares, noOfShares,pricePerShares,saleValue,personConcert,trunc(100*(noOfShares/(select totalshares from TOTAL_SHARES)),2) as percentage,\r\n"
			+ "noOfShares + previousShares as proposedShares,trunc(100*((noOfShares + previousShares)/(select totalshares from TOTAL_SHARES)),2) as percentageHolding\r\n"
			+ "			 from(select id,sr.uuid ,status, sellerName, buyername, \r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares,\r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.pricePerShare') as pricePerShares,\r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.amount') as saleValue, \r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerPAC') as personConcert,\r\n"
			+ "       \r\n"
			+ "            (select shares from benpose_data_master\r\n"
			+ "			 where client_id in (select json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerDpClientId') from share_transfer_master \r\n"
			+ "			 where uuid = sr.uuid and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID))) as previousShares\r\n"
			+ "			\r\n"
			+ "			 from SHARE_TRANSFER_MASTER sr )\r\n"
			+ "             where status like 'STAGE_ONE_VERIFIED' and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)  \r\n"
			+ "             and trunc(100*(noOfShares/(select totalshares from TOTAL_SHARES)),2)\r\n"
			+ "             <\r\n"
			+ "             (SELECT TOTAL_HOLDING_LIMIT FROM SHAREHOLDING_LIMIT_MASTER  \r\n"
			+ "			       WHERE CATEGORY='Level 1 Limit'  \r\n"
			+ "		       AND ACTIVE='Y'  \r\n"
			+ "			       AND ACCEPT='Y' \r\n"
			+ "			     ORDER BY ID DESC FETCH NEXT 1 ROWS ONLY)", nativeQuery = true)
	public List<BatchListingInterface> getListingForBatch();

	@Query(value = "select uuid, sellername,netWorth, buyername,previousShares, noOfShares,pricePerShares,saleValue,personConcert,trunc(100*(noOfShares/(select totalshares from TOTAL_SHARES)),2) as percentage,\r\n"
			+ "noOfShares + previousShares as proposedShares,trunc(100*((noOfShares + previousShares)/(select totalshares from TOTAL_SHARES)),2) as percentageHolding\r\n"
			+ "			 from(select id,sr.uuid ,status, sellerName, buyername, \r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares,\r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.pricePerShare') as pricePerShares,\r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.amount') as saleValue, \r\n"
			+ "			 json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerPAC') as personConcert,\r\n"
			+ "            json_query(METADATA, '$.buyerDocument.aboveTwoPercent.netWorthCibilScore') as netWorth,\r\n"
			+ "            (select shares from benpose_data_master\r\n"
			+ "			 where client_id in (select json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerDpClientId') from share_transfer_master \r\n"
			+ "			 where uuid = sr.uuid and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID))) as previousShares\r\n"
			+ "			\r\n"
			+ "			 from SHARE_TRANSFER_MASTER sr )\r\n"
			+ "             where status like 'STAGE_ONE_VERIFIED' and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)  \r\n"
			+ "             and trunc(100*(noOfShares/(select totalshares from TOTAL_SHARES)),2)\r\n"
			+ "             >=\r\n"
			+ "             (SELECT TOTAL_HOLDING_LIMIT FROM SHAREHOLDING_LIMIT_MASTER  \r\n"
			+ "			       WHERE CATEGORY='Level 1 Limit'  \r\n"
			+ "		       AND ACTIVE='Y'  \r\n"
			+ "			       AND ACCEPT='Y' \r\n"
			+ "			     ORDER BY ID DESC FETCH NEXT 1 ROWS ONLY)", nativeQuery = true)
	public List<BatchListingAboveTwo> getPreListingAboveTwo();

	@Query(value = "select json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerGovtCompany') as buyerGovtCompany from share_transfer_master\r\n"
			+ "where uuid in (:uuid) and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)", nativeQuery = true)
	public String getBuyerGovtCompany(@Param("uuid") Long uuid);
}
