package com.nseit.shareholder1.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableRowAlign;
import org.apache.poi.xwpf.usermodel.TableRowHeightRule;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTCellMergeTrackChange;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nseit.shareholder1.dao.BatchDAO;
import com.nseit.shareholder1.dao.BenposeDataMasterDAO;
import com.nseit.shareholder1.dao.CafGenerationDao;
import com.nseit.shareholder1.dao.CagDAO;
import com.nseit.shareholder1.dao.CagDownloadDAO;
import com.nseit.shareholder1.dao.ForeignResidentDAO;
import com.nseit.shareholder1.dao.MimpsReportDAO;
import com.nseit.shareholder1.dao.PacEntityDAO;
import com.nseit.shareholder1.dao.PacReportDownloadDAO;
import com.nseit.shareholder1.dao.SalesPurchaseReportDAO;
import com.nseit.shareholder1.dao.ShareHoldingPatternDao;
import com.nseit.shareholder1.dao.ShareHoldingPatternTemplateDAO;
import com.nseit.shareholder1.dao.ShareTransferDAO;
import com.nseit.shareholder1.dao.TaxTeamReportDownloadDAO;
import com.nseit.shareholder1.dao.TransferDetailsReportDownloadDAO;
import com.nseit.shareholder1.dao.TransferMasterExportDownloadDAO;
import com.nseit.shareholder1.dao.WeightedAverageDownloadDAO;
import com.nseit.shareholder1.emailphone.EmailApplication;
import com.nseit.shareholder1.metadatamodel.PacDetails;
import com.nseit.shareholder1.model.ApprovalStatusModel;
import com.nseit.shareholder1.model.AverageWeightedReportModel;
import com.nseit.shareholder1.model.CafGenerationModel;
import com.nseit.shareholder1.model.CagDownloadModel;
import com.nseit.shareholder1.model.MailModel;
import com.nseit.shareholder1.model.Mimps2Model;
import com.nseit.shareholder1.model.MimpsModel;
import com.nseit.shareholder1.model.MimpsReportModel;
import com.nseit.shareholder1.model.PacReportDownloadModel;
import com.nseit.shareholder1.model.PacReportModel;
import com.nseit.shareholder1.model.PostCal;
import com.nseit.shareholder1.model.SalesPurchaseModel;
import com.nseit.shareholder1.model.SalesPurchaseReportModel;
import com.nseit.shareholder1.model.ShareHoldingPatternTemplate;
import com.nseit.shareholder1.model.ShareTransferMaster;
import com.nseit.shareholder1.model.ShareholdingDistributionPatternWordModel;
import com.nseit.shareholder1.model.TaxTeamReportDownloadModel;
import com.nseit.shareholder1.model.TemplateModel;
import com.nseit.shareholder1.modelInterfaces.ApprovalEmailStatus;
import com.nseit.shareholder1.model.TransferDetailsReportDownload;
import com.nseit.shareholder1.model.TransferMasterExportDownloadModel;
import com.nseit.shareholder1.model.TransferMasterExportModel;
import com.nseit.shareholder1.model.WeightedAverageDownloadModel;
import com.nseit.shareholder1.model.WeightedAverageModel;
import com.nseit.shareholder1.modelInterfaces.AverageWeightedReportInterface;
import com.nseit.shareholder1.modelInterfaces.BeneficiaryDetailsInterface;
import com.nseit.shareholder1.modelInterfaces.CafListing;
import com.nseit.shareholder1.modelInterfaces.Mimps;
import com.nseit.shareholder1.modelInterfaces.Mimps2;
import com.nseit.shareholder1.modelInterfaces.MovementInterface;
import com.nseit.shareholder1.modelInterfaces.PacReportInterface;
import com.nseit.shareholder1.modelInterfaces.ReportsModel;
import com.nseit.shareholder1.modelInterfaces.SalesPurchaseInterface;
import com.nseit.shareholder1.modelInterfaces.ShareHoldingDistributionPatternTemplateInterface;
import com.nseit.shareholder1.modelInterfaces.ShareHoldingPatternTemplateInterface;
import com.nseit.shareholder1.modelInterfaces.ShareHoldingPatternInterface;
import com.nseit.shareholder1.modelInterfaces.TaxTeamInterface;
import com.nseit.shareholder1.modelInterfaces.TransferDetailsInterface;
import com.nseit.shareholder1.modelInterfaces.TransferMasterExportInterface;
import com.nseit.shareholder1.util.ResponseUtil;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReportsService {

	@Value("${filePath}")
	private String filePath;

	@Value("${templateFilePath}")
	private String templateFilePath;

	@Value("${transfermaster}")
	private String transfermaster;

	@Value("${transferDetails}")
	private String transferDetails;

	@Value("${intimation}")
	private String intimation;

	@Value("${salePurchase}")
	private String salePurchase;

	@Value("${pac}")
	private String pac;

	@Value("${cag}")
	private String cagReport;

	@Value("${beneficiary}")
	private String beneficiary;

	@Value("${nsdl}")
	private String nsdl;

	@Value("${cdsl}")
	private String cdsl;

	@Value("${mimps}")
	private String mimps;

	@Value("${taxTeam}")
	private String taxTeam;

	@Value("${weightedAverage}")
	private String weightedAverage;

	@Autowired
	WeightedAverageDownloadDAO weightedAverageDownloadDAO;

	@Autowired
	BenposeDataMasterDAO benposeDataMasterDAO;

	@Autowired
	ShareHoldingPatternDao shareHoldingPatternDao;

	@Autowired
	ResponseUtil response;

	@Autowired
	TransferMasterExportDownloadDAO transferMasterExportDownloadDAO;

	@Autowired
	ShareHoldingPatternTemplateDAO shareHoldingPatternTemplateDAO;

	@Autowired
	TaxTeamReportDownloadDAO taxTeamReportDownloadDAO;

	@Autowired
	ShareTransferDAO shareTransferDAO;

	@Autowired
	CafGenerationDao cafGenerationDao;

	@Autowired
	CagDAO cagDAO;

	@Autowired
	BatchDAO batchDAO;

	@Autowired
	MimpsReportDAO mimpsReportDAO;

	@Autowired
	PacEntityDAO pacEntityDAO;

	@Autowired
	EmailApplication emailApplication;

	@Autowired
	ForeignResidentDAO foreignResidentDAO;

	@Autowired
	SalesPurchaseReportDAO salesPurchaseReportDAO;

	@Autowired
	PacReportDownloadDAO pacReportDownloadDAO;

	@Autowired
	TransferDetailsReportDownloadDAO transferDetailsReportDownloadDAO;

	@Autowired
	CagDownloadDAO cagDownloadDAO;

	@Value("${rta}")
	private String rta;

	public ResponseEntity<?> prePostCalBenpose(String version) {
		// TODO Auto-generated method stub
		try {
			List<ReportsModel> cal = benposeDataMasterDAO.prePostCalBenpose();

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, cal, version);
		} catch (Exception e) {
			log.info("In prePostCalBenpose Exception INTERNAL_SERVER_ERROR--------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> prePostCalShare(String version) {
		// TODO Auto-generated method stub
		try {
			List<ReportsModel> cal = shareTransferDAO.totalShareTransferShares();

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, cal, version);
		} catch (Exception e) {
			log.info("In prePostCalShare Exception INTERNAL_SERVER_ERROR--------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getAboveLimitLevel1(String version) {
		// TODO Auto-generated method stub
		try {
			List<ReportsModel> cal = benposeDataMasterDAO.getAboveLimitLevel1();

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, cal, version);
		} catch (Exception e) {
			log.info("In getAboveLimitLevel1 Exception INTERNAL_SERVER_ERROR--------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getBelowLimitLevel1(String version) {
		// TODO Auto-generated method stub
		try {
			List<ReportsModel> cal = benposeDataMasterDAO.getBelowLimitLevel1();

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, cal, version);
		} catch (Exception e) {
			log.info("In getBelowLimitLevel1 Exception INTERNAL_SERVER_ERROR--------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> postCal(String version, PostCal postCal) {
		// TODO Auto-generated method stub
		try {
			List<ReportsModel> cal = shareTransferDAO.postCal(postCal.getUuid());

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, cal, version);
		} catch (Exception e) {
			log.info("In postCal Exception INTERNAL_SERVER_ERROR--------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> preCal(String version) {
		// TODO Auto-generated method stub
		try {
			List<ReportsModel> cal = shareTransferDAO.preCal();

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, cal, version);
		} catch (Exception e) {
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> cafListing(String version) {
		// TODO Auto-generated method stub
		try {
			List<CafListing> cal = shareTransferDAO.cafListing();

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, cal, version);

		} catch (Exception e) {
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public ResponseEntity<?> cafGeneration(String version, PostCal postCal) {
		try {
			List<CafListing> cal = shareTransferDAO.cafGeneration(postCal.getUuid());
			long millis = System.currentTimeMillis();
			Date date = new Date(millis);
			String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);
			String nsdlStatus = null;
			String cdslStatus = null;
			String beneficiary = null;
			CafGenerationModel cafGenerationModel = new CafGenerationModel();

			List<Integer> uuids = new ArrayList<Integer>();
			for (CafListing c : cal) {
				uuids.add(c.getUuid());
				if (c.getBuyerDpClientId().startsWith("IN") && c.getSellerDpClientId().startsWith("IN")) {

					String nsdl = cafNsdlGeneration(postCal, instant);
					cafGenerationModel.setNsdl(nsdl);
					nsdlStatus = "Y";
					cdslStatus = "N";
				} else if (c.getBuyerDpClientId().startsWith("IN") && !(c.getSellerDpClientId().startsWith("IN"))
						|| !(c.getBuyerDpClientId().startsWith("IN")) && c.getSellerDpClientId().startsWith("IN")) {
					cafGenerationModel.setCdsl(cafCdslGeneration(postCal, instant));
					cafGenerationModel.setNsdl(cafNsdlGeneration(postCal, instant));
					nsdlStatus = "Y";
					cdslStatus = "Y";
				} else if (!(c.getSellerDpClientId().startsWith("IN")) && !(c.getBuyerDpClientId().startsWith("IN"))) {
					cafGenerationModel.setCdsl(cafCdslGeneration(postCal, instant));
					nsdlStatus = "N";
					cdslStatus = "Y";
				}
			}

			String strUuid = uuids.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(","));
			cafGenerationModel.setUuid(strUuid);

			cafGenerationModel.setBeneficiaryDetials(beneficiaryDetails(postCal, instant));
			beneficiary = "Y";
			CafGenerationModel model = cafGenerationDao.save(cafGenerationModel);
//			Long id = model.getId();
			Map<String, Object> responseCaf = new HashMap<String, Object>();
			responseCaf.put("id", model.getId());
			responseCaf.put("nsdlStatus", nsdlStatus);
			responseCaf.put("cdslStatus", cdslStatus);
			responseCaf.put("beneficiary", beneficiary);

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, responseCaf, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public String cafCdslGeneration(PostCal postCal, String instant) throws Exception {
		// TODO Auto-generated method stub
//		try {
		List<CafListing> cal = shareTransferDAO.cafGeneration(postCal.getUuid());

		long millis = System.currentTimeMillis();

		// creating a new object of the class Date

		Date date = new Date(millis);
		SimpleDateFormat formatter;

		formatter = new SimpleDateFormat("MMMM, dd yyyy");
		String strDate = formatter.format(date);

		// String noOfshares1=String.valueOf(cal.get(0).getNoOfShares());
		// findText.add("{$NOOFSHARES1}");
		// replaceText.add(noOfshares1);

		FileInputStream fis = new FileInputStream(templateFilePath + cdsl);
		XWPFDocument doc = new XWPFDocument(OPCPackage.open(fis));

		List<XWPFTable> table = doc.getTables();
		doc = replaceTextFor(doc, "{$DATE}", strDate);
//			String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);
		saveWord(filePath + "CA-CDSL - write-" + instant + ".docx", doc);

		// List<XWPFTable> table = doc.getTables();
		String noOfRecords = String.valueOf(cal.size());
		System.out.println("noOfRecords-------------" + noOfRecords);
		int sum = 0;
		for (CafListing c : cal) {
			sum = sum + c.getNoOfShares();
		}
		System.out.println("noOfShare-------------" + sum);
		XWPFTable tbl = table.get(1);
		tbl.getRow(1).getCell(1).setText(String.valueOf(sum));
		tbl.getRow(1).getCell(1).getParagraphArray(0).setAlignment(ParagraphAlignment.RIGHT);
		tbl.getRow(0).getCell(1).setText(noOfRecords);
		tbl.getRow(0).getCell(1).getParagraphArray(0).setAlignment(ParagraphAlignment.RIGHT);

		XWPFTable tb2 = table.get(3);
		tb2.getRow(1).getCell(1).setText(String.valueOf(sum));
		tb2.getRow(0).getCell(1).setText(noOfRecords);
		saveWord(filePath + "CA-CDSL - write-" + instant + ".docx", doc);
		fis.close();

		List<XWPFTable> table1 = doc.getTables();
		XWPFTable tb3 = table1.get(2);
		List<TemplateModel> list = new ArrayList<TemplateModel>();
		for (int i = 0; i < cal.size(); i++) {
			list.add(new TemplateModel(i + 1, cal.get(i).getSellername(), cal.get(i).getSellerClientId(),
					cal.get(i).getNoOfShares()));
			// list.add(new TemplateModel(i + 1, "Prabhat " + i + " Test", 140 + i, 15 -
			// i));
		}

		ObjectMapper om = new ObjectMapper();
		Map<String, Object> objMap = om.convertValue(list.get(0), Map.class);
		Set<String> labelList = objMap.keySet();
		insertRowsInTable(list, tb3, labelList);

		List<XWPFTable> table2 = doc.getTables();
		XWPFTable tb4 = table2.get(4);
		List<TemplateModel> list1 = new ArrayList<TemplateModel>();
		for (int i = 0; i < cal.size(); i++) {
			list1.add(new TemplateModel(i + 1, cal.get(i).getBuyername(), cal.get(i).getBuyerClientId(),
					cal.get(i).getNoOfShares()));
			// list.add(new TemplateModel(i + 1, "Prabhat " + i + " Test", 140 + i, 15 -
			// i));
		}
		insertRowsInTable(list1, tb4, labelList);

		String finalFile = filePath + "CA-CDSL - write-" + instant + ".docx";
		saveWord(finalFile, doc);

//		CafGenerationModel 

		fis.close();
		// fis1.close();

//		beneficiaryDetails(postCal, instant);
//		return response.getAuthResponse("SUCCESS", HttpStatus.OK, cal, null);
		return finalFile;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, null);
//		}

	}

	public String cafNsdlGeneration(PostCal postCal, String instant) throws Exception {

//		try {
		List<CafListing> cal = shareTransferDAO.cafGeneration(postCal.getUuid());

		long millis = System.currentTimeMillis();

		// creating a new object of the class Date

		Date date = new Date(millis);
		SimpleDateFormat formatter;

		formatter = new SimpleDateFormat("MMMM, dd yyyy");
		String strDate = formatter.format(date);
		FileInputStream fis1 = new FileInputStream(templateFilePath + nsdl);
		XWPFDocument doc1 = new XWPFDocument(OPCPackage.open(fis1));

		List<XWPFTable> tableNsdl = doc1.getTables();
		doc1 = replaceTextFor(doc1, "{$DATE}", strDate);
		// saveWord(filePath + "CA-NDSL - write.docx", doc1);

		XWPFTable tableTemplate = doc1.getTables().get(1);
		CTTbl cTTblTemplate = tableTemplate.getCTTbl();
		XmlCursor cursor = setCursorToNextStartToken(cTTblTemplate);

		for (int i = 1; i < cal.size(); i++) {
			XWPFParagraph paragraph = doc1.insertNewParagraph(cursor); // insert new empty paragraph
			cursor = setCursorToNextStartToken(paragraph.getCTP());
			XWPFTable table = doc1.insertNewTbl(cursor); // insert new empty table at position t
			cursor = setCursorToNextStartToken(table.getCTTbl());

			XWPFTable tableCopy = new XWPFTable((CTTbl) cTTblTemplate.copy(), doc1); // copy the template table

			tableCopy.getRow(0).getCell(1).setText(cal.get(i).getBuyerClientId());
			tableCopy.getRow(1).getCell(1).setText(cal.get(i).getBuyername());
			tableCopy.getRow(2).getCell(1).setText(cal.get(i).getBuyerClientId());
			tableCopy.getRow(3).getCell(1).setText(cal.get(i).getBuyerNameOfDP());
			tableCopy.getRow(4).getCell(1).setText(cal.get(i).getNoOfShares().toString());
			doc1.setTable(i + 1, tableCopy);
			paragraph = doc1.insertNewParagraph(cursor); // insert new empty paragraph
			cursor = setCursorToNextStartToken(paragraph.getCTP());
		}
		// Add 1st record in template Table
		if (cal.size() > 0) {
			tableTemplate.getRow(0).getCell(1).setText(cal.get(0).getBuyerClientId());
			tableTemplate.getRow(1).getCell(1).setText(cal.get(0).getBuyername());
			tableTemplate.getRow(2).getCell(1).setText(cal.get(0).getBuyerClientId());
			tableTemplate.getRow(3).getCell(1).setText(cal.get(0).getBuyerNameOfDP());
			tableTemplate.getRow(4).getCell(1).setText(cal.get(0).getNoOfShares().toString());
		}

		// List<XWPFTable> tableNsdl = doc1.getTables();
		int index = doc1.getTables().size() - 1;
		System.out.println("listsize________________________________" + doc1.getTables().size());
		System.out.println("listsize________________________________" + doc1.getTables().get(index));
		// List<XWPFTable> buyerTable = tableNsdl.subList(0, 3);
		// List<XWPFTable> sellerTable = tableNsdl.subList(3, tableNsdl.size());
		// XWPFDocument d = doc1;
		XWPFTable tableTemplate3 = doc1.getTables().get(index);
		cTTblTemplate = tableTemplate3.getCTTbl();
		cursor = setCursorToNextStartToken(cTTblTemplate);

		// saveWord(filePath + "CA-NDSL - write.docx", doc1);

		for (int i = 1; i < cal.size(); i++) {
			XWPFParagraph paragraph = doc1.insertNewParagraph(cursor); // insert new empty paragraph

			cursor = setCursorToNextStartToken(paragraph.getCTP());
			XWPFTable table = doc1.insertNewTbl(cursor); // insert new empty table at position t
			cursor = setCursorToNextStartToken(table.getCTTbl());

			XWPFTable tableCopy = new XWPFTable((CTTbl) cTTblTemplate.copy(), doc1); // copy the template table
			tableCopy.getRow(0).getCell(1).setText(cal.get(i).getSellerClientId());
			tableCopy.getRow(1).getCell(1).setText(cal.get(i).getSellername());
			tableCopy.getRow(2).getCell(1).setText(cal.get(i).getSellerClientId());
			tableCopy.getRow(3).getCell(1).setText(cal.get(i).getSellerNameOfDP());
			tableCopy.getRow(4).getCell(1).setText(cal.get(i).getNoOfShares().toString());
			doc1.setTable(index + i, tableCopy);
			paragraph = doc1.insertNewParagraph(cursor); // insert new empty paragraph
			cursor = setCursorToNextStartToken(paragraph.getCTP());
			// }
		}
		// Add 1st record in template Table
		// XWPFTable tableTemplate3 = doc1.getTables().get(index);
		if (cal.size() > 0) {
			tableTemplate3.getRow(0).getCell(1).setText(cal.get(0).getSellerClientId());
			tableTemplate3.getRow(1).getCell(1).setText(cal.get(0).getSellername());
			tableTemplate3.getRow(2).getCell(1).setText(cal.get(0).getSellerClientId());
			tableTemplate3.getRow(3).getCell(1).setText(cal.get(0).getSellerNameOfDP());
			tableTemplate3.getRow(4).getCell(1).setText(cal.get(0).getNoOfShares().toString());
			doc1.setTable(index, tableTemplate3);

		}

		String finalFile = filePath + "CA-NDSL - write-" + instant + ".docx";

		saveWord(filePath + "CA-NDSL - write-" + instant + ".docx", doc1);

		fis1.close();

//		beneficiaryDetails(postCal, instant);
//		return response.getAuthResponse("SUCCESS", HttpStatus.OK, cal, null);
		return finalFile;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, null);
//		}
	}

	public String beneficiaryDetails(PostCal postcal, String instant) throws Exception {
		File file = new File(templateFilePath + beneficiary);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		List<BeneficiaryDetailsInterface> benficiaryDetials1 = shareTransferDAO
				.getBeneficiaryDetails(postcal.getUuid());

		Map<String, Object[]> data = new TreeMap<String, Object[]>();

		// Writing data to Object[]
		// using put() method
//		data.put("1", new Object[] { "ID", "NAME", "LASTNAME" });
		for (int i = 0; i < benficiaryDetials1.size(); i++) {
			Double duty = benficiaryDetials1.get(i).getAmount() * 0.015 * 0.01;
			String stampDuty;
			if (benficiaryDetials1.get(i).getStampDuty() == null) {
				stampDuty = "no";
			} else {
				stampDuty = benficiaryDetials1.get(i).getStampDuty();
			}
			data.put(String.valueOf(i),
					new Object[] { i + 1, benficiaryDetials1.get(i).getBuyername(),
							benficiaryDetials1.get(i).getBuyerClientId().substring(0,
									benficiaryDetials1.get(i).getBuyerClientId().indexOf("-")),
							benficiaryDetials1.get(i).getBuyerClientId().substring(0,
									benficiaryDetials1.get(i).getBuyerClientId().indexOf("-")),
							benficiaryDetials1.get(i).getBuyerClientId()
									.substring(benficiaryDetials1.get(i).getBuyerClientId().indexOf("-") + 1),
							benficiaryDetials1.get(i).getSellerClientId().substring(0,
									benficiaryDetials1.get(i).getSellerClientId().indexOf("-")),
							benficiaryDetials1.get(i).getSellerClientId()
									.substring(benficiaryDetials1.get(i).getSellerClientId().indexOf("-") + 1),
							"NA", benficiaryDetials1.get(i).getNoOfShares(), "NA", stampDuty,
							"0.015% of consideration", "NA", benficiaryDetials1.get(i).getPricePerShare(),
							"NA", benficiaryDetials1.get(i).getAmount(), duty,
							benficiaryDetials1.get(i).getSellerClientId().substring(0,
									benficiaryDetials1.get(i).getSellerClientId().indexOf("-")),
							benficiaryDetials1.get(i).getSellerClientId()
									.substring(benficiaryDetials1.get(i).getSellerClientId().indexOf("-") + 1),
							benficiaryDetials1.get(i).getUtrId() });
		}
		// Iterating over data and writing it to sheet
		// Set<String> keyset = data.keySet();

		// int rownum = 1;

		// XSSFCellStyle style = workbook.createCellStyle();
		// style.setBorderBottom(BorderStyle.THIN);
		// style.setBorderTop(BorderStyle.THIN);
		// style.setBorderRight(BorderStyle.THIN);
		// style.setBorderLeft(BorderStyle.THIN);
		// style.setAlignment(HorizontalAlignment.RIGHT);

		// for (String key : keyset) {

		// 	// Creating a new row in the sheet
		// 	Row row = sheet.createRow(rownum++);

		// 	Object[] objArr = data.get(key);

		// 	int cellnum = 0;

		// 	for (Object obj : objArr) {

		// 		// This line creates a cell in the next
		// 		// column of that row
		// 		Cell cell = row.createCell(cellnum++);

		// 		if (obj instanceof String) {
		// 			cell.setCellValue((String) obj);
		// 			cell.setCellStyle(style);
		// 		} else if (obj instanceof Integer) {
		// 			cell.setCellValue((Integer) obj);
		// 			cell.setCellStyle(style);
		// 		}
		// 	}
		// }
		workbook = fillData(workbook, data, 0, 1);
		Date date = new Date(System.currentTimeMillis());
//		String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);

		String finalFile = filePath + "BeneficiaryDetails - " + instant + " - write.xlsx";

		FileOutputStream out = new FileOutputStream(new File(finalFile));
		workbook.write(out);

		// Closing file output connections
		out.close();
//		return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, null);
		return finalFile;
	}

	static XmlCursor setCursorToNextStartToken(XmlObject object) {
		XmlCursor cursor = object.newCursor();
		cursor.toEndToken(); // Now we are at end of the XmlObject.
		// There always must be a next start token.
		while (cursor.hasNextToken() && cursor.toNextToken() != org.apache.xmlbeans.XmlCursor.TokenType.START)
			;
		// Now we are at the next start token and can insert new things here.
		return cursor;
	}

	private static XWPFDocument replaceTextFor(XWPFDocument doc, String findText, String replaceText) {
		// for(int i=0;i<fText.size();i++) {
		// String findText=fText.get(i);
		// String replaceText=rText.get(i);
		try {
			doc.getParagraphs().forEach(p -> {
				p.getRuns().forEach(run -> {
					String text = run.text();
					if (text.contains(findText)) {
						run.setText(text.replace(findText, replaceText), 0);
					}
				});
			});
			// }
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	private static void saveWord(String filePath, XWPFDocument doc) throws FileNotFoundException, IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			doc.write(out);
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	public void copyOfTemplate() {
		// Path fileToDeletePath = Paths.get("D://template.docx");

		Path copied = (Path) Paths.get("D://template.docx");
		Path originalPath = (Path) Paths.get("D://keycloak-example//template.docx");
		try {
			Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insertRowsInTable(List<?> list, XWPFTable tbl, Set<String> labelList) {
		tbl.setLeftBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
		tbl.setRightBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
		tbl.setTopBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
		tbl.setBottomBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
		tbl.setInsideHBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
		tbl.setInsideVBorder(XWPFBorderType.SINGLE, 1, 0, "000000");

		ObjectMapper om = new ObjectMapper();
		for (int k = 0; k < list.size(); k++) {
			Map<String, Object> objMap = om.convertValue(list.get(k), Map.class);
			if (labelList == null) {
				labelList = objMap.keySet();
			}
			XWPFTableRow newRow = tbl.createRow();
			int i = 0;
			List<XWPFTableCell> tableCells = newRow.getTableCells();
			for (String label : labelList) {
				int count = i++;
				tableCells.get(count).getParagraphArray(0).setAlignment(ParagraphAlignment.RIGHT);
				tableCells.get(count).setText(String.valueOf(objMap.get(label)));
			}
		}
	}

	public ResponseEntity<?> writeCellsMimps(String version, String month, String year) {
		XWPFDocument doc = null;

		long millis = System.currentTimeMillis();

		// creating a new object of the class Date

		String dt1;
		String dt2;
		if (month.equalsIgnoreCase("jan-mar")) {
			dt1 = "01-01-" + year;
			dt2 = "31-03-" + year;
		} else if (month.equalsIgnoreCase("apr-jun")) {
			dt1 = "01-04-" + year;
			dt2 = "30-06-" + year;
		} else if (month.equalsIgnoreCase("jul-sep")) {
			dt1 = "01-07-" + year;
			dt2 = "30-09-" + year;
		} else if (month.equalsIgnoreCase("oct-dec")) {
			dt1 = "01-10-" + year;
			dt2 = "31-12-" + year;
		} else {
			dt1 = "01-01-" + year;
			dt2 = "31-03-" + year;
		}
		Date date = new Date(millis);
		SimpleDateFormat formatter;

		formatter = new SimpleDateFormat("MMMM, dd yyyy");
//		String strDate = formatter.format(date);

		try {

			Date dat = new SimpleDateFormat("dd-MM-yyyy").parse(dt2);

			String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);

			String strDate = formatter.format(dat);
			FileInputStream fis = new FileInputStream(templateFilePath + mimps);
			// FileOutputStream fisout = new FileOutputStream("D://template.docx");
			doc = new XWPFDocument(OPCPackage.open(fis));
			doc = replaceTextFor(doc, "{$DATE}", strDate);
			// Iterator<IBodyElement> bodyElementIterator = doc.getBodyElementsIterator();
			String finalFile = filePath + "/mimps-write-" + instant + ".docx";
			FileOutputStream out = new FileOutputStream(finalFile);
			List<XWPFTable> table = doc.getTables();
			System.out.println("table------------------>" + table);
			// Output stream
			List<MimpsModel> list = new ArrayList<MimpsModel>();
			List<Mimps> mimpsList = benposeDataMasterDAO.mimpsForm();

			for (int l = 0; l < mimpsList.size(); l++) {
				// list.add(new TemplateModel(l + 1, "Prabhat " + l + " Test", 140 + l, 15 -
				// l));
				list.add(new MimpsModel(l + 1, mimpsList.get(l).getName(), mimpsList.get(l).getShares(),
						mimpsList.get(l).getPercentageShare()));
			}
			XWPFTable tbl = table.get(0);

			ObjectMapper om = new ObjectMapper();
			Map<String, Object> objMap = om.convertValue(list.get(0), Map.class);
			Set<String> labelList = objMap.keySet();
			insertRowsInTable(list, tbl, labelList);

			List<Mimps2Model> list1 = new ArrayList<Mimps2Model>();
			List<Mimps2> mimpsList2 = benposeDataMasterDAO.mimpsTable(dt1, dt2);
			XWPFTable tbl1 = table.get(1);
			if (mimpsList2.size() > 0) {
				for (int l = 0; l < mimpsList2.size(); l++) {
					list1.add(
							new Mimps2Model(l + 1, mimpsList2.get(l).getBuyername(), mimpsList2.get(l).getSellerName(),
									mimpsList2.get(l).getShares(), mimpsList2.get(l).getPercentageShare()));
				}

				tbl1.removeRow(1);

				ObjectMapper om1 = new ObjectMapper();
				Map<String, Object> objMap1 = om1.convertValue(list1.get(0), Map.class);
				Set<String> labelList1 = objMap1.keySet();
				insertRowsInTable(list1, tbl1, labelList1);
			} else {
				tbl1.getRow(1).getCell(1).setText(
						"No shareholder falling under Regulation 17 (i.e., shareholder holding more than 5 % or 15% (as the case may be) has acquired any shares nor any person has acquired equity shares which constitutes more than 5% or 15% (as the case may be) of the paid-up equity share capital of NSEIL, during the quarter.");
			}

			tableC(table);
//			System.out.println("size------------->" + tbl2.getRows().size());
			doc.write(out);
			out.close();

			MimpsReportModel mimpsReportModel = new MimpsReportModel();
			mimpsReportModel.setFilePath(finalFile);

			MimpsReportModel model = mimpsReportDAO.save(mimpsReportModel);
			Map<String, Object> responseMimps = new HashMap<String, Object>();
			responseMimps.put("id", model.getId());
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, responseMimps, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		} finally {
			if (doc != null)
				try {
					doc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public void tableC(List<XWPFTable> table) throws Exception {

		XWPFTable tbl2 = table.get(2);
		int templateRow = tbl2.getNumberOfRows() - 1;
		XWPFTableRow lastRow = tbl2.getRows().get(templateRow);
		CTRow ctrow = CTRow.Factory.parse(lastRow.getCtRow().newInputStream());
		CTRow abc = lastRow.getCtRow();
		ObjectMapper om1 = new ObjectMapper();
		tbl2.setLeftBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
		tbl2.setRightBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
		tbl2.setTopBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
		tbl2.setBottomBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
		tbl2.setInsideHBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
		tbl2.setInsideVBorder(XWPFBorderType.SINGLE, 1, 0, "000000");

		XWPFTableRow newRow = new XWPFTableRow(abc, tbl2);
		newRow.setHeightRule(TableRowHeightRule.AUTO);

		List<ShareHoldingDistributionPatternTemplateInterface> tmList = shareHoldingPatternTemplateDAO
				.getShareHoldingDistributionTM();
		tbl2.getRow(1).getCell(1).setText("A. Trading Member");
		List<ShareholdingDistributionPatternWordModel> insertTm = new ArrayList<ShareholdingDistributionPatternWordModel>();

		Long totalShares = (long) 0;
		Double percentage = (double) 0.00;
		for (int i = 0; i < tmList.size(); i++) {
			String categoryOfShareholder = tmList.get(i).getSubCategory();
			totalShares = totalShares + tmList.get(i).getTotalshares();
			percentage = percentage + tmList.get(i).getPercentageShare();
			if (tmList.get(i).getBuyerNriType() != null) {
				categoryOfShareholder = tmList.get(i).getBuyerNriType();
			} else if (tmList.get(i).getCorpListed() != null) {
				categoryOfShareholder = categoryOfShareholder + "(" + tmList.get(i).getCorpListed() + ")";
			}
			insertTm.add(new ShareholdingDistributionPatternWordModel(String.valueOf(i + 1), categoryOfShareholder,
					String.valueOf(tmList.get(i).getShareholderno()), String.valueOf(tmList.get(i).getTotalshares()),
					String.valueOf(tmList.get(i).getPercentageShare())));
		}
		ObjectMapper om3 = new ObjectMapper();
		Map<String, Object> objMap3 = om3.convertValue(insertTm.get(0), Map.class);
		Set<String> labelList3 = objMap3.keySet();

		insertRowsInTable(insertTm, tbl2, labelList3);
		XWPFTableRow totalA = tbl2.createRow();

		totalA.getCell(1).setText("Total(A)");
		totalA.getCell(2).setText(String.valueOf(tmList.size()));
		totalA.getCell(3).setText(String.valueOf(totalShares));
		BigDecimal bdA = new BigDecimal((percentage)).setScale(2, RoundingMode.HALF_UP);
		totalA.getCell(4).setText(String.valueOf(bdA.doubleValue()));

		XWPFTableRow newRows = tbl2.createRow();

		newRows.getCell(1).setText("B. Associate Trading Member");
		cellMerge(newRows);

		List<ShareHoldingDistributionPatternTemplateInterface> associateTmList = shareHoldingPatternTemplateDAO
				.getShareHoldingDistributionAssociateTmCm();
		insertTm = new ArrayList<ShareholdingDistributionPatternWordModel>();

		Long totalSharesB = (long) 0;
		Double percentageB = (double) 0.00;

		for (int i = 0; i < associateTmList.size(); i++) {
			String categoryOfShareholder = associateTmList.get(i).getSubCategory();
			totalSharesB = totalSharesB + associateTmList.get(i).getTotalshares();
			percentageB = percentageB + associateTmList.get(i).getPercentageShare();
			if (associateTmList.get(i).getBuyerNriType() != null) {
				categoryOfShareholder = associateTmList.get(i).getBuyerNriType();
			} else if (associateTmList.get(i).getCorpListed() != null) {
				categoryOfShareholder = categoryOfShareholder + "(" + associateTmList.get(i).getCorpListed() + ")";
			}
			insertTm.add(new ShareholdingDistributionPatternWordModel(String.valueOf(i + 1), categoryOfShareholder,
					String.valueOf(associateTmList.get(i).getShareholderno()),
					String.valueOf(associateTmList.get(i).getTotalshares()),
					String.valueOf(associateTmList.get(i).getPercentageShare())));
		}
		om3 = new ObjectMapper();
		objMap3 = om3.convertValue(insertTm.get(0), Map.class);
		labelList3 = objMap3.keySet();

		insertRowsInTable(insertTm, tbl2, labelList3);
		totalA = tbl2.createRow();

		totalA.getCell(1).setText("Total(B)");
		totalA.getCell(2).setText(String.valueOf(associateTmList.size()));
		BigDecimal bdB = new BigDecimal((percentageB)).setScale(2, RoundingMode.HALF_UP);
		totalA.getCell(3).setText(String.valueOf(totalSharesB));
		totalA.getCell(4).setText(String.valueOf(bdB.doubleValue()));

		newRows = tbl2.createRow();
		cellMerge(newRows);

		totalA = tbl2.createRow();

		totalA.getCell(1).setText("Total(A+B)");
		totalA.getCell(2).setText(String.valueOf(tmList.size() + associateTmList.size()));
		BigDecimal bdAB = new BigDecimal((percentage + percentageB)).setScale(2, RoundingMode.HALF_UP);
		totalA.getCell(3).setText(String.valueOf(totalShares + totalSharesB));
		totalA.getCell(4).setText(String.valueOf(bdAB.doubleValue()));

		newRows = tbl2.createRow();
		newRows.getCell(1).setText("C. Public");
		cellMerge(newRows);

		List<ShareHoldingDistributionPatternTemplateInterface> publicList = shareHoldingPatternTemplateDAO
				.getShareHoldingDistributionPublic();
		insertTm = new ArrayList<ShareholdingDistributionPatternWordModel>();

		Long totalSharesC = (long) 0;
		Double percentageC = (double) 0.00;

		for (int i = 0; i < publicList.size(); i++) {
			String categoryOfShareholder = publicList.get(i).getSubCategory();
			totalSharesC = totalSharesC + publicList.get(i).getTotalshares();
			percentageC = percentageC + publicList.get(i).getPercentageShare();
			if (publicList.get(i).getBuyerNriType() != null) {
				categoryOfShareholder = publicList.get(i).getBuyerNriType();
			} else if (publicList.get(i).getCorpListed() != null) {
				categoryOfShareholder = categoryOfShareholder + "(" + publicList.get(i).getCorpListed() + ")";
			}
			insertTm.add(new ShareholdingDistributionPatternWordModel(String.valueOf(i + 1), categoryOfShareholder,
					String.valueOf(publicList.get(i).getShareholderno()),
					String.valueOf(publicList.get(i).getTotalshares()),
					String.valueOf(publicList.get(i).getPercentageShare())));
		}
		om3 = new ObjectMapper();
		objMap3 = om3.convertValue(insertTm.get(0), Map.class);
		labelList3 = objMap3.keySet();

		insertRowsInTable(insertTm, tbl2, labelList3);
		totalA = tbl2.createRow();

		totalA.getCell(1).setText("Total(C)");
		BigDecimal perC = new BigDecimal((percentageC)).setScale(2, RoundingMode.HALF_UP);
		totalA.getCell(2).setText(String.valueOf(publicList.size()));
		totalA.getCell(3).setText(String.valueOf(totalSharesC));
		totalA.getCell(4).setText(String.valueOf(perC.doubleValue()));

		newRows = tbl2.createRow();
		cellMerge(newRows);

		totalA = tbl2.createRow();

		totalA.getCell(1).setText("Total(A+B+C)");
		BigDecimal bdC = new BigDecimal((percentage + percentageB + percentageC)).setScale(2, RoundingMode.HALF_UP);
		totalA.getCell(2).setText(String.valueOf(tmList.size() + associateTmList.size() + publicList.size()));
		totalA.getCell(3).setText(String.valueOf(totalShares + totalSharesB + totalSharesC));
		totalA.getCell(4).setText(String.valueOf(bdC.doubleValue()));

	}

	public void cellMerge(XWPFTableRow newRows) throws Exception {
		CTTcPr tcpcr = newRows.getCell(1).getCTTc().getTcPr();
		XWPFTableCell newCells = newRows.getCell(1);
		if (tcpcr == null) {
			tcpcr = newCells.getCTTc().addNewTcPr();
		}
		CTDecimalNumber newGridspan = tcpcr.getGridSpan();
		if (newGridspan == null) {
			newGridspan = tcpcr.addNewGridSpan();
		}
//		tcpcr.setCellMerge(CTCellMergeTrackChange.)
		newGridspan.setVal(BigInteger.valueOf((long) 4));
		newRows.removeCell(4);
		newRows.removeCell(3);
		newRows.removeCell(2);
	}

	public ResponseEntity<?> topTenShareholders(String version) {
		// TODO Auto-generated method stub
		try {
			List<MimpsModel> list = new ArrayList<MimpsModel>();
			List<Mimps> mimpsList = benposeDataMasterDAO.mimpsForm();
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, mimpsList, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);

		}
	}

	public ResponseEntity<?> shareHoldingDistribution(String version) {
		try {
//			List<ShareHoldingPatternInterface> shareHoldingDistribution = shareHoldingPatternDao
//					.shareHoldingDistribution();
			List<ShareHoldingDistributionPatternTemplateInterface> shareHoldingDistribution = shareHoldingPatternTemplateDAO
					.getShareHoldingDistribution();
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, shareHoldingDistribution, version);
		} catch (Exception e) {
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> cagDisplay(String version) {
		// TODO Auto-generated method stub

		try {
			List<Mimps> cag = cagDAO.getCag();
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, cag, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> cag(String version) {
		// TODO Auto-generated method stub
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;

		try {
			List<Mimps> cag = cagDAO.getCag();
			File file = new File(templateFilePath + cagReport);
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Map<String, Object[]> data = new TreeMap<String, Object[]>();

			for (int i = 0; i < cag.size(); i++) {
				data.put(String.valueOf(i), new Object[] { i + 1, cag.get(i).getName(), cag.get(i).getShares(),
						cag.get(i).getPercentageShare()});
			}

			// Iterating over data and writing it to sheet
			workbook = fillData(workbook, data, 0, 1);
			Date date = new Date(System.currentTimeMillis());
			String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);
			String finalFile = filePath + "cag - " + instant + " - write.xlsx";
			FileOutputStream out = new FileOutputStream(new File(finalFile));
			workbook.write(out);

			// Closing file output connections
			out.close();

			CagDownloadModel cagDownloadModel = new CagDownloadModel();
			cagDownloadModel.setFilePath(finalFile);

			CagDownloadModel model = cagDownloadDAO.save(cagDownloadModel);

			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("id", model.getId());

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, responseMap, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);

		}
	}

	public ResponseEntity<?> salesPurchase(String version, Integer month, Integer year) {
		// TODO Auto-generated method stub
		XWPFDocument doc = null;
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		SimpleDateFormat formatter;

		formatter = new SimpleDateFormat("MMMM, dd yyyy");
		try {

//			List;
//			YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), Month.JANUARY);
//			System.out.println("start---------------->"+yearMonth.atEndOfMonth());
			Calendar c = Calendar.getInstance();

			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month);

			c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));

			SimpleDateFormat dat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
			String startDate = formatter1.format(dat.parse(c.getTime().toString()));

			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

			String endDate = formatter1.format(dat.parse(c.getTime().toString()));

			String strDate = formatter.format(date);

			FileInputStream fis = new FileInputStream(templateFilePath + salePurchase);
			// FileOutputStream fisout = new FileOutputStream("D://template.docx");
			doc = new XWPFDocument(OPCPackage.open(fis));
			doc = replaceTextFor(doc, "{$DATE}", strDate);
			doc = replaceTextFor(doc, "{$MONTHYEAR}", Month.of(month + 1).name() + ", " + String.valueOf(year));
			String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);
			String finalFile = filePath + "Sale Purchase Transaction-" + instant + ".docx";
			FileOutputStream out = new FileOutputStream(finalFile);

			log.info("month--------------->" + month + " year--------------->" + year);
			log.info("dt1----------------------->" + startDate);
			log.info("dt2----------------------->" + endDate);
			List<SalesPurchaseInterface> salePurchase = shareTransferDAO.getSalePurchase(startDate, endDate);
			List<XWPFTable> table = doc.getTables();
			List<SalesPurchaseModel> list = new ArrayList<SalesPurchaseModel>();

			for (int l = 0; l < salePurchase.size(); l++) {
				// list.add(new TemplateModel(l + 1, "Prabhat " + l + " Test", 140 + l, 15 -
				// l));
				Date mdate = new Date(salePurchase.get(l).getModifiedOn().getTime());
				String dateModified = formatter1.format(dat.parse(mdate.toString()));
				String pricePerShare;
				if (salePurchase.get(l).getModeOfTransfer().equalsIgnoreCase("gift")) {
					pricePerShare = "Gift";
				} else {
					pricePerShare = String.valueOf(salePurchase.get(l).getPricePerShare());
				}
				String transactionParty;
				if (salePurchase.get(l).getTransactionParty() == null) {
					transactionParty = "no";
				} else {
					transactionParty = salePurchase.get(l).getTransactionParty();
				}
				list.add(new SalesPurchaseModel(dateModified, salePurchase.get(l).getCategoryTransfer(),
						salePurchase.get(l).getNoOfShares(), pricePerShare, transactionParty));
			}
			XWPFTable tbl = table.get(0);

			ObjectMapper om = new ObjectMapper();
			Map<String, Object> objMap = om.convertValue(list.get(0), Map.class);
			Set<String> labelList = objMap.keySet();
			insertRowsInTable(list, tbl, labelList);

			doc.write(out);
			out.close();

			SalesPurchaseReportModel salesPurchaseReportModel = new SalesPurchaseReportModel();
			salesPurchaseReportModel.setFilePath(finalFile);
			SalesPurchaseReportModel model = salesPurchaseReportDAO.save(salesPurchaseReportModel);
			Map<String, Object> responseCaf = new HashMap<String, Object>();
			responseCaf.put("id", model.getId());
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, responseCaf, version);
//		return finalFile;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		// HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> sendRta(String version, Long id) {
		try {
			CafGenerationModel files = cafGenerationDao.getCaf(id);
			List<String> filesList = new ArrayList<String>();
			if (files.getCdsl() != null) {
				filesList.add(files.getCdsl());
			}
			if (files.getNsdl() != null) {
				filesList.add(files.getNsdl());
			}
			filesList.add(files.getBeneficiaryDetials());

			MailModel mailModel = new MailModel();
			mailModel.setTo(rta);
			mailModel.setSubject("CAF Generated Documents");
			mailModel.setName("Rta");
			mailModel.setContent("Pfa");

			emailApplication.sendAttach(mailModel, filesList);
			CafGenerationModel sendToRta = (CafGenerationModel) files.clone();
			sendToRta.setId(null);
			Timestamp instant = Timestamp.from(Instant.now());
			sendToRta.setSendToRta(instant);
			cafGenerationDao.save(sendToRta);
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, files, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> nsdlDownloadFile(String version, Long id) {
		try {
			log.info("id------->" + id);
			CafGenerationModel cafFiles = cafGenerationDao.getById(id);

			String filePath1 = cafFiles.getNsdl();

			if (filePath1 == null) {
				return response.getAuthResponse("FILE_NOT_FOUND", HttpStatus.BAD_REQUEST, null, version);
			} else {
				String filePath = pdfConverter(filePath1);
				File file = new File(filePath);

				Path path = Paths.get(file.getAbsolutePath());
				System.out.println("file-------------" + Files.probeContentType(path));

				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

				log.info("resource-------------->" + resource);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + filePath.substring(filePath.lastIndexOf("/") + 1));
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");

				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)

						.body(resource);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In shareTransferDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	public ResponseEntity<?> cdslDownloadFile(String version, Long id) {

		try {
			log.info("id------->" + id);
			CafGenerationModel cafFiles = cafGenerationDao.getById(id);

			String filePath1 = cafFiles.getCdsl();

			if (filePath1 == null) {
				return response.getAuthResponse("FILE_NOT_FOUND", HttpStatus.BAD_REQUEST, null, version);
			} else {
				String filePath = pdfConverter(filePath1);
				File file = new File(filePath);

				Path path = Paths.get(file.getAbsolutePath());
				System.out.println("file-------------" + Files.probeContentType(path));

				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

				log.info("resource-------------->" + resource);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + filePath.substring(filePath.lastIndexOf("/") + 1));
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)

						.body(resource);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In shareTransferDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	public ResponseEntity<?> mimpsDownloadFile(String version, Long id) {

		try {
			log.info("id------->" + id);
			MimpsReportModel mimpsReport = mimpsReportDAO.getById(id);

			String filePath1 = mimpsReport.getFilePath();

			if (filePath1 == null) {
				return response.getAuthResponse("FILE_NOT_FOUND", HttpStatus.BAD_REQUEST, null, version);
			} else {
				String filePath = pdfConverter(filePath1);
				File file = new File(filePath);

				Path path = Paths.get(file.getAbsolutePath());
				System.out.println("file-------------" + Files.probeContentType(path));

				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

				log.info("resource-------------->" + resource);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + filePath.substring(filePath.lastIndexOf("/") + 1));
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)

						.body(resource);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In shareTransferDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	public ResponseEntity<?> beneficiaryDetailsDownloadFile(String version, Long id) {

		try {
			log.info("id------->" + id);
			CafGenerationModel cafFiles = cafGenerationDao.getById(id);

			String filePath = cafFiles.getBeneficiaryDetials();

			if (filePath == null) {
				return response.getAuthResponse("FILE_NOT_FOUND", HttpStatus.BAD_REQUEST, null, version);
			} else {
				File file = new File(filePath);

				Path path = Paths.get(file.getAbsolutePath());
				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

				log.info("resource-------------->" + resource);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + filePath.substring(filePath.lastIndexOf("/") + 1));
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)

						.body(resource);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In shareTransferDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	public ResponseEntity<?> salesPurchasesDownload(String version, Long id) {

		try {

			SalesPurchaseReportModel report = salesPurchaseReportDAO.getById(id);
//			String filePath1 = salesPurchase(month, year);
			String filePath1 = report.getFilePath();

			if (filePath1 == null) {
				return response.getAuthResponse("FILE_NOT_FOUND", HttpStatus.BAD_REQUEST, null, version);
			} else {
				String filePath = pdfConverter(filePath1);
				File file = new File(filePath);

				Path path = Paths.get(file.getAbsolutePath());
				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

				log.info("resource-------------->" + resource);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + filePath.substring(filePath.lastIndexOf("/") + 1));
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");

				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)

						.body(resource);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In shareTransferDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	public String sendApprovalStatus(String version, List<ShareTransferMaster> shareTransferMaster) {
		// TODO Auto-generated method stub
		XWPFDocument doc = null;
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		SimpleDateFormat formatter;
		int count = 0;
		Integer shares = 0;
		formatter = new SimpleDateFormat("MMMM, dd yyyy");
		try {

			Map<String, List<ShareTransferMaster>> map = new HashMap<String, List<ShareTransferMaster>>();
			String strDate = formatter.format(date);
			shareTransferMaster.forEach(x -> {
				map.putIfAbsent(x.getSellerClientId(), new ArrayList<ShareTransferMaster>());
				List<ShareTransferMaster> a = map.get(x.getSellerClientId());
				a.add(x);
				map.put(x.getSellerClientId(), a);
			});
			List<String> batch = new ArrayList<String>();
			List<ApprovalStatusModel> approvalStatusModel = new ArrayList<ApprovalStatusModel>();
			for (Entry<String, List<ShareTransferMaster>> i : map.entrySet()) {
				FileInputStream fis = new FileInputStream(templateFilePath + intimation);
				List<ShareTransferMaster> shareTransfer = i.getValue();
				// FileOutputStream fisout = new FileOutputStream("D://template.docx");
				doc = new XWPFDocument(OPCPackage.open(fis));
				doc = replaceTextFor(doc, "{$DATE}", strDate);
				doc = replaceTextFor(doc, "{$SELLERNAME}", shareTransfer.get(0).getSellerName());
				doc = replaceTextFor(doc, "{$SELLERNAME1}", shareTransfer.get(0).getSellerName());

				for (ShareTransferMaster j : shareTransfer) {
					batch.add(j.getBuyerName());
					count = count + 1;
					approvalStatusModel.add(new ApprovalStatusModel(String.valueOf(count), j.getBuyerName(), j
							.getMetadata().getShareTransferRequestDetails().getShareTransferDetails().getNoOfShares()));
					shares = j.getMetadata().getShareTransferRequestDetails().getShareTransferDetails().getNoOfShares()
							+ shares;

				}

				if (batch.size() > 1) {
					doc = replaceTextFor(doc, "{$BUYERNAME}", "transfrees");
				} else {
					doc = replaceTextFor(doc, "{$BUYERNAME}", "transfree");
				}

			}
			approvalStatusModel.add(new ApprovalStatusModel("", "TOTAL", shares));
//			FileInputStream fis = new FileInputStream(filePath + "Intimation.docx");
//			// FileOutputStream fisout = new FileOutputStream("D://template.docx");
//			doc = new XWPFDocument(OPCPackage.open(fis));
//			doc = replaceTextFor(doc, "{$DATE}", strDate);
//			doc = replaceTextFor(doc, "{$SELLERNAME}", shareTransferMaster.getSellerName());
//			doc = replaceTextFor(doc, "{$SELLERNAME1}", shareTransferMaster.getSellerName());
//			doc = replaceTextFor(doc, "{$BUYERNAME}", shareTransferMaster.getBuyerName());
//			doc = replaceTextFor(doc, "{$MONTHYEAR}", Month.of(month + 1).name() + ", " + String.valueOf(year));
			String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);
			String finalFile = filePath + "Intimation" + instant + ".docx";
			FileOutputStream out = new FileOutputStream(finalFile);

			List<XWPFTable> table = doc.getTables();
			List<String> list = new ArrayList<String>();

			System.out.println("List of batch----------------------" + batch);

			XWPFTable tbl = table.get(0);

			ObjectMapper om = new ObjectMapper();
			Map<String, Object> objMap = om.convertValue(approvalStatusModel.get(0), Map.class);
			Set<String> labelList = objMap.keySet();
			insertRowsInTable(approvalStatusModel, tbl, labelList);

			doc.write(out);
			out.close();
			return finalFile;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<?> transferDetailsReport(String version, String inputDate) {
		try {
			File file = new File(templateFilePath + transferDetails);
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);

			List<TransferDetailsInterface> transferDetials = shareTransferDAO.getTransferDetials(inputDate);
			Map<String, Object[]> data = new TreeMap<String, Object[]>();

			SimpleDateFormat dat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

			for (int i = 0; i < transferDetials.size(); i++) {
				Date mdate = new Date(transferDetials.get(i).getCreatedOn().getTime());
				String dateModified = formatter1.format(dat.parse(mdate.toString()));
				data.put(String.valueOf(i),
						new Object[] { i + 1, dateModified, transferDetials.get(i).getModeoftransfer(),
								transferDetials.get(i).getShares(),
								transferDetials.get(i).getPricePerShare(),
								transferDetials.get(i).getSellerClientId(), transferDetials.get(i).getSellername(),
								transferDetials.get(i).getBuyerClientId(), transferDetials.get(i).getBuyername() });
			}

			// Iterating over data and writing it to sheet
			workbook = fillData(workbook, data, 0, 6);
			Date date = new Date(System.currentTimeMillis());
			String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);
			String finalFile = filePath + "Transfer Details-Format - " + instant + " - write.xlsx";
			FileOutputStream out = new FileOutputStream(new File(finalFile));
			workbook.write(out);

			// Closing file output connections
			out.close();
			TransferDetailsReportDownload transferDetailsReportDownload = new TransferDetailsReportDownload();
			transferDetailsReportDownload.setFilePath(finalFile);

			TransferDetailsReportDownload model = transferDetailsReportDownloadDAO.save(transferDetailsReportDownload);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", model.getId());
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, transferDetials, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public String pdfConverter(String filePathName) {
		try {

//			String f = salesPurchase(3, 2022);
			InputStream docFile = new FileInputStream(filePathName);

			XWPFDocument doc = new XWPFDocument(docFile);

			PdfOptions pdfOptions = PdfOptions.create();

			String finalFileExtension = filePathName.substring(filePathName.lastIndexOf("/") + 1);

			String finalFile = finalFileExtension.substring(0, finalFileExtension.lastIndexOf(".") - 1);

			String path = filePath + finalFile + ".pdf";
			OutputStream out = new FileOutputStream(new File(path));

			PdfConverter.getInstance().convert(doc, out, pdfOptions);

			doc.close();

			out.close();

			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public ResponseEntity<?> pacReport(String version) {

		XWPFDocument doc = null;
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		SimpleDateFormat formatter;
		try {

			formatter = new SimpleDateFormat("MMMM, dd yyyy");

			SimpleDateFormat dat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

			FileInputStream fis = new FileInputStream(templateFilePath + pac);
			// FileOutputStream fisout = new FileOutputStream("D://template.docx");
			doc = new XWPFDocument(OPCPackage.open(fis));
//			doc = replaceTextFor(doc, "{$DATE}", strDate);
//			doc = replaceTextFor(doc, "{$MONTHYEAR}", Month.of(month + 1).name() + ", " + String.valueOf(year));
			String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);
			String finalFile = filePath + "PAC - Format -" + instant + ".docx";
			FileOutputStream out = new FileOutputStream(finalFile);

			List<XWPFTable> table = doc.getTables();
			Integer totalShares = foreignResidentDAO.getTotalShares();
			Map<String, List<PacReportInterface>> map = new HashMap<String, List<PacReportInterface>>();
			List<PacReportInterface> pacReport = pacEntityDAO.getPacReport();
			long groupId = pacReport.get(0).getGroupId();
			List<PacReportInterface> pacEntry = new ArrayList<PacReportInterface>();

			List<PacReportModel> report = new ArrayList<PacReportModel>();

			for (int i = 0; i < pacReport.size(); i++) {

				if (groupId == pacReport.get(i).getGroupId()) {
					pacEntry.add(pacReport.get(i));
					map.put(String.valueOf(groupId), pacEntry);
				} else {
					groupId = pacReport.get(i).getGroupId();
					pacEntry = new ArrayList<PacReportInterface>();
					pacEntry.add(pacReport.get(i));
					map.put(String.valueOf(groupId), pacEntry);
				}
			}
			Set<Entry<String, List<PacReportInterface>>> mapCollection = map.entrySet();

			
			List<String> test = new ArrayList<String>();
			List<String> test1 = new ArrayList<String>();
			int count = 0;
			for (Entry<String, List<PacReportInterface>> entry : mapCollection) {
//				count ++;
				List<PacReportInterface> list = entry.getValue();
				entry.getValue().forEach(x -> {

					String name = x.getName();
					test.add(name);
					int index = entry.getValue().indexOf(x);
//					entry.getValue().remove(index);
					List<String> tryNames = new ArrayList<String>();
					List<String> tryCategories = new ArrayList<String>();
					long otherShares = (long) 0;
					for (int i = 0; i < list.size(); i++) {
						if (i == index) {
							continue;
						} else {
							tryNames.add(list.get(i).getName());
							otherShares = otherShares + list.get(i).getShares();
							tryCategories.add(list.get(i).getCategory());
						}
					}

					String otherCategory = "";
					List<String> distinctCategory = tryCategories.stream().distinct().collect(Collectors.toList());
					if (distinctCategory.size() == 1) {
						otherCategory = distinctCategory.stream().map(n -> n).collect(Collectors.joining("\n"));
					} else {
						otherCategory = tryCategories.stream().map(n -> n).collect(Collectors.joining("\n"));
					}

					BigDecimal bd = new BigDecimal((otherShares * 100.00 / totalShares)).setScale(2,
							RoundingMode.HALF_UP);
					double otherPercentage = bd.doubleValue();
					String otherName = tryNames.stream().map(n -> n).collect(Collectors.joining(","));
					test1.add(otherName);

					report.add(new PacReportModel(index, name, x.getCategory(), x.getShares(), x.getPercentageShare(),
							otherName, otherCategory, otherShares, otherPercentage));
				});

			}
			test.forEach(x -> System.out.println("names-------->" + x));

			XWPFTable tbl = table.get(0);

			ObjectMapper om = new ObjectMapper();
			Map<String, Object> objMap = om.convertValue(report.get(0), Map.class);
			Set<String> labelList = objMap.keySet();
//			labelList.forEach(x->System.out.println("othernames-------->"+x));
//			System.out.println("os-------->" + tbl.getNumberOfRows() + " cells+");
//			tbl.createRow();

			int templateRow = tbl.getNumberOfRows() - 1;
			XWPFTableRow lastRow = tbl.getRows().get(templateRow);
			CTRow ctrow = CTRow.Factory.parse(lastRow.getCtRow().newInputStream());
			CTRow abc = lastRow.getCtRow();

//			for(PacReportModel r:report) {
//				System.out.println("inside reportlist-------------->"+r.getOtherCategory()+"  othername"+r.getOtherName());
//			}

			ObjectMapper om1 = new ObjectMapper();
			tbl.setLeftBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
			tbl.setRightBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
			tbl.setTopBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
			tbl.setBottomBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
			tbl.setInsideHBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
			tbl.setInsideVBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
			for (int k = 0; k < report.size(); k++) {
				XWPFTableRow newRow = new XWPFTableRow(abc, tbl);
				Map<String, Object> objMap1 = om1.convertValue(report.get(k), Map.class);
				if (labelList == null) {
					labelList = objMap.keySet();
				}
				newRow.setHeightRule(TableRowHeightRule.AUTO);

				tbl.addRow(newRow);

				int i = 0;
				List<XWPFTableCell> tableCells = newRow.getTableCells();

				for (String label : labelList) {

//					tableCells.get(count1).getParagraphArray(0).setAlignment(ParagraphAlignment.RIGHT);
//					System.out.println("Inside Report--------------------->" + String.valueOf(objMap1.get(label)));
					String data = String.valueOf(objMap1.get(label));
					tableCells.get(i).setVerticalAlignment(XWPFVertAlign.CENTER);
					tableCells.get(i).getCTTc().setPArray(new CTP[] { CTP.Factory.newInstance() });
					if (label.equals("srNo")) {
						tableCells.get(i).setText(String.valueOf(k + 1));
					} else {

						tableCells.get(i).setText(data);
					}
					i++;
				}
			}
			XWPFTableRow newRow = new XWPFTableRow(abc, tbl);
			tbl.addRow(newRow);
			tbl.removeRow(4);
			tbl.removeRow(3);
//			insertRowsInTable(report, tbl, labelList);

			doc.write(out);
			out.close();

			PacReportDownloadModel pacReportDownloadModel = new PacReportDownloadModel();
			pacReportDownloadModel.setFilePath(finalFile);
			PacReportDownloadModel model = pacReportDownloadDAO.save(pacReportDownloadModel);
			Map<String, Object> responsePac = new HashMap<String, Object>();
			responsePac.put("id", model.getId());
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, responsePac, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> pacReportDownload(String version, Long id) {
		// TODO Auto-generated method stub
		try {

			PacReportDownloadModel report = pacReportDownloadDAO.getById(id);
//			String filePath1 = salesPurchase(month, year);
			String filePath1 = report.getFilePath();

			if (filePath1 == null) {
				return response.getAuthResponse("FILE_NOT_FOUND", HttpStatus.BAD_REQUEST, null, version);
			} else {
				String filePath = pdfConverter(filePath1);
				File file = new File(filePath);

				Path path = Paths.get(file.getAbsolutePath());
				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

				log.info("resource-------------->" + resource);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + filePath.substring(filePath.lastIndexOf("/") + 1));
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");

				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)

						.body(resource);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In pacReportDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	public ResponseEntity<?> transferDetailsReportDownload(String version, Long id) {
		// TODO Auto-generated method stub
		try {
			log.info("id------->" + id);
			TransferDetailsReportDownload report = transferDetailsReportDownloadDAO.getById(id);

			String filePath = report.getFilePath();

			if (filePath == null) {
				return response.getAuthResponse("FILE_NOT_FOUND", HttpStatus.BAD_REQUEST, null, version);
			} else {
				File file = new File(filePath);

				Path path = Paths.get(file.getAbsolutePath());
				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

				log.info("resource-------------->" + resource);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + filePath.substring(filePath.lastIndexOf("/") + 1));
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)

						.body(resource);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In shareTransferDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	public ResponseEntity<?> cagDownload(String version, Long id) {
		// TODO Auto-generated method stub
		try {
			log.info("id------->" + id);
			CagDownloadModel report = cagDownloadDAO.getById(id);
			String filePath = report.getFilePath();

			if (filePath == null) {
				return response.getAuthResponse("FILE_NOT_FOUND", HttpStatus.BAD_REQUEST, null, version);
			} else {
				File file = new File(filePath);

				Path path = Paths.get(file.getAbsolutePath());
				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

				log.info("resource-------------->" + resource);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + filePath.substring(filePath.lastIndexOf("/") + 1));
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)

						.body(resource);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("In cagDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	public ResponseEntity<?> transferMasterExport(String version) {
		try {
			List<TransferMasterExportInterface> exportTransfer = shareTransferDAO.transferMasterExport();
			List<TransferMasterExportModel> exportTransferMaster = new ArrayList<TransferMasterExportModel>();
			List<CafGenerationModel> sendRtaDate = cafGenerationDao.getSendToRta();
			for (TransferMasterExportInterface export : exportTransfer) {
				MovementInterface fr = foreignResidentDAO.getMovementForeignResident(export.getUuid());
				MovementInterface tmp = foreignResidentDAO.getMovementTmToP(export.getUuid());
				String movement = tmp.getCategoryTransfer() + " " + fr.getCategoryTransfer();

				TransferMasterExportModel details = new TransferMasterExportModel();
				details.setUuid(export.getUuid());
				details.setBuyername(export.getBuyername());
				details.setSellername(export.getSellername());
				details.setComments(export.getComments());
				details.setPricePerShare(export.getPricePerShare());
				details.setPercentageShare(export.getPercentageShare());
				details.setMovement(movement);
				details.setDateOfExecution(export.getDateOfExecution());
				details.setStageOneDocs(export.getStageOneDocs());
				details.setStageTwoDocs(export.getStageTwoDocs());
				details.setPrincipalPending(export.getPrincipalPending());
				details.setNoOfShares(export.getnoOfShares());
				if (export.getPac() == null) {
					details.setPac("no");
				} else {
					details.setPac(export.getPac());
				}
				if (export.getPercentageShare() >= 5.00) {
					details.setBelowFive("Yes");
				} else {
					details.setBelowFive("No");
				}
				for (CafGenerationModel rtas : sendRtaDate) {
					List<String> uuid = Arrays.asList(rtas.getUuid().split(","));
					if (uuid.contains(export.getUuid().toString())) {
						details.setSendToRta(rtas.getSendToRta());
					} else {
//						details.setSendToRta(null);
						continue;
					}
				}

				exportTransferMaster.add(details);
			}

			File file = new File(templateFilePath + transfermaster);
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Map<String, Object[]> data = new TreeMap<String, Object[]>();

			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

			SimpleDateFormat dat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

			for (int i = 0; i < exportTransferMaster.size(); i++) {
				Date mdate = new Date(exportTransferMaster.get(i).getStageOneDocs().getTime());
				String stageOne = formatter1.format(dat.parse(mdate.toString()));
				Date mdate4 = new Date(exportTransferMaster.get(i).getDateOfExecution().getTime());
				String executionDate = formatter1.format(dat.parse(mdate4.toString()));

				Date mdate3 = new Date(exportTransferMaster.get(i).getSendToRta().getTime());
				String rtaDate = formatter1.format(dat.parse(mdate3.toString()));
				Date mdate2 = new Date(exportTransferMaster.get(i).getPrincipalPending().getTime());
				String principalPending = formatter1.format(dat.parse(mdate2.toString()));
				Date mdate1 = new Date(exportTransferMaster.get(i).getStageTwoDocs().getTime());
				String stageTwo = formatter1.format(dat.parse(mdate1.toString()));
				data.put(String.valueOf(i),
						new Object[] { exportTransferMaster.get(i).getUuid(),
								exportTransferMaster.get(i).getSellername(), exportTransferMaster.get(i).getBuyername(),
								exportTransferMaster.get(i).getNoOfShares(),
								exportTransferMaster.get(i).getPercentageShare(),
								exportTransferMaster.get(i).getPricePerShare(), stageOne,
								exportTransferMaster.get(i).getComments(), principalPending, stageTwo, rtaDate,
								executionDate, exportTransferMaster.get(i).getMovement(),
								exportTransferMaster.get(i).getPac(), exportTransferMaster.get(i).getBelowFive() });
			}

		
			Date date = new Date(System.currentTimeMillis());
			String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);
			String finalFile = filePath + "Transfer Master-Format - " + instant + " - write.xlsx";
			FileOutputStream out = new FileOutputStream(new File(finalFile));
			workbook = fillData(workbook, data,  0, 1);
			workbook.write(out);
			out.close();

			TransferMasterExportDownloadModel transferMasterExportDownloadModel = new TransferMasterExportDownloadModel();
			transferMasterExportDownloadModel.setFilePath(finalFile);

			TransferMasterExportDownloadModel model = transferMasterExportDownloadDAO
					.save(transferMasterExportDownloadModel);

			Map<String, Long> responseMap = new HashMap<String, Long>();
			responseMap.put("id", model.getId());

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, responseMap, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> shareHoldingPatternTemplate(String version) {
		try {
			List<ShareHoldingPatternTemplateInterface> newEntity = shareTransferDAO.getShareHoldingPatternTemplate();
			for (ShareHoldingPatternTemplateInterface entity : newEntity) {
				ShareHoldingPatternTemplate shareHoldingPatternTemplate = new ShareHoldingPatternTemplate();
				shareHoldingPatternTemplate.setClientId(entity.getClientId());
				shareHoldingPatternTemplate.setBuyername(entity.getBuyername());
				shareHoldingPatternTemplate.setBuyerNRIType(entity.getBuyerNRIType());
				shareHoldingPatternTemplate.setBuyerTypeTCCM(entity.getBuyerTypeTCCM());
				shareHoldingPatternTemplate.setCategoryOfBuyer(entity.getCategoryOfBuyer());
				if (entity.getCategoryOfBuyer().equalsIgnoreCase("individual")) {
					shareHoldingPatternTemplate.setSubCategory("individual");
				} else {
					shareHoldingPatternTemplate.setSubCategory(entity.getSubCategory());
				}

				shareHoldingPatternTemplate.setCorpListed(entity.getCorpListed());
				shareHoldingPatternTemplate.setDateOfExecution(entity.getDateOfExecution());
				shareHoldingPatternTemplate.setNoOfShares(entity.getNoOfShares());
				shareHoldingPatternTemplateDAO.save(shareHoldingPatternTemplate);
			}
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, newEntity, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> getTaxTeamReport1(String version) {
		// TODO Auto-generated method stub
		try {
			List<TaxTeamInterface> taxTeamReport = shareTransferDAO.getTaxTeamReport1();

			File file = new File(templateFilePath + taxTeam);
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Map<String, Object[]> data = new TreeMap<String, Object[]>();

			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

			SimpleDateFormat dat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

			for (int i = 0; i < taxTeamReport.size(); i++) {

				Date mdate = new Date(taxTeamReport.get(i).getDateOfExecution().getTime());
				String executionDate = formatter1.format(dat.parse(mdate.toString()));

				data.put(String.valueOf(i),
						new Object[] { taxTeamReport.get(i).getSellername(), taxTeamReport.get(i).getSellerpan(),
								taxTeamReport.get(i).getSellerresidenttype(), "Equity",
								taxTeamReport.get(i).getNoofshares(), "-", "-", "-", "-", executionDate,
								taxTeamReport.get(i).getModeoftransfer(), taxTeamReport.get(i).getBuyerpan(),
								taxTeamReport.get(i).getBuyername() });
			}

//			Set<String> keyset = data.keySet();
//
//			int rownum = 2;
//
//			XSSFCellStyle style = workbook.createCellStyle();
//			style.setBorderBottom(BorderStyle.THIN);
//			style.setBorderTop(BorderStyle.THIN);
//			style.setBorderRight(BorderStyle.THIN);
//			style.setBorderLeft(BorderStyle.THIN);
//			style.setAlignment(HorizontalAlignment.RIGHT);
//
//			for (String key : keyset) {
//
//				// Creating a new row in the sheet
//				Row row = sheet.createRow(rownum++);
//
//				Object[] objArr = data.get(key);
//
//				int cellnum = 0;
//
//				for (Object obj : objArr) {
//
//					// This line creates a cell in the next
//					// column of that row
//					Cell cell = row.createCell(cellnum++);
//
//					if (obj instanceof String) {
//						cell.setCellValue((String) obj);
//						cell.setCellStyle(style);
//					} else if (obj instanceof Integer) {
//						cell.setCellValue((Integer) obj);
//						cell.setCellStyle(style);
//					}
//				}
//			}
			Date date = new Date(System.currentTimeMillis());
			String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);
			String finalFile = filePath + "Tax Team Report - " + instant + " - write.xlsx";
			FileOutputStream out = new FileOutputStream(new File(finalFile));
			workbook = fillData(workbook, data,0 , 2);
			workbook.write(out);
			out.close();

			TaxTeamReportDownloadModel taxTeamReportDownloadModel = new TaxTeamReportDownloadModel();
			taxTeamReportDownloadModel.setFilePath(finalFile);

			TaxTeamReportDownloadModel model = taxTeamReportDownloadDAO.save(taxTeamReportDownloadModel);

			Map<String, Long> responseMap = new HashMap<String, Long>();
			responseMap.put("id", model.getId());

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, responseMap, version);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

	public ResponseEntity<?> taxTeamDownload(String version, Long id) {
		// TODO Auto-generated method stub
		try {
			log.info("id------->" + id);
			TaxTeamReportDownloadModel report = taxTeamReportDownloadDAO.getById(id);
			String filePath = report.getFilePath();

			if (filePath == null) {
				return response.getAuthResponse("FILE_NOT_FOUND", HttpStatus.BAD_REQUEST, null, version);
			} else {
				File file = new File(filePath);

				Path path = Paths.get(file.getAbsolutePath());
				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

				log.info("resource-------------->" + resource);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + filePath.substring(filePath.lastIndexOf("/") + 1));
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)

						.body(resource);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("In taxTeamDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	public ResponseEntity<?> transferMasterExportDownload(String version, Long id) {
		// TODO Auto-generated method stub
		try {
			log.info("id------->" + id);
			TransferMasterExportDownloadModel report = transferMasterExportDownloadDAO.getById(id);
			String filePath = report.getFilePath();

			if (filePath == null) {
				return response.getAuthResponse("FILE_NOT_FOUND", HttpStatus.BAD_REQUEST, null, version);
			} else {
				File file = new File(filePath);

				Path path = Paths.get(file.getAbsolutePath());
				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

				log.info("resource-------------->" + resource);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + filePath.substring(filePath.lastIndexOf("/") + 1));
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)

						.body(resource);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(
					"In transferMasterExportDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	public ResponseEntity<?> getWeightedAverage(String version, WeightedAverageModel weightedAverageModel) {
		try {
			Integer month = weightedAverageModel.getMonth();
			Integer year = weightedAverageModel.getYear();
			Integer period = weightedAverageModel.getPeriod();
			String startDate;
			String endDate;
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

			SimpleDateFormat dat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

			Calendar c;
			if (period != 1) {
				c = Calendar.getInstance();

				c.set(Calendar.YEAR, year);
				c.set(Calendar.MONTH, month);

				c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));

				startDate = formatter1.format(dat.parse(c.getTime().toString()));

				month = month + period;
				if (month > 11) {
					month = month - 11;
				}
				c.set(Calendar.YEAR, year);
				c.set(Calendar.MONTH, month);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

				endDate = formatter1.format(dat.parse(c.getTime().toString()));

				log.info("month--------------->" + month + " year--------------->" + year);
				log.info("dt1----------------------->" + startDate);
				log.info("dt2----------------------->" + endDate);
			} else {
				c = Calendar.getInstance();

				c.set(Calendar.YEAR, year);
				c.set(Calendar.MONTH, month);

				c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));

				startDate = formatter1.format(dat.parse(c.getTime().toString()));

				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

				endDate = formatter1.format(dat.parse(c.getTime().toString()));

				log.info("month--------------->" + month + " year--------------->" + year);
				log.info("dt1----------------------->" + startDate);
				log.info("dt2----------------------->" + endDate);
			}

			List<AverageWeightedReportInterface> reportData = shareTransferDAO.getWeightedAverage(startDate, endDate);
			List<AverageWeightedReportModel> report = new ArrayList<AverageWeightedReportModel>();
			Map<String, Object[]> data = new HashMap<String, Object[]>();
			if (!reportData.isEmpty()) {
				for (AverageWeightedReportInterface rd : reportData) {
					String pacTable;
					MovementInterface fr = foreignResidentDAO.getMovementForeignResident(rd.getUuid());
					MovementInterface tmp = foreignResidentDAO.getMovementTmToP(rd.getUuid());
					String movement = tmp.getCategoryTransfer() + " " + fr.getCategoryTransfer();

					AverageWeightedReportModel avgReport = new AverageWeightedReportModel();
					avgReport.setUuid(rd.getUuid());
					avgReport.setSellername(rd.getSellername());
					avgReport.setBuyername(rd.getBuyername());
					avgReport.setNoofshares(rd.getNoofshares());

					avgReport.setPricepershare(rd.getPricepershare());
					avgReport.setAmount(rd.getAmount());

					avgReport.setCategoryofbuyer(rd.getCategoryofbuyer());
					avgReport.setDateofapproval(rd.getDateofapproval());
					avgReport.setDateofexecution(rd.getDateofexecution());
					avgReport.setBuyerpac(rd.getBuyerpac());

					if (rd.getBuyerpac().equalsIgnoreCase("yes")) {

					String abc = rd.getPacdetailslist().toString();

					PacDetails pacDetails = new Gson().fromJson(abc, PacDetails.class);

					pacTable = pacDetails.getPacDetailsList().stream().map(x -> x.getName())
							.collect(Collectors.toList()).stream().map(n -> n).collect(Collectors.joining(","));

					avgReport.setPacdetailslist(pacTable);

				} else {
					avgReport.setPacdetailslist("NO");
				}
				avgReport.setMovement(movement);

				report.add(avgReport);

			}

				for (int i = 0; i < report.size(); i++) {

				Date mdate = new Date(report.get(i).getDateofexecution().getTime());
				String executionDate = formatter1.format(dat.parse(mdate.toString()));

				Date mdate1 = new Date(report.get(i).getDateofapproval().getTime());
				String dateofapproval = formatter1.format(dat.parse(mdate1.toString()));

					data.put(String.valueOf(i),
							new Object[] { String.valueOf(i + 1), report.get(i).getSellername(),
									report.get(i).getBuyername(), report.get(i).getNoofshares(),
									report.get(i).getPricepershare(), report.get(i).getAmount(), dateofapproval,
									executionDate, report.get(i).getMovement(), report.get(i).getPacdetailslist() });
				}
			}

			List<AverageWeightedReportInterface> reportData1 = shareTransferDAO
					.getWeightedAverageNonIndividuals(startDate, endDate);
			List<AverageWeightedReportModel> report1 = new ArrayList<AverageWeightedReportModel>();
			Map<String, Object[]> data1 = new HashMap<String, Object[]>();

			if (!reportData1.isEmpty()) {
				for (AverageWeightedReportInterface rd : reportData1) {
					String pacTable;
					MovementInterface fr = foreignResidentDAO.getMovementForeignResident(rd.getUuid());
					MovementInterface tmp = foreignResidentDAO.getMovementTmToP(rd.getUuid());
					String movement = tmp.getCategoryTransfer() + " " + fr.getCategoryTransfer();

					AverageWeightedReportModel avgReport = new AverageWeightedReportModel();
					avgReport.setUuid(rd.getUuid());
					avgReport.setSellername(rd.getSellername());
					avgReport.setBuyername(rd.getBuyername());
					avgReport.setNoofshares(rd.getNoofshares());
					avgReport.setPricepershare(rd.getPricepershare());
					avgReport.setAmount(rd.getAmount());
					avgReport.setCategoryofbuyer(rd.getCategoryofbuyer());
					avgReport.setDateofapproval(rd.getDateofapproval());
					avgReport.setDateofexecution(rd.getDateofexecution());
					avgReport.setBuyerpac(rd.getBuyerpac());

					if (rd.getBuyerpac().equalsIgnoreCase("yes")) {

						String abc = rd.getPacdetailslist().toString();

						PacDetails pacDetails = new Gson().fromJson(abc, PacDetails.class);

						pacTable = pacDetails.getPacDetailsList().stream().map(x -> x.getName())
								.collect(Collectors.toList()).stream().map(n -> n).collect(Collectors.joining(","));

						avgReport.setPacdetailslist(pacTable);

					} else {
						avgReport.setPacdetailslist("NO");
					}
					avgReport.setMovement(movement);

					report1.add(avgReport);
				}

				for (int i = 0; i < report1.size(); i++) {

					Date mdate = new Date(report1.get(i).getDateofexecution().getTime());
					String executionDate = formatter1.format(dat.parse(mdate.toString()));

				Date mdate1 = new Date(report1.get(i).getDateofapproval().getTime());
				String dateofapproval = formatter1.format(dat.parse(mdate1.toString()));

					data1.put(String.valueOf(i),
							new Object[] { String.valueOf(i + 1), report1.get(i).getSellername(),
									report1.get(i).getBuyername(), report1.get(i).getNoofshares(),
									report1.get(i).getPricepershare(), report1.get(i).getAmount(), dateofapproval,
									executionDate, report1.get(i).getMovement(), report1.get(i).getPacdetailslist() });
				}
			}

			if (reportData.isEmpty() && reportData1.isEmpty()) {
				log.info("In getWeightedAverage NO_DATA------------");
				return response.getAuthResponse("NO_DATA", HttpStatus.OK, null, version);
			} else {
				Date date = new Date(System.currentTimeMillis());
				String instant = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(date);
				String finalFile = filePath + "Weighted Average - " + instant + " - write.xlsx";
				File file = new File(templateFilePath + weightedAverage);
				FileInputStream fis = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				
				XSSFFormulaEvaluator formulaEvaluate = workbook.getCreationHelper().createFormulaEvaluator();
				
				XSSFCellStyle style = workbook.createCellStyle();
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderTop(BorderStyle.THIN);
				style.setBorderRight(BorderStyle.THIN);
				style.setBorderLeft(BorderStyle.THIN);
				style.setAlignment(HorizontalAlignment.RIGHT);
				
				if (!reportData.isEmpty()) {
					workbook = fillData(workbook, data, 0, 1);

					XSSFRow summaryRow = workbook.getSheetAt(0).createRow(report.size() + 4);
					XSSFRow summaryRow1 = workbook.getSheetAt(0).createRow(report.size() + 5);
					XSSFRow summaryRow2 = workbook.getSheetAt(0).createRow(report.size() + 6);

					XSSFCell summaryCell = summaryRow.createCell(1);
					summaryCell.setCellValue("Total Consideration");
					summaryCell.setCellStyle(style);
					XSSFCell formulaD = summaryRow.createCell(2);
					formulaD.setCellFormula("SUM(D:D)");
					formulaD.setCellStyle(style);
					XSSFCell summaryCell1 = summaryRow1.createCell(1);
					summaryCell1.setCellValue("Total Quantity");
					summaryCell1.setCellStyle(style);
					XSSFCell formulaF = summaryRow1.createCell(2);
					formulaF.setCellFormula("SUM(F:F)");
					formulaF.setCellStyle(style);

					XSSFCell summaryCell2 = summaryRow2.createCell(1);
					summaryCell2.setCellValue("Volume Weighted Average Price");
					summaryCell2.setCellStyle(style);
					XSSFCell formulaF1 = summaryRow2.createCell(2);
					String addressf = formulaF.getAddress().formatAsString();
					String addressd = formulaD.getAddress().formatAsString();
					formulaF1.setCellFormula(addressf + "/" + addressd);
					formulaF1.setCellStyle(style);

					
					formulaEvaluate.evaluateFormulaCell(formulaD);
					formulaEvaluate.evaluateFormulaCell(formulaF);
					formulaEvaluate.evaluateFormulaCell(formulaF1);
					
					for (int i = 0; i < workbook.getSheetAt(0).getRow(0).getPhysicalNumberOfCells(); i++) {
					workbook.getSheetAt(0).autoSizeColumn(i);
				}
				}

				FileOutputStream out = new FileOutputStream(new File(finalFile));

				// workbook.write(out);
				if (!reportData1.isEmpty()) {

					workbook = fillData(workbook, data1, 1, 1);

					XSSFRow summaryRowSheet2 = workbook.getSheetAt(1).createRow(report1.size() + 4);
					XSSFRow summaryRow1Sheet2 = workbook.getSheetAt(1).createRow(report1.size() + 5);
					XSSFRow summaryRow2Sheet2 = workbook.getSheetAt(1).createRow(report1.size() + 6);

					XSSFCell summaryCellSheet2 = summaryRowSheet2.createCell(1);
					summaryCellSheet2.setCellValue("Total Consideration");
					summaryCellSheet2.setCellStyle(style);
					XSSFCell formulaDSheet2 = summaryRowSheet2.createCell(2);
					formulaDSheet2.setCellFormula("SUM(D:D)");
					formulaDSheet2.setCellStyle(style);
					XSSFCell summaryCell1Sheet2 = summaryRow1Sheet2.createCell(1);
					summaryCell1Sheet2.setCellValue("Total Quantity");
					summaryCell1Sheet2.setCellStyle(style);
					XSSFCell formulaFSheet2 = summaryRow1Sheet2.createCell(2);
					formulaFSheet2.setCellFormula("SUM(F:F)");
					formulaFSheet2.setCellStyle(style);

					XSSFCell summaryCell2Sheet2 = summaryRow2Sheet2.createCell(1);
					summaryCell2Sheet2.setCellValue("Volume Weighted Average Price");
					summaryCell2Sheet2.setCellStyle(style);
					XSSFCell formulaF1Sheet2 = summaryRow2Sheet2.createCell(2);
					String addressfSheet2 = formulaFSheet2.getAddress().formatAsString();
					String addressdSheet2 = formulaDSheet2.getAddress().formatAsString();
					formulaF1Sheet2.setCellFormula(addressfSheet2 + "/" + addressdSheet2);
					formulaF1Sheet2.setCellStyle(style);

					formulaEvaluate.evaluateFormulaCell(formulaDSheet2);
					formulaEvaluate.evaluateFormulaCell(formulaFSheet2);
					formulaEvaluate.evaluateFormulaCell(formulaF1Sheet2);
				}

			for (int i = 0; i < workbook.getSheetAt(1).getRow(0).getPhysicalNumberOfCells(); i++) {
				workbook.getSheetAt(1).autoSizeColumn(i);
			}

				workbook.write(out);
				out.close();

				WeightedAverageDownloadModel weightedAverageDownloadModel = new WeightedAverageDownloadModel();
				weightedAverageDownloadModel.setFilePath(finalFile);

				WeightedAverageDownloadModel model = weightedAverageDownloadDAO.save(weightedAverageDownloadModel);

				Map<String, Long> responseMap = new HashMap<String, Long>();
				responseMap.put("id", model.getId());

				return response.getAuthResponse("SUCCESS", HttpStatus.OK, responseMap, version);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("In getAverageWeighted in Exception INTERNAL_SERVER_ERROR----------------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.BAD_REQUEST, null, version);
		}
	}

	public XSSFWorkbook fillData(XSSFWorkbook workbook, Map<String, Object[]> data, int sheetNo, int rowno)
			throws Exception {
//		File file = new File(templateFilePath + fileName);
//		FileInputStream fis = new FileInputStream(file);
//		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(sheetNo);
		log.info("No. of sheets------------>" + sheet.getSheetName());
		Set<String> keyset = data.keySet();

		int rownum = rowno;

		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.RIGHT);

		for (String key : keyset) {

			// Creating a new row in the sheet
			Row row = sheet.createRow(rownum++);

			Object[] objArr = data.get(key);

			int cellnum = 0;

			for (Object obj : objArr) {

				// This line creates a cell in the next
				// column of that row
				Cell cell = row.createCell(cellnum++);

				if (obj instanceof String) {
					cell.setCellValue((String) obj);
					cell.setCellStyle(style);
				} else if (obj instanceof Integer) {
					cell.setCellValue((Integer) obj);
					cell.setCellStyle(style);
				} else if (obj instanceof Long) {
					cell.setCellValue((Long) obj);
					cell.setCellStyle(style);
				} else if (obj instanceof Double) {
					cell.setCellValue((Double) obj);
					cell.setCellStyle(style);
				}
			}
		}

		for (int i = 0; i < workbook.getSheetAt(sheetNo).getRow(rowno).getPhysicalNumberOfCells(); i++) {
			workbook.getSheetAt(sheetNo).autoSizeColumn(i);
		}
//		FileOutputStream out = new FileOutputStream(new File(finalFile));
//		workbook.write(out);
//		out.close();
		return workbook;
	}

	public ResponseEntity<?> getWeightedAverageDownload(String version, Long id) {
		// TODO Auto-generated method stub
		try {
			log.info("id------->" + id);
			WeightedAverageDownloadModel report = weightedAverageDownloadDAO.getById(id);
			String filePath = report.getFilePath();

			if (filePath == null) {
				return response.getAuthResponse("FILE_NOT_FOUND", HttpStatus.BAD_REQUEST, null, version);
			} else {
				File file = new File(filePath);

				Path path = Paths.get(file.getAbsolutePath());
				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

				log.info("resource-------------->" + resource);
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=" + filePath.substring(filePath.lastIndexOf("/") + 1));
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
				return ResponseEntity.ok().headers(headers).contentLength(file.length())
						.contentType(MediaType.APPLICATION_OCTET_STREAM)

						.body(resource);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info("In weightedAverageDownloadFile in Exception DOWNLOAD_FAIL----------------------------------");
			return response.getAuthResponse("DOWNLOAD_FAIL", HttpStatus.BAD_REQUEST, null, version);
		}
	}
}