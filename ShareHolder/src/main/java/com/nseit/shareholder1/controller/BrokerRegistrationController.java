package com.nseit.shareholder1.controller;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nseit.shareholder1.model.BrokerDownloadFileModel;
import com.nseit.shareholder1.model.BrokerMaster;
import com.nseit.shareholder1.model.LoginOTP;
import com.nseit.shareholder1.model.LoginRequest;
import com.nseit.shareholder1.modelInterfaces.BrokerApproval;
import com.nseit.shareholder1.modelInterfaces.BrokerOtpVerify;
import com.nseit.shareholder1.modelInterfaces.BrokerVerify;
import com.nseit.shareholder1.modelInterfaces.CheckLoginOtpInterface;
import com.nseit.shareholder1.service.BrokerRegistrationService;
import com.nseit.shareholder1.service.ShareHolderService;
import com.nseit.shareholder1.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/brokerregistration")
@Slf4j
@Validated
public class BrokerRegistrationController {

	private static final RequestMethod[] POST = null;

	@Autowired
	ShareHolderService shareHolderService;

	@Autowired
	BrokerRegistrationService brokerRegistrationService;
	
	@Autowired
	ResponseUtil response;

	
	@RequestMapping(value = "/brokerRegister/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> brokerRegister(@PathVariable String version,
			@RequestParam("authorityLetter") MultipartFile multiPartFile,
			@Valid @RequestParam("clientId") @Pattern(regexp = "^(IN|\\d{2})(\\d{6})-(\\d{8})$", message = "clientId is invalid") String clientId,
			@Valid @RequestParam("authorisedEntity") @NotEmpty(message = "Authorised Entity may not be null") String brokerName,
			@Valid @RequestParam("authorisedPersonEmail") @NotEmpty(message = "email may not be null") String brokerEmail,
			@Valid @RequestParam("authorisedPersonMobile") @Pattern(regexp = "^(\\d{10})$", message = "phone is invalid") String phone,
			@Valid @RequestParam("authorisedPersonName") @NotEmpty(message = "Authorised Person Name may not be null") String authorisedPersonName) {

		// private ResponseEntity<?> brokerRegister(@PathVariable String
		// version,@RequestParam("File") MultipartFile multiPartFile,
		// @RequestParam("BrokerMaster") BrokerMaster brokerMaster) {
		// log.info("in brokerregister register controller " + version + " BrokerMaster
		// value is: " + brokerMaster);
		try {
			return brokerRegistrationService.brokerRegister(multiPartFile, version, clientId, brokerName, brokerEmail,
					phone, authorisedPersonName);
		} catch (ConstraintViolationException ce) {
			String msg = ce.getMessage();
			return response.getAuthResponse("BAD_REQUEST", HttpStatus.BAD_REQUEST, msg, version);
		}
	}

	@RequestMapping(value = "/brokerVerify/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> brokerVerify(@PathVariable String version,@Validated(BrokerVerify.class) @RequestBody BrokerMaster brokerMaster){
		return brokerRegistrationService.brokerVerify(version, brokerMaster);
	}
	
	@RequestMapping(value = "/brokerOtpVerify/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> brokerOtpVerify(@PathVariable String version,@Validated(BrokerOtpVerify.class) @RequestBody BrokerMaster brokerMaster){
		return brokerRegistrationService.brokerOtpVerify(version, brokerMaster);
	}
	
	@RequestMapping(value = "/brokerLogin/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> brokerLogin(@PathVariable String version,@Valid @RequestBody LoginRequest loginRequest){
		return brokerRegistrationService.brokerLogin(version, loginRequest);
	}
	
	@RequestMapping(value = "/brokerLoginOtpVerified/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> brokerLoginOtpVerified(@PathVariable String version,@Validated(CheckLoginOtpInterface.class) @RequestBody LoginOTP loginOTP){
		return brokerRegistrationService.brokerLoginOtpVerified(version, loginOTP);
	}
	
	@RequestMapping(value = "/brokerApproval/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> brokerApproval(@PathVariable String version,@Validated(BrokerApproval.class) @RequestBody BrokerMaster brokerMaster){
		return brokerRegistrationService.brokerApproval(version, brokerMaster);
	}


	/*
	 * @RequestMapping(value = "/getAllBrokerDetails/{version}", method =
	 * RequestMethod.POST) public ResponseEntity<?>
	 * getAllBrokerDetails(@PathVariable String
	 * version,@Validated(GetAllBrokerDetailsInterface.class) @RequestBody
	 * GetAllBrokerDetailsModel getAllBrokerDetailsModel){ return
	 * brokerRegistrationService.getAllBrokerDetails(version,
	 * getAllBrokerDetailsModel.getClientId()); }
	 */
	
	/*
	 * @RequestMapping(value = "/getAllBrokerDetails/{version}", method =
	 * RequestMethod.POST) public ResponseEntity<?>
	 * getAllBrokerDetails(@PathVariable String version){ return
	 * brokerRegistrationService.getAllBrokerDetails(version); }
	 */
	@RequestMapping(value = "/brokerDownloadFile/{version}", method = RequestMethod.POST)
	public ResponseEntity<?> brokerDownloadFile(@PathVariable String version,@Valid @RequestBody BrokerDownloadFileModel brokerDownloadFileModel){
		log.info("Map----------->"+brokerDownloadFileModel.getId());
		return brokerRegistrationService.brokerDownloadFile(version, brokerDownloadFileModel.getId());
	}
//	@RequestMapping(value = "/authorisedLetter", method = RequestMethod.POST, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
//	private AuthorisedLetter authorisedLetter(@RequestParam("File") MultipartFile multiPartFile) {
//		log.info("multiPartFile--------->" + multiPartFile);
//		return brokerRegistrationService.authorisedLetter(multiPartFile);
//	}
	

}
