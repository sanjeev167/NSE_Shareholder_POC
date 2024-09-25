package com.nse.common.sec.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author sanjeevkumar
 * @Project Share_Holder
 * 03-Apr-2024
 * 4:11:37 pm
 * @Objective: OTP request and response will be carried through this POJO.
 *
 */
public class OtpReqRes {
	@NotNull(message = "username can't be null")
	@NotBlank(message = "username is mandatory")
	private String username; 
	
	@NotNull(message = "otp can't be null")
	@NotBlank(message = "otp is mandatory")
	private String otp;	
	 
	private String sentPinId;//It needs to be returned while sending back the received otp. Will not be shown at 
	                         //the front end while asking for supplying otp 
	
	
	private boolean otpSentStatus;
	
	public OtpReqRes() {
		super();		
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
	 * @return the sentPinId
	 */
	public String getSentPinId() {
		return sentPinId;
	}

	/**
	 * @param sentPinId the sentPinId to set
	 */
	public void setSentPinId(String sentPinId) {
		this.sentPinId = sentPinId;
	}

	
	public boolean isOtpSentStatus() {
		return otpSentStatus;
	}

	public void setOtpSentStatus(boolean otpSentStatus) {
		this.otpSentStatus = otpSentStatus;
	}

	
	
}
