package com.nseit.regulatory.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "MEMBERSHIP")
@Qualifier("integrationDataSource")
public class RegulatoryCheckUpMember implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
	@Id
	@Column(name = "PAN_NO")
	private String panNo;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "DOMAIN")
	private String domain;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "TM_NAME")
	private String tmName;

	@Column(name = "PERC_SHAREHOLDING")
	private Double percShareHolding;

	
	@Column(name = "APPLICATION_NAME")
	private String applicantName;

	

	@Column(name = "TM_STATUS")
	private String tmStatus;

	@Column(name = "MEM_ID")
	private int memId;

	@Column(name = "DETAILS")
	private String details;

}
