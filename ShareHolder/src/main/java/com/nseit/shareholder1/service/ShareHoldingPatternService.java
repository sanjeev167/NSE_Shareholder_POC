package com.nseit.shareholder1.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.ant.DirectoryScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nseit.shareholder1.dao.ShareHoldingPatternDao;
import com.nseit.shareholder1.model.ShareHoldingPattern;
import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.ResponseUtil;

@Service
public class ShareHoldingPatternService {

	@Autowired
	ResponseUtil response;

	@Autowired
	ShareHoldingPatternDao shareHoldingPatternDao;

	@Autowired
//	@PersistenceContext
	EntityManagerFactory entityManagerFactory;

	@Value("${pathnew1}")
	private String path;

	@Autowired
	JwtUtil jwt;

	int fileCount = 0;
	int invalidFileCount = 0;
	String fi;
	Logger newLogger = LoggerFactory.getLogger(BenposeDataMasterService.class);

	public ResponseEntity<?> importExcelData(String version, MultipartFile multiPartFile) {

		String originalName = "";
		String extension = "";

		EntityManager entityManager1 = entityManagerFactory.createEntityManager();

		List<ShareHoldingPattern> tableExist = shareHoldingPatternDao.checkValues();
		// TODO Auto-generated method stub

		try {
			ShareHoldingPattern shp = new ShareHoldingPattern();

			String pathMon = createDirectory();
			String name = multiPartFile.getOriginalFilename();
			extension = name.substring(name.lastIndexOf("."));

			String path1 = pathMon + File.separator + name;

			originalName = org.springframework.util.StringUtils.stripFilenameExtension(name);

			// NEED TO APPEND SOMETHING WITH EXISTING FILE NAME
			Boolean existFile = new File(path1).exists();
			if (existFile != null) {

				System.out.println("files----------->" + countSameFiles(originalName, pathMon));
				fileCount = countSameFiles(originalName, pathMon);
				String newPath = pathMon + File.separator + originalName + fileCount + extension;
				File newFile = new File(newPath);
				FileOutputStream newFo = new FileOutputStream(newFile);
				newFo.write(multiPartFile.getBytes());

			}

			File file = new File(path1);

			FileOutputStream fo = new FileOutputStream(file);
			fo.write(multiPartFile.getBytes());

			fo.close();

			FileInputStream file1 = new FileInputStream(file);
			XSSFWorkbook workbook1 = new XSSFWorkbook(file1);

			List<String> headerList = new ArrayList<String>();

			XSSFSheet worksheet = workbook1.getSheetAt(0);
			String subCategoryOne = null;
			String subCategoryTwo = null;
			entityManager1.getTransaction().begin();
			for (int i = 0; i < worksheet.getRow(0).getPhysicalNumberOfCells(); i++) {
				headerList.add(worksheet.getRow(0).getCell(i).getStringCellValue());

			}
			System.out.println("worksheet" + worksheet.getPhysicalNumberOfRows());
			for (int index = 3; index < worksheet.getPhysicalNumberOfRows() - 3; index++) {

				ShareHoldingPattern shp1 = new ShareHoldingPattern();

				XSSFRow row = worksheet.getRow(index);
				CellType var = row.getCell(0).getCellType();
				CellType var1 = row.getCell(1).getCellType();
				CellType var2 = row.getCell(2).getCellType();
				CellType var3 = row.getCell(3).getCellType();
				System.out.println("var------------------------------------ " + var.equals(CellType.NUMERIC));
				if (var.equals(CellType.STRING) && var1.equals(CellType.STRING)) {
					subCategoryOne = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();

					continue;
				} else if (var.equals(CellType.BLANK) && var1.equals(CellType.STRING)) {
					subCategoryTwo = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();

					continue;
				} else if ((var.equals(CellType.BLANK) && var1.equals(CellType.BLANK))) {

					continue;
				} else if ((var.equals(CellType.BLANK) && row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.getStringCellValue().contains("Total"))) {

					continue;
				}

				else if (var3.equals(CellType.STRING)) {

					continue;
				}
				shp1.setSubCategoryOne(subCategoryOne.substring(subCategoryOne.indexOf(".") + 2));
				shp1.setSubCategoryTwo(subCategoryTwo);
				String sRNo = Integer.toString(
						(int) row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
				System.out.println("sRNo-------->" + sRNo);
				String category = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				System.out.println("category" + category);
				String noOfShares = Integer.toString(
						(int) row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
				String percentage = Float.toString(
						(float) row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
				if (sRNo.isBlank() || sRNo.isEmpty()) {
					shp1.setSrNo(0);
				} else {
					shp1.setSrNo(Integer.parseInt(sRNo));
				}

				shp1.setCategory(category);
				if (noOfShares.isBlank() || noOfShares.isEmpty()) {
					shp1.setNoOfShares(0);
				} else {
					shp1.setNoOfShares(Integer.parseInt(noOfShares));
				}
				if (percentage.isBlank() || percentage.isEmpty()) {

					shp1.setPercentage((float) 0.00);
				} else {
//					BigDecimal bd =  BigDecimal.valueOf(Double.valueOf(percentage));
//					bd.setScale(2, RoundingMode.CEILING);
//					double bd=rounding(Double.valueOf(percentage) , 2);
					Float f = Float.valueOf(percentage);

					shp1.setPercentage(Float.valueOf(percentage));
				}

				// shareHoldingPatternDao.save(shp1);
				entityManager1.persist(shp1);

			}
			entityManager1.getTransaction().commit();
			entityManager1.close();
			return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, version);
		} catch (Exception e) {
			e.printStackTrace();

			entityManager1.getTransaction().rollback();
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, null);
		}

	}

	public double rounding(double value, int places) {
		BigDecimal bd = BigDecimal.valueOf(Double.valueOf(value));
		bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public String createDirectory() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String time = dateFormat.format(now);
		// String pathnew = "D:\\Shareholder_Repository_Project\\shareHolderTest";
		String pathnew1 = path;
		String folder = path + File.separator + "benpose";
		File f = new File(folder);
		f.mkdir();

		String invalidFolder = folder + File.separator + "invalid";
		File f1 = new File(invalidFolder);
		f1.mkdir();

		fi = f1.getPath();
		// File pathnew1 = new File(pathnew + "\\" + time);

		File pathnew2 = new File(folder + File.separator + time);
		newLogger.debug("new path" + pathnew2.getPath());
		pathnew2.mkdir();
		String pathnew3 = pathnew2.getPath();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMM");
		String time1 = dateFormat1.format(now);
		// File pathnew3 = new File(pathnew2 + "\\" + time1);
		File pathnew4 = new File(pathnew3 + File.separator + time1);
		newLogger.debug("new path" + pathnew4.getPath());
		pathnew4.mkdir();
		newLogger.error("path of pathnew3-------->" + pathnew4.getPath());
		return pathnew4.getPath();
	}

	public int countSameFiles(String filename, String dirPath) {
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setIncludes(new String[] { filename + "*" });
		scanner.setBasedir(dirPath);
		scanner.setCaseSensitive(false);
		scanner.scan();
		String[] files = scanner.getIncludedFiles();
		return files.length;
	}
}
