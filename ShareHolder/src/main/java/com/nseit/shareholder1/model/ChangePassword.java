package com.nseit.shareholder1.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.nseit.shareholder1.modelInterfaces.ChangePasswordInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangePassword implements Serializable {

	@NotEmpty(message = "username  is required",groups = {ChangePasswordInterface.class})
	private String username;

	@NotEmpty(message = "oldPassword number is required",groups = {ChangePasswordInterface.class})
	private String oldPassword;

	@NotEmpty(message = "newPassword number is required",groups = {ChangePasswordInterface.class})
	private String newPassword;
	
	@NotEmpty(message = "confirmPassword number is required",groups = {ChangePasswordInterface.class})
	private String confirmPassword;

}
