package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "TRANSFER_MASTER")
public class TransferMasterModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name =  "UUID")
	private Integer uuid;
	
	@Column(name = "NAME_OF_TRANSFEROR")
	private String nameOfTransferor;
	
	@Column(name = "NAME_OF_TRANSFEREE")
	private String nameOfTransferee;
	
	@Column(name = "NO_OF_SHARES")
	private Integer noOfShares;
	
	@Column(name = "PRICE_PER_SHARE")
	private Float pricePerShare;
	
	@Column(name = "PERCENTAGE")
	private Float perc;
	
	@Column(name = "DATE_RECEIPT_APPLICATION")
	private Date dateReceiptApplication;
	
	@Column(name = "STATUS_QUERIES_RAISED")
	private String statusQueriesRaised;
	
	@Column(name = "DATE_OF_PRINCIPLE_APPROVAL")
	private Date dateOfPrincipleApproval;
	
	@Column(name = "DATE_RECEIPT_OF_STAGE2")
	private Date dateReceiptOfStage2;
	
	@Column(name = "DATE_SENDING_CA_TO_RTA")
	private Date dateSendingCaToRta;
	
	@Column(name = "DATE_CREDIT_INTO_DEMAT")
	private Date dateCreditDemat;
	
    @Column(name = "REMARKS")
	private String remarks;
    
    @Column(name="PERSONS_ACTING_CONCERT")
    private String pac;
    @Column(name="LESS_5_PERC")
    private Float lessFivePerc;

}
