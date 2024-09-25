package com.nseit.shareholder1.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nseit.shareholder1.model.CafGenerationModel;

public interface CafGenerationDao extends JpaRepository<CafGenerationModel, Long>{

	@Query(value = "select * from CAF_GENERATION_MASTER where id =:id", nativeQuery = true)
	public CafGenerationModel getCaf(Long id);

	@Query(value = "select * from CAF_GENERATION_MASTER where SEND_TO_RTA is not null", nativeQuery = true)
	public List<CafGenerationModel> getSendToRta();
}
