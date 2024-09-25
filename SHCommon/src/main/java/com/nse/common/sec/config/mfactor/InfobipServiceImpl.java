package com.nse.common.sec.config.mfactor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infobip.ApiException;
import com.infobip.api.TfaApi;
import com.infobip.api.TfaApi.CreateTfaEmailMessageTemplateRequest;
import com.infobip.api.TfaApi.Send2faPinCodeOverEmailRequest;
import com.infobip.model.EmailSendResponse;
import com.infobip.model.TfaCreateEmailMessageRequest;
import com.infobip.model.TfaPinType;
import com.infobip.model.TfaResendPinRequest;
import com.infobip.model.TfaStartAuthenticationRequest;
import com.infobip.model.TfaStartAuthenticationResponse;
import com.infobip.model.TfaStartEmailAuthenticationRequest;
import com.infobip.model.TfaStartEmailAuthenticationResponse;
import com.infobip.model.TfaVerifyPinRequest;
import com.infobip.model.TfaVerifyPinResponse;
import com.nse.common.sec.config.LocalUserDetails;
import com.nse.common.sec.model.OtpReqRes;
import com.nse.common.util.StaticPropertyHolder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author sanjeevkumar
 * @Project Share_Holder 
 * 03-Apr-2024 
 * 5:54:54 pm
 * @Objective: This class has been written with an objective of implementing different otp services of infobip.
 * [1] Sending otp at a user's registered mobile via sms
 * [2] Verifying this sent sma-otp via infobip api
 * [3] Sending otp at a user's registered email
 * [4] Verifying this sent mail-otp via infobip api
 *
 */
@Service("InfobipService")
public class InfobipServiceImpl extends InfobipService {
	
	

	/**
	 * Objective: This method will be used for sending an otp at a user's registered mobile.
	 * @param localUserDetails
	 * @return
	 * @throws ApiException
	 * Status of testing:  Working properly
	 */
	
	@Override
	public OtpReqRes sendOtpSms_For2FA(LocalUserDetails localUserDetails) throws ApiException {				
		String messageId = doMessageTemplateSetup(StaticPropertyHolder.tfaApi);		
		OtpReqRes otpReqRes = send2FaCodeViaSms(StaticPropertyHolder.tfaApi, localUserDetails.getPhone(), StaticPropertyHolder.appId, messageId);
		otpReqRes.setUsername(localUserDetails.getUsername());
		return otpReqRes;
	}// End of sendOtpSms_For2FA

	/**
	 * Objective: This is the place where the actual code for sms-otp has been written. An encoded PinId is
	 * generated while sending an otp at mobile no. This PinId will be used for validating this sent OTP in future.	
	 * We need to preserve this PinId somewhere in user's session so that we could have an access of it while OTP
	 * validation.
	 * @param tfaApi
	 * @param mobileNo
	 * @param appId
	 * @param messageId
	 * @return
	 * @throws ApiException
	 * Status of testing:  Working properly
	 */
	private OtpReqRes send2FaCodeViaSms(TfaApi tfaApi, String mobileNo, String appId, String messageId)
			throws ApiException {
		OtpReqRes otpReqRes = new OtpReqRes();
		// Send a 2FA code via an SMS
		TfaStartAuthenticationRequest startAuthenticationRequest = new TfaStartAuthenticationRequest()
				.applicationId(appId).messageId(messageId).from(StaticPropertyHolder.projectName).to(mobileNo);

		TfaStartAuthenticationResponse sendCodeResponse = tfaApi.sendTfaPinCodeOverSms(startAuthenticationRequest).execute();

		otpReqRes.setSentPinId(sendCodeResponse.getPinId());

		if (sendCodeResponse.getSmsStatus().equals("MESSAGE_SENT")) {
			otpReqRes.setOtpSentStatus(true);
			System.out.println("OTP has been sent at Mobile =>"+mobileNo +" whose PinId = "+sendCodeResponse.getPinId());
		} else {
			otpReqRes.setOtpSentStatus(false);
			System.out.println("OTP has not been sent at Mobile =>"+mobileNo);
		}
		return otpReqRes;
	}// End of send2FaCodeViaSms

	
	/**
	 * Objective : This will be called when the sms based otp needs to be validated. While validating an otp, its corresponding PinId needs to be passed to 
	 * the infobip api. We do the following steps	 
	 * [1] Prepare TfaVerifyPinRequest:  Using received user's otp, we prepare it.
	 * [2] Do this verification through tfaApi.verifyTfaPhoneNumber(). This method is receiving two parameters 
	 * verifyPinRequest and PinId.
	 * 
	 * @param otpReqRes
	 * @return
	 * @throws ApiException
	 * Status of testing:  Working properly
	 */
	@Override
	public boolean verifyOtpSms_For2FA(OtpReqRes otpReqRes) throws ApiException {
		boolean isOtpVerified = false;
		try {
			//Prepare TfaVerifyPinRequest
			TfaVerifyPinRequest verifyPinRequest = new TfaVerifyPinRequest().pin(otpReqRes.getOtp());			
			TfaVerifyPinResponse verifyResponse = StaticPropertyHolder.tfaApi.verifyTfaPhoneNumber(otpReqRes.getSentPinId(), verifyPinRequest).execute();
			isOtpVerified = verifyResponse.getVerified();
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
		return isOtpVerified;
	}// End of verifyOtpSms_For2FA

	/**
	 * Objective: This method will be used for sending an otp at a user's registered email.
	 * @param localUserDetails
	 * @return
	 * @throws ApiException
	 */	
	
	@Override
	public OtpReqRes sendOtpEmail_For2FA(LocalUserDetails localUserDetails) throws ApiException {		
		String emailListAsString = localUserDetails.getEmail();		
		String otp = "5678";//Here, we will provide otp for email
		OtpReqRes otpReqRes = send2FaCodeViaEmail(emailListAsString,"Sanjeev167@selfserviceib.com",localUserDetails.getUsername(), StaticPropertyHolder.projectName, StaticPropertyHolder.mailSubject, otp);
		return otpReqRes;
	}// End of sendOtpEmail_For2FA

	private OtpReqRes send2FaCodeViaEmail(String emailListAsString, String sender, String username, String projectName, String mailSubject, String otp) throws ApiException {
		
		OtpReqRes otpReqRes = new OtpReqRes();		
		
		try {
		String messageId=doMailTemplateSetup(StaticPropertyHolder.tfaApi);			
		TfaStartEmailAuthenticationRequest tfaStartEmailAuthenticationRequest = new TfaStartEmailAuthenticationRequest();	
		tfaStartEmailAuthenticationRequest.setApplicationId(StaticPropertyHolder.appId);
		tfaStartEmailAuthenticationRequest.setMessageId(messageId);
		tfaStartEmailAuthenticationRequest.setFrom("sanjeevkumar@nseit.com");//This is correct setting of single sender who can send 100 free mails
		                                                                     //If this channel is configured with a domain, then to mail could be anything within that domain
		tfaStartEmailAuthenticationRequest.setTo("sanjeevkumar@nseit.com");//This can be any mail-id other than gmail and yahoo
		//Place holder will contain a key value pair if it is defined in the template	
		
		Map<String , String> otpMap = new HashMap<String , String>();	
		otpMap.put("username", username);		
		tfaStartEmailAuthenticationRequest.setPlaceholders(otpMap);	
		Send2faPinCodeOverEmailRequest send2faPinCodeOverEmailRequest = StaticPropertyHolder.tfaApi.send2faPinCodeOverEmail(tfaStartEmailAuthenticationRequest);
        System.out.println("Before sending = > "+tfaStartEmailAuthenticationRequest.toString());
       
		TfaStartEmailAuthenticationResponse tfaStartEmailAuthenticationResponse = send2faPinCodeOverEmailRequest.execute();
		
		System.out.println("After sending "+tfaStartEmailAuthenticationResponse.getPinId());
		String pinId = null;

		if (tfaStartEmailAuthenticationResponse.getEmailStatus().equals("MESSAGE_SENT")) {
			pinId = tfaStartEmailAuthenticationResponse.getPinId();
			otpReqRes.setSentPinId(pinId);
			System.out.println("Mail has been sent successfully."+" pinId = > "+pinId);
		} else {
			System.out.println("Mail has not been sent.");
		}
		//TfaResendPinRequest tfaResendPinRequest = new TfaResendPinRequest();
		//StaticPropertyHolder.tfaApi.resend2faPinCodeOverEmail(pinId, tfaResendPinRequest);
		}catch(ApiException ex) {
			ex.printStackTrace();
		}
		return otpReqRes;
	}// End of send2FaCodeViaEmail

	
	
	@Override
	public boolean verifyOtpEmail_For2FA(OtpReqRes otpReqRes) throws ApiException {
		boolean isOtpVerified = false; 
		TfaVerifyPinRequest verifyPinRequest = new TfaVerifyPinRequest().pin(otpReqRes.getOtp());	
		TfaVerifyPinResponse verifyResponse = StaticPropertyHolder.tfaApi.verifyTfaPhoneNumber(otpReqRes.getSentPinId(), verifyPinRequest).execute();		
		isOtpVerified = verifyResponse.getVerified();
		if(isOtpVerified)
			System.out.println("Otp is verified.");
		else
			System.out.println("Otp is not verified.");
		return isOtpVerified;		
	}// End of verifyOtpEmail_For2FA
	
	}// End of InfobipServiceImpl
