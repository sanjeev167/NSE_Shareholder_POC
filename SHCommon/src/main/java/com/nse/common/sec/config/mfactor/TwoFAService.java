package com.nse.common.sec.config.mfactor;

import java.io.IOException;

import com.infobip.ApiException;
import com.nse.common.sec.config.LocalUserDetails;
import com.nse.common.sec.model.OtpReqRes;

/**
 * @author sanjeevkumar
 * @Project Share_Holder
 * 03-Apr-2024
 * 10:46:30 pm
 * @Objective: This interface works as a wrapper interface which will include all the required methods for OTP-operations.
 *
 */
public interface TwoFAService {
	
	OtpReqRes sendSmsOtp(LocalUserDetails localUserDetails) throws ApiException;
    boolean validateSmsOtp(OtpReqRes otpReqRes) throws ApiException;
    OtpReqRes sendMailOtp(LocalUserDetails localUserDetails) throws ApiException, IOException;
    
    
    boolean validateMailOtp(OtpReqRes otpReqRes) throws ApiException;
    
}//End of TwoFAService
