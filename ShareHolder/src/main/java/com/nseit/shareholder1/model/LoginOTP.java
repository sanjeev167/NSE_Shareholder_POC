package com.nseit.shareholder1.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.nseit.shareholder1.modelInterfaces.CheckLoginOtpInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "LOGIN_OTP")
public class LoginOTP implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "username is required",groups = {CheckLoginOtpInterface.class})
	@Column(name = "USERNAME")
	private String userName;
	
	@NotNull(message = "otp  is required",groups = {CheckLoginOtpInterface.class})
	@Column(name = "OTP")
	private Integer otp;
	
	@Column(name="TOKEN")
	private String token;
	
	@Column(name="REFRESH_TOKEN")
	private String refreshToken;
	
//	@Column(name = "CREATED_ON")
//	private Date createdOn;

//	@Column(name = "UPDATED_ON")
//	private Date updatedOn;
}
