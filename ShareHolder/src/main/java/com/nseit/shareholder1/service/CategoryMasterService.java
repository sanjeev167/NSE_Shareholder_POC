package com.nseit.shareholder1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nseit.shareholder1.dao.CategoryMasterDao;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class CategoryMasterService {

	@Autowired
	CategoryMasterDao categoryMasterDao;

	@Autowired
	ResponseUtil response;

	public ResponseEntity<?> getAllSubCategoryList(String version) {
		try {
			List subCategoryList = categoryMasterDao.getAllSubCategoryList();

			return response.getAuthResponse("SUCCESS", HttpStatus.OK, subCategoryList, version);
		} catch (Exception e) {
			log.error("In getAllSubCategoryList INTERNAL_SERVER_ERROR-----------------------------");
			return response.getAuthResponse("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null, version);
		}
	}

}
