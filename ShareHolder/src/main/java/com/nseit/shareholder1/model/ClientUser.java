//package com.nseit.shareholder1.model;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.nseit.shareholder1.modelInterfaces.CheckUserClientEmailAndPhoneInterface;
//import com.nseit.shareholder1.modelInterfaces.CheckUserClientIdInterface;
//import com.nseit.shareholder1.modelInterfaces.CheckUserSecurityQuestionInterface;
//import com.nseit.shareholder1.modelInterfaces.SetSecurityQuestionsInterface;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Entity
//@Table(name = "CLIENT_MASTER")
//public class ClientUser implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
////	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@Id
//
//	@Pattern(regexp = "^(IN|\\d{2})(\\d{6})-(\\d{8})$", message = "clientId is invalid",groups = {CheckUserClientIdInterface.class,CheckUserClientEmailAndPhoneInterface.class,CheckUserSecurityQuestionInterface.class,SetSecurityQuestionsInterface.class})
//	@Column(name = "CLIENT_ID")
//	private String clientId;
//
//	@Column(name = "SHARE_HOLDER_NAME")
//	private String shareholdername;
//
//	 @NotNull(message = "shares not null")
//	@Column(name = "SHARES")
//	private int shares;
//
//	// @Email(message = "email may not be null")
//	@Column(name = "EMAIL")
//	@NotEmpty(message = "email may not be null",groups = {CheckUserClientEmailAndPhoneInterface.class,CheckUserSecurityQuestionInterface.class,SetSecurityQuestionsInterface.class})
//	private String email;
//
//	@Pattern(regexp = "^(\\d{10})$", message = "phone is invalid",groups = {CheckUserClientEmailAndPhoneInterface.class,CheckUserSecurityQuestionInterface.class,SetSecurityQuestionsInterface.class})
//	@Column(name = "PHONE")
//	private String phone;
//
//	@NotEmpty(message = "pan number is required")
//	@Column(name = "PAN")
//	private String pan;
//
//	@Column(name = "BANK_NAME")
//	private String bankName;
//
//	@Column(name = "NOMINEE")
//	private String nominee;
//
//	@NotEmpty(message = "ifsc may not be null")
//	@Column(name = "IFSC")
//	private String ifsc;
//	
//	@Column(name = "ACCOUNT_NUM")
//	private String accountNum;
//	
//	@NotNull(message = "pincode may not be null")
//	@Column(name = "PINCODE")
//	private String pincode;
//
//	@OneToMany(targetEntity = BrokerClientMapping.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "CLIENT_ID", referencedColumnName = "CLIENT_ID")
//	private List<BrokerClientMapping> brokerClientMapping;
//
//}
