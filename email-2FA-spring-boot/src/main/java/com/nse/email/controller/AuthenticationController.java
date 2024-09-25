package com.nse.email.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nse.email.dto.OtpVerificationRequestDto;
import com.nse.email.dto.TokenRefreshRequestDto;
import com.nse.email.dto.UserAccountCreationRequestDto;
import com.nse.email.dto.UserLoginRequestDto;
import com.nse.email.dto.UserLoginSuccessDto;
import com.nse.email.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Creates a user account in the system")
    public ResponseEntity<?> userAccountCreationHandler(
            @RequestBody(required = true) final UserAccountCreationRequestDto userAccountCreationRequestDto) {
        return userService.createAccount(userAccountCreationRequestDto);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Endpoint to authenticate users credentials")
    public ResponseEntity<?> userLoginHandler(
            @RequestBody(required = true) final UserLoginRequestDto userLoginRequestDto) {
        return userService.login(userLoginRequestDto);
    }

    @PostMapping(value = "/verify-otp", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "verifies OTP and returns JWT corresponding to the user")
    public ResponseEntity<UserLoginSuccessDto> otpVerificationHandler(
            @RequestBody(required = true) final OtpVerificationRequestDto otpVerificationRequestDto) {
        return userService.verifyOtp(otpVerificationRequestDto);
    }

    @PutMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Returns new access_token")
    public ResponseEntity<?> tokenRefresherHandler(
            @RequestBody(required = true) final TokenRefreshRequestDto tokenRefreshRequestDto) {
        return userService.refreshToken(tokenRefreshRequestDto);
    }

}
