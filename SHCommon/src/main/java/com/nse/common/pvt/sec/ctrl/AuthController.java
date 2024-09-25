
package com.nse.common.pvt.sec.ctrl;

import java.io.IOException;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infobip.ApiException;
import com.nse.common.api.struct.ApiResponse;
import com.nse.common.sec.config.LocalUserDetails;
import com.nse.common.sec.config.mfactor.TwoFAService;
import com.nse.common.sec.entities.RefreshToken;
import com.nse.common.sec.model.AuthRequest;
import com.nse.common.sec.model.JwtResponse;
import com.nse.common.sec.model.LoginResponse;
import com.nse.common.sec.model.OtpReqRes;
import com.nse.common.sec.model.RefreshTokenRequest;
import com.nse.common.sec.service.JwtUtils;
import com.nse.common.sec.service.RefreshTokenService;

/**
 * @author sanjeevkumar 
 * 21-Mar-2024 
 * 7:30:49 pm 
 * Objective: This class is used for authentication and token related actions.
 */

@RestController
@RequestMapping("/auth/v1")
public class AuthController extends ApiBaseCtrl {

	@Autowired
	private TwoFAService twoFAService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	/**
	 * Objective: This login will be used by all types of share-holding users. They
	 * may be a share-holder or an Authorised-Representative or a NSE-User. Here,
	 * authentication-manager is using two authentication-providers internally. One
	 * is for DAO-Authentication-provider, which will authenticate local-users i.e.
	 * a share-holding users [Authorized-Reopresentative and Shareholders.], and
	 * another one is Ldap-Authentication-provider which will authenticate
	 * NSE-Users.
	 * 
	 *            Authentication-Manager 
	 *                    || 
	 * Dao-Authentication-Provider || Ldap-Authentication-Provider
	 * 
	 * @param authRequest
	 * @return
	 * @throws ApiException
	 * @throws IOException 
	 */
	@PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> login(@Valid @RequestBody AuthRequest authRequest) throws ApiException, IOException {

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), 
				                                                           authRequest.getPassword()));
		if (authentication.isAuthenticated()) {  
			LocalUserDetails localUserDetails = (LocalUserDetails) authentication.getPrincipal();
			// Return response in a pre-defined format
			//apiResponse = sendSmsOtpAndPrepareResponse(localUserDetails, authRequest);
			apiResponse = sendMailOtpAndPrepareResponse(localUserDetails, authRequest);
			return ResponseEntity.ok().body(apiResponse);
		} else {
			throw new BadCredentialsException("Invalid-Credential !");
		}
	}// End of login

	/**
	 * Objective : This will be called for validating the supplied OTP which was received at a mobile. Once the OTP is validated, an access-token and
	 * refresh-token will be generated.
	 * 
	 * @param otpReqRes
	 * @return
	 * @throws ApiException
	 */
	@PostMapping(value = "/validateSmsOtpAndGetToken", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> validateSmsOtpAndGetToken(@Valid @RequestBody OtpReqRes otpReqRes) throws ApiException {
		boolean isUserAvailableInSession = checkUserAvailabilityInSession(otpReqRes);
		if (isUserAvailableInSession) {
			boolean isOtpVerified = twoFAService.validateSmsOtp(otpReqRes);
			if (isOtpVerified) {
				apiResponse = generateAccessAndRefreshToken(otpReqRes);
				return ResponseEntity.ok().body(apiResponse);
			} else {
				throw new BadCredentialsException("Invalid OTP ..!!");
			}
		} else {
			throw new BadCredentialsException("1FA is not cleared!");
		}
	}// End of validateSmsOtpAndGetToken
	
	/**
	 * Objective : This will be called for validating the supplied OTP which was received at a mobile. Once the OTP is validated, an access-token and
	 * refresh-token will be generated.
	 * 
	 * @param otpReqRes
	 * @return
	 * @throws ApiException
	 */
	@PostMapping(value = "/validateMailOtpAndGetToken", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> validateMailOtpAndGetToken(@Valid @RequestBody OtpReqRes otpReqRes) throws ApiException {

		boolean isUserAvailableInSession = checkUserAvailabilityInSession(otpReqRes);
		if (isUserAvailableInSession) {
			boolean isOtpVerified = twoFAService.validateMailOtp(otpReqRes);
			if (isOtpVerified) {System.out.println("pin verified");
				apiResponse = generateAccessAndRefreshToken(otpReqRes);
				return ResponseEntity.ok().body(apiResponse);

			} else {
				throw new BadCredentialsException("Invalid OTP ..!!");
			}
		} else {
			throw new BadCredentialsException("1FA is not cleared!");
		}
	}// End of validateSmsOtpAndGetToken

	/**
	 * Objective: This is used when an access-token needs to be taken through refresh token. 
	 * @param refreshTokenRequest
	 * @return
	 */
	@PostMapping(value = "/refreshToken", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {

		Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByRefreshToken(refreshTokenRequest.getRefreshToken());
		JwtResponse jwtResponse;
		RefreshToken refreshToken;

		if (refreshTokenOptional != null) {
			refreshToken = refreshTokenOptional.get();
			refreshTokenService.verifyExpiration(refreshToken);
			jwtResponse = new JwtResponse();
			jwtResponse.setAccessToken(jwtUtils.GenerateToken(refreshToken.getUserReg().getLoginid()));
			jwtResponse.setRefreshToken(refreshTokenRequest.getRefreshToken());
			// Return response in a pre-defined format
			apiReq = makeApiMetaDataWhileLogin(refreshToken.getUserReg().getLoginid());
			apiReq.setPayLoad(refreshTokenRequest);
			apiResponse = makeSuccessResponse(jwtResponse, apiReq);
			return ResponseEntity.ok().body(apiResponse);
		} else {
			throw new RuntimeException("Refresh Token is not in DB..!!");
		}

	}// End of refreshToken

	/**
	 * Objective: It will generate fresh access and refresh token
	 * @param otpRequest
	 * @return
	 */
	private ApiResponse generateAccessAndRefreshToken(@Valid OtpReqRes otpReqRes) {

		JwtResponse jwtResponse = new JwtResponse();
		String actualUserName = otpReqRes.getUsername().split("@")[0];
		jwtResponse.setAccessToken(jwtUtils.GenerateToken(actualUserName));
		jwtResponse.setRefreshToken((refreshTokenService.createRefreshToken(actualUserName).getRefreshToken()));
		// Return response in a pre-defined format
		apiReq = makeApiMetaDataWhileLogin(otpReqRes.getUsername());
		apiReq.setPayLoad(otpReqRes);
		apiResponse = makeSuccessResponse(jwtResponse, apiReq);
		return apiResponse;
	}// End of generateAccessAndRefreshToken

	/**
	 * Objective: It will check availability of user in the session before going for validating received otp
	 * @param otpRequest
	 * @return
	 */
	private boolean checkUserAvailabilityInSession(@Valid OtpReqRes otpReqRes) {
		String actualUserName = otpReqRes.getUsername().split("@")[0];
        // System.out.println("Coming User Name = "+actualUserName+"  apiUser "+apiUser);
		if (apiUser != null && apiUser.equals(actualUserName))
			return true;
		else
			return false;
	}

	/**
	 * @param localUserDetails
	 * @return
	 * @throws ApiException
	 */
	private ApiResponse sendSmsOtpAndPrepareResponse(LocalUserDetails localUserDetails, AuthRequest authRequest)
			throws ApiException {
		apiReq = makeApiMetaDataWhileLogin(localUserDetails.getUsername());
		apiReq.setPayLoad(authRequest);
		LoginResponse loginResponse = null;
		// Generate OTP and send it at registered mail-id or mobile of the user.
		OtpReqRes otpReqRes = twoFAService.sendSmsOtp(localUserDetails);
		if (otpReqRes.isOtpSentStatus()) {
			loginResponse = prepareLoginResponse(localUserDetails, otpReqRes.isOtpSentStatus());
			loginResponse.setPid(otpReqRes.getSentPinId());
			apiResponse = makeSuccessResponse(loginResponse, apiReq);
			return apiResponse;
		} else {
			throw new BadCredentialsException("Issues in OTP generation and sending !");
		}
	}// End of sendSmsOtpAndPrepareResponse


	/**
	 * @param localUserDetails
	 * @return
	 * @throws ApiException
	 * @throws IOException 
	 */
	private ApiResponse sendMailOtpAndPrepareResponse(LocalUserDetails localUserDetails, AuthRequest authRequest) throws ApiException, IOException {
		apiReq = makeApiMetaDataWhileLogin(localUserDetails.getUsername());
		apiReq.setPayLoad(authRequest);
		LoginResponse loginResponse = null;
		// Generate OTP and send it at registered mail-id or mobile of the user.
		OtpReqRes otpReqRes = twoFAService.sendMailOtp(localUserDetails);
		if (otpReqRes.isOtpSentStatus()) {
			loginResponse = prepareLoginResponse(localUserDetails, otpReqRes.isOtpSentStatus());
			loginResponse.setPid(otpReqRes.getSentPinId());
			apiResponse = makeSuccessResponse(loginResponse, apiReq);
			return apiResponse;
		} else {
			throw new BadCredentialsException("Issues in OTP generation and sending !");
		}
	}// End of sendMailOtpAndPrepareResponse

	private LoginResponse prepareLoginResponse(LocalUserDetails localUserDetails, boolean isOtpSent) {
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setUserPhone(localUserDetails.getPhone());
		loginResponse.setUsername(localUserDetails.getUsername());
		loginResponse.setIs2FaEnabled(localUserDetails.isIs2fEnabled());
		loginResponse.setOtpSent(isOtpSent);
		loginResponse.setUserType(localUserDetails.getUserContext());
		loginResponse.setCr_code(localUserDetails.getOtpShared());// This will be changed latter
		return loginResponse;
	}// End of prepareLoginResponse

}// End of AuthController
