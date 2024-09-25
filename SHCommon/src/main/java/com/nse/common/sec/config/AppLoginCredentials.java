package com.nse.common.sec.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AppLoginCredentials {

	@NotEmpty
	@NotNull(message = "username can't be null")
	@NotBlank(message = "username is mandatory")
	private String username;
	
	@NotEmpty
	@NotNull(message = "password can't be null")
	@NotBlank(message = "Password is mandatory")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
