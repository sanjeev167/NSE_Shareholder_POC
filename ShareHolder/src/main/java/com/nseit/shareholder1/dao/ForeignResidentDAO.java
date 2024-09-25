package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nseit.shareholder1.model.BatchModel;
import com.nseit.shareholder1.modelInterfaces.ForeignResidentInterface;
import com.nseit.shareholder1.modelInterfaces.MovementInterface;
import com.nseit.shareholder1.modelInterfaces.PostTmToPCalInterface;
import com.nseit.shareholder1.modelInterfaces.ReportsModel;

public interface ForeignResidentDAO extends JpaRepository<BatchModel, Long> {

	@Query(value = " select sr.uuid, json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares, json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerResidentType') as buyerResidentType,\r\n"
			+ "        (\r\n" + "        select \r\n" + "			 ( \r\n"
			+ "			 case when status in ('RI','DC') then \r\n" + "			 'resident' \r\n"
			+ "			 else \r\n" + "			 'foreign' \r\n"
			+ "			 END) as typeOfStatus from benpose_data_master where \r\n"
			+ "                client_id in (\r\n"
			+ "                  select json_value(METADATA, '$.shareTransferRequestDetails.sellerDetails.dpClientIdSeller') from SHARE_TRANSFER_MASTER where UUID in (sr.uuid) \r\n"
			+ "                     and\r\n"
			+ "                  id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)\r\n"
			+ "                )\r\n" + "             ) as sellerResidentType\r\n"
			+ "             from share_transfer_master sr where UUID in (:uuid) and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)", nativeQuery = true)
	public List<ForeignResidentInterface> getForeignResident(@Param("uuid") List<Integer> uuid);

	@Query(value = "SELECT totalshares FROM TOTAL_SHARES", nativeQuery = true)
	public Integer getTotalShares();

	@Query(value = "select uuids, (\r\n" + "case\r\n"
			+ "    when buyerResidentType in ('domesticResident') and sellerResidentType in ('resident') then\r\n"
			+ "         'R to R'\r\n"
			+ "    when buyerResidentType in ('foreignResident') and sellerResidentType in ('resident') then\r\n"
			+ "        'R to R*'\r\n" + "    \r\n"
			+ "    when buyerResidentType in ('foreignResident') and sellerResidentType in ('foreign') then\r\n"
			+ "      'R* to R*'   \r\n" + "\r\n" + "    ELSE\r\n" + "        'R* to R'\r\n"
			+ "end) as categoryTransfer\r\n" + "from (\r\n"
			+ "    select sr.uuid as uuids, json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerResidentType') as buyerResidentType,\r\n"
			+ "        (\r\n" + "        select \r\n" + "			 ( \r\n"
			+ "			 case when status in ('RI','DC') then \r\n" + "			 'resident' \r\n"
			+ "			 else \r\n" + "			 'foreign' \r\n"
			+ "			 END) as typeOfStatus from benpose_data_master where \r\n" + "\r\n"
			+ "                client_id in (\r\n"
			+ "                  select json_value(METADATA, '$.shareTransferRequestDetails.sellerDetails.dpClientIdSeller') from SHARE_TRANSFER_MASTER where UUID in (sr.uuid) \r\n"
			+ "                     and\r\n"
			+ "                  id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)\r\n"
			+ "                )\r\n" + "             ) as sellerResidentType\r\n"
			+ "             from share_transfer_master sr where UUID in (:uuid) and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)\r\n"
			+ "    )", nativeQuery = true)

	public MovementInterface getMovementForeignResident(@Param("uuid") Long uuid);
	
	@Query(value = "select uuids, (\r\n"
			+ "case\r\n"
			+ "    when buyerTypeTCCM in ('TM/CM','associateTM/CM') and sellerResidentType in ('TM') then\r\n"
			+ "         'TM to TM'\r\n"
			+ "    when buyerTypeTCCM in ('public') and sellerResidentType in ('TM') then\r\n"
			+ "        'TM to P'\r\n"
			+ "    \r\n"
			+ "    when buyerTypeTCCM in ('public') and sellerResidentType in ('Public') then\r\n"
			+ "        'P to TM'   \r\n"
			+ "\r\n"
			+ "    ELSE\r\n"
			+ "        'P to P'\r\n"
			+ "end) as categoryTransfer\r\n"
			+ "from (\r\n"
			+ "    select sr.uuid as uuids,  json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerTypeTCCM') as buyerTypeTCCM,\r\n"
			+ "        (\r\n"
			+ "        select  (case when sub_category_one in ('TRADING MEMBERS','ASSOCIATES') then  \r\n"
			+ "			       'TM'  \r\n"
			+ "			       when sub_category_one in ('PUBLIC') then  \r\n"
			+ "			       'PUBLIC'  \r\n"
			+ "			       ELSE  \r\n"
			+ "			       ' '  \r\n"
			+ "			       END) as typeOfStatus from share_holding_pattern   where   \r\n"
			+ "			     --             client_id in ('99999999-99990007')    \r\n"
			+ "			                     category_of_shareholder in (    \r\n"
			+ "			                       select json_value(METADATA, '$.shareTransferRequestDetails.sellerDetails.nameOfSeller') from SHARE_TRANSFER_MASTER where UUID in (sr.uuid)   \r\n"
			+ "			                          and    \r\n"
			+ "			                       id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)    \r\n"
			+ "			                     )    \r\n"
			+ "			                  ) as sellerResidentType  \r\n"
			+ "             from share_transfer_master sr where UUID in (:uuid) and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)\r\n"
			+ "    )", nativeQuery = true)
	public MovementInterface getMovementTmToP(@Param("uuid") Long uuid);

//	@Query(value = "select typeOfStatus,sum(NO_OF_SHARES) as totalShares, round(100*(sum(NO_OF_SHARES)/(select totalshares from total_shares)) ,2) as percentageShares from \r\n"
//			+ "(select NO_OF_SHARES,\r\n"
//			+ "    (case when remarks like '%to P%' then\r\n"
//			+ "    'Public'\r\n"
//			+ "    when remarks like '%to TM%' then\r\n"
//			+ "    'TM'\r\n"
//			+ "    ELSE\r\n"
//			+ "    ''\r\n"
//			+ "    END) as typeofstatus from transfer_master\r\n"
//			+ ") group by typeofstatus", nativeQuery =  true)
//	public List<ReportsModel> getPreTmToPCal();

	@Query(value = "select typeOfStatus,sum(no_of_shares) as totalShares,trunc(100*(sum(no_of_shares)/(select totalshares from total_shares)),2) as percentageShares  from\r\n"
			+ "(\r\n"
			+ "    select no_of_shares, (case when sub_category_one in ('TRADING MEMBERS','ASSOCIATES') then\r\n"
			+ "    'TM'\r\n" + "    when sub_category_one in ('PUBLIC') then\r\n" + "    'PUBLIC'\r\n" + "    ELSE\r\n"
			+ "    ' '\r\n" + "    END) as typeOfStatus from share_holding_pattern\r\n"
			+ ") group by typeOfStatus", nativeQuery = true)
	public List<ReportsModel> getPreTmToPCal();

//	@Query(value = "select sr.uuid as uuids, json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares, json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerTypeTCCM') as buyerTypeTCCM,\r\n"
//			+ "        (\r\n"
//			+ "        select \r\n"
//			+ "			 ( \r\n"
//			+ "			 case when remarks like '%to P%' then \r\n"
//			+ "			 'Public'\r\n"
//			+ "            when remarks like '%to TM%' then\r\n"
//			+ "            'TM'\r\n"
//			+ "            ELSE\r\n"
//			+ "            '' \r\n"
//			+ "			 END) as typeOfStatus from transfer_master where \r\n"
//			+ "--             client_id in ('99999999-99990007')\r\n"
//			+ "                NAME_OF_TRANSFEREE in (\r\n"
//			+ "                  select json_value(METADATA, '$.shareTransferRequestDetails.sellerDetails.nameOfSeller') from SHARE_TRANSFER_MASTER where UUID in (sr.uuid) \r\n"
//			+ "                     and\r\n"
//			+ "                  id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)\r\n"
//			+ "                )\r\n"
//			+ "             ) as sellerResidentType\r\n"
//			+ "             from share_transfer_master sr where UUID in (:uuid) and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)", nativeQuery = true)

//	@Query(value = "select sr.uuid as uuids, json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares, json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerTypeTCCM') as buyerTypeTCCM,    \r\n"
//			+ "			             (    \r\n"
//			+ "			             select  (case when sub_category_one in ('TRADING MEMBERS','ASSOCIATES') then  \r\n"
//			+ "			       'TM'  \r\n" + "			       when sub_category_one in ('PUBLIC') then  \r\n"
//			+ "			       'PUBLIC'  \r\n" + "			       ELSE  \r\n" + "			       ' '  \r\n"
//			+ "			       END) as typeOfStatus from share_holding_pattern   where   \r\n"
//			+ "			                     category_of_shareholder in (    \r\n"
//			+ "			                       select json_value(METADATA, '$.shareTransferRequestDetails.sellerDetails.nameOfSeller') from SHARE_TRANSFER_MASTER where UUID in (sr.uuid)   \r\n"
//			+ "			                          and    \r\n"
//			+ "			                       id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)    \r\n"
//			+ "			                     )    \r\n" + "			                  ) as sellerResidentType    \r\n"
//			+ "			                  from share_transfer_master sr where UUID in (:uuid) and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)", nativeQuery = true)
	
	@Query(value = "select sr.uuid as uuids, json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as noOfShares, json_value(METADATA, '$.shareTransferRequestDetails.buyerDetails.buyerTypeTCCM') as buyerTypeTCCM,    \r\n"
			+ "			 			             (    \r\n"
			+ "			 			             select  (case when sub_category_one in ('TRADING MEMBERS','ASSOCIATES') then  \r\n"
			+ "			 			       'TM'    			       when sub_category_one in ('PUBLIC') then  \r\n"
			+ "			 			       'PUBLIC'    			       ELSE    			       ' '  \r\n"
			+ "			 			       END) as typeOfStatus from share_holding_pattern   where   \r\n"
			+ "			 			                     category_of_shareholder in (    \r\n"
			+ "			 			                        select shareholder_name from BENPOSE_DATA_MASTER where client_id in (select json_value(METADATA, '$.shareTransferRequestDetails.sellerDetails.dpClientIdSeller') from SHARE_TRANSFER_MASTER where UUID in (sr.uuid)   \r\n"
			+ "			 			                          and    \r\n"
			+ "			 			                       id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID) )   \r\n"
			+ "			 			                     )      			                  ) as sellerResidentType    \r\n"
			+ "			 			                  from share_transfer_master sr where UUID in (:uuid) and id in(SELECT MAX(id) from SHARE_TRANSFER_MASTER group by UUID)", nativeQuery = true)
	public List<PostTmToPCalInterface> getPostTmToPCal(@Param("uuid") List<Integer> uuid);
}
