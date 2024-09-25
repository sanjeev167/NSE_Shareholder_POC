package com.nseit.shareholder1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.CategoryMaster;
import com.nseit.shareholder1.model.NseUserModel;

public interface CategoryMasterDao extends JpaRepository<CategoryMaster, Long>{

	@Query(value="select * from CATEGORY_MASTER", nativeQuery=true)
	public List<CategoryMaster> getAllSubCategoryList();
}


