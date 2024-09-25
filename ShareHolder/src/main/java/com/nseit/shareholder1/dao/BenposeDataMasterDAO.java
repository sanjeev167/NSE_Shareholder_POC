package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.BenposeDataMasterModel;
import com.nseit.shareholder1.modelInterfaces.Mimps;
import com.nseit.shareholder1.modelInterfaces.Mimps2;
import com.nseit.shareholder1.modelInterfaces.ReportsModel;

public interface BenposeDataMasterDAO extends JpaRepository<BenposeDataMasterModel, String> {
	@Query(value = "select * from BENPOSE_DATA_MASTER", nativeQuery = true)
	public List<BenposeDataMasterModel> checkValues();

	@Query(value = "select typeOfStatus,sum(shares) as totalShares, trunc(100*(sum(shares)/(SELECT totalshares FROM TOTAL_SHARES)),2) as percentageShares "
			+ "from ( "
			+ "select shares,  "
			+ "( "
			+ "case when status in ('RI','DC') then "
			+ "'resident' "
			+ "else "
			+ "'foreign' "
			+ "END) as typeOfStatus from benpose_data_master "
			+ ") group by typeOfStatus", nativeQuery = true)
	public List<ReportsModel> prePostCalBenpose();

	@Query(value = "SELECT typeOfStatus,SUM(NO_SHARES) AS totalShares ,trunc(100* (SUM(NO_SHARES)/(SELECT totalshares FROM TOTAL_SHARES)),2) AS percentageShares "
			+ "FROM ( "
			+ "SELECT typeOfStatus, "
			+ "( "
			+ "CASE WHEN  "
			+ "SHARES >= "
			+ "    trunc ((SELECT TOTAL_HOLDING_LIMIT FROM SHAREHOLDING_LIMIT_MASTER  "
			+ "      WHERE CATEGORY='Level 1 Limit'  "
			+ "      AND ACTIVE='Y'  "
			+ "      AND ACCEPT='Y' "
			+ "      ORDER BY ID DESC FETCH NEXT 1 ROWS ONLY\r\n"
			+ "      ) * (SELECT SUM(M3.SHARES) FROM BENPOSE_DATA_MASTER M3) / 100 ,2)"
			+ "THEN SHARES "
			+ "ELSE 0 "
			+ "END) AS NO_SHARES  "
			+ "FROM ( "
			+ "    select shares,typeOfStatus "
			+ "    from ("
			+ "        select shares, "
			+ "        ("
			+ "            case when status in ('RI','DC') then\r\n"
			+ "                'resident'\r\n"
			+ "            else\r\n"
			+ "                'foreign'\r\n"
			+ "            END\r\n"
			+ "        ) as typeOfStatus from benpose_data_master\r\n"
			+ ") M1 \r\n"
			+ ")"
			+ ")"
			+ "GROUP BY typeOfStatus", nativeQuery = true)
	public List<ReportsModel> getAboveLimitLevel1();

	@Query(value = "SELECT typeOfStatus,SUM(NO_SHARES) AS totalShares ,trunc(100* (SUM(NO_SHARES)/(SELECT totalshares FROM TOTAL_SHARES)),2) AS percentageShares "
			+ "FROM ( "
			+ "SELECT typeOfStatus, "
			+ "( "
			+ "CASE WHEN  "
			+ "SHARES < "
			+ "    trunc ((SELECT TOTAL_HOLDING_LIMIT FROM SHAREHOLDING_LIMIT_MASTER  "
			+ "      WHERE CATEGORY='Level 1 Limit'  "
			+ "      AND ACTIVE='Y'  "
			+ "      AND ACCEPT='Y' "
			+ "      ORDER BY ID DESC FETCH NEXT 1 ROWS ONLY\r\n"
			+ "      ) * (SELECT SUM(M3.SHARES) FROM BENPOSE_DATA_MASTER M3) / 100 ,2)"
			+ "THEN SHARES "
			+ "ELSE 0 "
			+ "END) AS NO_SHARES  "
			+ "FROM ( "
			+ "    select shares,typeOfStatus "
			+ "    from ("
			+ "        select shares, "
			+ "        ("
			+ "            case when status in ('RI','DC') then\r\n"
			+ "                'resident'\r\n"
			+ "            else\r\n"
			+ "                'foreign'\r\n"
			+ "            END\r\n"
			+ "        ) as typeOfStatus from benpose_data_master\r\n"
			+ ") M1 \r\n"
			+ ")"
			+ ")"
			+ "GROUP BY typeOfStatus", nativeQuery = true)
	public List<ReportsModel> getBelowLimitLevel1();

	@Query(value = "select shareholder_name as name, shares, trunc(100*(shares/(select totalshares from total_shares)),2) as percentageShare from benpose_data_master order by shares desc fetch first 10 rows only"
			+ "", nativeQuery = true)
	public List<Mimps> mimpsForm();

	@Query(value = "SELECT buyername,json_value(METADATA, '$.shareTransferRequestDetails.sellerDetails.nameOfSeller') as sellerName,json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares') as shares, trunc(100*(json_value(METADATA, '$.shareTransferRequestDetails.shareTransferDetails.noOfShares')/(select totalshares from total_shares)),2) as percentageShare FROM share_transfer_master where modified_on between TO_DATE(:dt1, 'dd-MM-yyyy') and TO_DATE(:dt2, 'dd-MM-yyyy')\r\n"
			+ "and id in (select max(id) from share_transfer_master group by uuid) and status = 'SHARE_TRANSFER_COMPLETED'", nativeQuery = true)
	public List<Mimps2> mimpsTable(String dt1, String dt2);

	@Query(value = "select shares from BENPOSE_DATA_MASTER where client_id = :clientId", nativeQuery = true)
	public Long getSharesCheckBenpose(String clientId);

}
