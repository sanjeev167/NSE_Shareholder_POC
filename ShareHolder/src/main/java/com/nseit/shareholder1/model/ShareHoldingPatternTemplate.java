package com.nseit.shareholder1.model;

import java.io.Serializable;
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
@Table(name = "SHARE_HOLDING_PATTERN_TEMPLATE")
public class ShareHoldingPatternTemplate implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "BUYERNAME")
	private String buyername;
	@Column(name = "NOOFSHARES")
	private Long noOfShares;
	@Column(name = "CATEGORYOFBUYER")
	private String categoryOfBuyer;
	@Column(name = "SUBCATEGORY")
	private String subCategory;
	@Column(name = "CORPLISTED")
	private String corpListed;
	@Column(name = "BUYERTMCM")
	private String buyerTypeTCCM;
	@Column(name = "BUYERNRITYPE")
	private String buyerNRIType;
	@Column(name = "BUYERDPCLIENTID")
	private String clientId;
	@Column(name = "DATEOFEXECUTION")
	private Timestamp dateOfExecution;

}
