package com.nseit.shareholder1.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "BROKER_CLIENT_MAPPING")
public class BrokerClientMapping {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	@ManyToOne(targetEntity = ClientUser.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//	@JoinColumn(name = "CLIENT_ID",referencedColumnName="CLIENT_ID")
//	@Column(name="CLIENT_ID")
//	private String client_Id;
//	@ManyToOne(targetEntity = BrokerMaster.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//	@JoinColumn(name = "BROKER_ID",referencedColumnName="ID")
	@Column(name="BROKER_ID")
//	@ManyToOne
//	@JoinColumn(name="Id")
    private Long brokerId;
//	private String brokerId;
	
//	@Size
	@Column(name = "APPROVED")
	private String approved;
	
//	@OneToOne(targetEntity = AuthorisedLetter.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "ID", referencedColumnName = "AUTHORISED_LETTER")
	@OneToOne
	@JoinColumn(name="AUTHORISED_LETTER")
//	@Column(name = "AUTHORISED_LETTER")
	private AuthorisedLetter authorityLetter;
	
	@Column(name="MODIFIED_ON")
	private Timestamp modifiedOn;
	
	
	@OneToOne
	@JoinColumn(name = "CLIENT_ID")
	private BenposeDataMasterModel benposeDataMasterModel;
	
	
}
