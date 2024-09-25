package com.nseit.shareholder1.service;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nseit.shareholder1.dao.BenposeMasterDao;
import com.nseit.shareholder1.model.AuthorisedLetter;
import com.nseit.shareholder1.model.BenposeMaster;
import com.nseit.shareholder1.util.JwtUtil;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BenposeMasterService {
	@Value("${pathnew}")
	private String path;

	@Autowired
	private BenposeMasterDao benposeMasterDao;

	@Autowired
	ResponseUtil response;
    
	@Autowired
	JwtUtil jwt;
	
	int fileCount = 0;

	public ResponseEntity<?> uploadBenposeFile(MultipartFile multiPartFile, String version) {
		try {
			if (multiPartFile != null) {
				String name = multiPartFile.getOriginalFilename();
				String extension = name.substring(name.lastIndexOf("."));
				/* String path1 = "D://shareHolderTest//" + name; */
				String path1 = path + name;
				log.error("name-------->" + name);
				String originalName = org.springframework.util.StringUtils.stripFilenameExtension(name);
				log.error("orinalName-------->" + originalName);
				// NEED TO APPEND SOMETHING WITH EXISTING FILE NAME
				Boolean existFile = new File(path1).exists();
				if (existFile == true) {
					fileCount++;
					String newPath = path + originalName + fileCount + extension;
					File newFile = new File(newPath);
					FileOutputStream newFo = new FileOutputStream(newFile);
					newFo.write(multiPartFile.getBytes());
					log.error("fo---->" + newFo);
					BenposeMaster benposeMaster = new BenposeMaster();
					benposeMaster.setFileName(originalName + fileCount + extension);
					benposeMaster.setFilePath(newPath);
					benposeMaster.setCreatedby(jwt.extractUsername());
					benposeMasterDao.save(benposeMaster);
					return response.getAuthResponse("SUCCESS", HttpStatus.OK, benposeMaster, version);
				}
				File file = new File(path1);
				log.error("path--------->" + path1);
				FileOutputStream fo = new FileOutputStream(file);
				fo.write(multiPartFile.getBytes());
				log.error("fo---->" + fo);
				BenposeMaster benposeMaster1 = new BenposeMaster();
				benposeMaster1.setFileName(name);
				benposeMaster1.setFilePath(path1);
				benposeMaster1.setCreatedby(jwt.extractUsername());
				benposeMasterDao.save(benposeMaster1);
				return response.getAuthResponse("SUCCESS", HttpStatus.OK, benposeMaster1, version);
			} else {
                log.error("In uploadBenposeFile FILE_NOT_SELECTED--------------------------");
				return response.getAuthResponse("FILE_NOT_SELECTED", HttpStatus.BAD_REQUEST, null, version);
			}
		} catch (Exception e) {
			log.error("In uploadBenposeFile INTERNAL_SERVER_ERROR--------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}
}
