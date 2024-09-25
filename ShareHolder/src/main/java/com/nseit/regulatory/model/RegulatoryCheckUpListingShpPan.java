package com.nseit.regulatory.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "REGULATORY_CHECK_VW_SHP_PAN_DATA_FOR_MEMBERSHIP")
@Qualifier("integrationDataSource")
public class RegulatoryCheckUpListingShpPan implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
	@Id
	@Column(name = "SPDFM_PAN_NO")
	private String spdfmPan;
	
	@Column(name = "SPDFM_SHAREHOLDER_NAME")
	private String spdfmName;

	@Column(name = "SPDFM_NDS_ID")
	private int spdfmNdsId;

	@Column(name = "SPDFM_SYMB_ID")
	private int spdfmSymbId;

	@Column(name = "SPDM_REG_DATE")
	private Date spdfmRegDate;

	@Column(name = "SPDFM_REGULATION")
	private String spdfmRegulation;

	@Column(name = "SPDFM_SYMBOL")
	private String spdfmSymbol;

	@Column(name = "SPDFM_CMPY_NAME")
	private String spdfmCmpyName;

	



}
