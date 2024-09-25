package com.nseit.shareholder1.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.nseit.shareholder1.modelInterfaces.ResetPasswordInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPassword implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "userName may not be null", groups = { ResetPasswordInterface.class })
	private String userName;
	@NotEmpty(message = "newPassword may not be null", groups = { ResetPasswordInterface.class })
	private String newPassword;
	@NotEmpty(message = "confirmPassword may not be null", groups = { ResetPasswordInterface.class })
	private String confirmPassword;

}
