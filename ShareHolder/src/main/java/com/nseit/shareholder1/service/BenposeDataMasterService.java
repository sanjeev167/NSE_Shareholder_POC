package com.nseit.shareholder1.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
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

import com.nseit.shareholder1.dao.BenposeDataMasterBackupDAO;
import com.nseit.shareholder1.dao.BenposeDataMasterDAO;
import com.nseit.shareholder1.dao.BenposeMasterDao;
import com.nseit.shareholder1.dao.TransferMasterDAO;
import com.nseit.shareholder1.model.BenposeDataMasterBackupModel;
import com.nseit.shareholder1.model.BenposeDataMasterModel;
import com.nseit.shareholder1.model.BenposeMaster;
import com.nseit.shareholder1.model.TransferMasterModel;
import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.ResponseUtil;

@Service
// @Transactional

public class BenposeDataMasterService {

	@Autowired
	ResponseUtil response;

	@Autowired
	BenposeDataMasterDAO benposeDataMasterDAO;

	@Autowired
	BenposeMasterDao benposeMasterDao;

	@Autowired
	TransferMasterDAO transferMasterDAO;

	@Autowired
	// @PersistenceContext
	EntityManagerFactory entityManagerFactory;

	@Autowired
	BenposeDataMasterBackupDAO benposeDataMasterBackupDAO;
	@Value("${pathnew}")
	private String path;

	@Autowired
	JwtUtil jwt;

	int fileCount = 0;
	int invalidFileCount = 0;
	String fi;
	Logger newLogger = LoggerFactory.getLogger(BenposeDataMasterService.class);

	// @Transactional
	public ResponseEntity<?> importExcelData(String version, MultipartFile multiPartFile) {
		// TODO Auto-generated method stub
		Long benId = null;
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		String originalName = "";
		String extension = "";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityManager entityManager1 = entityManagerFactory.createEntityManager();
		List<BenposeDataMasterModel> tableExist = benposeDataMasterDAO.checkValues();
		try {

			if (multiPartFile != null) {
				String username = jwt.extractUsername();
				BenposeMaster ben = new BenposeMaster();
				entityManager1.getTransaction().begin();
				String pathMon = createDirectory();
				String name = multiPartFile.getOriginalFilename();
				extension = name.substring(name.lastIndexOf("."));
				/* String path1 = "D://shareHolderTest//" + name; */
				String path1 = pathMon + File.separator + name;
				// log.error("name-------->" + name);
				originalName = org.springframework.util.StringUtils.stripFilenameExtension(name);
				// log.error("orinalName-------->" + originalName);
				// NEED TO APPEND SOMETHING WITH EXISTING FILE NAME
				Boolean existFile = new File(path1).exists();
				if (existFile == true) {
					// DirectoryScanner scanner = new DirectoryScanner();
					// scanner.setIncludes(new String[]{originalName+"*"});
					// scanner.setBasedir(pathMon);
					// scanner.setCaseSensitive(false);
					// scanner.scan();
					// String[] files = scanner.getIncludedFiles();
					System.out.println("files----------->" + countSameFiles(originalName, pathMon));
					fileCount = countSameFiles(originalName, pathMon);
					String newPath = pathMon + File.separator + originalName + fileCount + extension;
					File newFile = new File(newPath);
					FileOutputStream newFo = new FileOutputStream(newFile);
					newFo.write(multiPartFile.getBytes());
					// log.error("fo---->" + newFo);
					ben.setFileName(originalName + fileCount + extension);
					ben.setFilePath(newPath);
					ben.setCreatedby(username);
					// benposeMasterDao.save(ben);
					entityManager1.persist(ben);
					newFo.close();
				} else {

					ben.setFileName(name);
					ben.setFilePath(path1);
					// benposeMasterDao.save(ben);
					ben.setCreatedby(username);
					entityManager1.persist(ben);

				}
				entityManager1.getTransaction().commit();
				benId = ben.getId();
				// entityManager1.close();

				File file = new File(path1);
				// log.error("path--------->" + path1);
				FileOutputStream fo = new FileOutputStream(file);
				fo.write(multiPartFile.getBytes());
				// log.error("fo---->" + fo);
				fo.close();

				fis = new FileInputStream(file);
				// FileInputStream fis = new FileInputStream("D://benpose formatTest.xlsx");
				workbook = new XSSFWorkbook(fis);
				List<String> headerList = new ArrayList<String>();
				newLogger.debug("fis12--------->" + fis + " workbook12--------->" + workbook);
				List<String> headerList1 = Arrays.asList("FOLIO NO./DP ID-CLIENT ID", "SHARE HOLDER NAME", "SHARES",
						"JOINT HOLDER-1", "JOINT HOLDER-2", "JOINT HOLDER-3", "FATHER/HUSBAND NAME", "ADDRESS LINE-1",
						"ADDRESS LINE-2", "ADDRESS LINE-3", "ADDRESS LINE-4", "PINCODE", "EMAIL ID", "PHONE NO.",
						"PANCARD NO.", "Second Holder PAN_No", "Third Holder PAN_No", "CATEGORY", "STATUS",
						"OCCUPATION", "BANK A/C NO.", "BANK NAME", "BANK ADDRESS LINE-1", "BANK ADDRESS LINE-2",
						"BANK ADDRESS LINE-3", "BANK ADDRESS LINE-4", "BANK PINCODE", "BANK A/C TYPE", "MICR CODE",
						"IFSC", "NOM_NAME", "GAUARIDAN_NM", "RowNumber");

				newLogger.debug("sheetName-------->" + workbook.getSheetName(0));
				XSSFSheet worksheet = workbook.getSheetAt(0);

				for (int i = 0; i < worksheet.getRow(0).getPhysicalNumberOfCells(); i++) {
					headerList.add(worksheet.getRow(0).getCell(i).getStringCellValue());
				}

				//// Above is file code

				// entityManager.getTransaction().begin();

				if (worksheet.getRow(0).getPhysicalNumberOfCells() == 33 && headerList.equals(headerList1)) {
					entityManager.getTransaction().begin();
					if (tableExist.size() > 0) {

						// benposeDataMasterDAO.deleteValues();
						// benposeDataMasterDAO.deleteAllInBatch();

						// entityManager.createQuery("delete from
						// BenposeDataMasterModel").executeUpdate();
						for(BenposeDataMasterModel tab:tableExist) {
							BenposeDataMasterBackupModel backup = new BenposeDataMasterBackupModel();
							BeanUtils.copyProperties(backup, tab);
							entityManager.persist(backup);
						}
						entityManager.createNativeQuery("delete from BENPOSE_DATA_MASTER").executeUpdate();
						// entityManager.getTransaction()
						// for(BenposeDataMasterModel tbl:tableExist) {
						// entityManager.remove(tbl);
						// }
						//
						newLogger.debug("entityManager------------->" + entityManager);
						entityManager.flush();
						// entityManager.clear();
						// System.out.println("entityManager.contains----------------------->"+entityManager.contains(tableExist.get(0)));
						// List<BenposeDataMasterModel> tableExist1 =
						// benposeDataMasterDAO.checkValues();
						// int tableExist1 = entityManager.createNativeQuery("select * from
						// BENPOSE_DATA_MASTER")
						// .getMaxResults();
						// System.out.println("tableExist1---------------->" + tableExist1);
						// benposeDataMasterDAO.deleteAll();
						// excelImportDAO.save();
					}
					for (int index = 1; index < worksheet.getPhysicalNumberOfRows() - 1; index++) {
						// if (index > 0) {
						XSSFRow row = worksheet.getRow(index);
						if (row != null) {
							BenposeDataMasterModel excelImportModel1 = new BenposeDataMasterModel();
							// @Pattern(regexp = "^(IN|\\d{2})(\\d{6})-(\\d{8})$") // "^(\\d{10})$"
							String clientId = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
									.getStringCellValue();
							String bankPincode = Integer.toString((int) row
									.getCell(26, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
							String pinCode = Integer.toString((int) row
									.getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
							String phone = String.valueOf((long) row
									.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
							if (clientId.isEmpty() || clientId.isBlank()) {
								continue;
							} else if (!clientId.matches("^(IN|\\d{2})(\\d{6})-(\\d{8})$")) {
								newLogger.debug("Invalid ClientId---------------->" + clientId);
								continue;
							} else if (!bankPincode.matches("^(\\d{6})$")) {

								newLogger.debug("Invalid bankPincode ClientId---------------->" + clientId);
								continue;
							} else if (!pinCode.matches("^(\\d{6})$")) {

								newLogger.debug("Invalid Pincode ClientId---------------->" + clientId);
								continue;
							} else if (!phone.matches("^(\\d{10})$")) {
								newLogger.debug("Invalid phone ClientId---------------->" + phone);
								newLogger.debug("Invalid phone ClientId---------------->" + clientId);
								continue;
							}

							else {
								excelImportModel1.setClientId(clientId);
								excelImportModel1.setShareholdername(
										row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setShares((long) row
										.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
								excelImportModel1.setJointHolder1(
										row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setJointHolder2(
										row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setJointHolder3(
										row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setFatherName(
										row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setAddressLine1(
										row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setAddressLine2(
										row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setAddressLine3(
										row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setAddressLine4(
										row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setPincode(pinCode);

								excelImportModel1.setEmail(
										row.getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setPhone(phone);

								excelImportModel1.setPan(
										row.getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setSecondHolderPan(
										row.getCell(15, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setThirdHolderPan(
										row.getCell(16, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setCategory(
										row.getCell(17, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue()); //
								excelImportModel1.setStatus(
										row.getCell(18, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue()); //
								excelImportModel1.setOccupation(
										row.getCell(19, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue()); //
								excelImportModel1.setAccountNum(String.valueOf((long) row
										.getCell(20, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
										.getNumericCellValue()));
								excelImportModel1.setBankName(
										row.getCell(21, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setBankAddressLine1(
										row.getCell(22, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setBankAddressLine2(
										row.getCell(23, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setBankAddressLine3(
										row.getCell(24, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setBankAddressLine4(
										row.getCell(25, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setBankPincode(bankPincode);

								excelImportModel1.setBankAccType(
										row.getCell(27, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue());
								excelImportModel1.setMicrCode(
										row.getCell(28, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue()); //
								excelImportModel1.setIfsc(
										row.getCell(29, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue()); //
								excelImportModel1.setNomName(
										row.getCell(30, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue()); //
								excelImportModel1.setGauaridanName(
										row.getCell(31, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
												.getStringCellValue()); //
								excelImportModel1.setRowNumber((long) row
										.getCell(32, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());

								// benposeDataMasterDAO.save(excelImportModel1);
								// entityManager.
								entityManager.persist(excelImportModel1);
								int tableExist12 = entityManager.createNativeQuery("select * from BENPOSE_DATA_MASTER")
										.getMaxResults();
								newLogger.debug("tableExist122222222---------------->" + tableExist12);
								// entityManager.getTransaction().commit();
								// List<BenposeDataMasterModel> table1 = benposeDataMasterDAO.checkValues();
								// newLogger.debug("table1----------->"+table1.size());
							}
							// }
						}
					}

					if (workbook != null) {
						workbook.close();
					}
					if (fis != null) {
						fis.close();
					}
					entityManager.getTransaction().commit();
					// entityManager.flush();
					// entityManager.clear();
					entityManager.close();

					return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, version);
				}

				// return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, version);
				else {
					// throw new Exception("INCORRECT_FILE_SELECTED"); //
					if (workbook != null) {
						workbook.close();
					}
					if (fis != null) {
						fis.close();
					}
					entityManager1.getTransaction().begin();
					Boolean existFile1 = new File(path1).exists();
					if (existFile1 == true) {
						invalidFileCount = countSameFiles(originalName, fi);
						String newPath = fi + File.separator + originalName + invalidFileCount + extension;
						moveFile(path1, newPath);
						// entityManager.createNativeQuery("update benpose_master set file_path
						// ='"+newPath+"' where id="+benId);
						ben.setFilePath(newPath);
						entityManager1.persist(ben);
					} else {

						String invalidFiles = fi + File.separator + name;
						moveFile(path1, invalidFiles);
						// entityManager.createNativeQuery("update benpose_master set file_path
						// ='"+invalidFiles+"' where id="+benId);
						ben.setFilePath(invalidFiles);
						entityManager1.persist(ben);
					}
					entityManager1.getTransaction().commit();
					newLogger.error("In importExcelData INCORRECT_FILE_SELECTED------------------");
					return response.getAuthResponse("INCORRECT_FILE_SELECTED", HttpStatus.OK, null, version);
				}

			} else {
				newLogger.error("In importExcelData FILE_NOT_SELECTED------------------");
				return response.getAuthResponse("FILE_NOT_SELECTED", HttpStatus.OK, null, version);
			}

		} catch (Exception e) {

			try {
				if (fis != null) {
					fis.close();
				}
				if (workbook != null) {
					workbook.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				newLogger.error("BenposeDataMasterService | WorkbookClose | Exception : ", e);

				e1.printStackTrace();
			}

			BenposeMaster path1 = benposeMasterDao.getById(benId);
			String fileName = path1.getFileName();
			String invalidFiles = fi + File.separator + fileName;

			Boolean existFile1 = new File(invalidFiles).exists();
			if (existFile1 == true) {
				invalidFileCount = countSameFiles(originalName, fi);
				String newPath = fi + File.separator + originalName + invalidFileCount + extension;
				moveFile(path1.getFilePath(), newPath);
				path1.setFilePath(invalidFiles);
				benposeMasterDao.save(path1);
			} else {
				moveFile(path1.getFilePath(), invalidFiles);
				path1.setFilePath(invalidFiles);
				benposeMasterDao.save(path1);
			}
			newLogger.error("BenposeDataMasterService | Exception : ", e);
			entityManager.getTransaction().rollback();
			if (e instanceof IllegalStateException) {
				newLogger.error("In importExcelData IllegalStateException--------------");
				return response.getAuthResponse("INVALID_FILE", HttpStatus.OK, null, version);
			}
			newLogger.error("In importExcelData INTERNAL_SERVER_ERROR--------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}

	}

	public String createDirectory() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String time = dateFormat.format(now);
		// String pathnew = "D:\\Shareholder_Repository_Project\\shareHolderTest";
		String pathnew = path;
		String folder = path + File.separator + "benpose";
		File f = new File(folder);
		f.mkdir();

		String invalidFolder = folder + File.separator + "invalid";
		File f1 = new File(invalidFolder);
		f1.mkdir();

		fi = f1.getPath();
		// File pathnew1 = new File(pathnew + "\\" + time);

		File pathnew1 = new File(folder + File.separator + time);
		newLogger.debug("new path" + pathnew1.getPath());
		pathnew1.mkdir();
		String pathnew2 = pathnew1.getPath();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMM");
		String time1 = dateFormat1.format(now);
		// File pathnew3 = new File(pathnew2 + "\\" + time1);
		File pathnew3 = new File(pathnew2 + File.separator + time1);
		newLogger.debug("new path" + pathnew3.getPath());
		pathnew3.mkdir();
		newLogger.error("path of pathnew3-------->" + pathnew3.getPath());
		return pathnew3.getPath();
	}

	public void moveFile(String source, String dest) {
		try {
			Path temp = Files.move(Paths.get(source), Paths.get(dest));
			if (temp != null) {
				newLogger.error("file moved--------------->");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			newLogger.debug("file exception------------------->");
			e.printStackTrace();
		}
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

	public ResponseEntity<?> transferMasterExcel(String version, MultipartFile multiPartFile) {
		// TODO Auto-generated method stub
		Long benId = null;
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		String originalName = "";
		String extension = "";
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityManager entityManager1 = entityManagerFactory.createEntityManager();
		List<TransferMasterModel> tableExist = transferMasterDAO.checkValues();
		try {

			if (multiPartFile != null) {
				BenposeMaster ben = new BenposeMaster();
				entityManager1.getTransaction().begin();
				String pathMon = createDirectory();
				String name = multiPartFile.getOriginalFilename();
				extension = name.substring(name.lastIndexOf("."));
				/* String path1 = "D://shareHolderTest//" + name; */
				String path1 = pathMon + File.separator + name;
				// log.error("name-------->" + name);
				originalName = org.springframework.util.StringUtils.stripFilenameExtension(name);
				// log.error("orinalName-------->" + originalName);
				// NEED TO APPEND SOMETHING WITH EXISTING FILE NAME
				Boolean existFile = new File(path1).exists();
				if (existFile == true) {

					System.out.println("files----------->" + countSameFiles(originalName, pathMon));
					fileCount = countSameFiles(originalName, pathMon);
					String newPath = pathMon + File.separator + originalName + fileCount + extension;
					File newFile = new File(newPath);
					FileOutputStream newFo = new FileOutputStream(newFile);
					newFo.write(multiPartFile.getBytes());
					// log.error("fo---->" + newFo);
					// ben.setFileName(originalName + fileCount + extension);
					// ben.setFilePath(newPath);
					//// benposeMasterDao.save(ben);
					// entityManager1.persist(ben);
					newFo.close();
				} else {

					// ben.setFileName(name);
					// ben.setFilePath(path1);
					//// benposeMasterDao.save(ben);
					// entityManager1.persist(ben);

				}
				entityManager1.getTransaction().commit();
				// benId = ben.getId();
				// entityManager1.close();

				File file = new File(path1);
				// log.error("path--------->" + path1);
				FileOutputStream fo = new FileOutputStream(file);
				fo.write(multiPartFile.getBytes());
				// log.error("fo---->" + fo);
				fo.close();

				fis = new FileInputStream(file);
				// fis = new FileInputStream("D://Sample docs//Transfer masterfile.xlsx");
				workbook = new XSSFWorkbook(fis);
				List<String> headerList = new ArrayList<String>();
				System.out.println("fis12--------->" + fis + " workbook12--------->" + workbook);
				List<String> headerList1 = Arrays.asList("UIN", "Name of the Transferor", "Name of the Transferee",
						"No.of equity shares", "%", "Price per eq. share", "Date of receipt of application",
						"Status of queries raised if any", "Date of in principle approval",
						"Date of receipt of stage II docs", "Date of sending CA to RTA",
						"Date of credit into the demat account of transferee", "Remarks", "Persons Acting in Concert",
						"> 5%");//////////

				System.out.println("sheetName-------->" + workbook.getSheetName(0));
				XSSFSheet worksheet = workbook.getSheetAt(0);
				System.out.println("worksheet.getRow(0).getPhysicalNumberOfCells()----------->"
						+ worksheet.getRow(0).getPhysicalNumberOfCells());

				//
				for (int i = 0; i < worksheet.getRow(0).getPhysicalNumberOfCells(); i++) {
					headerList.add(worksheet.getRow(0).getCell(i).getStringCellValue());
				}

				if (worksheet.getRow(0).getPhysicalNumberOfCells() == 15) {
					entityManager.getTransaction().begin();
					if (tableExist.size() > 0) {

						entityManager.createNativeQuery("delete from TRANSFER_MASTER").executeUpdate();

						System.out.println("entityManager------------->" + entityManager);
						entityManager.flush();

						// int tableExist1 = entityManager.createNativeQuery("select * from
						// TRANSFER_MASTER")
						// .getMaxResults();
						// System.out.println("tableExist1---------------->" + tableExist1);

					}
					for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {

						XSSFRow row = worksheet.getRow(index);

						TransferMasterModel excelImportModel1 = new TransferMasterModel();

						SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
						Date dateReceiptApplication;
						String dra = row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
						if (dra.isEmpty() || dra.isBlank()) {
							dateReceiptApplication = null;
						} else {
							dateReceiptApplication = formatter.parse(dra);
						}
						Date dateOfPrincipleApproval;
						String dOPA = row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
						if (dOPA.isBlank() || dOPA.isEmpty()) {
							dateOfPrincipleApproval = null;
						} else {
							dateOfPrincipleApproval = formatter.parse(dOPA);
						}

						Date dateReceiptOfStage2;
						String drs = row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
						if (drs.isEmpty() || drs.isBlank()) {
							dateReceiptOfStage2 = null;
						} else {
							dateReceiptOfStage2 = formatter.parse(drs);
						}
						Date dateSendingCaToRta;
						String dsca = row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
						if (dsca.isEmpty() || dsca.isBlank()) {
							dateSendingCaToRta = null;
						} else {
							dateSendingCaToRta = formatter.parse(dsca);
						}

						Date dateCreditDemat;
						String dcd = row.getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
						if (dcd.isBlank() || dcd.isEmpty()) {
							dateCreditDemat = null;
						} else {
							dateCreditDemat = formatter.parse(dcd);
						}

						excelImportModel1.setUuid(
								(int) row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
						excelImportModel1.setNameOfTransferor(
								row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
						excelImportModel1.setNameOfTransferee(
								row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
						excelImportModel1.setNoOfShares(
								(int) row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
						excelImportModel1.setPerc((float) row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
								.getNumericCellValue());
						excelImportModel1.setPricePerShare((float) row
								.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
						excelImportModel1.setDateReceiptApplication(dateReceiptApplication);
						excelImportModel1.setStatusQueriesRaised(
								row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
						excelImportModel1.setDateOfPrincipleApproval(dateOfPrincipleApproval);
						excelImportModel1.setDateReceiptOfStage2(dateReceiptOfStage2);
						excelImportModel1.setDateSendingCaToRta(dateSendingCaToRta);
						excelImportModel1.setDateCreditDemat(dateCreditDemat);
						excelImportModel1.setRemarks(
								row.getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
						excelImportModel1.setPac(
								row.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue());
						excelImportModel1.setLessFivePerc((float) row
								.getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());

						entityManager.persist(excelImportModel1);

					}

					if (workbook != null) {
						workbook.close();
					}
					if (fis != null) {
						fis.close();
					}
					entityManager.getTransaction().commit();

					entityManager.close();
					file.delete();

					return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, version);
				}

				// return response.getAuthResponse("SUCCESS", HttpStatus.OK, null, version);
				else {
					// throw new Exception("INCORRECT_FILE_SELECTED"); //
					if (workbook != null) {
						workbook.close();
					}
					if (fis != null) {
						fis.close();
					}
					entityManager1.getTransaction().begin();
					Boolean existFile1 = new File(path1).exists();
					if (existFile1 == true) {
						invalidFileCount = countSameFiles(originalName, fi);
						String newPath = fi + File.separator + originalName + invalidFileCount + extension;
						moveFile(path1, newPath);
						// entityManager.createNativeQuery("update benpose_master set file_path
						// ='"+newPath+"' where id="+benId);
						// ben.setFilePath(newPath);
						// entityManager1.persist(ben);
					} else {

						String invalidFiles = fi + File.separator + name;
						moveFile(path1, invalidFiles);
						// entityManager.createNativeQuery("update benpose_master set file_path
						// ='"+invalidFiles+"' where id="+benId);
						// ben.setFilePath(invalidFiles);
						// entityManager1.persist(ben);
					}
					entityManager1.getTransaction().commit();
					return response.getAuthResponse("INCORRECT_FILE_SELECTED", HttpStatus.OK, null, version);
				}

			} else {
				return response.getAuthResponse("FILE_NOT_SELECTED", HttpStatus.OK, null, version);
			}

		} catch (Exception e) {

			try {
				if (fis != null) {
					fis.close();
				}
				if (workbook != null) {
					workbook.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("workbook exception---------------------->");
				e1.printStackTrace();
			}

			BenposeMaster path1 = benposeMasterDao.getById(benId);
			String fileName = path1.getFileName();
			String invalidFiles = fi + File.separator + fileName;

			Boolean existFile1 = new File(invalidFiles).exists();
			if (existFile1 == true) {
				invalidFileCount = countSameFiles(originalName, fi);
				String newPath = fi + File.separator + originalName + invalidFileCount + extension;
				moveFile(path1.getFilePath(), newPath);
				// path1.setFilePath(invalidFiles);
				// benposeMasterDao.save(path1);
			} else {
				moveFile(path1.getFilePath(), invalidFiles);
				// path1.setFilePath(invalidFiles);
				// benposeMasterDao.save(path1);
			}

			newLogger.error("Error ", e);
			newLogger.error("Error ", e.getMessage());
			entityManager.getTransaction().rollback();
			if (e instanceof IllegalStateException) {
				return response.getAuthResponse("INVALID_FILE", HttpStatus.OK, null, version);
			}

			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
}
