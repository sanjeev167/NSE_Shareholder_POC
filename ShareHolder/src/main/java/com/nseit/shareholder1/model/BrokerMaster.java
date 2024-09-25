package com.nseit.shareholder1.model;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.nseit.shareholder1.modelInterfaces.BrokerApproval;
import com.nseit.shareholder1.modelInterfaces.BrokerOtpVerify;
import com.nseit.shareholder1.modelInterfaces.BrokerVerify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "BROKER_MASTER")
public class BrokerMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@Id
	@NotEmpty(message = "Broker username may not be Null", groups = {BrokerApproval.class,BrokerOtpVerify.class,BrokerVerify.class})
	@Column(name = "USERNAME")
	private String userName;
	
	@NotEmpty(message = "Broker name may not be Null", groups = {BrokerVerify.class})
	@Column(name = "NAME")
	private String authorisedEntity;
	
	@NotEmpty(message = "Broker email may not be Null", groups = {BrokerVerify.class})
	@Column(name = "EMAIL")
	private String authorisedPersonEmail;
	
	@NotEmpty(message = "Broker phone may not be Null", groups = {BrokerVerify.class})
	@Column(name = "PHONE")
	private String authorisedPersonMobile;
	
	
	
//	@Column(name = "AUTHORISED_LETTER")
	@NotNull(message = "Broker Authority Letter may not be Null", groups = {BrokerVerify.class})
	@Transient
	private Long authorityLetter;
	
	@NotEmpty(message = "Broker authorised person name may not be Null", groups = {BrokerApproval.class})
	@Column(name = "AUTHORISED_PERSON_NAME")
	private String authorisedPersonName;
	
	@NotNull(message = "Broker phone otp may not be Null", groups = {BrokerOtpVerify.class})
	@Column(name = "PHONE_OTP")
	private Integer phoneOtp;
	
	@NotNull(message = "Broker email otp may not be Null", groups = {BrokerOtpVerify.class})	
	@Column(name = "EMAIL_OTP")
	private Integer emailOtp;
	
	
	@Column(name = "OTP_VERIFIED")
	private String otpVerified;
	
	@Column(name = "KEYCLOAK_ID")
	private String keycloakId;
	
	
	//@NotEmpty(message = "Broker approved may not be Null", groups = {BrokerApproval.class})
	@Transient
	private String approved;
	
//	
	@OneToMany(targetEntity = BrokerClientMapping.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "BROKER_ID", referencedColumnName = "ID")
	private List<BrokerClientMapping> brokerClientMapping;
	
//	@OneToMany(targetEntity = BrokerMaster.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "CLIENTID", referencedColumnName = "CLIENTID")
//	private List<BrokerMaster> brokerMaster;
	
	
	@NotEmpty(message = "Broker client id may not be Null", groups = {BrokerOtpVerify.class,BrokerApproval.class})
	@Transient
	private String clientId;
	
	
	@Column(name="MODIFIED_ON")
	private Timestamp modifiedOn;
	
	
	
	
	
}
