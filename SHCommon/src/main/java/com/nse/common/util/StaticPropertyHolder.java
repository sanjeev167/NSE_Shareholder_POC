package com.nse.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.ApiKey;
import com.infobip.BaseUrl;
import com.infobip.api.EmailApi;
import com.infobip.api.TfaApi;
import com.infobip.model.TfaApplicationRequest;
import com.infobip.model.TfaApplicationResponse;

import jakarta.annotation.PostConstruct;

/**
 * sanjeevkumar 
 * 22-Jul-2024 
 * 4:04:34 pm
 * Objective: This class has been used for assigning static variable through @PostConstruct so that these static variables could be used with for initializing 
 * other static actions done in other classes.
 */
@Configuration
public class StaticPropertyHolder {

	
	public static String mailSubject;
	public static String projectName;	
	
	public static ApiClient apiClient;//
	public static EmailApi emailApi;//
	public static TfaApi tfaApi;//			
	public static String appId;//	This will be initialized after creating tfaApi

	@Value("${infobip.API_KEY}")
	String API_KEY;

	@Value("${infobip.BASE_URL}")
	String BASE_URL;	

	@Value("${infobip.mailSubject}")
	String mailSubject_;	

	@Value("${infobip.projectName}")
	String projectName_;	
	
	//This is used for initializing static fields.
	@PostConstruct
    public void init() throws ApiException {
		mailSubject=mailSubject_;projectName=projectName_;
		// Initialize client for Two-Factor Authentication (2FA) through its constructor
		apiClient = ApiClient.forApiKey(ApiKey.from(API_KEY)).withBaseUrl(BaseUrl.from(BASE_URL)).build();
		tfaApi = new TfaApi(apiClient);
		emailApi = new EmailApi(apiClient);
		// Application setup
		TfaApplicationRequest applicationRequest = new TfaApplicationRequest().name("2FA application");
		TfaApplicationResponse tfaApplication = tfaApi.createTfaApplication(applicationRequest).execute();
		appId = tfaApplication.getApplicationId();		
    }
}
