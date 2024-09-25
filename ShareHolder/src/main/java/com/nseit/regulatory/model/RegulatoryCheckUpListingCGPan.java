package com.nseit.regulatory.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "REGULATORY_CHECK_VW_CG_PAN_DATA_FOR_MEMBERSHIP")
@Qualifier("integrationDataSource")
public class RegulatoryCheckUpListingCGPan implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
	@Id
	@Column(name = "CPDFM_PAN")
	private String cpdfmPan;
	
	@Column(name = "CPDFM_NAME")
	private String cpdfmName;

	@Column(name = "CPDFM_APP_ID")
	private int cpdfmAppId;

	@Column(name = "CPDFM_SYMB_ID")
	private int cpdfmSymbId;

	@Column(name = "CPDFM_QT_START_DT")
	private Date cpdfmStartDate;

	@Column(name = "CPDFM_QT_END_DT")
	private Date cpdfmEndDate;

	@Column(name = "CPDFM_SYMBOL")
	private String cpdfmSymbol;

	@Column(name = "CPDFM_CMPY_NAME")
	private String cpdfmCmpyName;

	
}

