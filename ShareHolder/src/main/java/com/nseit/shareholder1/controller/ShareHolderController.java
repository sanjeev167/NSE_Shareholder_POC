package com.nseit.shareholder1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nseit.shareholder1.model.BenposeDataMasterModel;
import com.nseit.shareholder1.model.ChangePassword;
//import com.nseit.shareholder1.model.ClientUser;
import com.nseit.shareholder1.model.LoginOTP;
import com.nseit.shareholder1.model.LoginRequest;
import com.nseit.shareholder1.model.OTPRegistrationVerification;
import com.nseit.shareholder1.model.ResetPassword;
import com.nseit.shareholder1.model.UserMaster;
import com.nseit.shareholder1.modelInterfaces.ChangePasswordInterface;
import com.nseit.shareholder1.modelInterfaces.CheckLoginOtpInterface;
import com.nseit.shareholder1.modelInterfaces.CheckUserClientEmailAndPhoneInterface;
import com.nseit.shareholder1.modelInterfaces.CheckUserClientIdInterface;
import com.nseit.shareholder1.modelInterfaces.CheckUserIdInterface;
import com.nseit.shareholder1.modelInterfaces.CheckUserSecurityQuestionInterface;
import com.nseit.shareholder1.modelInterfaces.LoginInterface;
import com.nseit.shareholder1.modelInterfaces.RegisterInterface;
import com.nseit.shareholder1.modelInterfaces.ResetPasswordInterface;
import com.nseit.shareholder1.modelInterfaces.SetSecurityQuestionsInterface;
import com.nseit.shareholder1.service.KeycloakService;
import com.nseit.shareholder1.service.ShareHolderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/shareholder")
@Slf4j

public class ShareHolderController {

	@Autowired
	ShareHolderService shareHolderService;
	
	@Autowired
	KeycloakService keycloakService;

	@RequestMapping(value = "/login/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> login(@PathVariable String version,@Validated(LoginInterface.class) @RequestBody LoginRequest loginRequest)
			throws Exception {
		log.info("Value coming as request are Version :" + version + " LoginRequest :" + loginRequest);
		return shareHolderService.login(loginRequest, version);
	}

	/*
	 * @RequestMapping(value = "/logout/{version}", method = RequestMethod.POST)
	 * private ResponseEntity<?> logout(@PathVariable String version,
	 * HttpServletRequest httpServletRequest) { log.info("in  logout " + version);
	 * String authorization = httpServletRequest.getHeader("Authorization"); if
	 * (authorization != null) { String token = authorization.substring(7); return
	 * shareHolderService.logout(version, token); } else
	 * 
	 * return ResponseEntity.status(HttpStatus.FORBIDDEN).body(true); }
	 */

	@RequestMapping(value = "/register/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> register(@PathVariable String version,@Validated(RegisterInterface.class)@RequestBody OTPRegistrationVerification userMaster) {
		log.info("in  register controller " + version + " UserMaster value is: " + userMaster.getClientId());

		return shareHolderService.register(version, userMaster.getClientId(), userMaster);
	}

	@RequestMapping(value = "/checkLoginOtp/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> checkLoginOtp(@PathVariable String version,@Validated(CheckLoginOtpInterface.class) @RequestBody LoginOTP loginOTP) {
		log.info("in  register controller " + version + " UserMaster value is: " + loginOTP);

		return shareHolderService.checkLoginOtp(loginOTP, version);
	}

	@RequestMapping(value = "/checkUserClientId/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> checkUserClientId(@PathVariable String version,@Validated(CheckUserClientIdInterface.class) @RequestBody BenposeDataMasterModel client) {
		log.info("in  checkUserClientId controller " + version + " clientId value is: " + client);

		return shareHolderService.checkUserClientId(version, client.getClientId());
	}

	@RequestMapping(value = "/checkUserClientEmailAndPhone/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> checkUserClientEmailAndPhone(@PathVariable String version,
			@Validated(CheckUserClientEmailAndPhoneInterface.class) @RequestBody  BenposeDataMasterModel client) {
		log.info("in  checkUserClientId controller " + version + " clientId value is: " + client);

		return shareHolderService.checkUserClientEmailAndPhone(version, client);
	}
	
	/*
	 * @ResponseStatus(HttpStatus.BAD_REQUEST)
	 * 
	 * @ExceptionHandler(MethodArgumentNotValidException.class) public Map<String,
	 * String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
	 * Map<String, String> errors = new HashMap<>();
	 * 
	 * ex.getBindingResult().getFieldErrors().forEach(error ->
	 * errors.put(error.getField(), error.getDefaultMessage()));
	 * 
	 * return errors; }
	 */

//	@RequestMapping(value = "/OTPverify/{version}", method = RequestMethod.POST)
//	private ResponseEntity<?> otpVerify(@PathVariable String version,
//			@RequestBody OTPRegistrationVerification otpRegistrationVerification) {
//		log.info("in  checkUserClientId controller " + version + " clientId value is: " + otpRegistrationVerification);
//
//		return shareHolderService.otpVerify(version, otpRegistrationVerification);
//	}

	@RequestMapping(value = "/checkUserSecurityQuestion/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> checkUserSecurityQuestion(@PathVariable String version,@Validated(CheckUserSecurityQuestionInterface.class) @RequestBody BenposeDataMasterModel client) {
		log.info("in  checkUserSecurityQuestion controller " + version + " clientId value is: " + client);

		return shareHolderService.checkUserSecurityQuestion(version, client);
	}

	@RequestMapping(value = "/setSecurityQuestions/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> setSecurityQuestions(@Validated(SetSecurityQuestionsInterface.class) @RequestBody BenposeDataMasterModel client, @PathVariable String version) {
		log.info("in  setSecurityQuestions controller " + version + " clientId value is: " + client);

		return shareHolderService.setSecurityQuestions(client, version);
	}

	@RequestMapping(value = "/changePassword/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> changePassword(@Validated(ChangePasswordInterface.class) @RequestBody ChangePassword changePaswword, @PathVariable String version) {
		log.info("in  changePassword controller " + version + " changePaswword value is: " + changePaswword);

		return shareHolderService.changePassword(changePaswword, version);
	}

	@RequestMapping(value = "/checkUserId/{version}", method = RequestMethod.POST)
	private ResponseEntity<?> checkUserId(@PathVariable String version, @Validated(CheckUserIdInterface.class) @RequestBody UserMaster user) {
		log.info("in  checkUserId controller " + version + " clientId value is: " + user);

		return shareHolderService.checkUserId(version, user);
	}
	
//	@RequestMapping(value = "/resetPassword/{version}", method = RequestMethod.POST)
//	private ResponseEntity<?> resetPassword(@RequestBody ResetPassword resetPassword, @PathVariable String version) {
//		log.info("in  forgotPassword controller " + version + " forgotPassword value is: " + resetPassword);
//
//		return shareHolderService.resetPassword(resetPassword, version);
//	}
	
	@RequestMapping(value = "/resetPassword/{version}", method = RequestMethod.POST)
	ResponseEntity<?> resetPassword( @Validated(ResetPasswordInterface.class) @RequestBody ResetPassword resetPassword,String version){
//		String id = keycloakService.resetPassword(json.get("username"), json.get("password"), json.get("confirmPassword"));
		//return keycloakService.resetPassword(json.get("username"), json.get("password"),json.get("confirmPassword"));
		return keycloakService.resetPassword(resetPassword.getUserName(),resetPassword.getNewPassword(),resetPassword.getConfirmPassword());
		
//		return ResponseEntity.ok("Created------------------->"+id);
		}
}
