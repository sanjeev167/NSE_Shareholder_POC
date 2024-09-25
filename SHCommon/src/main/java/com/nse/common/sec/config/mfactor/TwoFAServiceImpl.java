package com.nse.common.sec.config.mfactor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infobip.ApiException;
import com.nse.common.sec.config.LocalUserDetails;
import com.nse.common.sec.model.OtpReqRes;

/**
 * @author sanjeevkumar
 * @Project Share_Holder
 * 03-Apr-2024
 * 10:47:16 pm
 * @Objective: This class will implement a vendor specific TwoFactor OTP authentications. This authentication includes OTP creation and its verification
 * and sending it either at a user's registered mobile or at a registered mail-id. Currently, Infobip-vendor implementation has been made. In future, other 
 * vendor implementation could be made and its corresponding implementation could be injected here.For example
 * @Autowired
 * DuoService twoFAService
 * Remark: Keep one service DI active at a time so that OTP-related operation could be performed through the active vendor implementation. 
 */
@Service
public class TwoFAServiceImpl implements TwoFAService{
	
	@Autowired
	InfobipService twoFAService;	
	
	@Override
	public OtpReqRes sendSmsOtp(LocalUserDetails localUserDetails) throws ApiException {
		OtpReqRes otpReqRes = twoFAService.sendOtpSms_For2FA(localUserDetails);
		return otpReqRes;
	}

	@Override
	public boolean validateSmsOtp(OtpReqRes otpReqRes) throws ApiException {
		boolean is2FaCorrect = false;
		is2FaCorrect = twoFAService.verifyOtpSms_For2FA(otpReqRes);		
		//printOtpValidationRequestContaint(otpReqRes);
		return is2FaCorrect;
	}
	@Override
	public OtpReqRes sendMailOtp(LocalUserDetails localUserDetails) throws ApiException {
		OtpReqRes otpReqRes = twoFAService.sendOtpEmail_For2FA(localUserDetails);
		return otpReqRes;
	}

	@Override
	public boolean validateMailOtp(OtpReqRes otpReqRes) throws ApiException {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * @param otpReqRes
	 */
	private void printOtpValidationRequestContaint(OtpReqRes otpReqRes) {
		System.out.println("username = " + otpReqRes.getUsername());		
		System.out.println("OTP received = " + otpReqRes.getOtp());
	}
}//End of TwoFAServiceImpl
