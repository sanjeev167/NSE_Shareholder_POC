package com.nseit.shareholder1.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.dao.BatchDAO;
import com.nseit.shareholder1.dao.BenposeDataMasterDAO;
import com.nseit.shareholder1.dao.ForeignResidentDAO;
import com.nseit.shareholder1.model.ForeignResidentCalModel;
import com.nseit.shareholder1.model.PostCal;
import com.nseit.shareholder1.modelInterfaces.ForeignResidentInterface;
import com.nseit.shareholder1.modelInterfaces.PostTmToPCalInterface;
import com.nseit.shareholder1.modelInterfaces.ReportsModel;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ForeignResidentService {

	@Autowired
	ForeignResidentDAO foreignResidentDAO;

	@Autowired
	BenposeDataMasterDAO benposeDataMasterDAO;

	@Autowired
	ResponseUtil response;

	@Autowired
	BatchDAO batchDAO;

	public Map<String, List> getForeignResidentCal(PostCal postCal) {
		try {

			List<ForeignResidentInterface> details = foreignResidentDAO.getForeignResident(postCal.getUuid());

			Integer totalShares = foreignResidentDAO.getTotalShares();

			List<ReportsModel> cal = benposeDataMasterDAO.prePostCalBenpose();

			Long residentShares;
			Long foreignShares;
			if (cal.get(0).getTypeOfStatus().equalsIgnoreCase("resident")) {
				residentShares = cal.get(0).getTotalShares();
				foreignShares = cal.get(1).getTotalShares();
			} else {
				foreignShares = cal.get(0).getTotalShares();
				residentShares = cal.get(1).getTotalShares();
			}

			for (ForeignResidentInterface detail : details) {
				if (detail.getBuyerResidentType().equalsIgnoreCase("foreignResident")
						&& detail.getSellerResidentType().equalsIgnoreCase("resident")) {
					residentShares = residentShares - detail.getNoOfShares();
					foreignShares = foreignShares + detail.getNoOfShares();
				} else if (detail.getBuyerResidentType().equalsIgnoreCase("domesticResident")
						&& detail.getSellerResidentType().equalsIgnoreCase("foreign")) {
					residentShares = residentShares + detail.getNoOfShares();
					foreignShares = foreignShares - detail.getNoOfShares();
				}
//				else {
//					continue;
//				}
			}
			ForeignResidentCalModel foreignResidentCalModel = new ForeignResidentCalModel();
			List<ForeignResidentCalModel> calList = new ArrayList<ForeignResidentCalModel>();
			foreignResidentCalModel.setTypeOfStatus("resident");
			foreignResidentCalModel.setTotalShares(residentShares);

			BigDecimal bd = new BigDecimal((residentShares * 100.00 / totalShares)).setScale(2,RoundingMode.HALF_UP);
//			System.out.println("calu-------------->"+(tmShares * 100 / totalShares));
//			System.out.println("bd------------------>"+bd);
			foreignResidentCalModel.setPercentageShares(bd.doubleValue());
			calList.add(foreignResidentCalModel);

			ForeignResidentCalModel foreignResidentCalModel1 = new ForeignResidentCalModel();
			foreignResidentCalModel1.setTypeOfStatus("foreign");
			foreignResidentCalModel1.setTotalShares(foreignShares);
			BigDecimal bd1 = new BigDecimal((foreignShares * 100.00 / totalShares)).setScale(2,RoundingMode.HALF_UP);
			
			foreignResidentCalModel1.setPercentageShares(bd1.doubleValue());
			calList.add(foreignResidentCalModel1);

			Map<String, List> foreignResident = new HashMap<String, List>();
			foreignResident.put("pre", cal);
			foreignResident.put("post", calList);
			return foreignResident;

//			return response.getAuthResponse("SUCCESS", HttpStatus.OK, calList, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
//			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public Map<String, List> getPostTmToPCal(PostCal postCal) {
		// TODO Auto-generated method stub
		try {
			List<ReportsModel> preTmToPCal = foreignResidentDAO.getPreTmToPCal();
			Integer totalShares = foreignResidentDAO.getTotalShares();
			List<PostTmToPCalInterface> postTm = foreignResidentDAO.getPostTmToPCal(postCal.getUuid());

			Long tmShares;
			Long publicShares;
			if (preTmToPCal.get(0).getTypeOfStatus().equalsIgnoreCase("TM")) {
				tmShares = preTmToPCal.get(0).getTotalShares();
				publicShares = preTmToPCal.get(1).getTotalShares();
			} else {
				publicShares = preTmToPCal.get(0).getTotalShares();
				tmShares = preTmToPCal.get(1).getTotalShares();
			}

			for (PostTmToPCalInterface tm : postTm) {
				if (tm.getBuyerTypeTCCM().equalsIgnoreCase("TM/CM")
						|| tm.getBuyerTypeTCCM().equalsIgnoreCase("associateTM/CM")
								&& tm.getSellerResidentType().equalsIgnoreCase("Public")) {
//					tm + p
					tmShares = tmShares + tm.getNoOfShares();
					publicShares = publicShares - tm.getNoOfShares();
				} else if (tm.getBuyerTypeTCCM().equalsIgnoreCase("public")
						&& tm.getSellerResidentType().equalsIgnoreCase("TM")) {
					tmShares = tmShares - tm.getNoOfShares();
					publicShares = publicShares + tm.getNoOfShares();
				}
			}
			ForeignResidentCalModel foreignResidentCalModel = new ForeignResidentCalModel();
			List<ForeignResidentCalModel> calList = new ArrayList<ForeignResidentCalModel>();
			foreignResidentCalModel.setTypeOfStatus("TM");
			foreignResidentCalModel.setTotalShares(tmShares);
			
			double per = (tmShares * 100.00 / totalShares);

			BigDecimal bd = new BigDecimal(per).setScale(2,RoundingMode.HALF_UP);
			
			foreignResidentCalModel.setPercentageShares(bd.doubleValue());
			calList.add(foreignResidentCalModel);

			ForeignResidentCalModel foreignResidentCalModel1 = new ForeignResidentCalModel();
			foreignResidentCalModel1.setTypeOfStatus("Public");
			foreignResidentCalModel1.setTotalShares(publicShares);
			BigDecimal bd1 = new BigDecimal((publicShares * 100.00 / totalShares)).setScale(2,RoundingMode.HALF_UP);
			foreignResidentCalModel1.setPercentageShares(bd1.doubleValue());
			calList.add(foreignResidentCalModel1);

			Map<String, List> tmToP = new HashMap<String, List>();
			tmToP.put("pre", preTmToPCal);
			tmToP.put("post", calList);
			return tmToP;

//			return response.getAuthResponse("SUCCESS", HttpStatus.OK, calList, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
//			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

//	public List<ReportsModel> getPreTmToPCal(String version) {
//		// TODO Auto-generated method stub
//		try {
//			List<ReportsModel> preTmToPCal = foreignResidentDAO.getPreTmToPCal();
//			return preTmToPCal;
////			return response.getAuthResponse("SUCCESS", HttpStatus.OK, preTmToPCal, version);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return null;
////			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
//		}
//	}

	public Map<String, List> getPostTmToPCalOfBatch(Long id) {
		// TODO Auto-generated method stub
		try {
			List<ReportsModel> preTmToPCal = foreignResidentDAO.getPreTmToPCal();
			Integer totalShares = foreignResidentDAO.getTotalShares();

			String batch = batchDAO.getBatch(id).getBatch();
			List<Integer> uuid = Arrays.asList(batch.split(",")).stream().map(Integer::parseInt)
					.collect(Collectors.toList());
			List<PostTmToPCalInterface> postTm = foreignResidentDAO.getPostTmToPCal(uuid);
			Long tmShares;
			Long publicShares;
			if (preTmToPCal.get(0).getTypeOfStatus().equalsIgnoreCase("TM")) {
				tmShares = preTmToPCal.get(0).getTotalShares();
				publicShares = preTmToPCal.get(1).getTotalShares();
			} else {
				publicShares = preTmToPCal.get(0).getTotalShares();
				tmShares = preTmToPCal.get(1).getTotalShares();
			}

			for (PostTmToPCalInterface tm : postTm) {
				if (tm.getBuyerTypeTCCM().equalsIgnoreCase("TM/CM")
						|| tm.getBuyerTypeTCCM().equalsIgnoreCase("associateTM/CM")
								&& tm.getSellerResidentType().equalsIgnoreCase("Public")) {
//					tm + p
					tmShares = tmShares + tm.getNoOfShares();
					publicShares = publicShares - tm.getNoOfShares();
				} else if (tm.getBuyerTypeTCCM().equalsIgnoreCase("public")
						&& tm.getSellerResidentType().equalsIgnoreCase("TM")) {
					tmShares = tmShares - tm.getNoOfShares();
					publicShares = publicShares + tm.getNoOfShares();
				}
			}
			ForeignResidentCalModel foreignResidentCalModel = new ForeignResidentCalModel();
			List<ForeignResidentCalModel> calList = new ArrayList<ForeignResidentCalModel>();
			foreignResidentCalModel.setTypeOfStatus("TM");
			foreignResidentCalModel.setTotalShares(tmShares);

			BigDecimal bd = new BigDecimal((tmShares * 100.00 / totalShares)).setScale(2,RoundingMode.HALF_UP);
			foreignResidentCalModel.setPercentageShares(bd.doubleValue());
			calList.add(foreignResidentCalModel);

			ForeignResidentCalModel foreignResidentCalModel1 = new ForeignResidentCalModel();
			foreignResidentCalModel1.setTypeOfStatus("Public");
			foreignResidentCalModel1.setTotalShares(publicShares);
			BigDecimal bd1 = new BigDecimal((publicShares * 100.00 / totalShares)).setScale(2,RoundingMode.HALF_UP);
			foreignResidentCalModel1.setPercentageShares(bd1.doubleValue());
			calList.add(foreignResidentCalModel1);

			Map<String, List> tmToP = new HashMap<String, List>();
			tmToP.put("pre", preTmToPCal);
			tmToP.put("post", calList);

			return tmToP;
//			return response.getAuthResponse("SUCCESS", HttpStatus.OK, calList, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
//			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public Map<String, List> getForeignResidentCalOfBatch(Long id) {
		try {

			String batch = batchDAO.getBatch(id).getBatch();
			List<Integer> uuid = Arrays.asList(batch.split(",")).stream().map(Integer::parseInt)
					.collect(Collectors.toList());

			List<ForeignResidentInterface> details = foreignResidentDAO.getForeignResident(uuid);

			Integer totalShares = foreignResidentDAO.getTotalShares();

			List<ReportsModel> cal = benposeDataMasterDAO.prePostCalBenpose();

			Long residentShares;
			Long foreignShares;
			if (cal.get(0).getTypeOfStatus().equalsIgnoreCase("resident")) {
				residentShares = cal.get(0).getTotalShares();
				foreignShares = cal.get(1).getTotalShares();
			} else {
				foreignShares = cal.get(0).getTotalShares();
				residentShares = cal.get(1).getTotalShares();
			}

			for (ForeignResidentInterface detail : details) {
				if (detail.getBuyerResidentType().equalsIgnoreCase("foreignResident")
						&& detail.getSellerResidentType().equalsIgnoreCase("resident")) {
					residentShares = residentShares - detail.getNoOfShares();
					foreignShares = foreignShares + detail.getNoOfShares();
				} else if (detail.getBuyerResidentType().equalsIgnoreCase("domesticResident")
						&& detail.getSellerResidentType().equalsIgnoreCase("foreign")) {
					residentShares = residentShares + detail.getNoOfShares();
					foreignShares = foreignShares - detail.getNoOfShares();
				}
//				else {
//					continue;
//				}
			}
			ForeignResidentCalModel foreignResidentCalModel = new ForeignResidentCalModel();
			List<ForeignResidentCalModel> calList = new ArrayList<ForeignResidentCalModel>();
			foreignResidentCalModel.setTypeOfStatus("resident");
			foreignResidentCalModel.setTotalShares(residentShares);

			BigDecimal bd = new BigDecimal((residentShares * 100.00 / totalShares)).setScale(2,RoundingMode.HALF_UP);
			foreignResidentCalModel.setPercentageShares(bd.doubleValue());
			calList.add(foreignResidentCalModel);

			ForeignResidentCalModel foreignResidentCalModel1 = new ForeignResidentCalModel();
			foreignResidentCalModel1.setTypeOfStatus("foreign");
			foreignResidentCalModel1.setTotalShares(foreignShares);
			BigDecimal bd1 = new BigDecimal((foreignShares * 100.00 / totalShares)).setScale(2,RoundingMode.HALF_UP);
			foreignResidentCalModel1.setPercentageShares(bd1.doubleValue());
			calList.add(foreignResidentCalModel1);

			Map<String, List> foreignResident = new HashMap<String, List>();
			foreignResident.put("pre", cal);
			foreignResident.put("post", calList);
			return foreignResident;
//			return response.getAuthResponse("SUCCESS", HttpStatus.OK, calList, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
//			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getPrePostOfBatch(String version, Long id) {
		// TODO Auto-generated method stub
		try {
			Map<String, List> tmToP = new HashMap<String, List>();
			Map<String, List> ForeignResident = getForeignResidentCalOfBatch(id);

			Map<String, List> postTmtoP = getPostTmToPCalOfBatch(id);
			tmToP.put("preTmToP", postTmtoP.get("pre"));
			tmToP.put("postTmToP", postTmtoP.get("post"));
			tmToP.put("preForeignResident", ForeignResident.get("pre"));
			tmToP.put("postForeignResident", ForeignResident.get("post"));

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, tmToP, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> getPrePost(String version, PostCal postCal) {
		// TODO Auto-generated method stub
		try {
			Map<String, List> tmToP = new HashMap<String, List>();
			Map<String, List> ForeignResident = getForeignResidentCal(postCal);

			Map<String, List> postTmtoP = getPostTmToPCal(postCal);
			tmToP.put("preTmToP", postTmtoP.get("pre"));
			tmToP.put("postTmToP", postTmtoP.get("post"));
			tmToP.put("preForeignResident", ForeignResident.get("pre"));
			tmToP.put("postForeignResident", ForeignResident.get("post"));

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, tmToP, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> getTotalShares(String version) {
		try {
			Map<String, Integer> totalShares = new HashMap<String, Integer>();
			totalShares.put("totalShares", foreignResidentDAO.getTotalShares());
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, totalShares, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
}
