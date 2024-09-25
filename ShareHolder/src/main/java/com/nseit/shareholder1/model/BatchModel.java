package com.nseit.shareholder1.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "BATCH_MASTER")
public class BatchModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="BATCH")
	private String batch;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="CREATED_ON", insertable = false, updatable = false)
	private Timestamp createdOn;
	
	@Column(name="APPROVED_BY")
	private String approvedBy;
	
	@Column(name = "APPROVED")
	private String approved;
	
	@Column(name="APPROVED_ON")
	private Timestamp approvedOn;
	
	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "COMMENTS")
	private String comments;
}
