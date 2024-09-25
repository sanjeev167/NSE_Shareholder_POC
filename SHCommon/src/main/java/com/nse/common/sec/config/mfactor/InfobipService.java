package com.nse.common.sec.config.mfactor;

import com.infobip.ApiException;
import com.infobip.api.TfaApi;
import com.infobip.api.TfaApi.CreateTfaEmailMessageTemplateRequest;
import com.infobip.model.TfaCreateEmailMessageRequest;
import com.infobip.model.TfaCreateMessageRequest;
import com.infobip.model.TfaEmailMessage;
import com.infobip.model.TfaMessage;
import com.infobip.model.TfaPinType;
import com.nse.common.sec.config.LocalUserDetails;
import com.nse.common.sec.model.OtpReqRes;
import com.nse.common.util.StaticPropertyHolder;

/**
 * @author sanjeevkumar
 * @Project Share_Holder 
 * 03-Apr-2024 
 * 10:39:21 pm
 * @Objective:
 *
 */
public abstract class InfobipService {
	
	
	protected String doMessageTemplateSetup(TfaApi tfaApi) throws ApiException {
		// Message template setup
		TfaCreateMessageRequest createMessageRequest = new TfaCreateMessageRequest()
															.messageText("Your pin is {{pin}}")
															.pinType(TfaPinType.NUMERIC).pinLength(4);

		TfaMessage tfaMessageTemplate = tfaApi.createTfaMessageTemplate(StaticPropertyHolder.appId, createMessageRequest).execute();

		return tfaMessageTemplate.getMessageId();
	}
	
	protected String doMailTemplateSetup(TfaApi tfaApi) throws ApiException {
		// Message template setup
		TfaCreateEmailMessageRequest tfaCreateEmailMessageRequest = new TfaCreateEmailMessageRequest();		
	    tfaCreateEmailMessageRequest.setFrom("Shareholding<Sanjeev167@selfserviceib.com>");	//This correct setting for the custom template.This template has been created within 
	                                                                                        //Sanjeev167 account at infobip
		tfaCreateEmailMessageRequest.setPinType(TfaPinType.NUMERIC);
		tfaCreateEmailMessageRequest.setPinLength(4);		
		tfaCreateEmailMessageRequest.emailTemplateId(200000000108320L);//Custom template id created at infobip site
		CreateTfaEmailMessageTemplateRequest createTfaEmailMessageTemplateRequest =
				                             tfaApi.createTfaEmailMessageTemplate(StaticPropertyHolder.appId, tfaCreateEmailMessageRequest);
		
		TfaEmailMessage tfaEmailMessage = createTfaEmailMessageTemplateRequest.execute();
		 
		return tfaEmailMessage.getMessageId();
	}
	
	// Methods for generating 2FA stuffs and sending them on the registered devices
	abstract OtpReqRes sendOtpSms_For2FA(LocalUserDetails localUserDetails) throws ApiException;

	abstract OtpReqRes sendOtpEmail_For2FA(LocalUserDetails localUserDetails) throws ApiException;

	// Methods for validating 2FA corresponding to all the above listed approaches.
	abstract boolean verifyOtpSms_For2FA(OtpReqRes otpReqRes) throws ApiException;

	abstract boolean verifyOtpEmail_For2FA(OtpReqRes otpReqRes) throws ApiException;	

}// End of InfobipService
