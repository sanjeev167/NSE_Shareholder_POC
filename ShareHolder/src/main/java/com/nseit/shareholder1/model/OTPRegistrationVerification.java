package com.nseit.shareholder1.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nseit.shareholder1.modelInterfaces.RegisterInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "OTP_REGISTRATION_VERIFY")
public class OTPRegistrationVerification implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "clientId may not be null")
	@Pattern(regexp = "^(IN|\\d{2})(\\d{6})-(\\d{8})$", message = "clientId is invalid",groups = {RegisterInterface.class})
	@Column(name = "CLIENT_ID")
	private String clientId;
	
	@NotEmpty(message = "email may not be null",groups = {RegisterInterface.class})
	@Column(name = "EMAIL")
	private String email;
	
	@Pattern(regexp = "^(\\d{10})$", message = "phone is invalid",groups = {RegisterInterface.class})
	@Column(name = "PHONE")
	private String phone;
	
	//@NotEmpty(message = "emailOtp  is required")
	@NotNull(message = "emailOtp is required")
	@Column(name = "EMAIL_OTP")
	private int emailOtp;
	
	//@NotEmpty(message = "phoneOtp number is required")
	@NotNull(message = "phoneOtp is required")
	@Column(name = "PHONE_OTP")
	private int phoneOtp;
	
	

}
