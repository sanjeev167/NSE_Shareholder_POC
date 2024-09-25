package com.nseit.shareholder1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nseit.shareholder1.model.WeightedAverageDownloadModel;

public interface WeightedAverageDownloadDAO extends JpaRepository<WeightedAverageDownloadModel, Long>{

}
