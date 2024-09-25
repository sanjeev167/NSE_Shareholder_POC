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
@Table(name = "REGULATORY_MATCHES_HHID")
@Qualifier("integrationDataSource")
public class RegulatoryCheckUpHhid implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
	
	@Id
	@Column(name = "CIS_ID")
	private Long cisId;

	@Column(name = "HH_ID")
	private Long hhId;

	@Column(name = "CCD_PANNO")
	private String ccdPan;

	@Column(name = "CCD_MEM_CD")
	private Long ccdMemCd;

	@Column(name = "CCD_CD")
	private String ccdCd;

	@Column(name = "CCD_SEG_IND")
	private char ccdSegInd;

	@Column(name = "CCD_NAME")
	private String ccdName;

	@Column(name = "CCD_CATEGORY")
	private Long ccdCategory;
    
	@Column(name = "CCD_ADD_ORIG")
	private String ccdAddOrig; 
	
	@Column(name = "CCD_ADD")
	private String ccdAdd;

	@Column(name = "CCD_EMAIL")
	private String ccdEmail;

	@Column(name = "CCD_DOB")
	private String ccdDob;

	@Column(name = "CCD_TEL_NO")
	private String ccdTelNom;

	@Column(name = "CCD_MOBILE")
	private Long ccdMobile;

	
}
